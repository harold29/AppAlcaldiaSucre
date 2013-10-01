package com.mobilemedia.AppAlcaldiaSucre.push.lib;

import net.rim.device.api.collection.ReadableList;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.util.Persistable;

import com.mobilemedia.AppAlcaldiaSucre.push.PushMessage;

public class ReadableListImpl implements ReadableList, Persistable
{
    private PushMessage[] messages;

    public ReadableListImpl( PushMessage[] msgs) { 
    	messages = msgs; 
    }

	public Object getAt(int index) {
		// TODO Auto-generated method stub
		return messages[index];
	}

	public int getAt(int index, int count, Object[] elements, int destIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getIndex(Object element) {
		// TODO Auto-generated method stub
		int numElem = messages.length, i;
		String msgId = ( (PushMessage) element ).getId();
        PushMessage pushMessage = null;
        
        for( i = numElem - 1; i >= 0; i-- ) {
            if( messages[ i ].getId().equals( msgId ) ) {
                pushMessage = messages[ i ];
                break;
            }
        }
        
		if ( pushMessage == null ) return -1;
		
		return i;
	}

	public int size() {
		// TODO Auto-generated method stub
		return messages.length;
	}
	
	public PushMessage[] toArray(){
		return messages;
	}
	
	public void setPushMessages(PushMessage[] m){
		EventLogger.logEvent(0xd79a2b82d8b05a40L, ("ReadableListImpl: setPushMessages: ANTES: messages.length = " +messages.length ).getBytes() );
		messages = m;
		EventLogger.logEvent(0xd79a2b82d8b05a40L, ("ReadableListImpl: setPushMessages: DESPUES: messages.length = " +messages.length ).getBytes() );
	}

	public boolean deleteMessage(PushMessage m) {
		// TODO Auto-generated method stub
		int numElem = messages.length, iv, in;
		String msgId = m.getId();
		
		EventLogger.logEvent(0xd79a2b82d8b05a40L, ("ReadableListImpl: deleteMessage: numElem =" + numElem ).getBytes() );
		
        PushMessage[] aux = new PushMessage[numElem-1];
        
        iv = 0; in = 0;
        while( iv < numElem ) {
            if( !messages[ iv ].getId().equals( msgId ) ) {
                aux[in] = messages[ iv ];
                in++;
            }
            iv++;
        }
        
		if ( iv-1 == in ){
			messages = aux;
			return true;
		}
		
		return false;
	}
}