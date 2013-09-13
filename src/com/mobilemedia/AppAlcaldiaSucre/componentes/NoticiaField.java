package com.mobilemedia.AppAlcaldiaSucre.componentes;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
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

import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;
import com.mobilemedia.AppAlcaldiaSucre.interfaces.CargaBitmap;
import com.mobilemedia.AppAlcaldiaSucre.objetos.Noticia;
import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.facade.*;


public class NoticiaField extends BaseButtonField implements FieldChangeListener, CargaBitmap{
	
	private static Border bordes = BorderFactory.createSimpleBorder(new XYEdges(1,0,1,0), 
                                                   new XYEdges(0xf9f9f8, 0, 0xe0dfdf, 0), 
                                                   Border.STYLE_SOLID);
	
    private CustomLabelField _labelNoticia, _fecha;
    private NoticiaBitmapField _bitmapNoticia;
    private boolean esPrimero;
    
    private Noticia noticia;
    
    public NoticiaField(Noticia not, boolean esPrimero)
    {
    	super(FIELD_HCENTER);
    	
    	noticia = not;
    	this.esPrimero = esPrimero;
    	_bitmapNoticia = null;
    	_labelNoticia  = new CustomLabelField(noticia.getTitulo(), false);
    	
    	// Si se va mostrar fecha:
    	if (ListaNoticiaCustom.MOSTRAR_FECHA)
    		_fecha = new CustomLabelField(noticia.getFecha() , true);
    	
    	// Si se va a mostrar la IMG
    	if (ListaNoticiaCustom.MOSTRAR_IMG){
    		Bitmap imagen = noticia.getFotoBitmap();
    		
	    	if ( imagen != null )
	    		setBitmap(imagen, 0); // aqui 0 no signifca nada, unicamente cumple la interfaz
	    	else{
	    		FotoSmallFacade _imageFacade;
	    		_imageFacade = new FotoSmallFacade();
	    		_imageFacade.cargarFoto(this.noticia, this, this.noticia.getId());
	    	}
    	}
    		
    	setPadding(ListaNoticiaCustom.PADDING);
    	setChangeListener(this);
    }
    
