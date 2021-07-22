/**
 * 学校网厅登录控制器
 *
 */
'use strict';
angular.module('authserver.school')
    .controller('schoolCtrl', ['$rootScope', '$scope', '$location', '$log', 'CaptchaService', 'schoolService', 'messageBox', '$http', 'girderConfig', 'XSRFTokenService',
        function ($rootScope, $scope, $location, $log, CaptchaService, schoolService, messageBox, $http, girderConfig, XSRFTokenService) {

            //messagebox
            $scope.messageBox = messageBox;
            /**
             * 登录
             * @type {{username: string, taxcode: string, password: string, captchaId: null, captchaWord: null}}
             */
            $scope.loginSystem = function (ls) {
                if (!ls.$valid) {
                    return;
                }
                var fd = '';
                var formObj = {
                    'username': encryptByDES($scope.credentials.username, $scope.credentials.randrom),
                    'password': encryptByDES($scope.credentials.password, $scope.credentials.randrom)

                };
                angular.forEach(formObj, function (value, key) {
                    fd += key + '=' + value + '&'
                });
                console.info('--fd--', fd);
                fd = fd.substr(0, fd.length - 1);
                console.info('--截取后的fd--', fd);
                $http.post(girderConfig.baseUrl + 'api/siagent/usernamepassword/login', fd,
                    {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                            'captchaWord': $scope.credentials.captchaWord,
                            'captchaId': $scope.credentials.captchaId
                        }
                    }
                )
                    .success(function (data) {
                        console.info('--登录成功后返回值--', data);
                        window.location.href = data.redirectUri;
                    }).error(function (err) {
                    $scope.getCaptcha();
                    messageBox.showError(err.message);
                });
            };
            $scope.credentials = {
                username: '',
                taxcode: '',
                password: '',
                captchaId: null,
                captchaWord: null,
                randrom: ''
            };
            //获取xsrfToken
            XSRFTokenService.getXsrfToken().success(function (data, status, headers, config) {
                $scope.getCaptcha();
                $scope.credentials.randrom = data.randrom;
            }).error(function (data, status) {
            });
            $scope.getCaptcha = function () {
                CaptchaService.getNextCaptcha().$promise.then(function (data) {
                    $log.info('loginCtrl获取到验证码', data.id);
                    $scope.captchaUrl = data.getImageUrl() + "?" + Math.random();
                    $scope.credentials.captchaId = data.id;
                });
            };
            //$scope.getCaptcha();

            // var singexVersion="2.0.2";
            // var signeXIni="2.0.2";
            $scope.checkSignerX = function (url) {
                try {
                    /*var comActiveX = new ActiveXObject("SignerX.FormSigner.1");
                     var version = comActiveX.Version;
                     var ver = version.substr(0,5);
                     if(ver!=singexVersion)
                     {
                     alert("您的补丁为版本为:"+version+"，请在本页下载最新的(E-sign090205.exe)补丁");
                     return false;
                     }*/
                    window.location.href = url;
                }
                catch (e) {
                    // alert(" 1 首先在本页下载补丁进行安装\n 2 降低您的IE级别为中或低 \n 3 如以上问题没有解决请联系江苏CA客服 025-833393888");
                }
            };
        }]);