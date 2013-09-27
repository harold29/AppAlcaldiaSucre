package com.mobilemedia.AppAlcaldiaSucre.screens;

import java.io.InputStream;

import javax.microedition.amms.control.camera.*;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.media.*;

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
import net.rim.device.api.system.EventInjector.KeyEvent;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;
import net.rim.device.api.ui.container.MainScreen;

import com.mobilemedia.AppAlcaldiaSucre.componentes.EditFieldDenuncias;
import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonField;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonHome;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonSet;
import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;
import com.mobilemedia.AppAlcaldiaSucre.engine.BitmapManager;
import com.mobilemedia.AppAlcaldiaSucre.utilidades.ImageManipulator;

public class DenunciasScreen extends MainScreen implements FieldChangeListener, FileSystemJournalListener {
	
	private static DenunciasScreen instance;
	
	public static DenunciasScreen getInstance() {
		if (instance == null) {
			instance = new DenunciasScreen();
		}
		return instance;
	}
	  
	private VerticalFieldManager pantalla, imgZone;
	private boolean ejecuto;
	private boolean changePic = false;
	private boolean imgHorizontal;
	private BitmapField foto;
	byte img[] = null;
	private EncodedImage ei2;
	private ButtonField photoButton,cambiar, guardar, borrar, submitButton;
	Bitmap ei3;
	
	
	private LabelFieldCustomColor labelPage;
//	ListStyleButtonHome photoButton;
//	ListStyleButtonHome submitButton;
	ListStyleButtonHome changeButton;
	ListStyleButtonHome deleteButton;
	ListStyleButtonHome saveButton;
	
	public DenunciasScreen() {
		Application.getApplication().addFileSystemJournalListener(this);
		loadScreen();
		add(pantalla);
		add(imgZone);
		//add(submitButton);
		
	}
	
	public void loadScreen () {
		pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE | USE_ALL_WIDTH);
		pantalla.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		imgZone = new VerticalFieldManager(Field.NON_FOCUSABLE | FIELD_HCENTER | USE_ALL_WIDTH);
		imgZone.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		labelPage = new LabelFieldCustomColor("Directorio", Field.NON_FOCUSABLE | Field.FIELD_HCENTER, ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
				ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
		
		HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_VCENTER | Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);
		headerPage.setBackground(BackgroundFactory.createSolidBackground(0xf8981c));
		headerPage.setPadding(10,10,10,10);
		headerPage.add(labelPage);
		pantalla.add(headerPage);
		
		VerticalFieldManager editArea = new VerticalFieldManager();
		//editArea.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		
		LabelFieldCustomColor labelEdit = new LabelFieldCustomColor("Nombre y Apellido:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField nombreDenunciante = new BasicEditField("", null, 40, USE_ALL_WIDTH);
		nombreDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		nombreDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(nombreDenunciante);
		editArea.setPadding(20,40,0,30);
		pantalla.add(editArea);
		
		editArea = new VerticalFieldManager();
		labelEdit = new LabelFieldCustomColor("Dirección:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField direccionDenunciante = new BasicEditField("",null,70,USE_ALL_WIDTH);
		direccionDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		direccionDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(direccionDenunciante);
		editArea.setPadding(20,40,0,30);
		pantalla.add(editArea);


		editArea = new VerticalFieldManager();
		labelEdit = new LabelFieldCustomColor("Número de Teléfono:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField numeroDenunciante = new BasicEditField("",null,20,USE_ALL_WIDTH);
		numeroDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		numeroDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(numeroDenunciante);
		editArea.setPadding(20,130,0,30);
		pantalla.add(editArea);
		
		editArea = new VerticalFieldManager();
		labelEdit = new LabelFieldCustomColor("Email:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField emailDenunciante = new BasicEditField("",null,30,USE_ALL_WIDTH);
		emailDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		emailDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(emailDenunciante);
		editArea.setPadding(20,90,0,30);
		pantalla.add(editArea);
		
		editArea = new VerticalFieldManager();
		labelEdit = new LabelFieldCustomColor("Comentarios:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField comentarioDenunciante = new BasicEditField("",null,140,USE_ALL_WIDTH);
		comentarioDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		comentarioDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(comentarioDenunciante);
		editArea.setPadding(20,40,0,30);
		pantalla.add(editArea);
		
		/*ListStyleButtonSet buttonSet = new ListStyleButtonSet();
		photoButton = new ListStyleButtonHome("Tomar Foto", Constantes.MEDIUM_NORMAL_FONT, FIELD_HCENTER | FIELD_VCENTER, 1);
		photoButton.setChangeListener(this);
		buttonSet.add(photoButton);
		buttonSet.setPadding(20, 40, 20, 40);
		pantalla.add(buttonSet);
		
		buttonSet = new ListStyleButtonSet();
		submitButton = new ListStyleButtonHome("Enviar Denuncia", Constantes.MEDIUM_NORMAL_FONT, FIELD_HCENTER | FIELD_VCENTER, 1);
		buttonSet.add(submitButton);
		buttonSet.setPadding(20, 40, 20, 40);	
		pantalla.add(buttonSet);*/
		
		photoButton = new ButtonField("Foto", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		photoButton.setChangeListener(this);
		imgZone.add(photoButton);
		
		submitButton = new ButtonField("Enviar Denuncia", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		submitButton.setChangeListener(this);
		imgZone.add(submitButton);
		
		cambiar = new ButtonField("Cambiar", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		cambiar.setChangeListener(this);
		//imgZone.add(cambiar);
		
		borrar = new ButtonField("Borrar Foto", ButtonField.CONSUME_CLICK | FIELD_HCENTER);
		borrar.setChangeListener(this);
		//imgZone.add(borrar);
	}
	
	public void fieldChanged(Field field, int context) 
	{
		if (field == photoButton) 
		{ 
			ejecuto = false;
			Invoke.invokeApplication(Invoke.APP_TYPE_CAMERA, new CameraArguments());
			changePic = true;
			//ScreenEngine.getInstance().goCamera(); 
		}
		if (field == submitButton) 
		{
			System.out.println("********GO SUBMIT*******"); 
		}		
		if (field == borrar) {
			ejecuto = false;
			ei2 = null;
		}
	}
	
	public void fileJournalChanged() 
	{
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
						setFoto(path);
	//					ejecuto = true;
					}
				} catch (ControlledAccessException e) {
					Dialog.alert("Por favor conceda los permisos a la aplicación.\n" + e.getMessage());
					//Log.log("Error de permisos",e.getMessage());
					
				}
			}
		}
	}
	
	private void setFoto(String file) 
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
			//pantalla.add(imgZone);
			//add(pantalla);
			//vfmCentral.add(cambiar);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR DE SETFOTO: " + e.getMessage());
		}
	}
	
	public  EncodedImage scaleToFactor(EncodedImage encoded, int curSize, int newSize) {
		int numerator = Fixed32.toFP(curSize);
		int denominator = Fixed32.toFP(newSize);
		int scale = Fixed32.div(numerator, denominator);
		
		return encoded.scaleImage32(scale, scale);
	}
	
	
	
	public boolean onClose() 
	{
		
//		LocalyticsEngine.getInstance().tagEvent(EventsLocalytics.ESTACIONAMIENTO);
//		this.close();
//		return true;
		
		if (changePic)
		{
			int response = Dialog.ask(Dialog.D_YES_NO, "¿Desea guardar los cambios?");
			if(response == Dialog.YES){
			//	guardarUbicacion();
				return true;
			}else{
				return super.onClose();
			}
			
		} else{
		
			return super.onClose();
		}
	}
}