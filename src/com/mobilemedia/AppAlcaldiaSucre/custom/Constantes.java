package com.mobilemedia.AppAlcaldiaSucre.custom;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.system.Display;

import com.mobilemedia.AppAlcaldiaSucre.dispositivo.FontManager;

public class Constantes {
	
	public static final long MEDIODIA_PERSISTENCIA 	= 86400000 / 2;
	public static final long UNDIA_PERSISTENCIA 	= 86400000;
	public static final long TRESDIAS_PERSISTENCIA 	= UNDIA_PERSISTENCIA * 3;
	public static final long UNASEMANA_PERSISTENCIA = UNDIA_PERSISTENCIA * 7;
	public static final long TRESSEMANAS_PERSISTENCIA = UNASEMANA_PERSISTENCIA * 3;
	public static final long UNMES_PERSISTENCIA 	= UNDIA_PERSISTENCIA * 30;
	public static final long PERMANENTE_PERSISTENCIA = -1;
	
	// Fuentes estilo: normal
	public static Font XSMALL_NORMAL_FONT	= FontManager.getXSmallFont(Font.PLAIN);
	public static Font SMALL_NORMAL_FONT 	= FontManager.getSmallFont(Font.PLAIN);
	public static Font MEDIUM_NORMAL_FONT 	= FontManager.getMediumFont(Font.PLAIN);
	public static Font XMEDIUM_NORMAL_FONT	= FontManager.getXMediumFont(Font.PLAIN);
	public static Font LARGE_NORMAL_FONT 	= FontManager.getLargeFont(Font.PLAIN);
	public static Font XLARGE_NORMAL_FONT 	= FontManager.getXLargeFont(Font.PLAIN);
	
	// Fuentes estilo: bold
	public static Font XSMALL_BOLD_FONT		= FontManager.getXSmallFont(Font.BOLD);
	public static Font SMALL_BOLD_FONT 		= FontManager.getSmallFont(Font.BOLD);
	public static Font MEDIUM_BOLD_FONT 	= FontManager.getMediumFont(Font.BOLD);
	public static Font XMEDIUM_BOLD_FONT 	= FontManager.getXMediumFont(Font.BOLD);
	public static Font LARGE_BOLD_FONT 		= FontManager.getLargeFont(Font.BOLD);
	public static Font XLARGE_BOLD_FONT 	= FontManager.getXLargeFont(Font.BOLD);
	
	// Fuentes estilo: italic
	public static Font XSMALL_ITALIC_FONT	= FontManager.getXSmallFont(Font.ITALIC);
	public static Font SMALL_ITALIC_FONT 	= FontManager.getSmallFont(Font.ITALIC);
	public static Font MEDIUM_ITALIC_FONT 	= FontManager.getMediumFont(Font.ITALIC);
	public static Font XMEDIUM_ITALIC_FONT	= FontManager.getXMediumFont(Font.ITALIC);
	public static Font LARGE_ITALIC_FONT 	= FontManager.getLargeFont(Font.ITALIC);
	public static Font XLARGE_ITALIC_FONT 	= FontManager.getXLargeFont(Font.ITALIC);
	
	// Fuentes estilo: UNDERLINED
	public static Font XSMALL_UNDERLINED_FONT	= FontManager.getXSmallFont(Font.UNDERLINED);
	public static Font SMALL_UNDERLINED_FONT 	= FontManager.getSmallFont(Font.UNDERLINED);
	public static Font MEDIUM_UNDERLINED_FONT 	= FontManager.getMediumFont(Font.UNDERLINED);
	public static Font XMEDIUM_UNDERLINED_FONT	= FontManager.getXMediumFont(Font.UNDERLINED);
	public static Font LARGE_UNDERLINED_FONT 	= FontManager.getLargeFont(Font.UNDERLINED);
	public static Font XLARGE_UNDERLINED_FONT 	= FontManager.getXLargeFont(Font.UNDERLINED);
	
	// Ancho y alto de imagenes
	public static int IMG_WIDTH_XXSMALL, 	IMG_HEIGHT_XXSMALL;
	public static int IMG_WIDTH_XSMALL, 	IMG_HEIGHT_XSMALL;
	public static int IMG_WIDTH_SMALL, 		IMG_HEIGHT_SMALL;
	public static int IMG_WIDTH_MEDIUM, 	IMG_HEIGHT_MEDIUM;
	public static int IMG_WIDTH_XMEDIUM, 	IMG_HEIGHT_XMEDIUM;
	public static int IMG_WIDTH_LARGE, 		IMG_HEIGHT_LARGE;
	public static int IMG_WIDTH_XLARGE, 	IMG_HEIGHT_XLARGE;
	public static int IMG_WIDTH_XXLARGE, 	IMG_HEIGHT_XXLARGE;
	
