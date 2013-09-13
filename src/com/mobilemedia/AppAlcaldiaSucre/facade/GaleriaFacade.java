package com.mobilemedia.AppAlcaldiaSucre.facade;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;

import com.mobilemedia.AppAlcaldiaSucre.interfaces.CargaBitmap;

/** SOLO USADA POR DETALLE NOTICA (No guarda las imgs)
 * Clase que, luego de recibida la respuesta de la peticion,
 * lleva a cabo alguna accion 
 */
public class GaleriaFacade { 
	private ImageFacade imageFacade;
	private CargaBitmap field;
	private String[] urls;
	private int indiceImg;
	
	private Bitmap[] bitmaps;
	private int ancho, alto;
	private boolean escalar;
	
	public GaleriaFacade(String[] urls, CargaBitmap field, int ancho, int alto, boolean escalar){
		this.field = field;
		this.urls = urls;
		this.indiceImg = 0; 
		this.ancho = ancho; this.alto = alto;
		this.bitmaps = new Bitmap[urls.length];
		this.escalar = escalar;
	}
	
	public void cargarFotos(){
		imageFacade = new ImageFacade();
		imageFacade.setListener(new ImageHandler());
		imageFacade.getImage(urls[indiceImg]);
	}
	
	private class ImageHandler implements ImageFacade.Listener 
	{
		public void onGetImageComplete(EncodedImage bitmap, String index) {
			if (escalar){
				Bitmap bitmapEscalado = new Bitmap(ancho, alto);
				bitmap.getBitmap().scaleInto(bitmapEscalado, 
	                                         Bitmap.FILTER_BILINEAR, 
	                                         Bitmap.SCALE_TO_FILL);
				bitmaps[indiceImg] = bitmapEscalado;
			} else 
				bitmaps[indiceImg] = bitmap.getBitmap() ;
			
			System.out.println("############################LLEgo Img: "+ indiceImg);
			indiceImg++;
			
			if (urls.length > indiceImg)
				cargarFotos();
			else{ // Carga completa
				int numImgs = urls.length;
				for (int i = 0; i < numImgs; i++ )
					field.setBitmap(bitmaps[i], i);
			}
		}

		public void onGetImageFailed(String index) {
			System.out.println("onGetImageFailed");
			System.out.println("############################FALLO Img: "+ indiceImg);
			indiceImg++;
			
			if (urls.length > indiceImg)
				cargarFotos();
			else{ // Carga completa
				int numImgs = urls.length;
				for (int i = 0; i < numImgs; i++ )
					field.setBitmap(bitmaps[i], i);
			}
		}
	}
}