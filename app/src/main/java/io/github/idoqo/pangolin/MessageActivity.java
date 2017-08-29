package io.github.idoqo.pangolin;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

public class MessageActivity extends AppCompatActivity {
    private static final String URL_HNGFUN_PROFILE = "http://hng.fun/profile/pangolin.php";
    private static final String KEY_LOCATION_HEADER = "location";

    private final OkHttpClient httpClient = new OkHttpClient();

    private Button sendButton;
    private EditText messageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageET = findViewById(R.id.message_edit_text);

        sendButton = findViewById(R.id.send_message_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageET.getText().toString();
                sendButton.setText(R.string.process_sending);
                if (message.isEmpty()) {
                    Toast.makeText(MessageActivity.this, R.string.error_empty_message, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    MessageSenderTask senderTask = new MessageSenderTask();
                    senderTask.execute(message);
                }
            }
        });
    }

    private class MessageSenderTask extends AsyncTask<String, Void, Response> {
        protected Response doInBackground(String... params) {
            String message = params[0];
            Log.i("MessageActivity", "sending message: "+message);

            RequestBody formBody = new FormBody.Builder()
                    .add("name", "michaels")
                    .add("message", message)
                    .build();
            try {
                return HttpHelper.POST(httpClient, URL_HNGFUN_PROFILE, formBody);
            } catch (IOException ioe) {
                Log.e("MessageActivity", ioe.getLocalizedMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (response == null || !response.isSuccessful()) {
                Log.e("MessageActivity", "Error while sending data");
                makeSnackbarResult("Error while sending data");
            } else {
                String locationHeader = response.header(KEY_LOCATION_HEADER);
                /*if (locationHeader != null && locationHeader.startsWith("/sendmail.php")) {
                    makeSnackbarResult("Message sent successfully :D");
                } else {
                    Log.d("MessageSenderTask", response.headers().toString());
                    makeSnackbarResult("Message sending failed :(");
                }*/
                //todo fix locationHeader is always null...
                Toast.makeText(MessageActivity.this.getApplicationContext(),
                        "Message sent successfully \uD83D\uDD25\uD83D\uDD25", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    private void makeSnackbarResult(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private static class HttpHelper {
        public static Response POST(OkHttpClient client, String url, RequestBody body)
                throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            return client.newCall(request).execute();
        }
    }
}
