package com.mobilemedia.AppAlcaldiaSucre.engine;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.container.MainScreen;

import com.mobilemedia.AppAlcaldiaSucre.screens.*;
import com.mobilemedia.AppAlcaldiaSucre.objetos.*;

public class ScreenEngine 
{
	/** Se usa el Patron de Diseño: Singleton */
	private static ScreenEngine instance;
	
	public static ScreenEngine getInstance(){
		if (instance == null) 
			instance = new ScreenEngine();
		
		return instance;
	}
	/** FIN -- Se usa el Patron de Diseño: Singleton */
	
	private UiApplication app;
    
    private ScreenEngine(){
        this.app = UiApplication.getUiApplication();
    }
    
    public void activarEfectoVisualSlide(Screen screen){
        // Efectos visuales
		TransitionContext transicionIn, transicionOut;
		
		transicionIn = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
        transicionIn.setIntAttribute(TransitionContext.ATTR_DURATION, 400); 
        transicionIn.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);  
        transicionIn.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);  
        
        transicionOut = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
        transicionOut.setIntAttribute(TransitionContext.ATTR_DURATION, 400);                    
        transicionOut.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_RIGHT);    
        transicionOut.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_OVER);  
        
        Ui.getUiEngineInstance().setTransition(null, screen, UiEngineInstance.TRIGGER_PUSH, transicionIn);
        Ui.getUiEngineInstance().setTransition(screen, null, UiEngineInstance.TRIGGER_POP, transicionOut);
		// FIN: Efectos visuales
    }
    
    public void goHome(){
    	HomeScreen screen = HomeScreen.getInstance();
        app.pushScreen(screen);
    }
    
    public void goDenuncias() {
    	DenunciasScreen screen = DenunciasScreen.getInstance();
    	app.pushScreen(screen);
    }
    
    public void goNoticias(){
    	NoticiasScreen screen = NoticiasScreen.getInstance();
        app.pushScreen(screen);
        //screen.actualizar(false);
    }
    
    public void goDetalleNoticia(Noticia n){
    	DetalleNoticiaScreen screen = DetalleNoticiaScreen.getInstance(n);
        app.pushScreen(screen);
    }
    
    public void goDirectorio() {
    	DirectorioScreen screen = DirectorioScreen.getInstance();
    	app.pushScreen(screen);
    }
    
    public void goDetalleDirectorio(Directorio d) {
    	DetalleDirectorioScreen screen = DetalleDirectorioScreen.getInstance(d);
    	app.pushScreen(screen);
    }
    
    public void goCamera() {
    	CameraScreen screen = CameraScreen.getInstance();
    	app.pushScreen(screen);
    }
    
    public void popScreen() {
    	app.popScreen(app.getActiveScreen());
    }
}