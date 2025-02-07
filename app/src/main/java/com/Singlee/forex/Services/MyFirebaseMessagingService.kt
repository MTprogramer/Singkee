package com.Singlee.forex.Services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.Singlee.forex.R
import com.Singlee.forex.screens.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.concurrent.ExecutionException

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val NOTIFICATION_ID = 101 // Unique ID for each notification
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            handleDataPayload(remoteMessage.data)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            val imageUrl = it.imageUrl?.toString()

            // Show notification
            sendNotification(
                messageBody = it.body,
                title = it.title,
                imageUrl = imageUrl
            )
        }
    }

    /**
     * Handle the custom data payload from Firebase.
     */
    private fun handleDataPayload(data: Map<String, String>) {
        // Handle your data here. You can parse any custom key-value pairs from data.
        val customValue = data["customKey"] ?: "Default Value"
        Log.d(TAG, "Custom Data: $customValue")

        // For example, if you need to trigger an action in the app:
        // Trigger local actions based on the data (e.g., updating UI, storing data in the database)
    }

    /**
     * Create and show a notification with an optional image.
     */
    private fun sendNotification(messageBody: String?, title: String?, imageUrl: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "singleeNoti"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.noti_icon)  // Your app's notification icon
            .setContentTitle(title ?: "Default Title")
            .setContentText(messageBody ?: "Default Message")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        // Handle the image if present
        if (!imageUrl.isNullOrEmpty()) {
            val bitmap = getBitmapFromUrl(imageUrl)
            if (bitmap != null) {
                val bigPictureStyle = NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .setBigContentTitle(title)
                    .setSummaryText(messageBody)

                notificationBuilder.setStyle(bigPictureStyle)
            }
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since Android Oreo (8.0), a notification channel is needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "singleeChannel", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    /**
     * Fetch image from URL using Glide and return as a Bitmap.
     */
    private fun getBitmapFromUrl(imageUrl: String): Bitmap? {
        return try {
            Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .submit()
                .get() // Block until the image is loaded
        } catch (e: ExecutionException) {
            Log.e(TAG, "Error getting bitmap from URL: $e")
            null
        } catch (e: InterruptedException) {
            Log.e(TAG, "Error getting bitmap from URL: $e")
            null
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    /**
     * Send the FCM token to your server or store it in the database.
     */
    private fun sendRegistrationToServer(token: String) {
        // Handle storing the FCM token on your backend if needed
    }
}
