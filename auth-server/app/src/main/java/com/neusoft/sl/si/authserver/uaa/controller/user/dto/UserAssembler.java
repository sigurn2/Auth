package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neusoft.sl.si.authserver.base.domains.person.*;
import com.neusoft.sl.si.authserver.base.domains.user.*;
import com.neusoft.sl.si.authserver.base.services.company.CompanyService;
import com.neusoft.sl.si.authserver.simis.SimisWebService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUser;
import com.neusoft.sl.girder.ddd.hibernate.utils.SpringContextUtils;
import com.neusoft.sl.girder.security.oauth2.domain.AppDTO;
import com.neusoft.sl.girder.security.oauth2.domain.AuthenticatedCasUser;
import com.neusoft.sl.girder.security.oauth2.domain.AuthenticatedCasUserAllowed;
import com.neusoft.sl.girder.security.oauth2.domain.CompanyDTO;
import com.neusoft.sl.girder.security.oauth2.domain.OrganizationDTO;
import com.neusoft.sl.girder.security.oauth2.domain.PersonDTO;
import com.neusoft.sl.si.authserver.base.domains.app.App;
import com.neusoft.sl.si.authserver.base.domains.company.Company;
import com.neusoft.sl.si.authserver.base.domains.company.CompanySi;
import com.neusoft.sl.si.authserver.base.domains.company.CompanySiService;
import com.neusoft.sl.si.authserver.base.domains.expert.PersonExpert;
import com.neusoft.sl.si.authserver.base.domains.expert.PersonExpertRepository;
import com.neusoft.sl.si.authserver.base.domains.family.Family;
import com.neusoft.sl.si.authserver.base.domains.org.Organization;
import com.neusoft.sl.si.authserver.base.domains.resource.Menu;
import com.neusoft.sl.si.authserver.base.domains.role.Role;
import com.neusoft.sl.si.authserver.uaa.controller.access.AccessAuthenticatedCasUser;

import javax.annotation.Resource;

/**
 * 用户转换
 *
 * @author mojf
 */
public class UserAssembler {

    private static Logger log = LoggerFactory.getLogger(UserAssembler.class);

    private static PersonRepositoryExtend personRepositoryExtend;

    private static PersonRepository personRepository;

    private static CompanySiService companySiService;

    private static PersonExpertRepository personExpertRepository;

    //private  static SimisWebService simisWebService;;


    private UserAssembler() {
        // hide for utils
    }

    static {
        personRepositoryExtend = SpringContextUtils.getBean("personRepositoryExtend");
        personRepository = SpringContextUtils.getBean("personRepository");
        companySiService = SpringContextUtils.getBean("companySiService");
        personExpertRepository = SpringContextUtils.getBean("personExpertRepository");
        //simisWebService = SpringContextUtils.getBean("simisWebService");
    }

    /**
     * 转换为DTO对象
     *
     * @param value
     * @return
     */
    public static AuthenticatedCasUser toDTO(User value) {
        if (value == null) {
            return null;
        }
        AuthenticatedCasUser dto = new AuthenticatedCasUser();
        dto.setAccount(value.getAccount());
        dto.setName(value.getName());
        dto.setEmail(value.getEmail());
        dto.setMobile(value.getMobile());
        dto.setIdNumber(value.getIdNumber());
        dto.setActived(value.isActivated());
        if ("1".equals(value.getUserTypeString()) && value.getExtension().contains("zwfw")) {
            List<CompanyDTO> list = companySiService.findByOrgCode(value.getOrgCode(), value.getName(),value.getUnitId()== null?null:value.getUnitId().toString());
            if (list.size() != 2 && list.get(0).getName() == null) {
                CompanyDTO companyDTO = new CompanyDTO();
                companyDTO.setClientType("simis");
                list.add(companyDTO);
            }
            dto.setAssociatedCompanys(list);

        }else if ("1".equals(value.getUserTypeString()) && !value.getExtension().contains("zwfw")){
            List<CompanyDTO> list = companySiService.findByUnitId(value.getUnitId());
            if (list.size() != 2 && list.get(0).getName() == null) {
                CompanyDTO companyDTO = new CompanyDTO();
                companyDTO.setClientType("simis");
                companyDTO.setCompanyNumber(value.getUnitId().toString());
                list.add(companyDTO);
            }
            for (CompanyDTO companyDTO:list
                 ) {
                companyDTO.setCompanyNumber(value.getUnitId().toString());
            }
            dto.setAssociatedCompanys(list);
        }

        log.debug("enterpriseUser = {}", value instanceof EnterpriseUser);
        if (value instanceof PersonalUser) {
            dto.setAssociatedPersons(toAssociatedPersons(value.getIdNumber()));
        } else if (value instanceof ExpertUser) {
            dto.setAssociatedPersons(toAssociatedExperts(value.getExpertAccount()));
        }
        // dto.setAssociatedFamilies(FamilyAssembler.toAssociatedFamilies(value.getFamilies()));
        dto.setRoles(toRoles(value.getRoles()));
        dto.setApps(toApps(value.getRoles()));
        dto.setUserType(value.getUserTypeString());
        dto.setHeadImgUrl(value.getHeadImgUrl());
        dto.setMainCompanyNumber(value.getMainCompanyNum());
        dto.setHasRealNameAuth(value.isRealNameAuthed());
        dto.setOrganization(toOrganizationDTO(value.getOrganization()));
        dto.setHasBindCardAuth(value.isBindCardAuthed());
        dto.setHasRealNameFace(value.isRealNameFace());
        dto.setExtension(value.getExtension());
        dto.setOrgCode(value.getOrgCode());
        return dto;
    }

