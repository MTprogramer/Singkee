# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

# Add project specific ProGuard rules here.

# Keep classes that are used in WebView JavaScript interfaces
# Uncomment and specify the fully qualified class name to the JavaScript interface class:
# -keepclassmembers class fqcn.of.javascript.interface.for.webview {
#    public *;
# }

# Preserve the line number information for debugging stack traces
-keepattributes SourceFile,LineNumberTable

# Uncomment this to hide the original source file name
# -renamesourcefileattribute SourceFile

# Keep Bouncy Castle classes to avoid NoClassDefFoundError
-keep class org.bouncycastle.** { *; }

# Keep any other classes that are necessary for your application
# For example, if you have specific classes that should not be obfuscated:
# -keep class com.yourpackage.YourClass { *; }

# Add any additional rules as needed