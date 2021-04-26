package com.neusoft.ehrss.liaoning.provider.ca.GXCA.dev.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.neusoft.ehrss.liaoning.provider.ca.GXCA.dev.ws.ObjectFactory;

@Configuration
public class GXCADevWSConfig {

	@Value("${saber.gxca.webservice.url}")
	private String url;

	@Bean("gxcaDevMarshaller")
	public Jaxb2Marshaller gxcaMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(ObjectFactory.class.getPackage().getName());
		return marshaller;
	}

	@Bean("gxcaDevWSClient")
	public GXCADevWSClient wsClient(@Qualifier(value = "gxcaDevMarshaller") Jaxb2Marshaller hnskMarshaller) {
		GXCADevWSClient client = new GXCADevWSClient();
		client.setDefaultUri(url);
		client.setMarshaller(hnskMarshaller);
		client.setUnmarshaller(hnskMarshaller);
		return client;
	}
}
