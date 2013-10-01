/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push;

import net.rim.device.api.util.Persistable;

/**
 * Encapsulates push configuration for the application
 */
public class PushConfig implements Persistable {

    // Default properties used for the demo.
    // You should register with BlackBerry Push API server and obtain
    // similar values for your application.
    
    public static final String Medio = "Chevrolet";
	public static final String Source = "AppWorld";
	// TCP port to listen for push messages
    private int port = 31845;
    // Application ID generated during Push API registration
    private String appId = "688-8534ccc58r00i5O554ae49ot096h8845a502";
    // URL to the Push BPS server
    private String bpsUrl = "http://cp688.pushapi.na.blackberry.com";
    // URL to Content Provider using Frameworks library
    private String contentProviderUrl = "http://www.mobilemediapush.com";
    // Property specifying whether push communication is going through enterprise server (BES)
    private boolean isEnterprise;

    private static PushConfig instance;
    
    static {
        instance = PersistentStorage.getConfig();
        if( instance == null ) {
            instance = new PushConfig();
            PersistentStorage.setConfig( instance );
        }
    }

    /**
     * Defines whether application supports application level acknowledgment
     */
    private boolean applicationAcknoledgment = true;

    public static int getPort() {
        return instance.port;
    }

    public static String getAppId() {
        return instance.appId;
    }

    public static String getBpsUrl() {
        return instance.bpsUrl;
    }

    public static boolean isApplicationAcknoledgementSupported() {
        return instance.applicationAcknoledgment;
    }

    public static String getContentProviderUrl() {
        return instance.contentProviderUrl;
    }

    public static void setPort( int port ) {
        instance.port = port;
    }

    public static void setAppId( String appId ) {
        instance.appId = appId;
    }

    public static void setBpsUrl( String bpsUrl ) {
        instance.bpsUrl = bpsUrl;
    }

    public static void setContentProviderUrl( String contentProviderUrl ) {
        instance.contentProviderUrl = contentProviderUrl;
    }

    public static void setApplicationAcknoledgment( boolean applicationAcknoledgment ) {
        instance.applicationAcknoledgment = applicationAcknoledgment;
    }

    public static boolean isEnterprise() {
        return instance.isEnterprise;
    }

    public static void setEnterprise( boolean isEnterprise ) {
        instance.isEnterprise = isEnterprise;
    }
    
    public static void saveSettings() {
        PersistentStorage.setConfig( instance );
    }
    
}