    public static AuthenticatedCasUser redisToDTO(AuthenticatedCasUserByRedis redis, OAuth2Authentication authentication) {
        if (redis == null) {
            return null;
        }
        AuthenticatedCasUser dto = new AuthenticatedCasUser();
        dto.setAccount(redis.getAccount());
        dto.setName(redis.getName());
        dto.setEmail(redis.getEmail());
        dto.setMobile(redis.getMobile());
        dto.setIdNumber(redis.getIdNumber());
        dto.setAssociatedCompanys(redis.getAssociatedCompanys());
        dto.setAssociatedPersons(redis.getAssociatedPersons());
        dto.setRoles(toRoles(authentication));
        dto.setApps(new HashSet<AppDTO>());
        dto.setUserType(redis.getUserType());
        dto.setHeadImgUrl(null);
        dto.setMainCompanyNumber(null);
        dto.setHasRealNameAuth(false);
        dto.setOrganization(null);
        dto.setHasBindCardAuth(false);
        dto.setHasRealNameFace(false);
        return dto;
    }

    private static List<String> toRoles(OAuth2Authentication authentication) {
        Collection<GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> list = new ArrayList<String>();
        if (authorities != null) {
            for (GrantedAuthority grantedAuthority : authorities) {
                list.add(grantedAuthority.getAuthority());
            }
        }
        return list;
    }

    /**
     * 基于缓存方式生成信息，主要用于支付宝，爱南宁登录后直接返回
     *
     * @param value
     * @return
     */
    public static AuthenticatedCasUserByRedis toDTOByRedis(PersonRedisUser value) {
        if (value == null) {
            return null;
        }
        AuthenticatedCasUserByRedis dto = new AuthenticatedCasUserByRedis();
        dto.setAccount(value.getAccount());
        dto.setName(value.getName());
        dto.setEmail(value.getEmail());
        dto.setMobile(value.getMobile());
        dto.setIdNumber(value.getIdNumber());
        dto.setAssociatedCompanys(new ArrayList<CompanyDTO>());
        if (StringUtils.isNotBlank(value.getPersonNumber())) {
            dto.setAssociatedPersons(toAssociatedPersonsByPersonNumber(value.getPersonNumber()));
        } else {
            dto.setAssociatedPersons(toAssociatedPersons(value.getIdNumber()));
        }
        dto.setUserType(value.getUserType());
        return dto;
    }

    /**
     * 基于缓存方式生成信息，主要用于爱南宁登录后直接返回
     *
     * @param value
     * @return
     */
    public static AuthenticatedCasUserByRedis toDTOByRedis(ThinUser value) {
        if (value == null) {
            return null;
        }
        AuthenticatedCasUserByRedis dto = new AuthenticatedCasUserByRedis();
        dto.setAccount(value.getAccount());
        dto.setName(value.getName());
        dto.setEmail(value.getEmail());
        dto.setMobile(value.getMobile());
        dto.setIdNumber(value.getIdNumber());
        dto.setAssociatedCompanys(new ArrayList<CompanyDTO>());
        dto.setAssociatedPersons(toAssociatedPersons(value.getIdNumber()));
        dto.setUserType(value.getUserTypeString());
        return dto;
    }

