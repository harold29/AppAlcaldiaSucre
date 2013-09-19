package com.mobilemedia.AppAlcaldiaSucre.componentes;



import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaDirectorioCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;
import com.mobilemedia.AppAlcaldiaSucre.interfaces.CargaBitmap;
import com.mobilemedia.AppAlcaldiaSucre.objetos.Directorio;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;
import net.rim.device.api.ui.Field;



public class DirectorioField extends BaseButtonField implements FieldChangeListener, CargaBitmap{
	
	private static Border bordes = BorderFactory.createSimpleBorder(new XYEdges(1,0,1,0), 
                                                   new XYEdges(0xf9f9f8, 0, 0xe0dfdf, 0), 
                                                   Border.STYLE_SOLID);
	
    private CustomLabelField _labelDirectorio, _fecha, _numsTlf;
    private DirectorioBitmapField _bitmapDirectorio;
    private boolean esPrimero;
    
    private Directorio directorio;
    
    public DirectorioField(Directorio not, boolean esPrimero)
    {
    	super(FIELD_HCENTER);
    	
    	directorio = not;
    	this.esPrimero = esPrimero;
    	_bitmapDirectorio = null;
    	_labelDirectorio  = new CustomLabelField(directorio.getDependencia(), false);
    	_numsTlf  = new CustomLabelField(directorio.getTelefonos(),false);
    	
    	// Si se va mostrar fecha:
    	if (ListaDirectorioCustom.MOSTRAR_FECHA)
    		_fecha = new CustomLabelField(directorio.getInstitucion() , true);
    		
    	setPadding(ListaDirectorioCustom.PADDING);
    	setChangeListener(this);
    }
    
