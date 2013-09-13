package com.mobilemedia.AppAlcaldiaSucre.custom;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.XYEdges;

import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;

public class DetalleNoticiaCustom {
	
	/* Paddings:
	 * CUADRADO_SMALL, CUADRADO_MEDIUM, CUADRADO_LARGE, CUADRADO_XLARGE
	 * IZQ_DER_SMALL, IZQ_DER_MEDIUM, IZQ_DER_LARGE, IZQ_DER_XLARGE
	 * ARRIBA_ABAJO_SMALL, ARRIBA_ABAJO_MEDIUM, ARRIBA_ABAJO_LARGE, ARRIBA_ABAJO_XLARGE
	 */
	public static XYEdges PADDING_FOTO 			= Constantes.CUADRADO_SMALL;
	public static XYEdges PADDING_TEXTO 		= Constantes.CUADRADO_XLARGE;
	public static XYEdges PADDING_FECHA_HORA 	= Constantes.IZQ_DER_SMALL;
	public static XYEdges PADDING_TITULO		= Constantes.CUADRADO_XLARGE;
	public static XYEdges PADDING_AUTOR 		= Constantes.IZQ_DER_SMALL;
	public static XYEdges PADDING_FUENTE		= Constantes.IZQ_DER_SMALL;
	public static XYEdges PADDING_PIE_DE_FOTO 	= Constantes.CUADRADO_MEDIUM;
	
	/* Fuentes:
	 * XSMALL_NORMAL_FONT, SMALL_NORMAL_FONT, MEDIUM_NORMAL_FONT, XMEDIUM_NORMAL_FONT, LARGE_NORMAL_FONT, XLARGE_NORMAL_FONT
	 * XSMALL_BOLD_FONT, SMALL_BOLD_FONT, MEDIUM_BOLD_FONT, XMEDIUM_BOLD_FONT, LARGE_BOLD_FONT, XLARGE_BOLD_FONT
	 * XSMALL_ITALIC_FONT, SMALL_ITALIC_FONT, MEDIUM_ITALIC_FONT, XMEDIUM_ITALIC_FONT, LARGE_ITALIC_FONT, XLARGE_ITALIC_FONT
	 * XSMALL_UNDERLINED_FONT, SMALL_UNDERLINED_FONT, MEDIUM_UNDERLINED_FONT, XMEDIUM_UNDERLINED_FONT, LARGE_UNDERLINED_FONT, XLARGE_UNDERLINED_FONT
	 */
	public static Font TEXTO_FONT 		= Constantes.SMALL_NORMAL_FONT;
	public static Font FECHA_HORA_FONT 	= Constantes.SMALL_ITALIC_FONT;
	public static Font TITULO_FONT 		= Constantes.MEDIUM_BOLD_FONT;
	public static Font AUTOR_FONT 		= Constantes.SMALL_ITALIC_FONT;
	public static Font FUENTE_FONT 		= Constantes.SMALL_ITALIC_FONT;
	public static Font PIE_DE_FOTO_FONT = Constantes.XSMALL_ITALIC_FONT;
	
	/* Colores: 
	 * Color.BLACK, Color.Red, ... http://www.blackberry.com/developers/docs/5.0.0api/net/rim/device/api/ui/Color.html
	 * o tambien valores en hexadeximales: 0xAFB20C, 0x670CB2, ... http://www.josesupo.com/paleta-de-color-y-conversor-rgb-hexadecimal 
	 */
	public static int TITULO_COLOR_FONT 	= 0x2b2b2b; // Color.BLACK;
	public static int FECHA_COLOR_FONT 		= 0x2b2b2b;
	public static int AUTOR_COLOR_FONT 		= 0x2b2b2b;
	public static int FUENTE_COLOR_FONT 	= 0x2b2b2b;
	public static int PIE_DE_FOTO_COLOR_FONT= 0x2b2b2b;
	public static int TEXTO_COLOR_FONT 		= 0x2b2b2b;
	
	/* Widths: 
	 * IMG_WIDTH_XSMALL, IMG_WIDTH_SMALL, IMG_WIDTH_MEDIUM, IMG_WIDTH_XMEDIUM, IMG_WIDTH_LARGE, IMG_WIDTH_XLARGE; 
	 */
	public static int WIDTH_IMG 		= Constantes.IMG_WIDTH_XLARGE;
	public static int WIDTH_GALERIA 	= Constantes.WIDTH_DISPONIBLE;
	public static int WIDTH_IMG_GALERIA = Constantes.IMG_WIDTH_XMEDIUM;
	/* Heights: 
	 * IMG_HEIGHT_XSMALL, IMG_HEIGHT_SMALL, IMG_HEIGHT_MEDIUM, IMG_HEIGHT_XMEDIUM, IMG_HEIGHT_LARGE, IMG_HEIGHT_XLARGE; 
	 */
	public static int HEIGHT_IMG 		= Constantes.IMG_HEIGHT_XLARGE;
	public static int HEIGHT_GALERIA 	= Constantes.IMG_HEIGHT_MEDIUM;
	public static int HEIGHT_IMG_GALERIA= Constantes.IMG_HEIGHT_MEDIUM;
	
	// Constantes.SI o Constantes.NO
	public static boolean MOSTRAR_IMG 		= Constantes.SI;
	public static boolean MOSTRAR_FECHA 	= Constantes.SI;
	public static boolean MOSTRAR_AUTOR 	= Constantes.SI;
	public static boolean MOSTRAR_FUENTE 	= Constantes.SI;
	public static boolean MOSTRAR_GALERIA 	= Constantes.SI;
	
	public static final int ALPHA_GALERIA = Constantes.ALPHA_BAJO;
	public static final int COLOR_GALERIA = Color.WHITE;
	
	/* Al colocar solo el nombre se accede a la carpeta: src/img/
	 * si la img se encuentra en cualquier otra ubicacion hay que inidica la ruta
	 */
	public static String PATH_FLECHA_IZQ = "flechaIzq.png";
	public static String PATH_FLECHA_DER = "flechaDer.png";
}