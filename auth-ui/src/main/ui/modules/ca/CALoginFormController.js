/**
 * login controller
 * author:y_zhang.neu
 */
'use strict';
angular.module('authserver.ca')
    .controller('caCtrl', ['$rootScope', '$scope', '$location', '$log', 'caService', 'messageBox', '$http', 'girderConfig', 'XSRFTokenService', '$timeout', '$q',
        function ($rootScope, $scope, $location, $log, caService, messageBox, $http, girderConfig, XSRFTokenService, $timeout, $q) {
            //messagebox
            $scope.messageBox = messageBox;

            $scope.isSubmit = false;

            $scope.credentials = {
                cellnum: '',
                password: '',
                certsOptions: [],
                selectedOption: {}
            };

            var neuca = null;

            var DF = 'df';
            var GX = 'gx';

            var arrayCa = [DF, GX];

            var ca_index = -1;

            var getCAObject = function (ca_type) {
                if (ca_type === DF) {
                    neuca = $NEUCA.DF_API;
                } else if (ca_type === GX) {
                    neuca = $NEUCA.GX_API;
                } else {
                    neuca = null;
                }
            };

            /**
             * 验证证书密码
             * @param type
             */
            var calogin = function (type) {
                var deferred = $q.defer();
                neuca.login($scope.credentials.selectedOption.certid, $scope.credentials.password, function (data) {
                    $log.info("证书密码验证成功");
                    deferred.resolve(type);
                }, function (err) {
                    $scope.loginButton = "单位证书用户登录";
                    $scope.isSubmit = false;
                    $log.error("证书密码登录失败", err);
                    $scope.credentials.password = '';
                    messageBox.showError(err.errorMsg);
                });
                return deferred.promise;
            };

            /**
             * 获取后台产生的随机数
             * @param type 证书类型
             */
            var getRandrom = function (type) {
                var deferred = $q.defer();
                $http.get(girderConfig.baseUrl + 'ws/ca/randrom/' + type + '?' + Math.random()).success(function (data, status, headers, config) {
                    console.info("获取随机数成功", data);
                    var form = {
                        'type': type,
                        'randrom': data.randrom
                    };
                    deferred.resolve(form);
                }).error(function (err, status, headers, config) {
                    $scope.loginButton = "单位证书用户登录";
                    $scope.isSubmit = false;
                    console.error('获取随机数失败', err);
                    if (err.hasOwnProperty('detail')) {
                        messageBox.showError("获取随机数失败：" + err.detail);
                    } else if (err.hasOwnProperty('message')) {
                        messageBox.showError("获取随机数失败：" + err.message);
                    } else {
                        messageBox.showError("获取随机数失败");
                    }
                });
                return deferred.promise;
            };

            /**
             * 根据证书id获取证书信息中的单位编号（ou）
             * @param form
             */
            var getCompanyNumber = function (form) {
                var deferred = $q.defer();
                // 获取单位编号
                neuca.loadNumber($scope.credentials.selectedOption.certid, function (data) {
                    form.indata = data[0];
                    deferred.resolve(form);
                }, function (err) {
                    $scope.loginButton = "单位证书用户登录";
                    $scope.isSubmit = false;
                    $log.error("获取单位编号失败", err, err.errorMsg);
                    messageBox.showError("获取单位编号失败：" + err.errorMsg);
                });
                return deferred.promise;
            };

            /***
             * 对单位编号+‘|||’+随机数 进行签名操作
             * @param form
             */
            var signData = function (form) {
                var deferred = $q.defer();
                //对单位编号和随机数签名
                neuca.signData($scope.credentials.selectedOption.certid, form.indata + '|||' + form.randrom, function (data) {
                    form.signdata = data
                    deferred.resolve(form);
                }, function (err) {
                    $scope.loginButton = "单位证书用户登录";
                    $scope.isSubmit = false;
                    $log.error("签名失败", err);
                    messageBox.showError("签名失败，请重试");
                });
                return deferred.promise;
            };

            /**
             * 导出用户证书
             * @param form
             */
            var exportUserCert = function (form) {
                var deferred = $q.defer();
                // 获取用户证书
                neuca.exportUserCert($scope.credentials.selectedOption.certid, function (data) {
                    form.certdata = data;
                    deferred.resolve(form);
                }, function (err) {
                    $scope.loginButton = "单位证书用户登录";
                    $scope.isSubmit = false;
                    $log.error("获取用户证书失败", err);
                    messageBox.showError("获取用户证书失败，请重试");
                });
                return deferred.promise;
            };

            /**
             * CA登录
             * @param form
             */
            var formLogin = function (form) {
                var formObj = {
                    'type': form.type,
                    'indata': form.indata,
                    'signdata': form.signdata,
                    'certdata': form.certdata
                };

                var fd = '';
                angular.forEach(formObj, function (value, key) {
                    fd += key + '=' + value + '&'
                });
                fd = fd.substr(0, fd.length - 1);
                var header = {headers: {'Content-Type': 'application/x-www-form-urlencoded'}};
                $http.post(girderConfig.baseUrl + 'api/ca/login', fd, header).success(function (data) {
                    console.info('--登录成功后返回值--', data);
                    // localStorage.setItem("ca-login-type", $scope.credentials.selectedOption.type);
                    // localStorage.setItem("ca-login-certid", $scope.credentials.selectedOption.certid);
                    sessionStorage.setItem("ca-login-type", $scope.credentials.selectedOption.type);
                    sessionStorage.setItem("ca-login-certid", $scope.credentials.selectedOption.certid);
                    window.location.href = data.redirectUri;
                }).error(function (err) {
                    $scope.loginButton = "单位证书用户登录";
                    $scope.isSubmit = false;
                    console.info('--登录失败--', err);
                    if (err.hasOwnProperty('detail')) {
                        messageBox.showError("登录失败：" + err.detail);
                    } else if (err.hasOwnProperty('message')) {
                        messageBox.showError("登录失败：" + err.message);
                    } else {
                        messageBox.showError("登录失败：" + err);
                    }
                });
            };

            /**
             * 登录
             * @type {{username: string, taxcode: string, password: string, captchaId: null, captchaWord: null}}
             */
            $scope.loginSystem = function (ls) {
                if (!ls.$valid) {
                    return;
                }
                var sele = $scope.credentials.selectedOption;
                if (!sele.hasOwnProperty("type")) {
                    messageBox.showError("请选择证书");
                    return;
                }
                $scope.loginButton = "登录中。。。";
                $scope.isSubmit = true;

                var type = $scope.credentials.selectedOption.type;
                $log.info("当前证书厂商", $scope.credentials.selectedOption, type);

                getCAObject(type);

                calogin(type)//验证pin码
                    .then(getRandrom)//获取随机数
                    .then(getCompanyNumber)//获取单位编号，取数组第一个
                    .then(signData)//签名
                    .then(exportUserCert)//导出用户证书
                    .then(formLogin);//form登录
            };

            //获取xsrfToken
            // XSRFTokenService.getXsrfToken().success(function (data, status, headers, config) {
            // }).error(function (data, status) {
            // });

            /**
             * 切换证书事件
             */
            // $scope.certChange = function () {
            // if ($scope.credentials.selectedOption && $scope.credentials.selectedOption.certid) {
            // var type = $scope.credentials.selectedOption.type;
            // $log.info("选择证书事件", $scope.credentials.selectedOption, type);
            // $scope.credentials.password = '';
            // }
            // };

            /**
             * 广西CA USB change 事件
             */
            $_$onUsbKeyChangeCallBackFunc = function () {
                // safeApply($scope, function () {
                console.log("接收到usb change事件");
                var appElement = document.getElementById('caview');
                var $outscope = angular.element(appElement).scope();
                $outscope.initLogin();
                // $outscope.$apply();
                // });

                // debugger
                // $outscope.localLocation.path('/calogin');
                // alert($outscope.localLocation.absUrl());
                // window.location.reload();
            };

            /**
             * 加载证书
             * @param ca_type
             */
            var loadCert = function (ca_type) {
                var deferred = $q.defer();
                if (ca_type) {
                    neuca.loadCert(function (data) {
                        $log.info(ca_type + "获取证书成功", data.length);
                        safeApply($scope, function () {
                            if (data.length > 0) {
                                for (var i = 0; i < data.length; i++) {
                                    var tmp = data[i];
                                    tmp.type = ca_type;
                                    var index = JSON.stringify($scope.credentials.certsOptions).indexOf(tmp.certid);
                                    if (index == -1) {
                                        $scope.credentials.certsOptions.push(tmp);
                                    }
                                }
                                // $scope.credentials.certsOptions = $scope.credentials.certsOptions.concat(data);
                                $scope.credentials.selectedOption = $scope.credentials.certsOptions[0];
                            } else {
                                // $log.error(ca_type + "未获取到证书", data);
                            }
                        });
                        deferred.resolve();
                    }, function (err) {
                        $log.error(ca_type + "获取证书失败", err);
                        // messageBox.showError("获取证书失败");
                        deferred.resolve();
                    });
                } else {
                    deferred.resolve();
                }
                return deferred.promise;
            };

            /**
             * 初始化CA控件
             */
            var initCAObject = function () {
                ca_index++;
                var ca_type = arrayCa[ca_index];
                getCAObject(ca_type);

                var deferred = $q.defer();
                neuca.initCAObject(function (data) {
                    $log.info(ca_type + "初始化CA组件成功");
                    deferred.resolve(ca_type);
                }, function (err) {
                    $log.error(ca_type + "初始化CA组件失败", err);
                    deferred.resolve();
                });
                return deferred.promise;
            };


            $scope.initLogin = function () {
                safeApply($scope, function () {
                    $scope.credentials.password = '';
                    $scope.credentials.certsOptions = [];
                    $scope.credentials.selectedOption = {};
                });

                ca_index = -1;

                initCAObject()
                    .then(loadCert)
                    .then(initCAObject)
                    .then(loadCert)
                    .then(function () {
                        safeApply($scope, function () {
                            if ($scope.credentials.certsOptions.length == 0) {
                                messageBox.showError("未安装驱动或未插入Ukey", 5000);
                            }
                            neuca = null;
                            $scope.loginButton = "单位证书用户登录";
                            // $scope.certChange();
                        });
                    });
            };

            function safeApply(scope, fn) {
                (scope.$$phase || scope.$root.$$phase) ? fn() : scope.$apply(fn);
            }

        }]);

