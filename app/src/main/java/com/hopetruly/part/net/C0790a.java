package com.hopetruly.part.net;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/* renamed from: com.hopetruly.part.net.a */
public class C0790a extends DefaultHttpClient {

    /* renamed from: a */
    private static C0790a f2986a;

    /* renamed from: b */
    private int f2987b = 0;

    public C0790a(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
    }

    /* renamed from: a */
    public static synchronized C0790a m2869a() {
        synchronized (C0790a.class) {
            if (f2986a == null) {
                BasicHttpParams basicHttpParams = new BasicHttpParams();
                HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
                HttpProtocolParams.setUseExpectContinue(basicHttpParams, true);
                HttpProtocolParams.setUserAgent(basicHttpParams, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
                ConnManagerParams.setTimeout(basicHttpParams, 1000);
                HttpConnectionParams.setConnectionTimeout(basicHttpParams, 2000);
                HttpConnectionParams.setSoTimeout(basicHttpParams, 4000);
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                f2986a = new C0790a(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                C0790a aVar = f2986a;
                return aVar;
            }
            HttpParams params = f2986a.getParams();
            HttpConnectionParams.setSoTimeout(params, 4000);
            f2986a.setParams(params);
            C0790a aVar2 = f2986a;
            return aVar2;
        }
    }

    /* renamed from: b */
    public void mo2863b() {
        if (f2986a != null) {
            f2986a.getConnectionManager().shutdown();
            f2986a = null;
        }
    }
}
