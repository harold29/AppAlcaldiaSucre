package com.mobilemedia.AppAlcaldiaSucre.screens;

import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Image;

import com.mobilemedia.AppAlcaldiaSucre.AppAlcaldiaSucre;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;



public final class ImageScreen extends MainScreen 
{
	private static ImageScreen instance;
	
	public static ImageScreen getInstance(byte[] raw) 
	{
		if (instance == null) 
		{
			instance = new ImageScreen(raw);
		}
		return instance;
	}
	
	/** The down-scaling ratio applied to the snapshot Bitmap */
    private static final int IMAGE_SCALING = 7;

    /** The base file name used to store pictures */
    private static String FILE_NAME = System.getProperty("fileconn.dir.photos") + "IMAGE";

    /** The extension of the pictures to be saved */
    private static String EXTENSION = ".bmp";

    /** A counter for the number of snapshots taken */
    private static int _counter;

    /** A reference to the current screen for listeners */
    private ImageScreen _imageScreen;
    
    public ImageScreen (final byte[] raw) 
    {
    	_imageScreen = this;
    	
    	VerticalFieldManager pantalla = new VerticalFieldManager(NO_VERTICAL_SCROLL | FIELD_HCENTER | FIELD_VCENTER );
    //	pantalla.setBackground(BackgroundFactory.createSolidBackground(0x00F1F1F1));
    	
    	Bitmap image = Bitmap.createBitmapFromBytes(raw, 0, -1, IMAGE_SCALING);
    	
    	HorizontalFieldManager pictContainer = new HorizontalFieldManager(Field.FIELD_HCENTER);
    	HorizontalFieldManager buttonContainer = new HorizontalFieldManager(Field.FIELD_HCENTER);
    	
    	BitmapField imageField = new BitmapField(image);
    	pictContainer.add(imageField);
    	
    	ButtonField pictButton = new ButtonField("Guardar");
    	pictButton.setChangeListener(new SaveListener(raw));
    	buttonContainer.add(pictButton);
    	
    	ButtonField cancelButton = new ButtonField("Cancelar");
    	cancelButton.setChangeListener(new CancelListener());
    	buttonContainer.add(cancelButton);
    	
    	pantalla.add(pictContainer);
    	pantalla.add(buttonContainer);
    	
    	add(pantalla);
    }
    
    private class SaveListener implements FieldChangeListener
    {
    	private byte [] _raw;
    	
    	public SaveListener(byte[] raw) {
			// TODO Auto-generated constructor stub
    		_raw = raw;
		}
    	
    	public void fieldChanged(Field field, int context)
    	{
    		try 
    		{
    			FileConnection file = (FileConnection) Connector.open(FILE_NAME + _counter + EXTENSION);
    			
    			while (file.exists()) {
    				file.close();
    				++ _counter;
    				file = (FileConnection)Connector.open(FILE_NAME +_counter + EXTENSION);
    			}
    			
    			file.create();
    			
    			OutputStream out = file.openOutputStream();
    			out.write(_raw);
    			
    			out.close();
    			file.close();
    		}
    		catch (Exception e) 
    		{
    			System.out.println("ERROR " + e.getMessage());
    		}
    		
    		Dialog.inform("Guardado en: " + FILE_NAME + _counter + EXTENSION);
    		
    		++_counter;
    		
    		UiApplication.getUiApplication().popScreen(_imageScreen);
    	}
    }
    
    private class CancelListener implements FieldChangeListener
    {
    	public void fieldChanged (Field field, int context)
    	{
			UiApplication.getUiApplication().popScreen(_imageScreen);
    	}
    }
}