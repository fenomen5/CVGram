package com.barkov.ais.cvgram;

import android.util.Log;

import com.barkov.ais.cvgram.mail.GMailSender;

public class EmailHelper {

    public boolean send(final String recipient, final  String subject, final String body)
    {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("antnorv94@kmail.com",
                            "");
                    sender.sendMail(subject, body,
                            "antnorv94@kmail.com", recipient);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
        return true;
    }
}
