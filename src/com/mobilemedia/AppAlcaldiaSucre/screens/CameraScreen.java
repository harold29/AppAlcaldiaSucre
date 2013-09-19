package com.mobilemedia.AppAlcaldiaSucre.screens;

import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;

import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonSet;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;


public class CameraScreen extends MainScreen {
	private static CameraScreen instance;
	
	public static CameraScreen getInstance() {
		if (instance == null) {
			instance = new CameraScreen();
		}
		return instance;
	}
	
	private VideoControl _controlVideo;
	private Field _areaVideo;
	
	private LabelFieldCustomColor labelPage;
	
	public CameraScreen() {
		VerticalFieldManager pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE | NO_VERTICAL_SCROLL | Field.USE_ALL_HEIGHT | Field.USE_ALL_WIDTH);
		pantalla.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		labelPage = new LabelFieldCustomColor("Camara Denuncias", Field.NON_FOCUSABLE | Field.FIELD_HCENTER, ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
				ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
		
		HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_VCENTER | Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);
		headerPage.setBackground(BackgroundFactory.createSolidBackground(0xf8981c));
		headerPage.setPadding(10,10,20,10);
		headerPage.add(labelPage);
		pantalla.add(headerPage);
		
		inicializarCamara();
		_areaVideo.setPadding(0,0,0,0);
		pantalla.add(_areaVideo);
		
		//pantalla.add(headerPage);
		
		//ListStyleButtonSet buttonSet = new ListStyleButtonSet();
		add(pantalla);
	}
	
	
	public void tomarFoto() {
		try {
			String encoding = null;
			createImageScreen(_controlVideo.getSnapshot(encoding));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private void createImageScreen(byte[] raw) {
		ImageScreen imageScreen = new ImageScreen(raw);
		
		UiApplication.getUiApplication().pushScreen(imageScreen);
	}
	
	
	private void inicializarCamara() {
		try {
			Player user = Manager.createPlayer("capture://video");
			
			user.realize();
			_controlVideo = (VideoControl)user.getControl("VideoControl");
			
			if (_controlVideo != null) {
				_areaVideo = (Field) _controlVideo.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, "net.rim.device.api.ui.Field");
				_controlVideo.setDisplayFullScreen(false);
				_controlVideo.setVisible(true);
			}		
			user.start();
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	protected boolean invokeAction(int action) {
		boolean handled = super.invokeAction(action);
		
		if (!handled) 
		{
			switch(action) 
			{ 
				case ACTION_INVOKE:
				{
					tomarFoto();
					return true;
				}
			}
		}
		return handled;
	}
}