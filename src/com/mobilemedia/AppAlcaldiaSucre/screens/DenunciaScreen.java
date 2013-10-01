package com.mobilemedia.AppAlcaldiaSucre.screens;

import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import com.mobilemedia.AppAlcaldiaSucre.componentes.EditFieldDenuncias;
import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.utilidades.ImageManipulator;

import net.rim.blackberry.api.invoke.CameraArguments;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.device.api.io.file.FileSystemJournal;
import net.rim.device.api.io.file.FileSystemJournalEntry;
import net.rim.device.api.io.file.FileSystemJournalListener;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.ControlledAccessException;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.EventInjector;
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


public class DenunciaScreen extends MainScreen implements FieldChangeListener, FileSystemJournalListener {
	
	private static DenunciaScreen instance;
	
	public static DenunciaScreen getInstance() {
		if (instance == null) {
			instance = new DenunciaScreen();
		}
		return instance;
	}

	/*****************************************************************************************************************/
	
	private boolean ejecuto, imgHorizontal, changePic = false;
	private BitmapField foto;
	byte img[] = null;
	private EncodedImage ei2;
	Bitmap ei3;
	private ButtonField tomarfoto, cambiar, borrar, enviar;
	private LabelFieldCustomColor labelPage;
	private VerticalFieldManager pantalla, imgZone, nombre, direccion, telefono, email, comentarios;

	/*****************************************************************************************************************/
	
	public DenunciaScreen() {
		Application.getApplication().addFileSystemJournalListener(this);
		
		loadScreen();
	}
	
	public void loadScreen() {
		pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE | USE_ALL_WIDTH);
		pantalla.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		imgZone = new VerticalFieldManager(Field.NON_FOCUSABLE | USE_ALL_WIDTH | FIELD_HCENTER);
		imgZone.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		/*************************************************************************************************************/
		
		labelPage = new LabelFieldCustomColor("Denuncias", Field.NON_FOCUSABLE, ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
				ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
		
		HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);
		headerPage.setBackground(BackgroundFactory.createSolidBackground(0xF8981C));
		headerPage.setPadding(10, 10, 10, 10);
		headerPage.add(labelPage);
		
		pantalla.add(headerPage);
		
		/*************************************************************************************************************/
		EditFieldDenuncias vfmaux;
		
		vfmaux = new EditFieldDenuncias(USE_ALL_WIDTH, 40, "Nombre y Apellido",20);
		nombre = vfmaux.getVFM();
		pantalla.add(nombre);
		
		vfmaux = new EditFieldDenuncias(USE_ALL_WIDTH, 70, "Dirección",20);
		direccion = vfmaux.getVFM();
		pantalla.add(direccion);
		
		vfmaux = new EditFieldDenuncias(USE_ALL_WIDTH, 70, "Teléfono",20);
		telefono = vfmaux.getVFM();
		pantalla.add(telefono);
		
		vfmaux = new EditFieldDenuncias(USE_ALL_WIDTH, 70, "Correo Electrónico",20);
		email = vfmaux.getVFM();
		pantalla.add(email);
		
		vfmaux = new EditFieldDenuncias(USE_ALL_WIDTH, 70, "Comentarios",20);
		comentarios = vfmaux.getVFM();
		pantalla.add(comentarios);
		
		/*************************************************************************************************************/
		
		foto = new BitmapField(null, FIELD_HCENTER);
		foto.setPadding(20, 0, 20, 0);
		imgZone.add(foto);
		
		/*************************************************************************************************************/
		
		tomarfoto = new ButtonField("Tomar Foto", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		tomarfoto.setChangeListener(this);
		
		enviar = new ButtonField("Enviar Denuncia", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		enviar.setChangeListener(this);
		
		cambiar = new ButtonField("Cambiar Foto", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		cambiar.setChangeListener(this);
		
		borrar = new ButtonField("Borrar Foto", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		borrar.setChangeListener(this);
		
		cambiar = new ButtonField("Cambiar Foto", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		cambiar.setChangeListener(this);
		
		imgZone.add(tomarfoto);
		imgZone.add(enviar);
		
		/*************************************************************************************************************/
		
		add(pantalla);
		add(imgZone);
	}

	public void fileJournalChanged() {
		if (UiApplication.getUiApplication().getActiveScreen() instanceof DenunciaScreen)
		{
			if (!ejecuto) {
				try {
					long lookUSN = FileSystemJournal.getNextUSN() - 1;
					String path = "";
					FileSystemJournalEntry entry = FileSystemJournal.getEntry(lookUSN);
					if (entry != null 
							 && entry.getEvent() == FileSystemJournalEntry.FILE_CHANGED)
					{
						path = entry.getPath();
						EventInjector.KeyEvent injector = new EventInjector.KeyEvent(
								EventInjector.KeyCodeEvent.KEY_DOWN,
								Characters.ESCAPE, 0, -1);
						injector.post();
						injector.post();
						setFoto(path);
						ejecuto = true;
					}
				} catch (ControlledAccessException e) {
					Dialog.alert("Por favor conceda los permisos a la aplicación.\n" + e.getMessage());					
				}
			}
		}
		
	}

	public void fieldChanged(Field field, int context) {
		if (field == tomarfoto) 
		{ 
			ejecuto = false;
			Invoke.invokeApplication(Invoke.APP_TYPE_CAMERA, new CameraArguments());
			changePic = true;
		}
		if (field == enviar) 
		{
			System.out.println("********GO SUBMIT*******"); 
		}		
		if (field == borrar) 
		{
			ejecuto = false;
			ei2 = null;
			foto.setBitmap(null);
			
			imgZone.deleteAll();
			imgZone.add(foto);
			imgZone.add(tomarfoto);
			imgZone.add(enviar);
		}
		if (field == cambiar) 
		{
			ejecuto = false;
			Invoke.invokeApplication(Invoke.APP_TYPE_CAMERA, new CameraArguments());
			changePic = true;
		}
	}
	
	public void setFoto(String file) 
	{
		try {
			FileConnection fc = (FileConnection) Connector.open("file://" + file);
		
			InputStream is = fc.openInputStream();
			img = new byte [(int) fc.fileSize()];		
			is.read(img);
			is.close();
			fc.close();
			EncodedImage ei = EncodedImage.createEncodedImage(img, 0, img.length);
			ei2 = scaleToFactor(ei, ei.getWidth(), 200);

			if (Display.getOrientation() == Display.ORIENTATION_PORTRAIT) {
				ei3 = ImageManipulator.rotate(ei2.getBitmap(), 270);
			} else {
				ei3 = ei2.getBitmap();
				imgHorizontal = true;
			}
			
			foto.setBitmap(ei3);
			
			imgZone.deleteAll();
			imgZone.add(foto);
			imgZone.add(cambiar);
			imgZone.add(borrar);
			imgZone.add(enviar);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR DE SETFOTO: " + e.toString());
		}
	}
	
	public  EncodedImage scaleToFactor(EncodedImage encoded, int curSize, int newSize) {
		int numerator = Fixed32.toFP(curSize);
		int denominator = Fixed32.toFP(newSize);
		int scale = Fixed32.div(numerator, denominator);
		
		return encoded.scaleImage32(scale, scale);
	}
}