    public void setBitmap(final Bitmap bitmap, final int indice){
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				synchronized (UiApplication.getEventLock()) {
					Bitmap bitmapEscalado = new Bitmap(ListaNoticiaCustom.WIDTH_IMG, ListaNoticiaCustom.HEIGHT_IMG);
					bitmap.scaleInto(bitmapEscalado, Bitmap.FILTER_BILINEAR, Bitmap.SCALE_TO_FILL);
			    	_bitmapNoticia = new NoticiaBitmapField(bitmapEscalado);
			    	invalidate();
				}
			}
		});
    }
    
	protected void layout(int width, int height) {
		int noticiaWidth = Constantes.WIDTH_DISPONIBLE - ListaNoticiaCustom.PADDING.left - ListaNoticiaCustom.PADDING.right -5; // Sin IMG: OJO:-5
		int alturaTotal;

		if ( ListaNoticiaCustom.MOSTRAR_IMG && ListaNoticiaCustom.ALINEACION_IMG != Constantes.CENTRO)
			noticiaWidth = noticiaWidth - ListaNoticiaCustom.WIDTH_IMG - 3;

		_labelNoticia.layout( noticiaWidth, height);

		if (ListaNoticiaCustom.MOSTRAR_FECHA)
			_fecha.layout(200, ListaNoticiaCustom.FECHA_FONT.getHeight());

		if (ListaNoticiaCustom.MOSTRAR_IMG && _bitmapNoticia != null) 
			_bitmapNoticia.layout(ListaNoticiaCustom.WIDTH_IMG, ListaNoticiaCustom.HEIGHT_IMG);

		if (ListaNoticiaCustom.MOSTRAR_IMG && ListaNoticiaCustom.ALINEACION_IMG == Constantes.CENTRO)
			alturaTotal = _labelNoticia.getHeight() + 
                          ( ListaNoticiaCustom.MOSTRAR_FECHA ? _fecha.getHeight() : 0) + 
                          ListaNoticiaCustom.HEIGHT_IMG + 20;
		else
			alturaTotal = Math.max( _labelNoticia.getHeight() + ( ListaNoticiaCustom.MOSTRAR_FECHA ? _fecha.getHeight() : 0), 
                                    (ListaNoticiaCustom.MOSTRAR_IMG ? ListaNoticiaCustom.HEIGHT_IMG : 0 ) ) + 20;

		setExtent( Constantes.WIDTH_DISPONIBLE - ListaNoticiaCustom.PADDING.left - ListaNoticiaCustom.PADDING.right, 
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
		
		paintBg(graphics);
		
		System.out.println("ID: "+ noticia.getId() );
		
// ********************************************************************************************
		if (ListaNoticiaCustom.MOSTRAR_IMG){
			if ( ListaNoticiaCustom.ALINEACION_IMG == Constantes.DERECHA ){
				leftEdgeTexto = 3;
				leftEdgeImg = getContentWidth() - ListaNoticiaCustom.WIDTH_IMG -3;
				topEdgeImg = (getHeight() - ListaNoticiaCustom.HEIGHT_IMG) / 2;
			}else if (ListaNoticiaCustom.ALINEACION_IMG == Constantes.IZQUIERDA){
				leftEdgeTexto = leftEdgeImg + ListaNoticiaCustom.WIDTH_IMG + 10;
				leftEdgeImg = 3; 
				topEdgeImg = (getHeight() - ListaNoticiaCustom.HEIGHT_IMG) / 2;
			}else {	// Centro
				leftEdgeTexto = 3;
				leftEdgeImg = (getContentWidth() - ListaNoticiaCustom.WIDTH_IMG ) /2; 
				topEdgeImg = 3;
			}
		}else{
			leftEdgeTexto = 3;
		}

		// Imagen
		if (ListaNoticiaCustom.MOSTRAR_IMG){
	        try {
	        	  graphics.pushRegion( leftEdgeImg , 
                                       topEdgeImg,
                                       ListaNoticiaCustom.WIDTH_IMG,
                                       ListaNoticiaCustom.HEIGHT_IMG,
                                       0,
                                       0);
	        	  
	          if (_bitmapNoticia != null)
	        	_bitmapNoticia.paint(graphics);
	        } finally {
	        	graphics.popContext();
	        }
		}
// ********************************************************************************************
		if (ListaNoticiaCustom.MOSTRAR_IMG){
			if ( ListaNoticiaCustom.ALINEACION_IMG == Constantes.DERECHA || ListaNoticiaCustom.ALINEACION_IMG == Constantes.IZQUIERDA ){
				topEdgeTexto = ( getHeight() - _labelNoticia.getHeight() ) / 2;
				widthTexto = getContentWidth() - ListaNoticiaCustom.WIDTH_IMG - 3;
			} else {	// Centro
				topEdgeTexto = ListaNoticiaCustom.HEIGHT_IMG + 5 + (ListaNoticiaCustom.MOSTRAR_FECHA ? ListaNoticiaCustom.FECHA_FONT.getHeight() + 5 : 0 );
				widthTexto = getContentWidth();
			}
		}else{
			topEdgeTexto = 3 + (ListaNoticiaCustom.MOSTRAR_FECHA ? ListaNoticiaCustom.FECHA_FONT.getHeight() + 5 : 0 );
			widthTexto = getContentWidth();
		}
        
        // Texto Noticia
        try {
        	graphics.pushRegion( leftEdgeTexto,
                                 topEdgeTexto,
                                 widthTexto,
                                 _labelNoticia.getHeight(), 
                                 0, 
                                 0 );
        	
        	 graphics.setColor(ListaNoticiaCustom.CONTENIDO_COLOR_FONT);
        	 
        	 _labelNoticia.paint( graphics );
          
        } finally {
            graphics.popContext();
        }
        
// ********************************************************************************************
        // Fecha
        if (ListaNoticiaCustom.MOSTRAR_FECHA){
			if (ListaNoticiaCustom.MOSTRAR_IMG){
				if ( ListaNoticiaCustom.ALINEACION_IMG == Constantes.CENTRO || ListaNoticiaCustom.ALINEACION_IMG == Constantes.IZQUIERDA ){
					leftEdgeFecha = getContentWidth() - _fecha.getWidth() - 3;
					topEdgeFecha = ( ListaNoticiaCustom.ALINEACION_IMG == Constantes.CENTRO ? ListaNoticiaCustom.HEIGHT_IMG + 3 : 3);
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
	        	
	        	 graphics.setColor(ListaNoticiaCustom.FECHA_COLOR_FONT);
	        	 _fecha.paint( graphics );
	          
	        } finally {
	            graphics.popContext();
	        }
        }
	}

//	protected void drawFocus(Graphics graphics, boolean on) {
//	}
//	
//	public void focusChangeNotify(int arg0) {
//		invalidate();
//		super.focusChangeNotify(arg0);
//	}

//	public Noticia getNoticia() {
//		return noticia;
//	}
//
//	public void setNoticia(Noticia noticia) {
//		this.noticia = noticia;
//	}

	// ******* Implementacion de los Metodos de la Interfaz: FieldChangeListener *******
	public void fieldChanged(Field field, int context) {
//		Dialog.inform("num Request en cola:" + BBRequestQueue.getInstance().getRequestCount());
		ScreenEngine.getInstance().goDetalleNoticia(noticia);
	}
	// ******* FIN Implementacion de los Metodos de la Interfaz: FieldChangeListener *******
	
	
	// *********************** CLASES ***********************
	
		public class CustomLabelField extends LabelField{
			public CustomLabelField(String label, boolean esFecha) {
				super(label);
			
				if (esFecha) setFont(ListaNoticiaCustom.FECHA_FONT);
				else         setFont(ListaNoticiaCustom.CONTENIDO_FONT);
			}
	        public void layout( int width, int height )
	        {   
	            super.layout( width, height );
	        }
			protected void paint(Graphics graphics) {
				super.paint(graphics);
			}
		}
		
		public class NoticiaBitmapField extends BitmapField{
			public NoticiaBitmapField(Bitmap b){
				super(b);
			}
			public NoticiaBitmapField(){
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