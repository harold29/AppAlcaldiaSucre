package com.mobilemedia.AppAlcaldiaSucre.componentes;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.extension.component.PictureScrollField;

public class PictureScrollFieldCustom extends PictureScrollField
{
	private boolean paint_left, paint_right;
	private int numImgs;
	private Bitmap flechaIzq, flechaDer;
//	private int width, height;
	
	public PictureScrollFieldCustom(int w, int h, 
                                    String srcFlechaIzquierda, 
                                    String srcFlechaDerecha,
                                    boolean mostrarRejilla) { 
		super (w, h);
		paint_right = true;
		flechaIzq = Bitmap.getBitmapResource(srcFlechaIzquierda);
		flechaDer = Bitmap.getBitmapResource(srcFlechaDerecha);
		
//		width = w; height = h;
		
		setHighlightStyle(HighlightStyle.ILLUMINATE_WITH_SHRINK_LENS);
		setLabelSliderPadding(0);
		setPaddingImageTop(10);
		setLabelsVisible(false);
		
		if (mostrarRejilla)
		// ebebeb
			setBackground(BackgroundFactory.createBitmapBackground(
                                     Bitmap.getBitmapResource("Rejilla_Transparente.png"), 
                                     Background.POSITION_X_LEFT, 
                                     Background.POSITION_Y_BOTTOM, 
                                     Background.REPEAT_BOTH    ) 
                      );
		else
	        setBackground(BackgroundFactory.createSolidTransparentBackground( 0xFFFFFF, 0) );
	}
	
	public void setNumImgs(int num){ 
		numImgs = num;
		verificarFlechas();
	}

    public void verificarFlechas(){
    	paint_left = true; paint_right = true;
    	
        if (getCurrentImageIndex() == 0){
            paint_left = false;
        }
        
        if (getCurrentImageIndex() == numImgs - 1){
             paint_right = false;
        }
    }
    
    public void paint(Graphics graphics) {
        super.paint(graphics);
        
        if (paint_left){
        	graphics.drawBitmap( 5,
                                 ( getHeight() - flechaIzq.getHeight() ) /2, 
                                 flechaIzq.getWidth(), 
                                 flechaIzq.getHeight(), 
                                 flechaIzq, 
                                 0, 
                                 0);
         }
        
        if (paint_right){
        	graphics.drawBitmap( getWidth() - flechaDer.getWidth() - 5, 
                                 (getHeight() - flechaDer.getHeight()) / 2, 
                                 flechaDer.getWidth(), 
                                 flechaDer.getHeight(), 
                                 flechaDer, 
                                 0, 
                                 0);
        }
    };
    
    protected boolean keyChar( char character, int status, int time ) 
     {
        if( character == Characters.ENTER ) {
            FieldChangeListener listener = getChangeListener();
            // avisa que se dio click
            if (listener != null)  
                listener.fieldChanged(this, 1);
            return true;
        }
        
        return super.keyChar( character, status, time );
    }
     
    protected boolean navigationClick( int status, int time ) 
    {
    	if (isFocus()){
            FieldChangeListener listener = getChangeListener();
            // avisa que se dio click
            if (listener != null)  
                listener.fieldChanged(this, 1);
            return true;
    	}
    	
        return super.navigationClick(status, time);  
    }
    
    protected boolean trackwheelClick( int status, int time )
     {        
        FieldChangeListener listener = getChangeListener();
        // avisa que se dio click
        if (listener != null)  
            listener.fieldChanged(this, 1);
        return true;
    }

    protected boolean touchEvent(TouchEvent message) {  	
    	// Si el usuario dejo de presionar la pantalla
        if ( TouchEvent.UNCLICK == message.getEvent() ) {
            FieldChangeListener listener = getChangeListener();
            // avisa que se dio click
            if (listener != null)  
                listener.fieldChanged(this, 1);
            return true;
        }
        
        return super.touchEvent(message);
    }

	public void layout(int width, int height) {
		// TODO Auto-generated method stub
//		setExtent(this.width, this.height);
		super.layout(width, height);
	}

//	public void setDimensiones(int w, int h){
//		width = w; height = h;
//	}
}