/**
 * login controller
 * 用window.location加上#
 * 用$location不需要#
 * author:xiaowzh
 */
'use strict';
angular.module('authserver.expert')
    .controller('expertregisterCtrl', ['CaptchaService', 'XSRFTokenService', 'messageBox', '$rootScope', '$scope', '$location', '$log', 'IDCard', 'expertService', '$timeout',
        function (CaptchaService, XSRFTokenService, messageBox, $rootScope, $scope, $location, $log, IDCard, expertService, $timeout) {
            $scope.disableCanbaoSelectRadio = true;
            $scope.isCanbao = [0, 1];
            $scope.checkBox = null;
            $scope.confirmPassword = '';
            var INTERVALOBJ = null; //timer变量，控制时间
            var COUNT = 60; //间隔函数，1秒执行
            var CURCOUNT;//当前剩余秒数
            // select选中、未选中内容
            // var SELECTED_CONTEXT = '已被选中问题';
            // var UNSELECTED_CONTEXT = '请选择';
            window.clearInterval(INTERVALOBJ);
            $scope.messageBox = messageBox;
            //图形验证码
            $scope.captcha = {
                'captchaUrl': '',
                'captchaId': '',
                'captchaWord': ''
            };
            //用户注册表单
            //防止再次添加社保卡号验证使用的DTO
            $scope.tjPersonUserDTO = {
                'idNumber': '',
                'name': '',
                'password': ''
            };
            //前往注册的协议验证
            $scope.goToRegiste = function () {
                $log.info('注册协议的checkbox=' + $scope.checkBox);
                if (false === $scope.checkBox || null === $scope.checkBox) {
                    $scope.messageBox.showInfo('您尚未同意协议内容。');
                } else if (true === $scope.checkBox) {
                    $location.path('/personRegister');
                }
            };
            $scope.MobileInfo = {
                'mobilenumber': '',
                'captchaId': '',
                'captchaWord': ''
            };

            $scope.MobileDTO = {
                'title': '',
                'content': '',
                'url': '',
                'mobile': '',
                'email': '',
                'webacc': '',
                'name': ''
            };
            //校验身份证号码
            $scope.checkCardNum = function () {
                $scope.idNumber = $scope.tjPersonUserDTO.idNumber;
                if ($scope.idNumber === undefined || $scope.idNumber === '') {
                    return true;
                }
//                $log.info('注册表单的身份证号码', $scope.idNumber);
                //如果输入身份证号码最后一位为x,自动变成大写X
                if ($scope.idNumber.substring(17) === 'x') {
                    $scope.tjPersonUserDTO.idNumber = $scope.idNumber.substring(0, 17) + $scope.idNumber.substring(17).toLocaleUpperCase();
                }
                //校验身份证号码
                var IDCardValidateResult = IDCard.validateIDCard($scope.tjPersonUserDTO.idNumber);
                $scope.isValid = IDCardValidateResult.message;
                if (IDCardValidateResult.isValid === 'false') {
                    $scope.messageBox.showInfo(IDCardValidateResult.message);
                    return true;
                }
                return false;
            };
            //
            $scope.secretQuestion = {
                'birth': '',
                'fatherName': '',
                'motherName': ''
            };
            $scope.validatePhone = function () {
                var regExp = /^1[34578]\d{9}$/;
                $scope.mobilenumber = $scope.MobileInfo.mobilenumber;
                return regExp.test($scope.mobilenumber);
            };
            $scope.validatePassword = function () {
                var regExp = /^[a-zA-Z]{1}.*\d+.*$/;
                $scope.password = $scope.tjPersonUserDTO.password;
                return regExp.test($scope.password);
            };
            //注册用户
            $scope.registeredUser = function () {
                //验证姓名、证件号码、图形验证码是否正确
                if ($scope.validateCode()) {
                    return;
                }
                //验证输入密码是否正确
                if ($scope.validateRegister()) {
                    return;
                }
                $scope.MobileInfo.captchaId = $scope.captcha.captchaId;
                $scope.MobileInfo.captchaWord = $scope.captcha.captchaWord;
                console.info('---注册信息1---', $scope.tjPersonUserDTO);
                console.info('---注册信息2---', $scope.MobileInfo);
                expertService.registePerson($scope.tjPersonUserDTO, $scope.MobileInfo).then(function (success) {
                    $location.path('/expertRegister_View');
                }, function (err) {
                    $scope.getCaptcha();
                    $log.info('注册失败', err);
                    if (err.data.hasOwnProperty('detail')) {
                        $scope.messageBox.showInfo('注册失败！' + err.data.detail);
                    } else if (err.data.hasOwnProperty('message')) {
                        $scope.messageBox.showInfo('注册失败！' + err.data.message);
                    } else {
                        $scope.messageBox.showInfo('当前用户注册失败！');
                    }
                });
            };

            //手机验证码请求
            $scope.passwordCtrlMessage = function () {
                if ($scope.validateCode()) {
                    return false;
                } else {
                    expertService.mobileCodePostByPersonNumberxiaowzh($scope.tjPersonUserDTO.idNumber, $scope.tjPersonUserDTO.name, $scope.tjPersonUserDTO.personNumber, $scope.captcha).then(function (success) {
                        $scope.pwmobilenumbermsg = '发送到' + success.data.mobilenumber;
                        $log.info('验证码已经发送');
                        CURCOUNT = COUNT;
                        //设置button效果，开始计时
                        angular.element('#btnPSendCode').attr("disabled", "true");
                        angular.element('#btnPSendCode').val(CURCOUNT + '秒后重发');
                        INTERVALOBJ = window.setInterval(
                            function () {
                                if (CURCOUNT == 0) {
                                    window.clearInterval(INTERVALOBJ);//停止计时器
                                    angular.element('#btnPSendCode').removeAttr("disabled");//启用按钮
                                    angular.element('#btnPSendCode').val('重新发送');
                                    angular.element('#resetMobileNumber').removeAttr("disabled");
                                }
                                else {
                                    CURCOUNT--;
                                    angular.element('#btnPSendCode').val(CURCOUNT + '秒后重发');
                                    angular.element('#resetMobileNumber').attr('disabled', 'true');
                                }
                            }, 1000); //启动计时器，1秒执行一次

                    }, function (err) {
                        $scope.getCaptcha();
                        if (err.data.hasOwnProperty('detail')) {
                            $scope.messageBox.showInfo('手机验证码发送失败！' + err.data.detail);
                        } else if (err.data.hasOwnProperty('message')) {
                            $scope.messageBox.showInfo(err.data.message);
                        } else {
                            $scope.messageBox.showInfo('手机验证码发送失败！');
                        }
                    });
                }
            };

            $scope.getCaptcha = function () {
                CaptchaService.getNextCaptcha().$promise.then(function (data) {
                    $log.info('loginCtrl获取到验证码', data.id);
                    $scope.captchaUrl = data.getImageUrl() + "?" + Math.random();
                    $scope.captcha.captchaId = data.id;
                });
            };
            $scope.getCaptcha();
            //短信验证码
            $scope.sendRegisterMessage = function () {
                //添加字段元素验证
                if ($scope.validateCode()) {
                    return;
                } else {
                    $scope.MobileDTO.title = '个人注册';
                    $scope.MobileDTO.mobile = $scope.MobileInfo.mobilenumber;
                    $scope.MobileDTO.webacc = $scope.tjPersonUserDTO.idNumber;
                    $scope.MobileDTO.name = $scope.tjPersonUserDTO.name;

                    $scope.BatchMsgDto = {
                        msglst: [$scope.MobileDTO],
                        msgType: 'Sms',
                        userType: 'PERSON',
                        businessType: 'Register',
                        clientType: 'PC',
                        systemType: 'Person'
                    };

                    //向后台发送处理数据
                    expertService.CodePostMethod($scope.BatchMsgDto, $scope.captcha).then(function (rs) {
                        if (rs.data.code != 200) {
                            $scope.getCaptcha();
                            $scope.messageBox.showInfo('手机验证码发送失败！' + rs.data.errmsg);
                        } else {
                            CURCOUNT = COUNT;
                            //设置button效果，开始计时
                            angular.element('#btnRegisterSendCode').attr("disabled", "true");
                            angular.element('#btnRegisterSendCode').val(CURCOUNT + '秒后重发');
                            INTERVALOBJ = window.setInterval(
                                function () {
                                    if (CURCOUNT == 0) {
                                        window.clearInterval(INTERVALOBJ);//停止计时器
                                        angular.element('#btnRegisterSendCode').removeAttr("disabled");//启用按钮
                                        angular.element('#btnRegisterSendCode').val('重新发送');
                                        angular.element('#registerMobileNumber').removeAttr("disabled");
                                    }
                                    else {
                                        CURCOUNT--;
                                        angular.element('#btnRegisterSendCode').val(CURCOUNT + '秒后重发');
                                        angular.element('#registerMobileNumber').attr('disabled', 'true');
                                    }
                                }, 1000); //启动计时器，1秒执行一次
                        }
                    }, function (err) {
                        $scope.getCaptcha();
                        if (err.data.hasOwnProperty('detail')) {
                            $scope.messageBox.showInfo('手机验证码发送失败！' + err.data.detail);
                        } else if (err.data.hasOwnProperty('message')) {
                            $scope.messageBox.showInfo(err.data.message);
                        } else {
                            $scope.messageBox.showInfo('手机验证码发送失败！');
                        }
                    });
                }
            };

            //发送手机验证码之前进行验证
            $scope.validateCode = function () {
                if (undefined === $scope.tjPersonUserDTO.name || 1 > $scope.tjPersonUserDTO.name.length) {
                    $scope.messageBox.showInfo('请输入注册人姓名！');
                    return true;
                }
                //证件号码
                if ($scope.registerForm.idNumber.$invalid) {
                    if (null == $scope.registerForm.idNumber.$viewValue || '' == $scope.registerForm.idNumber.$viewValue) {
                        $scope.messageBox.showInfo('请输入证件号码！');
                    } else {
                        $scope.messageBox.showInfo('请输入正确位数的证件号码！');
                    }
                    return true;
                }
                if ($scope.checkCardNum()) {
                    return true;
                }
                if (undefined === $scope.captcha.captchaWord || '' === $scope.captcha.captchaWord) {
                    $scope.messageBox.showInfo('请输入图形验证码！');
                    return true;
                }
                return false;
            };
            //添加前往注册之前进行验证
            $scope.validateRegister = function () {
                if (undefined === $scope.tjPersonUserDTO.password || 6 > $scope.tjPersonUserDTO.password.length) {
                    if (null == $scope.registerForm.password.$viewValue || '' == $scope.registerForm.password.$viewValue) {
                        $scope.messageBox.showInfo('请输入至少6位的密码！');
                    } else {
                        $scope.messageBox.showInfo('密码不合法，请修改！');
                    }
                    return true;
                }
                //校验密码规则
//                if(!$scope.validatePassword()){
//                	$scope.messageBox.showInfo('密码不合法，请修改！');
//                	return true;
//                }

                //密码
//            	if($scope.registerForm.password.$invalid){
//            		if(null == $scope.registerForm.password.$viewValue || '' == $scope.registerForm.password.$viewValue)
//            			$scope.messageBox.showInfo('请输入密码！');
//            		else
//            			$scope.messageBox.showInfo('密码有误，请修改！');
//            		return;
//            	}
                //重复密码
                if ($scope.registerForm.confirmpassword.$error._required || $scope.registerForm.confirmpassword.$error.notEqual) {
                    if (null == $scope.registerForm.confirmpassword.$viewValue || '' == $scope.registerForm.confirmpassword.$viewValue) {
                        $scope.messageBox.showInfo('请输入重复密码！');
                    } else if ($scope.tjPersonUserDTO.password != $scope.registerForm.confirmpassword.$viewValue) {
                        $scope.messageBox.showInfo('设置密码与重复密码不匹配！');
                    } else {
                        $scope.messageBox.showInfo('重复密码有误，请修改！');
                    }
                    return true;
                }
                return false;
            }
        }]);