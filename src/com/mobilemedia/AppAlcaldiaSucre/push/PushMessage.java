/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.rim.blackberry.api.messagelist.ApplicationMessage;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.util.Persistable;
import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;
import net.rim.device.api.xml.parsers.ParserConfigurationException;

public class PushMessage implements Persistable, ApplicationMessage {

    private String id;
    private long timestamp;
    private boolean textMesasge;
    private boolean unread;
    private byte[] data;
    
    private String _sender;
    private String _subject;
    private String _message;
    private boolean _deleted;
    private String _replyMessage;
    private long _replyTime;
    
    private String title, url, zone, banner, clickurl, logurl, shareurl, shareprivate;

    public PushMessage( String id, long timestamp, byte[] data, 
    		boolean textMesasge, boolean unread ) {
    	
        super();
        
        this.id = id;
        this.timestamp = timestamp;
        this.textMesasge = textMesasge;
        this.unread = unread;
        this.data = data;
        
        _sender  = "AppGenericaBB";
        _subject = "Notificación AppGenericaBB";
        
        if ( textMesasge ) {
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {           	
				DocumentBuilder builder = factory.newDocumentBuilder();
	            InputStream inputStream = new ByteArrayInputStream( data );
	        	try {
					Document document = builder.parse( inputStream );
					Element rootElement = document.getDocumentElement();
					rootElement.normalize();
					parser (rootElement, 0);
		            		            
				} catch (SAXException e) {
					EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessage: PushMessage(): SAXException").getBytes() );
				} catch (IOException e) {
					EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessage: PushMessage(): IOException").getBytes() );
				}
			} catch (ParserConfigurationException e) {
				EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushMessage: PushMessage(): ParserConfigurationException").getBytes() );
			}
        }
    }
    
    public PushMessage(){ // Para mensajes simulados
    	timestamp = System.currentTimeMillis();
        id = ""+timestamp;
        textMesasge = true;
        unread = true;
        data = "Este es un mensaje Push Simulado".getBytes();
        _sender  = "AppGenericaBB";
        _subject = "Notificación AppGenericaBB";
    	title = "Mensaje Push";
		_message = "Este es un mensaje Push Simulado";
    	url = "www.google.co.ve";
    	zone = "Generic";
    	banner = "www.google.co.ve";
    	clickurl = "www.google.co.ve";
    	logurl = "www.google.co.ve";
    	shareurl = "www.google.co.ve";
    	shareprivate = "www.google.co.ve";
    }

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    
    
