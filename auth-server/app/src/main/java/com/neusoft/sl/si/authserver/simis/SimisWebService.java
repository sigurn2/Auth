package com.neusoft.sl.si.authserver.simis;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.PersonBasicInfoOutputDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.UnitInfoOutputDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonBasicInfoCZDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonalEmpListOutResponseDTO;
import com.neusoft.sl.girder.security.oauth2.domain.CompanyDTO;
import com.neusoft.sl.si.authserver.base.domains.company.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SimisWebService {

    /**
     * 根据身份证号查询
     *
     * @param
     * @return
     */
    public PersonBasicInfoOutputDTO queryPersonInfo(String idNumber, String name, String mobile);

    public List<PersonBasicInfoCZDTO> queryPersonInfoCZ(String idNumber, String name, String mobile);

    public CompanyDTO queryCompanyInfo(String orgCode, String companyName,String companyNumber);

}
