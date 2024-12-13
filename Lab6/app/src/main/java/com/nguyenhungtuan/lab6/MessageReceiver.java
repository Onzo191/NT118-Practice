package com.nguyenhungtuan.lab6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {

    private TextView tvContent;

    public MessageReceiver(TextView tvContent) {
        this.tvContent = tvContent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        final String SMS_EXTRA = "pdus";
        Object[] messages = (Object[]) bundle.get(SMS_EXTRA);

        String sms = "";

        for (Object pdu : messages) {
            SmsMessage smsMessage;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                smsMessage = SmsMessage.createFromPdu((byte[]) pdu, format);
            } else {
                smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
            }

            String address = smsMessage.getDisplayOriginatingAddress();
            String body = smsMessage.getMessageBody();

            sms += address + ":\n" + body + "\n";
        }

        tvContent.setText(sms);

        Toast.makeText(context, "Bạn có tin nhắn mới", Toast.LENGTH_LONG).show();
    }
}
