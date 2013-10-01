/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push;

import com.mobilemedia.AppAlcaldiaSucre.AppAlcaldiaSucre;
import com.mobilemedia.AppAlcaldiaSucre.objetos.Usuario;
import com.mobilemedia.AppAlcaldiaSucre.push.comandos.*;
import com.mobilemedia.AppAlcaldiaSucre.push.lib.PushMessageListener;
import com.mobilemedia.AppAlcaldiaSucre.push.screens.*;
import com.mobilemedia.AppAlcaldiaSucre.screens.HomeScreen;

import net.rim.blackberry.api.messagelist.ApplicationIcon;
import net.rim.blackberry.api.messagelist.ApplicationIndicator;
import net.rim.blackberry.api.messagelist.ApplicationIndicatorRegistry;
import net.rim.blackberry.api.messagelist.ApplicationMessage;

import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

/**
 * Main controller of the push application.
 * <p>
 * The class performs registration actions, analyzes errors and handles state transitions, 
 * uses UI screens to display push messages.
 */
public class PushController {

    private static NetworkCommand currentCommand;
    private static final Object cmdLock = new Object();

    /**
     * Registers current device and application to receive push messages. This method is called 
     * when user selects Register menu item and should be called in the application 
     * event thread.
     */
    public static void register() {
        if( commandInProgress() ) {
            return;
        }

        // check that configuration is set
        if( invalidSettings() ) {
            return;
        }

        if( !NetworkCoverageManager.hasCoverage() ) {
            int retry = Dialog.ask( Dialog.D_YES_NO, "Actualmente no hay cobertura. "
                    + "Desea registrarse automaticamente cuando se alcance señal?" );
            if( retry == Dialog.NO ) {
                // user will register manually when device has coverage
                Dialog.inform( "Por favor, registrese cuando haya cobetura" );
                return;
            }
        }

        // Se solicitan datos al Usuario
        Usuario u = Usuario.getInstance();
        RegistroDialog ucd = RegistroDialog.getInstance( u );
        boolean datosValidos = false;
        int i;
        
        for (i = 0; i < 5 && !datosValidos && ucd.doModal() == Dialog.OK; i++){
        	datosValidos = ucd.esValido();
        }
        		
        if ( datosValidos ){
        	u.setCorreo( ucd.getCorreo() );
        	u.setFechaNac( ucd.getFechaNac() );
        	u.setGenero( ucd.getGenero() );
        	u.setEstado( ucd.getEstado() );
        	u.setZonas( ucd.getZonas() );
        	// store user
        	PersistentStorage.setUsuario( u );
        	// perform the registration
        	RegisterCommand command = new RegisterCommand( PushConfig.isEnterprise() );
        	executeCommand( command );
        } else if ( i == 5 )
        	Dialog.inform( "Por favor, intente luego");
    }

    /**
     * Unregisters from receiving push messages
     */
    public static void unregister() {
        // check that configuration is set and no command in progress
        if( commandInProgress() || invalidSettings() ) {
            return;
        }
        
        if( !NetworkCoverageManager.hasCoverage() ) {
            int retry = Dialog.ask( Dialog.D_YES_NO, "Actualmente no hay cobertura. "
                    + "¿Desea registrarse automaticamente cuando se alcance señal?" );
            if( retry == Dialog.NO ) {
                // user will register manually when device has coverage
                Dialog.inform( "Por favor, registrese cuando haya cobetura" );
                return;
            }
        }
        
        int confirmacion = Dialog.ask( Dialog.D_YES_NO, "¿Está seguro?"
                + " Dejará de recibir notificaciones" );
        if( confirmacion == Dialog.YES ) {
          UnregisterCommand command = new UnregisterCommand( false, PushConfig.isEnterprise() );
          executeCommand( command );
        }
    }

    /**
     * Suspend current registration
     */
    public static void suspend() {
        // check that configuration is set and no command in progress
        if( commandInProgress() || invalidSettings() ) {
            return;
        }
        
        int confirmacion = Dialog.ask( Dialog.D_YES_NO, "¿Está seguro?"
                + " Dejará de recibir notificaciones" );
        if( confirmacion == Dialog.YES ) {
          SuspendCommand command = new SuspendCommand( PushConfig.isEnterprise() );
          executeCommand( command );
        }
    }

    /**
     * Resume current registration
     */
    public static void resume() {
        // check that configuration is set and no command in progress
        if( commandInProgress() || invalidSettings() ) {
            return;
        }

        ResumeCommand command = new ResumeCommand( PushConfig.isEnterprise() );
        executeCommand( command );
    }

