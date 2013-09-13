package com.mobilemedia.AppAlcaldiaSucre.custom;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.XYEdges;

public class CatalogoCustom 
{
	public static final long TIEMPO_PERSISTENCIA = Constantes.UNASEMANA_PERSISTENCIA;
	
	public static final XYEdges NOMBRE_PROD_PADDING = Constantes.CUADRADO_MEDIUM;
	public static final XYEdges PRECIO_PADDING 		= Constantes.IZQ_DER_MEDIUM;
	
	public static final Font NOMBRE_PROD_FONT 		= Constantes.LARGE_BOLD_FONT;
	public static final Font PRECIO_FONT 			= Constantes.MEDIUM_NORMAL_FONT;
	
	public static final int NOMBRE_PROD_FONT_COLOR 	= 0x2b2b2b;
	public static final int PRECIO_FONT_COLOR 		= 0x2b2b2b;
	
	public static final String PATH_FLECHA_IZQ = "flechaIzq.png";
	public static final String PATH_FLECHA_DER = "flechaDer.png";
	
	/* Widths: 
	 * IMG_WIDTH_XSMALL, IMG_WIDTH_SMALL, IMG_WIDTH_MEDIUM, IMG_WIDTH_XMEDIUM, IMG_WIDTH_LARGE, IMG_WIDTH_XLARGE; 
	 */
	public static final int WIDTH_GALERIA 	= Constantes.WIDTH_GALERIA_COMPLETO;
	public static final int WIDTH_IMG_GALERIA = Constantes.IMG_WIDTH_XXLARGE;
	
	/* Heights: 
	 * IMG_HEIGHT_XSMALL, IMG_HEIGHT_SMALL, IMG_HEIGHT_MEDIUM, IMG_HEIGHT_XMEDIUM, IMG_HEIGHT_LARGE, IMG_HEIGHT_XLARGE; 
	 */
	public static final int HEIGHT_GALERIA 	= Constantes.HEIGHT_GALERIA_MEDIUM;
	public static final int HEIGHT_IMG_GALERIA= Constantes.IMG_HEIGHT_XLARGE;
}