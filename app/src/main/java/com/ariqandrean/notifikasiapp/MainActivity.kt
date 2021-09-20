package com.ariqandrean.notifikasiapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ariqandrean.notifikasiapp.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var titleEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var urlEditText: EditText
    private lateinit var sendTextView: TextView

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        titleEditText = binding.mainTITLEeditText
        descEditText = binding.mainDescEditText
        urlEditText = binding.mainURLEditText
        sendTextView = binding.mainSendTextView

        createNotificationChannel()
        sendTextView.setOnClickListener {
            binding.mainSendTextView.setBackgroundColor(Color.CYAN)
            binding.mainSendTextView.setBackgroundColor(Color.WHITE)
            sendNotification()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val editTextTitle = titleEditText.text
        val editTextDesc = descEditText.text
        val editTextUrl = urlEditText.text

        /** Intent*/
        val intentGoToUrl = Intent(Intent.ACTION_VIEW, Uri.parse("http://$editTextUrl")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0 , intentGoToUrl, 0)

//        val bitmap: Bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_background)
        val bitMapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_background)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
            .setContentTitle(editTextTitle.toString())
            .setContentText("Example Description")
            .setLargeIcon(bitMapLargeIcon)
            .setStyle(NotificationCompat.BigTextStyle().bigText(editTextDesc.toString()))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }

//    private fun gotoUrl(Url: String?) {
//        val uri: Uri = Uri.parse(Url)
//        startActivity(Intent(Intent.ACTION_VIEW, uri))
//    }
}