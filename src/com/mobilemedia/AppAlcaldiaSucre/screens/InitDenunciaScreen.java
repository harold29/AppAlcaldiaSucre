package com.mobilemedia.AppAlcaldiaSucre.screens;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.container.MainScreen;


import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonField;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonSet;
import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;
import com.mobilemedia.AppAlcaldiaSucre.push.PersistentStorage;
import com.mobilemedia.AppAlcaldiaSucre.push.PushController;
import com.mobilemedia.AppAlcaldiaSucre.push.PushMessage;


public class InitDenunciaScreen extends MainScreen implements FieldChangeListener {
	private static InitDenunciaScreen instance;
	
	private static InitDenunciaScreen getInstance() {
		if (instance == null) {
			instance = new InitDenunciaScreen();
		}
		return instance;
	}
	
	private static final int SEP_IDX1 = 0x00010000;
    private static final int SEP_IDX2 = 0x00020000;
	
	private ListStyleButtonField listStyleButtonFieldDenuncias;
	private ListStyleButtonField listStyleButtonFieldRegistro;
	private VerticalFieldManager pantalla;
	
	public InitDenunciaScreen () {
		pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE | USE_ALL_WIDTH);
		pantalla.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		listStyleButtonFieldDenuncias = new ListStyleButtonField("Hacer Denuncia", Constantes.MEDIUM_NORMAL_FONT, FIELD_HCENTER | FIELD_VCENTER);
		listStyleButtonFieldDenuncias.setChangeListener(this);
		
		listStyleButtonFieldRegistro = new ListStyleButtonField("Registrar", Constantes.MEDIUM_NORMAL_FONT, FIELD_HCENTER | FIELD_VCENTER);
		listStyleButtonFieldRegistro.setChangeListener(this);
		
		pantalla.add(listStyleButtonFieldDenuncias);
		if (!PersistentStorage.isRegistered()) {
			pantalla.add(listStyleButtonFieldRegistro);
		}
	}
	
	/*private static class RegisterMenuItem extends MenuItem {
		
		public RegisterMenuItem() {
			//super(PersistentStorage)
		}

		public void run() {
			// TODO Auto-generated method stub
			
		}
	}*/
	
	private static class RegisterMenuItem extends MenuItem {
		public RegisterMenuItem() {
			super (PersistentStorage.isRegistered() ? "Configurar registro" : "Registrar", SEP_IDX1 + 1, 1);
		}

		public void run() {
			// TODO Auto-generated method stub
			PushController.register();
		}
	}
	
	private static class EnviarDatos extends MenuItem {
		
		public EnviarDatos() {
			super("Enviar Datos", SEP_IDX1 + 2, 1);
		}
		
		public void run() {
			PushMessage message = new PushMessage();
			PushController.onMessage(message);
		}
	}
	
	public void fieldChanged(Field field, int context) {
		if (field == listStyleButtonFieldDenuncias) 
		{
			ScreenEngine.getInstance().goDenuncias();
		}
		if (field == listStyleButtonFieldRegistro) {}
	}
}