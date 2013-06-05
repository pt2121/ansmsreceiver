package com.test.ansmsreceiver;

import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BaseSMSReceiver {

	private static String TAG = "SMSReceiver";
	@Override
	protected void onSmsReceived(SmsMessage[] messages) {
		// TODO Auto-generated method stub
		Log.v(TAG, " ** "+messages[0].getDisplayMessageBody());
	}

}
