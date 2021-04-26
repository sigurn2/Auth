package com.neusoft.sl.si.authserver.base.domains.company;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.neusoft.sl.girder.ddd.hibernate.entity.EntityBase;

@Entity
@Access(AccessType.FIELD)
@Table(name = "COMPANY_SI")
public class CompanySi extends EntityBase<CompanySi, String> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 单位编号 */
    @Column(name = "COMPANYNUMBER")
    private String companyNumber;

    /** 单位名称 */
    private String name;

    /** 单位税号 */
    @Column(name = "TAXCODE")
    private String taxCode;

    /** 单位代码 **/
    @Column(name = "ORGCODE")
    private String orgCode;
    /** 地区 **/
    @Column(name = "AREACODE")
    private String areaCode;

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode the areaCode to set
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the companyNumber
     */
    public String getCompanyNumber() {
        return companyNumber;
    }

    /**
     * @param companyNumber the companyNumber to set
     */
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the taxCode
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * @param taxCode the taxCode to set
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public String getPK() {
        return companyNumber;
    }

}
