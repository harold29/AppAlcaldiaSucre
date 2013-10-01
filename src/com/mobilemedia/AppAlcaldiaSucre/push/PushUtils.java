/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push;

import java.io.*;

import javax.microedition.io.*;

import com.mobilemedia.AppAlcaldiaSucre.push.comandos.NetworkCommand.Transaction;

import net.rim.device.api.io.http.*;
import net.rim.device.api.system.*;
import net.rim.device.api.util.*;

/**
 * Small utilities for common usage in the push sample application
 */
public class PushUtils {

    /**
     * Declines push message with given reason
     */
    public static void decline( PushInputStream pis, int reason ) {
        try {
            pis.decline( reason );
        } catch( IOException e ) {
        }
    }

    /**
     * Safely closes connection and streams
     */
    public static void close( Connection conn, InputStream is, OutputStream os ) {
        if( os != null ) {
            try {
                os.close();
            } catch( IOException e ) {
            }
        }
        if( is != null ) {
            try {
                is.close();
            } catch( IOException e ) {
            }
        }
        if( conn != null ) {
            try {
                conn.close();
            } catch( IOException e ) {
            }
        }
    }

    public static void runOnEventThread( Runnable r ) {
        if( Application.isEventDispatchThread() ) {
            r.run();
        } else {
            Application.getApplication().invokeLater( r );
        }
    }

    /**
     * Requests an HTTP resource and returns its response as a string
     */
    public static String request( String httpUrl, Transaction tx ) throws IOException {
        checkTransaction( tx );
        
        DataBuffer buffer = new DataBuffer( 256, false );
        InputStream is = null;
        Connection conn = null;
        try {
            // append connection suffix
            httpUrl += getConnectionSuffix();
            EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushUtils: request: "+"Opening URL: " + httpUrl).getBytes() );
            conn = Connector.open( httpUrl );
            tx.setNetworkOperation( conn, is );
            if( conn instanceof HttpConnection ) {
                HttpConnection httpConn = (HttpConnection) conn;
                int responseCode = httpConn.getResponseCode();
                is = httpConn.openInputStream();
                tx.setNetworkOperation( conn, is );
                int length = is.read( buffer.getArray() );
                buffer.setLength( length );

                String response = new String( buffer.getArray(), buffer.getArrayStart(), buffer.getArrayLength() );
                if( responseCode == 200 ) {
                	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushUtils: request: "+"HTTP response: " + response).getBytes() );
                    return response;
                } else {
                	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushUtils: request: "+"HTTP error response: " + response).getBytes() );
                    throw new IOException( "Http error: " + responseCode + ", " + response );
                }
            } else {
                throw new IOException( "Can not make HTTP connection for URL '" + httpUrl + "'" );
            }
        } finally {
            PushUtils.close( conn, is, null );
            tx.clearNetworkOperation();
        }
    }

    /**
     * Forms listen URL based on port number
     */
    public static String formPushListenUrl() {
        String url = "http://:" + PushConfig.getPort();
        url += getConnectionSuffix();
        return url;
    }

    private static String getConnectionSuffix() {
        return ";deviceside=false;ConnectionType=mds-public";
    }

    private static void checkTransaction( Transaction tx ) throws IOException {
        if( tx.isCancelled() ) {
            throw new IOException( "Transaction was canceled" );
        }
    }

}
