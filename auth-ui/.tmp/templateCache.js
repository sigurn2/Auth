angular.module('AuthUI').run(['$templateCache', function($templateCache) {
  'use strict';

  $templateCache.put('modules/ca/views/login.html',
    "<div class=\"L_bag\" id=\"caview\" ng-init=\"loginButton = '数据加载中。。。';initLogin()\"> <div class=\"L_three_enterprise\"> <div class=\"L_t_left\"></div> <div class=\"L_t_right\"> <form class=\"form-horizontal\" style=\"margin: 0 15px\" method=\"post\" id=\"loginForm\" name=\"loginForm\" ng-submit=\"loginSystem(loginForm)\"> <div class=\"L_o_text\">欢迎登录</div> <div class=\"fg\" style=\"height:10px\"></div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">选择证书：</div> <div class=\"L_T_adright\"> <select id=\"selectcert\" class=\"L_t_input\" ng-options=\"x.certcn for x in credentials.certsOptions track by x.certid\" data-ng-model=\"credentials.selectedOption\" required> </select> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">证书密码：</div> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"credentials.password\" required> </div> </div> <div class=\"fg\" style=\"height:20px\"></div> <button type=\"submit\" class=\"L_t_text_color hand\" style=\"margin-left: 11%\" ng-disabled=\"isSubmit\"> {{loginButton}} </button> </form> <div style=\"float: right;margin-right:10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/GXCA.zip\">广西CA驱动下载</a> </div> <div style=\"float: left;margin-left: 10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/DFCA.rar\">东方CA驱动下载</a> </div> <div style=\"float: left;margin-left: 10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/OperationInstruction.rar\">用户手册下载</a> </div> <div style=\"float: right;margin-right:10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/CAApplication.rar\">申请表格下载</a> </div> <div style=\"float: left;margin-left: 10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"javascript:alert('暂无文件');\">通知文件下载</a> </div> <div style=\"float: right;margin-right:10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/QQGroup.rar\">交流群QQ号</a> </div> <div style=\"float: left;margin-left: 10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/BusinessInstruction.rar\">（重要）单位网报业务须知</a> </div> </div> </div> </div> <div class=\"L_twofg\" style=\"border-bottom:#d4d4d4 1px solid;height:10px\"></div> <div class=\"P_L_twofg\"> <div class=\"P_L_twoleft\" style=\"width:auto\"> 建议使用Google Chrome 浏览器（Chrome 49.0及以上版本）、360浏览器（极速模式）访问系统。 <a target=\"_blank\" style=\"color:red\">Chrome 64位浏览器下载 <a style=\"bold\" href=\"http://dl.google.com/chrome/install/ChromeStandaloneSetup64.exe\">点击下载；</a> <a target=\"_blank\" style=\"color:red\">Chrome 32位浏览器下载<a style=\"bold\" href=\"http://dl.google.com/chrome/install/ChromeStandaloneSetup.exe\">点击下载；</a> <a target=\"_blank\" style=\"color:red\">360浏览器 <a style=\"bold\" href=\"https://browser.360.cn/ee/\">点击下载；</a> <div>版权所有：南宁市人力资源和社会保障局</div> </a></a></a></div> </div>"
  );


  $templateCache.put('modules/enterprise/views/bxlogin.html',
    "<div class=\"L_bag\"> <div class=\"L_three\"> <div class=\"L_t_right\"> <form name=\"loginForm\" id=\"loginForm\" ng-submit=\"loginSystem(loginForm)\"> <div class=\"L_o_text\">账号登录</div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"username\" id=\"username\" autofocus data-ng-model=\"credentials.username\" required type=\"text\" placeholder=\"请输入用户名\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"credentials.password\" data-ng-minlength=\"6\" required placeholder=\"请输入密码\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{credentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"L_t_input\" data-ng-model=\"credentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\"> </div> <div class=\"L_t_Code\"> <img id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"84\" height=\"40\" alt=\"验证码\"> </div> </div> <button type=\"submit\" class=\"L_t_text_color hand\">登 录 系 统</button> </form> </div> </div> </div> <div class=\"L_twofg\"></div>"
  );


  $templateCache.put('modules/enterprise/views/login.html',
    "<div class=\"L_bag\"> <div class=\"L_three\"> <div class=\"L_t_right\"> <form name=\"loginForm\" id=\"loginForm\" ng-submit=\"loginSystem(loginForm)\"> <div class=\"L_o_text\">账号登录（{{areaName}}）</div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"username\" id=\"username\" autofocus data-ng-model=\"credentials.username\" required type=\"text\" placeholder=\"请输入用户名\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"credentials.password\" data-ng-minlength=\"6\" required placeholder=\"请输入密码\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{credentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"L_t_input\" data-ng-model=\"credentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\"> </div> <div class=\"L_t_Code\"> <img id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"84\" height=\"40\" alt=\"验证码\"> </div> </div> <button type=\"submit\" class=\"L_t_text_color hand\">登 录 系 统</button> <div class=\"other_login\"> <div class=\"other_login_title\"> <span class=\"line\"></span> <span class=\"txt\">使用其他账号登录</span> <span class=\"line\"></span> </div> <a href=\"http://175.174.62.1:8081/uaa/idstools/getGssionid/enterprise\" class=\"link_btn2\" target=\"_self\"></a> </div> </form> </div> </div> <div class=\"pop_box\" ng-show=\"popShow\"> <a href=\"\" class=\"pop_close\" data-ng-click=\"popClose()\"></a> <div class=\"pop_head\">本溪市人力资源和社会保障公共服务平台试运行</div> <div class=\"pop_con\"> <p><b>市级原账号密码登录相关说明：</b>本页面为市级原网厅账号密码登录入口，如您已在原网厅开通过账号，可使用原账号密码登录。</p> </div> </div> </div> <div class=\"L_twofg\"></div>"
  );


  $templateCache.put('modules/expert/views/expertlogin.html',
    "<div class=\"L_bag\"> <div class=\"L_three_expert\"> <div class=\"L_t_left\"></div> <div class=\"L_t_right\"> <form name=\"loginForm\" id=\"loginForm\" ng-submit=\"loginSystem(loginForm)\"> <div class=\"L_o_text\">欢迎登录</div> <div class=\"fg\" style=\"height:20px\"></div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">用户名：</div> <div class=\"L_T_adright\"> <input id=\"username\" data-ng-model=\"personcredentials.idNum\" name=\"username\" class=\"P_L_t_input\" placeholder=\"证件号码\" type=\"text\" required> {{loginForm.username.$valid}} </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">密 码：</div> <div class=\"L_T_adright\"> <input id=\"inputPassword\" name=\"password\" data-ng-model=\"personcredentials.password\" class=\"P_L_t_input\" type=\"password\" required> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">验证码：</div> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{personcredentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"P_L_t_input\" data-ng-model=\"personcredentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\" style=\"width: 90px\"> </div> <div class=\"L_t_Code\"> <span> <img id=\"captchaImage\" ng-src=\"{{personcaptchaUrl}}\" style=\"margin-bottom: 3px\" data-ng-click=\"persongetCaptcha()\" width=\"80\" height=\"30\" alt=\"验证码\"></span> </div> <span style=\"float: left;height: 20px;margin-left: 70px;color:red\" ng-if=\"!loginForm.captchaWord.$valid && loginForm.captchaWord.$touched\"> 请输入4位验证码</span> </div> <div class=\"fg\" style=\"height: 20px\"></div> <div class=\"P_L_t_text_color hand\"> <button class=\"P_L_t_text_color hand\" type=\"submit\">登 录 系 统</button> </div> <div class=\"P_L_t_text\" style=\"margin-top: -20px\"> </div> <div class=\"fg\"></div> <div class=\"fg\" style=\"height:35px\"></div> <div class=\"L_t_text\" style=\"margin-top: -45px\"> <div style=\"float:left; width:80px; height:30px; padding-left:20px; font-size:12px;cursor: pointer\"> <span sty=\"font-size:13px; float:right;\"><a href=\"#/expertPassword\">忘记密码</a></span></div> <div style=\"float:left; width:80px; height:30px; margin-left:80px\"><a href=\"#/expertRegister\">注册激活</a></div> </div> </form> </div> </div> </div> <div class=\"L_twofg\" style=\"border-bottom:#d4d4d4 1px solid;height:10px\"></div> <div class=\"P_L_twofg\"> <div class=\"P_L_twoleft\" style=\"width:auto\"> 建议使用Google Chrome 浏览器（Chrome 49.0及以上版本）、Internet Explorer 浏览器（IE 11稳定版本）、搜狗浏览器（急速模式）、360浏览器（极速模式）访问系统。 <span style=\"color:red\">Chrome 49.0及以上版本 <a style=\"bold\" href=\"https://www.google.cn/intl/zh-CN/chrome/\">点击下载；</a> <span style=\"color:red\">IE11浏览器 <a style=\"bold\" href=\"https://www.microsoft.com/zh-cn/windows/\">点击下载；</a> <span style=\"color:red\">搜狗浏览器 <a style=\"bold\" href=\"https://ie.sogou.com//\">点击下载；</a> <span style=\"color:red\">360浏览器 <a style=\"bold\" href=\"https://browser.360.cn/ee/\">点击下载；</a> <div>版权所有：南宁市人力资源和社会保障局</div> </span></span></span></span></div> </div>"
  );


  $templateCache.put('modules/expert/views/expertpassword.html',
    "<div style=\"background:#f6f6f6\"> <div class=\"P_L_cen_right\" style=\"margin-top:-60px;margin-right:180px\"> <div class=\"P_L_ceb_R01 hand\"><a href=\"#/expertlogin\" class=\"P_L_ceb_R01 hand\">登 录</a></div> <div class=\"P_L_ceb_R02 hand\"><a href=\"#/expertRegister\" class=\"P_L_ceb_R02 hand\">注 册</a></div> </div> <div class=\"P_L_twobg\"> <form name=\"resetForm\" id=\"resetForm\"> <div class=\"P_L_register\" style=\"height:480px\"> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>姓名：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" autofocus class=\"L_I_input\" id=\"name\" ng-model=\"passWordResetDetailDTO.name\" name=\"name\" type=\"text\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.name.$dirty && resetForm.name.$invalid]\" style=\"line-height:34px\">请输入专家姓名。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>证件号码：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" id=\"idNumber\" ng-model=\"passWordResetDetailDTO.idNumber\" data-ng-pattern=\"/(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/\" name=\"idNumber\" required type=\"text\"> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.idNumber.$dirty && resetForm.idNumber.$invalid]\" style=\"line-height:34px\">请输入18位有效证件号码。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>专家编号：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" autofocus class=\"L_I_input\" id=\"personNumber\" ng-model=\"passWordResetDetailDTO.personNumber\" name=\"personNumber\" type=\"text\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.name.$dirty && resetForm.name.$invalid]\" style=\"line-height:34px\">请输入您的专家编号。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>图形验证码：</div> <div class=\"P_L_I_Cen03\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{captcha.captchaId}}\"> <input type=\"text\" style=\"width: 220px\" id=\"captchaWord\" name=\"captchaWord\" class=\"P_L_t_input\" data-ng-model=\"captcha.captchaWord\" required placeholder=\"点击图片更换验证码\" style=\"width: 90px\"> </div> <div class=\"L_t_Code\"> <span> <img style=\"margin-left:70%\" id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"80\" height=\"30\" alt=\"验证码\"></span> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>手机验证码：</div> <div class=\"P_L_I_Cen\"> <input class=\"P_L_I_input2\" name=\"validateMobileNumber\" ng-model=\"passWordResetDetailDTO.captcha\" required type=\"text\"> <div class=\"P_L_I_buttom\"> <div class=\"P_L_I_Green hand\"> <input id=\"btnPSendCode\" class=\"P_L_I_Green hand\" value=\"发送验证码\" data-ng-click=\"passwordCtrlMessage()\" ng-disabled=\"resetForm.mobilenumber.$invalid\"> </div> </div> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.validateMobileNumber.$dirty && resetForm.validateMobileNumber.$invalid]\" style=\"line-height:34px\">请输入您接收到的验证码。 </div> </div> </div> <div class=\"P_L_T_input\" style=\"height:auto\"> <div class=\"P_L_I_left\" style=\"height:auto\"></div> <div class=\"P_L_I_Cen\" style=\"margin-left:200px;height:auto;width:500px\"> <span style=\"color:red; font-size:13px\" class=\"ng-binding\">{{pwmobilenumbermsg}}</span> </div> <div class=\"P_L_I_Right\" style=\"height:auto\"> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>设置密码：</div> <div class=\"P_L_I_Cen03\"> <input class=\"P_L_I_input\" name=\"newPassword\" type=\"password\" ng-model=\"passWordResetDetailDTO.newPassword\" data-ng-minlength=\"6\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.newPassword.$dirty && resetForm.newPassword.$invalid]\" style=\"line-height:34px\"> 请设置密码，8位及以上包含数字和字母。 </div> </div> </div> <div class=\"P_L_T_input\" style=\"height:2px\" ng-show=\"pwmobilenumbermsg\"></div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>确认密码：</div> <div class=\"P_L_I_Cen03\"> <input class=\"P_L_I_input\" ng-model=\"confirmPassword\" name=\"confirmpassword\" type=\"password\" girder-valid-password=\"passWordResetDetailDTO.newPassword\" required> </div> <div class=\"P_L_I_Right\"> <div id=\"confirmPassword\" class=\"P_L_zc_ts\" ng-class=\"{true: 'P_L_zc_tr'}[resetForm.confirmpassword.$dirty&&(resetForm.confirmpassword.$error._required||resetForm.confirmpassword.$error.notEqual)]\"> 请再次输入新密码，进行密码确认；请确保两次输入的密码一致。 </div> </div> </div> <div class=\"L_T_input\" style=\"height:15px\"></div> <div class=\"P_L_t_text_color2 hand\" data-ng-click=\"resetLoginPassword()\">重&nbsp;&nbsp;置&nbsp;&nbsp;密&nbsp;&nbsp;码</div> </div> </form> </div> </div> "
  );


  $templateCache.put('modules/expert/views/expertpassword_view.html',
    "<div class=\"P_L_top\" style=\"background:none\"> </div> <div class=\"P_L_twofg\"> <div class=\"P_R_pic\">恭喜您！密码修改成功。</div> <div class=\"P_L_T_input\" style=\"margin:0 auto\"> <div class=\"P_L_I_Cen\" style=\"border:0;  width:50%\"> <div class=\"P_L_ceb_R01 hand\" style=\"float:right\"><a href=\"#/expertlogin\" class=\"P_L_ceb_R01 hand\">登 录</a> </div> </div> <div class=\"P_L_I_Right\">返回登录页面</div><br><br><br> </div> </div>"
  );


  $templateCache.put('modules/expert/views/expertregister.html',
    "<div style=\"background:#f6f6f6\"> <div class=\"P_L_cen_right\" style=\"margin-top:-60px;margin-right:180px\"> <div class=\"P_L_ceb_R01 hand\"><a href=\"#/expertlogin\" class=\"P_L_ceb_R01 hand\">登 录</a></div> <div class=\"P_L_ceb_R02 hand\"><a href=\"#/expertPassword\" class=\"P_L_ceb_R02 hand\">重置密码</a></div> </div> <div class=\"P_L_twobg\"> <form name=\"registerForm\" id=\"registerForm\"> <div class=\"P_L_register\" style=\"height:auto\"> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>姓名：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" autofocus class=\"L_I_input\" id=\"name\" ng-model=\"tjPersonUserDTO.name\" name=\"name\" type=\"text\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.name.$dirty && registerForm.name.$invalid]\" style=\"line-height:34px\">请填写激活专家姓名。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>证件号码：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" id=\"idNumber\" data-ng-pattern=\"/(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/\" ng-model=\"tjPersonUserDTO.idNumber\" ng-blur=\"checkCardNum();\" name=\"idNumber\" type=\"text\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.idNumber.$dirty && registerForm.idNumber.$invalid]\" style=\"line-height:34px\">请输入18位有效证件号码。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>专家编号：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" autofocus class=\"L_I_input\" id=\"personNumber\" ng-model=\"tjPersonUserDTO.personNumber\" name=\"personNumber\" type=\"text\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.name.$dirty && registerForm.name.$invalid]\" style=\"line-height:34px\">请填写激活专家编号。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>图形验证码：</div> <div class=\"P_L_I_Cen03\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{captcha.captchaId}}\"> <input type=\"text\" style=\"width: 220px\" id=\"captchaWord\" name=\"captchaWord\" class=\"P_L_t_input\" data-ng-model=\"captcha.captchaWord\" required placeholder=\"点击图片更换验证码\" style=\"width: 90px\"> </div> <div class=\"L_t_Code\"> <span> <img style=\"margin-left:70%\" id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"80\" height=\"30\" alt=\"验证码\"></span> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>手机验证码：</div> <div class=\"P_L_I_Cen\"> <input class=\"P_L_I_input2\" name=\"validateMobileNumber\" data-ng-model=\"MobileInfo.captcha\" required type=\"text\"> <div class=\"P_L_I_buttom\"> <input id=\"btnPSendCode\" value=\"发送验证码\" data-ng-click=\"passwordCtrlMessage()\" ng-disabled=\"registerForm.mobilenumber.$invalid\" class=\"P_L_I_Green hand\"> </div> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.validateMobileNumber.$dirty && registerForm.validateMobileNumber.$invalid]\" style=\"line-height:36px\">请输入您接收到的验证码。 </div> </div> <div class=\"P_L_T_input\" style=\"height:auto\"> <div class=\"P_L_I_left\" style=\"height:auto\"></div> <div class=\"P_L_I_Cen\" style=\"margin-left:200px;height:auto;width:500px\"> <span style=\"color:red; font-size:13px\" class=\"ng-binding\">{{pwmobilenumbermsg}}</span> </div> <div class=\"P_L_I_Right\" style=\"height:auto\"> </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>设置密码：</div> <div class=\"P_L_I_Cen03\"> <input class=\"P_L_I_input\" name=\"password\" id=\"registerInputPassword\" type=\"password\" ng-model=\"tjPersonUserDTO.password\" data-ng-minlength=\"6\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.password.$dirty && registerForm.password.$invalid]\" style=\"line-height:34px\"> 请设置密码，8位及以上包含数字和字母。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>重复密码：</div> <div class=\"P_L_I_Cen03\"> <input class=\"P_L_I_input\" name=\"confirmpassword\" girder-valid-password=\"tjPersonUserDTO.password\" ng-model=\"confirmPassword\" type=\"password\" required> </div> <div class=\"P_L_I_Right\"> <div id=\"confirmPassword\" class=\"P_L_zc_ts\" ng-class=\"{true: 'P_L_zc_tr'}[registerForm.confirmpassword.$dirty&&(registerForm.confirmpassword.$error._required||registerForm.confirmpassword.$error.notEqual)]\"> 请再次输入新密码，进行密码确认；请确保两次输入的密码一致。 </div> </div> </div> <div class=\"P_L_t_text_color2 hand\" style=\"margin: 20px auto\" data-ng-click=\"registeredUser()\">激活 </div> </div> </form> </div> </div> "
  );


  $templateCache.put('modules/expert/views/expertregister_view.html',
    "<div class=\"P_L_top\" style=\"background:none\"> </div> <div class=\"P_L_twofg\"> <div class=\"P_R_pic\">恭喜您！注册成功。</div> <div class=\"P_L_T_input\" style=\"margin:0 auto\"> <div class=\"P_L_I_Cen\" style=\"border:0;  width:50%\"> <div class=\"P_L_ceb_R01 hand\" style=\"float:right\"> <button onclick=\"window.location.href='#/expertlogin'\" class=\"P_L_ceb_R01 hand\">登 录</button> </div> </div> <div class=\"P_L_I_Right\">返回登录页面</div> <br><br><br> </div> </div> "
  );


  $templateCache.put('modules/login/login.html',
    " "
  );


  $templateCache.put('modules/person/views/agreement.html',
    "<div style=\"background:#f6f6f6\"> <div class=\"P_L_top\"> <div class=\"P_L_center\"> <div class=\"P_L_cen_left\"></div> <div class=\"P_L_cen_right\"> <div class=\"P_L_ceb_R01 hand\"><a href=\"#/personLogin\" class=\"P_L_ceb_R01 hand\">登 录</a></div> <div class=\"P_L_ceb_R02 hand\"><a href=\"#/personAgree\" class=\"P_L_ceb_R02 hand\">注 册</a></div> </div> </div> </div> <div style=\"margin: 40px 20% 20px 20%; padding: 20px;background-color: #fffffe; border-radius: 4px; border: 1px solid #666666;font-family:楷体;height: 800px;overflow-x: hidden;overflow-y: visible\"> <h1 style=\"text-align: center\">天津市社会保险个人网厅网站用户注册协议</h1> ---------------------------------------------------------------------------------------------------------<br> <span style=\"font-size:20px;text-indent:2em\"> <strong>第一条 天津市社会保险基金管理中心个人网厅网站服务条款的确认和接纳</strong><br> &emsp;&emsp;天津市社会保险基金管理中心个人网厅网站的各项电子服务的所有权和运作权归天津市社会保险基金管理中心。天津市社会保险基金管理中心个人网厅网站提供的服务将完全按照其发布的经办规定、服务条款和操作规则严格执行。用户必须完全同意所有服务条款并完成注册程序，才能成为天津市社会保险基金管理中心个人网厅网站的正式用户。<br> <strong>第二条 服务简介</strong><br> &emsp;&emsp;天津市社会保险基金管理中心个人网厅网站运用自己的操作系统通过国际互联网络为用户提供网络服务。<br> 用户必须：<br> (1)自行配备上网的所需设备，包括个人电脑、调制解调器或其他必备上网装置。<br> (2)自行负担个人上网所支付的与此服务有关的电话费用、网络费用。<br> 基于天津市社会保险基金管理中心个人网厅网站所提供的网络服务的重要性，用户应同意：<br> (1)提供详尽、准确的个人资料。<br> (2)不断更新注册资料，符合及时、详尽、准确的要求。<br> &emsp;&emsp;天津市社会保险基金管理中心个人网厅网站不公开用户的姓名、身份证号码、社保卡卡号和手机号码，除以下情况外：<br> &emsp;&emsp;相应的法律法规要求天津市社会保险基金管理中心个人网厅网站提供用户的个人资料。如果用户提供的资料包含有不正确的信息，天津市社会保险基金管理中心个人网厅网站保留结束用户使用网络服务资格的权利。<br> <strong>第三条 服务条款的修改和服务修订</strong><br> &emsp;&emsp;天津市社会保险基金管理中心个人网厅网站有权在必要时修改服务条款，天津市社会保险基金管理中心个人网厅网站服务条款一旦发生变动，将会在重要页面上提示修改内容。如果不同意所改动的内容，用户可以主动取消获得的网络服务。如果用户继续享用网络服务，则视为接受服务条款的变动。天津市社会保险基金管理中心个人网厅网站保留随时修改或中断服务而不需知会用户的权利。天津市社会保险基金管理中心个人网厅网站行使修改或中断服务的权利，不需对用户或第三方负责。<br> <strong>第四条 用户隐私制度</strong><br> &emsp;&emsp;尊重用户个人隐私是天津市社会保险基金管理中心个人网厅网站的一项基本政策。所以，作为对上述个人注册资料分析的补充，天津市社会保险基金管理中心个人网厅网站不会在未经合法用户授权时公开、编辑或透露其注册资料及保存在天津市社会保险基金管理中心个人网厅网站中的非公开内容，除非有法律许可要求或天津市社会保险基金管理中心个人网厅网站在诚信的基础上认为透露这些信息在以下三种情况是必要的：<br> (1)遵守有关法律规定，遵从天津市社会保险基金管理中心个人网厅网站合法服务程序。<br> (2)在紧急情况下竭力维护用户个人和社会大众的隐私安全。<br> (3)符合其他相关的要求。<br> <strong>第五条 用户的帐号，密码和安全性</strong><br> &emsp;&emsp;用户一旦按照本网站的规定方式注册成功，成为天津市社会保险基金管理中心个人网厅网站的合法用户，将得到一个用户名和密码。<br> &emsp;&emsp;用户将对用户名和密码安全负全部责任。另外，每个用户都要对以其用户名进行的所有活动和事件负全责。用户若发现任何非法使用用户帐号或存在安全漏洞的情况，请立即通告天津市社会保险基金管理中心个人网厅网站。<br> <strong>第六条 拒绝提供担保</strong><br> &emsp;&emsp;用户个人对网络服务的使用承担风险。天津市社会保险基金管理中心个人网厅网站对此不作任何类型的担保，不论是明确的或隐含的，但是不对商业性的隐含担保、特定目的和不违反规定的适当担保作限制。天津市社会保险基金管理中心个人网厅网站不担保服务一定能满足用户的要求，也不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保。<br> <strong>第七条 有限责任</strong><br> &emsp;&emsp;天津市社会保险基金管理中心个人网厅网站对任何直接、间接、偶然、特殊及继起的损害不负责任，这些损害可能来自：不正当使用网络服务，在网上购买商品或进行同类型服务，在网上进行交易，非法使用网络服务或用户传送的信息有所变动。这些行为都有可能会导致天津市社会保险基金管理中心个人网厅网站的形象受损，所以天津市社会保险基金管理中心个人网厅网站事先提出这种损害的可能性。<br> <strong>第八条 对用户信息的存储和限制</strong><br> &emsp;&emsp;天津市社会保险基金管理中心个人网厅网站不对用户所发布信息的删除或储存失败负责。天津市社会保险基金管理中心个人网厅网站有判定用户的行为是否符合天津市社会保险基金管理中心个人网厅网站服务条款的要求和精神的保留权利，如果用户违背了服务条款的规定，天津市社会保险基金管理中心个人网厅网站有中断对其提供网络服务的权利。<br> <strong>第九条 用户管理</strong><br> &emsp;&emsp;用户单独承担发布内容的责任。用户对服务的使用根据所有适用于天津市社会保险基金管理中心个人网厅网站的国家法律、地方法律和国际法律标准。用户必须遵循：<br> (1) 从中国境内向外传输技术性资料时必须符合中国有关法规。<br> (2)使用网络服务不作非法用途。<br> (3)不干扰或混乱网络服务。<br> (4)遵守所有使用网络服务的网络协议、规定、程序和惯例。<br> &emsp;&emsp;用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的，淫秽等信息资料。另外，用户也不能传输任何教唆他人构成犯罪行为的资料；不能传输助长国内不利条件和涉及国家安全的资料；不能传输任何不符合当地法规、国家法律和国际法律的资料。未经许可而非法进入其它电脑系统是禁止的。若用户的行为不符合以上提到的服务条款，天津市社会保险基金管理中心个人网厅网站将作出独立判断立即终止为用户提供所有服务。用户需对自己在网上的行为承担法律责任。<br> &emsp;&emsp;用户若在天津市社会保险基金管理中心个人网厅网站上散布和传播反动、色情或其他违反国家法律的信息，天津市社会保险基金管理中心个人网厅网站的系统记录有可能作为用户违反法律的证据。<br> <strong>第十条 保障</strong><br> &emsp;&emsp;用户同意保障和维护天津市社会保险基金管理中心个人网厅网站全体成员的利益，负责支付由用户使用超出服务范围引起的律师费用，违反服务条款的损害补偿费用等。<br> <strong>第十一条 结束服务</strong><br> &emsp;&emsp;用户或天津市社会保险基金管理中心个人网厅网站可随时根据实际情况中断一项或多项网络服务。天津市社会保险基金管理中心个人网厅网站不需对任何个人或第三方负责而随时中断服务。用户对后来的条款修改有异议，或对天津市社会保险基金管理中心个人网厅网站的服务不满，可以行使如下权利：<br> (1)停止使用天津市社会保险基金管理中心个人网厅网站的网络服务。<br> (2)通告天津市社会保险基金管理中心个人网厅网站停止对该用户的服务。<br> 结束用户服务后，用户使用网络服务的权利马上中止。从那时起，用户没有权利，天津市社会保险基金管理中心个人网厅网站也没有义务传送任何未处理的信息或未完成的服务给用户或第三方。<br> <strong>第十二条 通告</strong><br> &emsp;&emsp;所有发给用户的通告都可通过重要页面的公告或电子邮件或常规的信件传送。服务条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。<br> <strong>第十三条 网络服务内容的所有权</strong><br> &emsp;&emsp;天津市社会保险基金管理中心个人网厅网站定义的网络服务内容包括：文字、软件、图片、图表中的全部内容；电子邮件或短信的全部内容；天津市社会保险基金管理中心个人网厅网站为用户提供的其他信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能在天津市社会保险基金管理中心个人网厅网站授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。天津市社会保险基金管理中心个人网厅网站所有的内容版权归天津市社会保险基金管理中心个人网厅网站所有，任何人需要转载必须征得天津市社会保险基金管理中心个人网厅网站授权。<br> <strong>第十四条 法律</strong><br> &emsp;&emsp;网络服务条款要与中华人民共和国的法律解释相一致，用户和天津市社会保险基金管理中心个人网厅网站一致同意服从法院所有管辖。如发生天津市社会保险基金管理中心个人网厅网站服务条款与中华人民共和国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它条款则依旧保持对用户产生法律效力和影响。 <p></p> </span> </div> <div style=\"font-size: 25px;font-family: 楷体;text-align: center\"> <p style=\"font-size: large; color: red\"><input type=\"checkbox\" name=\"checkAgree\" data-ng-model=\"checkBox\">本人同意上述内容，接受社会保险网上经办服务并承担相应的风险与责任。</p> <br> <div class=\"P_L_t_text_color hand\"> <button class=\"P_L_t_text_color hand\" data-ng-click=\"goToRegiste()\">前 往 注 册</button> </div> </div> <br><br><br> </div>"
  );


  $templateCache.put('modules/person/views/login.html',
    "<div class=\"L_bag\"> <div class=\"L_three\"> <div class=\"L_t_right\"> <form ng-submit=\"loginSystem(loginForm)\" name=\"loginForm\" id=\"loginForm\"> <div class=\"L_o_text\">账号登录（个人服务）</div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"username\" id=\"username\" autofocus data-ng-model=\"personcredentials.idNum\" required type=\"text\" placeholder=\"证件号码\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"personcredentials.password\" data-ng-minlength=\"6\" required placeholder=\"请输入密码\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{personcredentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"L_t_input\" data-ng-model=\"personcredentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\"> </div> <div class=\"L_t_Code\"> <img id=\"captchaImage\" ng-src=\"{{personcaptchaUrl}}\" data-ng-click=\"persongetCaptcha()\" width=\"84\" height=\"40\" alt=\"验证码\"> </div> </div> <button type=\"submit\" class=\"L_t_text_color hand\">登 录 系 统</button> <div class=\"other_login\"> <div class=\"other_login_title\"> <span class=\"line\"></span> <span class=\"txt\">使用其他账号登录</span> <span class=\"line\"></span> </div> <a href=\"http://175.174.62.1:8081/uaa/idstools/getGssionid/person\" class=\"link_btn2\" target=\"_self\"></a> </div> </form> </div> </div> </div> <div class=\"L_twofg\" style=\"border-bottom:#d4d4d4 1px solid;height:10px\"></div>"
  );


  $templateCache.put('modules/person/views/password.html',
    "<div style=\"background:#f6f6f6\"> <div class=\"P_L_cen_right\" style=\"margin-top:-60px;margin-right:180px\"> <div class=\"P_L_ceb_R01 hand\"><a href=\"#/personlogin\" class=\"P_L_ceb_R01 hand\">登 录</a></div> <div class=\"P_L_ceb_R02 hand\"><a href=\"#/personRegister\" class=\"P_L_ceb_R02 hand\">注 册</a></div> </div> <div class=\"P_L_twobg\"> <form name=\"resetForm\" id=\"resetForm\"> <div class=\"P_L_register\" style=\"height:480px\"> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>姓名：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" autofocus class=\"L_I_input\" id=\"name\" ng-model=\"passWordResetDetailDTO.name\" name=\"name\" type=\"text\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.name.$dirty && resetForm.name.$invalid]\" style=\"line-height:34px\">请输入您的姓名。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>证件号码：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" id=\"idNumber\" ng-model=\"passWordResetDetailDTO.idNumber\" data-ng-pattern=\"/(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/\" name=\"idNumber\" required type=\"text\"> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.idNumber.$dirty && resetForm.idNumber.$invalid]\" style=\"line-height:34px\">请输入18位有效证件号码。 </div> </div> </div> <div class=\"P_L_T_input\" ng-repeat=\"ql in questionList\"> <div class=\"P_L_I_left\"><span>*</span>问题{{$index+1}}：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_label\" ng-model=\"ql.answer\" placeholder=\"{{ql.content}}\"> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>图形验证码：</div> <div class=\"P_L_I_Cen03\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{captcha.captchaId}}\"> <input type=\"text\" style=\"width: 220px\" id=\"captchaWord\" name=\"captchaWord\" class=\"P_L_t_input\" data-ng-model=\"captcha.captchaWord\" required placeholder=\"点击图片更换验证码\" style=\"width: 90px\"> </div> <div class=\"L_t_Code\"> <span> <img style=\"margin-left:70%\" id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"80\" height=\"30\" alt=\"验证码\"></span> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>手机验证码：</div> <div class=\"P_L_I_Cen\"> <input class=\"P_L_I_input2\" name=\"validateMobileNumber\" ng-model=\"passWordResetDetailDTO.captcha\" required type=\"text\"> <div class=\"P_L_I_buttom\"> <div class=\"P_L_I_Green hand\"> <input id=\"btnPSendCode\" class=\"P_L_I_Green hand\" value=\"发送验证码\" data-ng-click=\"passwordCtrlMessage()\" ng-disabled=\"resetForm.mobilenumber.$invalid\"> </div> </div> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.validateMobileNumber.$dirty && resetForm.validateMobileNumber.$invalid]\" style=\"line-height:34px\">请输入您接收到的验证码。 </div> </div> </div> <div class=\"P_L_T_input\" style=\"height:auto\"> <div class=\"P_L_I_left\" style=\"height:auto\"></div> <div class=\"P_L_I_Cen\" style=\"margin-left:200px;height:auto;width:500px\"> <span style=\"color:red; font-size:13px\" class=\"ng-binding\">{{pwmobilenumbermsg}}</span> </div> <div class=\"P_L_I_Right\" style=\"height:auto\"> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>设置密码：</div> <div class=\"P_L_I_Cen03\"> <input class=\"P_L_I_input\" name=\"newPassword\" type=\"password\" ng-model=\"passWordResetDetailDTO.newPassword\" data-ng-minlength=\"6\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[resetForm.newPassword.$dirty && resetForm.newPassword.$invalid]\" style=\"line-height:34px\"> 请设置密码，8位及以上包含数字和字母。 </div> </div> </div> <div class=\"P_L_T_input\" style=\"height:2px\" ng-show=\"pwmobilenumbermsg\"></div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>确认密码：</div> <div class=\"P_L_I_Cen03\"> <input class=\"P_L_I_input\" ng-model=\"confirmPassword\" name=\"confirmpassword\" type=\"password\" girder-valid-password=\"passWordResetDetailDTO.newPassword\" required> </div> <div class=\"P_L_I_Right\"> <div id=\"confirmPassword\" class=\"P_L_zc_ts\" ng-class=\"{true: 'P_L_zc_tr'}[resetForm.confirmpassword.$dirty&&(resetForm.confirmpassword.$error._required||resetForm.confirmpassword.$error.notEqual)]\"> 请再次输入新密码，进行密码确认；请确保两次输入的密码一致。 </div> </div> </div> <div class=\"L_T_input\" style=\"height:15px\"></div> <div class=\"P_L_t_text_color2 hand\" data-ng-click=\"resetLoginPassword()\">重&nbsp;&nbsp;置&nbsp;&nbsp;密&nbsp;&nbsp;码</div> </div> </form> </div> </div> "
  );


  $templateCache.put('modules/person/views/password_view.html',
    "<div class=\"P_L_top\" style=\"background:none\"> </div> <div class=\"P_L_twofg\"> <div class=\"P_R_pic\">恭喜您！密码修改成功。</div> <div class=\"P_L_T_input\" style=\"margin:0 auto\"> <div class=\"P_L_I_Cen\" style=\"border:0;  width:50%\"> <div class=\"P_L_ceb_R01 hand\" style=\"float:right\"><a href=\"#/personlogin\" class=\"P_L_ceb_R01 hand\">登 录</a> </div> </div> <div class=\"P_L_I_Right\">返回登录页面</div><br><br><br> </div> </div>"
  );


  $templateCache.put('modules/person/views/register.html',
    "<div style=\"background:#f6f6f6\"> <div class=\"P_L_cen_right\" style=\"margin-top:-60px;margin-right:180px\"> <div class=\"P_L_ceb_R01 hand\"><a href=\"#/personlogin\" class=\"P_L_ceb_R01 hand\">登 录</a></div> <div class=\"P_L_ceb_R02 hand\"><a href=\"#/personPassword\" class=\"P_L_ceb_R02 hand\">重置密码</a></div> </div> <div class=\"P_L_twobg\"> <form name=\"registerForm\" id=\"registerForm\"> <div class=\"P_L_register\" style=\"height:auto\"> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>注册人姓名：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" autofocus class=\"L_I_input\" id=\"name\" ng-model=\"tjPersonUserDTO.name\" name=\"name\" type=\"text\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.name.$dirty && registerForm.name.$invalid]\" style=\"line-height:34px\">请填写注册人姓名。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>证件号码：</div> <div class=\"P_L_I_Cen01\"> <input class=\"P_L_I_input\" id=\"idNumber\" data-ng-pattern=\"/(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/\" ng-model=\"tjPersonUserDTO.idNumber\" ng-blur=\"checkCardNum();\" name=\"idNumber\" type=\"text\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.idNumber.$dirty && registerForm.idNumber.$invalid]\" style=\"line-height:34px\">请输入18位有效证件号码。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>图形验证码：</div> <div class=\"P_L_I_Cen03\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{captcha.captchaId}}\"> <input type=\"text\" style=\"width: 220px\" id=\"captchaWord\" name=\"captchaWord\" class=\"P_L_t_input\" data-ng-model=\"captcha.captchaWord\" required placeholder=\"点击图片更换验证码\" style=\"width: 90px\"> </div> <div class=\"L_t_Code\"> <span> <img style=\"margin-left:70%\" id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"80\" height=\"30\" alt=\"验证码\"></span> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>手机号码：</div> <div class=\"P_L_I_Cen02\"> <input class=\"P_L_I_input\" name=\"mobilenumber\" id=\"MobileNumber\" onkeyup=\"value=value.replace(/[^\\d]/g,'')\" data-ng-model=\"MobileInfo.mobilenumber\" required data-ng-maxlength=\"11\" data-ng-minlength=\"11\" type=\"text\"> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.mobilenumber.$dirty &&registerForm.mobilenumber.$invalid]\" style=\"line-height:36px\">手机号码为必输项且应为11位数字。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>手机验证码：</div> <div class=\"P_L_I_Cen\"> <input class=\"P_L_I_input2\" name=\"validateMobileNumber\" data-ng-model=\"MobileInfo.captcha\" required type=\"text\"> <div class=\"P_L_I_buttom\"> <input id=\"btnPSendCode\" value=\"发送验证码\" data-ng-click=\"passwordCtrlMessage()\" ng-disabled=\"registerForm.mobilenumber.$invalid\" class=\"P_L_I_Green hand\"> </div> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.validateMobileNumber.$dirty && registerForm.validateMobileNumber.$invalid]\" style=\"line-height:36px\">请输入您接收到的验证码。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>设置密码：</div> <div class=\"P_L_I_Cen03\"> <input class=\"P_L_I_input\" name=\"password\" id=\"registerInputPassword\" type=\"password\" ng-model=\"tjPersonUserDTO.password\" data-ng-minlength=\"6\" required> </div> <div class=\"P_L_I_Right\"> <div ng-class=\"{true: 'P_L_zc_tr', false: 'P_L_zc_ts'}[registerForm.password.$dirty && registerForm.password.$invalid]\" style=\"line-height:34px\"> 请设置密码，8位及以上包含数字和字母。 </div> </div> </div> <div class=\"P_L_T_input\"> <div class=\"P_L_I_left\"><span>*</span>重复密码：</div> <div class=\"P_L_I_Cen03\"> <input class=\"P_L_I_input\" name=\"confirmpassword\" girder-valid-password=\"tjPersonUserDTO.password\" ng-model=\"confirmPassword\" type=\"password\" required> </div> <div class=\"P_L_I_Right\"> <div id=\"confirmPassword\" class=\"P_L_zc_ts\" ng-class=\"{true: 'P_L_zc_tr'}[registerForm.confirmpassword.$dirty&&(registerForm.confirmpassword.$error._required||registerForm.confirmpassword.$error.notEqual)]\"> 请再次输入新密码，进行密码确认；请确保两次输入的密码一致。 </div> </div> </div> <div class=\"P_L_t_text_color2 hand\" style=\"margin: 20px auto\" data-ng-click=\"registeredUser()\">注册 </div> </div> </form> </div> </div> "
  );


  $templateCache.put('modules/person/views/register_view.html',
    "<div class=\"P_L_top\" style=\"background:none\"> </div> <div class=\"P_L_twofg\"> <div class=\"P_R_pic\">恭喜您！注册成功。</div> <div class=\"P_L_T_input\" style=\"margin:0 auto\"> <div class=\"P_L_I_Cen\" style=\"border:0;  width:50%\"> <div class=\"P_L_ceb_R01 hand\" style=\"float:right\"> <button onclick=\"window.location.href='#/personlogin'\" class=\"P_L_ceb_R01 hand\">登 录</button> </div> </div> <div class=\"P_L_I_Right\">返回登录页面</div> <br><br><br> </div> </div> "
  );


  $templateCache.put('modules/resident/views/login.html',
    " "
  );


  $templateCache.put('modules/school/views/login.html',
    "<div class=\"L_bag\"> <div class=\"L_three_siagent\"> <div class=\"L_t_left\"></div> <div class=\"L_t_right\"> <form name=\"loginForm\" id=\"loginForm\" ng-submit=\"loginSystem(loginForm)\"> <div class=\"L_o_text\">欢迎登录</div> <div class=\"fg\" style=\"height:20px\"></div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">用户名：</div> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"username\" id=\"username\" autofocus data-ng-model=\"credentials.username\" required type=\"text\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">密 码：</div> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"credentials.password\" required> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">验证码：</div> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{credentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"L_t_input\" data-ng-model=\"credentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\" style=\"width:90px\"> </div> <div class=\"L_t_Code\"> <span> <img id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"80\" height=\"35\" alt=\"验证码\"></span> </div> </div> <div class=\"fg\" style=\"height:20px\"></div> <button type=\"submit\" class=\"L_t_text_color hand\" style=\"margin-left: 15%\">登 录 系 统</button> </form> </div> </div> </div> <div class=\"L_twofg\" style=\"border-bottom:#d4d4d4 1px solid;height:10px\"></div> <div class=\"P_L_twofg\"> <div class=\"P_L_twoleft\" style=\"width:auto\"> 建议使用Google Chrome 浏览器（Chrome 49.0及以上版本）、Internet Explorer 浏览器（IE 11稳定版本）、搜狗浏览器（急速模式）、360浏览器（极速模式）访问系统。 <span style=\"color:red\">Chrome 49.0及以上版本 <a style=\"bold\" href=\"https://www.google.cn/intl/zh-CN/chrome/\">点击下载；</a> <span style=\"color:red\">IE11浏览器 <a style=\"bold\" href=\"https://www.microsoft.com/zh-cn/windows/\">点击下载；</a> <span style=\"color:red\">搜狗浏览器 <a style=\"bold\" href=\"https://ie.sogou.com//\">点击下载；</a> <span style=\"color:red\">360浏览器 <a style=\"bold\" href=\"https://browser.360.cn/ee/\">点击下载；</a> <div>版权所有：南宁市人力资源和社会保障局</div> </span></span></span></span></div> </div>"
  );


  $templateCache.put('modules/siagent/views/active.html',
    " "
  );


  $templateCache.put('modules/siagent/views/login.html',
    " "
  );


  $templateCache.put('modules/siagent/views/password.html',
    " "
  );


  $templateCache.put('modules/support/template/401.html',
    "<!DOCTYPE html> <html lang=\"en\"> <head> <meta charset=\"utf-8\"> <title>Not Authorized</title> <style> ::-moz-selection {\r" +
    "\n" +
    "            background: #b3d4fc;\r" +
    "\n" +
    "            text-shadow: none;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        ::selection {\r" +
    "\n" +
    "            background: #b3d4fc;\r" +
    "\n" +
    "            text-shadow: none;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        html {\r" +
    "\n" +
    "            padding: 30px 10px;\r" +
    "\n" +
    "            font-size: 20px;\r" +
    "\n" +
    "            line-height: 1.4;\r" +
    "\n" +
    "            color: #737373;\r" +
    "\n" +
    "            background: #f0f0f0;\r" +
    "\n" +
    "            font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;\r" +
    "\n" +
    "            -webkit-text-size-adjust: 100%;\r" +
    "\n" +
    "            -ms-text-size-adjust: 100%;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        body {\r" +
    "\n" +
    "            max-width: 550px;\r" +
    "\n" +
    "            _width: 550px;\r" +
    "\n" +
    "            padding: 30px 20px 50px;\r" +
    "\n" +
    "            border: 1px solid #b3b3b3;\r" +
    "\n" +
    "            border-radius: 4px;\r" +
    "\n" +
    "            margin: 0 auto;\r" +
    "\n" +
    "            box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;\r" +
    "\n" +
    "            background: #fcfcfc;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h1 {\r" +
    "\n" +
    "            margin: 0 10px;\r" +
    "\n" +
    "            font-size: 50px;\r" +
    "\n" +
    "            text-align: center;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h1 span {\r" +
    "\n" +
    "            color: #bbb;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h3 {\r" +
    "\n" +
    "            margin: 1.5em 0 0.5em;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        p {\r" +
    "\n" +
    "            margin: 1em 0;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        ul {\r" +
    "\n" +
    "            padding: 0 0 0 40px;\r" +
    "\n" +
    "            margin: 1em 0;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        .container {\r" +
    "\n" +
    "            max-width: 500px;\r" +
    "\n" +
    "            _width: 500px;\r" +
    "\n" +
    "            margin: 0 auto;\r" +
    "\n" +
    "        } </style> </head> <body> <div class=\"container\"> <h1>没有授权</h1> <p>对不起，您没有被授予权限查看当前页面.</p> </div> </body> </html> "
  );


  $templateCache.put('modules/support/template/404.html',
    "<!DOCTYPE html> <html lang=\"en\"> <head> <meta charset=\"utf-8\"> <title>Page Not Found</title> <style> ::-moz-selection {\r" +
    "\n" +
    "            background: #b3d4fc;\r" +
    "\n" +
    "            text-shadow: none;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        ::selection {\r" +
    "\n" +
    "            background: #b3d4fc;\r" +
    "\n" +
    "            text-shadow: none;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        html {\r" +
    "\n" +
    "            padding: 30px 10px;\r" +
    "\n" +
    "            font-size: 20px;\r" +
    "\n" +
    "            line-height: 1.4;\r" +
    "\n" +
    "            color: #737373;\r" +
    "\n" +
    "            background: #f0f0f0;\r" +
    "\n" +
    "            font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;\r" +
    "\n" +
    "            -webkit-text-size-adjust: 100%;\r" +
    "\n" +
    "            -ms-text-size-adjust: 100%;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        body {\r" +
    "\n" +
    "            max-width: 550px;\r" +
    "\n" +
    "            _width: 550px;\r" +
    "\n" +
    "            padding: 30px 20px 50px;\r" +
    "\n" +
    "            border: 1px solid #b3b3b3;\r" +
    "\n" +
    "            border-radius: 4px;\r" +
    "\n" +
    "            margin: 0 auto;\r" +
    "\n" +
    "            box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;\r" +
    "\n" +
    "            background: #fcfcfc;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h1 {\r" +
    "\n" +
    "            margin: 0 10px;\r" +
    "\n" +
    "            font-size: 50px;\r" +
    "\n" +
    "            text-align: center;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h1 span {\r" +
    "\n" +
    "            color: #bbb;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h3 {\r" +
    "\n" +
    "            margin: 1.5em 0 0.5em;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        p {\r" +
    "\n" +
    "            margin: 1em 0;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        ul {\r" +
    "\n" +
    "            padding: 0 0 0 40px;\r" +
    "\n" +
    "            margin: 1em 0;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        .container {\r" +
    "\n" +
    "            max-width: 500px;\r" +
    "\n" +
    "            _width: 500px;\r" +
    "\n" +
    "            margin: 0 auto;\r" +
    "\n" +
    "        } </style> </head> <body> <div class=\"container\"> <h1>资源没有找到 <span>:(</span></h1> <p>对不起，您尝试查阅的内容不存在.</p> </div> </body> </html> "
  );


  $templateCache.put('modules/support/template/500.html',
    "<!DOCTYPE html> <html lang=\"en\"> <head> <meta charset=\"utf-8\"> <title>Server Error</title> <style> ::-moz-selection {\r" +
    "\n" +
    "            background: #b3d4fc;\r" +
    "\n" +
    "            text-shadow: none;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        ::selection {\r" +
    "\n" +
    "            background: #b3d4fc;\r" +
    "\n" +
    "            text-shadow: none;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        html {\r" +
    "\n" +
    "            padding: 30px 10px;\r" +
    "\n" +
    "            font-size: 20px;\r" +
    "\n" +
    "            line-height: 1.4;\r" +
    "\n" +
    "            color: #737373;\r" +
    "\n" +
    "            background: #f0f0f0;\r" +
    "\n" +
    "            font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;\r" +
    "\n" +
    "            -webkit-text-size-adjust: 100%;\r" +
    "\n" +
    "            -ms-text-size-adjust: 100%;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        body {\r" +
    "\n" +
    "            max-width: 550px;\r" +
    "\n" +
    "            _width: 550px;\r" +
    "\n" +
    "            padding: 30px 20px 50px;\r" +
    "\n" +
    "            border: 1px solid #b3b3b3;\r" +
    "\n" +
    "            border-radius: 4px;\r" +
    "\n" +
    "            margin: 0 auto;\r" +
    "\n" +
    "            box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;\r" +
    "\n" +
    "            background: #fcfcfc;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h1 {\r" +
    "\n" +
    "            margin: 0 10px;\r" +
    "\n" +
    "            font-size: 50px;\r" +
    "\n" +
    "            text-align: center;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h1 span {\r" +
    "\n" +
    "            color: #bbb;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        h3 {\r" +
    "\n" +
    "            margin: 1.5em 0 0.5em;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        p {\r" +
    "\n" +
    "            margin: 1em 0;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        ul {\r" +
    "\n" +
    "            padding: 0 0 0 40px;\r" +
    "\n" +
    "            margin: 1em 0;\r" +
    "\n" +
    "        }\r" +
    "\n" +
    "\r" +
    "\n" +
    "        .container {\r" +
    "\n" +
    "            max-width: 500px;\r" +
    "\n" +
    "            _width: 500px;\r" +
    "\n" +
    "            margin: 0 auto;\r" +
    "\n" +
    "        } </style> </head> <body> <div class=\"container\"> <h1>服务端错误</h1> <p>对不起，服务端出现错误阻止当前页的显示.</p> </div> </body> </html> "
  );


  $templateCache.put('modules/support/template/messageview.html',
    "<div class=\"modal-header btn-primary\"> <h3>{{modalOptions.headerText}}</h3> </div> <div class=\"modal-body\"> <p>{{modalOptions.bodyText}}</p> </div> <div class=\"modal-footer\"> <button type=\"button\" class=\"btn\" data-ng-click=\"modalOptions.close()\" data-ng-show=\"modalOptions.closeButtonText\">{{modalOptions.closeButtonText}}</button> <button class=\"btn btn-success\" data-ng-click=\"modalOptions.ok();\">{{modalOptions.actionButtonText}}</button> </div>"
  );


  $templateCache.put('modules/uic/views/login.html',
    "<div class=\"L_bag\"> <div class=\"L_three\"> <div class=\"L_t_left\"></div> <div class=\"L_t_right\"> <form name=\"loginForm\" id=\"loginForm\" action=\"api/enterprise/usernamepassword/login\" method=\"post\"> <div class=\"L_o_text\">欢迎登录</div> <div class=\"fg\" style=\"height:20px\"></div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">用户名：</div> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"username\" id=\"username\" autofocus data-ng-model=\"credentials.username\" required type=\"text\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">密 码：</div> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"credentials.password\" data-ng-minlength=\"6\" required> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">验证码：</div> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{credentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"L_t_input\" data-ng-model=\"credentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\" style=\"width:90px\"> </div> <div class=\"L_t_Code\"> <span> <img id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"80\" height=\"35\" alt=\"验证码\"></span> </div> </div> <div class=\"fg\" style=\"height:20px\"></div> <button type=\"submit\" class=\"L_t_text_color hand\" style=\"margin-left: 15%\" ng-disabled=\"loginForm.$invalid\">登 录 系 统</button> </form> </div> </div> </div> <div class=\"L_twofg\" style=\"border-bottom:#d4d4d4 1px solid\"></div>"
  );

}]);
