package com.mobilemedia.AppAlcaldiaSucre.facade;

import com.mobilemedia.AppAlcaldiaSucre.interfaces.CargaBitmap;
import com.mobilemedia.AppAlcaldiaSucre.interfaces.ObjConFoto;

import net.rim.device.api.system.EncodedImage;

public class FotoSmallFacade 
{
	private ImageFacade imageFacade;
	private ObjConFoto obj;
	private CargaBitmap field;
	
	public void cargarFoto(ObjConFoto obj, CargaBitmap field, String id)
	{
		this.field = field;
		System.out.println("cargarFoto");
		imageFacade = new ImageFacade();
        imageFacade.setListener(new ImageHandler());
        imageFacade.getImage(obj.getUrlFoto(), id); // OJO aqui, ID??
        this.obj = obj;
	}
	
	private class ImageHandler implements ImageFacade.Listener 
	{
		public void onGetImageComplete(EncodedImage bitmap, String index) 
		{
			System.out.println("onGetImageCompelte ##### index: " + index);

			obj.setFotoBitmap(bitmap.getData());
			field.setBitmap(bitmap.getBitmap(), Integer.parseInt( index.trim() ));
		}

		public void onGetImageFailed(String index) {
			System.out.println("onGetImageFailed ##### index: " + index);
		}
	}
}