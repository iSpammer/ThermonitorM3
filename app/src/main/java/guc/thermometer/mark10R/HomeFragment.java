package guc.thermometer.mark10R;

import android.Manifest;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private static final String ARG_TEXT = "argText";
//    private static final String[] list = {"SQL", "Java", "JavaScript", "C#", "Python", "C++", "PHP", "Koltin"};

    private String mail;
    private ListView listView;
    private TextView tvmail;
    private WifiManager mainWifi;
    private WifiReceiver receiverWifi;
    private List<ScanResult> wifiList;
    private ArrayList<String> wifiRes = new ArrayList<String>();
    private StringBuilder sb = new StringBuilder();
    private StringBuilder csv = new StringBuilder();
    private boolean scanFinished = false;

    private Bundle args;


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
        args = new Bundle();
        tvmail = view.findViewById(R.id.tvmail);
        listView = view.findViewById(R.id.listesp);
        mainWifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        receiverWifi = new WifiReceiver();
        //getActivity().registerReceiver(receiverWifi, new IntentFilter(
        //        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mainWifi.startScan();
//        Log.i("HI", "HEY");
//        mainText.setText("Starting Scan...\n");
        if(scanFinished) {
//            CustomAdapter customAdapter = new CustomAdapter(getActivity().getApplicationContext(), wifiRes);
//            listView.setAdapter(customAdapter);
        }
        if (isEmailValid(mail)) {
            String stringFormater = getResources().getString(R.string.logedas, mail);
            tvmail.setText(stringFormater);
        } else {
            System.out.println(mail + "MAAAIL");
            String stringFormater = getResources().getString(R.string.Welcome, mail);
            tvmail.setText(stringFormater);
        }


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String clicked = String.valueOf(parent.getItemAtPosition(position));
                        infoFragment InfoFragment = infoFragment.newInstance(clicked);
                        args.putString("esp", clicked);
                        InfoFragment.setArguments(args);
                        replaceFragmentWithAnimation(InfoFragment, "");
                    }
                }
        );
        return view;
    }


    private void replaceFragmentWithAnimation(Fragment fragment, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    private static boolean isEmailValid(String email) {
        try {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            sb = new StringBuilder();
            csv = new StringBuilder();
            wifiList = mainWifi.getScanResults();
            Log.i("LIST", mainWifi.getScanResults().toString());
            sb.append("Number of APs Detected: ");
            sb.append((Integer.valueOf(wifiList.size())).toString());
            sb.append("\n\n");
            for (int i = 0; i < wifiList.size(); i++) {
                if(wifiList.get(i).SSID.equalsIgnoreCase("ESPap")) {
                    wifiRes.add("SSID: " + wifiList.get(i).SSID + "\n" + "MAC: " + wifiList.get(i).BSSID.toUpperCase());
                    System.out.println("FFOUNTIT "+wifiList.get(i).BSSID);
                }
            }

            scanFinished = true;
            CustomAdapter customAdapter = new CustomAdapter(getActivity().getApplicationContext(), wifiRes);
            listView.setAdapter(customAdapter);
        }
    }

    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 87);
        } else {
            getActivity().registerReceiver(receiverWifi, new IntentFilter(
                    WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        }

        // if (scanFinished == true) {
        // // wait until Wi-Fi scan is finished
        // // Handler handler = new Handler();
        // // handler.postDelayed(new Runnable() {
        // // public void run() {
        // // }
        // // }, 1000);
        // // To return results back to calling activity (e.g., MIT App
        // // Inventor App)
        // Intent scanResults = new Intent();
        // scanResults.putExtra("AP_LIST", sb.toString());
        // setResult(RESULT_OK, scanResults);
        // finish();
        // }
    }


//    public void onPause() {
//        super.onPause();
//        try {
//            getActivity().unregisterReceiver(receiverWifi);
//        } catch (IllegalArgumentException e) {
//
//        }
//
//
//        Intent scanResults = new Intent();
//        scanResults.putExtra("AP_LIST", csv.toString());
//        getActivity().setResult(RESULT_OK, scanResults);
//        getActivity().finish();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 87 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("LOOOL", "request accepted");
            getActivity().registerReceiver(receiverWifi, new IntentFilter(
                    WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        }
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
}