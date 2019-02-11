package guc.thermometer.mark10R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity{
    private EditText mailtv;
    private EditText passwordtv;
    private EditText usernametv;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mailtv = findViewById(R.id.mail);
        passwordtv = findViewById(R.id.passw);
        usernametv = findViewById(R.id.usereditText);
        progressBar = findViewById(R.id.progressBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#fafafa"));
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primaryTextColor));
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(ContextCompat.getColor(this,R.color.primaryTextColor), PorterDuff.Mode.SRC_ATOP);
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        mAuth = FirebaseAuth.getInstance();

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String mail = "";
        String password = "";
        Bundle extras = getIntent().getExtras();
        try {


            if (extras.getString("mail") != null)
                mail = extras.getString("mail");
                password = extras.getString("pw");
        }
        catch (Exception e){

        }

        if (mail != null && !(mail.isEmpty())) {
            mailtv.setText(mail);
        }
        if (password != null && !(password.isEmpty())) {
            passwordtv.setText(password);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            //handle the logged in user
        }
    }

    public void registerUser() {
        final String mail = mailtv.getText().toString();
        final String password = passwordtv.getText().toString();
        final String name = usernametv.getText().toString();

        if(name.isEmpty()){
            usernametv.setError("Username is required");
            usernametv.requestFocus();
            return;
        }

        if (mail.isEmpty()) {
            mailtv.setError(this.getText(R.string.incorrectmail));
            mailtv.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mailtv.setError(this.getText(R.string.incorrectmail));
            mailtv.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordtv.setError(this.getText(R.string.incorrectpw));
            passwordtv.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordtv.setError(this.getText(R.string.shortpw));
            passwordtv.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    try {
                        User user = new User(name, mail);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                    progressBar.setVisibility(View.GONE);
                    onBackPressed();
                    Toast.makeText(getApplicationContext(),R.string.regDone, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        Toast.makeText(getApplicationContext(),R.string.alreadyReg,Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void registerOnClick(View view) {
        registerUser();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);

    }
}
