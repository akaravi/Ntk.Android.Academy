package ntk.android.academy.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ntk.android.academy.model.Notify;
import ntk.android.academy.room.RoomDb;
import ntk.android.academy.utill.PugPush;

/**
 * Created by Mehrdad Safari on 18-Jan-17.
 */

public class SeFirebase extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Notify notification = new Notify();
        notification.Title = remoteMessage.getData().get("Title");
        if (remoteMessage.getData().get("ContentType") != null) {
            notification.ContentType = Integer.parseInt(remoteMessage.getData().get("ContentType"));
        }
        notification.Content = remoteMessage.getData().get("Content");
        notification.BigImageSrc = remoteMessage.getData().get("BigImageSrc");
        notification.IsRead = 0;
        RoomDb.getRoomDb(getApplicationContext()).NotificationDoa().Insert(notification);
        RoomDb.DestroyInstance();
        PugPush.ShowNotification(getApplicationContext(), notification);
    }
}
