package com.neusoft.ehrss.liaoning.provider.ca.GXCA.product.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.neusoft.ehrss.liaoning.provider.ca.GXCA.product.ws.ObjectFactory;


@Configuration
public class GXCAProductWSConfig {

	@Value("${saber.gxca.webservice.url}")
	private String url;

	@Bean("gxcaProductMarshaller")
	public Jaxb2Marshaller gxcaMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(ObjectFactory.class.getPackage().getName());
		return marshaller;
	}

	@Bean("gxcaProductWSClient")
	public GXCAProductWSClient wsClient(@Qualifier(value = "gxcaProductMarshaller") Jaxb2Marshaller hnskMarshaller) {
		GXCAProductWSClient client = new GXCAProductWSClient();
		client.setDefaultUri(url);
		client.setMarshaller(hnskMarshaller);
		client.setUnmarshaller(hnskMarshaller);
		return client;
	}
}
