package com.mobilemedia.AppAlcaldiaSucre.interfaces;

import net.rim.device.api.system.Bitmap;

public interface ObjConGaleria {
	
	public String[] getFotos_imagenUrl();
	public Bitmap[] getBitmapGaleria();
	public void setBitmapGaleria(byte[][] galeria);
	public void setIndexBitmapGaleria(byte[] fotoBitmap, int i);
}
