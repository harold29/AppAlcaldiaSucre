package com.mobilemedia.AppAlcaldiaSucre.dispositivo;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Ui;

public class FontManager {
	
	private static boolean isHighDPI = Display.getVerticalResolution() > 8000;
	
	private static int pointsXSmallHDPI = 4;
	private static int pointsXSmallLDPI = 5;
	
	private static int pointsSmallHDPI = 5;
	private static int pointsSmallLDPI = 6;

	private static int pointsMediumHDPI = 6;
	private static int pointsMediumLDPI = 7;
	
	private static int pointsXMediumHDPI = 7;
	private static int pointsXMediumLDPI = 8;
	
	private static int pointsLargeHDPI = 9;
	private static int pointsLargeLDPI = 10;
	
	private static int pointsXLargeHDPI = 12;
	private static int pointsXLargeLDPI = 13;

	private static Font fontBBAlpha = Font.getDefault();

//	static
//    {
//    	FontFamily fontFam;
//        try {
//        	fontFam = FontFamily.forName("BBAlpha Serif");
//        	FontFamily[] fonts = FontFamily.getFontFamilies();
//        	for (int i=0; i<fonts.length; i++){
//        		if (fonts[i].getName().equals("BBAlpha Serif")){
//        			fontFam = FontFamily.forName("BBCAlpha Serif");
//        			break;
//        		}
//        	}
//        	
//        	fontBBAlpha = fontFam.getFont(FontFamily.SCALABLE_FONT, 20).derive(Font.PLAIN);           
//             
//        } catch (ClassNotFoundException e) {
//        	fontBBAlpha = Font.getDefault();
//        	e.printStackTrace();
//        }
//    }
	
    public static Font getXSmallFont(int style) // Pequeño
	{
    	return isHighDPI ?  fontBBAlpha.derive(style, pointsXSmallHDPI,Ui.UNITS_pt) :  
                            fontBBAlpha.derive(style, pointsXSmallLDPI,Ui.UNITS_pt);
    }

    public static Font getSmallFont(int style) // Regular
	{
    	return isHighDPI ?  fontBBAlpha.derive(style, pointsSmallHDPI,Ui.UNITS_pt) :  
                            fontBBAlpha.derive(style, pointsSmallLDPI,Ui.UNITS_pt);
    }
    
    public static Font getMediumFont(int style)
	{
		return isHighDPI ?  fontBBAlpha.derive(style, pointsMediumHDPI,Ui.UNITS_pt) :  
                            fontBBAlpha.derive(style, pointsMediumLDPI,Ui.UNITS_pt);
    }
    
    public static Font getXMediumFont(int style)
	{
		return isHighDPI ?  fontBBAlpha.derive(style, pointsXMediumHDPI,Ui.UNITS_pt) :  
                            fontBBAlpha.derive(style, pointsXMediumLDPI,Ui.UNITS_pt);
    }
        
    public static Font getLargeFont(int style)
	{
    	return isHighDPI ?  fontBBAlpha.derive(style, pointsLargeHDPI,Ui.UNITS_pt) :  
                            fontBBAlpha.derive(style, pointsLargeLDPI,Ui.UNITS_pt);
    }
    
    public static Font getXLargeFont(int style)
	{
    	return isHighDPI ?  fontBBAlpha.derive(style, pointsXLargeHDPI,Ui.UNITS_pt) :  
                            fontBBAlpha.derive(style, pointsXLargeLDPI,Ui.UNITS_pt);
    }
}