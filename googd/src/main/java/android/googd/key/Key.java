package android.googd.key;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Key {

   /* public static void main(String[]d){
        //System.out.print("内核");
       System.out.print(jmi("3803891952000",2958474980L));
    }*/

    //加密密令
    private static Long keys=2958474980L;

    /**加密
     *
     * @param time 时间戳
     * @param key 加密密令
     *
     */
    public static   String jmi(String time,Long key){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("time",encryptKaiser(time,key));
        jsonObject.put("key",key);
        String m=encryptKaiser(jsonObject.toJSONString(),key);
        String js=encryptKaiser(m,keys);
        int MMWEBID = (int) (2000 + Math.random() * (8000 - 2000 + 1));
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("string",encryptKaiser(js, (long) MMWEBID)+"YES"+MMWEBID);
        jsonObject1.put("key",key);
        return jsonObject1.toJSONString();
    }

    /**
     *
     * @param string 解密
     * @return 返回源数据1541569323155
     */
    public static String jiem(String string){
        JSONObject jsonObject1= JSON.parseObject(string);
        String string1=jsonObject1.getString("string");
        int key=jsonObject1.getInteger("key");
        String a[]=string1.split("YES");
        String b=decryptKaiser(a[0],Long.parseLong(a[1]));
        String jmi=decryptKaiser(b, (long) key);
        String d=decryptKaiser(jmi,keys);
        JSONObject jsonObject=JSON.parseObject(d);
        String time=decryptKaiser(jsonObject.getString("time"),Long.parseLong(jsonObject.getString("key")));
        return time;
    }

    /**
     * 使用凯撒加密方式加密数据
     *
     * @param orignal :原文
     * @param key     :密钥
     * @return :加密后的数据
     */
    public static   String encryptKaiser(String orignal, Long key) {
        // 将字符串转为字符数组
        char[] chars = orignal.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 遍历数组
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode += key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }

        return sb.toString();
    }

    /**
     * 使用凯撒加密方式解密数据
     *
     * @param encryptedData :密文
     * @param key           :密钥
     * @return : 源数据
     */
    public static String decryptKaiser(String encryptedData, Long key) {
        // 将字符串转为字符数组
        char[] chars = encryptedData.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 遍历数组
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode -= key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }
        return sb.toString();
    }

    /**时间戳转换时间
     *
     * @param time  1541569323155
     * @param pattern yyyy-MM-dd HH:mm:ss
     * @return 2018-11-07 13:42:03
     */
    public String getDate2String(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

}

