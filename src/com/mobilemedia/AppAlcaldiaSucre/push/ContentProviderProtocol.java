/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push;

import java.io.IOException;

import com.mobilemedia.AppAlcaldiaSucre.objetos.Usuario;
import com.mobilemedia.AppAlcaldiaSucre.facade.StringFacade;
import com.mobilemedia.AppAlcaldiaSucre.push.comandos.NetworkCommand.Transaction;
import com.mobilemedia.AppAlcaldiaSucre.push.lib.URLUTF8Encoder;

import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.Display;

/* Ejemplo Url MobileMediaNetworks:
 * 
 * REGISTRO:
 * http://www.mobilemediapush.com/Registro_Portalesv4/modules/Register/registration.php?
 * pin=27a525df
 * &c=masucre%40hotmail.com
 * &t=04141330820
 * &nac=406823510334
 * &country=Venezuela
 * &state=Distrito+Capital
 * &gender=1
 * &media=Millonarios
 * &source=OTA
 * &maxdaily=1
 * &z1=Noticias
 * &id=0
 * &status=true
 * &os=7.1.0.275
 * &brand=Research+In+Motion
 * &device=9900
 * &version=1.0.0
 * &width=300
 * &height=200
 * 
 * DES-REGISTRO:
 * http://www.mobilemediapush.com/Registro_Portalesv4/modules/Register/registration.php?
 * c=correo
 * &id=id_dado_por_sistema
 * &status=false
 * &country=pais
 */

/**
 * Provides registration functions with Content Provider server using Framework library.
 * <p>
 * Performs network commands in a dedicated thread so the caller is not blocked and may be executed from the UI application event
 * thread.
 */
public class ContentProviderProtocol {

    /**
     * Command to perform user subscription
     */
    public static final int CMD_SUBSCRIBE = 0;
    /**
     * Command to un-subscribe user
     */
    public static final int CMD_UNSUBSCRIBE = 1;
    /**
     * Command to suspend user subscription
     */
    public static final int CMD_SUSPEND = 2;
    /**
     * Command to resume user subscription
     */
    public static final int CMD_RESUME = 3;

    // List of Content Provider & Framework server expected parameters
    private static final String PIN_PARAM 		= "pin=";
    private static final String C_PARAM 		= "&c=";
    private static final String T_PARAM 		= "&t=";
    private static final String NAC_PARAM 		= "&nac=";
    private static final String COUNTRY_PARAM 	= "&country=";
    private static final String STATE_PARAM 	= "&state=";
    private static final String GENDER_PARAM 	= "&gender=";
    private static final String MEDIA_PARAM 	= "&media=";
    private static final String SOURCE_PARAM 	= "&source=";
    private static final String MAXDAILY_PARAM 	= "&maxdaily=";
    private static final String Z_PARAM 		= "&z";
    private static final String ID_PARAM 		= "&id=";
    private static final String STATUS_PARAM 	= "&status=";
    private static final String OS_PARAM 		= "&os=";
    private static final String BRAND_PARAM 	= "&brand=";
    private static final String DEVICE_PARAM 	= "&device=";
    private static final String VERSION_PARAM 	= "&version=";
    private static final String WIDTH_PARAM 	= "&width=";
    private static final String HEIGHT_PARAM 	= "&height=";

    // List of URLs supported by Content Provider and Frameworks server
    private static final String SUBSCRIBE_URL 	= "/Registro_Portalesv4/modules/Register/registration.php?";
    private static final String UNSUBSCRIBE_URL = "/Registro_Portalesv4/modules/Register/registration.php?";
    private static final String SUSPEND_URL 	= "/suspend?";
    private static final String RESUME_URL 		= "/resume?";
    
    /**
     * Performs a command specified by one of the CMD_XXX constants
     */
    public static void performCommand( int cmd, 
            final boolean isEnterprise, Transaction tx ) throws IOException {
    	
        Usuario u = PersistentStorage.getUsuario();
        String url = PushConfig.getContentProviderUrl();
        
        switch( cmd ) {
            case CMD_SUBSCRIBE:
                url += SUBSCRIBE_URL;
                
                url += PIN_PARAM 		+ Integer.toHexString( DeviceInfo.getDeviceId() );
                url += C_PARAM 			+ URLUTF8Encoder.encode( u.getCorreo() );
                url += T_PARAM 			+ URLUTF8Encoder.encode( u.getTelefono() );
                url += NAC_PARAM 		+ u.getFechaNac();
                url += COUNTRY_PARAM 	+ URLUTF8Encoder.encode( u.getPais() );
                url += STATE_PARAM 		+ URLUTF8Encoder.encode( u.getEstado() );
                url += GENDER_PARAM 	+ u.getGenero();
                url += MEDIA_PARAM 		+ PushConfig.Medio;
                url += SOURCE_PARAM 	+ PushConfig.Source;
                url += MAXDAILY_PARAM 	+ u.getMaxDaily();
                
                // Zonas:
                String[] zonas = u.getZonas();
                int numZonas = zonas.length;
                if  (numZonas > 0 )
                	for ( int i = 1; i <= numZonas; i++ )
                		url += Z_PARAM + i +"="+ URLUTF8Encoder.encode( zonas[i-1] );
                else
                	url += Z_PARAM +"1="+ "Generic";
                // Fin Zonas              
                
                url += ID_PARAM 		+ u.getId();
                url += STATUS_PARAM 	+ true; //Registro
                url += OS_PARAM 		+ DeviceInfo.getSoftwareVersion();
                url += BRAND_PARAM 		+ URLUTF8Encoder.encode( DeviceInfo.getManufacturerName() );
                url += DEVICE_PARAM 	+ URLUTF8Encoder.encode( DeviceInfo.getDeviceName() );
                url += VERSION_PARAM 	+ "1.0.0";
                url += WIDTH_PARAM 		+ Display.getWidth();
                url += HEIGHT_PARAM 	+ Display.getHeight();
                
                break;
            case CMD_UNSUBSCRIBE:
                url += UNSUBSCRIBE_URL;
                
                url += "c="				+ URLUTF8Encoder.encode( u.getCorreo() );
                url += ID_PARAM 		+ u.getId();
                url += STATUS_PARAM 	+ false; //Des-registro
                url += COUNTRY_PARAM 	+ URLUTF8Encoder.encode( u.getPais() );
                
                break;
            case CMD_SUSPEND: // No es soportado, aun, por Mobile Media
                url += SUSPEND_URL;
                break;
            case CMD_RESUME: // No es soportado, aun,  por Mobile Media
                url += RESUME_URL;
                break;
            default:
                return;
        }
        
        StringFacade stringFacade = new StringFacade();
        
        stringFacade.setListener(new StringHandler());
        stringFacade.getString(url);
        
    }
    
	private static class StringHandler implements StringFacade.Listener {
		public void onGetStringComplete(String s){			
			try {
				long id = Long.parseLong(s);
				Usuario u = PersistentStorage.getUsuario();
				u.setId(id);
//				u.setStatus(true);
				PersistentStorage.setUsuario( u );
			}catch (NumberFormatException e) { }
		}

		public void onGetStringFailed() {}
	}
}