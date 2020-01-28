package co.chatsdk.core.events;

/**
 * Created by benjaminsmiley-andrews on 16/05/2017.
 */

public enum EventType {

    ThreadAdded,
    ThreadRemoved,
    ThreadDetailsUpdated,
    ThreadLastMessageUpdated,
    ThreadMetaUpdated,
    MessageAdded,
    MessageUpdated,
    MessageRemoved,
    MessageSendStatusChanged,
    ThreadUsersChanged,
    UserMetaUpdated,
    UserPresenceUpdated,
    ContactAdded,
    ContactDeleted,
    ContactsUpdated,
    TypingStateChanged,
    Logout,
    ThreadRead,
    ThreadReadReceiptUpdated,
    NearbyUserAdded,
    NearbyUserMoved,
    NearbyUserRemoved,
    NearbyUsersUpdated,
    Error
}
