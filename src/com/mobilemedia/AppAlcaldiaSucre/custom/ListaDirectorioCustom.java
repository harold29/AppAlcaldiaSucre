package com.mobilemedia.AppAlcaldiaSucre.custom;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.XYEdges;

public class ListaDirectorioCustom {
	 
	/* Grados de persistencia:
	 * MEDIODIA_PERSISTENCIA, UNDIA_PERSISTENCIA, TRESDIAS_PERSISTENCIA
	 * UNASEMANA_PERSISTENCIA, TRESSEMANAS_PERSISTENCIA, UNMES_PERSISTENCI
	 * PERMANENTE_PERSISTENCIA
	 */
	public static final long TIEMPO_PERSISTENCIA = Constantes.MEDIODIA_PERSISTENCIA;
	
	/* Fuentes:
	 * XSMALL_NORMAL_FONT, SMALL_NORMAL_FONT, MEDIUM_NORMAL_FONT, XMEDIUM_NORMAL_FONT, LARGE_NORMAL_FONT, XLARGE_NORMAL_FONT
	 * XSMALL_BOLD_FONT, SMALL_BOLD_FONT, MEDIUM_BOLD_FONT, XMEDIUM_BOLD_FONT, LARGE_BOLD_FONT, XLARGE_BOLD_FONT
	 * XSMALL_ITALIC_FONT, SMALL_ITALIC_FONT, MEDIUM_ITALIC_FONT, XMEDIUM_ITALIC_FONT, LARGE_ITALIC_FONT, XLARGE_ITALIC_FONT
	 * XSMALL_UNDERLINED_FONT, SMALL_UNDERLINED_FONT, MEDIUM_UNDERLINED_FONT, XMEDIUM_UNDERLINED_FONT, LARGE_UNDERLINED_FONT, XLARGE_UNDERLINED_FONT
	 */
	public static Font FECHA_ACTUALIZACION_FONT = Constantes.SMALL_NORMAL_FONT;
	public static Font FECHA_FONT 				= Constantes.XSMALL_ITALIC_FONT;
	public static Font CONTENIDO_FONT 			= Constantes.SMALL_NORMAL_FONT;
	public static Font CATSUBCAT_FONT 			= Constantes.SMALL_BOLD_FONT;
	public static Font VACIO_FONT 				= Constantes.MEDIUM_NORMAL_FONT;
	
	/* Colores: 
	 * Color.BLACK, Color.Red, ... http://www.blackberry.com/developers/docs/5.0.0api/net/rim/device/api/ui/Color.html
	 * o tambien valores en hexadeximales: 0xAFB20C, 0x670CB2, ... http://www.josesupo.com/paleta-de-color-y-conversor-rgb-hexadecimal 
	 */
	public static int FECHA_ACTUALIZACION_FONT_COLOR 	= 0x2b2b2b; //0x383838;
	public static int FECHA_COLOR_FONT 					= 0x2b2b2b;
	public static int CONTENIDO_COLOR_FONT 				= 0x2b2b2b;
	public static int CATSUBCAT_FONT_COLOR 				= 0x2b2b2b;
	public static int VACIO_FONT_COLOR 					= 0x2b2b2b;
	
	// Imagen: Constantes.SI o Constantes.NO
	public static boolean MOSTRAR_IMG 		= Constantes.NO;
	// Constantes.IZQUIERDA, Constantes.CENTRO o Constantes.DERECHA
	public static int ALINEACION_IMG 		= Constantes.IZQUIERDA;
	
	/* Widths: 
	 * IMG_WIDTH_XSMALL, IMG_WIDTH_SMALL, IMG_WIDTH_MEDIUM, IMG_WIDTH_XMEDIUM, IMG_WIDTH_LARGE, IMG_WIDTH_XLARGE; 
	 */
	public static int WIDTH_IMG  			= Constantes.IMG_WIDTH_XSMALL;
	/* Heights: 
	 * IMG_HEIGHT_XSMALL, IMG_HEIGHT_SMALL, IMG_HEIGHT_MEDIUM, IMG_HEIGHT_XMEDIUM, IMG_HEIGHT_LARGE, IMG_HEIGHT_XLARGE; 
	 */
	public static int HEIGHT_IMG 			= Constantes.IMG_HEIGHT_XSMALL;
	
	// Fecha
	public static boolean MOSTRAR_FECHA		= Constantes.SI;
	
	/* Paddings:
	 * CUADRADO_SMALL, CUADRADO_MEDIUM, CUADRADO_LARGE, CUADRADO_XLARGE
	 * IZQ_DER_SMALL, IZQ_DER_MEDIUM, IZQ_DER_LARGE, IZQ_DER_XLARGE
	 * ARRIBA_ABAJO_SMALL, ARRIBA_ABAJO_MEDIUM, ARRIBA_ABAJO_LARGE, ARRIBA_ABAJO_XLARGE
	 */
	public static XYEdges PADDING = Constantes.IZQ_DER_SMALL;
	
	// Icono de carga
	public static String ANIMACION_IMG_CARGANDO = "spinner22.png";
	public static int NUM_CUADROS_ANIMACION 	= 6;
	
	// Imagen para el boton de actualizar
	public static String ACTUALIZAR_IMG_NORMAL 	= "refreshNormal.png";
	public static String ACTUALIZAR_IMG_FOCO 	= "refreshFocus.png";
}