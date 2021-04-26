package com.neusoft.sl.si.authserver.simis;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.PersonBasicInfoInputDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.PersonBasicInfoOutputDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.UnitInfoInputDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.UnitInfoOutputDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonBasicInfoCZDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonBasicInfoInputCZDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonBasicInfoInputListDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonalEmpListOutResponseDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.PersonBasicInfoCZRequest;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.PersonBasicListInfoRequest;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.PersonCxBasicInfoRequest;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.QueryCompanyInfoRequest;
import com.neusoft.ehrss.liaoning.processor.thirdparty.response.PersonBasicInfoCZResponse;
import com.neusoft.ehrss.liaoning.processor.thirdparty.response.PersonBasicInfoListResponse;
import com.neusoft.ehrss.liaoning.processor.thirdparty.response.PersonCxBasicInfoResponse;
import com.neusoft.ehrss.liaoning.processor.thirdparty.response.QueryCompanyInfoResponse;
import com.neusoft.sl.girder.security.oauth2.domain.CompanyDTO;
import com.neusoft.sl.si.authserver.base.domains.company.Company;
import com.neusoft.sl.si.authserver.base.domains.company.CompanySiService;
import com.neusoft.sl.si.authserver.base.domains.person.PersonBasicInfo;
import com.neusoft.sl.si.authserver.base.services.user.DefaultUserCustomServiceImpl;
import com.neusoft.sl.si.authserver.simis.support.gateway.ClientGateWay;
import com.neusoft.sl.si.authserver.simis.support.gateway.ServerNodeEnum;
import com.neusoft.sl.si.authserver.uaa.exception.WebserviceApiException;
import com.neusoft.sl.si.yardman.server.message.RequestHead;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.Query;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-02-17 17:57
 **/
@Service
public class SimisWebServiceImpl implements SimisWebService {

    /**
     * 日志
     */
    protected static Logger LOGGER = LoggerFactory.getLogger(SimisWebServiceImpl.class);


    @Resource
    private ClientGateWay clientGateWay;

    @Value("${yardman.flagWsTure}")
    private boolean flagWsTure;
    @Value("${yardman.flagWsTureSi}")
    private boolean flagWsTureSi;

    @Value("${yardman.flagWsTureCZ}")
    private boolean flagWsTureCZ;

    @Autowired
    private CompanySiService companySiService;

    @Override
    public PersonBasicInfoOutputDTO queryPersonInfo(String idNumber, String name, String mobile) {
//        if (flagWsTure) {
//            PersonCxBasicInfoRequest request = new PersonCxBasicInfoRequest(new PersonBasicInfoInputDTO(name, "01", idNumber));
//            //request.setBody();
//            //PersonBasicInfoOutputDTO dto = (PersonBasicInfoOutputDTO) clientGateWay.query(PersonBasicInfoOutputDTO.class, request, Object.class);
//            PersonBasicInfoOutputDTO personBasicInfoListDTO = new PersonBasicInfoOutputDTO();
//            try {
//                personBasicInfoListDTO = (PersonBasicInfoOutputDTO) clientGateWay.send(ServerNodeEnum.城乡居民.toString(), PersonCxBasicInfoResponse.class, request, PersonBasicInfoOutputDTO.class);
//            } catch (WebserviceApiException e) {
//                List<PersonBasicInfo> personBasicInfoList = new ArrayList<>();
//              return  new PersonBasicInfoOutputDTO(personBasicInfoList);
//            }
//            LOGGER.debug("个人基本信息城乡居民返回结果personBasicInfoListDTO={}", personBasicInfoListDTO);
//            return personBasicInfoListDTO;
//        } else {
            PersonBasicInfoOutputDTO dto = new PersonBasicInfoOutputDTO();
            List<PersonBasicInfo> list = new ArrayList<>();
            PersonBasicInfo personBasicInfo = new PersonBasicInfo();
        //    personBasicInfo.setAAC001(11111L);
            personBasicInfo.setAAC002(idNumber);
            personBasicInfo.setAAC003(name);
          //  personBasicInfo.setAAC004("1111");
            list.add(personBasicInfo);
            dto.setPersonBasicInfoList(list);
            return dto;
        //}
    }


    @Override
    public List<PersonBasicInfoCZDTO> queryPersonInfoCZ(String idNumber, String name, String mobile) {
        if (flagWsTureCZ) {
            PersonBasicListInfoRequest request = new PersonBasicListInfoRequest(new PersonBasicInfoInputListDTO("","01",idNumber, name));
            //request.setBody();
            //PersonBasicInfoOutputDTO dto = (PersonBasicInfoOutputDTO) clientGateWay.query(PersonBasicInfoOutputDTO.class, request, Object.class);
            PersonalEmpListOutResponseDTO personalEmpListOutResponseDTO = new PersonalEmpListOutResponseDTO();
            try {
                personalEmpListOutResponseDTO = (PersonalEmpListOutResponseDTO) clientGateWay.query(ServerNodeEnum.城镇职工.toString(), PersonBasicInfoListResponse.class, request, PersonalEmpListOutResponseDTO.class);
            } catch (WebserviceApiException e) {
                if (e.getMessage().contains("参保信息合并业务")){
                    throw new BadCredentialsException(e.getMessage());
                }else{
                    return  personalEmpListOutResponseDTO.getList();
                }

            }
            LOGGER.debug("个人基本信息城镇职工返回结果personBasicInfoListDTO={}", personalEmpListOutResponseDTO);
            return personalEmpListOutResponseDTO.getList();
        } else {
            PersonalEmpListOutResponseDTO dto = new PersonalEmpListOutResponseDTO();

//            dto.setPersonId(11111L);
//            dto.setName("1111");
//            dto.setCertificateNumber("1111");
//            dto.setSex("01");
            return dto.getList();
        }
    }

    @Override
    public CompanyDTO queryCompanyInfo(String orgCode, String companyName, String companyNumber) {
        if (flagWsTureSi) {
            CompanyDTO companyDTO = new CompanyDTO();
            UnitInfoInputDTO infoInputDTO = new UnitInfoInputDTO();
            infoInputDTO.setCompanyName(companyName);
            infoInputDTO.setSocialCreditCode(orgCode);
            if (!StringUtils.isEmpty(companyNumber)){
                infoInputDTO.setUnitId(Long.valueOf(companyNumber));
            }
            QueryCompanyInfoRequest queryCompanyInfoRequest = new QueryCompanyInfoRequest(infoInputDTO);
            LOGGER.debug("request = {}", queryCompanyInfoRequest.getDto());
            try {
                UnitInfoOutputDTO unitInfoOutputDTO = (UnitInfoOutputDTO) clientGateWay.send(ServerNodeEnum.社保.toString(), QueryCompanyInfoResponse.class, queryCompanyInfoRequest, UnitInfoOutputDTO.class);
                companyDTO.setId(unitInfoOutputDTO.getUnitId());
                companyDTO.setCompanyNumber(unitInfoOutputDTO.getUnitNumber());
                companyDTO.setName(unitInfoOutputDTO.getName());
                companyDTO.setOrgCode(unitInfoOutputDTO.getSocialCreditCode());
                companyDTO.setLevel(unitInfoOutputDTO.getLevel());
                companyDTO.setSocialPoolCode(unitInfoOutputDTO.getSocialPoolCode());
                return companyDTO;
            }
            catch (Exception e) {
                return companyDTO;
            }

        } else {
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setName(companyName);
            companyDTO.setOrgCode(orgCode);
            companyDTO.setCompanyNumber(companyNumber);
            companyDTO.setId(123456L);
            companyDTO.setLevel(0);
            return companyDTO;
        }
    }
}
