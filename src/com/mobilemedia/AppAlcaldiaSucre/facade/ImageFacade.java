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


package com.mobilemedia.AppAlcaldiaSucre.facade;

import net.rim.device.api.system.EncodedImage;
//import net.rim.device.api.system.EventLogger;

//import com.mobmedianet.golfbb.GolfBBMain;
import com.mobilemedia.AppAlcaldiaSucre.transport.BBRequest;
import com.mobilemedia.AppAlcaldiaSucre.transport.BBRequestQueue;
import com.mobilemedia.AppAlcaldiaSucre.transport.HttpRequest;

/**
 *
 */
public class ImageFacade implements BBRequest.Listener{

    private Listener _listener;
    private final static String WIDTH  = "param_width";
    private final static String HEIGHT = "param_height";
    private final static String INDEX  = "param_index";


    public ImageFacade() {
    }

    public void setListener(Listener listener){
        _listener = listener;
    }

    public Listener getListener(){
        return _listener;
    }

    public interface Listener
    {
        /**
         * Method that will be called on successful completion of image download()
         */
        public void onGetImageComplete(EncodedImage bitmap,String index);

        public void onGetImageFailed(String index);
    }

    public void getImage(String imageURL){
        getImage(imageURL,"-1",0,0);
    }

    public void getImage(String imageURL, String index){
        getImage(imageURL,index,0,0);
    }

    public void getImage(String imageURL, String index, int width, int height){

        if(imageURL == null || imageURL.length() < 1) return;

        HttpRequest req = new HttpRequest();
        req.setRequestURL(imageURL);

        if(width > 0 && height >0){
            req.addAttachment(WIDTH,new Integer(width));
            req.addAttachment(HEIGHT,new Integer(height));
        }
        if(index != ""){
            req.addAttachment(INDEX,index);
        }

        req.setListener(this);
        BBRequestQueue.getInstance().addRequest(req);
    }

    public void requestStarted(BBRequest request){
//    	EventLogger.logEvent(GolfBBMain.EventLoggerID, ("descargando: "+((HttpRequest)request).getRequestURL()).getBytes());
    }

    public void requestSucceeded(BBRequest request){

    	System.out.println("requestSucceded");
        HttpRequest httpRequest = (HttpRequest) request;
        byte[] imageBytes = httpRequest.getResponseByte();

        String index = "";
        int width = 0;
        int height = 0;
        Object obj;

        obj = httpRequest.getAttachment(INDEX);
        if (obj != null && obj instanceof String) index = ((String) obj);

        obj = httpRequest.getAttachment(WIDTH);
        if (obj != null && obj instanceof Integer) width = ((Integer) obj).intValue();

        obj = httpRequest.getAttachment(HEIGHT);
        if (obj != null && obj instanceof Integer) height = ((Integer) obj).intValue();

        EncodedImage bitmap = EncodedImage.createEncodedImage(imageBytes, 0, -1);
        
        
        if(_listener != null)
            _listener.onGetImageComplete(bitmap,index);
    }

    public void requestCancelled(BBRequest request){
    }

    public void requestFailed(BBRequest request)
    {
    	HttpRequest httpRequest = (HttpRequest) request;
        byte[] imageBytes = httpRequest.getResponseByte();
        
    	String index = "";
        int width = 0;
        int height = 0;
        Object obj;

        obj = httpRequest.getAttachment(INDEX);
        if (obj != null && obj instanceof String) index = ((String) obj);
    	
    	if(_listener != null)
            _listener.onGetImageFailed(index);
    }

	public void downloadProgress(int bytes) {
	}

	public void setContentLenght(String value) {
	}
}