    public static void updateCountLabels() {
        int unreadCount = 0;
        PushMessage[] messages = PersistentStorage.getMessages();
        for( int i = messages.length - 1; i >= 0; i-- ) {
            if( messages[ i ].isUnread() ) {
                unreadCount++;
            }
        }
        
        int total = PersistentStorage.getTotalMessageCount();
        
        AppGenericaBB.getWelcomeScreen().setCount( total, unreadCount, PersistentStorage.getLastMessageReceived() );
    }

    public static void updateIndicator( int inc ) {
        ApplicationIndicatorRegistry indicatorRegistry = ApplicationIndicatorRegistry.getInstance();
        ApplicationIndicator indicator = indicatorRegistry.getApplicationIndicator();
        boolean refresh;
        
        if( indicator == null ) {
            ApplicationIcon icon = new ApplicationIcon( EncodedImage.getEncodedImageResource( "app_icon_small.png" ) );
            indicator = indicatorRegistry.register( icon, false, true );
            refresh = true;
        } else {
            refresh = inc == 0;
        }

        int unreadCount = 0;
        if( refresh ) {
            PushMessage[] messages = PersistentStorage.getMessages();
            for( int i = messages.length - 1; i >= 0; i-- ) {
                if( messages[ i ].isUnread() ) {
                    unreadCount++;
                }
            }
        } else {
            unreadCount = indicator.getValue() + inc;
        }

        indicator.setValue( unreadCount );
        if( unreadCount > 0 ) {
            indicator.setVisible( true );
        } else {
            indicator.setVisible( false );
        }
    }

    /**
     * Called when SIM card has changed and our application was unsubscribed automatically by Push API
     */
    public static void onSimChange() {
        EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: onSimChange: SIM card has changed. Automatically unregistering ...").getBytes() );

        PersistentStorage.setRegistered( false );
        PushMessageListener listener = (PushMessageListener) AppGenericaBB.getInstance();
        listener.stopListening();

        Runnable uiUpdater = new Runnable() {
            public void run() {
                // SIM card has changed
                // this can happen if the BlackBerry device was given to another user
                UnregisterCommand command = new UnregisterCommand( true, PushConfig.isEnterprise() );
                executeCommand( command );

                // re-authorize the user and re-register if needed
                int registerAgain = Dialog.ask( Dialog.D_YES_NO, "SIM cambiado, registro cancelado. "
                        + "¿Desa registrarse para recibir notificaciones?" );
                if( registerAgain == Dialog.YES ) {
                    register();
                }
            }
        };
        PushUtils.runOnEventThread( uiUpdater );
    }

    /**
     * Called when a new message is pushed
     */
    public static void onMessage( PushMessage message ) {
        byte[] data = message.getData();
        int size = data.length;
        if( message.isTextMesasge() ) {
        	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: onMessage:Text message pushed, size " + size + " bytes").getBytes() );
        } else {
        	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: onMessage: Image message pushed, size " + size + " bytes").getBytes() );
        }
        PersistentStorage.incTotalMessageCount();
        PushMessage[] messages = PersistentStorage.getMessages();
        int length = messages.length;
        EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: onMessage: messages.length = " + length).getBytes() );
        PushMessage[] extended = new PushMessage[ length + 1 ];
        System.arraycopy( messages, 0, extended, 0, length );
        extended[ length ] = message;
        PersistentStorage.setMessages( extended );

        Runnable uiUpdater = new Runnable() {
            public void run() {
                updateCountLabels();
                updateIndicator( 1 );
            }
        };
        
        PushUtils.runOnEventThread( uiUpdater );
        MessageListStore.getInstance().getInboxFolder().fireElementAdded(message);
    }

    /**
     * Check whether application can quit or should remain running in the background
     */
    public static boolean canQuit() {
        synchronized( cmdLock ) {
            if(currentCommand != null) {
                return false;
            }
        }
        
        PushMessageListener messageListener = (PushMessageListener) AppGenericaBB.getInstance();
        return messageListener.applicationCanQuit();
    }

    /**
     * Called when application is started either by user or by Push API. If we registered 
     * for push messages then start the message listener thread.
     */
    public static void appStarted() {

        // check whether this is the first application launch
        if( !PersistentStorage.isOnceLaunched() ) {
            PersistentStorage.setOnceLaunched( true );
            PushUtils.runOnEventThread( new Runnable() {
                public void run() {
                    Dialog.inform( "Por favor, registrese si desea recibir notificaciones" );
                    register();
                }
            } );
            return;
        }

        // the application may have been started by Push API when a message arrived
        // check the registration status and if registered then start listening thread
        if( PersistentStorage.isRegistered() ) {
            PushMessageListener messageListener = (PushMessageListener) AppGenericaBB.getInstance();
            messageListener.startListening();
        }
    }

