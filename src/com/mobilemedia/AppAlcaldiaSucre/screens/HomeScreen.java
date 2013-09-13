package com.mobilemedia.AppAlcaldiaSucre.screens;

import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonSet;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonHome;
import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;
import com.mobilemedia.AppAlcaldiaSucre.custom.HomeCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.BitmapManager;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class HomeScreen extends MainScreen implements FieldChangeListener {
	private static HomeScreen instance;
	
	public static HomeScreen getInstance() {
		if (instance == null) {
			instance = new HomeScreen();
		}
		return instance;
	}
	
    private ListStyleButtonHome listStyleButtonFieldNoticias;
    private ListStyleButtonHome listStyleButtonFieldAgenda;
    private ListStyleButtonHome listStyleButtonFieldDirectorio;
    private ListStyleButtonHome listStyleButtonFieldDenuncias;
        
    public HomeScreen()
	{

  //       addMenuItem( new RegisterMenuItem() );
 //       addMenuItem( new UnregisterMenuItem() );
 //       addMenuItem( new EnviarPush() );
 //       addMenuItem( new EliminarPersistencia() );
    	
    /**************************************************************************/
    	VerticalFieldManager pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE | NO_VERTICAL_SCROLL);
    	pantalla.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
    	//0x00FCB53E
    	
		ListStyleButtonSet buttonSet = new ListStyleButtonSet();
				
		
		listStyleButtonFieldDenuncias = new ListStyleButtonHome( "Denuncias", Constantes.MEDIUM_NORMAL_FONT, FIELD_HCENTER | FIELD_VCENTER, 1);		
		listStyleButtonFieldDenuncias.setChangeListener(this);
		
		listStyleButtonFieldDirectorio = new ListStyleButtonHome( "Directorio", Constantes.MEDIUM_NORMAL_FONT, 0, 2);
		listStyleButtonFieldDirectorio.setChangeListener(this);		
		
		listStyleButtonFieldAgenda = new ListStyleButtonHome( "Agenda Sucre", Constantes.MEDIUM_NORMAL_FONT, 0,3);
		listStyleButtonFieldAgenda.setChangeListener(this);
		
		listStyleButtonFieldNoticias = new ListStyleButtonHome( "Noticias", Constantes.MEDIUM_NORMAL_FONT, 0, 4);
		listStyleButtonFieldNoticias.setChangeListener(this);
		
		
		buttonSet.add(listStyleButtonFieldDenuncias);
		buttonSet.add(listStyleButtonFieldDirectorio);
		buttonSet.add(listStyleButtonFieldAgenda);
		buttonSet.add(listStyleButtonFieldNoticias);
		
		BitmapField bf = new BitmapField(BitmapManager.getInstance().getLogoAlcaldiaSucre() , FIELD_HCENTER | USE_ALL_WIDTH );
		bf.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		bf.setPadding(10, 123, 5, 100);
	//	bf.setPadding(0,0,0,0);
		pantalla.add(bf);
		pantalla.add(buttonSet);

		add(pantalla);
	}
    
    

	public void fieldChanged(Field field, int context) {
		 if( field == listStyleButtonFieldNoticias 	) { ScreenEngine.getInstance().goNoticias(); }	
		 if (field == listStyleButtonFieldDirectorio) { ScreenEngine.getInstance().goDirectorio(); }
	}
	
	public void paintBackground (Graphics g) {
		g.setColor(0xf1f1f1);
		super.paintBackground(g);
	}
}

