package jainakshat.in.housesupplies;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Akshat Jain on 10/30/2015.
 */
public class sendNotif {

    void notif(String item) {
        new sendToServer().execute("http://jainakshat.in/HSApp/send_notif.php?regId=APA91bElvHSrU6S7wBRgZz6Dy0RwKyO3ecF1m1XLF4g52dplD5rRvXRva8ZyiPWSoeGctluYudbrKuZmjY4il5UEr3tVATYMMaL9as16orCGt-ey257atmzevSk0aMpZ2qUI9M72j5RfNHYHCAf5Kh1hbhPWZ2wIGA&message="+item+")");
    }

    private class sendToServer extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... url) {
            url[0] = url[0].replace(" ", "%20");
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(url[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Used for reading the result and attaching a callback by returning a value.
            if (result != null) {
                if (result.equals("success")) { } else { }
            }else{ }

        }

    }
}
