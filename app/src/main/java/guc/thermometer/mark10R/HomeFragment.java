package guc.thermometer.mark10R;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private static final String ARG_TEXT = "argText";
    static final String[] list = {"Android", "iPhone", "Windows", "BlackBerru", "Linux"};
    private String mail;


    public static HomeFragment newInstance(String text) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceeState) {
        super.onCreate(savedInstanceeState);
        if (getArguments() != null) {
            mail = getArguments().getString(ARG_TEXT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (getArguments() != null) {
            mail = getArguments().getString(ARG_TEXT);
        }

        TextView tvmail = view.findViewById(R.id.tvmail);
        tvmail.setText("Hello " + mail);


        ListView listView = view.findViewById(R.id.listviewLV);
        //     ListAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);

        ListAdapter adapter = new customAdapter(getActivity(), list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String clicked = String.valueOf(parent.getItemAtPosition(position));
                        infoFragment InfoFragment = infoFragment.newInstance(clicked);
                        replaceFragmentWithAnimation(InfoFragment,null);
                    }
                }
        );
        return view;
    }


    public void replaceFragmentWithAnimation(Fragment fragment, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
}