    public String get_message() {
		return _message;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getZone() {
		return zone;
	}

	public String getBanner() {
		return banner;
	}

	public String getClickurl() {
		return clickurl;
	}

	public String getLogurl() {
		return logurl;
	}

	public String getShareurl() {
		return shareurl;
	}

	public String getShareprivate() {
		return shareprivate;
	}



	public boolean isTextMesasge() {
        return textMesasge;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread( boolean unread ) {
        this.unread = unread;
    }
    
    public byte[] getData(){
    	return data;
    }
    
    
    // Implementation of ApplicationMessage ------------------------------------
    public static final int DEMO_MESSAGE_TYPE = 0x01;
    
    // All our messages are received, we don't show sent messages
    public static final int BASE_STATUS = ApplicationMessage.Status.INCOMING;
    /**
     * Flag for replied messages. The lower 16 bits are RIM-reserved, so we have
     * to use higher 16 bits.
     */
    public static final int FLAG_REPLIED = 1 << 16;

    /**
     * Flag for deleted messages. The lower 16 bits are RIM-reserved, so we have
     * to use higher 16 bits.
     */
    public static final int FLAG_DELETED = 1 << 17;

    public static final int STATUS_NEW 	= BASE_STATUS | ApplicationMessage.Status.UNOPENED;
    public static final int STATUS_OPENED 	= BASE_STATUS | ApplicationMessage.Status.OPENED;
    public static final int STATUS_REPLIED = BASE_STATUS | ApplicationMessage.Status.OPENED 	| FLAG_REPLIED;
    public static final int STATUS_DELETED = BASE_STATUS | FLAG_DELETED;
    
    /**
     * @see net.rim.blackberry.api.messagelist.ApplicationMessage#getContact()
     */
    public String getContact() { return _sender; }

    /**
     * @see net.rim.blackberry.api.messagelist.ApplicationMessage#getStatus()
     */
    public int getStatus() {
        // Form message list status based on current message state
        if ( isUnread() ) { return STATUS_NEW; }
        if (_deleted) { return STATUS_DELETED; }
        if (_replyMessage != null) { return STATUS_REPLIED; }
        
        return STATUS_OPENED;
    }

    /**
     * 
     * @see net.rim.blackberry.api.messagelist.ApplicationMessage#getSubject()
     */
    public String getSubject() {
        if (_replyMessage != null) {
            return "Re: " + _subject;
        } else {
            return _subject;
        }
    }

    /**
     * @see net.rim.blackberry.api.messagelist.ApplicationMessage#getType()
     */
    public int getType() {
        // All messages have the same type
        return DEMO_MESSAGE_TYPE;
    }

    /**
     * @see net.rim.blackberry.api.messagelist.ApplicationMessage#getPreviewText()
     */
    public String getPreviewText() {
        if (_message == null) {
            return null;
        }

        final StringBuffer buffer = new StringBuffer(_message);

        if (_replyMessage != null) {
            buffer.append(". You replied on ").append(new Date(_replyTime))
                    .append(": ").append(_replyMessage);
        }

        return buffer.length() > 100 ? buffer.toString().substring(0, 100)
                + " ..." : buffer.toString();
    }

    /**
     * @see net.rim.blackberry.api.messagelist.ApplicationMessage#getCookie(int)
     */
    public Object getCookie(final int cookieId) { return null; }

    /**
     * 
     * @see net.rim.blackberry.api.messagelist.ApplicationMessage#getPreviewPicture()
     */
    public Object getPreviewPicture() { return null; }


    // Implementacion del Parser ------------------------------------
    private void parser(final Node node, final int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            final NodeList childNodes = node.getChildNodes();
            final int numChildren = childNodes.getLength();
            final Node firstChild = childNodes.item(0);

            if (numChildren == 1 && firstChild.getNodeType() == Node.TEXT_NODE) {
                if ( node.getNodeName().equals("title") )
                	title = firstChild.getNodeValue();

				else if ( node.getNodeName().equals("text") ) 
					_message = firstChild.getNodeValue();
                
                else if ( node.getNodeName().equals("url") ) 
                	url = firstChild.getNodeValue();
                
                else if ( node.getNodeName().equals("zone") )
                	zone = firstChild.getNodeValue();

                else if ( node.getNodeName().equals("banner") ) 
                	banner = firstChild.getNodeValue();
                
                else if ( node.getNodeName().equals("clickurl") ) 
                	clickurl = firstChild.getNodeValue();
                
                else if ( node.getNodeName().equals("logurl") ) 
                	logurl = firstChild.getNodeValue();

                else if ( node.getNodeName().equals("shareurl") ) 
                	shareurl = firstChild.getNodeValue();
                
                else if ( node.getNodeName().equals("shareprivate") ) 
                	shareprivate = firstChild.getNodeValue();
            } else {
                for (int i = 0; i < numChildren; ++i) {
                    parser(childNodes.item(i), depth + 1);
                }
            }
        } // Sino: es texto
    }



	public String toString() {
		return "PushMessage [_message=" + _message + ", title=" + title
				+ ", url=" + url + ", zone=" + zone + ", banner=" + banner
				+ ", clickurl=" + clickurl + ", logurl=" + logurl
				+ ", shareurl=" + shareurl + ", shareprivate=" + shareprivate
				+ "]";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PushMessage other = (PushMessage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}