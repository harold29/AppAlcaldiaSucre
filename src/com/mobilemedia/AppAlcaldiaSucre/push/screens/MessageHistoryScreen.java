package com.mobilemedia.AppAlcaldiaSucre.push.screens;

import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonField;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonSet;
import com.mobilemedia.AppAlcaldiaSucre.custom.InboxCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;
import com.mobilemedia.AppAlcaldiaSucre.push.PushController;
import com.mobilemedia.AppAlcaldiaSucre.push.PushMessage;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class MessageHistoryScreen extends MainScreen implements FieldChangeListener {

	/** Se usa el Patron de Diseño: Singleton */
	private static MessageHistoryScreen instance;
	
	public static MessageHistoryScreen getInstance(){
		if (instance == null) 
			instance = new MessageHistoryScreen();
		return instance;
	}
	/** FIN -- Se usa el Patron de Diseño: Singleton */
	
    private DateFormat dateFormat;
    private ListStyleButtonField[] items;
    private ListStyleButtonSet bset;
    private LabelFieldCustomColor titulo, vacio;

    private String[] ids;
    
    private static Bitmap _caret = Bitmap.getBitmapResource( "flechaDer.png" );

    public MessageHistoryScreen() {
        dateFormat = new SimpleDateFormat("MMM dd, hh:mm:ss");
        bset = new ListStyleButtonSet();
        
        titulo = new LabelFieldCustomColor("Notificaciones Recibidas", USE_ALL_WIDTH, 
        		InboxCustom.TITULO_FONT_COLOR, 
        		InboxCustom.TITULO_FONT);
        
        vacio = new LabelFieldCustomColor("** Vacio **", 
        		Field.USE_ALL_WIDTH | DrawStyle.HCENTER, 
        		InboxCustom.VACIO_FONT_COLOR, 
        		InboxCustom.VACIO_FONT);

        vacio.setPadding(20,1,1,1);
        
        addMenuItem( new ClearHistoryMenuItem() );
        add(titulo);
        add(bset);
        
        titulo.setBorder( BorderFactory.createSimpleBorder(new XYEdges(0,0,1,0), 
                new XYEdges(0, 0, 0x8d8d8d, 0), 
                Border.STYLE_SOLID) );
        
        ScreenEngine.getInstance().activarEfectoVisualSlide(this);
    }

    public void populate( PushMessage[] messages ) {
        int length = messages.length;
        PushMessage pushMessage;
        String text;
        
        bset.deleteAll();
        
        if (length == 0){
        	try { add(vacio); } catch (IllegalStateException e) {}
        	return;
        }else
        	try { delete(vacio); } catch (IllegalArgumentException e) {}
        
        ids 	= new String[ length ];
        items 	= new ListStyleButtonField[ length ];

        for( int i = 0; i < length; i++ ) {
            // Se comienza desde el ultimo (mas reciente), para que este de primero
        	pushMessage = messages[ length - i - 1 ];
            text = pushMessage.get_message();;
            
            if( text.length() > 17 ) {
            	text = text.substring( 0, 17 ) + "...";
            }

            ids[ i ] 	= pushMessage.getId();
            items[ i ] 	= new ListStyleButtonField( dateFormat.formatLocal( pushMessage.getTimestamp() ) + ": " + text, 
                          pushMessage.isUnread() ? InboxCustom.NO_LEIDO_FONT : InboxCustom.LEIDO_FONT , 
                          _caret, 
                          i );
        	bset.add( items[i] );
        	items[i].setChangeListener(this);
        }
    }

    // ************************ MENU ************************
    private static class ClearHistoryMenuItem extends MenuItem {
        
        public ClearHistoryMenuItem() {
            super( "Eliminar todos", 0x00010000 + 1, 1 );
        }
        
        public void run() {
            PushController.clearMessageHistory();
        }
    }
    // ************************ FIN:MENU ************************

	public void fieldChanged(Field field, int context) {
		PushController.showMessage( ids[ context ], false );
	}
}
