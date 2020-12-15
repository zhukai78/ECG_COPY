package com.hopetruly.part.net;

import com.hopetruly.ecg.entity.APPUploadFile;
import com.hopetruly.ecg.entity.ErrorInfo;
import com.hopetruly.ecg.entity.UserInfo;
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.ecg.util.StreamToByteUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/* renamed from: com.hopetruly.part.net.b */
public class MyHttpHelper {

    /* renamed from: a */
    private static boolean isHttpDebug = false;

    /* renamed from: a */
    public static String checkLogin() {
        return postMyUrl("http://www.bitsun.com/cloud/usermanage/check_login.php", new String[0], new String[0]);
    }

    /* renamed from: a */
    public static String get_user_info(UserInfo userInfo) {
        return postMyUrl("http://www.bitsun.com/cloud/usermanage/user_info.php?type=modi", new String[]{"user_first_name", "user_last_name", "user_sex", "user_age", "user_birthday", "user_height", "user_weight", "user_profession", "user_email", "user_phone", "user_addr", "user_medications", "user_smoker"}, new String[]{userInfo.getFirstName(), userInfo.getLastName(), userInfo.getSex(), String.valueOf(userInfo.getAge()), userInfo.getBirthday(), userInfo.getHeight(), userInfo.getWeight(), userInfo.getProfession(), userInfo.getEmail(), userInfo.getPhone(), userInfo.getAddress(), userInfo.getMedications(), userInfo.getSmoker()});
    }

    /* renamed from: a */
    public static String m2873a(String str) {
        return postMyUrl("http://www.bitsun.com/cloud/apps/ecg/ecg_file_list.php", new String[]{"userId"}, new String[]{str});
    }

    /* renamed from: a */
    public static String get_ecg_file_list(String str, String str2) {
        if (!str.equals("test") || !str2.equals("test")) {
            isHttpDebug = false;
            return postMyUrl("http://www.bitsun.com/cloud/usermanage/login_ajax.php", new String[]{"user_name", "user_pwd"}, new String[]{str, str2});
        }
        isHttpDebug = true;
        return "[0, 2, [\"23603115785715725\"]]";
    }

