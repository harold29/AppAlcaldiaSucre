/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push;

import java.io.ByteArrayInputStream;

import javax.microedition.io.Connection;

import com.mobilemedia.AppAlcaldiaSucre.AppAlcaldiaSucre;

import net.rim.device.api.io.Base64InputStream;
import net.rim.device.api.io.http.HttpServerConnection;
import net.rim.device.api.io.http.PushInputStream;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.util.Arrays;

/**
 * Reads incoming push messages and extracts texts and images. Uses main controller to display the data.
 */
public class PushMessageReader {

    // HTTP header property that carries unique push message ID
    private static final String MESSAGE_ID_HEADER = "Push-Message-ID";
    // content type constant for text messages
    private static final String MESSAGE_TYPE_TEXT = "text";
    // content type constant for image messages
    private static final String MESSAGE_TYPE_IMAGE = "image";

    private static final int MESSAGE_ID_HISTORY_LENGTH = 10;
    private static String[] messageIdHistory = new String[ MESSAGE_ID_HISTORY_LENGTH ];
    private static byte historyIndex;

    private static byte[] buffer = new byte[ 15 * 1024 ];
    private static byte[] imageBuffer = new byte[ 10 * 1024 ];

    /**
     * Reads the incoming push message from the given streams in the current thread and notifies controller to display the
     * information.
     */
    public static void process( PushInputStream pis, Connection conn ) {
    	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessageReader: process: Reading incoming push message ...").getBytes() );
        try {

            HttpServerConnection httpConn;
            if( conn instanceof HttpServerConnection ) {
                httpConn = (HttpServerConnection) conn;
            } else {
                throw new IllegalArgumentException( "Can not process non-http pushes, expected HttpServerConnection but have "
                        + conn.getClass().getName() );
            }

            String msgId = httpConn.getHeaderField( MESSAGE_ID_HEADER );
            String msgType = httpConn.getType();
            String encoding = httpConn.getEncoding();

            EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessageReader: process: Message props: ID=" + msgId + ", Type=" + msgType + ", Encoding=" + encoding).getBytes() );
                        
            if( !alreadyReceived( msgId ) ) {
                byte[] binaryData;
                
                if(msgId == null) {
                    msgId = String.valueOf( System.currentTimeMillis() );
                }
                
                if( msgType == null ) {
                	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessageReader: process: msgType==null: Message content type is NULL").getBytes() );
                } else if( msgType.indexOf( MESSAGE_TYPE_TEXT ) >= 0 ) {
                    // a string
                    int size = pis.read( buffer );
                    binaryData = new byte[size];
                    System.arraycopy( buffer, 0, binaryData, 0, size );
                    PushMessage message = new PushMessage(msgId, System.currentTimeMillis(), binaryData, true, true );
                    PushController.onMessage( message );
                } else if( msgType.indexOf( MESSAGE_TYPE_IMAGE ) >= 0 ) {
                    // an image in binary or Base64 encoding
                    int size = pis.read( buffer );
                    if( encoding != null && encoding.equalsIgnoreCase( "base64" ) ) {
                        // image is in Base64 encoding, decode it
                        Base64InputStream bis = new Base64InputStream( new ByteArrayInputStream( buffer, 0, size ) );
                        size = bis.read( imageBuffer );
                    }
                    
                    binaryData = new byte[ size ];
                    System.arraycopy( buffer, 0, binaryData, 0, size );
                    PushMessage message = new PushMessage(msgId, System.currentTimeMillis(), binaryData, false, true );
                    PushController.onMessage( message );
                } else {
                    EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessageReader: process: "+ "Unknown message type " + msgType).getBytes() );
                }
            } else {
                EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessageReader: process: "+"Received duplicate message with ID " + msgId).getBytes() );
            }

            // perform application acknowledgment            
            if( PushConfig.isApplicationAcknoledgementSupported() ) {                
                pis.accept();                
            }
        } catch( Exception e ) {
            EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessageReader: process: "+"Failed to process push message: " + e).getBytes() );
        } finally {
            PushUtils.close( conn, pis, null );
        }
        
//        if ( !AppGenericaBB.getInstance().seMostroScreen() ) 
//        	System.exit(0);
    }

    /**
     * Check whether the message with this ID has been already received.
     */
    private static boolean alreadyReceived( String id ) {
        if( id == null ) {
            return false;
        }
        
        if( Arrays.contains( messageIdHistory, id ) ) {
            return true;
        }
        
        // new ID, append to the history (oldest element will be eliminated)
        messageIdHistory[ historyIndex++ ] = id;
        if( historyIndex >= MESSAGE_ID_HISTORY_LENGTH ) {
            historyIndex = 0;
        }
        
        return false;
    }

}
