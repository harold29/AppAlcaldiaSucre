package com.mobilemedia.AppAlcaldiaSucre.facade;


import java.io.UnsupportedEncodingException;
import com.mobilemedia.AppAlcaldiaSucre.transport.*;

public class StringFacade implements BBRequest.Listener 
{
	public interface Listener {
		public void onGetStringComplete(String s);
		public void onGetStringFailed();
	}
	
	private Listener _listener;
	
	public StringFacade() { 
	}
	
	public void setListener(Listener listener) {
		_listener = listener;
	}
	
	public Listener getListener() {
		return _listener;
	}
	
	public void getString(String stringURL){
		
		if(stringURL == null || stringURL.length() < 1) return;
		
		HttpRequest req = new HttpRequest();
		req.setRequestURL(stringURL);
		
		req.setListener(this);
		BBRequestQueue.getInstance().addRequest(req);
	}
	
	public void requestStarted(BBRequest request){ }

	public void requestSucceeded(BBRequest request){
		System.out.println("requestSucceded");
		HttpRequest httpRequest = (HttpRequest) request;
		byte[] stringBytes = httpRequest.getResponseByte();
		
		String s;
		try {
			s = new String(stringBytes, "UTF-8");
			
			if(_listener != null)
		          _listener.onGetStringComplete(s);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void requestCancelled(BBRequest request){ }

	public void requestFailed(BBRequest request){  	
		System.out.println("requestFailed");
      
		if(_listener != null)
			_listener.onGetStringFailed();
	}
}