    /* renamed from: a */
    public static String getMyUrl(String str, String str2, String str3) {
        try {
            HttpGet httpGet = new HttpGet(str);
            HttpResponse execute = MyHttpClient.initMyHttpClient().execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                InputStream content = execute.getEntity().getContent();
                if (content != null) {
                    File file = new File(str2);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(file, str3);
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = content.read(bArr);
                        if (read != -1) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            content.close();
                            return file2.getAbsolutePath();
                        }
                    }
                }
                return null;
            }
            LogUtils.logI("NetInterface", "Connection release..");
            httpGet.abort();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (SocketTimeoutException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    public static String postMyUrl(String str, String[] strArr, String[] strArr2) {
        HttpPost httpPost = new HttpPost(str);
        if (strArr.length != strArr2.length) {
            throw new IllegalArgumentException("参数与值的数目不相等!");
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < strArr2.length; i++) {
            if (strArr[i] != null) {
                arrayList.add(new BasicNameValuePair(strArr[i], strArr2[i]));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "utf-8"));
            HttpResponse execute = MyHttpClient.initMyHttpClient().execute(httpPost);
            if (execute.getStatusLine().getStatusCode() == 200) {
                for (Cookie cookie : MyHttpClient.initMyHttpClient().getCookieStore().getCookies()) {
                    LogUtils.logI("NetInterface", "cookie name>>" + cookie.getName() + "\ncookie value>>" + cookie.getValue() + "\ncookie Domain>>" + cookie.getDomain());
                }
                return new String(StreamToByteUtils.stoBytes(execute.getEntity().getContent()), Charset.forName("UTF-8"));
            }
            LogUtils.logI("NetInterface", "Connection release..");
            httpPost.abort();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e2) {
            e2.printStackTrace();
            return null;
        } catch (SocketTimeoutException e3) {
            e3.printStackTrace();
            LogUtils.logW("NetInterface", "socket time out");
            return null;
        } catch (IOException e4) {
            e4.printStackTrace();
            return null;
        } catch (Exception e5) {
            e5.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    public static String httpPostUrl(String str, String[] strArr, String[] strArr2, String[] strArr3, File[] fileArr) throws IOException, UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(str);
        boolean z = true;
        boolean z2 = strArr.length != strArr2.length;
        if (strArr3.length == fileArr.length) {
            z = false;
        }
        if (z || z2) {
            throw new IllegalArgumentException("参数与值的数目不相等!");
        }
        MultipartEntity multipartEntity = new MultipartEntity();
        int i = 0;
        while (i < strArr2.length) {
            try {
                if (strArr[i] != null) {
                    multipartEntity.addPart(strArr[i], new StringBody(strArr2[i], Charset.forName("UTF-8")));
                }
                i++;
            } catch (IOException e4) {
                e4.printStackTrace();
                return null;
            } catch (Exception e5) {
                e5.printStackTrace();
                return null;
            }
        }
        for (int i2 = 0; i2 < fileArr.length; i2++) {
            if (strArr3 != null) {
                multipartEntity.addPart(strArr3[i2], new FileBody(fileArr[i2]));
            }
        }
        httpPost.setEntity(multipartEntity);
        MyHttpClient a = MyHttpClient.initMyHttpClient();
        HttpParams params = a.getParams();
        HttpConnectionParams.setSoTimeout(params, 360000);
        a.setParams(params);
        HttpResponse execute = a.execute(httpPost);
        if (execute.getStatusLine().getStatusCode() == 200) {
            return new String(StreamToByteUtils.stoBytes(execute.getEntity().getContent()), Charset.forName("UTF-8"));
        }
        LogUtils.logI("NetInterface", "Connection release..");
        httpPost.abort();
        return null;
    }

    /* renamed from: a */
    public static boolean get_error_report(ErrorInfo errorInfo) {
        String a = postMyUrl("http://www.bitsun.com/cloud/apps/error_report/rec_error_report.php", new String[]{"soft_version", "soft_name", "soft_id", "phone_brand", "phone_model", "phone_cpu_abi", "system_ver", "firmware_ver", "opt_dec", "error_info", "report_time"}, new String[]{errorInfo.getVersionName(), "ECG Air", "1", errorInfo.getManufacturer(), errorInfo.getModel(), errorInfo.getCPU_ABI(), errorInfo.getAndroidVer(), errorInfo.getFirmwareVer(), errorInfo.getOpDesc(), errorInfo.getErrorInfo(), errorInfo.getTime()});
        if (a == null) {
            return false;
        }
        try {
            return new JSONArray(a).getInt(0) == 0;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* renamed from: b */
    public static String get_q_user_info() {
        LogUtils.logE("NetInterface", "testFlag:" + isHttpDebug);
        return isHttpDebug ? getTestUser() : postMyUrl("http://www.bitsun.com/cloud/usermanage/user_info.php?type=query", new String[0], new String[0]);
    }

    /* renamed from: b */
    public static String register_ajax(String str, String str2) {
        return postMyUrl("http://www.bitsun.com/cloud/usermanage/register_ajax.php", new String[]{"user_name", "user_pwd", "user_re_pwd"}, new String[]{str, str2, str2});
    }

    /* renamed from: b */
    public static String upload_file(String str, String str2, String str3) throws IOException {
        String[] strArr = new String[2];
        String[] strArr2 = new String[2];
        if (str3 != null) {
            strArr[0] = "fileType";
            strArr2[0] = str;
            strArr[1] = "machineId";
            strArr2[1] = str3;
        } else {
            strArr[0] = "fileType";
            strArr2[0] = str;
        }
        return httpPostUrl("http://www.bitsun.com/cloud/upload/upload_file.php", strArr, strArr2, new String[]{"file"}, new File[]{new File(str2)});
    }

    /* renamed from: c */
    public static APPUploadFile check_update() {
        String a = postMyUrl("http://www.bitsun.com/cloud/downloads/check_update.php?soft_id=ecg", new String[0], new String[0]);
        APPUploadFile fVar = null;
        if (a == null) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(a);
            if (jSONArray.getInt(0) == 0) {
                JSONArray jSONArray2 = jSONArray.getJSONArray(2);
                APPUploadFile fVar2 = new APPUploadFile();
                try {
                    fVar2.mo2695a(jSONArray2.getString(0));
                    fVar2.mo2697b(jSONArray2.getString(1));
                    return fVar2;
                } catch (JSONException e) {
                    e = e;
                    fVar = fVar2;
                    e.printStackTrace();
                    return fVar;
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            return fVar;
        }
        return fVar;
    }

    /* renamed from: c */
    public static String mark_ecg_air_ids(String str, String str2) {
        return postMyUrl("http://www.bitsun.com/cloud/upload/mark_ecg_air_ids.php", new String[]{"machineId", "userName"}, new String[]{str, str2});
    }

    /* renamed from: d */
    private static String getTestUser() {
        return "[0,3,{\"user_id\": \"23603115785715725\", \"user_first_name\": \"Test\",\"user_last_name\": \"Warick\",\"user_sex\": \"M\",\"user_age\": \"30\",\"user_birthday\": \"1986-2-2\",\"user_height\": \"180\",\"user_weight\": \"100\",\"user_profession\": \"Analyst\",\"user_email\": \"waricksale@163.com\",\"user_phone\": \"10000\",\"user_addr\": \"Room 208, Building C2, No.182 of Kexue Avenue,Science Town of Luoguang District,Guangzhou, Guangdong, China\"}]";
    }
}