    /**
     * Displays history of received push messages
     */
    public static void showMessage( String msgId, boolean desdeFolder) {
        PushMessage[] messages = PersistentStorage.getMessages();
        PushMessage pushMessage = null;
        for( int i = messages.length - 1; i >= 0; i-- ) {
            if( messages[ i ].getId().equals( msgId ) ) {
                pushMessage = messages[ i ];
                break;
            }
        }

        if( pushMessage != null ) {
            // mark the message as read and update indicator and labels
            if( pushMessage.isUnread() ) {
                pushMessage.setUnread( false );
                PersistentStorage.setMessages( messages );
                updateCountLabels();
                updateIndicator( -1 );
            }

            // show the message
            MensajeScreen messageViewScreen = AppGenericaBB.getMessageViewScreen();
            byte[] data = pushMessage.getData();
            if( pushMessage.isTextMesasge() ) {
                messageViewScreen.setMessage( pushMessage );
                
                if (desdeFolder)
                    UiApplication.getUiApplication().pushGlobalScreen( messageViewScreen, 0, UiApplication.GLOBAL_MODAL );
                else
                	UiApplication.getUiApplication().pushScreen( messageViewScreen );
                
            } else {
//                try {
//                    EncodedImage image = EncodedImage.createEncodedImage( data, 0, data.length );
//                    messageViewScreen.setMessage( pushMessage.getTimestamp(), image );
//                    UiApplication.getUiApplication().pushScreen( messageViewScreen );
//                } catch( Exception ex ) {
//                    Dialog.alert( "Failed to convert image, caused by " + ex );
//                }
            }

            // refresh the history
            MessageHistoryScreen historyScreen = AppGenericaBB.getHistoryScreen();
            historyScreen.populate( PersistentStorage.getMessages() );
            
            MessageListStore.getInstance().getInboxFolder().fireElementUpdated(pushMessage, pushMessage);
            
        } else {
        	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: showMessage: Can't find message by ID: " + msgId).getBytes() );
        }
    }

    public static void clearMessageHistory() {
        PushMessage[] messages = new PushMessage[ 0 ];
        PersistentStorage.setMessages( messages );
        PersistentStorage.setTotalMessageCount( 0 );
        MessageHistoryScreen historyScreen = AppGenericaBB.getHistoryScreen();
        historyScreen.populate( messages );
        updateCountLabels();
        updateIndicator( 0 );
        
        MessageListStore.getInstance().getInboxFolder().fireReset();
        EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: clearMessageHistory: TODOS los Mensajes Eliminados").getBytes() );
    }
    
    public static void deleteMessage(PushMessage message) {
        PersistentStorage.deleteMessage(message); // actualiza cuenta
        EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: deleteMessages: paso PersistentStorage.deleteMessage(message);").getBytes() );
        
        Runnable uiUpdater = new Runnable() {
            public void run() {
                updateCountLabels();
                updateIndicator( -1 );
            }
        };
        
        PushUtils.runOnEventThread( uiUpdater );
        
//        MessageHistoryScreen historyScreen = AppGenericaBB.getHistoryScreen();
//        historyScreen.populate( PersistentStorage.getMessages() );
//        updateCountLabels();
//        updateIndicator( -1 );
    }
    
