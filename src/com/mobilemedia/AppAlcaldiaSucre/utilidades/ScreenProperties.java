package com.mobilemedia.AppAlcaldiaSucre.utilidades;

import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Font;

public final class ScreenProperties {

	
	public static boolean isHighRes;
	public static boolean isWide;
	
	public static Font bigFieldFont;
	public static Font bigFieldFontBold;
	
	public static Font normalFieldFont;
	public static Font smallFieldFont;
	public static Font tinyFieldFont;
	
	public static Font smallFieldFontItalic;
	
	public static Font normalFieldFontUnderlined;
	public static Font smallFieldFontUnderlined;
	public static Font tinyFieldFontUnderlined;
	
	public static Font normalFieldFontBold;
	public static Font smallFieldFontBold;
	public static Font tinyFieldFontBold;
	
	public static int fullFieldWidth;
	public static int scoreFieldWidth;
	public static int bsoFieldWidth;
	
	public static int scoreFieldHeight;
	public static int shortScoreFieldHeight;
	public static int feedFieldHeight;
	public static int clasificacionFieldHeight;
	public static int paddingAboutScreen;
	
	static{
		
		if (Display.getWidth() > 400 ){
			isWide = true;
			isHighRes = true;
			fullFieldWidth = 380;
			scoreFieldWidth = 260;
			bsoFieldWidth = 120;
			scoreFieldHeight = 70;
			shortScoreFieldHeight = 60;
			feedFieldHeight = 50;
			clasificacionFieldHeight = 30;
			tinyFieldFont   = Font.getDefault().derive(Font.PLAIN, 14);
			smallFieldFont  = Font.getDefault().derive(Font.PLAIN, 16);
			smallFieldFontItalic  = Font.getDefault().derive(Font.ITALIC, 16);
			normalFieldFont = Font.getDefault().derive(Font.PLAIN, 18);
			
			bigFieldFont = Font.getDefault().derive(Font.PLAIN, 19);
			bigFieldFontBold = Font.getDefault().derive(Font.BOLD, 19);
			
			tinyFieldFontBold   = Font.getDefault().derive(Font.BOLD, 14);
			smallFieldFontBold  = Font.getDefault().derive(Font.BOLD, 16);
			normalFieldFontBold = Font.getDefault().derive(Font.BOLD, 18);
			tinyFieldFontUnderlined   = Font.getDefault().derive(Font.UNDERLINED, 14);
			smallFieldFontUnderlined  = Font.getDefault().derive(Font.UNDERLINED, 16);
			normalFieldFontUnderlined = Font.getDefault().derive(Font.UNDERLINED, 18);
		} else if (Display.getWidth() > 320 ){
			isWide = false;
			isHighRes = true;
			fullFieldWidth = 340;
			scoreFieldWidth = 250;
			bsoFieldWidth = 90;
			scoreFieldHeight = 70;
			shortScoreFieldHeight = 60;
			feedFieldHeight = 50;
			clasificacionFieldHeight = 30;
			tinyFieldFont   = Font.getDefault().derive(Font.PLAIN, 13);
			smallFieldFont  = Font.getDefault().derive(Font.PLAIN, 15);
			smallFieldFontItalic = Font.getDefault().derive(Font.ITALIC, 15);
			normalFieldFont = Font.getDefault().derive(Font.PLAIN, 17);
			
			bigFieldFont = Font.getDefault().derive(Font.PLAIN, 18);
			bigFieldFontBold = Font.getDefault().derive(Font.BOLD, 18);
			
			tinyFieldFontBold   = Font.getDefault().derive(Font.BOLD, 13);
			smallFieldFontBold  = Font.getDefault().derive(Font.BOLD, 15);
			normalFieldFontBold = Font.getDefault().derive(Font.BOLD, 17);
			tinyFieldFontUnderlined   = Font.getDefault().derive(Font.UNDERLINED, 13);
			smallFieldFontUnderlined  = Font.getDefault().derive(Font.UNDERLINED, 15);
			normalFieldFontUnderlined = Font.getDefault().derive(Font.UNDERLINED, 17);
		} else {
			isWide = false;
			isHighRes = false;
			fullFieldWidth = 240;
			scoreFieldWidth = 180;
			bsoFieldWidth = 60;
			scoreFieldHeight = 60;
			shortScoreFieldHeight = 70;
			feedFieldHeight = 40;
			clasificacionFieldHeight = 20;
			tinyFieldFont   = Font.getDefault().derive(Font.PLAIN, 11);
			smallFieldFont  = Font.getDefault().derive(Font.PLAIN, 12);
			smallFieldFontItalic = Font.getDefault().derive(Font.ITALIC, 12);
			
			normalFieldFont = Font.getDefault().derive(Font.PLAIN, 14);
			
			bigFieldFont = Font.getDefault().derive(Font.PLAIN, 15);
			bigFieldFontBold = Font.getDefault().derive(Font.BOLD, 15);
			
			tinyFieldFontBold   = Font.getDefault().derive(Font.BOLD, 11);
			smallFieldFontBold  = Font.getDefault().derive(Font.BOLD, 12);
			normalFieldFontBold = Font.getDefault().derive(Font.BOLD, 13);
			tinyFieldFontUnderlined   = Font.getDefault().derive(Font.UNDERLINED, 11);
			smallFieldFontUnderlined  = Font.getDefault().derive(Font.UNDERLINED, 12);
			normalFieldFontUnderlined = Font.getDefault().derive(Font.UNDERLINED, 13);
		}
		
		if(Display.getWidth() > 400){
			paddingAboutScreen = 0;
			if(DeviceInfo.getDeviceName().equals("9800")){
				paddingAboutScreen = 50;
			}
		}else if(Display.getWidth() > 320){
			paddingAboutScreen = 0;
		}else {
			paddingAboutScreen = 0;
		}
	}
}

