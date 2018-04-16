# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\sev_user\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keepattributes *Annotation*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-assumenosideeffects class android.util.Log {
public static *** d(...);
public static *** v(...);
}

-keepclasseswithmembernames class * {
native <methods>;
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
public static <fields>;
}

-keep class * extends java.util.ListResourceBundle {
protected Object[][] getContents();
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
@com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}

-dontwarn **CompatHoneycomb
-dontwarn android.support.v4.app.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep class android.support.** { *; }
-keep class android.app.** {*;}
-keep interface android.app.** { *; }

-dontwarn **CompatHoneycomb
-keep public class * extends android.support.v4.app.Fragment

-keep class com.squareup.** { *; }


-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
@com.google.android.gms.common.annotation.KeepName *;
}
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

### Material Spinner
-keep class com.jaredrummler.** { *; }
-dontwarn com.jaredrummler.**

### Toasty
-keep class com.github.GrenderG.** { *; }
-dontwarn com.github.GrenderG.**

### CIRCULARIMAGEVIEW
-keep class com.pkmmte.view.** { *; }

### Greenrobot
-keep class de.greenrobot.** { *; }
-dontwarn de.greenrobot.**
-keep class org.greenrobot.** { *; }
-dontwarn org.greenrobot.**

-keep class org.greenrobot.greendao.**
-dontwarn org.greenrobot.greendao.**
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keep public class my.dao.package.models.** {
     public static <fields>;
}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

### EXO PLAYER
-keep class com.google.android.exoplayer.** { *; }
-keep class com.google.android.exoplayer2.** { *; }

### MaterialSearchview
-keep class com.miguelcatalan.** { *; }
-dontwarn com.miguelcatalan.**

#fire-base
-keep class com.firebase.** { *; }
-keep class com.google.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.shaded.fasterxml.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.apache.**
-dontwarn org.w3c.dom.**
-dontwarn com.google.android.gms.internal.zzbhd
-dontwarn com.google.android.gms.internal.zzbhe

#volley
-keepclasseswithmembernames  interface com.android.volley.** { *; }
-keep class org.json.** {*;}
-keep class android.accounts.**{*;}
-keep class java.io.**{*;}
-keep class android.net.**{*;}
-keep class org.apache.http.**{*;}
-keep class android.net.http.AndroidHttpClient{*;}
-keep class com.android.volley.** {*;}
-keep class com.android.volley.toolbox.** {*;}
-keep class com.android.volley.Response$* { *; }
-keep class com.android.volley.Request$* { *; }
-keep class com.android.volley.RequestQueue$* { *; }
-keep class com.android.volley.toolbox.HurlStack$* { *; }
-keep class com.android.volley.toolbox.ImageLoader$* { *; }
-keep class org.apache.commons.logging.**
-dontwarn javax.management.**
-dontwarn java.lang.management.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.slf4j.**
-dontwarn org.json.**

#viewpagerindicator
-dontwarn com.viewpagerindicator.**

#Toolbar
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
# Hide warnings about references to newer platforms in the library
-dontwarn android.support.v7.**
-keep interface android.support.v7.** { *; }
-keep class com.google.** { *; }
-keep class com.facebook.** { *; }
-keep class org.apache.** { *; }
-keep public class org.apache.commons.io.**

-keep class android.support.v7.internal.view.menu.** {*;}
-keep class android.support.v7.widget.SearchView {*;}
-keep class android.support.v7.widget.ActionMenuPresenter {*;}
-keep class android.support.v7.widget.ActionMenuView {*;}
-keep class android.support.v7.widget.Toolbar {*;}
-dontwarn
-ignorewarnings

-keep class android.widget.ArrayAdapter.** { *; }

### PICASSO

# Checks for OkHttp versions on the classpath to determine Downloader to use.
-dontnote com.squareup.picasso.Utils
# Downloader used only when OkHttp 2.x is present on the classpath.
-dontwarn com.squareup.picasso.OkHttpDownloader
# Downloader used only when OkHttp 3.x is present on the classpath.
-dontwarn com.squareup.picasso.OkHttp3Downloader


### OKHTTP

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote okhttp3.internal.Platform
-ignorewarnings

### fabric
-keep class io.fabric.sdk.android.** { *; }
-keep interface io.fabric.sdk.android.** { *; }
-keep class com.digits.sdk.android.core.** { *; }
-keep interface com.digits.sdk.android.core.** { *; }
-keep class com.twitter.sdk.android.** { *; }
-keep interface com.twitter.sdk.android.** { *; }

-keep class io.fabric.** { *; }
-keep interface io.fabric.** { *; }
-keep class com.digits.** { *; }
-keep interface com.digits.** { *; }
-keep class com.twitter.** { *; }
-keep interface com.twitter.** { *; }
-keep class com.crashlytics.** { *; }
-keep interface com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

###JSOUP
-keeppackagenames org.jsoup.nodes
-keep public class org.jsoup.** {
public *;
}
-keep public class org.jsoup.** {
public protected *;
}
### ONE SIGNAL
-dontwarn com.onesignal.**

# These 2 methods are called with reflection.
-keep class com.google.android.gms.common.api.GoogleApiClient {
    void connect();
    void disconnect();
}


-keep class com.onesignal.ActivityLifecycleListenerCompat** {*;}


# Observer backcall methods are called with reflection
-keep class com.onesignal.OSSubscriptionState {
    void changed(com.onesignal.OSPermissionState);
}

-keep class com.onesignal.OSPermissionChangedInternalObserver {
    void changed(com.onesignal.OSPermissionState);
}

-keep class com.onesignal.OSSubscriptionChangedInternalObserver {
    void changed(com.onesignal.OSSubscriptionState);
}

-keep class ** implements com.onesignal.OSPermissionObserver {
    void onOSPermissionChanged(com.onesignal.OSPermissionStateChanges);
}

-keep class ** implements com.onesignal.OSSubscriptionObserver {
    void onOSSubscriptionChanged(com.onesignal.OSSubscriptionStateChanges);
}

-keep class com.onesignal.shortcutbadger.impl.AdwHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.ApexHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.AsusHomeLauncher { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.DefaultBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.EverythingMeHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.HuaweiHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.LGHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.NewHtcHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.NovaHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.OPPOHomeBader { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.SamsungHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.SonyHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.VivoHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.XiaomiHomeBadger { <init>(...); }
-keep class com.onesignal.shortcutbadger.impl.ZukHomeBadger { <init>(...); }


-dontwarn com.amazon.**

# Proguard ends up removing this class even if it is used in AndroidManifest.xml so force keeping it.
-keep public class com.onesignal.ADMMessageHandler {*;}

-keep class com.onesignal.JobIntentService$* {*;}

-keep class com.onesignal.OneSignalUnityProxy {*;}

### FIREBASE
-keepnames class com.firebase.** { *; }
-keepnames class com.shaded.fasterxml.jackson.** { *; }
-keepnames class org.shaded.apache.** { *; }
-keepnames class javax.servlet.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.commons.logging.impl.**
-keep class com.firebase.** { *; }
-keep class org.shaded.apache.** { *; }
-keepnames class com.shaded.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**
-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**

-keep class com.shaded.fasterxml.jackson.** { *; }
-keep class !com.my.package.** { *; }
# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class com.yourcompany.models.** {
*;
}