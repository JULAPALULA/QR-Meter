package com.jdev.qrmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String index_path = "file:///android_asset/index.html";
    public static WebView webView = null;
    public static WebSettings webSettings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Para que la barra superior desaparezca
        getSupportActionBar().hide();

        try {
            //webview
            webView = findViewById(R.id.webview0);

            //Para que el webview funcione como un navegador Chrome

            webView.setWebViewClient(new WebViewClient());

            //Para dar permisos a la cámara del web view
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request.grant(request.getResources());
                        int hasCameraPermission = 0;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
                        }

                        List<String> permissions = new ArrayList<String>();

                        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                            permissions.add(Manifest.permission.CAMERA);
                        }
                        if (!permissions.isEmpty()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
                            }
                        }
                    }
                }
            });

            //Configuración del Web View
            webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setSupportMultipleWindows(true);
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setDisplayZoomControls(false);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setMediaPlaybackRequiresUserGesture(false);
            webSettings.setAllowContentAccess(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setPluginState(WebSettings.PluginState.ON);
            webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            webSettings.setUseWideViewPort(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setSupportZoom(true);
            webSettings.setDefaultTextEncodingName("utf-8");
            //Cargar el index
            webView.loadUrl(index_path);


        } catch (Exception e) {
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}