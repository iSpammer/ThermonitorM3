package guc.thermometer.mark10R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class RegisterActivity extends AppCompatActivity {
    private EditText usernametv;
    private EditText mailtv;
    private EditText passwordtv;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernametv = findViewById(R.id.username);
        mailtv = findViewById(R.id.mail);
        passwordtv = findViewById(R.id.passw);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#d32f2f"));
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        String mail = extras.getString("mail");
        String password = extras.getString("pw");


        if (!(mail.isEmpty() || password.isEmpty())) {
            mailtv.setText(mail);
            passwordtv.setText(password);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_down,R.anim.slide_up);

    }
}
