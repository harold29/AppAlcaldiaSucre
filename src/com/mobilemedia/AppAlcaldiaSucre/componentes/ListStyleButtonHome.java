package com.mobilemedia.AppAlcaldiaSucre.componentes;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.*;



public class ListStyleButtonHome extends ListStyleButtonField {
	
	protected static int COLOR;
	protected static int FOCUS_COLOR;
	protected static int INDEX;
	
    boolean lowRes = Display.getWidth() <= 320;
	
	public ListStyleButtonHome (String label, Font fuente, long style, int index) {
		super(label, fuente, style);
		_indexButton = index;
	}
	
	public void layout( int width, int height )
    {

		if (lowRes)
			_targetHeight = (getFont().getHeight() / 2 * 3 + 2 * VPADDING) + 10;
		else
			_targetHeight = (getFont().getHeight() / 2 * 3 + 2 * VPADDING) + 20; //Aumentar tamaño igual
		
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
        if (lowRes) 
        	_labelHeight = _labelField.getHeight()+10; //Aumentar tamaño
        else
        	_labelHeight = _labelField.getHeight() + 40;
        	
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
	
	protected void paint (Graphics g) {
		try {
			g.pushRegion(_leftOffset, (getHeight() - _labelHeight) / 2, getWidth() - _leftOffset - _rightOffset, _labelHeight, 0, 0);
			if (this.isFocus())
				g.setColor(0xFFFFFF);
			else 
				g.setColor(0x0);
			_labelField.paint( g );
		} finally {
			g.popContext();
		}
	}
	
	protected void paintBackground (Graphics g) {
	     
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
		
       super.paintBackground(g);
    }
}
	
