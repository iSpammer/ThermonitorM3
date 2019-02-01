package guc.thermometer.mark10R;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private EditText mailTxt;
    private EditText password;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mailTxt = findViewById(R.id.mail);
        password = findViewById(R.id.passw);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            password.setAutofillHints(View.AUTOFILL_HINT_PASSWORD);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#d32f2f"));
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void loginClick(View view) {
        Intent intent = new Intent(this, loggedActivity.class);
        String msg = mailTxt.getText().toString();
        if (!"".equals(msg)) {
            intent.putExtra(EXTRA_MESSAGE, msg);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
            finish();
        }
        else{
            Toast.makeText(this, "Please enter a correct mail",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Contact:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "ispamossama@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Mark10 Support");
                intent.putExtra(Intent.EXTRA_TEXT, R.string.placeholdertxt);
                startActivity(Intent.createChooser(intent, ""));
                break;
            case R.id.About:
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle(R.string.about)
                        .setMessage(R.string.about_text)
                        .setPositiveButton(R.string.contact, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("plain/text");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "ispamossama@gmail.com" });
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Mark10 Support");
                                intent.putExtra(Intent.EXTRA_TEXT, R.string.placeholdertxt);
                                startActivity(Intent.createChooser(intent, ""));
                            }
                        })
                        .setNegativeButton(R.string.github, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "https://github.com/iSpammer/";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case R.id.register:
                Intent intentreg = new Intent(this, RegisterActivity.class);
                intentreg.putExtra("mail", mailTxt.getText().toString());
                intentreg.putExtra("pw", password.getText().toString());
                startActivity(intentreg);
                overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
                break;
        }
        return true;
    }
}
