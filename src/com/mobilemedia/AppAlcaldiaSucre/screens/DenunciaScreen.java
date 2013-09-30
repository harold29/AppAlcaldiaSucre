package com.mobilemedia.AppAlcaldiaSucre.screens;

import com.mobilemedia.AppAlcaldiaSucre.componentes.EditFieldDenuncias;
import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;

import net.rim.blackberry.api.invoke.CameraArguments;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.device.api.io.file.FileSystemJournal;
import net.rim.device.api.io.file.FileSystemJournalEntry;
import net.rim.device.api.io.file.FileSystemJournalListener;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.ControlledAccessException;
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
	
	private boolean ejecuto, imgHorizontal, changePic = false;
	private BitmapField foto;
	byte img[] = null;
	private EncodedImage ei2;
	private Bitmap ei3;
	private ButtonField Tomarfoto, cambiar, guardar, borrar, enviar;
	private LabelFieldCustomColor labelPage;
	private VerticalFieldManager pantalla, imgZone;
	private EditFieldDenuncias nombre, direccion, telefono, email, comentarios;
	
	public DenunciaScreen() {
		Application.getApplication().addFileSystemJournalListener(this);
	}
	
	public void loadScreen() {
		pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE | USE_ALL_WIDTH);
		pantalla.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		imgZone = new VerticalFieldManager(Field.NON_FOCUSABLE | USE_ALL_WIDTH);
		imgZone.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		/***************************************************************************************************************/
		
		labelPage = new LabelFieldCustomColor("Denuncias", Field.NON_FOCUSABLE, ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
				ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
		
		HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);
		headerPage.setBackground(BackgroundFactory.createSolidBackground(0xF8981C));
		headerPage.setPadding(10, 10, 10, 10);
		headerPage.add(labelPage);
		
		pantalla.add(labelPage);
		
		/***************************************************************************************************************/
		
		nombre = new EditFieldDenuncias(USE_ALL_WIDTH, 40, "Nombre y Apellido");
		pantalla.add(nombre);
		
		direccion = new EditFieldDenuncias(USE_ALL_WIDTH, 70, "Dirección");
		pantalla.add(direccion);
		
		telefono = new EditFieldDenuncias(USE_ALL_WIDTH, 70, "Teléfono");
		pantalla.add(telefono);
		
		email = new EditFieldDenuncias(USE_ALL_WIDTH, 70, "Correo Electrónico");
		pantalla.add(email);
		
		comentarios = new EditFieldDenuncias(USE_ALL_WIDTH, 70, "Comentarios");
		pantalla.add(comentarios);
	}

	public void fileJournalChanged() {
		if (UiApplication.getUiApplication().getActiveScreen() instanceof DenunciasScreen)
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
					//	setFoto(path);
						ejecuto = true;
					}
				} catch (ControlledAccessException e) {
					Dialog.alert("Por favor conceda los permisos a la aplicación.\n" + e.getMessage());
					//Log.log("Error de permisos",e.getMessage());
					
				}
			}
		}
		
	}

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		if (field == Tomarfoto) 
		{ 
			ejecuto = false;
			Invoke.invokeApplication(Invoke.APP_TYPE_CAMERA, new CameraArguments());
			changePic = true;
			//ScreenEngine.getInstance().goCamera(); 
		}
		if (field == enviar) 
		{
			System.out.println("********GO SUBMIT*******"); 
		}		
		if (field == borrar) {
			ejecuto = false;
			ei2 = null;
			loadScreen();
		}
		if (field == cambiar) 
		{
			ejecuto = false;
			Invoke.invokeApplication(Invoke.APP_TYPE_CAMERA, new CameraArguments());
		}
	}
}