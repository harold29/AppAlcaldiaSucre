/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push.comandos;

import java.io.InputStream;

import javax.microedition.io.Connection;

import net.rim.device.api.system.EventLogger;

import com.mobilemedia.AppAlcaldiaSucre.push.NetworkCoverageManager;
import com.mobilemedia.AppAlcaldiaSucre.push.PushController;
import com.mobilemedia.AppAlcaldiaSucre.push.PushUtils;

/**
 * An abstract command that involves network communication. The command 
 * should be executed in a dedicated thread to prevent UI blocking.
 */
public abstract class NetworkCommand implements Runnable {

    protected Transaction tx;

    protected NetworkCommand( ) { super(); }

    public synchronized void cancel() {
        tx.cancelAndNotify();
    }

    public final void run() {

        this.tx = new Transaction();
        Exception ex = null;

        try {
        	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("NetworkCommand: run: "+"Executing command '" + getCommandName() + "' ...").getBytes() );
            // first we wait until there's network coverage
            NetworkCoverageManager.getInstance().waitForNetworkCoverage( tx );
            // execute the network request(s)
            execute();
        } catch( Exception e ) {
            ex = e;
        }

        // log the result
        final boolean canceled;
        synchronized( tx ) {
            canceled = tx.isCancelled();
            if( canceled ) {
            	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("NetworkCommand: run: "+"Command '" + getCommandName() + "' canceled").getBytes() );
                ex = null;
            } else {
                if( ex == null ) {
                	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("NetworkCommand: run: "+"Command '" + getCommandName() + "' executed successfully").getBytes() );
                } else {
                	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("NetworkCommand: run: "+"Command '" + getCommandName() + "' failed with error: " + ex).getBytes() );
                }
            }
        }
        tx = null;

        // notify controller to update UI if required
        final Exception error = ex;
        PushUtils.runOnEventThread( new Runnable() {
            public void run() {
                PushController.commandExecuted( NetworkCommand.this, canceled, error );
            }
        } );
    }

    protected abstract void execute() throws Exception;

    /**
     * Returns command name, e.g. 'register'.
     * @return command name
     */
    public abstract String getCommandName();

    /**
     * Encapsulates current state of the command - running or canceled.
     */
    public static class Transaction {

        private static final int STATE_RUNNING = 1;
        private static final int STATE_CANCELED = 2;

        private int state;

        private Connection conn;
        private InputStream is;

        private Transaction() {
            this.state = STATE_RUNNING;
        }

        public synchronized void cancelAndNotify() {
            if( state == STATE_RUNNING ) {
                state = STATE_CANCELED;
                PushUtils.close( conn, is, null );
                clearNetworkOperation();
                notifyAll();
            }
        }

        public synchronized void resumeAndNotify() {
            notifyAll();
        }

        public synchronized void waitForNotification() {
            try {
                wait();
            } catch( InterruptedException e ) {
            }
        }

        public synchronized void setNetworkOperation( Connection conn, InputStream is ) {
            if( isCancelled() ) {
                PushUtils.close( conn, is, null );
            } else {
                this.conn = conn;
                this.is = is;
            }
        }

        public synchronized void clearNetworkOperation() {
            this.conn = null;
            this.is = null;
        }

        public synchronized boolean isCancelled() {
            return state == STATE_CANCELED;
        }
    }

}
