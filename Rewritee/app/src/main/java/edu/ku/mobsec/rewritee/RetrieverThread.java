package edu.ku.mobsec.rewritee;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RetrieverThread extends Thread{
    Entry e;

    public RetrieverThread(Entry e) {
        this.e  = e;
    }

    @Override
    public void run(){
        try {
            String urlStr = "https://people.eecs.ku.edu/~a807d786/key.html";
            URL url = new URL(urlStr);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            if (conn.getResponseCode() != 200){
                Log.e("RESPONSE", "RESPONSE " + conn.getResponseCode());
                String resp = conn.getResponseMessage();
            } else {
                String key = new Scanner(conn.getInputStream()).nextLine();
                e.checkKey(key);
            }
        } catch (Exception e){
            Log.d("TAG", "Error " + e);
        }
    }
}
