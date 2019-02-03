package guc.thermometer.mark10R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        String url = "https://ispammer.github.io/site/";

        WebView webview = view.findViewById(R.id.myWebView);
        progressBar = view.findViewById(R.id.progressBarWV);
        //next line explained below
        webview.setWebViewClient(new MyWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);

        return view;
    }

    public class MyWebViewClient extends WebViewClient {

        public MyWebViewClient() {
            super();
            //start anything you need to
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
