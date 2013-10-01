
package com.mobilemedia.AppAlcaldiaSucre.push;

import com.mobilemedia.AppAlcaldiaSucre.objetos.Usuario;
import com.mobilemedia.AppAlcaldiaSucre.push.PersistentStorage;
import com.mobilemedia.AppAlcaldiaSucre.push.PushConfig;
import com.mobilemedia.AppAlcaldiaSucre.push.lib.ReadableListImpl;
//import com.mobilemedia.AppAlcaldiaSucre.push.PushMessage;

import net.rim.blackberry.api.messagelist.ApplicationMessage;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.util.Persistable;

public class PersistentStorage implements Persistable {
	
	//com.mobilemedia.AppAlcaldiaSucre.push.PersistentStorage 0x7e69425b3f6e8547L
	private static long ID = 0x7e69425b3f6e8547L;
	private static PersistentStorage ps;
	
	 // Total count of received messages
    private int totalMessageCount;
    // Timestamp when the last message was received
    private long lastMessageReceived;

    // Config settings
    private PushConfig config;

    // SIM card info to detect SIM swap event
    private byte[] simCardInfo;
    
    // Save the user name while registered
    private Usuario usuario;
    
    private ReadableListImpl messages;
    
    // Specifies whether the application has been already launched
    // at least once
    private boolean onceLaunched;
    
    // Specifies whether the application has been successfully registered
    // with Content Provider and BPS server
    private boolean registered;

    static {
        PersistentObject po = PersistentStore.getPersistentObject( ID );
        ps = (PersistentStorage) po.getContents();
        if( ps == null ) {
            ps = new PersistentStorage();
            po.setContents( ps );
            po.commit();
        }
    }
    
    private PersistentStorage() {
    	messages = new ReadableListImpl(new PushMessage[0]);
    }
    
    /**
     * Increments message count and commits to the persistent storage. 
     * Also assigns current time to the last received timestamp.
     */
    public static void incTotalMessageCount() {
        ps.totalMessageCount++;
        ps.lastMessageReceived = System.currentTimeMillis();
        PersistentObject.commit( ps );
    }

    /**
     * Returns received message count
     */
    public static int getTotalMessageCount() {
        return ps.totalMessageCount;
    }
    
    /**
     * Set message count
     */
    public static void setTotalMessageCount( int total ) {
    	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PersistentStorage: setTotalMessageCount: (total, ps.messages.size()) =("+total +","+ ps.messages.size() +")" ).getBytes() );
        ps.totalMessageCount = total;
        PersistentObject.commit( ps );
    }

    /**
     * Returns timestamp when the last message was received.
     */
    public static long getLastMessageReceived() {
        return ps.lastMessageReceived;
    }

    public static PushConfig getConfig() {
        return ps.config;
    }

    public static void setConfig( PushConfig config ) {
        ps.config = config;
        PersistentObject.commit( ps );
    }

    public static byte[] getSimCardInfo() {
        return ps.simCardInfo;
    }

    public static void setSimCardInfo( byte[] simCardInfo ) {
        ps.simCardInfo = simCardInfo;
        PersistentObject.commit( ps );
    }
    
    public static Usuario getUsuario() {
        return ps.usuario;
    }

    public static void setUsuario( Usuario u ) {
        ps.usuario = u;
        PersistentObject.commit( ps );
    }
        
    public static PushMessage[] getMessages() {
        return ps.messages.toArray();
    }

    public static void setMessages( PushMessage[] messages ) {
        ps.messages.setPushMessages( messages );
        PersistentObject.commit( ps );
    }
    
    public static boolean isOnceLaunched() {
        return ps.onceLaunched;
    }

    public static void setOnceLaunched( boolean onceLaunched ) {
        ps.onceLaunched = onceLaunched;
        PersistentObject.commit( ps );
    }
    
    public static boolean isRegistered() {
        return ps.registered;
    }

    public static void setRegistered( boolean registered ) {
        ps.registered = registered;
        PersistentObject.commit( ps );
    }

    /**
     * Erases persistent store, used for debug purposes only
     */
    public static void erase() {
        PersistentStore.destroyPersistentObject( ID );
    }

    
    
    
    
    

    public static PersistentStorage getInstance(){
    	return ps;
    }
    
    public static ReadableListImpl getReadableListMessages(){
    	return ps.messages;
    }
    
    public static void deleteMessage(PushMessage m){
    	if ( ps.messages.deleteMessage(m) ){
    		ps.totalMessageCount--;
    		PersistentObject.commit( ps );
    	}
    }
    
    public static int deleteMultipleMessages(ApplicationMessage[] messages){
    	int numMsgs = messages.length, contador = 0;
    	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PersistentStorage: deleteMultipleMessages: numMsgs = "+numMsgs ).getBytes() );
    	for (int i = 0; i < numMsgs; i++){
    		if ( ps.messages.deleteMessage( (PushMessage) messages [i] ) )
    			contador++;
    	}
    	
    	return contador;
    }

	public void commit() {
		// TODO Auto-generated method stub
		PersistentObject.commit( ps );
	}
}