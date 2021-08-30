package com.neusoft.sl.si.authserver.base.domains.company;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.sl.girder.security.oauth2.domain.CompanyDTO;
import com.neusoft.sl.si.authserver.simis.SimisWebService;
import com.neusoft.sl.si.authserver.simis.support.gateway.dto.AB01DTO;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 单位仓储
 *
 * @author mojf
 */
@Service
public class CompanySiService {

    private static final Logger log = LoggerFactory.getLogger(CompanySiService.class);

    private static final String CACHE_COMPANY_SI_REDIS_KEY = "CACHE_COMPANY_SI";

    @Autowired
    private CompanySiRepository companySiRepository;

    @Autowired
    private SimisWebService simisWebService;
    @Value("${saber.auth.getLabour.url}")
    private String enterpriseUrl = "";


    @Value("${saber.si.enterprise.orgCode}")
    private List<String> orgCodeList;

    @Value("${saber.si.flag}")
    private boolean flagsi;


    @Value("${saber.la.flag}")
    private boolean flagla;
//	@Autowired
//	private SimisWebService simisWebService;

    /**
     * 根据单位编号查询
     *
     * @param companyNumber
     * @return
     */
    @Cacheable(value = CACHE_COMPANY_SI_REDIS_KEY, key = "'CACHE_COMPANY_SI_'+#companyNumber", unless = "null==#result")
    public CompanySi findByCompanyNumber(String companyNumber) {
        log.debug("查询CompanySi，companyNumber={}", companyNumber);
        return companySiRepository.findByCompanyNumber(companyNumber);
    }

    @Cacheable(value = CACHE_COMPANY_SI_REDIS_KEY, key = "'CACHE_COMPANY_SI_'+#id", unless = "null==#result")
    public CompanySi findById(Long id) {
        log.debug("查询CompanySi，id={}", id);
        return companySiRepository.findById(id);
    }



    public List<CompanyDTO> findByOrgCode(String orgCode, String companyName,String companyNumber) {

        List<CompanyDTO> companyDTOS = new ArrayList<>();
        //目前只有就业后续社保ws
        AB01DTO request = new AB01DTO(orgCode);

        //   此处省略的代码原因是 ： 本溪打包办不查询就业信息

//        if (flagla){
//            try {
//                log.debug("request = {}", JSONObject.toJSONString(request));
//                JSONObject json = JSONObject.parseObject(HttpClientTools.httpPost(enterpriseUrl, JSONObject.parseObject(JSONObject.toJSONString(request))).toString());
//                if (json.toString().contains("aab001")) {
//                    log.debug("dto = {}", json);
//                    companyDTOLa.setOrgCode(json.get("aab998").toString());
//                    companyDTOLa.setName(json.get("aab004").toString());
//                    companyDTOLa.setCompanyNumber(json.get("aab001").toString());
//                    companyDTOLa.setId(Long.valueOf(json.get("aab001").toString()));
//                    companyDTOLa.setClientType("labour");
//                    companyDTOS.add(companyDTOLa);
//                }
//            }catch (Exception e){
//                log.debug("未查询到就业信息");
//            }
//
//        }else {
//            companyDTOLa.setOrgCode("test");
//            companyDTOLa.setName("test");
//            companyDTOLa.setCompanyNumber("test");
//            companyDTOLa.setId(Long.valueOf("1234"));
//            companyDTOLa.setClientType("labour");
//            companyDTOS.add(companyDTOLa);
//        }
        //查询社保

        CompanyDTO companyDTOSi = simisWebService.queryCompanyInfo(orgCode, companyName,companyNumber);
        companyDTOSi.setClientType("simis");
        if (null == companyDTOSi.getLevel()){
            companyDTOSi.setLevel(0);
        }
        companyDTOS.add(companyDTOSi);
        log.debug("companyDTOS = {}, companyDTOSi = {}", companyDTOS, companyDTOSi);

        return companyDTOS;


    }



    public List<CompanyDTO> findByUnitId(Long unitId) {

        List<CompanyDTO> companyDTOS = new ArrayList<>();
        //目前只有就业后续社保ws
        //查询社保
        CompanyDTO companyDTOSi = simisWebService.queryCompanyInfo(null, null,unitId.toString());
        companyDTOSi.setClientType("simis");
        if (null == companyDTOSi.getLevel()){
            companyDTOSi.setLevel(0);
        }
        companyDTOS.add(companyDTOSi);
        log.debug("companyDTOS = {}, companyDTOSi = {}", companyDTOS, companyDTOSi);

        return companyDTOS;


    }




}
