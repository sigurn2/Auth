package com.neusoft.ehrss.liaoning.utils;
import	java.io.DataInputStream;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @author Turbo
 * @date 2019/1/7.
 */
public class DemoDesUtil {

    private final static String DES = "DES";
    private final static String ENCODE = "UTF-8";


    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null){
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    public static void main(String[] args) {
        try {
            String key=DemoDesUtil.getDtKey();
         //  String returnBase1 = DemoDesUtil.decrypt("",DemoDesUtil.getDtKey());

           //登录加密
            System.out.println(DemoDesUtil.encPwd("123456"));
            String returnBase2 = DemoDesUtil.encrypt("{\"score\":\"1\",\"username\":\"weishaohang\",\"password\":\"2c81f3e9d507745438203e4b5f92e8d8\",\"method\":\"loginnp\",\"service\":\"user\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());
            //  String returnBase = DemoDesUtil.encrypt("123456",key);
//            returnBase2 = returnBase2.replaceAll("\\+","%2B");
            //唯一性校验加密
            String checkIdNumber = DemoDesUtil.encrypt("{\"score\":\"1\",\"field\":\"creditid\",\"method\":\"param\",\"service\":\"check\",\"value\":\"211224198809139111\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());
            String checkAccount = DemoDesUtil.encrypt("{\"score\":\"1\",\"field\":\"username\",\"method\":\"param\",\"service\":\"check\",\"value\":\"211224198809139111\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());
            String checkMobile = DemoDesUtil.encrypt("{\"score\":\"1\",\"field\":\"mobile\",\"method\":\"param\",\"service\":\"check\",\"value\":\"211224198809139111\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());



            // System.out.println(returnBase1);
            System.out.println("==========");
            System.out.println("====加密=======");
            System.out.println("Q1031ZAin2JiO4OSQ9uBeT5Q0Sojjd3tPYf94f0D63oZoMjwhVGf28X55SFXZuGKFOTJqtKMGMTd\n" +
                    "3YuUd0mLcwUELC4a9PiJNZXP/NFZDGdZl5PbNF+emGSREfl3jS6+VqT243BMbhBgo9vK/c81STOG\n" +
                    "lFH2nSuKAd6xKgtEFR+dGw32iYKvxheA9X30SjnODIflOuKS7z1AIG4BaeXH5JsEfs/0hjKYmwR+\n" +
                    "z/SGMpibBH7P9IYymGU0/wAVRPXd");
            System.out.println("====解密入参=======");
            System.out.println("Q1031ZAin2JiO4OSQ9uBeT5Q0Sojjd3tPYf94f0D63oZoMjwhVGf28X55SFXZuGKFOTJqtKMGMTd\n" +
                    "3YuUd0mLcwUELC4a9PiJNZXP/NFZDGdZl5PbNF+emGSREfl3jS6+VqT243BMbhBgo9vK/c81STOG\n" +
                    "lFH2nSuKAd6xKgtEFR+dGw32iYKvxheA9X30SjnODIflOuKS7z1AIG4BaeXH5JsEfs/0hjKYmwR+\n" +
                    "z/SGMpibBH7P9IYymGU0/wAVRPXd");
            System.out.println(DemoDesUtil.decrypt("znNnQNXPTQW8M783GpX6Ex+59QYn2Ho2P1pad85E/ONP+bsq+o4MDXlqTTOIX1HW0+nJPWE78Ic=",DemoDesUtil.getDtKey()));
            System.out.println("====解密出参=======");
            System.out.println(DemoDesUtil.decrypt("4NmxuVn+4oifh/DiUUY1DYLeOENalWLNFMWqDNQJ1oyjlwUtTm7QsPOWq7c/J9lyKrdg1iJtJToR\n" +
                    "zdIZk0D9YQrIo9qB85wbjR/mouYVeBCqRoh41lsldyf3g9DV7f85TgpL0bwbOFg0Z3ltSAGimxrL\n" +
                    "N1Hm+02N5iVeJF7hRDQjAywZ67Npr8j3FFT0JHC5K7+60RwRJ6lX5y80ZqUZPxih5vt8Qn7dnVsf\n" +
                    "hL7smx3dTQBFFAwRTttYbotvDZHEkxUxTb09Ouqp2GDzOjtk/FGVcD9Opvlkqava7Q01la3wPFFA\n" +
                    "7T7Hw5+22PHE7HChQ9NPB1Y3fdbxoe/Jx/WIAhXbBJqw9/+GaUJpuY/fwZUT4sx8RHmluCcE4rnl\n" +
                    "vA+ZhxKlStVSQotqvLB2QggC2HZ8IBxJ8bHqBSBdwtnDGtJ0RXBkZQMIyOHzV/+nCA8XxN/UYUZT\n" +
                    "8xq+S1L+MpqL3v++YlEzxgaiCYQEGB2p5LHU8pLI6wQCwKnYYPM6O2T83MqNxcb/g2eHuBFuEEaa\n" +
                    "MYy9er7JS4hPFh3gI2lP3npKR7imOlrzOL62VfIjAw9XU+qkX2mQMua4gxH2k9sOQvpwd2YNwyTb\n" +
                    "hi6Ul2NswvXQvyrVFXGeh1PqpF9pkDLmPKor7pq7BoLjHpIGXRbWV/5ksl2M+TcGwftU7QjSoXAC\n" +
                    "S8YX9WtQMD2HMvZPv8s+lxNvKYUX/HZ1JaFCIwZ1XqrAwydChktdXBBlyp9TaSkxIU5xDDGOj46X\n" +
                    "V+1GXslHIrYW4BfIUIAto8IoaV29NoUYiqR+Qs4A1pcPyqD74kE2xew6S2oUrMU9cGqqfeJMF3lU\n" +
                    "sJyHXsoWW+xQVwE26N23IjaXLBqZ9EkpaXc2kAkjCnHtLlbIafHITPmKtf0vJHlIrac8KJTB7Zu4\n" +
                    "b7wmbVrEEJyeTszXyL40cEAtyUvSdm4LNgQDx1jmvajzASbW+CbxNVHxVAzj6ZRjgO0sZdBsDM7b\n" +
                    "04ZRgKSDHuXjj9IF9daIsa5MzcQ55MttHDwejCgoUs5wJKtmz9a+wHikeiHTB1r7YWpxUsU9VZtV\n" +
                    "Co/WovUW4BwptitENF9wgWH/zGW6R26hLjZtbt79JNY4vSI6C+P1H/91fbdr0wQoWyk0OZpeovCC\n" +
                    "cv22DjWh99oAIjoL4/Uf/3XS4wBLXuGXO+pVGSiyHQazPxc6LWyozdpCZ2Npu/6JzPusfvXR4wKo\n" +
                    "Z+cC+9O0Mr05SRmT8cZEngzx4k3FRGsyADkdTJF3nrtHfnE1AwL/ASVJOs9KUOo74wgkCqP3qeXp\n" +
                    "4PgN3Jy/nr6c+S9dQ9UYbTrIDUcCXJmp2GDzOjtk/Cs2z7lrHx2tlfHN4yHUQw9Yga4ATcyAmaAj\n" +
                    "KZs6/04AXxKAw9IbTlCp2GDzOjtk/NiS7oO8LUmqIdURHh+KeR32wdIJjIQ7RUqoj2Sh37chGKbj\n" +
                    "9gKWLQbxYDrkSD7OdWuuOLpjDqDq3ii5WkCJXxPfZlyvj7VKFKpj3IwGIVP1pXfPTTiwrzX/uzee\n" +
                    "yZHmCMAIhpwTQwjq5fXIEnn/Qxw7yjJgPxlIZla/s/Zl5R9T+BGRyRSmPXLVhtjDkJw/6mz0ZqEj\n" +
                    "Omt0opWcvoUp3RNH2IIcGPoL4i6EV72iZBELq/nVRFzdsUnNAkZAZM5EJSMLpXdMOaSe6+7af2LR\n" +
                    "7ULfVOiBwf/OP2S9OnUssAdUeEZ/deztoDKTCNGr6/v+pqgY+VytOoYbYoUqoPYP3isObZB+bQlM\n" +
                    "M4y9er7JS4hPiHNY+2cjG+QOgfX6jAwtp+67zr/9tScujL16vslLiE9FKrcPr/nf1IwoKFLOcCSr\n" +
                    "bbPr6uEi/OHBS+QlVxY1DDHOfS/38vi9AS83SUDD0NEjr8cGca/rbwPR0ageGVaZ5rIlHChw9CZT\n" +
                    "6qRfaZAy5mST1Xb4tj0tOUkZk/HGRJ4iOgvj9R//dS6QPkcvOWdpwSvILWEH8rs5Wc2BOaTpXvBC\n" +
                    "4GqklPTRhEQhdPQFrYOJhuyQajkK8mikGW0ja86HA+Rp2b7K8Ht5RQZ7UikGpon4eMQLY+GZNGhb\n" +
                    "P87966OSuffNjDCwYxRfVxDPU0elaxT7ZVrmWL9U1G3DxhXSk1quAyMDDgQwUa5IpE8kucA5SRmT\n" +
                    "8cZEnmW4UHBpxHSXupdhZLHR8fUrznFxkExlcH3sGNLY2CosK7H7iL7z32FoYSZeG4Rg0sOLia1r\n" +
                    "6nEvu/HaOpIV/q3u1rCkPTds5lPqpF9pkDLmuEhaggiTS4w6zF8Dtz9okOasMbrKonZe6vZEBUL4\n" +
                    "qm2nONQqmJuX6k1U5fXCnpMgtBauAi4mfn1bmLKzt9sUa41pzPfbo/W8zPTRPDX5M/3W70uF5VrM\n" +
                    "jVcKt5wUVrfPNDmaXqLwgnJgZZq7PKzgr0E00PQ7N8r3X1UVh5Fyv9O8p61JH8gkgJHenL9XRlIj\n" +
                    "smz5T3S2LWBCiL6J62NxmaQxq4hKMa2L8s9tdswWEANT6qRfaZAy5nO9eMPSf4QChu/84oS5wgqn\n" +
                    "LRr0X0Dg4RBOA7bpQtKTEhNmGKI/vWOMxT2hSdHZ9hBOA7bpQtKTj8g4nCuHON3JYzJIoggjhBKJ\n" +
                    "4y59Q+zwPaQ6RMQAHNIURFKds4KS28jckgGTXq0Vtyo2wCjz5eJ7xphpKmzhli3sBf61lIppRsPi\n" +
                    "9zZKi7Z9VJdZLVigcjDjod3qPQKEzPTRPDX5M/1fXXnfJAdgNg+egKXCw8pfXxKAw9IbTlDZd3GV\n" +
                    "lzvGwqDDvBj2OGhB+S1VVis/VFDj5AP2NBpnqHEHkty3/F0FfekIVf6FqbrMgOIi4qCT+2KEQx0D\n" +
                    "aYufUQh9RB6re6U8oPq2W7fCRqrCJHwBxU3tcR+Lcr+8NKZxwE7ConGA6kT+PHY0c/1oyVS5Ui6k\n" +
                    "zFJN3O2PBL+If57HaLTUBlFs9SBURmX0tSWQ2rtQ1Rt/V0p8YiXrKGOLOx1DJecRp85T6qRfaZAy\n" +
                    "5nuer8TzCFqT1g+GHY2klSsDDPKwH1kRlEY/L00pDm8pHW1Ipn3CH+A8TdNC8OsMytz2Zp27gMON\n" +
                    "stltXr4FnWcdbUimfcIf4DxN00Lw6wzK6pnolXe9hgSISexuO4F4o1AGWyJTsmCUeLFuGtFOPUhW\n" +
                    "Ith/ZNTJljhR+docH+/JHW1Ipn3CH+A8TdNC8OsMyuqZ6JV3vYYE1FYZHh3/JVWk36l4gDFPidDG\n" +
                    "mpNEsuPqyvz13aS51frEzW8d5xSMyM10nkveL8FOj4UITrgISKvZLDSD2x/GJ+A6GXlPuoBD3nij\n" +
                    "kjVHc2FOsPB+ya7YMAMBX+ZngBfbsStqfbTkXxOIKl0Ue0oCxRvs/iwvqSH5RLeCktxrTbCSZwQc\n" +
                    "hAjJk/S5Go87znBUPf2N3uT1tAVgDPBqrNX/rOmFpJe5zAkH2m2wab5E2T6GDvrwKd1oGZezB8Jm\n" +
                    "tpUnthFIpAOMvbcx547J5Ke7Y7mcmVXfhrh7naZoQmCP4Km+QRCoXFhj6fMVYKGIcBX0gmvklMe8\n" +
                    "9Hl/1deOuZ2RDI+FCE64CEirdKJhm3qHcjS/ozIjH5/5gL2lyOWfc2gZcZEfm8TGF3o6xrq+IRl4\n" +
                    "P128W3Rho1zpH5ztXA9D/JaPhQhOuAhIq19m0GekebzHiuFWfAZn+Sf54/VGU3Dtdj39jd7k9bQF\n" +
                    "YAzwaqzV/6z1uP1uYhphIOzfjNOHmBNVVQzpq3uDSXTTlI0NDKqaKh1tSKZ9wh/gPE3TQvDrDMqj\n" +
                    "paIjzVNGyoh7rk6I2mUx6j9nbUYvMQhOsPB+ya7YMO/E2GVUMQfp+7n3FqwTudqMvXq+yUuIT9DG\n" +
                    "mpNEsuPqLjOrMA7tlaOSW/HxKzGxnuo/Z21GLzEITrDwfsmu2DDibbrWm7Z69F6xxPNbb2IJ6j9n\n" +
                    "bUYvMQhOsPB+ya7YMNpFVH0cLBKV6F1uXUmpTfg5SRmT8cZEnj39jd7k9bQFYAzwaqzV/6yFZBDE\n" +
                    "KCefsF5MV1BIK03KYaH0IXsCNzxQuV4gQzR20FoEd+ZuCOrofZP6dMEj4MkdbUimfcIf4DxN00Lw\n" +
                    "6wzKv51tADVi5piuvPcctNwWDmGh9CF7Ajc8ULleIEM0dtCJ4KMSZ8AksY8GacmP2XqIU+qkX2mQ\n" +
                    "MuYx547J5Ke7Yx0+huFiWipM6QFJvdVUyUzNdJ5L3i/BTo+FCE64CEir9oCmvmqVUPeFqA+I9Glv\n" +
                    "2x1tSKZ9wh/gPE3TQvDrDMqsDIkMpFCEuIy9er7JS4hP0Maak0Sy4+p9NA5HnQlGUG6nGJNNC7FM\n" +
                    "6j9nbUYvMQhOsPB+ya7YMFU8avXQLT2ooU143f5025s5SRmT8cZEnj39jd7k9bQFYAzwaqzV/6xU\n" +
                    "9QZ5yN5jUOo/Z21GLzEITrDwfsmu2DBVPGr10C09qFI3+V8Wd0rY6j9nbUYvMQhOsPB+ya7YMFU8\n" +
                    "avXQLT2ofel7kfXoAR+MvXq+yUuIT9DGmpNEsuPq31J1LDT4cG0WSKXC6BFM6p6HaTuVSg8cYaH0\n" +
                    "IXsCNzxQuV4gQzR20DxUqBfi3zu3U+qkX2mQMuYx547J5Ke7Y2dcRdjxArdJjwZpyY/ZeohT6qRf\n" +
                    "aZAy5jHnjsnkp7tjNiehdJ4kdB8oEBYTVmKuG4y9er7JS4hP0Maak0Sy4+qe6Zi84q1an8CS5HMg\n" +
                    "6nuM6j9nbUYvMQhOsPB+ya7YMAr+cU6t8ZJappUQ9d1Bsaoijf1yEkUhEiZLSMjs+EtTPf2N3uT1\n" +
                    "tAVgDPBqrNX/rEdSWCm0Z1HJ9D6vncf0pyXZA5RBRb1JJR1tSKZ9wh/gPE3TQvDrDMrSb7JFjf/I\n" +
                    "XQomnIo61Cqh/RCd+KxL8hKzUf6Y9Lw5t+Wp2GVoz0eNPldugdOalz5KkA6qwO9MJiBYaWPmPOMK\n" +
                    "UhLkxpVBqUgHxz8inr4nE9MPKIodrwyRPf2N3uT1tAVgDPBqrNX/rJLX3QuwgF3hhrCl1tzwW89h\n" +
                    "ofQhewI3PFC5XiBDNHbQVoqmU8mATV4mRh8jarBlCmGh9CF7Ajc8ULleIEM0dtAcQYoZQH1J6Fgt\n" +
                    "KdE+fqEwU+qkX2mQMuYx547J5Ke7Y2Jj2J7XGujojkY8b29qkuLrd/yf2Nxebq689xy03BYOYaH0\n" +
                    "IXsCNzxQuV4gQzR20BxBihlAfUnoMx8hew2w0ZUVfTTAiKfU+c10nkveL8FOj4UITrgISKukGaQC\n" +
                    "yfLOnGNGcZ984+TNU+qkX2mQMuYx547J5Ke7Y7flb29RK7uAifqwL1L2mZ11HSt4YqwL/HYdU5cX\n" +
                    "zP80j4UITrgISKukGaQCyfLOnHVMNNVgwvsTGjBOCeh6LFcdbUimfcIf4DxN00Lw6wzKFBGCeODK\n" +
                    "raH1kAEhCefkGc10nkveL8FOj4UITrgISKukGaQCyfLOnGNXgY5PDtxfU+qkX2mQMuYx547J5Ke7\n" +
                    "Y/f0zS1Sxu/SsghrFtYCWyNT6qRfaZAy5jHnjsnkp7tjt+Vvb1Eru4Bupxq/yxZ6cwLHwQFxr5wS\n" +
                    "1NEjuYBGmfxOsPB+ya7YMHI5ZfnqcJyO+SvGByC6U0nkWcFJTPk/dTu6wqvQj6IcrOCbf0lbMWFh\n" +
                    "Kkq+SewD5fRQPXx+SvCHzttUqrJYJvltIbuYCHgp4nGocoBJjBXJaF87Y0LN4POqHdOhM7iGDiMh\n" +
                    "o1K4jqUUKIaPqNVrCf4=",DemoDesUtil.getDtKey()));
            System.out.println("==========");
            System.out.println(key);
            System.out.println("==========");
            System.out.println(checkIdNumber);
            System.out.println("==========");
            System.out.println(checkAccount);
            System.out.println("==========");
            System.out.println(checkMobile);
            // System.out.println(returnBaseff);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public static String getDtKey() throws UnsupportedEncodingException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(date);
        String re = Base64.getEncoder().encodeToString(dateStr.getBytes("UTF-8"));
        String md5 = MD5Tools.getPwd(re, true);
        return md5 ;
    }



    public static String encPwd(String pwd)  {
        return MD5Tools.getPwd(pwd,false);
    }

    public static String encrypt(String data) throws Exception {
        String key = getDtKey();
        byte[] bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }


}
