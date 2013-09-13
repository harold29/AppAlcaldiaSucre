package com.mobilemedia.AppAlcaldiaSucre.custom;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;

public class HomeCustom {

	public static final String NOTICIAS_ICONO = "news_1.png";
	public static final String LOCALIZADOR_ICONO = "map_pin_1.png";
	public static final String CATALOGO_ICONO 	 = "bag.png";
	
	public static final Bitmap SIN_MENSAJE_ICONO = Bitmap.getBitmapResource("read.png");
	public static final Bitmap CON_MENSAJE_ICONO = Bitmap.getBitmapResource("new.png");
	public static Bitmap LOGO;
	
	static{
		//if (Display.getWidth() > 480 && Display.getHeight() > 320)
			LOGO = Bitmap.getBitmapResource("slogo.png");
		//else
		//	LOGO = Bitmap.getBitmapResource("logoJustificado_1.png");
	}
}
