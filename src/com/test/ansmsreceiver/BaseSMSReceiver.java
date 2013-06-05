package com.test.ansmsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * This class abstracts away the messy details of setting up a BroadcastReceiver
 * to receive SMS messages.
 * 
 * Extend this and implement onSmsReceived to create an easy SMS message receiver.
 * Once extended, you must add the following to your AndroidManifest.xml
 * 
 * To use this, you must have the following XML in your AndroidManifest.xml file
 * 
 * <uses-permission android:name="android.permission.RECEIVE_SMS" />
 * 
 * <application> ...
 * 
 * <receiver android:name=".SMSReceiver"> 
 * <intent-filter> 
 * <action android:name="android.provider.Telephony.SMS_RECEIVED" /> 
 * <action android:name="android.provider.Telephony.NEW_OUTGOING_SMS" />
 * </intent-filter>
 * </receiver>
 * 
 * Apache 2.0 License
 * 
 * @author Tom Dignan
 */
public abstract class BaseSMSReceiver extends BroadcastReceiver {
	/** Tag for identify class in Log */
	
	/** Broadcast action for received SMS */
	public static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
 
	/** Broadcast action for sent SMS */
	public static final String ACTION_NEW_OUTGOING_SMS = "android.provider.Telephony.NEW_OUTGOING_SMS";
	
	/** Intent extra for get SMS pdus */
	private static final String EXTRA_PDUS = "pdus";
 
	/** Processes Intent data into SmsMessage array and calls onSmsReceived */
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action != null && (action.equals(ACTION_SMS_RECEIVED) || action.equals(ACTION_NEW_OUTGOING_SMS))) {
			
			Bundle bundle = intent.getExtras();
			Object[] pdus = (Object[]) bundle.get(EXTRA_PDUS);
			SmsMessage[] messages = new SmsMessage[pdus.length];
			
			for (int i = 0; i < pdus.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			onSmsReceived(messages);
			
			//abortBroadcast();
		}
	}
	
	protected abstract void onSmsReceived(SmsMessage[] messages);
}