    /**
     * 提供给一体机使用，/uaa/user返回使用
     *
     * @param idNumber
     * @return
     */
    public static AuthenticatedCasUserByPerson toATMDTOByPerson(String idNumber) {
        AuthenticatedCasUserByPerson dto = new AuthenticatedCasUserByPerson();
        dto.setAssociatedPersons(toPersonDTOByPerson(idNumber));
        return dto;
    }

    /**
     * 一体机
     *
     * @param idNumber
     * @return
     */
    private static List<AtmPersonResultDTO> toPersonDTOByPerson(String idNumber) {
        List<Person> personList = personRepositoryExtend.findByCertificate(idNumber);
        List<AtmPersonResultDTO> list = new ArrayList<AtmPersonResultDTO>();
        AtmPersonResultDTO dto = null;
        for (Person item : personList) {
            if (item != null) {
                dto = new AtmPersonResultDTO();
                dto.setId(item.getId());
                dto.setPersonNumber(item.getPersonNumberString());
                dto.setName(item.getName());
                dto.setRetireStatus(item.getRetireStatus());
                dto.setIdNumber(item.getCertificate().getNumber());
                dto.setCardNumber(item.getSocialSecurityCardNumber());
                list.add(dto);
            }
        }
        return list;
    }

    public static AccessAuthenticatedCasUser toAccessDTO(User value) {
        if (value == null) {
            return null;
        }
        AccessAuthenticatedCasUser dto = new AccessAuthenticatedCasUser();
        dto.setAccount(value.getAccount());
        dto.setName(value.getName());
        dto.setEmail(value.getEmail());
        dto.setMobile(value.getMobile());
        dto.setIdNumber(value.getIdNumber());
        dto.setAssociatedCompanys(toAssociatedCompanys(value.getCompanys()));
        if (value instanceof PersonalUser) {
            dto.setAssociatedPersons(toAssociatedPersons(value.getIdNumber()));
        } else if (value instanceof ExpertUser) {
            dto.setAssociatedPersons(toAssociatedExperts(value.getExpertAccount()));
        }
        dto.setRoles(toRoles(value.getRoles()));
        dto.setApps(toApps(value.getRoles()));
        dto.setUserType(value.getUserTypeString());
        dto.setHeadImgUrl(value.getHeadImgUrl());
        dto.setMainCompanyNumber(value.getMainCompanyNum());
        dto.setHasRealNameAuth(value.isRealNameAuthed());
        dto.setOrganization(toOrganizationDTO(value.getOrganization()));
        return dto;
    }

    /**
     * 组织机构信息转换
     *
     * @param org
     * @return
     */
    private static OrganizationDTO toOrganizationDTO(Organization org) {
        if (null == org) {
            return null;
        }
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(org.getId());
        dto.setName(org.getName());
        dto.setOrgNumber(org.getOrganizationNumberString());
        dto.setAddress(org.getAddress());
        dto.setOrgType(org.getOrgType());
        dto.setPhone(org.getPhone());
        dto.setStateCode(org.getStateCode());
        dto.setZip(org.getZip());
        return dto;
    }

    /**
     * 系统数据转换
     *
     * @param roles
     * @return
     */
    private static Set<AppDTO> toApps(Set<Role> roles) {
        Set<AppDTO> apps = new HashSet<AppDTO>();
        AppDTO dto = null;
        for (Role item : roles) {
            if (item != null) {
                // LOGGER.trace("===============角色=={}================", item);
                // LOGGER.trace("===============应用数=={}================",
                // item.getApps().size());
                for (App app : item.getApps()) {
                    if (app != null) {
                        dto = new AppDTO();
                        dto.setId(app.getId());
                        dto.setName(app.getName());
                        dto.setDesc(app.getDesc());
                        dto.setUrl(app.getUrl());
                        apps.add(dto);
                    }
                }
            }
        }
        return apps;
    }

    /**
     * 角色数据转换
     *
     * @param roles
     * @return
     */
    private static List<String> toRoles(Set<Role> roles) {
        List<String> list = new ArrayList<String>();
        for (Role item : roles) {
            if (item != null) {
                list.add(item.getName());
            }
        }
        return list;
    }

