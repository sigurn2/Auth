package com.neusoft.ehrss.liaoning.utils;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * 工具类：创建一个忽略用户证书验证的httpClient实例
 * @date 2015-02-13
 * @author Barry
 */
public class CertificateValidationIgnored {
	
	public static HttpClient getNoCertificateHttpClient(String url){
		return getCertificateValidationIgnoredHttpClient();
	}
	
	private static HttpClient getCertificateValidationIgnoredHttpClient() {  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore  
                    .getDefaultType());  
            trustStore.load(null, null);  
            //核心代码，创建一个UnVerifySocketFactory对象，验证证书时总是返回true
            SSLSocketFactory sf = new UnVerifySocketFactory(trustStore);
            
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            SchemeRegistry registry = new SchemeRegistry();  
            registry.register(new Scheme("http", PlainSocketFactory  
                    .getSocketFactory(), 80));  
            registry.register(new Scheme("https", sf, 443));  
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(  
                    params, registry);  
            return new DefaultHttpClient(ccm, params);  
        } catch (Exception e) {  
        	System.out.println("CertificateValidationIgnored,创建忽略用户证书的HttpClient对象失败，尝试创建普通HttpClient对象");
        	e.printStackTrace();
            return new DefaultHttpClient();  
        }  
    }  
	
	/**
	 * 核心类
	 * UnVerifySocketFactory:一个验证证书时总是返回true的SSLSocketFactory的子类
	 */
	private static X509HostnameVerifier ignoreVerifier;
	private static class UnVerifySocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");
 
		public UnVerifySocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);
 
			TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}
				@Override
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
 
			sslContext.init(null, new TrustManager[] { tm }, null);
		}
 
		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}
 
		//核心代码
		@Override
		public void setHostnameVerifier(X509HostnameVerifier hostnameVerifier) {
			// TODO Auto-generated method stub
			ignoreVerifier = new X509HostnameVerifier() {
				@Override
				public void verify(String arg0, String[] arg1, String[] arg2)
						throws SSLException {
				}
				@Override
				public void verify(String arg0, X509Certificate arg1)
						throws SSLException {
				}
				@Override
				public void verify(String arg0, SSLSocket arg1)
						throws IOException {
				}
				
				//最最核心代码
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			super.setHostnameVerifier(ignoreVerifier);
		}
 
		@Override
		public X509HostnameVerifier getHostnameVerifier() {
			return ignoreVerifier;
		}
 
		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
		
}