    public void setBitmap(final Bitmap bitmap, final int indice){
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				synchronized (UiApplication.getEventLock()) {
					Bitmap bitmapEscalado = new Bitmap(ListaDirectorioCustom.WIDTH_IMG, ListaDirectorioCustom.HEIGHT_IMG);
					bitmap.scaleInto(bitmapEscalado, Bitmap.FILTER_BILINEAR, Bitmap.SCALE_TO_FILL);
			    	_bitmapDirectorio = new DirectorioBitmapField(bitmapEscalado);
			    	invalidate();
				}
			}
		});
    }
    
	protected void layout(int width, int height) {
		int directorioWidth = Constantes.WIDTH_DISPONIBLE - ListaDirectorioCustom.PADDING.left - ListaDirectorioCustom.PADDING.right -5; // Sin IMG: OJO:-5
		int alturaTotal;

		if ( ListaDirectorioCustom.MOSTRAR_IMG && ListaDirectorioCustom.ALINEACION_IMG != Constantes.CENTRO)
			directorioWidth = directorioWidth - ListaDirectorioCustom.WIDTH_IMG - 3;

		_labelDirectorio.layout( directorioWidth, height);
		_numsTlf.layout(directorioWidth, height);

		if (ListaDirectorioCustom.MOSTRAR_FECHA)
			_fecha.layout(200, ListaDirectorioCustom.FECHA_FONT.getHeight());

		if (ListaDirectorioCustom.MOSTRAR_IMG && _bitmapDirectorio != null) 
			_bitmapDirectorio.layout(ListaDirectorioCustom.WIDTH_IMG, ListaDirectorioCustom.HEIGHT_IMG);

		if (ListaDirectorioCustom.MOSTRAR_IMG && ListaDirectorioCustom.ALINEACION_IMG == Constantes.CENTRO)
			alturaTotal = _labelDirectorio.getHeight() + 
                          ( ListaDirectorioCustom.MOSTRAR_FECHA ? _fecha.getHeight() : 0) + 
                          ListaDirectorioCustom.HEIGHT_IMG + _numsTlf.getHeight() + 20;
		else
			alturaTotal = Math.max( _labelDirectorio.getHeight() + ( ListaDirectorioCustom.MOSTRAR_FECHA ? _fecha.getHeight() : 0) + _numsTlf.getHeight(), 
                                    (ListaDirectorioCustom.MOSTRAR_IMG ? ListaDirectorioCustom.HEIGHT_IMG : 0 ) ) + 20;

		setExtent( Constantes.WIDTH_DISPONIBLE - ListaDirectorioCustom.PADDING.left - ListaDirectorioCustom.PADDING.right, 
				alturaTotal);
	}

	protected void paintBg(Graphics graphics) {
		if ( esPrimero )
			// e3e3e3, 8d8d8d, dfded9
			setBorder( BorderFactory.createSimpleBorder(new XYEdges(1,0,1,0), 
                                       new XYEdges(0x8d8d8d, 0, 0xe0dfdf, 0), 
                                                           Border.STYLE_SOLID) );
		else 
			setBorder( bordes );
		
		if( isFocus() ){
			setBackground( BackgroundFactory.createSolidBackground(0xebebeb) ); 
		} else
			// ebebeb
			setBackground(BackgroundFactory.createBitmapBackground(
                                         Bitmap.getBitmapResource("Rejilla_Transparente.png"), 
                                         Background.POSITION_X_LEFT, 
                                         Background.POSITION_Y_BOTTOM, 
                                         Background.REPEAT_BOTH    ) 
                          );
	}
	
	protected void paint(Graphics graphics) {
		int leftEdgeImg = -1, leftEdgeTexto = -1, topEdgeImg = -1;
		int topEdgeTexto = -1, widthTexto = -1;
		int leftEdgeFecha = -1, topEdgeFecha = -1;
		int topEdgeTlf = -1, widthTlf = -1, leftEdgeTlf = -1;
		
		paintBg(graphics);
		
		System.out.println("ID: "+ directorio.getInstitucion() );
		
// ********************************************************************************************
		if (ListaDirectorioCustom.MOSTRAR_IMG){
			if ( ListaDirectorioCustom.ALINEACION_IMG == Constantes.DERECHA ){
				leftEdgeTexto = 3;
				leftEdgeImg = getContentWidth() - ListaDirectorioCustom.WIDTH_IMG -3;
				topEdgeImg = (getHeight() - ListaDirectorioCustom.HEIGHT_IMG) / 2;
			}else if (ListaDirectorioCustom.ALINEACION_IMG == Constantes.IZQUIERDA){
				leftEdgeTexto = leftEdgeImg + ListaDirectorioCustom.WIDTH_IMG + 10;
				leftEdgeImg = 3; 
				topEdgeImg = (getHeight() - ListaDirectorioCustom.HEIGHT_IMG) / 2;
			}else {	// Centro
				leftEdgeTexto = 3;
				leftEdgeImg = (getContentWidth() - ListaDirectorioCustom.WIDTH_IMG ) /2; 
				topEdgeImg = 3;
			}
		}else{
			leftEdgeTexto = 3;
		}

		// Imagen
		if (ListaDirectorioCustom.MOSTRAR_IMG){
	        try {
	        	  graphics.pushRegion( leftEdgeImg , 
                                       topEdgeImg,
                                       ListaDirectorioCustom.WIDTH_IMG,
                                       ListaDirectorioCustom.HEIGHT_IMG,
                                       0,
                                       0);
	        	  
	          if (_bitmapDirectorio != null)
	        	_bitmapDirectorio.paint(graphics);
	        } finally {
	        	graphics.popContext();
	        }
		}
// ********************************************************************************************
		if (ListaDirectorioCustom.MOSTRAR_IMG){
			if ( ListaDirectorioCustom.ALINEACION_IMG == Constantes.DERECHA || ListaDirectorioCustom.ALINEACION_IMG == Constantes.IZQUIERDA ){
				topEdgeTexto = ( getHeight() - _labelDirectorio.getHeight() ) / 2;
				widthTexto = getContentWidth() - ListaDirectorioCustom.WIDTH_IMG - 3;
			} else {	// Centro
				topEdgeTexto = ListaDirectorioCustom.HEIGHT_IMG + 5 + (ListaDirectorioCustom.MOSTRAR_FECHA ? ListaDirectorioCustom.FECHA_FONT.getHeight() + 5 : 0 );
				widthTexto = getContentWidth();
			}
		}else{
			topEdgeTexto = 3 + (ListaDirectorioCustom.MOSTRAR_FECHA ? ListaDirectorioCustom.FECHA_FONT.getHeight() + 5 : 0 );
			widthTexto = getContentWidth();
		}
        
        // Texto Directorio
        try {
        	graphics.pushRegion( leftEdgeTexto,
                                 topEdgeTexto,
                                 widthTexto,
                                 _labelDirectorio.getHeight(), 
                                 0, 
                                 0 );
        	
        	 graphics.setColor(ListaDirectorioCustom.CONTENIDO_COLOR_FONT);
        	 
        	 _labelDirectorio.paint( graphics );
          
        } finally {
            graphics.popContext();
        }

//************************************Nums Tlf*****************************************************
        topEdgeTlf = topEdgeTexto + _labelDirectorio.getHeight() + 8;
        widthTlf = getContentWidth();
        
        try {
        	graphics.pushRegion(leftEdgeTlf,
        						topEdgeTlf,
        						widthTlf,
        						_numsTlf.getHeight(),
        						0,
        						0);
        	graphics.setColor(ListaDirectorioCustom.CONTENIDO_COLOR_FONT);
        	_numsTlf.paint( graphics );
        	
        } finally {
        	graphics.popContext();
        }
        
// ********************************************************************************************
        // Fecha
        if (ListaDirectorioCustom.MOSTRAR_FECHA){
			if (ListaDirectorioCustom.MOSTRAR_IMG){
				if ( ListaDirectorioCustom.ALINEACION_IMG == Constantes.CENTRO || ListaDirectorioCustom.ALINEACION_IMG == Constantes.IZQUIERDA ){
					leftEdgeFecha = getContentWidth() - _fecha.getWidth() - 3;
					topEdgeFecha = ( ListaDirectorioCustom.ALINEACION_IMG == Constantes.CENTRO ? ListaDirectorioCustom.HEIGHT_IMG + 3 : 3);
				} else { // Derecha
					leftEdgeFecha = leftEdgeImg - _fecha.getWidth() - 3;
					topEdgeFecha = 3;
				}
			}else{
				leftEdgeFecha = getContentWidth() - _fecha.getWidth() - 3;
				topEdgeFecha = 3;
			}
		
	        try {
	        	graphics.pushRegion( leftEdgeFecha, 
                                     topEdgeFecha,  
                                     200, 
                                     _fecha.getHeight(), 
                                     0, 
                                     0 );
	        	
	        	 graphics.setColor(ListaDirectorioCustom.FECHA_COLOR_FONT);
	        	 _fecha.paint( graphics );
	          
	        } finally {
	            graphics.popContext();
	        }
        }
	}


	// ******* Implementacion de los Metodos de la Interfaz: FieldChangeListener *******
	
	public void fieldChanged(Field field, int context) {
		ScreenEngine.getInstance().goDetalleDirectorio(directorio);
	}
	
	// ******* FIN Implementacion de los Metodos de la Interfaz: FieldChangeListener *******
	
	
	// *********************** CLASES ***********************
	
		public class CustomLabelField extends LabelField{
			public CustomLabelField(String label, boolean esFecha) {
				super(label);
			
				if (esFecha) setFont(ListaDirectorioCustom.FECHA_FONT);
				else         setFont(ListaDirectorioCustom.CONTENIDO_FONT);
			}
	        public void layout( int width, int height )
	        {   
	            super.layout( width, height );
	        }
			protected void paint(Graphics graphics) {
				super.paint(graphics);
			}
		}
		
		public class DirectorioBitmapField extends BitmapField{
			public DirectorioBitmapField(Bitmap b){
				super(b);
			}
			public DirectorioBitmapField(){
				super();
			}
			public void layout( int width, int height )
	        {   
	            super.layout( width, height );
	        }  
			protected void paint(Graphics graphics) {
				super.paint(graphics);
			}
		}
		
	// *********************** FIN CLASES ***********************
}