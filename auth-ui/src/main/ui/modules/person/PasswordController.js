/**
 * Created by y_zhang.neu on 2015/11/5.
 * 密码重置controller
 */
'use strict';
angular.module('authserver.person')
    .controller('passwordCtrl', ['CaptchaService', 'XSRFTokenService', '$rootScope', '$scope', '$location', '$log', 'IDCard', 'personService', 'messageBox', '$http',
        function (CaptchaService, XSRFTokenService, $rootScope, $scope, $location, $log, IDCard, personService, messageBox, $http) {
            $scope.confirmPassword = '';
            var INTERVALOBJ = null; //timer变量，控制时间
            var COUNT = 60; //间隔函数，1秒执行
            var CURCOUNT;//当前剩余秒数
            window.clearInterval(INTERVALOBJ);
            $scope.messageBox = messageBox;
            //图形验证码
            $scope.captcha = {
                'captchaUrl': '',
                'captchaId': '',
                'captchaWord': ''
            };
            //mobile info
            $scope.MobileInfo = {
                'captchaId': '',
                'captchaWord': ''
            };
            /**
             * 用户密码重置
             */
            //用户密码修改DTO
            $scope.passWordResetDetailDTO = {
                'newPassword': '',
                'idNumber': ''
            };
            // 初始化密保问题队列
            // $scope.questionList = [];

            // 当前是否正在通过身份证号获取问题列表
            // var isGetQuestion = false;

            //校验身份证号码
            $scope.checkCardNum = function () {
                $scope.idNumber = $scope.passWordResetDetailDTO.idNumber;
                if ($scope.idNumber === undefined || $scope.idNumber === '') {
                    return true;
                }
                //如果输入身份证号码最后一位为x,自动变成大写X
                if ($scope.idNumber.substring(17) === 'x') {
                    $scope.passWordResetDetailDTO.idNumber = $scope.idNumber.substring(0, 17) + $scope.idNumber.substring(17).toLocaleUpperCase();
                }
                //校验身份证号码
                var IDCardValidateResult = IDCard.validateIDCard($scope.passWordResetDetailDTO.idNumber);
                $scope.isValid = IDCardValidateResult.message;
                if (IDCardValidateResult.isValid === 'false') {
                    $scope.messageBox.showInfo(IDCardValidateResult.message);
                    return true;
                }
                return false;
            };
            //根据用户姓名证件号查询用户信息
            $scope.usernameChanged = function () {
                $scope.pwmobilenumbermsg = '';
                //向后台发送处理数据
                $log.info('获取手机号，用户名是', $scope.passWordResetDetailDTO.name);
                $log.info('获取手机号，证件号是', $scope.passWordResetDetailDTO.idNumber);
                if ($scope.passWordResetDetailDTO.name === null || $scope.passWordResetDetailDTO.name === undefined || $scope.passWordResetDetailDTO.name === '') {
                    //$scope.messageBox.showInfo('请输入用户名!');
                    $log.error('请输入姓名!');
                    return;
                }
                if ($scope.passWordResetDetailDTO.idNumber === null || $scope.passWordResetDetailDTO.idNumber === undefined || $scope.passWordResetDetailDTO.idNumber === '') {
                    //$scope.messageBox.showInfo('请输入用户名!');
                    $log.error('请输入证件号码!');
                    return;
                }
                personService.mobileNumberGet($scope.passWordResetDetailDTO.name, $scope.passWordResetDetailDTO.idNumber).then(function (success) {
                    $log.info('手机号码查询成功', success);
                    $scope.pwmobilenumbermsg = '发送到' + success.data.mobilenumber;

                }, function (err) {
                    $log.info('手机号码查询失败', err);
                    if (err.data.hasOwnProperty('detail')) {
                        $scope.pwmobilenumbermsg = err.data.detail;
                    } else if (err.data.hasOwnProperty('message')) {
                        $scope.pwmobilenumbermsg = err.data.message;
                    } else {
                        $scope.messageBox.showInfo('手机号码查询失败！');
                    }
                });
            };
            $scope.validatePassword = function () {
                var regExp = /^[a-zA-Z]{1}.*\d+.*$/;
                $scope.password = $scope.passWordResetDetailDTO.newPassword;
                return regExp.test($scope.password);
            };
            //密码重置
            $scope.resetLoginPassword = function () {
                if ($scope.validateCode()) {
                    return;
                }
                if ($scope.validateProtect()) {
                    return;
                }
                // 整合密保问题
                // $scope.passWordResetDetailDTO.answers = $scope.questionList;
                // 整合验证码header
                // $scope.MobileInfo.captchaId = $scope.captcha.captchaId;
                // $scope.MobileInfo.captchaWord = $scope.captcha.captchaWord;
                $scope.MobileInfo.captcha = $scope.passWordResetDetailDTO.captcha;
                personService.restPasswordMethod($scope.MobileInfo, $scope.passWordResetDetailDTO).then(function () {
                    $log.info('密码重置成功！');
                    $location.path('/personPassword_View');
                }, function (err) {
                    $scope.getCaptcha();
                    $log.info('密码重置失败', err);
                    if (err.data.hasOwnProperty('detail')) {
                        $scope.messageBox.showInfo('密码重置失败!' + err.data.detail);
                    } else if (err.data.hasOwnProperty('message')) {
                        $scope.messageBox.showInfo('密码重置失败!' + err.data.message);
                    } else {
                        $scope.messageBox.showInfo('密码重置失败！');
                    }
                });
            };
            // $scope.validateReset = function () {
            //     if (undefined === $scope.passWordResetDetailDTO.captcha || 3 > $scope.passWordResetDetailDTO.captcha.length) {
            //         $scope.messageBox.showInfo('请输入接收到的短信验证码！');
            //         return true;
            //     } else {
            //         return false;
            //     }
            // };
            $scope.validateCode = function () {
                if (undefined === $scope.passWordResetDetailDTO.name || '' === $scope.passWordResetDetailDTO.name || null === $scope.passWordResetDetailDTO.name) {
                    $scope.messageBox.showInfo('请输入您的姓名！');
                    return true;
                }
                //证件号码
                if ($scope.resetForm.idNumber.$invalid) {
                    if (null == $scope.resetForm.idNumber.$viewValue || '' == $scope.resetForm.idNumber.$viewValue) {
                        $scope.messageBox.showInfo('请输入证件号码！');
                    } else {
                        $scope.messageBox.showInfo('请输入正确位数的证件号码！');
                    }
                    return true;
                }
                if ($scope.checkCardNum()) {
                    return true;
                }
                //图形验证码
                if (undefined === $scope.captcha.captchaWord || '' === $scope.captcha.captchaWord) {
                    $scope.messageBox.showInfo('请输入图形验证码！');
                    return true;
                }
                return false;
            };
            $scope.validateProtect = function () {
                //设置密码
                if (undefined === $scope.passWordResetDetailDTO.newPassword || 6 > $scope.passWordResetDetailDTO.newPassword.length) {
                    if (null == $scope.resetForm.newPassword.$viewValue || '' == $scope.resetForm.newPassword.$viewValue) {
                        $scope.messageBox.showInfo('请输入至少6位的密码！');
                    } else {
                        $scope.messageBox.showInfo('密码不合法，请修改！');
                    }
                    return true;
                }
                //重复密码
                if ($scope.resetForm.confirmpassword.$error._required || $scope.resetForm.confirmpassword.$error.notEqual) {
                    if (null == $scope.resetForm.confirmpassword.$viewValue || '' == $scope.resetForm.confirmpassword.$viewValue) {
                        $scope.messageBox.showInfo('请输入重复密码！');
                    } else if ($scope.passWordResetDetailDTO.newPassword != $scope.resetForm.confirmpassword.$viewValue) {
                        $scope.messageBox.showInfo('设置密码与重复密码不匹配！');
                    } else {
                        $scope.messageBox.showInfo('重复密码有误，请修改！');
                    }
                    return true;
                }

                return false;
            };
            $scope.getCaptcha = function () {
                CaptchaService.getNextCaptcha().$promise.then(function (data) {
                    $log.info('loginCtrl获取到验证码', data.id);
                    $scope.captchaUrl = data.getImageUrl() + "?" + Math.random();
                    $scope.captcha.captchaId = data.id;
                });
            };
            //获取图形验证码
            $scope.getCaptcha();
            //手机验证码请求
            $scope.passwordCtrlMessage = function () {
                if ($scope.validateCode()) {
                    return;
                } else {
                    personService.mobileCodePostByName($scope.passWordResetDetailDTO.name, $scope.passWordResetDetailDTO.idNumber, $scope.captcha).then(function (success) {
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
        }])
    .animation(".P_L_T_input", function () {
        return {

            //一项被插入到列表时触发
            enter: function (element, done) {


                element.css({
                    position: 'relative',
                    left: -10,
                    opacity: 0
                });
                element.animate({
                    left: 0,
                    opacity: 1
                }, done);

            },

            //一项从列表中被移除时触发
            leave: function (element, done) {

                element.css({
                    position: 'relative',
                    left: 0,
                    opacity: 1
                });
                element.animate({
                    left: -10,
                    opacity: 0
                }, done);
            }

        }
    });