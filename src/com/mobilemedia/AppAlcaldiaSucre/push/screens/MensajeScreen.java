package com.mobilemedia.AppAlcaldiaSucre.push.screens;

import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.custom.MensajeCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;
import com.mobilemedia.AppAlcaldiaSucre.push.PushMessage;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

/**
 * Screen to display incoming push message
 */
public class MensajeScreen extends MainScreen {

	/** Se usa el Patron de Diseño: Singleton */
	private static MensajeScreen instance;
	
	public static MensajeScreen getInstance(){
		if (instance == null) 
			instance = new MensajeScreen();
		return instance;
	}
	/** FIN -- Se usa el Patron de Diseño: Singleton */
	
    private DateFormat dateFormat;
    private LabelFieldCustomColor titulo, fecha, texto;
    
    public MensajeScreen() {
        dateFormat = new SimpleDateFormat("MMM dd, hh:mm:ss");

        titulo = new LabelFieldCustomColor("Notificación Push", USE_ALL_WIDTH, 
                MensajeCustom.TITULO_FONT_COLOR, 
                MensajeCustom.TITULO_FONT);
        
        fecha = new LabelFieldCustomColor("", USE_ALL_WIDTH, 
                MensajeCustom.FECHA_FONT_COLOR, 
                MensajeCustom.FECHA_FONT);
        
        texto = new LabelFieldCustomColor("", USE_ALL_WIDTH, 
                MensajeCustom.TEXTO_FONT_COLOR, 
                MensajeCustom.TEXTO_FONT);
        
        add( titulo );
        add( fecha );
        add( texto );
        
        getMainManager().setBackground(BackgroundFactory.createBitmapBackground(
        		Bitmap.getBitmapResource("Rejilla_Transparente.png"), 
                Background.POSITION_X_LEFT, 
                Background.POSITION_Y_BOTTOM, 
                Background.REPEAT_BOTH    ) 
                );
        titulo.setBorder( BorderFactory.createSimpleBorder(new XYEdges(0,0,1,0), 
                new XYEdges(0, 0, 0xe0dfdf, 0), 
                Border.STYLE_SOLID) );
        
        fecha.setBorder( BorderFactory.createSimpleBorder(new XYEdges(1,0,1,0), 
                new XYEdges(0xf9f9f8, 0, 0xe0dfdf, 0), 
                Border.STYLE_SOLID) );
        
        texto.setBorder( BorderFactory.createSimpleBorder(new XYEdges(1,0,0,0), 
                new XYEdges(0x8d8d8d, 0, 0, 0), 
                Border.STYLE_SOLID) );
        
        titulo.setPadding(5,5,5,5);
        fecha.setPadding(5,5,5,5);
        texto.setPadding(5, 5, 5, 5);
        
        ScreenEngine.getInstance().activarEfectoVisualSlide(this);
    }

    public void setMessage( PushMessage msg ) {
    	fecha.setText("Recibido: " + dateFormat.formatLocal( msg.getTimestamp() ) );
    	texto.setText( msg.get_message() );
    }
}