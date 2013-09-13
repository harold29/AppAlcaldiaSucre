//#preprocess

/*
* Copyright (c) 2011 Research In Motion Limited.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.mobilemedia.AppAlcaldiaSucre.componentes;

import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class ListStyleButtonField extends Field
{
    public static int DRAWPOSITION_TOP = 0;
    public static int DRAWPOSITION_BOTTOM = 1;
    public static int DRAWPOSITION_MIDDLE = 2;
    public static int DRAWPOSITION_SINGLE = 3;
    
    private static final int CORNER_RADIUS = 18;
    
    private static final int HPADDING = Display.getWidth() <= 320 ? 6 : 8;
    private static final int VPADDING = 4;
    
    private static int COLOR_BACKGROUND = 0xFFFFFF;
    private static int COLOR_BORDER = 0x00f1f1f1;
    private static int COLOR_BACKGROUND_FOCUS = 0xebebeb;
    
    
    private MyLabelField _labelField;
    private Bitmap _leftIcon;
    private Bitmap _actionIcon;
    
    private int _targetHeight;
    private int _rightOffset;
    private int _leftOffset;
    private int _labelHeight;
    private int _indexButton = 0;
    
    private int _drawPosition = -1;
    
    private int contextChangeListener = 0; // Modificado
	private static Border bordes = BorderFactory.createSimpleBorder(new XYEdges(1,0,1,0), 
                                                   new XYEdges(0xf9f9f8, 0, 0xe0dfdf, 0), 
                                                                      Border.STYLE_SOLID);

    public ListStyleButtonField( String label, Font fuente, long style )
    {
        this( label, fuente, null, style );
    }
    
    // Modificado
    public ListStyleButtonField( String label, Font fuente, Bitmap actionIcon, int contextChangeListener )
    {
        this( label, fuente, actionIcon, (long ) 0 );
        this.contextChangeListener = contextChangeListener;
    }
    
    public ListStyleButtonField( String label, Font fuente, Bitmap actionIcon, long style )
    {
        this( null, label, fuente, actionIcon, style );
    }
    
    public ListStyleButtonField( Bitmap icon, String label, Font fuente, long style )
    {
        this( icon, label, fuente, null, style );
    }
    
    public ListStyleButtonField( Bitmap icon, String label, Font fuente, Bitmap actionIcon, long style)
    {
        super( USE_ALL_WIDTH | Field.FOCUSABLE );
               
        _labelField = new MyLabelField( label, fuente, style );
        _actionIcon = actionIcon;
        _leftIcon = icon;
    }
    
    public ListStyleButtonField( Bitmap icon, String label, Font fuente, Bitmap actionIcon, long style, int index)
    {
        super( USE_ALL_WIDTH | Field.FOCUSABLE );
               
        _labelField = new MyLabelField( label, fuente, style );
        _actionIcon = actionIcon;
        _leftIcon = icon;
        _indexButton = index; 
    }
    
    
    /**
     * DRAWPOSITION_TOP | DRAWPOSITION_BOTTOM | DRAWPOSITION_MIDDLE
	     * Determins how the field is drawn (borders)
	     * If none is set, then no borders are drawn
     */
    public void setDrawPosition( int drawPosition )
    {
        _drawPosition = drawPosition;
    }
    
    public String toString()
    {
        return _labelField.toString();
    }
    
    public void layout( int width, int height )
    {

        _targetHeight = (getFont().getHeight() / 2 * 3 + 2 * VPADDING)+10;
//#ifndef VER_4.6.1 | VER_4.6.0 | VER_4.5.0 | VER_4.2.1 | VER_4.2.0
        if( Touchscreen.isSupported() ) {
            _targetHeight = getFont().getHeight() * 2 + 2 * VPADDING;
        }
//#endif
    	
        _leftOffset = HPADDING;
        if( _leftIcon != null ) {
            _leftOffset += _leftIcon.getWidth() + HPADDING;
        }
        
        _rightOffset = HPADDING;
        if( _actionIcon != null ) {
            _rightOffset += _actionIcon.getWidth() + HPADDING;
        }
        
        _labelField.layout( width - _leftOffset - _rightOffset, height );
        _labelHeight = _labelField.getHeight()+10;
        int labelWidth = _labelField.getWidth();
        
        if( _labelField.isStyle( DrawStyle.HCENTER ) ) {
            _leftOffset = ( width - labelWidth ) / 2;
        } else if ( _labelField.isStyle( DrawStyle.RIGHT ) ) {
            _leftOffset = width - labelWidth - HPADDING - _rightOffset;
        }
        
        int extraVPaddingNeeded = 0;
        if( _labelHeight < _targetHeight ) {
            // Make sure that they are at least 1.5 times font height
            extraVPaddingNeeded =  ( _targetHeight - _labelHeight ) / 2;
        }
        
        setExtent( width, _labelHeight +  2 * extraVPaddingNeeded );
    }
    
    public void setText( String text )
    {
        _labelField.setText( text );
        updateLayout();
    }
    
    protected void paint( Graphics g )
    {
        // Left Bitmap
        if( _leftIcon != null ) {
            g.drawBitmap( HPADDING, VPADDING, _leftIcon.getWidth(), _leftIcon.getHeight(), _leftIcon, 0, 0 );
        }
        
        // Text
        try {
            g.pushRegion( _leftOffset, ( getHeight() - _labelHeight ) / 2, getWidth() - _leftOffset - _rightOffset, _labelHeight, 0, 0 );
            _labelField.paint( g );
        } finally {
            g.popContext();
        }
        
        // Right (Action) Bitmap
        if( _actionIcon != null ) {
            g.drawBitmap( getWidth() - HPADDING - _actionIcon.getWidth(), ( getHeight() - _actionIcon.getHeight() ) / 2, _actionIcon.getWidth(), _actionIcon.getHeight(), _actionIcon, 0, 0 );
        }
    }
    
    protected void paintBackground( Graphics g)
    {

        if( _drawPosition < 0 ) {
            // it's like a list field, let the default background be drawn
            super.paintBackground( g );
            return;
        }
        
        if (_indexButton == 1) {
        	COLOR_BACKGROUND = 0xf68025;
        	COLOR_BORDER = 0xf68025;
        	COLOR_BACKGROUND_FOCUS = 0xb85000;
        } else if (_indexButton == 2) {
        	COLOR_BACKGROUND = 0xf8981c;
        	COLOR_BORDER = 0xf8981c;
        	COLOR_BACKGROUND_FOCUS = 0xc67001;
        } else if (_indexButton == 3) {
        	COLOR_BACKGROUND = 0x6cb33e;
        	COLOR_BORDER = 0x6cb33e;
        	COLOR_BACKGROUND_FOCUS = 0x3b8a08;
        } else if (_indexButton == 4) {
        	COLOR_BACKGROUND = 0x00a94e;
        	COLOR_BORDER = 0x00a94e;
        	COLOR_BACKGROUND_FOCUS = 0x007837;
        }
        
        int oldColour = g.getColor();
        
        int background = g.isDrawingStyleSet( Graphics.DRAWSTYLE_FOCUS ) ? COLOR_BACKGROUND_FOCUS : COLOR_BACKGROUND;
        try {
            if( _drawPosition == 0 ) {
                // Top
                g.setColor( background );
                g.fillRoundRect( 0, 0, getWidth(), getHeight() + CORNER_RADIUS, 0, 0 );
                g.setColor( COLOR_BORDER );
                g.drawRoundRect( 0, 0, getWidth(), getHeight() + CORNER_RADIUS, 0, 0 );
                g.drawLine( 0, getHeight() - 1, getWidth(), getHeight() - 1 );
            } else if( _drawPosition == 1 ) {
                // Bottom 
                g.setColor( background );
                g.fillRoundRect( 0, -CORNER_RADIUS, getWidth(), getHeight() + CORNER_RADIUS, 0, 0 );
                g.setColor( COLOR_BORDER );
                g.drawRoundRect( 0, -CORNER_RADIUS, getWidth(), getHeight() + CORNER_RADIUS, 0, 0 );
            } else if( _drawPosition == 2 ) {
                // Middle
                g.setColor( background );
                g.fillRoundRect( 0, -CORNER_RADIUS, getWidth(), getHeight() + 8 * CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS );
                g.setColor( COLOR_BORDER );
                g.drawRoundRect( 0, -CORNER_RADIUS, getWidth(), getHeight() + 8 * CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS );
                g.drawLine( 0, getHeight() - 1, getWidth(), getHeight() - 1 );
            } else {
                // Single
                g.setColor( background );
                g.fillRoundRect( 0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS );
                g.setColor( COLOR_BORDER );
                g.drawRoundRect( 0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS );
            }
        } finally {
            g.setColor( oldColour );
        }
        
        // Modificado
//        setBorder( bordes );
		// ebebeb
//    	super.setBackground(BackgroundFactory.createBitmapBackground(
//                                     Bitmap.getBitmapResource("Rejilla_Transparente.png"), 
//                                     Background.POSITION_X_LEFT, 
//                                     Background.POSITION_Y_BOTTOM, 
//                                     Background.REPEAT_BOTH    ) 
//                      );
    }
    
    protected void drawFocus( Graphics g, boolean on )
    {
        if( _drawPosition < 0 ) {
            super.drawFocus( g, on );
        } else {
            boolean oldDrawStyleFocus = g.isDrawingStyleSet( Graphics.DRAWSTYLE_FOCUS );
            try {
                if( on ) {
                    g.setDrawingStyle( Graphics.DRAWSTYLE_FOCUS, true );
                }
                paintBackground( g );
                paint( g );
            } finally {
                g.setDrawingStyle( Graphics.DRAWSTYLE_FOCUS, oldDrawStyleFocus );
            }
        }
    }
    
    protected boolean keyChar( char character, int status, int time ) 
    {
        if( character == Characters.ENTER ) {
            clickButton();
            return true;
        }
        return super.keyChar( character, status, time );
    }
    
    protected boolean navigationClick( int status, int time ) 
    {
        if (status != 0) clickButton(); 
        return true;    
    }
    
    protected boolean trackwheelClick( int status, int time )
    {        
        if (status != 0) clickButton();    
        return true;
    }
    
    //#ifndef VER_4.1.0 | 4.0.0
        protected boolean invokeAction( int action ) 
        {
            switch( action ) {
                case ACTION_INVOKE: {
                    clickButton(); 
                    return true;
                }
            }
            return super.invokeAction( action );
        }
    //#endif        
         
    /**
     * A public way to click this button
     */
    public void clickButton() 
    {
        fieldChangeNotify( contextChangeListener );
    }
       
//#ifndef VER_4.6.1 | VER_4.6.0 | VER_4.5.0 | VER_4.2.1 | VER_4.2.0
    protected boolean touchEvent( TouchEvent message )
    {
        int x = message.getX( 1 );
        int y = message.getY( 1 );
        if( x < 0 || y < 0 || x > getExtent().width || y > getExtent().height ) {
            // Outside the field
            return false;
        }
        switch( message.getEvent() ) {
       
            case TouchEvent.UNCLICK:
                clickButton();
                return true;
        }
        return super.touchEvent( message );
    }
//#endif 

    public void setDirty( boolean dirty ) {}
    public void setMuddy( boolean muddy ) {}
    
    
    private static class MyLabelField extends LabelField
    {
        
        public MyLabelField( String text, Font fuente, long style )
        {
            super( text, style );
            setFont(fuente);
        }
    
        public void layout( int width, int height )
        {   
            super.layout( width, height );
        }   
        
        public void paint( Graphics g ) 
        {
        	g.setColor(0x2b2b2b);
            super.paint( g );
        }
    }


	public void setLeftImg(Bitmap bitmapResource) {
		// TODO Auto-generated method stub
		_leftIcon = bitmapResource;
	}
}