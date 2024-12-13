package com.nguyenhungtuan.lab6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class SmsReceiver extends BroadcastReceiver {

    private static final String AUTO_RESPONSE_KEY = "auto_response";

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            // Lấy dữ liệu tin nhắn
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getDisplayOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();

                        // Kiểm tra nếu có tin nhắn yêu cầu phản hồi
                        if (messageBody.toLowerCase().contains("are you ok")) {
                            // Kiểm tra nếu Auto Response đang bật
                            boolean autoResponse = PreferenceManager.getDefaultSharedPreferences(context)
                                    .getBoolean(AUTO_RESPONSE_KEY, false);
                            if (autoResponse) {
                                respondToSender(context, sender, true);
                            }
                        }
                    }
                }
            }
        }
    }

    private void respondToSender(Context context, String recipient, boolean isSafe) {
        String message = isSafe
                ? "I am safe and well, worry not."
                : "Tell my mother I love her.";

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(recipient, null, message, null, null);
        Toast.makeText(context, "Sent to: " + recipient + ", Message: " + message, Toast.LENGTH_SHORT).show();
    }
}
