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
public class MyHttpClient extends DefaultHttpClient {

    /* renamed from: a */
    private static MyHttpClient myHttpClient;

    /* renamed from: b */
    private int f2987b = 0;

    public MyHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
    }

    /* renamed from: a */
    public static synchronized MyHttpClient initMyHttpClient() {
        synchronized (MyHttpClient.class) {
            if (myHttpClient == null) {
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
                myHttpClient = new MyHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                MyHttpClient aVar = myHttpClient;
                return aVar;
            }
            HttpParams params = myHttpClient.getParams();
            HttpConnectionParams.setSoTimeout(params, 4000);
            myHttpClient.setParams(params);
            MyHttpClient aVar2 = myHttpClient;
            return aVar2;
        }
    }

    /* renamed from: b */
    public void closeHttpClient() {
        if (myHttpClient != null) {
            myHttpClient.getConnectionManager().shutdown();
            myHttpClient = null;
        }
    }
}
