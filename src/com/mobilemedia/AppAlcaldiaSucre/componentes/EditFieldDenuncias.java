package com.mobilemedia.AppAlcaldiaSucre.componentes;


import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;

import net.rim.device.api.system.Display;
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
    
    protected static final int HPADDING = Display.getWidth() <= 320 ? 8 : 10;
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
	
	
	public EditFieldDenuncias(long style, int numChars, String text, int width) {
		super("", null, numChars, USE_ALL_WIDTH);
		_verticalField = new VerticalFieldManager(USE_ALL_WIDTH);
		_labelField = new LabelFieldCustomColor(text, USE_ALL_WIDTH, 0, Constantes.LARGE_NORMAL_FONT);
		
		setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		setBackground(BackgroundFactory.createSolidBackground(0xF1F1F1));		
		
		_verticalField.add(_labelField);
		_verticalField.add(this);
		_verticalField.setPadding(20, HPADDING + width, 0, 30);
	}
	
	public VerticalFieldManager getVFM() {		
		return _verticalField;
	}
}