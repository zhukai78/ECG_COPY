package com.hopetruly.ecg.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hopetruly.ecg.R;
import com.hopetruly.ecg.util.C0771g;

import java.util.Locale;

/* renamed from: com.hopetruly.ecg.activity.c */
public class C0730c extends Fragment {

    /* renamed from: a */
    final String f2712a = "EcgKnowledgeActivity";
    /* access modifiers changed from: private */

    /* renamed from: b */
    public WebView f2713b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public TextView f2714c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public ProgressBar f2715d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public boolean f2716e = true;

    /* renamed from: a */
    private void m2577a() {
        WebView webView;
        String str = "";
        this.f2714c = (TextView) getView().findViewById(R.id.tips_text);
        this.f2714c.setVisibility(View.INVISIBLE);
        this.f2715d = (ProgressBar) getView().findViewById(R.id.progressBar_loading);
        this.f2713b = (WebView) getView().findViewById(R.id.webView1);
        this.f2713b.getSettings().setUseWideViewPort(true);
        this.f2713b.getSettings().setLoadWithOverviewMode(true);
        this.f2713b.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.f2713b.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0 || i != 4 || !C0730c.this.f2713b.canGoBack()) {
                    return false;
                }
                C0730c.this.f2713b.goBack();
                return true;
            }
        });
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals("zh")) {
            webView = this.f2713b;
            str = "http://www.bitsun.com/ecg_knows/ch/ecg.html";
        } else {
            if (locale.getLanguage().equals("en")) {
                webView = this.f2713b;
                str = "http://www.bitsun.com/ecg_knows/en/ecg.html";
            }
            this.f2713b.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView webView, int i, String str, String str2) {
                    super.onReceivedError(webView, i, str, str2);
                    C0730c.this.f2713b.findViewById(R.id.webView1).setVisibility(View.GONE);
                    C0730c.this.f2714c.setVisibility(View.VISIBLE);
                    C0730c.this.f2715d.findViewById(R.id.progressBar_loading).setVisibility(View.GONE);
                    boolean unused = C0730c.this.f2716e = false;
                }

                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    webView.loadUrl(str);
                    return true;
                }
            });
            this.f2713b.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView webView, int i) {
                    if (i == 100) {
                        C0730c.this.f2715d.setVisibility(View.INVISIBLE);
                        if (C0730c.this.f2716e) {
                            C0730c.this.f2713b.findViewById(R.id.webView1).setVisibility(View.VISIBLE);
                            return;
                        }
                        return;
                    }
                    C0730c.this.f2715d.setVisibility(View.VISIBLE);
                }
            });
        }
        f2713b.loadUrl(str);
        this.f2713b.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                C0730c.this.f2713b.findViewById(R.id.webView1).setVisibility(View.GONE);
                C0730c.this.f2714c.setVisibility(View.VISIBLE);
                C0730c.this.f2715d.findViewById(R.id.progressBar_loading).setVisibility(View.GONE);
                boolean unused = C0730c.this.f2716e = false;
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
        this.f2713b.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    C0730c.this.f2715d.setVisibility(View.INVISIBLE);
                    if (C0730c.this.f2716e) {
                        C0730c.this.f2713b.findViewById(R.id.webView1).setVisibility(View.VISIBLE);
                        return;
                    }
                    return;
                }
                C0730c.this.f2715d.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onCreate(Bundle bundle) {
        setHasOptionsMenu(true);
        super.onCreate(bundle);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        getActivity().getMenuInflater().inflate(R.menu.ecg_about, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_ecg_knowledge, viewGroup, false);
    }

    public void onDestroy() {
        C0771g.m2787d("EcgKnowledgeActivity", "onDestory~~");
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_reflash) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.f2714c.setVisibility(View.INVISIBLE);
        this.f2713b.reload();
        this.f2716e = true;
        return true;
    }

    public void onStart() {
        C0771g.m2787d("EcgKnowledgeActivity", "onStart~~");
        m2577a();
        super.onStart();
    }
}
