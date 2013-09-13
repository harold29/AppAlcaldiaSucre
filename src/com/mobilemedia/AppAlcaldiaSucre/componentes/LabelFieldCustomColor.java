package com.mobilemedia.AppAlcaldiaSucre.componentes;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.LabelField;

public class LabelFieldCustomColor extends LabelField{
	private int color;
	
	public LabelFieldCustomColor(String text, long style, int color, Font fuente){
		super(text,style);
		this.color = color;
		setFont(fuente);
	}

	public void paint(Graphics graphics) {
		graphics.setColor(color);
		super.paint(graphics);
	}

	public void layout(int width, int height) {
		// TODO Auto-generated method stub
		super.layout(width, height);
	}

	protected void drawFocus(Graphics g, boolean on){
		XYRect rect = new XYRect();
		getFocusRect(rect);
		drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
	}
}