    public static void deleteMultipleMessages(ApplicationMessage[] messages) {
        final int numEliminados = PersistentStorage.deleteMultipleMessages(messages); // No actualiza cuenta
        int numMsgs = PersistentStorage.getTotalMessageCount(); // Antes de la eliminacion
        
        if ( numEliminados == messages.length && numMsgs >= numEliminados){
        	
        	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: deleteMultipleMessages: numMsgs =" + numMsgs ).getBytes() );
        	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: deleteMultipleMessages: numEliminados =" + numEliminados ).getBytes() );
        	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: deleteMultipleMessages: numMsgs - numEliminados =" + (numMsgs - numEliminados) ).getBytes() );
        	
        	PersistentStorage.setTotalMessageCount(numMsgs - numEliminados); // Hace Commit
//	        MessageHistoryScreen historyScreen = AppGenericaBB.getHistoryScreen();
//	        historyScreen.populate( PersistentStorage.getMessages() );
//        	
//	        updateCountLabels();
//	        updateIndicator( -numEliminados );
	        
            Runnable uiUpdater = new Runnable() {
                public void run() {
                    updateCountLabels();
                    updateIndicator( -numEliminados );
                }
            };
            
            PushUtils.runOnEventThread( uiUpdater );
            
	        MessageListStore.getInstance().getInboxFolder().fireReset();
	        EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: deleteMultipleMessages: "+numEliminados + " Mensajes Eliminados").getBytes() );
	    } else {
	    	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("PushController: deleteMultipleMessages: "+ "Error Eliminando - "+ messages.length +" - (deleteMultipleMessages ) Eliminados - "+ numEliminados +" -").getBytes() );
	    }
    }

    /**
     * Displays history of received push messages
     */
    public static void showMessages() {
        MessageHistoryScreen historyScreen = AppGenericaBB.getHistoryScreen();
        historyScreen.populate( PersistentStorage.getMessages() );
        UiApplication.getUiApplication().pushScreen( historyScreen );
    }

    
    /**
     * Displays application configuration screen
     */
    public static void cancelCurrentCommand() {
        synchronized( cmdLock ) {
            if( currentCommand != null ) {
                currentCommand.cancel();
                currentCommand = null;
            }
        }
        HomeScreen welcomeScreen = AppGenericaBB.getWelcomeScreen();
        welcomeScreen.setAction( null );
    }

    private static boolean invalidSettings() {
        String appId = PushConfig.getAppId();
        String cpUrl = PushConfig.getContentProviderUrl();
        String bpsUrl = PushConfig.getBpsUrl();
        boolean isEnterprise = PushConfig.isEnterprise();
        
        if( empty( appId ) || empty( cpUrl ) || ( ( !isEnterprise ) && empty( bpsUrl ) ) ) {
            Dialog.inform( "Some of the settings are empty or NULL.\nPlease make sure that all of the following are set"
                    + " in settings: application ID, content provider URL and BPS URL" );
            return true;
        }
        return false;
    }

    private static boolean empty( String s ) {
        return s == null || s.trim().length() == 0;
    }

    private static boolean commandInProgress() {
        NetworkCommand currentLocal;
        synchronized( cmdLock ) {
            currentLocal = currentCommand;
        }
        if( currentLocal == null ) {
            return false;
        }
        String commandName = currentLocal.getCommandName();
        int cancel = Dialog.ask( Dialog.D_YES_NO, "Actualmente se ejecuta la acción - "
                + commandName + ".\n ¿Desea cancelarla?" );
        if( cancel == Dialog.YES ) {
            synchronized( cmdLock ) {
                if( currentLocal == currentCommand ) {
                    currentCommand.cancel();
                    currentCommand = null;
                    return false;
                } else {
                    return commandInProgress();
                }
            }
        } else {
            // user doesn't want to cancel current command
            return true;
        }
    }

    public static void executeCommand( NetworkCommand cmd ) {
        synchronized( cmdLock ) {
            if( currentCommand != null ) {
                currentCommand.cancel();
            }
            currentCommand = cmd;
            new Thread( cmd ).start();
        }
        
        HomeScreen welcomeScreen = AppGenericaBB.getWelcomeScreen();
        welcomeScreen.setAction( cmd.getCommandName() );
    }

    public static void commandExecuted( NetworkCommand cmd, boolean canceled, Exception error ) {
        NetworkCommand commandLocal = null;
        synchronized( cmdLock ) {
            if( currentCommand == cmd ) {
                commandLocal = currentCommand;
                currentCommand = null;
            }
        }
        if( commandLocal == null ) {
            return;
        }
        
        // check whether we need to listen for push messages
        PushMessageListener messageListener = (PushMessageListener) AppGenericaBB.getInstance();
        if( PersistentStorage.isRegistered() ) {
            messageListener.startListening();
        } else {
            messageListener.stopListening();
        }
        
        HomeScreen welcomeScreen = AppGenericaBB.getWelcomeScreen();
        welcomeScreen.setAction( null );
        
        boolean autoCommand = false;
        if(commandLocal instanceof UnregisterCommand) {
            UnregisterCommand unregisterCommand = (UnregisterCommand) commandLocal;
            autoCommand = unregisterCommand.isOnSimSwap();
        }
        
        if( !canceled && !autoCommand ) {
            String name = commandLocal.getCommandName();
            // notify the user
            if( error == null ) {
                Dialog.inform( "Petición de " + name + " ejecutado exitosamente" );
            } else {
                Dialog.inform( "Petición de " + name + " falló. Causado por " + error );
            }
        }
    }
}
