package com.neusoft.sl.si.authserver.uaa.validate.identity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 表示中华人民共和国身份证对象
 * <p>
 * 迁移原3.1中代码
 * 
 * 公民身份号码是特征组合码,由十七位数字本体码和一位数字校验码组成.
 * 
 * 排列顺序从左至右依次为:六位数字地址码,八位数字出生日期码,三位数字顺序码和一位数字校验码。
 * 
 * 1、地址码 表示编码对象常住户口所在县（市、旗、区）的行政区划代码，按 GB/T 2260 的规定执行。
 * 
 * 2、出生日期码 表示编码对象出生的年、月、日，按 GB/T 7408 的规定执行。年、月、日代码之间不用分隔符。 例：某人出生日期为
 * 1966年10月26日，其出生日期码为 19661026。
 * 
 * 3、顺序码 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数千分配给女性。
 * 
 * 4 、校验码 校验码采用ISO 7064：1983，MOD 11-2 校验码系统。
 * 
 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 * 
 * （2）计算模 Y = mod(S, 11)
 * 
 * （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
 * 
 * 
 * 
 * </p>
 * 
 * 新增方法
 * 1.校验15位和18位身份证号是否合法：
 * 	（1）是否符合位数和数字组合要求，d(15)或d(18)或d(17)x|X
 *  （2）身份证中的出生日期是否合法
 *  （3）身份证前2位表示所在地是否合法
 * 2.根据身份证号码获取出生日期
 * 3.根据身份证号码获取性别
 * 4.根据身份证号码获取省份
 *  
 * 
 * @author tanxy
 * @author wuyf
 */
public class IdentityCard extends AbstractCertificate implements Certificate {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 526071008488604503L;
	private static String  MALE = "1";
	private static String FEMALE = "2";

