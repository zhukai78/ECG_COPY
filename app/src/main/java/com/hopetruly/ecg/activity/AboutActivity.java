package com.hopetruly.ecg.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hopetruly.ecg.R;

import org.xml.sax.XMLReader;

import java.util.Locale;

public class AboutActivity extends BaseActivity {

    /* renamed from: a */
    TextView about_rich_text;

    /* renamed from: com.hopetruly.ecg.activity.AboutActivity$a */
    class MyClickableSpan extends ClickableSpan {

        /* renamed from: a */
        String spstr;

        public MyClickableSpan(String str) {
            this.spstr = str;
        }

        public void onClick(View view) {
            Toast.makeText(AboutActivity.this.getApplicationContext(), this.spstr, Toast.LENGTH_SHORT).show();
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.AboutActivity$b */
    class MTagHandler implements Html.TagHandler {

        /* renamed from: a */
        int editableLen;

        /* renamed from: b */
        int editableLen2;

        MTagHandler() {
        }

        public void handleTag(boolean z, String str, Editable editable, XMLReader xMLReader) {
            if (!str.toLowerCase(Locale.CHINA).equals("phone")) {
                return;
            }
            if (z) {
                this.editableLen = editable.length();
                return;
            }
            this.editableLen2 = editable.length();
            editable.setSpan(new MyClickableSpan(editable.subSequence(this.editableLen, this.editableLen2).toString()), this.editableLen, this.editableLen2, 33);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_about);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.about_rich_text = (TextView) findViewById(R.id.about_rich_text);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<p>");
        stringBuffer.append(getString(R.string.app_name));
        stringBuffer.append(" V");
        try {
            stringBuffer.append(getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName);
            stringBuffer.append("</p>");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            stringBuffer.append("</p>");
        }
        stringBuffer.append("<p><strong>广州沃里医疗科技有限公司</strong></p>");
        stringBuffer.append("<p><strong>Guangzhou Warick Medical Technologies Co., LTD</strong></p>");
        stringBuffer.append("地址:广州市萝岗区科学城科学大道182号创新大厦C2-208室<br />");
        stringBuffer.append("ADDRESS:Room 208, Building C2, No.182 of Kexue Avenue,Science Town of Luoguang District,Guangzhou, Guangdong, China<br />");
        stringBuffer.append("邮政编号:510663<br />");
        stringBuffer.append("POSTCODE:510663<br />");
        stringBuffer.append("网站：<a href='http://www.bitsun.com' target='_blank'>http://www.bitsun.com</a> <br />");
        stringBuffer.append("WEBSITE:<a href='http://www.bitsun.com' target='_blank'>http://www.bitsun.com</a> <br />");
        stringBuffer.append("电话：<phone>+86 020-62880399</phone> <br />");
        stringBuffer.append("TEL: <phone>+86 020-62880399</phone> <br />");
        this.about_rich_text.setText(Html.fromHtml(stringBuffer.toString(), (Html.ImageGetter) null, new MTagHandler()));
        this.about_rich_text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
