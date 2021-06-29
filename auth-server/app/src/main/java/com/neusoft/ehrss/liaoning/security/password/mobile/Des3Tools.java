package com.neusoft.ehrss.liaoning.security.password.mobile;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Turbo
 * @date 2019/5/27.
 */
public class Des3Tools {


    private final static String privateKey = "xUHdKxzVCbsgVIwTnc1jtpWn";
    private final static String sw = "on";
    private static List<String> fieldList = new ArrayList<>();
    static {
        fieldList.add("mobile");
        fieldList.add("creditid");
        fieldList.add("profession");
        fieldList.add("idsfld_idsext_creditid");
        fieldList.add("idsfld_idsext_mobile");
        fieldList.add("idsfld_idsext_jbrtel");
        fieldList.add("idsfld_idsext_jbrcreditid");
    }

    /**
     * 转换成十六进制字符串
     * @param
     * @return
     *
     * lee on 2017-08-09 10:54:19
     */
    private static byte[] hex(String key){
        String f = DigestUtils.md5Hex(key);
        byte[] bkeys = new String(f).getBytes();
        byte[] enk = new byte[24];
        for (int i=0;i<24;i++){
            enk[i] = bkeys[i];
        }
        return enk;
    }

    /**
     * 3DES加密
     * @param key 密钥，24位
     * @param srcStr 将加密的字符串
     * @return
     *
     * lee on 2017-08-09 10:51:44
     */
    private static String encode3Des(String key,String srcStr){
        byte[] keybyte = hex(key);
        byte[] src = srcStr.getBytes();
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            //加密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.ENCRYPT_MODE, deskey);

            String pwd = Base64.encodeBase64String(c1.doFinal(src));
            return pwd;
        } catch (java.security.NoSuchAlgorithmException e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }catch(javax.crypto.NoSuchPaddingException e2){
            e2.printStackTrace();
        }catch(Exception e3){
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 3DES解密
     * @param key 加密密钥，长度为24字节
     * @param desStr 解密后的字符串
     * @return
     *
     * lee on 2017-08-09 10:52:54
     */
    private static String decode3Des(String key, String desStr){
        Base64 base64 = new Base64();
        byte[] keybyte = hex(key);
        byte[] src = base64.decode(desStr);

        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            //解密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            String pwd = new String(c1.doFinal(src));
            return pwd;
        } catch (java.security.NoSuchAlgorithmException e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }catch(javax.crypto.NoSuchPaddingException e2){
            e2.printStackTrace();
        }catch(Exception e3){
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 方法功能描述:TODO  3des加密
     *
     * @Author Turbo
     * @date 2019/5/27 10:36 AM
     * @param
     * @return
     * @throws
     */
    public static String encode(String srcStr){
        if(StringUtils.isNotEmpty(srcStr)){
            srcStr = srcStr.toUpperCase();
        }
        return encode3Des(privateKey,srcStr);
    }

    public static String encodeOld(String srcStr){
        return encode3Des(privateKey,srcStr);
    }

    /**
     * 方法功能描述:TODO 3des解密
     *
     * @Author Turbo
     * @date 2019/5/27 10:37 AM
     * @param
     * @return
     * @throws
     */
    /*public static String decode(String desStr){

        return decode3Des(privateKey,desStr);
    }*/

    public static String encode(String field,String value){
        if(StringUtils.isNotEmpty(field)){
            field=field.toLowerCase();
        }
        if("on".equals(sw)){
            if(fieldList.contains(field)){
                if(StringUtils.isEmpty(value)){
                    return value;
                }
                if(value.length() < 24){
                    return encode(value);
                }
            }
        }
        return value;
    }

    public static String decode(String value){
        if(StringUtils.isEmpty(value)){
            return value;
        }
        if(value.length() >= 24){
            return decode3Des(privateKey,value);
        }
        return value;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String card = "15566554246";
        String str = encode(card);
        System.out.println(str);
        System.out.println(str.length());
        encode("PROFESSION","21012319950718222X");

        System.out.println(decode("LMsQrP3tPfAjBdKfpsqGmA=="));

       String abc= URLEncoder.encode("https://www.lnzwfw.gov.cn/", "UTF-8");
       System.out.print(abc);
    }

}
