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
		VerticalFieldManager pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE);
		pantalla.setBackground(BackgroundFactory.createSolidBackground(0xF1F1F1));
		
		ListStyleButtonSet buttonSet = new ListStyleButtonSet();
		
		listStyleButtonFieldDenuncias = new ListStyleButtonField(null, "Realizar Denuncia", 
																Constantes.MEDIUM_NORMAL_FONT, null,
																Field.FIELD_HCENTER, 0);
		listStyleButtonFieldDenuncias.setChangeListener(this);
		
		listStyleButtonFIeldRegistro = new ListStyleButtonField(null, 
																"Registro", 
																Constantes.MEDIUM_NORMAL_FONT,
																null, 
																Field.FIELD_HCENTER,
																0);
		listStyleButtonFIeldRegistro.setChangeListener(this);
		
		buttonSet.add(listStyleButtonFieldDenuncias);
		buttonSet.add(listStyleButtonFIeldRegistro);
		
		pantalla.add(buttonSet);
		add(pantalla);
	}
	
	public void fieldChanged(Field field, int context) {
		if (field == listStyleButtonFieldDenuncias) {}
		if (field == listStyleButtonFIeldRegistro) {}
	}
}