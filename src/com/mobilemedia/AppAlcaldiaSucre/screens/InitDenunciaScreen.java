package com.mobilemedia.AppAlcaldiaSucre.screens;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.container.MainScreen;


import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonField;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonSet;
import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;


public class InitDenunciaScreen extends MainScreen implements FieldChangeListener {
	private static InitDenunciaScreen instance;
	
	private static InitDenunciaScreen getInstance() {
		if (instance == null) {
			instance = new InitDenunciaScreen();
		}
		return instance;
	}
	
	private ListStyleButtonField listStyleButtonFieldDenuncias;
	private ListStyleButtonField listStyleButtonFIeldRegistro;
	
	public InitDenunciaScreen () {
		
	}
	
	/*private static class RegisterMenuItem extends MenuItem {
		
		public RegisterMenuItem() {
			//super(PersistentStorage)
		}

		public void run() {
			// TODO Auto-generated method stub
			
		}
	}*/
	
	public void fieldChanged(Field field, int context) {
		if (field == listStyleButtonFieldDenuncias) {}
		if (field == listStyleButtonFIeldRegistro) {}
	}
}