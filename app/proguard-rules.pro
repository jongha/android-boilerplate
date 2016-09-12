# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# Me can edit the include path and order by changing the proguardFiles
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
-keepattributes Signature
-keepattributes *Annotation*

# for sdk
-keep class net.mrlatte.boilerplate.sdk.apis.responses.** { *; }
-keep class net.mrlatte.boilerplate.sdk.apis.types.** { *; }
-keep class net.mrlatte.boilerplate.sdk.sockets.types.** { *; }
-keep class net.mrlatte.boilerplate.sdk.sockets.models.** { *; }
-keep class org.webrtc.** { *; }

# for app
-keep class net.mrlatte.boilerplate.types.** { *; }
-keep class net.mrlatte.boilerplate.models.** { *; }
-keep class net.mrlatte.boilerplate.persistences.** { *; }
-keep class net.mrlatte.boilerplate.ui.widgets.JumpingSpan { *; }
-keep class net.mrlatte.boilerplate.events.constants.** { *; }
-keep class net.mrlatte.boilerplate.jobs.constants.** { *; }

-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption

# for support
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-dontwarn android.support.v7.**

# for realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
#    public static java.lang.String TABLENAME;
#}
#-keep class **$Properties

# icepick
-dontwarn icepick.**
-keep class icepick.** { *; }
-keep class **$$Icepick { *; }
-keepclasseswithmembernames class * {
    @icepick.* <fields>;
}
-keepnames class * { @icepick.State *;}

# for eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class butterknife.** { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# for picasso
-dontwarn com.squareup.okhttp.**

# for butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# for joda-time
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }

# for volley
-keep class org.apache.commons.logging.**
-keep class com.android.volley.** { *; }
-keep interface com.android.volley.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.android.volley.toolbox.**

# for gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }

# for apache
-dontwarn org.apache.commons.**
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**

#for drawableview
-keep class me.panavtec.drawableview.** { *; }
-dontwarn me.panavtec.drawableview.**

# for sqlcipher
-dontwarn com.google.common.primitives.**
-keepclasseswithmembernames class * {
    native <methods>;
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

-keep class net.sqlcipher.** {
    *;
}

# for okio
-dontwarn okio.**

# for about library
-keep class .R
-keep class **.R$* {
    <fields>;
}