/**
 * PushMessageDaemon.java
 * 
 * Copyright © 1998-2011 Research In Motion Limited
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Note: For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 */

package com.mobmedianet.AppGenericaBB.push.daemon;

import com.mobmedianet.AppGenericaBB.push.MessageListStore;
import com.mobmedianet.AppGenericaBB.push.PersistentStorage;
import com.mobmedianet.AppGenericaBB.push.PushController;
import com.mobmedianet.AppGenericaBB.push.PushMessage;
import com.mobmedianet.AppGenericaBB.push.lib.ReadableListImpl;

import net.rim.blackberry.api.menuitem.ApplicationMenuItem;
import net.rim.blackberry.api.messagelist.ApplicationIcon;
import net.rim.blackberry.api.messagelist.ApplicationMessage;
import net.rim.blackberry.api.messagelist.ApplicationMessageFolder;
import net.rim.blackberry.api.messagelist.ApplicationMessageFolderListener;
import net.rim.blackberry.api.messagelist.ApplicationMessageFolderRegistry;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.EventLogger;

/**
 * Daemon process that runs in the background. It's tasks include non-gui
 * message operations such as message deletion, marking messages as read/unread
 * and (in a typical real world scenario) synchronization with a mail server.
 */
public final class MessageListDaemon extends Application implements
        ApplicationMessageFolderListener {
    
	public static final String APPLICATION_NAME = "AppGenericaBB_Background";
    /* com.mobmedianet.AppGenericaBB.push.daemon3.INBOX_FOLDER_ID */
    public static final long INBOX_FOLDER_ID = 0x2e5ec52c287918f4L;
    
    /**
     * Called during device startup. Registers application descriptors, message
     * folder listeners, message icons and menu items.
     */
    public void init() {
        // 1. Register folders and application descriptors
        // ----------------------
    	
        final ApplicationMessageFolderRegistry reg =
                ApplicationMessageFolderRegistry.getInstance();

        // Some context menu items don't need a GUI (e.g. an item for deleting a
        // message) and will be run in the current daemon application.
        final ApplicationDescriptor daemonDescr =
                ApplicationDescriptor.currentApplicationDescriptor();

        // Main application descriptor - causes application to be launched with
        // default welcome screen if a user clicks on the "Message List Demo"
        // header in the home screen notifications view.
//        final ApplicationDescriptor mainDescr =
//                new ApplicationDescriptor(daemonDescr, APPLICATION_NAME,
//                        new String[] {});

        // This application descriptor launches this application with a GUI to
        // execute listener callbacks, e.g. to display a message.
        final ApplicationDescriptor uiCallbackDescr =
                new ApplicationDescriptor(daemonDescr, APPLICATION_NAME,
                        new String[] { "verMsg" });

        // Get existing messages from storage and register them in folders

        final ReadableListImpl messages = PersistentStorage.getReadableListMessages();
        final ApplicationMessageFolder inbox = 
        	reg.registerFolder(INBOX_FOLDER_ID, "Entrada AppGenerica", messages);

        // Register as a listener for callback notifications
        inbox.addListener( this, ApplicationMessageFolderListener.MESSAGE_DELETED
                        | ApplicationMessageFolderListener.MESSAGE_MARKED_OPENED,
                        daemonDescr);

        MessageListStore.getInstance().setFolder(inbox);

        // 2. Register message icons -------------------------------------------

        final ApplicationIcon newIcon =
                new ApplicationIcon(EncodedImage
                        .getEncodedImageResource("new.png"));
        final ApplicationIcon readIcon =
                new ApplicationIcon(EncodedImage
                        .getEncodedImageResource("read.png"));

        reg.registerMessageIcon(PushMessage.DEMO_MESSAGE_TYPE,
                PushMessage.STATUS_NEW, newIcon);
        
        reg.registerMessageIcon(PushMessage.DEMO_MESSAGE_TYPE,
        		PushMessage.STATUS_OPENED, readIcon);
        
        // 3. Register message menu items --------------------------------------
        
        final ApplicationMenuItem openMenuItem = new OpenContextMenu(0x230010);
  
        final ApplicationMenuItem[] newGuiMenuItems = new ApplicationMenuItem[] { openMenuItem };
        final ApplicationMenuItem[] openedGuiMenuItems = new ApplicationMenuItem[] { openMenuItem };
        final ApplicationMenuItem[] repliedGuiMenuItems = new ApplicationMenuItem[] { openMenuItem };
        final ApplicationMenuItem[] deletedGuiMenuItems = new ApplicationMenuItem[] { openMenuItem, };

        reg.registerMessageMenuItems(PushMessage.DEMO_MESSAGE_TYPE, PushMessage.STATUS_NEW, newGuiMenuItems, uiCallbackDescr);
        reg.registerMessageMenuItems(PushMessage.DEMO_MESSAGE_TYPE, PushMessage.STATUS_OPENED, openedGuiMenuItems, uiCallbackDescr);
        reg.registerMessageMenuItems(PushMessage.DEMO_MESSAGE_TYPE, PushMessage.STATUS_REPLIED, repliedGuiMenuItems, uiCallbackDescr);
        reg.registerMessageMenuItems(PushMessage.DEMO_MESSAGE_TYPE, PushMessage.STATUS_DELETED, deletedGuiMenuItems, uiCallbackDescr);
    }

    /**
     * @see ApplicationMessageFolderListener#actionPerformed(int,
     *      ApplicationMessage[], ApplicationMessageFolder)
     */
    public void actionPerformed(final int action,
            final ApplicationMessage[] messages,
            final ApplicationMessageFolder folder) {
    	
        final PersistentStorage messageStore = PersistentStorage.getInstance();

        synchronized (messageStore) {
            // Check if action was performed on multiple messages
            if (messages.length == 1) {
                final PushMessage message = (PushMessage) messages[0];

                switch (action) {
                case ApplicationMessageFolderListener.MESSAGE_DELETED:
                    if (folder.getId() == INBOX_FOLDER_ID) {
                    	EventLogger.logEvent(0xd79a2b82d8b05a40L, ("MessageListDaemon: actionPerformed: messages.length = 1: MESSAGE_DELETED: folder.getId() == INBOX_FOLDER_ID" ).getBytes() );
                        // Message from Inbox was deleted, update storage,
                        PushController.deleteMessage(message);
                        // Note: There is no need to fireElementRemoved(),
                        // message was already deleted.
                    }
                    break;
                }
            } else {
            	// Multiple messages were affected, optimize notifications
            	if ( action == ApplicationMessageFolderListener.MESSAGE_DELETED && 
            			folder.getId() == INBOX_FOLDER_ID ) {
            		EventLogger.logEvent(0xd79a2b82d8b05a40L, ("MessageListDaemon: actionPerformed: messages.length > 1: MESSAGE_DELETED: folder.getId() == INBOX_FOLDER_ID" ).getBytes() );
            		PushController.deleteMultipleMessages( messages );
            	}
            }
        }
    }

    /**
     * Open Context menu item. Marks read and opens the selected message for
     * viewing.
     */
    static class OpenContextMenu extends ApplicationMenuItem { 
        /**
         * Creates a new ApplicationMenuItem instance with provided menu
         * position
         * 
         * @param order
         *            Display order of this item, lower numbers correspond to
         *            higher placement in the menu
         */
        public OpenContextMenu(final int order) {
            super(order);
        }

        /**
         * @see ApplicationMenuItem#run(Object)
         */
        public Object run(final Object context) {
            if (context instanceof PushMessage) {
                final PushMessage message = (PushMessage) context;
                // Update status if message is new
                PushController.showMessage( message.getId(), true );
            }
            return context;
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return "View Message";
        }
    }
}