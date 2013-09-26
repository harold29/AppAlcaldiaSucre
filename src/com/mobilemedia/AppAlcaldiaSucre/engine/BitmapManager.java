package com.mobilemedia.AppAlcaldiaSucre.engine;

import com.mobilemedia.AppAlcaldiaSucre.custom.HomeCustom;
import com.mobilemedia.AppAlcaldiaSucre.utilidades.ScreenProperties;

import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;


public final class BitmapManager {
		
	private static BitmapManager instance = null;
	
	private Bitmap logoAlcaldiaSucre = null;
	private Bitmap logoAlcaldiaSucreLarge = null;
	private Bitmap backg_image = null;
	private Bitmap news_img = null;
	private Bitmap logoAlcaldiaMedium = null;
	
	private int caso = -1;
	
	
	public static BitmapManager getInstance() {
		if (instance == null) {
			instance = new BitmapManager();
		}
		return instance;
	}
	
	private BitmapManager() {
		if (ScreenProperties.isHighRes & ScreenProperties.isWide) {
			caso = 1;
		} else if (ScreenProperties.isHighRes & !ScreenProperties.isWide) {
			caso = 2;
		} else {
			caso = 3;
		}
	}
	
	public void cagarImagenesMemoria() {
		logoAlcaldiaSucre = Bitmap.getBitmapResource(HomeCustom.LOGO);
		logoAlcaldiaSucreLarge = Bitmap.getBitmapResource(HomeCustom.LOGO_LARGE);
		backg_image = Bitmap.getBitmapResource(HomeCustom.BACKGROUND_GENERAL_IMG);
		logoAlcaldiaMedium = scaleWidthBitmap(HomeCustom.LOGO_MEDIUM, 130);
	}
	
	public final Bitmap scaleWidthBitmap(String ruta, int tam) {
		EncodedImage ei = EncodedImage.getEncodedImageResource(ruta);
		if (ei.getBitmap().getWidth() == tam) {
			return Bitmap.getBitmapResource(ruta);
		}
		ei = scaleImageToWidth(ei,tam);
		return ei.getBitmap();
	}
	
	public static EncodedImage scaleImageToWidth(EncodedImage encoded, int newWidth) {
		return scaleToFactor(encoded, encoded.getWidth(),newWidth);
	}
	
	public static EncodedImage scaleImageToHeight(EncodedImage encoded, int newHeight) {
		return scaleToFactor(encoded, encoded.getHeight(), newHeight);
	}
	
	public static EncodedImage scaleToFactor(EncodedImage encoded, int curSize, int newSize) {
		int numerator = Fixed32.toFP(curSize);
		int denominator = Fixed32.toFP(newSize);
		int scale = Fixed32.div(numerator, denominator);

		return encoded.scaleImage32(scale, scale);
	}
	
	public Bitmap getLogoAlcaldiaSucre() {
		return logoAlcaldiaSucre;
	}
	
	public Bitmap getLogoAlcaldiaSucreLarge() {
		return logoAlcaldiaSucreLarge;
	}
	
	public Bitmap getLogoAlcaldiaSucreMedium() {
		return logoAlcaldiaMedium;
	}
	
	public Bitmap getBackgroundImage() {
		return backg_image;
	}
	
	public Bitmap getNewsImage() {
		return news_img;
	}
	
	public void setLogoAlcaldiaSucre(Bitmap logo) {
		this.logoAlcaldiaSucre = logo;
	}
	
	public void setLogoAlcaldiaSucreLarge(Bitmap logo) {
		this.logoAlcaldiaSucreLarge = logo;
	}
	
	public void setBackgroundImage(Bitmap back_img) {
		this.backg_image = back_img;
	}
	
	public void setNewsImage(Bitmap news_img) {
		this.news_img =  news_img;
	}
		
	
}