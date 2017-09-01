package io.github.idoqo.pangolin;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton githubButton;
    private ImageButton twitterButton;
    private ImageButton slackButton;
    private ImageButton emailButton;
    private ImageButton bioButton;

    private static final String GITHUB_PROFILE_URL = "https://github.com/idoqo";
    private static final String TWITTER_PROFILE_URL = "https://twitter.com/jordan__zzz";
    private static final String SLACK_PROFILE_URL = "https://hnginterns.slack.com/team/pangolin";
    private static final String DEV_ADDR = "radpangolin@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();
    }

    private void initUI() {
        githubButton = findViewById(R.id.button_github);
        twitterButton = findViewById(R.id.button_twitter);
        slackButton = findViewById(R.id.button_slack);
        emailButton = findViewById(R.id.button_mail);
        bioButton = findViewById(R.id.button_bio);

        githubButton.setOnClickListener(new LinkButtonClickListener(GITHUB_PROFILE_URL));
        twitterButton.setOnClickListener(new LinkButtonClickListener(TWITTER_PROFILE_URL));
        slackButton.setOnClickListener(new LinkButtonClickListener(SLACK_PROFILE_URL));

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lauchMessageActivity();
            }
        });
        bioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBioDialog();
            }
        });
    }

    private void lauchMessageActivity() {
        Intent openMessageUI = new Intent(ProfileActivity.this, MessageActivity.class);
        startActivity(openMessageUI);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    private class LinkButtonClickListener implements View.OnClickListener
    {
        private String linkToOpen;

        public LinkButtonClickListener(String url) {
            linkToOpen = url;
        }

        @Override
        public void onClick(View view) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(linkToOpen));
            startActivity(browserIntent);
        }
    }

    private void showBioDialog() {
        AlertDialog.Builder bioBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        bioBuilder.setView(inflater.inflate(R.layout.dialog_bio, null));

        AlertDialog bioDialog = bioBuilder.create();
        bioBuilder.show();
    }
}
