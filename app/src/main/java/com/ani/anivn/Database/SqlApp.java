package com.ani.anivn.Database;

import android.app.Application;
import android.util.Log;

import com.ani.anivn.Model.DaoMaster;
import com.ani.anivn.Model.DaoSession;
import com.crashlytics.android.Crashlytics;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import io.fabric.sdk.android.Fabric;
import org.json.JSONObject;

/**
 * Created by sev_user on 04/02/2018.
 */

public class SqlApp extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        /// Config onesignal
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.WARN);
        OneSignal.startInit(this).setNotificationOpenedHandler(new SqlApp.ExampleNotificationOpenedHandler()).autoPromptLocation(true).init();
        ////////////////////
        daoSession = new DaoMaster(new DbOpenHelper(this, "animevsub_v1.db").getWritableDb()).newSession();
    }
    /// Config onesignal
    public class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        private ExampleNotificationOpenedHandler() {
        }

        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            if (data != null) {
                String customKey = data.optString("customkey", null);
                if (customKey != null) {
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
                }
            }
            if (actionType == OSNotificationAction.ActionType.ActionTaken) {
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
            }
        }
    }
    ////////////////////
    public DaoSession getDaoSession() {
        return daoSession;
    }
}