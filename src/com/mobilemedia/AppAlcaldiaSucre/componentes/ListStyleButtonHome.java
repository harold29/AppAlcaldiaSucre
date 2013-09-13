package com.mobilemedia.AppAlcaldiaSucre.componentes;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.*;



public class ListStyleButtonHome extends ListStyleButtonField {
	
	protected static int COLOR;
	protected static int FOCUS_COLOR;
	protected static int INDEX;
	
	public ListStyleButtonHome (String label, Font fuente, long style, int index) {
		super(label, fuente, style);
		_indexButton = index;
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
	
