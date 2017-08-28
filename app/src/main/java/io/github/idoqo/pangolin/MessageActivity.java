package io.github.idoqo.pangolin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;
import java.net.URLConnection;

public class MessageActivity extends AppCompatActivity {
    private static final String URL_HNGFUN_PROFILE = "http://hng.fun/profile/pangolin.php";
    private static final String KEY_LOCATION_HEADER = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }
}
