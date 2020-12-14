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
import com.hopetruly.ecg.util.LogUtils;

import java.util.Locale;

/* renamed from: com.hopetruly.ecg.activity.c */
public class AboutEcgFragment extends Fragment {

    /* renamed from: a */
    final String f2712a = "EcgKnowledgeActivity";
    /* access modifiers changed from: private */

    /* renamed from: b */
    public WebView abp_ecg_webView1;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public TextView tv_tips_text;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public ProgressBar pb_progressBar_loading;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public boolean isAbpUrlConn = true;

    /* renamed from: a */
    private void initView() {
        WebView webView;
        String str = "";
        this.tv_tips_text = (TextView) getView().findViewById(R.id.tips_text);
        this.tv_tips_text.setVisibility(View.INVISIBLE);
        this.pb_progressBar_loading = (ProgressBar) getView().findViewById(R.id.progressBar_loading);
        this.abp_ecg_webView1 = (WebView) getView().findViewById(R.id.webView1);
        this.abp_ecg_webView1.getSettings().setUseWideViewPort(true);
        this.abp_ecg_webView1.getSettings().setLoadWithOverviewMode(true);
        this.abp_ecg_webView1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.abp_ecg_webView1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0 || i != 4 || !AboutEcgFragment.this.abp_ecg_webView1.canGoBack()) {
                    return false;
                }
                AboutEcgFragment.this.abp_ecg_webView1.goBack();
                return true;
            }
        });
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals("zh")) {
            webView = this.abp_ecg_webView1;
            str = "http://www.bitsun.com/ecg_knows/ch/ecg.html";
        } else {
            if (locale.getLanguage().equals("en")) {
                webView = this.abp_ecg_webView1;
                str = "http://www.bitsun.com/ecg_knows/en/ecg.html";
            }
            this.abp_ecg_webView1.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView webView, int i, String str, String str2) {
                    super.onReceivedError(webView, i, str, str2);
                    AboutEcgFragment.this.abp_ecg_webView1.findViewById(R.id.webView1).setVisibility(View.GONE);
                    AboutEcgFragment.this.tv_tips_text.setVisibility(View.VISIBLE);
                    AboutEcgFragment.this.pb_progressBar_loading.findViewById(R.id.progressBar_loading).setVisibility(View.GONE);
                    boolean unused = AboutEcgFragment.this.isAbpUrlConn = false;
                }

                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    webView.loadUrl(str);
                    return true;
                }
            });
            this.abp_ecg_webView1.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView webView, int i) {
                    if (i == 100) {
                        AboutEcgFragment.this.pb_progressBar_loading.setVisibility(View.INVISIBLE);
                        if (AboutEcgFragment.this.isAbpUrlConn) {
                            AboutEcgFragment.this.abp_ecg_webView1.findViewById(R.id.webView1).setVisibility(View.VISIBLE);
                            return;
                        }
                        return;
                    }
                    AboutEcgFragment.this.pb_progressBar_loading.setVisibility(View.VISIBLE);
                }
            });
        }
        abp_ecg_webView1.loadUrl(str);
        this.abp_ecg_webView1.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                AboutEcgFragment.this.abp_ecg_webView1.findViewById(R.id.webView1).setVisibility(View.GONE);
                AboutEcgFragment.this.tv_tips_text.setVisibility(View.VISIBLE);
                AboutEcgFragment.this.pb_progressBar_loading.findViewById(R.id.progressBar_loading).setVisibility(View.GONE);
                boolean unused = AboutEcgFragment.this.isAbpUrlConn = false;
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
        this.abp_ecg_webView1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    AboutEcgFragment.this.pb_progressBar_loading.setVisibility(View.INVISIBLE);
                    if (AboutEcgFragment.this.isAbpUrlConn) {
                        AboutEcgFragment.this.abp_ecg_webView1.findViewById(R.id.webView1).setVisibility(View.VISIBLE);
                        return;
                    }
                    return;
                }
                AboutEcgFragment.this.pb_progressBar_loading.setVisibility(View.VISIBLE);
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
        LogUtils.logE("EcgKnowledgeActivity", "onDestory~~");
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_reflash) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.tv_tips_text.setVisibility(View.INVISIBLE);
        this.abp_ecg_webView1.reload();
        this.isAbpUrlConn = true;
        return true;
    }

    public void onStart() {
        LogUtils.logE("EcgKnowledgeActivity", "onStart~~");
        initView();
        super.onStart();
    }
}
