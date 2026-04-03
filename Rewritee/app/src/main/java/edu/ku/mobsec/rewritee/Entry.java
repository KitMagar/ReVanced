package edu.ku.mobsec.rewritee;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class Entry extends Activity {
    public static String SHA1(String text) {
        try {
            MessageDigest msg = MessageDigest.getInstance("SHA-1");

            byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
            msg.update(textBytes, 0, textBytes.length);
            byte[] digest = msg.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            StackTraceElement[] arr = e.getStackTrace();
            Log.e("REWRITEE", "Exception " + Arrays.toString(arr));
            return null;
        }
    }

    //This is called from the RetrieverThread
    public void checkKey(String key){
        String keyHash = SHA1(key);
        if (keyHash.equals("893c32a96c23200939e8c3706f4e1b727de94eb8")){
            Log.d("REWRITEE", "key is correct");
        }
    }

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);

        if (authenticated()){
            new RetrieverThread(this).start();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Not authenticated").setTitle("Message");
            builder.create().show();
        }
    }

    public boolean authenticated(){
        return false;
    }
}
