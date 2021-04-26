package com.neusoft.ehrss.liaoning.provider.enterprise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class EnterpriseService {
    private static final Logger log = LoggerFactory.getLogger(EnterpriseService.class);
    @Value("${saber.company.path}")
    private String path;

    @Resource(name = "customRestTemplate")
    private RestTemplate restTemplate;
}
