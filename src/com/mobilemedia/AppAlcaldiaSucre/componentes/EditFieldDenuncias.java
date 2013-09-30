package com.mobilemedia.AppAlcaldiaSucre.componentes;

import javax.microedition.lcdui.Font;


import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;

public class EditFieldDenuncias extends BasicEditField
{
	public static int DRAWPOSITION_TOP = 0;
    public static int DRAWPOSITION_BOTTOM = 1;
    public static int DRAWPOSITION_MIDDLE = 2;
    public static int DRAWPOSITION_SINGLE = 3;
    
    protected static final int HPADDING = Display.getWidth() <= 320 ? 6 : 8;
    protected static final int VPADDING = 4;
        
    protected VerticalFieldManager _verticalField;
    protected LabelFieldCustomColor _labelField;
	
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
	
	public EditFieldDenuncias(long style, int numChars, String text) {
		super("", null, numChars, style);
		_verticalField = new VerticalFieldManager(style);
		_labelField = new LabelFieldCustomColor(text, USE_ALL_WIDTH, 0, getFont());
		
		setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		setBackground(BackgroundFactory.createSolidBackground(0xF1F1F1));		
	}
	
	public VerticalFieldManager getVFM() {
		_verticalField.add(_labelField);
		_verticalField.add(this);
		
		return _verticalField;
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
	
	
}