/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.neusoft.ehrss.liaoning.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

import com.neusoft.sl.si.yardman.server.service.NeuWebService;

@Profile("webservice")
@Configuration
@ImportResource({ "classpath*:/applicationContext-yardman-core.xml", "classpath:/applicationContext-yardman-dispatch-nanning.xml", "classpath*:/applicationContext-yardman-ws.xml" })
public class WebServiceConfig {

	public WebServiceConfig() {

	}

	@Autowired
	@Qualifier("NeuWebService_bo")
	private NeuWebService neuWebService;

	@SuppressWarnings("unused")
	@Autowired
	private WSS4JInInterceptor wss4jIn;

	@Bean
	public ServletRegistrationBean yardManDispatcherServlet() {
		CXFServlet cxfServlet = new CXFServlet();
		return new ServletRegistrationBean(cxfServlet, "/webservice/*");
	}

	@Bean(name = "cxf")
	public SpringBus springBus() {
		return new SpringBus();
	}

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl((NeuWebService) neuWebService);

		endpoint.setImplementorClass(NeuWebService.class);

		endpoint.setServiceName(new QName("http://service.server.yardman.si.sl.neusoft.com/", "NeuWebService"));

		// endpoint.setDataBinding(new AegisDatabinding());

		List<Interceptor<? extends Message>> inInterceptors = new ArrayList<Interceptor<? extends Message>>();
		inInterceptors.add(new LoggingInInterceptor());

		// 报文加密
		// inInterceptors.add(wss4jIn);

		endpoint.setInInterceptors(inInterceptors);

		List<Interceptor<? extends Message>> outInterceptors = new ArrayList<Interceptor<? extends Message>>();
		outInterceptors.add(new LoggingOutInterceptor());
		endpoint.setOutInterceptors(outInterceptors);

		// Map<String, Object> properties = new HashMap<String, Object>();
		// properties.put("mtom-enabled", true);
		// endpoint.setProperties(properties);

		endpoint.getServerFactory().setDataBinding(new AegisDatabinding());
		endpoint.publish("/NeuWebService");
		return endpoint;
	}
}
