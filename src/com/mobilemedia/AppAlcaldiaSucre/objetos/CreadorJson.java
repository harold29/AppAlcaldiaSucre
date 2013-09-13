package com.mobilemedia.AppAlcaldiaSucre.objetos;


import com.mobilemedia.AppAlcaldiaSucre.JsonMe.*;
import com.mobilemedia.AppAlcaldiaSucre.facade.*;


public class CreadorJson {
	private StringFacade stringFacade;
	private String url;
	private ScreenConJson screen;
	
	public interface ScreenConJson
	{
		public void setJson(JSONObject j);
		public void extraerInformacionJson();
	}
	
	public CreadorJson(String url, ScreenConJson screen) {
		this.url = url;
		this.screen = screen;
	}
	
	public void cargarJson() {
		stringFacade = new StringFacade();
		stringFacade.setListener(new StringHandler());
		stringFacade.getString(url);
	}
	
	private class StringHandler implements StringFacade.Listener {
		public void onGetStringComplete(String s) {
			System.out.println("onGetStringComplete");
			JSONObject json;
			
			try {
				json = new JSONObject(s);
				screen.setJson(json);
				screen.extraerInformacionJson();
				System.out.println("JSON CREADO");
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("*********JSON NO CREADO************");
			}
		}
		
		public void onGetStringFailed() {
			System.out.println("onGetImageFailed");
		}
	}
}