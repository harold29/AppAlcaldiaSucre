package com.mobilemedia.AppAlcaldiaSucre.componentes;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.KeypadListener;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.ContextMenu;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.Dialog;


/**
 *  A custom field that allows for scrolling of an image that is larger than the field can display at one time.
 */

public class ScrollableImageField extends BitmapField
{
    private Bitmap _theImage;       //The image to display.
    private int _preferredHeight;   //The height the field request.
    private int _preferredWidth;    //The width the field requests.
    
    private int _xCoord = 0;         //The x coordinate for the top left corner of the image.
    private int _yCoord = 0;         //The y coordinate for the top left corner of the image.
    
    private int _xTouch = 0;		//The x coordinate of the previous touch point.
    private int _yTouch = 0;		//The y coordinate of the previous touch point.
    
    private static final int[] COLOURS = {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK};  //Colours used to draw the scroll arrows.
    
    private static final int TRIANGLE_SIZE = 6;     //The scroll arrow size.
    private static final int TRIANGLE_OFFSET = 1;   //The scroll arrow offset from the edge of the focus bar.
    
//    private MenuItem _helpMenu;         //The help MenuItem for this field.
    
    private boolean topTr = true, bottomTr = true, leftTr = true, rightTr = true; 
    
    public ScrollableImageField(){ super();	}
    
    public ScrollableImageField(Bitmap theImage) 
    {    
        super(theImage, BitmapField.FOCUSABLE);
        _theImage = theImage;
        calculateSize();
        
        //Initialize the help MenuItem.
//        _helpMenu = new MenuItem("Image Help", 40, 40)
//        {
//            public void run()
//            {
//                Dialog.alert("Scroll the trackwheel to view the image.  Scroll to the end of the image or hold down Alt while scrolling to leave the field.");
//            }
//        };  
    }
    
    public ScrollableImageField(EncodedImage theImage)
    {
        this(theImage.getBitmap());
    }
    
    //Calculates the preferred width and height of the field and determines if the image should scroll. 
    private void calculateSize()
    {
        //Set the preferred height to the image size or screen height if the image is larger than the screen height.
        if (_theImage.getHeight() > Display.getHeight())
        {
            _preferredHeight = Display.getHeight();
        }
        else
        {
            _preferredHeight = _theImage.getHeight();
        }
        
        //Set the preferred width to the image size or screen width if the image is larger than the screen width.
        if (_theImage.getWidth() > Display.getWidth())
        {
            _preferredWidth = Display.getWidth();
        }
        else
        {
            _preferredWidth = _theImage.getWidth();
        }
        
        // Para que el foco este en el centro de la img
        _xCoord = (_theImage.getWidth() - getWidth()) / 2;
        _yCoord = (_theImage.getHeight() - getHeight()) / 2;
        
//        invalidate();
    }
    
    //Override setBitmap to perform new size calculations.
    public void setBitmap(Bitmap bitmap)
    {
        super.setBitmap(bitmap);
        
        //Reset the image location to display the top left of the image.
//        _xCoord = 0;
//        _yCoord = 0;
        
        _theImage = bitmap;
        calculateSize();
    }
    
    //Override setImage to perform new size calculations.
    public void setImage(EncodedImage image)
    {
        _theImage = image.getBitmap();
        //Reset the image location to display the top left of the image.
        
//        _xCoord = 0;
//        _yCoord = 0;
        
        this.setBitmap(_theImage);
        calculateSize();
    }
    
    public int getPreferredHeight()
    {
        return _preferredHeight;
    }
    
    public int getPreferredWidth()
    {
        return _preferredWidth;
    }
    
    protected void paint(Graphics graphics)
    {
        //Get the actual field size from its manager.
        XYRect rect = this.getManager().getContentRect();        
        
        //Draw the Bitmap, taking up the full size of the BitmapField.
        //Start drawing the bitmap at the current coordinates.        
        graphics.drawBitmap(0, 0, rect.width, rect.height, _theImage, _xCoord, _yCoord);
    }
    
