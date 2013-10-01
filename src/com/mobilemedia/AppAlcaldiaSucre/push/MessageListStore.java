package com.mobilemedia.AppAlcaldiaSucre.push;

import net.rim.blackberry.api.messagelist.ApplicationMessageFolder;
import net.rim.device.api.system.RuntimeStore;

public final class MessageListStore {
    // com.mobmedianet.AppGenericaBB.push.MessageListStore
    private static final long MSG_KEY = 0x887de9e0fed4e92cL;

    private static MessageListStore _instance;

    private ApplicationMessageFolder _inboxFolder;

    /**
     * Creates a new MessageListDemoStore object
     */
    private MessageListStore() {}

    /**
     * Gets the singleton instance of the MessageListDemoStore
     * 
     * @return The singleton instance of the MessagelistDemoStore
     */
    public static synchronized MessageListStore getInstance() {
        // Keep messages as singleton in the RuntimeStore
        if (_instance == null) {
            final RuntimeStore rs = RuntimeStore.getRuntimeStore();

            synchronized (rs) {
                _instance = (MessageListStore) rs.get(MSG_KEY);

                if (_instance == null) {
                    _instance = new MessageListStore();
                    rs.put(MSG_KEY, _instance);
                }
            }
        }

        return _instance;
    }

    /**
     * Sets the main and deleted folders
     * 
     * @param mainFolder
     *            The main folder to use
     * @param deletedFolder
     *            The deleted folder to use
     */
    public void setFolder(final ApplicationMessageFolder in) {
        _inboxFolder = in;
    }

    /**
     * Retrieves the inbox folder
     * 
     * @return The inbox folder
     */
    public ApplicationMessageFolder getInboxFolder() {
        return _inboxFolder;
    }
}