	// wi =2(n-1)(mod 11)
	private static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };

	// verify digit
	private static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };

	
	
	private Map<String, String> cityCodeMap = new HashMap<String, String>() { 
		/**  
		* 省，直辖市代码表： { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",  
		* 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",  
		* 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",  
		* 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",  
		* 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",  
		* 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}  
		*/  
		
		private static final long serialVersionUID = 1L;

		{
			this.put("11", "北京");
			this.put("12", "天津"); 
			this.put("13", "河北");
			this.put("14", "山西");
			this.put("15", "内蒙古");
			this.put("21", "辽宁");   
			this.put("22", "吉林");   
			this.put("23", "黑龙江");   
			this.put("31", "上海");   
			this.put("32", "江苏");   
			this.put("33", "浙江");   
			this.put("34", "安徽");   
			this.put("35", "福建");   
			this.put("36", "江西");   
			this.put("37", "山东");   
			this.put("41", "河南");   
			this.put("42", "湖北");   
			this.put("43", "湖南");   
			this.put("44", "广东");   
			this.put("45", "广西");   
			this.put("46", "海南");   
			this.put("50", "重庆");   
			this.put("51", "四川");   
			this.put("52", "贵州");   
			this.put("53", "云南");   
			this.put("54", "西藏");   
			this.put("61", "陕西");   
			this.put("62", "甘肃");   
			this.put("63", "青海");   
			this.put("64", "宁夏");   
			this.put("65", "新疆");   
			this.put("71", "台湾");   
			this.put("81", "香港");   
			this.put("82", "澳门");   
			this.put("91", "国外"); 
		}
	};
	
	private String cityCode[] = { "11", "12", "13", "14", "15", "21", "22", 
			"23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", 
			"44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", 
			"64", "65", "71", "81", "82", "91" };  

	/**
	 * 身份证构造函数
	 * 
	 * @param number
	 *            身份证号
	 */
	public IdentityCard(String number) {
		Validate.notNull(number, "身份证号码不能为空%s",number);
		Validate.notBlank(number,"身份证号码不能为空字符%s",number);
		this.number = number;
		this.certificateType = CertificateEnum.居民身份证;
		this.socialEnsureNumber=number;
	}
	
	/**
	 * 身份证构造函数新增 20170430
	 * 解决查询人员信息时证件号码与社会保障号码不同时的显示问题
	 * @param number 证件号码
	 * @param socialEnsureNumber 社会保障号码
	 */
	public IdentityCard(String number, String socialEnsureNumber) {
		Validate.notNull(number, "身份证号码不能为空%s",number);
		Validate.notBlank(number,"身份证号码不能为空字符%s",number);
		this.number = number;
		this.certificateType = CertificateEnum.居民身份证;
		if (StringUtils.isNotBlank(socialEnsureNumber)){
			this.socialEnsureNumber=socialEnsureNumber;
		} else {
			this.socialEnsureNumber=number;
		}
	}

	/**
	 * 构造函数
	 */
	IdentityCard() {
		super();
		// for hibernate
	}
	
	/**
	 * 验证身份证号有效性，15位转18位，从3.1迁移过来
	 * @param number
	 * @return
	 */
	public boolean verify(){
		if(number.trim().length() == 15 && verifyFifteenCardId(number)){
			number = upToEighteen(number);
		}
		if(number.trim().length() == 18 && verifyEightteenCardId(number)){
			String provinceid = number.substring(0, 2);
			String birthday = number.substring(6, 14);
			int year = Integer.parseInt(number.substring(6, 10));
			int month = Integer.parseInt(number.substring(10, 12));
			int day = Integer.parseInt(number.substring(12, 14));
			
			//1校验身份证里所在地是否合法
			boolean flag = false;
			for (String id : cityCode) {
				if (id.equals(provinceid)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				
				return false;
			}
			
			//2校验身份证里的出生日期是否合法
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (birthdate == null || new Date().before(birthdate)) {
				return false;
			}
			// 判断是否为合法的年份
			GregorianCalendar curDay = new GregorianCalendar();
			int curYear = curDay.get(Calendar.YEAR) - 2;

			// 判断该年份的两位表示法，小于1900的和大于当前年份的，为假
			if ((year < 1900 && year > curYear)) {
				return false;
			}
			// 判断是否为合法的月份
			if (month < 1 || month > 12) {
				return false;
			}
			// 判断是否为合法的日期
			boolean mflag = false;
			curDay.setTime(birthdate);  //将该身份证的出生日期赋于对象curDay
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				mflag = (day >= 1 && day <= 31);
				break;
			case 2: //公历的2月非闰年有28天,闰年的2月是29天。
				if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
					mflag = (day >= 1 && day <= 29);
				} else {
					mflag = (day >= 1 && day <= 28);
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				mflag = (day >= 1 && day <= 30);
				break;
			}
			if (!mflag) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 15位身份证的正则表达式验证
	 * @param number
	 * @return
	 */
	
	public boolean verifyFifteenCardId(String number){
		Pattern p = Pattern.compile("\\d{15}");
		Matcher m = p.matcher(number);
		if(m.matches()){
			return true;
		}
		return false;
		
	}
	
	/**
	 * 18位身份证的正则表达式验证
	 * @param number
	 * @return
	 */
	public boolean verifyEightteenCardId(String number){
		number = number.toUpperCase(); 
		Pattern p = Pattern.compile("\\d{17}([0-9]|X)");
		Matcher m = p.matcher(number);
		if(m.matches()){
			//判断校验位
			String verify = number.substring(17, 18);
			if (verify.equals(getVerify(number))) {
				return true;
			}
			return false;
		}
		return false;
	}
	

	/**
	 * 获取验证码
	 * @param eightcardid
	 * @return
	 */
	private static String getVerify(String eightcardid){
		int remaining = 0;
		int[] ai = new int[18];
		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}
		
		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i<17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
		}

		for (int i = 0; i <17; i++) {
			sum = sum + wi[i] * ai[i];
		}
			remaining = sum % 11;
		}

		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}
	
	/**
	 * 15位身份证转换为18位身份证
	 * 
	 * @param fifteencardid
	 * @return
	 */
	public static String upToEighteen(String fifteenCardId) {
		if (fifteenCardId!=null && fifteenCardId.length()==15){
			String eightcardid = fifteenCardId.substring(0, 6);
			eightcardid = eightcardid + "19";
			eightcardid = eightcardid + fifteenCardId.substring(6, 15);
			eightcardid = eightcardid + getVerify(eightcardid);
			return eightcardid;
		}
		return fifteenCardId;		
	}
	
	/**
	 * 18位身份证转换为15位
	 * @param eighteenCardId
	 * @return
	 */
	public static String downToFifteen(String eighteenCardId){
		if (eighteenCardId!=null && eighteenCardId.length()==18 && "19".equals(eighteenCardId.substring(6, 8))){
			StringBuffer buffer  = new StringBuffer(eighteenCardId.substring(0, 6));
			buffer.append(eighteenCardId.substring(8, 17));
			return new String(buffer);
		}
		return eighteenCardId;
	}
	
	/**
	 * 如果是15位则转换为18位，如果是18位则转换为15位
	 * @return
	 */
	public static String convertEighteenOrFifteen(String number){
		Validate.notNull(number, "身份证号码不能为空%s",number);
		Validate.notBlank(number,"身份证号码不能为空字符%s",number);
		if (number.length()==15){
			return upToEighteen(number);
		}else{
			return downToFifteen(number);
		}
	}
	
	/**
	 * 如果是15位则转换为18位，如果是18位则转换为15位
	 * @return
	 */
	public String convertEighteenOrFifteen(){
		if (this.number.length()==15){
			return upToEighteen(this.number);
		}else{
			return downToFifteen(this.number);
		}
	}
	
	/**
	 * 获得出生日期
	 */
	public Date getBirthday() {
		Calendar birthdayCalendar = Calendar.getInstance();
		if(number !=null && (number.trim().length() == 15 || number.trim().length() == 18)){
			if(number.trim().length() == 15){
				number = upToEighteen(number);
			}
			
			birthdayCalendar.set(Calendar.YEAR, Integer.parseInt(number.substring(6, 10)));
			birthdayCalendar.set(Calendar.MONTH, Integer.parseInt(number.substring(10, 12)) - 1);
			birthdayCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(number.substring(12, 14)));
			birthdayCalendar.set(Calendar.HOUR_OF_DAY, 0);
			birthdayCalendar.set(Calendar.MINUTE, 0);
			birthdayCalendar.set(Calendar.SECOND, 0);
			return birthdayCalendar.getTime();
		}
		return null;
	}

	/**
	 * 获得性别
	 */
	public String getSex() {
		if(number !=null && (number.trim().length() == 15 || number.trim().length() == 18)){
			if(number.trim().length() == 15){
				number = upToEighteen(number);
			}
			 if (Integer.parseInt(number.substring(16,17)) % 2 == 0) {
				return FEMALE;
			} else {
				return MALE;
			}
		}
		return null;
	}
	
	/**
	 * 获得社会保障编码
	 */
	public String getSocialEnsureNumber() {
		return socialEnsureNumber;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	
	/**  
	* @return the province  
	*/  
	public String getProvince() {
		if(number !=null && (number.trim().length() == 15 || number.trim().length() == 18)){
			String provinceId = number.substring(0, 2);
			String province = "";
			Set<String> key = this.cityCodeMap.keySet();
			for (String id : key) {   
				if (id.equals(provinceId)) {
					province = this.cityCodeMap.get(id); 
					break; 
				}
			}
			return province;  
		}
		return null;
		 
	}
	
	public CertificateEnum getCertificateType() {
		return this.certificateType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IdentityCard [certificateType=")
				.append(certificateType).append(", number=").append(number)
				.append(", socialEnsureNumber=").append(socialEnsureNumber)
				.append("]");
		return builder.toString();
	}

}
