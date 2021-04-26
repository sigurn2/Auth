package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

/**
 * 单位信息查询（入参）
 * @author tianmx
 */
public class UnitInfoInputDTO {

    /*** 单位编号 */
    private String unitNumber;

    /*** 单位编号 */
    private Long unitId;

    /*** 统一社会信用代码 */
    private String socialCreditCode;

    /**
     * 单位名称
     */
    private String companyName;


    public UnitInfoInputDTO() {

    }
    public UnitInfoInputDTO(String unitNumber, Long unitId,String socialCreditCode) {
        this.unitNumber = unitNumber;
        this.unitId = unitId;
        this.socialCreditCode = socialCreditCode;
    }

    public UnitInfoInputDTO(String socialCreditCode, String companyName) {
        this.socialCreditCode = socialCreditCode;
        this.companyName = companyName;
    }

    public UnitInfoInputDTO(String unitNumber, String socialCreditCode, String companyName) {
        this.unitNumber = unitNumber;
        this.socialCreditCode = socialCreditCode;
        this.companyName = companyName;
    }

    public UnitInfoInputDTO(Long unitId, String socialCreditCode, String companyName) {
        this.unitId = unitId;
        this.socialCreditCode = socialCreditCode;
        this.companyName = companyName;
    }

    public UnitInfoInputDTO(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "UnitInfoInputDTO{" +
                "unitNumber='" + unitNumber + '\'' +
                ", unitId=" + unitId +
                ", socialCreditCode='" + socialCreditCode + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
