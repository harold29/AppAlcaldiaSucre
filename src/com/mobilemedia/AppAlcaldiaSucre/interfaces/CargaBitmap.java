package com.mobilemedia.AppAlcaldiaSucre.interfaces;

import net.rim.device.api.system.Bitmap;

public interface CargaBitmap{ // Con hilo
    /** Method that will be called on successful completion of image download() */
    public void setBitmap(final Bitmap bitmap, final int indice);
}