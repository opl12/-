package com.ying.jianxuan.Ua;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ua implements interfaceUa {
    public  String getfile(File file) {
        FileInputStream fileInputStream;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        String s=stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
        return s;
    }

    @Override
    public String getua(String[] a) {
        String qdd = null;
        for (int ud=0;a.length>ud;ud++) {
            int i = (int) (0 + Math.random() * (a.length - 0 + 1));
            String q=a[i];
            Log.d("测试长度3",q);
            Log.d("测试长度",""+q.length());
            if (q.length()>190){
                String qd = q.substring(q.indexOf("("), q.indexOf(")"));
                String xhao = qd.substring(qd.indexOf("(") + 1, qd.indexOf("(") + 3);
                if (xhao.equals("iP")) {
             /*  String Mobile=q.substring(q.indexOf("Mobile/"),q.length()-1).split(" ")[0];
                Log.d("测试",Mobile);
                String AppleWebKit=q.substring(q.indexOf("AppleWebKit"),q.indexOf(" (KHTML, like Gecko)"));
                qdd = "  Mozilla/5.0 "   +qd+") "+AppleWebKit+" (KHTML, like Gecko) "+Mobile+" MicroMessenger/7.0.12(0x17000c2b) NetType/"+shuju[uaaaa]+" Language/zh_CN";
                //  break;*/
                } else if (xhao.equals("Ba")) {
                } else if (xhao.equals("Li")) {
                    int ttt = (int) (0 + Math.random() * (tt.length-1 - 0 + 1));
                    qdd = "Mozilla/5.0 " +qd.replace(" U;", "").replace(" zh-cn;", "") + " AppleWebKit/537.36 (KHTML, like Gecko) " + getweibu();
                    break;
                }
            }
        }
        return qdd;
    }

    public String[] tt={"9.0","7.0","8.0","10","9.0.1","9.1.0","9.1.1","7.0.1","7.1.0","7.1.1","8.0.1","8.1.0","8.0.1"};
    private String[] bb={"7.0.10.1580","7.0.11.1600","7.0.12.1620","7.0.13.1640","7.0.14.1660","7.0.15.1680","7.0.16.1700","7.0.17.1720"};
    public String MicroMessenger;
    public Ua() {
        MicroMessenger=getMicroMessenger();
    }
    public String getweibu() {
        String cs=null;
        String Chrome="77.0.3865.120";
        MicroMessenger=getMicroMessenger();
        int MMWEBID = (int) (2000 + Math.random() * (8000 - 2000 + 1));
        if (MicroMessenger.equals("MicroMessenger/7.0.13.1640")) {
            String uaam[]={"45140"};
            int san = (int) (0 + Math.random() * (uaam.length-1 - 0 + 1));
            cs = " Version/4.0 Chrome/" + Chrome +/*+chrome+".0."+chrome1+"."+chrome2 + */" MQQBrowser/6." + 2 + " TBS/" + "0" + uaam[san] + " Mobile Safari/537.36 MMWEBID/" + MMWEBID + " " + MicroMessenger + "(" + get16() + ") Process/tools NetType/" + getwifi() + " Language/zh_CN ABI/arm64";
        }
        else if (MicroMessenger.equals("MicroMessenger/7.0.10.1580")) {
            cs = " Version/4.0 Chrome/" + Chrome +/*+chrome+".0."+chrome1+"."+chrome2 + */" MQQBrowser/6." + 2 + " TBS/" + "0" + "45227" + " Mobile Safari/537.36 MMWEBID/" + MMWEBID + " " + MicroMessenger + "(" + get16() + ") Process/tools NetType/" + getwifi()+ " Language/zh_CN ABI/arm64";
        }
        else if (MicroMessenger.equals("MicroMessenger/7.0.14.1660")) {
            cs = " Version/4.0 Chrome/" + Chrome +/*+chrome+".0."+chrome1+"."+chrome2 + */" MQQBrowser/6." + 2 + " TBS/" + "0" + "45227" + " Mobile Safari/537.36 MMWEBID/" + MMWEBID + " " + MicroMessenger + "(" + get16() + ") Process/tools NetType/" + getwifi() + " Language/zh_CN ABI/arm64";
        } else if (MicroMessenger.equals("MicroMessenger/7.0.15.1680")) {
            cs = " Version/4.0 Chrome/" + Chrome +/*+chrome+".0."+chrome1+"."+chrome2 + */" MQQBrowser/6." + 2 + " TBS/" + "0" + "45227" + " Mobile Safari/537.36 MMWEBID/" + MMWEBID + " " + MicroMessenger + "(" + get16() + ") Process/tools NetType/" + getwifi() + " Language/zh_CN ABI/arm64";
        }else if (MicroMessenger.equals("MicroMessenger/7.0.16.1700")) {
            cs = " Version/4.0 Chrome/" + Chrome +/*+chrome+".0."+chrome1+"."+chrome2 + */" MQQBrowser/6." + 2 + " TBS/" + "0" + "45227" + " Mobile Safari/537.36 MMWEBID/" + MMWEBID + " " + MicroMessenger + "(" + get16() + ") Process/tools NetType/" + getwifi() + " Language/zh_CN ABI/arm64";
        }else if (MicroMessenger.equals("MicroMessenger/7.0.17.1720")) {
            cs = " Version/4.0 Chrome/" + Chrome +/*+chrome+".0."+chrome1+"."+chrome2 + */" MQQBrowser/6." + 2 + " TBS/" + "0" + "45327" + " Mobile Safari/537.36 MMWEBID/" + MMWEBID + " " + MicroMessenger + "(" + get16() + ") Process/tools NetType/" + getwifi() + " Language/zh_CN ABI/arm64";
        }
        else if (MicroMessenger.equals("MicroMessenger/7.0.12.1620")) {
            //045132
            cs = " Version/4.0 Chrome/" + Chrome +/*+chrome+".0."+chrome1+"."+chrome2 + */" MQQBrowser/6." + 2 + " TBS/" + "0" + "45227" + " Mobile Safari/537.36 MMWEBID/" + MMWEBID + " " + MicroMessenger + "(" + get16() + ") Process/tools NetType/" + getwifi() + " Language/zh_CN ABI/arm64";
        }
        else if (MicroMessenger.equals("MicroMessenger/7.0.11.1600")) {
            int XWEB = (int) (1000 + Math.random() * (1200 - 1000 + 1));
            int MMWEBSDK = (int) (191000 + Math.random() * (192201 - 191000 + 1));
            cs=" Version/4.0 Chrome/53.0.2785.143 XWEB/"+XWEB+" MMWEBSDK/"+MMWEBSDK+" Mobile Safari/537.36 MMWEBID/"+MMWEBID+" MicroMessenger/7.0.11.1600("+get16()+") Process/toolsmp NetType/WIFI Language/zh_CN ABI/arm32";
        }
        return cs;
    }

    @Override
    public String getwifi() {
        String wifi[] = {"4G", "WIFI"};
        final long time = System.currentTimeMillis();
        final int wifi1 = (int) (time % wifi.length);
        return wifi[wifi1];
    }

    @Override
    public String get16() {
        String intf = null;
        int san = (int) (13 + Math.random() * (21 - 13 + 1));
        switch (MicroMessenger) {
            case "MicroMessenger/7.0.10.1580":
                intf = intToHex(654314036 - san);
                break;
            //相隔260
            case "MicroMessenger/7.0.11.1600":
                intf = intToHex(654314036 + 260 - san);
                break;
            case "MicroMessenger/7.0.12.1620":
                intf = intToHex(654314036 + 260 * 2 - san);
                break;
            case "MicroMessenger/7.0.13.1640":
                intf = intToHex(654314036 + 260 * 3 - san);
                break;
            case "MicroMessenger/7.0.14.1660":
                intf = intToHex(654314036 + 260 * 4 - san);
                break;
            case "MicroMessenger/7.0.15.1680":
                intf = intToHex(654314036 + 260 * 5 - san);
                break;
            case "MicroMessenger/7.0.16.1700":
                intf = intToHex(654314036 + 260 * 6 - san);
                break;
            case "MicroMessenger/7.0.17.1770":
                intf = intToHex(654314036 + 260 * 7 - san);
                break;
        }
        return "0x" + intf;

    }

    @Override
    public String getMicroMessenger() {
        //,"7.0.10.1580"
        final long uaa = System.currentTimeMillis();
        final int uaaaa = (int) (uaa % bb.length);
        String MicroMessenger="MicroMessenger/"+bb[uaaaa];
        return MicroMessenger;
    }

    private String intToHex(int n) {
        //StringBuffer s = new StringBuffer();
        StringBuilder sb = new StringBuilder(8);
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            sb = sb.append(b[n%16]);
            n = n/16;
        }
        a = sb.reverse().toString();
        return a;
    }
}
