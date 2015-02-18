package com.example.notificationmanagertesting;

import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends ActionBarActivity implements  OnClickListener{

	Button notification_button;
	Button custom_notify_button;
	static int mId = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		notification_button = (Button)findViewById(R.id.button_notification);
		notification_button.setOnClickListener(this);
        
		custom_notify_button = (Button)findViewById(R.id.button_custom_notification);
		custom_notify_button.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_notification){
	ShowNotification();	
	}else{
		showCustomNotification();
	}
	}	

	void ShowNotification(){
	
	NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
	 .setSmallIcon(R.drawable.small)
	 .setContentTitle("OpenResults")
	 .setContentText("Click ME")
	 ;
	
	NotificationCompat.InboxStyle mInboxStyle = new NotificationCompat.InboxStyle();
	String[] sArr = {"a","b","c","d","e","f"};
	mInboxStyle.setBigContentTitle("Event Title");
	
	for (int i=0; i < sArr.length;i++){
	mInboxStyle.addLine(sArr[i]);
	}
	mBuilder.setStyle(mInboxStyle);
	
	Intent resultIntent = new Intent(this, ResultActivity.class);	
	TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
	taskStackBuilder.addParentStack(ResultActivity.class);
	taskStackBuilder.addNextIntent(resultIntent);
	
	PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
	mBuilder.setContentIntent(pendingIntent);
	mId++;
	
	mBuilder.addAction(R.drawable.ic_launcher, "Action Button", pendingIntent);
	
	
	NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	mNotificationManager.notify(mId,mBuilder.build());
	}
	
	public void showCustomNotification(){
		// Using RemoteViews to bind custom layouts into Notification
		RemoteViews remoteViews = new RemoteViews(getPackageName(),
				R.layout.customnotification);
 
		// Set Notification Title
	
		// Open NotificationView Class on Notification Click
		Intent intent = new Intent(this, ResultActivity.class);
	
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
 
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.small)
				.setTicker("ticker string")
				.setAutoCancel(true)
				.setContentIntent(pIntent)
				.setContent(remoteViews);
 
		// Locate and set the Image into customnotificationtext.xml ImageViews
		remoteViews.setImageViewResource(R.id.imagenotileft,R.drawable.fail);
		remoteViews.setImageViewResource(R.id.imagenotiright,R.drawable.suc);
 
		// Locate and set the Text into customnotificationtext.xml TextViews
		remoteViews.setTextViewText(R.id.title,"Title");
		remoteViews.setTextViewText(R.id.text,"Text");
 
		// Create Notification Manager
		NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager
		notificationmanager.notify(0, builder.build());
 
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
