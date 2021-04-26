package com.neusoft.sl.si.authserver.base.domains.person;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonBasicInfoCZDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonalEmpListOutResponseDTO;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.sl.girder.security.oauth2.domain.CompanyDTO;
import com.neusoft.sl.si.authserver.base.domains.company.CompanySiRepository;
import com.neusoft.sl.si.authserver.base.domains.company.CompanySiService;
import com.neusoft.sl.si.authserver.simis.SimisWebService;
import com.neusoft.sl.si.authserver.simis.support.gateway.dto.AB01DTO;
import com.neusoft.sl.si.authserver.simis.support.gateway.dto.AC01DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: liaoning-auth
 * @description: 查询个人基本信息多系统查询
 * @author: lgy
 * @create: 2020-03-27 13:01
 **/
@Service
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private static final String CACHE_COMPANY_SI_REDIS_KEY = "CACHE_PERSON_SI";

    @Autowired
    private PersonRepository person;

    @Autowired
    private SimisWebService simisWebService;


    @Value("${saber.auth.getLabour.person.url}")
    private String personUrl = "";


    public List<PersonBasicInfo> findByIdNumberAndName(String idNumber, String name) {

        log.debug("idNumber = {}, url = {}", idNumber, personUrl);
        List<PersonBasicInfo> personDTOS = new ArrayList<>();
        if (StringUtils.isEmpty(idNumber)) {
            return personDTOS;
        }
        try{
            //目前只有就业后续社保ws
            AC01DTO request = new AC01DTO(idNumber);
            PersonBasicInfo personBasicInfoLa = new PersonBasicInfo();
           // log.debug("request = {}", JSONObject.toJSONString(request));
         //   JSONObject json = JSONObject.parseObject(HttpClientTools.httpPost(personUrl, JSONObject.parseObject(JSONObject.toJSONString(request))).toString());
         //   if (json.toString().contains("aac001")) {
          //      log.debug("dto = {}", json);
           //     personBasicInfoLa.setAAC001();
                personBasicInfoLa.setAAC002(idNumber);
                personBasicInfoLa.setAAC003(name);
               // personBasicInfoLa.setAAC004(json.get("aac004").toString());
                personBasicInfoLa.setClientType("ROLE_PERSON_ORDINARY_USER");
                personDTOS.add(personBasicInfoLa);
         //   }
        }catch (Exception e){
            log.debug("未查询到信息");
        }




        //查询城乡
        List<PersonBasicInfo> personListCX = simisWebService.queryPersonInfo(idNumber, name, "").getPersonBasicInfoList();
//        if (personList.size() > 1) {
//            throw new BadCredentialsException("当前身份证存在多条信息，请联系社保局处理");
//        }
        //多条应该也让做
        if (personListCX != null && personListCX.size() != 0) {
            for (PersonBasicInfo personBasicInfo : personListCX) {
                personBasicInfo.setClientType("ROLE_PERSON_CX");
            }
            personDTOS.addAll(personListCX);
        }else{
            PersonBasicInfo personBasicInfo = new PersonBasicInfo();
            personBasicInfo.setClientType("ROLE_PERSON_NOCX");

            personDTOS.add(personBasicInfo);
        }


        /**
         * 查询城职
         */
//        PersonBasicInfoCZDTO personBasicInfoCZDTO = simisWebService.queryPersonInfoCZ(idNumber, name, "");
   //     List<PersonBasicInfoCZDTO> list = simisWebService.queryPersonInfoCZ(idNumber,name,"");
   //     if (list != null && list.size()!= 0) {
   //         PersonBasicInfo personBasicInfoCZ = new PersonBasicInfo();
   //         personBasicInfoCZ.setClientType("ROLE_PERSON_CZ");
   //         personBasicInfoCZ.setAAC001(list.get(0).getPersonId());
   //         personBasicInfoCZ.setAAC004(list.get(0).getSex());
   //         personBasicInfoCZ.setAAC003(list.get(0).getName());
   //         personDTOS.add(personBasicInfoCZ);
   //     }


        return personDTOS;


    }


}
