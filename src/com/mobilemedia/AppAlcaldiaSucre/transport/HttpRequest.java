//#preprocessor
/**
 * Copyright © 1998-2009 Research In Motion Ltd.
 *
 * Note:
 *
 * 1. For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 *
 * 2. The sample serves as a demonstration of principles and is not intended for a
 * full featured application. It makes no guarantees for completeness and is left to
 * the user to use it as sample ONLY.
 */
package com.mobilemedia.AppAlcaldiaSucre.transport;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpsConnection;

//#ifdef VER_4.2.2 | VER_4.2.1 | VER_4.2.0
//import net.rim.device.api.system.WLANListener;
//#else
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.system.WLANInfo;
//#endif
import net.rim.device.api.ui.component.Dialog;

public class HttpRequest extends BBRequest
//#ifdef VER_4.2.2 | VER_4.2.1 | VER_4.2.0
//implements WLANListener
//#endif
{
    public static String USER_AGENT_PROP_NAME = "User-Agent";
    public static String REFERER_PROP_NAME = "Referer";
    private static long DEFAULT_TIMEOUT = 3 * 60 * 1000; // 5 minutes for timeout

    private boolean useWifi = true; // always use Wifi when it is enabled and connected
    //#ifdef VER_4.2.2 | VER_4.2.1 | VER_4.2.0
    private boolean wifiConnected = false;
    //#endif
    //#ifdef VER_4.2.0
   private boolean radioOn = false;
    //#endif

    protected int responseCode = 0;
    protected String requestURL = null;

    protected byte[] payload = null;
    protected Hashtable properties = null;

    protected String responseType = null;
    protected String responseEncoding = null;
    private byte[] responseByte = null;

    protected Hashtable responseHeaders = null;

    private HttpConnection httpConnection = null;

    private OutputStream postStream = null;
    private static final String DEFAULT_ENCODING = "UTF-8";
    public HttpRequest()
    {
        properties = new Hashtable();
        this.setRequestTimeout(DEFAULT_TIMEOUT);
        this.addProperty(USER_AGENT_PROP_NAME, "BlackBerry"+DeviceInfo.getDeviceName()+"/"+DeviceInfo.getSoftwareVersion()+ " BeisbolTotal");
        this.addProperty(REFERER_PROP_NAME, "BlackBerry"+DeviceInfo.getDeviceName()+"/"+DeviceInfo.getSoftwareVersion()+ " BeisbolTotal");
        
        //this.addProperty(USER_AGENT_PROP_NAME, "Blackberry HTTP Request");   
    
    }
    
    public HttpRequest(String referer)
    {
        properties = new Hashtable();
        this.setRequestTimeout(DEFAULT_TIMEOUT);
        this.addProperty(USER_AGENT_PROP_NAME, "BlackBerry"+DeviceInfo.getDeviceName()+"/"+DeviceInfo.getSoftwareVersion()+ " BeisbolTotal");
        this.addProperty(REFERER_PROP_NAME, "BeisbolTotal " + referer);
        
        //this.addProperty(USER_AGENT_PROP_NAME, "Blackberry HTTP Request");   
    
    }
    
    public void addRealUserAgent(){
//    	Dialog.alert("BlackBerry"+DeviceInfo.getDeviceName()+"/"+DeviceInfo.getSoftwareVersion()+ " Profile/MIDP-2.0 Configuration/CLDC-1.1");
//    	EventLogger.logEvent(BeisbolBBMain.EventLoggerID, ("UserAgent: "+"BlackBerry"+DeviceInfo.getDeviceName()+"/"+DeviceInfo.getSoftwareVersion()+ " Profile/MIDP-2.0 Configuration/CLDC-1.1").getBytes());
    	this.addProperty(USER_AGENT_PROP_NAME, "BlackBerry"+DeviceInfo.getDeviceName()+"/"+DeviceInfo.getSoftwareVersion()+ " Profile/MIDP-2.0 Configuration/CLDC-1.1");
        
    }

    protected void process() throws Throwable
    {
        try
        {
            if (requestURL == null)
                throw new HttpRequestException("Url is null!");
            httpConnection = createHttpConnection();
            Enumeration en = properties.keys();
            
            while (en.hasMoreElements())
            {
                String key = (String) en.nextElement();
                String value = (String) properties.get(key);
                setRequestProperty(httpConnection, key, value);
            }
           
         // property specified to disable transcoding
            httpConnection.setRequestProperty("x-rim-transcode-content", "none");
            
            if (httpConnection == null)
                    throw new HttpRequestException("Unable to create connection!");

            if (hasPayload())
            {
                httpConnection.setRequestMethod(HttpConnection.POST);
                postStream = httpConnection.openOutputStream();
                // postStream.write(payload.getPayload().getBytes(payload.getEncoding()));
                postStream.write(payload);
                postStream.flush();
            }
            else
            {
                httpConnection.setRequestMethod(HttpConnection.GET);
            }

            this.responseCode = httpConnection.getResponseCode();
            this.setResponseCode(responseCode);
            // if (logger.isDebugEnabled())
            // logger.logDebugEvent("HTTP Response: " + responseCode);
            System.out.println("HTTP Response: " + responseCode);
            if (!isValidResponseCode(responseCode))
                    throw new HttpRequestException(
                                    "Request failed, response code: " + responseCode);

            responseType = httpConnection.getType();
            responseEncoding = httpConnection.getEncoding();
            if(responseEncoding == null) responseEncoding = DEFAULT_ENCODING;
            responseHeaders = new Hashtable();
            for (int i = 0;; i++)
            {
                String key = httpConnection.getHeaderFieldKey(i);
                if (key == null) break;
                responseHeaders.put(key, httpConnection.getHeaderField(i));
            }
            readResult(httpConnection);
        }
        finally
        {
            cleanup();
        }
    }

    private void cleanup()
    {
        if (postStream != null)
        {
            try
            {
                postStream.close();
            }
            catch (Throwable t)
            {
                    // logger.logError("postStream can not be closed. Handling Throwable");
                System.out
                        .println("postStream can not be closed. Handling Throwable");
            }
        }

        if (httpConnection != null)
        {
            try
            {
                httpConnection.close();
            }
            catch (Throwable t)
            {
                // logger.logError("httpConnection can not be closed. Handling Throwable");
            }
        }
    }

    private void setRequestProperty(HttpConnection connection, String key,
                    String value) throws IOException
    {
        if (connection != null && key != null && value != null)
        {
            // if (logger.isDebugEnabled())
            // logger.logDebugEvent("HTTP Request header: " + key + ": "
            // + value);
            connection.setRequestProperty(key, value);
        }
    }

    protected void cancel()
    {
        cleanup();
    }


    public boolean isWiFiConnected()
    {
        //#ifdef VER_4.2.2 | VER_4.2.1 | VER_4.2.0
        //return wifiConnected;
        //#else
        return (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED);
        //#endif
    }

    public boolean isWiFiEnabled()
    {
        //#ifdef VER_4.2.0
        // WLAN.isRadioON method does not exist in VER_4.2.0
      //  return isWiFiConnected();
        //#else
        return isWiFiConnected();
        //#endif
    }

    public void addWiFiConnectionParam(){
        if(isWiFiEnabled() && useWifi){
            requestURL+=";interface=wifi";
        } else {
        	if (DeviceInfo.isSimulator()){
            	// simulador
            	requestURL+=";deviceside=false";        		
        	} else {
            	// real
            	requestURL+=";deviceside=false;ConnectionType=mds-public";
        	}
        }
    }

    protected HttpConnection createHttpConnection() throws Exception
    {
        addWiFiConnectionParam();
        return (HttpConnection)Connector.open(this.requestURL);
        //return null;
    }

    protected boolean isValidResponseCode(int code)
    {
        return code == HttpConnection.HTTP_OK;
    }

    public boolean isHttpOk()
    {
        return responseCode == HttpConnection.HTTP_OK;
    }

    protected final void printResponseHeaders()
    {
        Enumeration en = responseHeaders.keys();
        StringBuffer buf = new StringBuffer("ResponseHeader:\n");
        while (en.hasMoreElements())
        {
            String key = (String) en.nextElement();
            String value = (String) responseHeaders.get(key);
            buf.append(key + ": " + value + "\n");
        }
        System.out.println(buf.toString());
    }

    protected final InputStream debugResponse(InputStream inputStream)
                    throws IOException
    {
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();

            byte[] readBuffer = new byte[1024];
            int readSize = 0;

            while ((readSize = inputStream.read(readBuffer)) != -1)
            {
                    baos.write(readBuffer, 0, readSize);
            }

            byte[] responseBytes = baos.toByteArray();

            // if (logger.isDebugEnabled())
            // logger.logDebugEvent("Response: "
            // + ByteUtils.toString(responseBytes));
            System.out.println(new String(responseBytes, responseEncoding));
            return new DataInputStream(new java.io.ByteArrayInputStream(
                            responseBytes));
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (Throwable t)
                {
                    // logger.logError("InputStream can not be closed. Handling Throwable");
                    System.out.println("InputStream can not be closed. Handling Throwable");
                }
            }

            if (baos != null)
            {
                try
                {
                    baos.close();
                }
                catch (Throwable t)
                {
                    // logger.logError("dataInputStream can not be closed. Handling Throwable");
                    System.out.println("dataInputStream can not be closed. Handling Throwable");
                }
            }
        }
    }

    protected void readResult(HttpConnection httpConnection) throws Exception
    {
        InputStream inputStream = null;
        try
        {
            inputStream = httpConnection.openDataInputStream();
            printResponseHeaders();
            inputStream = debugResponse(inputStream);

            readResult(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (Throwable t)
                {
                    System.out.println("InputStream can not be closed. Handling Throwable");
                }
            }
        }
    }

    protected void readResult(InputStream inputStream) throws Exception
    {
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();

            byte[] readBuffer = new byte[1024];
            int readSize = 0;

            while ((readSize = inputStream.read(readBuffer)) != -1)
            {
                baos.write(readBuffer, 0, readSize);
            }

            responseByte = baos.toByteArray();
        }
        finally
        {
            if (baos != null)
            {
                try
                {
                    baos.close();
                }
                catch (Throwable t)
                {
                    // logger.logError("dataInputStream can not be closed. Handling Throwable");
                    System.out.println("dataInputStream can not be closed. Handling Throwable");
                }
            }
        }
    }

    public void addProperty(String key, String value)
    {
        properties.put(key, value);
    }

    public void removeProperty(String key)
    {
        properties.remove(key);
    }

    public String getRequestURL()
    {
        return requestURL;
    }

    public void setRequestURL(String requestURL)
    {
        this.requestURL = requestURL;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    protected void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }

    public void setPayload(byte[] payload)
    {
        this.payload = payload;
    }

    public void setPayload(String payload, String encoding)
                    throws UnsupportedEncodingException
    {
        this.payload = payload.getBytes(encoding);
    }

    public byte[] getPayload()
    {
        return payload;
    }

    public String getPayload(String encoding)
                    throws UnsupportedEncodingException
    {
        return new String(payload, encoding);
    }

    public boolean hasPayload()
    {
        return (payload != null && payload.length > 0);
    }

    public byte[] getResponseByte()
    {
        return responseByte;
    }

    public void setResponseByte(byte[] responseByte)
    {
        this.responseByte = responseByte;
    }

    public String getResponseEncoding()
    {
        return responseEncoding;
    }

    public void setResponseEncoding(String responseEncoding)
    {
        this.responseEncoding = responseEncoding;
    }

    public String getResponseType()
    {
        return responseType;
    }

    public void setResponseType(String responseType)
    {
        this.responseType = responseType;
    }

    public Hashtable getResponseHeaders()
    {
        return responseHeaders;
    }

    public String getResponseHeader(String key)
    {
        String value = null;
        if (responseHeaders != null)
        {
            value = (String) responseHeaders.get(key);
        }
        return value;
    }

    public void setUseWifi(boolean useWifi){
        this.useWifi = useWifi;
    }

    public boolean getUseWifi(){
        return useWifi;
    }

    //#ifdef VER_4.2.2 | VER_4.2.1 | VER_4.2.0
    public void radioStatus(boolean started)
    {
        // do nothing
    }

    public void networkSuccess()
    {
        wifiConnected = true;
    }

    public void networkFound(int handle)
    {
        // do nothing
    }

    public void networkApChange()
    {
        // do nothing
    }

    //#endif

    // WLANistener has different number of arguments in different 4.2.x OS
    // Versions

    //#ifdef VER_4.2.2
    public void networkFail(int status, int error, int extendedInfo)
    {
        wifiConnected = false;
    }
    //#endif
    //#ifdef VER_4.2.1
    public void networkFail(int status, int error)
    {
        wifiConnected = false;
    }
    //#endif

    //#ifdef VER_4.2.0
    public void networkFail(int status)
    {
        wifiConnected = false;
    }

    public void interfaceSuccess(int iface)
    {
        radioOn = true;
        // do nothing
    }

    public void interfaceFail(int iface, int status)
    {
        radioOn = false;
        // do nothing
    }

    public void interfaceIpChange(int iface, int ipv4Address)
    {
        // do nothing
    }

    //#endif
}
