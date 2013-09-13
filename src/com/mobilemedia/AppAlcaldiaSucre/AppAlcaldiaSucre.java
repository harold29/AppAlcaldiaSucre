package com.mobilemedia.AppAlcaldiaSucre;

import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;

import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class AppAlcaldiaSucre extends UiApplication
{
	
	private static AppAlcaldiaSucre instance;
	
	public static AppAlcaldiaSucre getInstance() {
		if (instance == null)
			instance = new AppAlcaldiaSucre();
		return instance;
	}
	
	private static boolean seMostroScreen = false;
	//com.mobilemedia.AppAlcaldiaSucre.mainEventLoggerID
	public static long EventLoggerID = 0x64f2fd5a3c6fc5e7L;
	 
    public static void main(String[] args)
    {
    	EventLogger.register(EventLoggerID, ".AppAlcaldiaSucre",EventLogger.VIEWER_STRING);
    	EventLogger.logEvent(EventLoggerID, "INICIO".getBytes());
    	
    	if (args!=null && args.length > 0) {
    		EventLogger.logEvent(EventLoggerID, "No por icono".getBytes());
    	} else {
    		System.out.println("HOLA");
    		EventLogger.logEvent(EventLoggerID, "Inicio por icono".getBytes());
    		final AppAlcaldiaSucre theApp = AppAlcaldiaSucre.getInstance();  
    	
    		theApp.showGui();
    		theApp.enterEventDispatcher();
    	}
    }
    

    /**
     * Creates a new MyApp object
     */
    public AppAlcaldiaSucre()
    {        
        // Push a screen onto the UI stack for rendering.
  //      pushScreen(new MyScreen());
    }    
    
    public void showGui() {
    	ScreenEngine.getInstance().goHome();
    }
}
