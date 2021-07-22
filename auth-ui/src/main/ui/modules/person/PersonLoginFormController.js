/**
 * login controller
 * author:y_zhang.neu
 */
'use strict';
angular.module('authserver.person')
    .controller('personCtrl', ['$rootScope', 'XSRFTokenService', '$scope', '$location', '$log','personService', 'CaptchaService','messageBox', '$cookies', '$cookieStore', 'girderConfig', '$http',
        function ($rootScope, XSRFTokenService, $scope, $location, $log,personService, CaptchaService, messageBox, $cookies, $cookieStore, girderConfig, $http) {
            //messagebox
            $scope.messageBox = messageBox;
            $scope.popShow = true;
            $scope.popClose = function () {
                $scope.popShow = false
            };

            /**
             * 登录
             * @type {{username: string, taxcode: string, password: string, captchaId: null, captchaWord: null}}
             */
            $scope.personcredentials = {
                idNum: '',
                password: '',
                captchaId: null,
                captchaWord: null,
                randrom: ''
            };

            /**
             * 系统登录fun
             */
            $scope.loginSystem = function (ls) {
                if (!ls.$valid) {
                    return;
                }
                //包装formdata对象
                // var fd = new FormData();
                var fd = '';
                var formObj = {
                    // 'username': encryptByDES($scope.personcredentials.idNum, $scope.personcredentials.randrom),
                    // 'password': encryptByDES($scope.personcredentials.password, $scope.personcredentials.randrom)
                    //'username': encryptByDES($scope.personcredentials.idNum, 'OqcbHRPGWILe43usueyHZyYT'),
                     //'password': encryptByDES($scope.personcredentials.password, 'OqcbHRPGWILe43usueyHZyYT')
                    'username': $scope.personcredentials.idNum,
                    'password': $scope.personcredentials.password

                    // 'captchaWord': $scope.personcredentials.captchaWord,
                    // 'captchaId': $scope.personcredentials.captchaId
                };
                angular.forEach(formObj, function (value, key) {
                    fd += key + '=' + value + '&'
                });
                fd = fd.substr(0, fd.length - 1);
                $http.post(girderConfig.baseUrl + 'api/person/idandmobile/login', fd,
                    {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                            'captchaWord': $scope.personcredentials.captchaWord,
                            'captchaId': $scope.personcredentials.captchaId
                        }
                    }
                )
                    .success(function (data) {
                        window.location.href = data.redirectUri;
                    }).error(function (err) {
                    $scope.persongetCaptcha();
                    messageBox.showError(err.message);
                });
            };

            $scope.persongetCaptcha = function () {
                CaptchaService.getNextCaptcha().$promise.then(function (data) {
//                    $log.info('personloginCtrl获取到验证码', data.id);
                    $scope.personcaptchaUrl = data.getImageUrl() + "?" + Math.random();
                    $scope.personcredentials.captchaId = data.id;
                });
            };

            //获取政务服务地址并跳转
            $scope.getRedirect = function () {
                personService.getRedirectUrl().$promise.then(function (data) {
                    debugger;
                    $log.debug(data);
                });
            };


            //获取xsrfToken
            XSRFTokenService.getXsrfToken().success(function (data, status, headers, config) {
                $scope.persongetCaptcha();
                $scope.personcredentials.randrom = data.randrom;
            }).error(function (data, status) {
            });

            //添加到收藏夹
            $scope.addFavorite = function () {
                $log.info('添加到收藏夹');
                try {
                    window.external.addFavorite('http://public.tj.hrss.gov.cn/personlogin/', '六安市社会保险网上');
                }
                catch (e) {
                    try {
                        window.sidebar.addPanel('天津个人社保申报', 'http://public.tj.hrss.gov.cn/personlogin/', "");
                    }
                    catch (e) {
                        //跳转到添加收藏夹说明页
                        //$location.path('/add_favorite');
                        $scope.messageBox.showInfo("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加\n\n收藏地址：http://public.tj.hrss.gov.cn/personlogin/");
                    }
                }

            };
            $scope.tellYou = function () {
                $scope.messageBox.showInfo("如原手机丢失，请本人携带身份证原件和新手机到社保分中心柜台办理注册手机变更业务。");
            };
        }]);