    public static List<PersonDTO> toAssociatedExperts(String idNumber) {
        List<PersonExpert> personList = personExpertRepository.findByIdNumber(idNumber);
        List<PersonDTO> list = new ArrayList<PersonDTO>();
        PersonDTO dto = null;
        for (PersonExpert item : personList) {
            if (item != null) {
                dto = new PersonDTO();
                dto.setId(Long.parseLong(item.getId()));
                dto.setPersonNumber(item.getPersonNumber());
                dto.setName(item.getName());
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * 关联个人数据转换
     *
     * @param persons
     * @return
     */
    public static List<PersonDTO> toAssociatedPersons(String idNumber) {
        //List<PersonBasicInfo> list = simisWebService.queryPersonInfo(idNumber);
        List<Person> personList = personRepositoryExtend.findByCertificate(idNumber);
        List<PersonDTO> list = new ArrayList<PersonDTO>();
        PersonDTO dto = null;
        for (Person item : personList) {
            if (item != null) {
                dto = new PersonDTO();
                dto.setId(item.getId());
                dto.setPersonNumber(item.getPersonNumberString());
                dto.setName(item.getName());
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * 关联个人数据转换
     *
     * @param persons
     * @return
     */
    public static List<PersonDTO> toAssociatedPersonsAtm(String account) {
        //List<PersonBasicInfo> listp = simisWebService.queryPersonInfo(account.split("@@")[0],account.split("@@")[1],"").getPersonBasicInfoList();

        List<PersonDTO> list = new ArrayList<PersonDTO>();
        return list;
    }

    /**
     * 关联单位数据转换
     *
     * @param companys
     * @return
     */
    private static List<CompanyDTO> toAssociatedCompanys(Set<Company> companys) {
        List<CompanyDTO> list = new ArrayList<CompanyDTO>();
        if (companys == null) {
            return list;
        }
        CompanyDTO dto = null;
        for (Company item : companys) {
            if (item != null) {
                dto = new CompanyDTO();
                dto.setId(item.getId());
                dto.setCompanyNumber(item.getCompanyNumber());
                dto.setName(item.getName());
                dto.setOrgCode(item.getOrgCode());
                list.add(dto);
            }
        }
        return list;
    }

    private static List<CompanyDTO> toAssociatedCompanysForSi(List<CompanyDTO> companys) {
        List<CompanyDTO> list = new ArrayList<CompanyDTO>();
        CompanyDTO dto = null;
        for (CompanyDTO item : companys) {
            if (item != null) {
                CompanySi companySi = companySiService.findByCompanyNumber(item.getCompanyNumber());
                if (null != companySi) {
                    dto = new CompanyDTO();
                    dto.setId(companySi.getId());
                    dto.setCompanyNumber(companySi.getCompanyNumber());
                    dto.setName(companySi.getName());
                    dto.setOrgCode(companySi.getOrgCode());
                    list.add(dto);
                }
            }
        }
        return list;
    }

    /**
     * 构造前台菜单树
     *
     * @param menus
     * @return
     */
    public static List<MenuDTO> constructMenus(Set<Menu> menus) {
        List<MenuDTO> roots = new ArrayList<MenuDTO>();
        Map<String, List<MenuDTO>> leafNodes = new HashMap<String, List<MenuDTO>>();
        List<MenuDTO> lists = null;
        for (Menu n : menus) {
            if (n.getParent() == null) {
                roots.add(toMenuDTO(n));
            } else {
                if (!leafNodes.containsKey(n.getParent().getId())) {
                    lists = new ArrayList<MenuDTO>();
                    leafNodes.put(n.getParent().getId(), lists);
                }
                leafNodes.get(n.getParent().getId()).add(toMenuDTO(n));
            }
        }

        for (MenuDTO root : roots) {
            creatRootMenus(root, leafNodes);
        }
        // 对菜单排序
        Collections.sort(roots);
        return roots;
    }

    public static MenuDTO creatRootMenus(MenuDTO node, Map<String, List<MenuDTO>> leafNodes) {
        if (leafNodes.containsKey(node.getId())) {
            List<MenuDTO> nodes = leafNodes.get(node.getId());
            for (MenuDTO dto : nodes) {
                node.addMenus(creatRootMenus(dto, leafNodes));
            }
        }
        return node;
    }

    /**
     * 转换为DTO
     *
     * @param menu
     * @return
     */
    public static MenuDTO toMenuDTO(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setIndex(menu.getIndex());
        dto.setLabel(menu.getTitle());
        dto.setPath(menu.getUrl());
        return dto;
    }

    public static Set<PersonDTO> toAssociatedFamilies(Set<Family> families) {
        Set<PersonDTO> list = new HashSet<PersonDTO>();
        PersonDTO dto = null;
        for (Family item : families) {
            // 平行权限只处理实名认证的
            if (item != null && item.isAuth()) {
                List<Person> personList = personRepositoryExtend.findByCertificate(item.getIdNumber());
                if (personList == null) {
                    continue;
                }
                for (Person person : personList) {
                    dto = new PersonDTO();
                    dto.setId(person.getId());
                    dto.setPersonNumber(person.getPersonNumberString());
                    dto.setName(person.getName());
                    list.add(dto);
                }
            }
        }
        return list;
    }

    private static List<PersonDTO> toAssociatedPersonsByPersonNumber(String personNumber) {
        PersonNumber number = new PersonNumber(personNumber);
        List<Person> personList = personRepository.findByPersonNumber(number);
        List<PersonDTO> list = new ArrayList<PersonDTO>();
        PersonDTO dto = null;
        for (Person item : personList) {
            if (item != null) {
                dto = new PersonDTO();
                dto.setId(item.getId());
                dto.setPersonNumber(item.getPersonNumberString());
                dto.setName(item.getName());
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * 转换为供平行权限验证使用的DTO对象
     *
     * @param value
     * @return
     */
    public static AuthenticatedCasUserAllowed toAllowedDTO(User value) {
        if (value == null) {
            return null;
        }
        AuthenticatedCasUserAllowed dto = new AuthenticatedCasUserAllowed();
        log.debug("平行权限数据组装companyNumber={}", value.getAccount());
        List<CompanyDTO> companyDTOs = toAssociatedCompanys(value.getCompanys());
        companyDTOs.addAll(toAssociatedCompanysForSi(companyDTOs));
        dto.setAssociatedCompanys(companyDTOs);
        log.debug("平行权限数据组装完成companyNumber={}", value.getAccount());
        if (value instanceof PersonalUser) {
            log.debug("平行权限数据组装idNumber={}", value.getIdNumber());
            List<PersonDTO> personDTOs = toAssociatedPersons(value.getIdNumber());
            personDTOs.addAll(toAssociatedFamilies(value.getFamilies()));
            dto.setAssociatedPersons(personDTOs);
            log.debug("平行权限数据组装完成idNumber={}", value.getIdNumber());
        } else if (value instanceof ExpertUser) {
            log.debug("平行权限数据组装expertAccount={}", value.getExpertAccount());
            dto.setAssociatedPersons(toAssociatedExperts(value.getExpertAccount()));
            log.debug("平行权限数据组装完成expertAccount={}", value.getExpertAccount());
        }
        return dto;
    }


    /**
     * 生成平行权限时使用
     *
     * @param idNumber
     * @return
     */
    public static AuthenticatedCasUserAllowed toAllowedDTOByIdNumber(String idNumber) {
        AuthenticatedCasUserAllowed dto = new AuthenticatedCasUserAllowed();
        log.debug("平行权限数据组装idNumber={}", idNumber);
        dto.setAssociatedPersons(toAssociatedPersons(idNumber));
        log.debug("平行权限数据组装完成idNumber={}", idNumber);
        return dto;
    }

    /**
     * 生成平行权限时使用
     *
     * @param idNumber
     * @return
     */
    public static AuthenticatedCasUserAllowed toAllowedDTOByAtm(String account) {
        AuthenticatedCasUserAllowed dto = new AuthenticatedCasUserAllowed();
        log.debug("平行权限数据组装idNumber={}", account);
        dto.setAssociatedPersons(toAssociatedPersonsAtm(account));
        log.debug("平行权限数据组装完成idNumber={}", account);
        return dto;
    }


    /**
     * 生成平行权限时使用
     *
     * @param personNumber
     * @return
     */
    public static AuthenticatedCasUserAllowed toAllowedDTOByPersonNumber(String personNumber) {
        AuthenticatedCasUserAllowed dto = new AuthenticatedCasUserAllowed();
        log.debug("平行权限数据组装personNumber={}", personNumber);
        dto.setAssociatedPersons(toAssociatedPersonsByPersonNumber(personNumber));
        log.debug("平行权限数据组装完成personNumber={}", personNumber);
        return dto;
    }
}
