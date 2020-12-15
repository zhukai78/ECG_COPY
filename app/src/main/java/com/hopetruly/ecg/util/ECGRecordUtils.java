package com.hopetruly.ecg.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.entity.ECGEntity;
import com.hopetruly.ecg.entity.ECGRecord;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/* renamed from: com.hopetruly.ecg.util.f */
public class ECGRecordUtils {
    /* renamed from: a */
    public static ECGEntity m2773a(String str) throws ParserConfigurationException, IOException, SAXException {
        String str2;
        Object[] objArr;
        Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(new File(str)));
        ECGEntity eCGEntity = new ECGEntity();
        Element documentElement = parse.getDocumentElement();
        Element element = (Element) documentElement.getElementsByTagName("effectiveTime").item(0);
        eCGEntity.setStartTime(((Element) element.getElementsByTagName("low").item(0)).getAttribute("value"));
        eCGEntity.setEndTime(((Element) element.getElementsByTagName("high").item(0)).getAttribute("value"));
        Element element2 = (Element) ((Element) documentElement.getElementsByTagName("sequence").item(0)).getElementsByTagName("increment").item(0);
        eCGEntity.setTimeUnit(element2.getAttribute("unit"));
        eCGEntity.setTimeUnitValue(element2.getAttribute("value"));
        Element element3 = (Element) documentElement.getElementsByTagName("sequence").item(1);
        Element element4 = (Element) element3.getElementsByTagName("code").item(0);
        eCGEntity.setLead(element4.getAttribute("code"));
        if (element4.getAttribute("extension") != null) {
            eCGEntity.setLeadExten(element4.getAttribute("extension"));
        }
        Element element5 = (Element) ((Element) element3.getElementsByTagName("value").item(0)).getElementsByTagName("scale").item(0);
        eCGEntity.setScaleUnit(element5.getAttribute("unit"));
        eCGEntity.setScaleUnitValue(element5.getAttribute("value"));
        eCGEntity.setDigits(((Element) element3.getElementsByTagName("digits").item(0)).getTextContent());
        int[] a = m2783a(parse);
        eCGEntity.setMark_period(a);
        if (a != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < a.length; i += 2) {
                if (i == 0) {
                    str2 = "%d,%d";
                    objArr = new Object[]{Integer.valueOf(a[i]), Integer.valueOf(a[i + 1])};
                } else {
                    str2 = "|%d,%d";
                    objArr = new Object[]{Integer.valueOf(a[i]), Integer.valueOf(a[i + 1])};
                }
                stringBuffer.append(String.format(str2, objArr));
            }
            eCGEntity.setMark_time(stringBuffer.toString());
            Log.d("HL7Test", stringBuffer.toString());
        }
        return eCGEntity;
    }

    /* renamed from: a */
    public static ECGRecord m2774a(Context context, String str) {
        ECGApplication eCGApplication = (ECGApplication) context.getApplicationContext();
        File file = new File(str);
        ECGRecord eCGRecord = new ECGRecord();
        eCGRecord.setUser(eCGApplication.mUserInfo);
        eCGRecord.setMachine(eCGApplication.appMachine);
        eCGRecord.setFileName(file.getName());
        eCGRecord.setFilePath(file.getPath());
        try {
            ECGEntity a = m2773a(str);
            eCGRecord.setEcgEntity(a);
            eCGRecord.setMark_time(a.getMark_time());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            eCGRecord.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(simpleDateFormat.parse(a.getStartTime())));
            long time = simpleDateFormat.parse(a.getEndTime()).getTime() - simpleDateFormat.parse(a.getStartTime()).getTime();
            long j = time - ((time / 86400000) * 86400000);
            long j2 = j / 3600000;
            long j3 = j - (3600000 * j2);
            eCGRecord.setPeriod(String.format("%02d", new Object[]{Long.valueOf(j2)}) + ":" + String.format("%02d", new Object[]{Long.valueOf(j3 / 60000)}) + ":" + String.format("%02d", new Object[]{Long.valueOf((j3 % 60000) / 1000)}));
            eCGRecord.setHeartRate(0);
            eCGRecord.setDescription(a.getDesc());
            if (a.getLeadExten().equals(ECGEntity.LEAD_PART_HAND)) {
                eCGRecord.setLeadType(0);
                return eCGRecord;
            }
            if (a.getLeadExten().equals(ECGEntity.LEAD_PART_CHEST)) {
                eCGRecord.setLeadType(1);
            }
            return eCGRecord;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    public static String annotatedECG(File file, String str) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(file)).getElementsByTagName(str).item(0).getTextContent();
    }

    /* renamed from: a */
    public static void m2776a(Context context, File file, File file2, ECGEntity eCGEntity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        InputStream open = context.getApplicationContext().getResources().getAssets().open("hl7.xml");
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read == -1) {
                break;
            }
            fileOutputStream.write(bArr, 0, read);
        }
        open.close();
        fileOutputStream.close();
        Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        ((Element) parse.getElementsByTagName("id").item(0)).setAttribute("root", UUID.randomUUID().toString());
        Element element = (Element) parse.getElementsByTagName("effectiveTime").item(0);
        ((Element) element.getElementsByTagName("low").item(0)).setAttribute("value", eCGEntity.getStartTime());
        ((Element) element.getElementsByTagName("high").item(0)).setAttribute("value", eCGEntity.getEndTime());
        Element element2 = (Element) ((Element) parse.getElementsByTagName("series").item(0)).getElementsByTagName("effectiveTime").item(0);
        ((Element) element2.getElementsByTagName("low").item(0)).setAttribute("value", eCGEntity.getStartTime());
        ((Element) element2.getElementsByTagName("high").item(0)).setAttribute("value", eCGEntity.getEndTime());
        Element element3 = (Element) ((Element) parse.getElementsByTagName("sequence").item(0)).getElementsByTagName("value").item(0);
        ((Element) element3.getElementsByTagName("head").item(0)).setAttribute("value", eCGEntity.getStartTime());
        Element element4 = (Element) element3.getElementsByTagName("increment").item(0);
        element4.setAttribute("unit", eCGEntity.getTimeUnit());
        element4.setAttribute("value", eCGEntity.getTimeUnitValue());
        Element element5 = (Element) parse.getElementsByTagName("sequence").item(1);
        Element element6 = (Element) element5.getElementsByTagName("code").item(0);
        element6.setAttribute("code", eCGEntity.getLead());
        element6.setAttribute("extension", eCGEntity.getLeadExten());
        ((Element) ((Element) parse.getElementsByTagName("subjectFindingComment").item(0)).getElementsByTagName("text").item(0)).setTextContent(eCGEntity.getDesc());
        Element element7 = (Element) element5.getElementsByTagName("value").item(0);
        Element element8 = (Element) element7.getElementsByTagName("origin").item(0);
        element8.setAttribute("unit", eCGEntity.getScaleUnit());
        element8.setAttribute("value", "0");
        Element element9 = (Element) element7.getElementsByTagName("scale").item(0);
        element9.setAttribute("unit", eCGEntity.getScaleUnit());
        element9.setAttribute("value", eCGEntity.getScaleUnitValue());
        Element element10 = (Element) element7.getElementsByTagName("digits").item(0);
        FileInputStream fileInputStream = new FileInputStream(file2);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuffer stringBuffer = new StringBuffer(fileInputStream.available());
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                stringBuffer.append(readLine);
            } else {
                bufferedReader.close();
                element10.setTextContent(stringBuffer.toString());
                m2781a(parse, eCGEntity.getMark_period());
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(parse), new StreamResult(new PrintWriter(file)));
                return;
            }
        }
    }

    /* renamed from: a */
    public static void m2777a(Context context, File file, float[] fArr, ECGEntity eCGEntity) throws TransformerException, IOException, ParserConfigurationException, SAXException {
        int i;
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        InputStream open = context.getApplicationContext().getResources().getAssets().open("hl7.xml");
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read == -1) {
                break;
            }
            fileOutputStream.write(bArr, 0, read);
        }
        open.close();
        fileOutputStream.close();
        Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        ((Element) parse.getElementsByTagName("id").item(0)).setAttribute("root", UUID.randomUUID().toString());
        Element element = (Element) parse.getElementsByTagName("effectiveTime").item(0);
        ((Element) element.getElementsByTagName("low").item(0)).setAttribute("value", eCGEntity.getStartTime());
        ((Element) element.getElementsByTagName("high").item(0)).setAttribute("value", eCGEntity.getEndTime());
        Element element2 = (Element) ((Element) parse.getElementsByTagName("series").item(0)).getElementsByTagName("effectiveTime").item(0);
        ((Element) element2.getElementsByTagName("low").item(0)).setAttribute("value", eCGEntity.getStartTime());
        ((Element) element2.getElementsByTagName("high").item(0)).setAttribute("value", eCGEntity.getEndTime());
        Element element3 = (Element) ((Element) parse.getElementsByTagName("sequence").item(0)).getElementsByTagName("value").item(0);
        ((Element) element3.getElementsByTagName("head").item(0)).setAttribute("value", eCGEntity.getStartTime());
        Element element4 = (Element) element3.getElementsByTagName("increment").item(0);
        element4.setAttribute("unit", eCGEntity.getTimeUnit());
        element4.setAttribute("value", eCGEntity.getTimeUnitValue());
        Element element5 = (Element) parse.getElementsByTagName("sequence").item(1);
        Element element6 = (Element) element5.getElementsByTagName("code").item(0);
        element6.setAttribute("code", eCGEntity.getLead());
        element6.setAttribute("extension", eCGEntity.getLeadExten());
        ((Element) ((Element) parse.getElementsByTagName("subjectFindingComment").item(0)).getElementsByTagName("text").item(0)).setTextContent(eCGEntity.getDesc());
        Element element7 = (Element) element5.getElementsByTagName("value").item(0);
        Element element8 = (Element) element7.getElementsByTagName("origin").item(0);
        element8.setAttribute("unit", eCGEntity.getScaleUnit());
        element8.setAttribute("value", "0");
        Element element9 = (Element) element7.getElementsByTagName("scale").item(0);
        element9.setAttribute("unit", eCGEntity.getScaleUnit());
        element9.setAttribute("value", eCGEntity.getScaleUnitValue());
        Element element10 = (Element) element7.getElementsByTagName("digits").item(0);
        StringBuffer stringBuffer = new StringBuffer(fArr.length);
        for (float f : fArr) {
            stringBuffer.append((int) f);
            stringBuffer.append(" ");
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        element10.setTextContent(stringBuffer.toString());
        m2781a(parse, eCGEntity.getMark_period());
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(parse), new StreamResult(new PrintWriter(file)));
    }

    /* renamed from: a */
    public static void m2778a(Context context, String str, String str2, ECGEntity eCGEntity) throws IOException, TransformerException, SAXException, ParserConfigurationException {
        File file = new File(str2);
        if (!file.exists()) {
            Log.e("HL7Encoder", "can not find resourse file");
            return;
        }
        File file2 = new File(str);
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdirs();
        }
        if (!file2.exists()) {
            file2.createNewFile();
        }
        m2776a(context, file2, file, eCGEntity);
    }

    /* renamed from: a */
    public static void m2779a(Context context, String str, float[] fArr, ECGEntity eCGEntity) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        File file = new File(str);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        m2777a(context, file, fArr, eCGEntity);
    }

    /* renamed from: a */
    public static void getDescriptionFile(File file, String str, String str2) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(file));
        parse.getElementsByTagName(str).item(0).setTextContent(str2);
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(parse), new StreamResult(new PrintWriter(file)));
    }

    /* renamed from: a */
    private static void m2781a(Document document, int[] iArr) {
        if (iArr != null) {
            int length = iArr.length;
            int i = length - (length % 2);
            if (i > 0) {
                Element createElement = document.createElement("component");
                document.getLastChild().appendChild(createElement);
                Element createElement2 = document.createElement("annotation");
                createElement.appendChild(createElement2);
                Element createElement3 = document.createElement("code");
                createElement2.appendChild(createElement3);
                createElement3.setAttribute("code", "MDC_ECG_BEAT");
                createElement3.setAttribute("codeSystem", "2.16.840.1.113883.6.24");
                Element createElement4 = document.createElement("value");
                createElement4.setAttribute("xsi:type", "CE");
                createElement4.setAttribute("code", "MDC_ECG_BEAT_ABNORMAL");
                createElement4.setAttribute("codeSystem", "2.16.840.1.113883.6.24");
                createElement2.appendChild(createElement4);
                Element createElement5 = document.createElement("support");
                createElement2.appendChild(createElement5);
                Element createElement6 = document.createElement("supportingROI");
                createElement5.appendChild(createElement6);
                Element createElement7 = document.createElement("code");
                createElement7.setAttribute("code", "ROIPS");
                createElement7.setAttribute("codeSystem", "2.16.840.1.113883.6.24");
                createElement6.appendChild(createElement7);
                for (int i2 = 0; i2 < i; i2 += 2) {
                    Element createElement8 = document.createElement("component");
                    createElement6.appendChild(createElement8);
                    Element createElement9 = document.createElement("boundary");
                    createElement8.appendChild(createElement9);
                    Element createElement10 = document.createElement("code");
                    createElement10.setAttribute("code", "TIME_RELATIVE");
                    createElement10.setAttribute("codeSystem", "2.16.840.1.113883.6.24");
                    createElement9.appendChild(createElement10);
                    Element createElement11 = document.createElement("value");
                    createElement11.setAttribute("xsi:type", "IVL_PQ");
                    createElement9.appendChild(createElement11);
                    Element createElement12 = document.createElement("low");
                    createElement12.setAttribute("value", String.valueOf(iArr[i2]));
                    createElement12.setAttribute("unit", "ms");
                    createElement11.appendChild(createElement12);
                    Element createElement13 = document.createElement("high");
                    createElement13.setAttribute("value", String.valueOf(iArr[i2 + 1]));
                    createElement13.setAttribute("unit", "ms");
                    createElement11.appendChild(createElement13);
                }
            }
        }
    }

    /* renamed from: a */
    public static boolean m2782a(String str, String str2, boolean z, Context context, String str3) {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hopetruly/ECGdata/" + str3 + "_" + str2);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                return false;
            }
            InputStream open = z ? context.getAssets().open(str) : new FileInputStream(new File(str));
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            byte[] bArr = new byte[400000];
            while (true) {
                int read = open.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.close();
                    open.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* renamed from: a */
    private static int[] m2783a(Document document) {
        Element element = (Element) document.getElementsByTagName("annotation").item(0);
        if (element == null) {
            return null;
        }
        NodeList elementsByTagName = element.getElementsByTagName("low");
        NodeList elementsByTagName2 = element.getElementsByTagName("high");
        if (elementsByTagName == null || elementsByTagName2 == null) {
            return null;
        }
        int length = elementsByTagName.getLength() > elementsByTagName2.getLength() ? elementsByTagName2.getLength() : elementsByTagName.getLength();
        int[] iArr = new int[(length * 2)];
        for (int i = 0; i < length; i++) {
            int i2 = 2 * i;
            iArr[i2] = Integer.parseInt(elementsByTagName.item(i).getAttributes().getNamedItem("value").getNodeValue());
            iArr[i2 + 1] = Integer.parseInt(elementsByTagName2.item(i).getAttributes().getNamedItem("value").getNodeValue());
        }
        return iArr;
    }
}
