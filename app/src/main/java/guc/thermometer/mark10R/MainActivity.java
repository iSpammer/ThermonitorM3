package guc.thermometer.mark10R;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private EditText mailTxt;
    private EditText password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mailTxt = findViewById(R.id.mail);
        password = findViewById(R.id.passw);

        Button regBtn = findViewById(R.id.regBtn);

        progressBar = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

        regBtn.setPaintFlags(regBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            password.setAutofillHints(View.AUTOFILL_HINT_PASSWORD);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#d32f2f"));

        MobileAds.initialize(this, getString(R.string.admob_app_id));

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void regBtnOnClick(View view) {
        Intent intentReg = new Intent(this, RegisterActivity.class);
        if (isEmpty(mailTxt)) {
            intentReg.putExtra("mail", mailTxt.getText().toString());
        }
        if (isEmpty(password)) {
            intentReg.putExtra("pw", password.getText().toString());
        }
        startActivity(intentReg);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    public void loginClick(View view) {
        if (isEmpty(mailTxt) && isEmpty(password)) {
            String mail = mailTxt.getText().toString();
            String pw = password.getText().toString();
            userLogin(mail, pw);

        } else {
            Toast.makeText(this, R.string.checkCredent, Toast.LENGTH_LONG).show();
        }
    }

    public void tstImage(View v) {
        Intent tst = new Intent(this, FirsttimesetupActivity.class);
        startActivity(tst);
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
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ispamossama@gmail.com"});
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
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ispamossama@gmail.com"});
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
                                startActivity(i);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case R.id.register:
                Intent intentReg = new Intent(this, RegisterActivity.class);
                if (!isEmpty(mailTxt)) {
                    intentReg.putExtra("mail", mailTxt.getText().toString());
                }
                if (!isEmpty(password)) {
                    intentReg.putExtra("pw", password.getText().toString());
                }
                startActivity(intentReg);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                break;
        }
        return true;
    }

    private boolean isEmpty(EditText etText) {
        return !(etText.getText().toString().trim().length() == 0);
    }

    public void userLogin(String mail, String passw) {
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mailTxt.setError(R.string.incorrectmail + "");
            mailTxt.requestFocus();
            return;
        }
        if (passw.length() < 6) {
            password.setError(R.string.incorrectpw + "");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mail, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getBoolean("isFirstRun", true);

                    if (isFirstRun) {
                        //show start activity

                        startActivity(new Intent(MainActivity.this, FirsttimesetupActivity.class));
                        Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                                .show();
                        finish();
                    }


                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).apply();

                    if (!isFirstRun) {
                        Intent intent = new Intent(getApplicationContext(), loggedActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                    }
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), loggedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            finish();
        }
    }
}
