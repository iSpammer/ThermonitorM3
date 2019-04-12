package guc.thermometer.mark10R;

import android.content.Context;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.mikepenz.iconics.Iconics.TAG;


public class infoFragment extends Fragment {
    private static final String ARG_TEXT = "argText";
    private TextView tvinfo;
    private TextView chosenEsp;
    private String clicked;


    public static infoFragment newInstance(String text) {
        infoFragment InfoFragment = new infoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        InfoFragment.setArguments(args);
        return InfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceeState) {
        super.onCreate(savedInstanceeState);
//        if (getArguments() != null) {
//            clicked = getArguments().getString(ARG_TEXT);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        tvinfo = view.findViewById(R.id.tvinfo);
        chosenEsp = view.findViewById(R.id.chosenDev);

        tvinfo.setText("Loading...");
//        if (getArguments() != null) {
//            clicked = getArguments().getString(ARG_TEXT);
//        }
//        chosenEsp.setText(clicked);
        chosenEsp.setText(getArguments().getString("esp"));
//        onlyMacString(getArguments().getString("esp"));
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(onlyMacString(getArguments().getString("esp")));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long s = dataSnapshot.getValue(Long.class);
                tvinfo.setText("Temperature in this node is: " + s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                tvinfo.setText(databaseError.getMessage());
            }
        });


        return view;

    }


    private String onlyMacString(String str) {
        String res = "";
//        for(char c: str.toCharArray()){
//            if(c == "M")
//        }
        str = str.toUpperCase();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'M') {
                i++;
                if (str.charAt(i) == 'A') {
                    i++;
                    if (str.charAt(i) == 'C') {
                        i++;
                        if (str.charAt(i) == ':') {
                            i += 2;
                            int t = i;
                            for (; i < str.length(); i++) {
                                if (i == t + 1)
                                    res = res + (char) ((int) str.charAt(i) - 2);
                                else {
                                    res = res + (str.charAt(i));

                                }
                            }
                            System.out.println("FOUNDIT: " + res);
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        getWindow().setExitTransition(new Explode());
//
//    }

}
