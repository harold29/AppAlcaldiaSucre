package com.mobilemedia.AppAlcaldiaSucre.componentes;

import javax.microedition.lcdui.Font;

import com.mobilemedia.AppAlcaldiaSucre.componentes.ListStyleButtonField.MyLabelField;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Touchscreen;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class EditFieldDenuncias extends EditField 
{
	public static int DRAWPOSITION_TOP = 0;
    public static int DRAWPOSITION_BOTTOM = 1;
    public static int DRAWPOSITION_MIDDLE = 2;
    public static int DRAWPOSITION_SINGLE = 3;
    
    protected static final int HPADDING = Display.getWidth() <= 320 ? 6 : 8;
    protected static final int VPADDING = 4;
    
    protected MyLabelField _labelField;
    
    protected VerticalFieldManager _verticalField;
	
	protected static int WIDTH = 100;
	protected static int HEIGHT = 10;
	
	protected static int COLOR_BACKGROUND = 0xFFFFFF;
	protected static int COLOR_BORDER = 0x0;
	
	protected int _targetHeight;
    protected int _rightOffset;
    protected int _leftOffset;
    protected int _labelHeight;
    
    protected int _drawPosition = -1;
	
	/*public EditFieldDenuncias(long style) {
		this(style);
	}*/
	
/*	public EditFieldDenuncias(String label, String initVal) {
		this(label,initVal);
	}*/
	
	public EditFieldDenuncias(String label, String initVal, int maxChar, long Style, int width, int height) {
		super(label, initVal, maxChar, Style);
		WIDTH = width;
		HEIGHT = height;
	}
	
	public EditFieldDenuncias(long style, int _color, int width, int height) {
		super(style);
		WIDTH = width;
		HEIGHT = height;
		COLOR_BACKGROUND = _color;
	}
	
	
	public void layout (int width, int height) {
		setExtent(WIDTH, HEIGHT);
		super.layout(WIDTH, HEIGHT);
	} 
	
	public void paintBackground (Graphics g) {
		if (_drawPosition < 0) {
			super.paintBackground(g);
			return;
		}
		
		int background = COLOR_BACKGROUND;
		try {
			if(_drawPosition == 0) {
				g.setColor(background);
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
				g.setColor(COLOR_BORDER);
				g.drawRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
				g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
			} else if( _drawPosition == 1 ) {
                // Bottom 
                g.setColor( background );
                g.fillRoundRect( 0, 0, getWidth(), getHeight() + 0, 0, 0 );
                g.setColor( COLOR_BORDER );
                g.drawRoundRect( 0, 0, getWidth(), getHeight() + 0, 0, 0 );
            } else if( _drawPosition == 2 ) {
                // Middle
                g.setColor( background );
                g.fillRoundRect( 0, 0, getWidth(), getHeight() + 8 * 0, 0, 0 );
                g.setColor( COLOR_BORDER );
                g.drawRoundRect( 0, 0, getWidth(), getHeight() + 8 * 0, 0, 0 );
                g.drawLine( 0, getHeight() - 1, getWidth(), getHeight() - 1 );
            } else {
                // Single
                g.setColor( background );
                g.fillRoundRect( 0, 0, getWidth(), getHeight(), 0, 0 );
                g.setColor( COLOR_BORDER );
                g.drawRoundRect( 0, 0, getWidth(), getHeight(), 0, 0 );
            }
		} finally {
			g.setColor(background);
		}
	}
	
	public void setText (String text) {
		_labelField.setText(text);
	}
	
	
	
	
}