	public static int WIDTH_DISPONIBLE 	= Display.getWidth();
	public static int HEIGHT_DISPONIBLE = Display.getHeight();
	
	// Ancho y Alto de Galerias de Imgs
	public static int WIDTH_GALERIA_COMPLETO = WIDTH_DISPONIBLE;
	public static int HEIGHT_GALERIA_COMPLETO = Math.min(WIDTH_DISPONIBLE,HEIGHT_DISPONIBLE);
	public static int HEIGHT_GALERIA_SMALL 	= HEIGHT_GALERIA_COMPLETO / 2;
	public static int HEIGHT_GALERIA_MEDIUM = HEIGHT_GALERIA_COMPLETO / 2 + HEIGHT_GALERIA_COMPLETO / 8;
	public static int HEIGHT_GALERIA_LARGE 	= HEIGHT_GALERIA_COMPLETO / 2 + HEIGHT_GALERIA_COMPLETO / 4;
	
	static{
		if ( WIDTH_DISPONIBLE > 320 ){ 	// High Resolution
			IMG_WIDTH_XXSMALL 	= 60;
			IMG_WIDTH_XSMALL 	= 80;
			IMG_WIDTH_SMALL 	= 100;
			IMG_WIDTH_MEDIUM	= 120;
			IMG_WIDTH_XMEDIUM 	= 140;
			IMG_WIDTH_LARGE 	= 160;
			IMG_WIDTH_XLARGE 	= 180;
			
			IMG_HEIGHT_XXSMALL 	= 60;
			IMG_HEIGHT_XSMALL 	= 80;
			IMG_HEIGHT_SMALL 	= 100;
			IMG_HEIGHT_MEDIUM	= 120;
			IMG_HEIGHT_XMEDIUM 	= 140;
			IMG_HEIGHT_LARGE 	= 160;
			IMG_HEIGHT_XLARGE 	= 180;
		}else{ 								// Low Resolution
			IMG_WIDTH_XXSMALL 	= 35;
			IMG_WIDTH_XSMALL 	= 50;
			IMG_WIDTH_SMALL 	= 70;
			IMG_WIDTH_MEDIUM	= 90;
			IMG_WIDTH_XMEDIUM 	= 110;
			IMG_WIDTH_LARGE 	= 130;
			IMG_WIDTH_XLARGE 	= 150;
			
			IMG_HEIGHT_XXSMALL 	= 35;
			IMG_HEIGHT_XSMALL 	= 50;
			IMG_HEIGHT_SMALL 	= 70;
			IMG_HEIGHT_MEDIUM	= 90;
			IMG_HEIGHT_XMEDIUM 	= 110;
			IMG_HEIGHT_LARGE 	= 130;
			IMG_HEIGHT_XLARGE 	= 150;
		}
		
		IMG_WIDTH_XXLARGE 	= WIDTH_DISPONIBLE - 50;
		IMG_HEIGHT_XXLARGE 	= HEIGHT_DISPONIBLE - 50;
	}
	
	// Paddings
	public static XYEdges CUADRADO_SMALL 	= new XYEdges(5,5,5,5);
	public static XYEdges CUADRADO_MEDIUM 	= new XYEdges(8,8,8,8);
	public static XYEdges CUADRADO_LARGE 	= new XYEdges(11,11,11,11);
	public static XYEdges CUADRADO_XLARGE 	= new XYEdges(15,15,15,15);
	
	public static XYEdges IZQ_DER_SMALL 	= new XYEdges(1,5,1,5);
	public static XYEdges IZQ_DER_MEDIUM 	= new XYEdges(1,8,1,8);
	public static XYEdges IZQ_DER_LARGE 	= new XYEdges(1,11,1,11);
	public static XYEdges IZQ_DER_XLARGE 	= new XYEdges(1,15,1,15);
	
	public static XYEdges ARRIBA_ABAJO_SMALL = new XYEdges(5,1,5,1);
	public static XYEdges ARRIBA_ABAJO_MEDIUM= new XYEdges(8,1,8,1);
	public static XYEdges ARRIBA_ABAJO_LARGE = new XYEdges(11,1,11,1);
	public static XYEdges ARRIBA_ABAJO_XLARGE = new XYEdges(15,1,15,1);
	
	// Alineaciones:
	public static int IZQUIERDA 	= 0;
	public static int CENTRO 		= 1;
	public static int DERECHA 		= 2;
	
	public static boolean SI = true;
	public static boolean NO = false;
	
	public static int ALPHA_ALTO = 190;
	public static int ALPHA_MEDIO = 120;
	public static int ALPHA_BAJO = 20;
}