    protected void drawFocus(Graphics graphics, boolean on)
    {
        if (on)
        {
            //Get the field size that is available from its manager.
            XYRect rect = this.getManager().getContentRect();
            graphics.setColor(Color.BLUE);
            
            int width = rect.width; 
            int height = rect.height;
            
//            Dialog.inform("w, h "+ width +" "+ height + " wi, hi " + _theImage.getWidth() + " " + _theImage.getHeight());
//            Dialog.inform("wp, hp "+ _preferredWidth +" "+ _preferredHeight );
            
            //The manager may allocate more space than we need.
            //Make sure our dimensions are not greater than the image's.
            if (width > _theImage.getWidth())
            {
                width = _theImage.getWidth();
            }
            
            if (height > _theImage.getHeight())
            {
                height = _theImage.getHeight();
            }            
/*            
            //Draw a border around the field.
            graphics.fillRect(0, 0, 8, height);     	//Left border
            graphics.fillRect(0, 0, width, 8);      	//Top border
            graphics.fillRect(width - 8, 0, 8, height); //Right border
            graphics.fillRect(0, height - 8, width, 8); //Bottom border
  */          
            
            //Determine if the image is larger than the displayed area..
            if (width < _theImage.getWidth() || height < _theImage.getHeight())
            {
                //It is, draw scrolling indicators.
                //All point paths start at the inner tip of the triangle and return back to it.
                int centerWidth = width / 2;
                int centerHeight = height / 2;
                
                //Draw the top triangle.
                int[] xPts = {centerWidth, centerWidth + TRIANGLE_SIZE, centerWidth - TRIANGLE_SIZE, centerWidth};
                int[] yPts = {TRIANGLE_OFFSET, TRIANGLE_OFFSET + TRIANGLE_SIZE, TRIANGLE_OFFSET + TRIANGLE_SIZE, TRIANGLE_OFFSET};
                
                if ( topTr )
                	graphics.drawShadedFilledPath(xPts, yPts, null, COLOURS, null);
                
                //Draw the bottom arrow.
                yPts[0] = height - TRIANGLE_OFFSET;
                yPts[1] = height - TRIANGLE_SIZE;
                yPts[2] = height - TRIANGLE_SIZE;
                yPts[3] = height - TRIANGLE_OFFSET;
                
                if (bottomTr)
                	graphics.drawShadedFilledPath(xPts, yPts, null, COLOURS, null);
                
                //Draw the left arrow.
                xPts[0] = TRIANGLE_OFFSET;
                xPts[1] = TRIANGLE_OFFSET + TRIANGLE_SIZE;
                xPts[2] = TRIANGLE_OFFSET + TRIANGLE_SIZE;
                xPts[3] = TRIANGLE_OFFSET;
                
                yPts[0] = centerHeight;
                yPts[1] = centerHeight + TRIANGLE_SIZE;
                yPts[2] = centerHeight - TRIANGLE_SIZE;
                yPts[3] = centerHeight;
                
                if (leftTr)
                	graphics.drawShadedFilledPath(xPts, yPts, null, COLOURS, null);
                
                //Draw the right arrow.
                xPts[0] = width - TRIANGLE_OFFSET;
                xPts[1] = width - TRIANGLE_SIZE;
                xPts[2] = width - TRIANGLE_SIZE;
                xPts[3] = width - TRIANGLE_OFFSET;
                
                if (rightTr)
                	graphics.drawShadedFilledPath(xPts, yPts, null, COLOURS, null);
            }
        }
    }
    
    //Override navigationMovement to provide image scrolling.
    protected boolean navigationMovement(int dx, int dy, int status, int time)
    {
        //Alt + scroll bypasses scrolling within the field.
        if ((status & KeypadListener.STATUS_ALT) != 0)
        {
            return super.navigationMovement(dx, dy, status, time);
        }
        
        if (scrollImage( dx * 5, dy * 5))
        {
        	//The image was scrolled and the movement consumed.
            return true;
        }
        else
        {
        	//Scrolling is not required.
            return super.navigationMovement(dx, dy, status, time);
        }       
    }
    
    
  	//Override touchEvent to provide image scrolling for touch screen devices.
    protected boolean touchEvent(TouchEvent touchEvent)
    {
    	int event = touchEvent.getEvent();
    	
    	//Populate the start coordinates of the touch event.
    	if (event == TouchEvent.DOWN && touchEvent.isValid())
    	{
    		_xTouch = touchEvent.getX(1);
			_yTouch = touchEvent.getY(1);
    	}
    	else if (event == TouchEvent.MOVE && touchEvent.isValid())
    	{
    		
			boolean result = scrollImage((touchEvent.getX(1) - _xTouch), (touchEvent.getY(1) - _yTouch));
			
			_xTouch = touchEvent.getX(1);
			_yTouch = touchEvent.getY(1);
			
			//If scrolling occurred, consume the touch event.
			if (result)
			{
				return true;
			}
			else
			{
				return false;
			}
    	}
    	
    	//The touch event was not consumed.
    	return false;
    }

    
    //Determines if scrolling is required and handles the positioning of the image 
    //when scrolling occurs.  Returns true if the image was scrolled.
    private boolean scrollImage(int xScroll, int yScroll)
    {
        //Get the actual field size from its manager.
        XYRect rect = this.getManager().getContentRect();
        
        //Determine if the image is larger than the field.
        if (rect.width < _theImage.getWidth() || rect.height < _theImage.getHeight())
        {
            //Image is larger than the field.  Enable scrolling support.
            _xCoord += xScroll;
            _yCoord += yScroll;
            
            //If the user has scrolled to the end of the image, use the default
            //navigationMovement to allow focus to scroll off the field.
            if (_xCoord < 0  || _yCoord < 0  || 
                    ((_theImage.getWidth() - _xCoord) < rect.width) ||  
                    ((_theImage.getHeight() - _yCoord) < rect.height))
            {
                //Ensure the coordinates do not go lower than 0.
                if (_xCoord < 0)
                {
                    _xCoord = 0;
                    leftTr = false;
                }
                
                if (_yCoord < 0)
                {
                    _yCoord = 0;
                    topTr = false;
                }
                
                //Ensure that we don't scroll beyond the image size (causing white space to be drawn).
                if ((_theImage.getWidth() - _xCoord) < rect.width)
                {
                    _xCoord = _theImage.getWidth() - rect.width;
                    rightTr = false;
                }
                
                if ((_theImage.getHeight() - _yCoord) < rect.height)
                {
                    _yCoord = _theImage.getHeight() - rect.height;
                    bottomTr = false;
                }
                //Scrolling is not required.
                return false;
            }
            else
            {
                //The image was scrolled and the movement consumed.
                //Redraw the bitmap.
            	topTr = true; rightTr = true; bottomTr = true; leftTr = true;
                this.invalidate();
                return true;
            }
        }
        else
        {
            //Scrolling is not required.
            return false;
        }    	
    }
    
    //Override makeContextMenu to add the field's help MenuItem.
//    protected void makeContextMenu(ContextMenu contextMenu)
//    {
//        contextMenu.addItem(_helpMenu);
//    }
} 
