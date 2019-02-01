package guc.thermometer.mark10R;

import android.content.Context;
import android.os.Bundle;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class infoFragment extends Fragment {
    private static final String ARG_TEXT = "argText";
    TextView tvinfo;
    String clicked;


    public static infoFragment newInstance(String text){
        infoFragment InfoFragment = new infoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        InfoFragment.setArguments(args);
        return InfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceeState){
        super.onCreate(savedInstanceeState);
        if(getArguments()!=null){
            clicked = getArguments().getString(ARG_TEXT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        if(getArguments()!=null){
            clicked = getArguments().getString(ARG_TEXT);
        }
        tvinfo = view.findViewById(R.id.tvinfo);
        tvinfo.setText(clicked);
        return view;

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        getWindow().setExitTransition(new Explode());
//
//    }

}
