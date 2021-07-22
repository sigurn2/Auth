angular.module('AuthUI',
    [
        'ngRoute',
        'ngResource',
        'ngAnimate',
        'Validateform',
        'ui.bootstrap',
        'ngCookies',
        'captcha',
        'Directive',
        'xsrfToken',
        'authserver',
        'authserver.enterprise',
        'authserver.ca',
        'authserver.person',
        'authserver.resident',
        'authserver.school',
        'authserver.expert'
    ]);
angular.module('authserver.enterprise', []);//企业
angular.module('authserver.ca', []);//CA企业
angular.module('authserver.person', []);//个人
// angular.module('authserver.siagent', []);//建筑单位
angular.module('authserver.resident', []);//居民
angular.module('authserver.school', []);//学校
angular.module('authserver.expert', []);//专家

//工程配置
angular.module('AuthUI').config(['girderConfigProvider', function (girderConfigProvider) {
    girderConfigProvider.baseUrl = '/uaa/';
}]);


//允许访问区域配置(输入值可为：['person','enterprise','siagent','resident'])
angular.module('AuthUI')
    .constant('ALLOW_ACCESS_LIST', {
        //默认路由
        otherwiseRoute: 'enterprise',
        //系统允许列表
        systems: ['person', 'enterprise', 'ca', 'siagent', 'expert','bxenterprise']
    });


'use strict';
// scripts/insure.js
angular.module('AuthUI').config(['$routeProvider', '$locationProvider',function ($routeProvider,$locationProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: 'modules/login/login.html',
            controller: 'LoginCtrl'
        })
        //---------------默认路由
        .otherwise({
            redirectTo: '/login'
        });

    $locationProvider.html5Mode(false);
}]);



//TODO 路由变换检查
angular.module('AuthUI').run(["$log", "$rootScope", "$location", "ALLOW_ACCESS_LIST", function ($log, $rootScope, $location, ALLOW_ACCESS_LIST) {
    $rootScope.$on('$routeChangeStart', function (event, next, current) {

        //如果不是angular初始化
        if (next.hasOwnProperty('$$route')) {
            //允许访问路由名单
            var allowAccessList = ALLOW_ACCESS_LIST.systems;
            //当前将要访问的路由
            var currentPath = next.$$route.originalPath;
            //当前路由是否允许访问(默认不允许)
            var isAllowAccess = false;

            //检查当前路由是否允许跳转
            angular.forEach(allowAccessList, function (flag) {
                if (currentPath.indexOf(flag) === 1 || currentPath === '/login') {
                    // console.log(currentPath);
                    $rootScope.$broadcast('changeSystemFlag',flag);
                    isAllowAccess = true;
                }
            });

            //如果当前路由未经允许
            if (!isAllowAccess) {
                if (angular.isDefined(current)) {
                    //如果从其他路由跳转
                    $location.path(current.$$route.originalPath);
                } else {
                    $location.path('/');
                }
            }
        }
    });
    $rootScope.$on('$routeChangeSuccess', function (evt, next, current) {
        console.log("请求成功：");
        var firsttitle = '哈尔滨市人社网上经办大厅';
        if(next.$$route.originalPath==="/bxenterpriselogin"){
            firsttitle="哈尔滨市养老保障公共服务平台";
        }else{
            firsttitle="哈尔滨市人社网上经办大厅";
        };

        $rootScope.$watch('title',function(){ document.title = firsttitle;});

    });

}]);



/**
 * Created by Erwin on 2017/9/30 0030.
 * 登录工程config
 */
angular.module('AuthUI')
    .provider('girderConfig', {
        //可以配置的属性
        baseUrl: 'please config baseUrl before use this frameWork', //全局后台服务路径 示例 /sipub
        //可以访问到的属性
        $get: [function () {
            var service = {
                baseUrl: this.baseUrl
            };
            return service;
        }]
    });
    // .factory('authGlobalInterceptor', function($q,$cookies) {
    //     return {
    //         // 可选，拦截成功的请求
    //         request: function(config) {
    //             // 进行预处理
    //             $cookies.get('X-XSRF-TOKEN');
    //
    //             return config || $q.when(config);
    //         }

            // // 可选，拦截失败的请求
            // requestError: function(rejection) {
            //     // 对失败的请求进行处理
            //     // ...
            //     if (canRecover(rejection)) {
            //         return responseOrNewPromise
            //     }
            //     return $q.reject(rejection);
            // },
            //
            //
            //
            // // 可选，拦截成功的响应
            // response: function(response) {
            //     // 进行预处理
            //     // ....
            //     return response || $q.when(reponse);
            // },
            //
            // // 可选，拦截失败的响应
            // responseError: function(rejection) {
            //     // 对失败的响应进行处理
            //     // ...
            //     if (canRecover(rejection)) {
            //         return responseOrNewPromise
            //     }
            //     return $q.reject(rejection);
            // }
    //     };
    // })
    // .config(function($httpProvider) {
    //     $httpProvider.interceptors.push('authGlobalInterceptor');
    // });
/**
 * 应用级别的控制器，其他的控制器将会继承此控制器
 * 可以将应用级别的全局变量放到这里，而不是放到$rootScope上
 */

'use strict';
angular.module('AuthUI')
    .controller('ApplicationController', ['$log', '$scope', 'ALLOW_ACCESS_LIST',
        function ($log, $scope, ALLOW_ACCESS_LIST) {

            var uri = window.location.href;
            $scope.areaCode = uri.substring(uri.length -6, uri.length);
            console.log('areaCode',$scope.areaCode);
            if (uri.indexOf('person')!==-1){
                $scope.areaName = '个人登录'
            } else if ($scope.areaCode == "210500") {
                $scope.areaName = '虚拟单位'
            } else  {
                $scope.areaName = '原账号密码'
            }
            //应用程序载入初始化
            $scope.loadApp = function () {
                $log.info('应用程序初始化开始..');
                $scope.systemFlag = ALLOW_ACCESS_LIST.otherwiseRoute;
            };

            $scope.$on('changeSystemFlag', function (p1, p2) {
                // console.log(p1,p2);
                $scope.systemFlag = p2;
            });

            /**
             * 获取系统icon
             * @returns {string}
             */
            $scope.getSystemIconClass = function () {
                return 'L_cen_left_' + $scope.systemFlag;
            }

        }]);

function encryptByDES(message, key) {
	var keyHex = CryptoJS.enc.Utf8.parse(key);
	var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
		mode : CryptoJS.mode.ECB,
		padding : CryptoJS.pad.Pkcs7
	});
	return encrypted.toString();
}

/**
 * Controller For Home Page
 */
'use strict';
angular.module('authserver', [])
    .controller('LoginCtrl', ['$log', '$scope', '$location', 'ALLOW_ACCESS_LIST',
        function ($log, $scope, $location, ALLOW_ACCESS_LIST) {

            var keepGoing = true;
            angular.forEach(ALLOW_ACCESS_LIST.systems, function (value) {
                if (keepGoing) {
                    if ($location.absUrl().indexOf(value) != -1) {
                        $location.path('/' + value + 'login');
                        keepGoing = false;
                    }
                }
            });
            if (keepGoing) {
                $location.path('/' + ALLOW_ACCESS_LIST.otherwiseRoute + 'login');
            }
        }]);


/**
 * Created by Erwin on xx/xx/xx.
 * XSRF security token
 */
'use strict';
angular.module('xsrfToken', [])
    .factory('XSRFTokenService', ['$log', '$resource', 'girderConfig','$http',
        function ($log, $resource, girderConfig,$http) {
            //xsrf地址
            var url = girderConfig.baseUrl + 'prevent/token ';

            //定义工厂对象
            var factory = {
                //
            };
            /**
             * 获取xsrfToken
             */
            factory.getXsrfToken = function () {
                return  $http({ method: 'GET', url:url });
            };

            return factory;
        }]);


/**
 * 用户安全信息查询资源服务模块
 *
 * @author wuyf
 * @date 2015-03-16
 */
'use strict';
angular.module('captcha', [])
    .factory('CaptchaService', ['$log', '$resource', 'girderConfig',
        function ($log, $resource, girderConfig) {
            //验证码地址
            var url = girderConfig.baseUrl + 'captcha/img';
            var Resource = $resource(url, {}, {
                getCaptcha: {
                    method: 'GET',
                    url: url,
                    isArray: false
                }
            });
            Resource.prototype.getImageUrl = function () {
                return url + '/' + this.id;
            };
            //定义工厂对象
            var factory = {
                //
            };
            /**
             * 获取下一个验证码
             * 返回对象{id,url}
             */
            factory.getNextCaptcha = function () {
                return Resource.getCaptcha();
            };
            return factory;
        }]);


/**
 * Created by y_zhang.neu on 2015/11/14.
 */
angular.module('Directive', [])
    //密码重复输入比对自定义指令
    .directive('girderValidPassword', function () {
        return {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                reference: '=girderValidPassword'  //双向绑定
            },
            link: function (scope, elm, attrs, ctrl) {
                ctrl.$parsers.unshift(function (viewValue) {
                    if (viewValue === '') {
                        ctrl.$setValidity('_required', false);
                    } else {
                        ctrl.$setValidity('_required', true);
                    }
                    var noMatch = viewValue !== scope.reference;
                    ctrl.$setValidity('notEqual', !noMatch);
                });
                //监听
                scope.$watch('reference', function (value) {
                    ctrl.$setValidity('notEqual', value === ctrl.$viewValue);
                });
            }
        };
    });
/**
 * Created by y_zhang.neu on 2015/9/28.
 * validate ID Card Number
 */
'use strict';
angular.module('Validateform', [])
    .factory('IDCard', [function () {
        var factory = {};
        /**
         * 校验身份证是否合法
         * @param number
         * {isvalid:(true/false),message:'错误信息'}
         */
        factory.validateIDCard = function (number) {
            //检查号码是否符合规范，包括长度，类型
            function isCardNo(card) {
                //身份证号码为18位,前17位为数字，最后一位是校验位，可能为数字或字符X
                var reg = /(^\d{17}(\d|X)$)/;
                if (reg.test(card) === false) {
                    return false;
                }
                return true;
            }

            //检查生日是否正确
            function checkBirthday(card) {
                var len = card.length;
                //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
                if (len === 18) {
                    var eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
                    var arrdata = card.match(eighteen);
                    var year = arrdata[2];
                    var month = arrdata[3];
                    var day = arrdata[4];
                    var birthday = new Date(year + '/' + month + '/' + day);
                    return verifyBirthday(year, month, day, birthday);
                }
                return false;
            }

            //校验日期
            function verifyBirthday(year, month, day, birthday) {
                var now = new Date();
                var nowyear = now.getFullYear();
                //年月日是否合理
                if (birthday.getFullYear() === parseInt(year, 10) && (birthday.getMonth() + 1) === parseInt(month, 10) && birthday.getDate() === parseInt(day, 10)) {
                    //判断年份的范围（1岁到100岁之间) 南宁制卡要求
                    var time = nowyear - year;
                    if (time >= 1 && time <= 100) {
                        return true;
                    }
                    return false;
                }
                return false;
            }

            //判断校验位是否正确
            function checkParity(card) {
                var len = card.length;
                if (len === 18) {
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                    var cardTemp = 0, i, valnum;
                    for (i = 0; i < 17; i++) {
                        cardTemp += card.substr(i, 1) * arrInt[i];
                    }
                    valnum = arrCh[cardTemp % 11];
                    if (valnum === card.substr(17, 1)) {
                        return true;
                    }
                    return false;
                }
                return false;
            }

            var IDCardValidateResult = {isValid: 'true', message: ''};
            //校验身份证位数是否为18位
            if (number.length !== 18) {
                IDCardValidateResult = {isValid: 'false', message: '录入证件号码位数不正确,应为18位'};
                return IDCardValidateResult;
            }
            if (!isCardNo(number)) {
                IDCardValidateResult = {isValid: 'false', message: '身份证格式不正确'};
                return IDCardValidateResult;
            }
            if (!checkBirthday(number)) {
                IDCardValidateResult = {isValid: 'false', message: '身份证出生日期不正确'};
                return IDCardValidateResult;
            }
            if (!checkParity(number)) {
                IDCardValidateResult = {isValid: 'false', message: '身份证号码有误,请修改'};
                return IDCardValidateResult;
            }
            return IDCardValidateResult;
        };
        /**
         * 获取出生日期
         * @param number
         */
        factory.getBirthday = function (number) {
            var birthYear = number.substring(6, 10);
            var birthMonth = number.substring(10, 12);
            var birthDay = number.substring(12, 14);
            var birthDate = new Date(birthYear, parseInt(birthMonth, 10) - 1, birthDay);
            return birthDate;
        };
        /**
         * 获取性别
         * @param number
         */
        factory.getSex = function (number) {
            var sex = number.substring(16, 17);
            if ((parseInt(sex, 10) % 2) === 0) {
                return '2';
            } else {
                return '1';
            }
        };
        return factory;
    }]);
/**
 * Created by wuyf on 2014/7/4.
 *
 * messageBox组件
 * -------------
 */
'use strict';
// app/girder/ui/messagebox/messagebox.js

angular.module('AuthUI')
    .service('messageService', ['$modal', function ($modal) {
        //默认参数配置
        var modalDefaults = {
            backdrop: true,
            keyboard: true,
            modalFade: true,
            templateUrl: 'modules/support/template/messageview.html'
        };

        //默认view参数配置
        var modalOptions = {
            closeButtonText: 'Close',
            actionButtonText: 'OK',
            headerText: 'Proceed?',
            bodyText: 'Perform this action?'
        };

        this.showModal = function (customModalDefaults, customModalOptions) {
            if (!customModalDefaults) {
                customModalDefaults = {};
            }
            customModalDefaults.backdrop = 'static';
            return this.show(customModalDefaults, customModalOptions);
        };

        this.show = function (customModalDefaults, customModalOptions) {
            //Create temp objects to work with since we're in a singleton service
            var tempModalDefaults = {};
            var tempModalOptions = {};

            //Map angular-ui modal custom defaults to modal defaults defined in service
            angular.extend(tempModalDefaults, modalDefaults, customModalDefaults);

            //Map modal.html $scope custom properties to defaults defined in service
            angular.extend(tempModalOptions, modalOptions, customModalOptions);

            if (!tempModalDefaults.controller) {
                tempModalDefaults.controller = 'messageServiceController';
            }

            tempModalDefaults.resolve = {
                modalOptions: function () {
                    return tempModalOptions;
                }
            };
            //显示模态对话框
            return $modal.open(tempModalDefaults).result;
        };

    }])
    .controller('messageServiceController', ['$scope', '$modalInstance', 'modalOptions', '$timeout',
        function ($scope, $modalInstance, modalOptions, $timeout) {
            $scope.modalOptions = modalOptions;

            //是否定时关闭
            if ($scope.modalOptions.hasOwnProperty('timeClose')) {
                var time = $scope.modalOptions['timeClose'];
                if (time) {
                    $timeout(function () {
                        $modalInstance.dismiss('cancel');
                    }, time);
                }
            }
            //确定
            $scope.modalOptions.ok = function (result) {
                $modalInstance.close(result);
            };
            //取消
            $scope.modalOptions.close = function () {
                $modalInstance.dismiss('cancel');
            };
        }])
    //--------------------------
    //显示提示信息组件
    //
    //messageBox.showError('真心保存失败!!').then(function (result) {
    // $log.debug('messagebox then ',result);
    //});
    //--------------------------
    .service('messageBox', ['messageService', function (messageService) {

        var _showMsg = function (title, message, time) {
            var modalOptions = {
                closeButtonText: null,
                actionButtonText: '确定',
                headerText: title,
                bodyText: message,
                timeClose: time
            };

            return messageService.showModal({}, modalOptions);
        };

        //显示 标题 / 提示消息
        this.showInfo = function (title, message, time) {
            var t = (title === null) ? '消息提示' : title;
            return _showMsg(t, message, time);
        };

        // 显示提示消息
        this.showInfo = function (message, time) {
            return _showMsg('消息提示', message, time);
        };

        // 显示 标题 / 错误详情
        this.showError = function (title, message, time) {
            var t = (title === null) ? '错误提示' : title;
            return _showMsg(t, message, time);
        };

        // 显示 错误信息
        this.showError = function (message, time) {
            return _showMsg('错误提示', message, time);
        };

    }])
    //--------------------------
    //显示选择信息组件
    //
    //confirmBox.showInfo('这次要选择yes吗？').then(function (result) {
    //  $log.debug('这次选了yes!');
    //});
    //
    //--------------------------
    .service('confirmBox', ['messageService', function (messageService) {

        var _showMsg = function (title, message) {
            var modalOptions = {
                closeButtonText: '否',
                actionButtonText: '是',
                headerText: title,
                bodyText: message
            };
            return messageService.showModal({}, modalOptions);
        };

        //显示 标题 / 提示消息
        this.showInfo = function (title, message) {
            var t = (title === null) ? '消息提示' : title;
            return _showMsg(t, message);
        };

        // 显示提示消息
        this.showInfo = function (message) {
            return _showMsg('消息提示', message);
        };

    }]);


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
/**
 * Created by y_zhang.neu on 2015/11/13.
 */
'use strict';
angular.module('authserver.person')
    .factory('personService', ['$http', '$log', '$resource', 'girderConfig', function ($http, $log, $resource, girderConfig) {
        var fatory = [];
        //重置密码URL
        var REST_PASSWORD_URL = girderConfig.baseUrl + 'ws/password/reset';
        //发送短信URL
        var SEND_MOBILE_CODE_URL = girderConfig.baseUrl + 'captcha/sm';
        //修改手机号码URL
        var MOBILE_MODIFY_URL = girderConfig.baseUrl + 'ws/user/person/mobilenumber';
        //注册URL
        var PERSON_REGISTE_URL = girderConfig.baseUrl + 'ws/user/person/web';
        //获取手机号URL
        var MOBILE_GET_URL = girderConfig.baseUrl + 'captcha/sm/idnumbername';
        //密码重置发送短信URL
        var SEND_MOBILE_BYNAME_URL = girderConfig.baseUrl + 'captcha/sm/web/byidnumbername';
        //根据手机号码发送短信
        var SEND_MOBILE_BYPHONE_URL = girderConfig.baseUrl + 'captcha/sm/web/';
        //获取密保问题列表
        var QUESTION_LIST = girderConfig.baseUrl + 'ws/question';
        //根据身份证获取密保问题列表
        var QUESTION_USER_LIST = girderConfig.baseUrl + 'ws/user/question';

        var GET_REDIRECT = girderConfig.baseUrl + '/idstools/getGssionid';
        //发送密码重置短信获得验证码
        fatory.CodePostMethod = function (MobileDTO, captcha) {
            return $http({method: 'POST', url: SEND_MOBILE_CODE_URL, headers: captcha, data: MobileDTO});
        };

        //密码重置
        fatory.restPasswordMethod = function (passWordResetDetailDTO, mobileInfo) {
            //向PUT请求中定义headers
            debugger;
            return $http({method: 'POST', url: REST_PASSWORD_URL, headers: passWordResetDetailDTO, data: mobileInfo});
        };
        //手机号码修改
        fatory.modifyMobileMethod = function (mobileDetails, mobileInfo) {
            //向PUT请求中定义headers
            return $http({method: 'PUT', url: MOBILE_MODIFY_URL, headers: mobileInfo, data: mobileDetails});
        };
        //注册用户
        fatory.registePerson = function (personUserDTO, mobileInfo) {
            //alert(PERSON_REGISTE_URL);
            return $http({method: 'POST', url: PERSON_REGISTE_URL, headers: mobileInfo, data: personUserDTO});
        };

        //获取手机号
        fatory.mobileNumberGet = function (name, idnumber) {
            $log.info('获取手机号', name, idnumber);
            return $http({
                method: 'POST',
                url: MOBILE_GET_URL,
                data: {mobilenumber: '', name: name, idNumber: idnumber}
            })
        };

        //获取手机号
        fatory.mobileCodePostByName = function (name, idnumber, captcha) {
            $log.info('发送验证码', name, idnumber);
            return $http({
                method: 'POST',
                url: SEND_MOBILE_BYNAME_URL,
                headers: captcha,
                data: {name: name, idNumber: idnumber}
            })
        };

        //根据手机号码发送短信
        fatory.mobileCodePostByphoneNumberxiaowzh = function (phoneNumber, captcha) {
            $log.info('发送验证码', phoneNumber, captcha);
            return $http({
                method: 'POST',
                url: SEND_MOBILE_BYPHONE_URL + phoneNumber,
                headers: captcha
                // data: { name: name, idNumber: idnumber}
            })
        };

        // 获取密保问题列表
        fatory.getQuestionList = function () {
            return $http({method: 'GET', url: QUESTION_LIST});
        };

        // 获取密保问题列表
        fatory.getQuestionListById = function (idNumber) {
            return $http({method: 'GET', url: QUESTION_USER_LIST + '/' + idNumber});
        };

        //获取跳转url
        fatory.getRedirectUrl = function () {
            return $http({method: 'GET', url: GET_REDIRECT});
        }

        return fatory;
    }]);
/**
 * login controller
 * author:y_zhang.neu
 */
'use strict';
angular.module('authserver.person')
    .controller('registerCtrl', ['CaptchaService', 'XSRFTokenService', 'messageBox', '$rootScope', '$scope', '$location', '$log', 'IDCard', 'personService', '$timeout',
        function (CaptchaService, XSRFTokenService, messageBox, $rootScope, $scope, $location, $log, IDCard, personService, $timeout) {
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
                'idType': '01',
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
            // 获取密保问题列表
            /*personService.getQuestionList().then(function (data) {
                $scope.questions = data.data;

                // 监听select变化
                for (var i = 1; i <= $scope.questions.length; i++) {
                    $scope.$watch('tjPersonUserDTO.questionsPre.question' + i, function (newVal, oldVal) {
                        if (newVal !== oldVal) {
                            checkSelectStatus();
                            applyScopeChange();
                        }
                    });
                }
            });*/
            /**
             * 检查选中状态
             */
            /*function checkSelectStatus() {
                var questionsVal = [$scope.tjPersonUserDTO.questionsPre.question1, $scope.tjPersonUserDTO.questionsPre.question2, $scope.tjPersonUserDTO.questionsPre.question3];

                // 判断是否有重复值
                var questionsValBak = angular.copy(questionsVal);
                angular.forEach(questionsValBak, function (qVal, index) {
                    questionsValBak.splice(index, 1);
                    // 如果有重复值
                    if (questionsValBak.indexOf(qVal) !== -1) {
                        // 则取消选中
                        console.log('重复值为：', qVal);
                        questionsVal[index] = null;
                        eval('$scope.tjPersonUserDTO.questionsPre.question' + (index + 1) + ' = null');
                    }
                });
                // 恢复问题列表激活状态
                angular.forEach($scope.questions, function (qItem) {
                    qItem.active = UNSELECTED_CONTEXT;
                });
                // 改变分组状态
                angular.forEach($scope.questions, function (qItem, qIndex) {
                    angular.forEach(questionsVal, function (vItem, vIndex) {
                        if (qItem.questionId === vItem) {
                            // $scope.questions.splice(index, 1);
                            $scope.questions[qIndex].active = SELECTED_CONTEXT;
                        }
                    })
                });
                // 防止selet的ngModle显示undefined
                for (var i = 1; i <= 3; i++) {
                    if (angular.isUndefined(eval('$scope.tjPersonUserDTO.questionsPre.question' + i))) {
                        eval('$scope.tjPersonUserDTO.questionsPre.question' + i + '= null')
                    }
                }

                console.log('checkSelectStatus',angular.copy($scope.tjPersonUserDTO.questionsPre));

            };*/
            /**
             * 强制angular刷新select
             */
            /*function applyScopeChange() {
                $scope.questions.push({});

                $timeout(function () {
                    angular.forEach($scope.questions, function (qVal, index) {
                        if (!qVal.hasOwnProperty('questionId')) {
                            $scope.questions.splice(index, 1);
                        }
                    })
                }, 1)
            };*/
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
                personService.registePerson($scope.tjPersonUserDTO, $scope.MobileInfo).then(function (success) {
                    $location.path('/personRegister_View');
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
                    personService.mobileCodePostByphoneNumberxiaowzh($scope.MobileInfo.mobilenumber, $scope.captcha).then(function (success) {
                        $scope.pwmobilenumbermsg = '发送到' + success.data.errmsg;
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
                    debugger;
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
                    personService.CodePostMethod($scope.BatchMsgDto, $scope.captcha).then(function (rs) {
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
/**
 * @author y_zhang.neu
 * time 2015.11.24
 * @classDescription 天津个人前台路由
 */
angular.module('authserver.person')
    .config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        // 登录
        .when('/personlogin', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/login.html',
            controller: 'personCtrl'
        })
        //注册协议
        .when('/personAgree', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/agreement.html',
            controller: 'registerCtrl'
        })
        //注册
        .when('/personRegister', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/register.html',
            controller: 'registerCtrl'
        })
        //注册成功
        .when('/personRegister_View', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/register_view.html'
        })
        //密码修改
        .when('/personPassword', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/password.html',
            controller: 'passwordCtrl'
        })
        //密码修改成功
        .when('/personPassword_View', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/password_view.html'
        })
}]);
/**
 * login controller
 * author:y_zhang.neu
 */
'use strict';
angular.module('authserver.enterprise')
    .controller('enterpriseCtrl', ['$rootScope', '$scope', '$location', '$log', 'CaptchaService', 'enterpriseService', 'messageBox', '$http', 'girderConfig', 'XSRFTokenService',
        function ($rootScope, $scope, $location, $log, CaptchaService, enterpriseService, messageBox, $http, girderConfig, XSRFTokenService) {
            //浮动框
            $scope.popShow = true;
            $scope.popClose = function () {
                $scope.popShow = false
            };

            $scope.messageBox = messageBox;
            /**
             * 登录
             * @type {{username: string, taxcode: string, password: string, captchaId: null, captchaWord: null}}
             */
            $scope.loginSystem = function (ls) {
                console.info('--进来了--');
                // if (!ls.$valid) {
                //     console.info('--进入到方法了--');
                //     return;
                // }
                var fd = '';
                var formObj = {
                    // 'username': encryptByDES($scope.credentials.username, $scope.credentials.randrom),
                    // 'password': encryptByDES($scope.credentials.password, $scope.credentials.randrom)
                    'username': $scope.credentials.username,
                    'password': $scope.credentials.password

                };
                angular.forEach(formObj, function (value, key) {
                    fd += key + '=' + value + '&'
                });
                console.info('--fd--', fd);
                fd = fd.substr(0, fd.length - 1);
                console.info('--截取后的fd--', fd);
                $http.post(girderConfig.baseUrl + 'api/enterprise/usernamepassword/login', fd,
                    {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                            'captchaWord': $scope.credentials.captchaWord,
                            'captchaId': $scope.credentials.captchaId,
                        }
                    }
                ).success(function (data) {
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
                } catch (e) {
                    // alert(" 1 首先在本页下载补丁进行安装\n 2 降低您的IE级别为中或低 \n 3 如以上问题没有解决请联系江苏CA客服 025-833393888");
                }
            };
        }]);
/**
 * Created by y_zhang.neu on 2015/11/13.
 * 天津企业相关服务，暂时只设定功能登录，待后期增加功能可在此处增加服务
 */
'use strict';
angular.module('authserver.enterprise')
    .factory('enterpriseService', ['$http', '$log', '$resource', function ($http, $log, $resource) {

        var factory = {};

        return factory;

    }]);
/**
 * @author y_zhang.neu
 * @time 2016.10.31
 * @classDescription 企业前台路由
 */
angular.module('authserver.enterprise').config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        // 登录
        .when('/enterpriselogin', {
            //查询压缩后的路径
            templateUrl: 'modules/enterprise/views/login.html',
            controller: 'enterpriseCtrl'
        })
        .when('/bxenterpriselogin', {// 哈尔滨
            //查询压缩后的路径
            templateUrl: 'modules/enterprise/views/bxlogin.html',
            controller: 'bxenterpriseCtrl'
        })
}]);
/**
 * login controller
 * author:y_zhang.neu
 */
'use strict';
angular.module('authserver.enterprise')
    .controller('bxenterpriseCtrl', ['$rootScope', '$scope', '$location', '$log', 'CaptchaService', 'enterpriseService', 'messageBox', '$http', 'girderConfig', 'XSRFTokenService',
        function ($rootScope, $scope, $location, $log, CaptchaService, enterpriseService, messageBox, $http, girderConfig, XSRFTokenService) {

            //messagebox
            $scope.messageBox = messageBox;
            /**
             * 登录
             * @type {{username: string, taxcode: string, password: string, captchaId: null, captchaWord: null}}
             */
            $scope.loginSystem = function (ls) {
                console.info('--进来了--');
                // if (!ls.$valid) {
                //     console.info('--进入到方法了--');
                //     return;
                // }
                var fd = '';
                var formObj = {
                    // 'username': encryptByDES($scope.credentials.username, $scope.credentials.randrom),
                    // 'password': encryptByDES($scope.credentials.password, $scope.credentials.randrom)
                    'username': $scope.credentials.username,
                     'password': $scope.credentials.password

                };
                angular.forEach(formObj, function (value, key) {
                    fd += key + '=' + value + '&'
                });
                console.info('--fd--', fd);
                fd = fd.substr(0, fd.length - 1);
                console.info('--截取后的fd--', fd);
                $http.post(girderConfig.baseUrl + 'api/enterprise/usernamepassword/login', fd,
                    {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                            'captchaWord': $scope.credentials.captchaWord,
                            'captchaId': $scope.credentials.captchaId
                        }
                    }
                ).success(function (data) {
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


/**
 * Created by y_zhang.neu on 2015/11/13.
 * 天津企业相关服务，暂时只设定功能登录，待后期增加功能可在此处增加服务
 */
'use strict';
angular.module('authserver.ca')
    .factory('caService', ['$http', '$resource', 'messageBox', function ($http, $resource, messageBox) {

        var factory = {};

        return factory;
    }]);
/**
 * @author y_zhang.neu
 * @time 2016.10.31
 * @classDescription 企业前台路由
 */
angular.module('authserver.ca').config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        // 登录
        .when('/calogin', {
            //查询压缩后的路径
            templateUrl: 'modules/ca/views/login.html',
            controller: 'caCtrl'
        })
}]);
var $_$onUsbKeyChangeCallBackFunc = null;

// set custom alert function
function SetAlertFunction(custom_alert) {
    $_$XTXAlert = custom_alert;
}

function $checkBrowserISIE() {
    return (!!window.ActiveXObject || 'ActiveXObject' in window) ? true : false;
}


//usbkey change default callback function
function $OnUsbKeyChange() {
    // console.info("gxca usb change");
    if (typeof $_$onUsbKeyChangeCallBackFunc == 'function') {
        $_$onUsbKeyChangeCallBackFunc();
    }
}

// IE11 attach event
function $AttachIE11OnUSBKeychangeEvent(strObjName) {
    var handler = document.createElement("script");
    handler.setAttribute("for", strObjName);
    handler.setAttribute("event", "OnUsbKeyChange");
    handler.appendChild(document.createTextNode("$OnUsbKeyChange()"));
    document.body.appendChild(handler);
}

//load a control
function $LoadControl(CLSID, ctlName, testFuncName, addEvent) {
    var pluginDiv = document.getElementById("pluginDiv" + ctlName);
    if (pluginDiv) {
        return true;
    }
    pluginDiv = document.createElement("div");
    pluginDiv.id = "pluginDiv" + ctlName;
    document.body.appendChild(pluginDiv);

    try {
        if ($checkBrowserISIE()) {  // IE
            pluginDiv.innerHTML = '<object id="' + ctlName + '" classid="CLSID:' + CLSID + '" style="HEIGHT:0px; WIDTH:0px"></object>';
            if (addEvent) {
                var clt = eval(ctlName);
                if (clt.attachEvent) {
                    clt.attachEvent("OnUsbKeyChange", $OnUsbKeyChange);
                } else {// IE11 not support attachEvent, and addEventListener do not work well, so addEvent ourself
                    $AttachIE11OnUSBKeychangeEvent(ctlName);
                }
            }
        } else {
            var chromeVersion = window.navigator.userAgent.match(/Chrome\/(\d+)\./);
            if (chromeVersion && chromeVersion[1]) {
                if (parseInt(chromeVersion[1], 10) >= 42) { // not support npapi return false
                    document.body.removeChild(pluginDiv);
                    pluginDiv.innerHTML = "";
                    pluginDiv = null;
                    return false;
                }
            }

            if (addEvent) {
                pluginDiv.innerHTML = '<embed id=' + ctlName + ' type=application/x-xtx-axhost clsid={' + CLSID + '} event_OnUsbkeyChange=$OnUsbKeyChange width=0 height=0 />';
            } else {
                pluginDiv.innerHTML = '<embed id=' + ctlName + ' type=application/x-xtx-axhost clsid={' + CLSID + '} width=0 height=0 />';
            }
        }

        if (testFuncName != null && testFuncName != "" && eval(ctlName + "." + testFuncName) == undefined) {
            document.body.removeChild(pluginDiv);
            pluginDiv.innerHTML = "";
            pluginDiv = null;
            return false;
        }
        return true;
    } catch (e) {
        document.body.removeChild(pluginDiv);
        pluginDiv.innerHTML = "";
        pluginDiv = null;
        return false;
    }
}

function $XTXAlert(strMsg) {
    if (typeof $_$XTXAlert == 'function') {
        $_$XTXAlert(strMsg);
    } else {
        alert(strMsg);
    }
}

function $myOKRtnFunc(retVal, cb, ctx) {
    if (typeof cb == 'function') {
        var retObj = {retVal: retVal, ctx: ctx};
        cb(retObj);
    }
    return retVal;
}

//XTXAppCOM class
function CreateXTXAppObject() {
    var bOK = $LoadControl("3F367B74-92D9-4C5E-AB93-234F8A91D5E6", "XTXAPP", "SOF_GetVersion()", true);
    if (!bOK) {
        return null;
    }

    var o = new Object();

	o.SOF_GetCertInfoByOid = function(strCert, strOID, cb, ctx) {
        var ret = XTXAPP.SOF_GetCertInfoByOid(strCert, strOID);
        return $myOKRtnFunc(ret, cb, ctx);
    };
	
    o.SOF_GetCertInfo = function (Cert, type, cb, ctx) {
        var ret = XTXAPP.SOF_GetCertInfo(Cert, type);
        return $myOKRtnFunc(ret, cb, ctx);
    }

    o.SOF_GetUserList = function (cb, ctx) {
        var ret = XTXAPP.SOF_GetUserList();
        return $myOKRtnFunc(ret, cb, ctx);
    }

    o.SOF_SignData = function (CertID, InData, cb, ctx) {
        var ret = XTXAPP.SOF_SignData(CertID, InData);
        return $myOKRtnFunc(ret, cb, ctx);
    }

    o.SOF_SignFile = function (CertID, InFile, cb, ctx) {
        var ret = XTXAPP.SOF_SignFile(CertID, InFile);
        return $myOKRtnFunc(ret, cb, ctx);
    }

    o.SOF_ExportUserCert = function (CertID, cb, ctx) {
        var ret = XTXAPP.SOF_ExportUserCert(CertID);
        return $myOKRtnFunc(ret, cb, ctx);
    }
	
	o.SOF_Login = function(CertID,PassWd,cb,ctx){
		var ret = XTXAPP.SOF_Login(CertID,PassWd);
		return $myOKRtnFunc(ret, cb, ctx);
	}
	
	o.SOF_GetPinRetryCount = function(CertID,cb,ctx){
		var ret = XTXAPP.SOF_GetPinRetryCount(CertID);
		return $myOKRtnFunc(ret, cb, ctx);
	}

    return o;
}


//webSocket client class
function CreateWebSocketObject(myonopen, myonerror) {

    var o = new Object();


    o.ws_obj = null;
    o.ws_heartbeat_id = 0;
    o.ws_queue_id = 0; // call_cmd_id
    o.ws_queue_list = {};  // call_cmd_id callback queue
    o.ws_queue_ctx = {};
    o.xtx_version = "";

    o.load_websocket = function () {

        var ws_url = "ws://127.0.0.1:21051/xtxapp/";
        try {
			if(window.g_ws && window.g_ws.close){
				window.g_ws.close();
			}
            o.ws_obj = new WebSocket(ws_url);
			window.g_ws = o.ws_obj;
        } catch (e) {
            console.log(e);
            return false;
        }

        o.ws_queue_list["onUsbkeyChange"] = $OnUsbKeyChange;

        o.ws_obj.onopen = function (evt) {
            if (myonopen) {
                myonopen();
            }

        };

        o.ws_obj.onerror = function (evt) {
            if (myonerror) {
                myonerror();
            }
        };

        o.ws_obj.onclose = function (evt) {

        };


        o.ws_obj.onmessage = function (evt) {

            var res = JSON.parse(evt.data);
            if (res['set-cookie']) {
                document.cookie = res['set-cookie'];
            }

            //登录失败
            if (res['loginError']) {
                alert(res['loginError']);
            }

            var call_cmd_id = res['call_cmd_id'];
            if (!call_cmd_id) {
                return;
            }

            var execFunc = o.ws_queue_list[call_cmd_id];
            if (typeof(execFunc) != 'function') {
                return;
            }

            var ctx = o.ws_queue_ctx[res['call_cmd_id']];
            ctx = ctx || {returnType: "string"};

            var ret;
            if (ctx.returnType == "bool") {
                ret = res.retVal == "true" ? true : false;
            }
            else if (ctx.returnType == "number") {
                ret = Number(res.retVal);
            }
            else {
                ret = res.retVal;
            }
            var retObj = {retVal: ret, ctx: ctx};

            execFunc(retObj);

            if (res['call_cmd_id'] != "onUsbkeyChange") {
                delete o.ws_queue_list[res['call_cmd_id']];
            }
            delete o.ws_queue_ctx[res['call_cmd_id']];

        };

        return true;
    };


    o.sendMessage = function (sendMsg) {
        if (o.ws_obj.readyState == WebSocket.OPEN) {
            o.ws_obj.send(JSON.stringify(sendMsg));
        } else {
            setTimeout(function () {
                if (sendMsg.count) {
                    sendMsg.count++;
                    if (sendMsg.count === 4) {
                        return;
                    }
                }
                else {
                    sendMsg.count = 1;
                }
                o.sendMessage(sendMsg);
            }, 500);
            console.log("Can't connect to WebSocket server!");
        }
    };

    o.callMethod = function (strMethodName, cb, ctx, returnType, argsArray) {
        o.ws_queue_id++;
        if (typeof(cb) == 'function') {
            o.ws_queue_list['i_' + o.ws_queue_id] = cb;
            ctx = ctx || {};
            ctx.returnType = returnType;
            o.ws_queue_ctx['i_' + o.ws_queue_id] = ctx;
        }

        var sendArray = {};
        sendArray['cookie'] = document.cookie;
        sendArray['xtx_func_name'] = strMethodName;
        sendArray['call_cmd_id'] = 'i_' + o.ws_queue_id;


        if (arguments.length > 4) {
            sendArray["param"] = argsArray;
        }
        o.sendMessage(sendArray);

    };
	
	o.SOF_GetCertInfoByOid = function(strCert, strOID, cb, ctx) {
        var paramArray = [strCert, strOID];
        o.callMethod('SOF_GetCertInfoByOid', cb, ctx, "string", paramArray);
    };

    o.SOF_GetCertInfo = function (Cert, type, cb, ctx) {
        var paramArray = [Cert, type];
        o.callMethod('SOF_GetCertInfo', cb, ctx, "string", paramArray);
    }

    o.SOF_GetUserList = function (cb, ctx) {
        var paramArray = [];
        o.callMethod('SOF_GetUserList', cb, ctx, "string", paramArray);
    }

    o.SOF_SignData = function (CertID, InData, cb, ctx) {
        var paramArray = [CertID, InData];
        o.callMethod('SOF_SignData', cb, ctx, "string", paramArray);
    }

    o.SOF_SignFile = function (CertID, InFile, cb, ctx) {
        var paramArray = [CertID, InFile];
        o.callMethod('SOF_SignFile', cb, ctx, "string", paramArray);
    }

    o.SOF_ExportUserCert = function (CertID, cb, ctx) {
        var paramArray = [CertID];
        o.callMethod('SOF_ExportUserCert', cb, ctx, "string", paramArray);
    }

	o.SOF_Login = function(CertID,PassWd,cb,ctx){
		var paramArray = [CertID,PassWd];
		o.callMethod('SOF_Login', cb, ctx, "bool", paramArray);
	}
	
	o.SOF_GetPinRetryCount = function(CertID,cb,ctx){
		var paramArray = [CertID];
		o.callMethod('SOF_GetPinRetryCount', cb, ctx, "number", paramArray);
	}

    if (!o.load_websocket()) {
        return null;
    }
    return o;
}


   


/**
根据浏览器创建WGUKey控件
**/
(function () {

    if (typeof (WGUKey) == 'undefined') {

        var msie = /msie/.test(navigator.userAgent.toLowerCase()) || /rv:\d+\.0\S+\s+like\sgecko/.test(navigator.userAgent.toLowerCase());

        if (msie) {
            // IE浏览器
            window.WGUKey = document.createElement('OBJECT');
            WGUKey.id = 'WGUKey';
            WGUKey.data = 'data:application/x-oleobject;base64,7rU6JZ20LkCKhnD/kuq4nxAHAADYEwAA2BMAAA==';
            WGUKey.classid = 'clsid:253AB5EE-B49D-402E-8A86-70FF92EAB89F';
            WGUKey.VIEWASTEXT = 'VIEWASTEXT';
            var headElem = document.getElementsByTagName('head')[0];
            headElem.insertBefore(WGUKey, headElem.childNodes[0]);
        }
        else {

            function addControl() {
                if (typeof (WGUKey) != 'undefined') {
                    clearInterval(addControlInterval);
                    return;
                }

                if (document.body && document.body.childNodes) {
                    // FF、Chrome浏览器
                    window.WGUKey = document.createElement('OBJECT');
                    WGUKey.id = 'WGUKey';
                    WGUKey.setAttribute('TYPE', 'WallGreat-x-itst-activex');
                    WGUKey.data = 'data:application/x-oleobject;base64,7rU6JZ20LkCKhnD/kuq4nxAHAADYEwAA2BMAAA==';
                    WGUKey.setAttribute('clsid', '{253AB5EE-B49D-402E-8A86-70FF92EAB89F}');

                    WGUKey.setAttribute('progid', 'WallGreatUKey.UKey');
                    WGUKey.setAttribute('viewastext', 'viewastext');

                    WGUKey.style.width = 0;
                    WGUKey.style.height = 0;
                    WGUKey.style.display = 'none';
                    document.body.insertBefore(WGUKey, document.body.childNodes[0]);
                }
            }

            addControl();
            var addControlInterval = setInterval(addControl, 1);
        }

    }

})();






//获取签名证书
function getCertData(pszContainerName) {
    var b = ReadCertData(1, pszContainerName);
    return b == false ?  "" : b;
}

function trim(str, is_global) {
    var result;
    result = str.replace(/(^\s+)|(\s+$)/g, "");
    if (is_global.toLowerCase() == "g") {
        result = result.replace(/\s/g, "");
    }
    return result;
}



function utf16to8(str) {
    var out, i, len, c;

    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}

function utf8to16(str) {
    var out, i, len, c;
    var char2, char3;

    out = "";
    len = str.length;
    i = 0;
    while (i < len) {
        c = str.charCodeAt(i++);
        switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                // 0xxxxxxx
                out += str.charAt(i - 1);
                break;
            case 12:
            case 13:
                // 110x xxxx 10xx xxxx
                char2 = str.charCodeAt(i++);
                out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                break;
            case 14:
                // 1110 xxxx 10xx xxxx 10xx xxxx
                char2 = str.charCodeAt(i++);
                char3 = str.charCodeAt(i++);
                out += String.fromCharCode(((c & 0x0F) << 12) |
					((char2 & 0x3F) << 6) |
					((char3 & 0x3F) << 0));
                break;
        }
    }

    return out;
}
var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
	52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
	15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);

function base64encode(str) {
    var out, i, len;
    var c1, c2, c3;

    len = str.length;
    i = 0;
    out = "";
    while (i < len) {
        c1 = str.charCodeAt(i++) & 0xff;
        if (i == len) {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt((c1 & 0x3) << 4);
            out += "==";
            break;
        }
        c2 = str.charCodeAt(i++);
        if (i == len) {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt((c2 & 0xF) << 2);
            out += "=";
            break;
        }
        c3 = str.charCodeAt(i++);
        out += base64EncodeChars.charAt(c1 >> 2);
        out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
        out += base64EncodeChars.charAt(c3 & 0x3F);
    }
    return out;
}

function base64decode(str) {
    //alert(str);
    //alert(str.length);
    var c1, c2, c3, c4;
    var i, len, out;

    len = str.length;
    //alert(len);
    i = 0;
    out = "";

    while (i < len) {
        /* c1 */
        do {
            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while (i < len && c1 == -1);
        if (c1 == -1)
            break;

        /* c2 */
        do {
            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while (i < len && c2 == -1);
        if (c2 == -1)
            break;

        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

        /* c3 */
        do {
            c3 = str.charCodeAt(i++) & 0xff;
            if (c3 == 61)
                return out;
            c3 = base64DecodeChars[c3];
        } while (i < len && c3 == -1);
        if (c3 == -1)
            break;

        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

        /* c4 */
        do {
            c4 = str.charCodeAt(i++) & 0xff;
            if (c4 == 61)
                return out;
            c4 = base64DecodeChars[c4];
        } while (i < len && c4 == -1);
        if (c4 == -1)
            break;
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
    }
    return out;
}
var domain = "http://127.0.0.1:64737";

window.WallGreatUKey = {
    //连接key
    ConnectUK: function (keyConnectType, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/ConnectUK",
            async: false,
            dataType: "json",
            data: {
                "UKeyName": keyConnectType,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回成功标识
            }
        });

        return ajaxResult.responseText;
    },


    //读取证书数据
    UKReadCertData: function (dwCertFlag, pszContainerName, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKReadCertData",
            async: false,
            dataType: "json",
            data: {
                "CertFlag": dwCertFlag,
                "ContainerName": pszContainerName,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                // jsondata.Data1;  返回证书数据
            }
        });
        return ajaxResult.responseText;
    },


    //获取证书信息
    UKGetUkeyCertInfo: function (dwCertFlag, dataFlag, pszContainerName, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKGetUkeyCertInfo",
            async: false,
            dataType: "json",
            data: {
                "CertFlag": dwCertFlag,
                "DataFlag": dataFlag,
                "ContainerName": pszContainerName,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回证书数据
            }
        });
        return ajaxResult.responseText;
    },
    //解析证书扩展项
    UKGetCertExtensions: function (szCert, szOID, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKGetCertExtensions",
            async: false,
            dataType: "json",
            data: {
                "Cert": szCert,
                "OID": szOID,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回扩展项数据
            }
        });
        return ajaxResult.responseText;
    },
    //数据签名
    UKSignData: function (pszContainerName, inData, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: 'POST',
            url: domain + "/UKSignData",
            async: false,
            data: {
                "ContainerName": pszContainerName,
                "InData": inData,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                // jsondata.Data1;  返回证书数据
            }
        });
        return ajaxResult.responseText;
    },
    //附件签名
    UKSignFileData: function (pszContainerName, inData, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: 'POST',
            url: domain + "/UKSignFileData",
            async: false,
            data: {
                "ContainerName": pszContainerName,
                "InData": inData,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                // jsondata.Data1;  返回证书数据
            }
        });
        return ajaxResult.responseText;
    },
    //口令验证
    UKVerifyPin: function (szUserPin, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKVerifyPin",
            async: false,
            dataType: "json",
            data: {
                "szUserPin": szUserPin,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回成功标识
            }
        });
        return ajaxResult.responseText;
    },
    //验证签名
    UKVerifyData: function (signCert, signData, destdata, nCertFlag, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: 'POST',
            url: domain + "/UKVerifyData",
            async: false,
            data: {
                "CertData": signCert,
                "SrcData": signData,
                "DestData": destdata,
                "nCertFlag": nCertFlag,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回成功标识
            }
        });
        return ajaxResult.responseText;
    },
    //附件验签
    UKVerifyFileData: function (signCert, signData, destdata, nCertFlag, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: 'POST',
            url: domain + "/UKVerifyFileData",
            async: false,
            data: {
                "CertData": signCert,
                "SrcData": signData,
                "DestData": destdata,
                "nCertFlag": nCertFlag,
                "SessionId": currentSessionKey
            },
            success: function (data) {
                //返回成功标识
            }
        });
        return ajaxResult.responseText;
    },
    //导出SM2公钥
    UKExportSM2Key: function (dwCertFlag, pszContainerName, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKExportSM2Key",
            async: false,
            dataType: "json",
            data: { "Flag": dwCertFlag, "ContainerName": pszContainerName, "SessionId": currentSessionKey },
            success: function (data) {
                //返回证书数据
            }
        });
        return ajaxResult.responseText;
    },
    //导出RSA公钥
    UKExportRSAKey: function (dwCertFlag, pszContainerName, currentSessionKey) {
        var ajaxResult = $.ajax({
            type: "POST",
            url: domain + "/UKExportRSAKey",
            async: false,
            dataType: "json",
            data: { "Flag": dwCertFlag, "ContainerName": pszContainerName, "SessionId": currentSessionKey },
            success: function (data) {
                //返回证书数据
            }
        });
        return ajaxResult.responseText;
    }
}



function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    } else {
        return null;
    }
}

function setCookie(name, value) {
    var Days = 1;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

function getRandSeesionKey() {
    var key = "";
    for (var i = 1; i <= 32; i++) {
        var n = Math.floor(Math.random() * 16.0).toString(16);
        key += n;
    }

    return key;
}

//删除cookie
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

function JsonFlag(data) {
    if (data != 'undefined') {
        var jsondata = eval('(' + data + ')');
        if (jsondata.result == "true") {
            return true;
        } else {
            return false;
        }
    } else
        return false;
}


function JsonData(data) {
    if (data != 'undefined') {
        var jsondata = eval('(' + data + ')');
        if (jsondata.result == "true") {
            return jsondata.Data1;
        } else {
            return "";
        }
    } else
        return "";
}

//是否是IE浏览器
function isIE() {
    var msie = /msie/.test(navigator.userAgent.toLowerCase()) || /rv:\d+\.0\S+\s+like\sgecko/.test(navigator.userAgent.toLowerCase());

    if (msie) {
        // IE浏览器
        return true;
    } else {
        return false;
    }
}



//以下是应用集成时调用的接口
function Connect(ConnectName) {
    var Mes = false;
    try {
        if (isIE()) {
            Mes = WGUKey.ConnectUK(ConnectName);

        } else {
            var currentSessionKey = getCookie("SessionKey");
            if (currentSessionKey == "" || currentSessionKey == null) {
                currentSessionKey = getRandSeesionKey();
                setCookie("SessionKey", currentSessionKey);
            }
            var b = WallGreatUKey.ConnectUK(ConnectName, currentSessionKey);
            var jsonB = eval('(' + b + ')');
            if (jsonB.result == "true") {
                Mes = true;
            }
        }
    }
    catch (err) {
        //       alert("连接SSKEY异常");
        return "err";
    }
    return Mes;
}


/**
* 证书解析
*/
function GetCertInfo(CertFlag, DataFlag, ContainerName) {
    if (isIE()) {
        var res = WGUKey.UKGetUkeyCertInfo(CertFlag, DataFlag, ContainerName);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKGetUkeyCertInfo(CertFlag, DataFlag, ContainerName, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return utf8to16(base64decode(jsonB.Data1));
        }
    }
    return false;
}

/**
* 读取证书 dwCertFlag 1 签名 2 加密
*/
function ReadCertData(CertFlag, ContainerName) {
    if (isIE()) {
        var res = WGUKey.UKReadCertData(CertFlag, ContainerName);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKReadCertData(CertFlag, ContainerName, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}


/**
* 签名
*/
function SignData(ContainerName, InData) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKSignData(ContainerName, InData);
        if (Mes == true)
            return WGUKey.Data1;

    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        InData = base64encode(utf16to8(InData));

        var b = WallGreatUKey.UKSignData(ContainerName, InData, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}

/**
* 验证签名
*/
function VerifyData(SignCert, InData, SignData) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKVerifyData(SignCert, InData, SignData, 0);
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        InData = base64encode(utf16to8(InData));

        var b = WallGreatUKey.UKVerifyData(SignCert, InData, SignData, 0, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            Mes = true;
        }
    }
    return Mes;
}

/**
* 附件签名
*/
function SignFile(ContainerName, FilePath) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKSignFileData(ContainerName, FilePath);
        if (Mes == true)
            return WGUKey.Data1;

    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        FilePath = base64encode(utf16to8(FilePath));

        var b = WallGreatUKey.UKSignFileData(ContainerName, FilePath, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}
/**
* 附件验签
*/
function VerifyFile(SignCert, FilePath, SignData) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKVerifyFileData(SignCert, FilePath, SignData, 0);
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        FilePath = base64encode(utf16to8(FilePath));

        var b = WallGreatUKey.UKVerifyFileData(SignCert, FilePath, SignData, 0, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            Mes = true;
        }
    }
    return Mes;
}


/**
*导SM2公钥
**/
function ExportSM2Key(CertFlag, ContainerName) {
    if (isIE()) {
        var res = WGUKey.UKExportSM2Key(CertFlag, ContainerName);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKExportSM2Key(CertFlag, ContainerName, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}


/**
*导RSA公钥
**/
function ExportRSAKey(CertFlag, ContainerName) {
    if (isIE()) {
        var res = WGUKey.UKExportRSAKey(CertFlag, ContainerName);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKExportRSAKey(CertFlag, ContainerName, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return jsonB.Data1;
        }
    }
    return false;
}


/**
* 证书解析扩展项
*/
function GetCertExtensions(CertData, ExtnsID) {
    if (isIE()) {
        var res = WGUKey.UKGetCertExtensions(CertData, ExtnsID);
        if (res == true) {
            return WGUKey.Data1;
        }
    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKGetCertExtensions(CertData, ExtnsID, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            return utf8to16(base64decode(jsonB.Data1));
        }
    }
    return false;
}
/**
* 验证口令
*/
function verifyUPin(szPin) {
    var Mes = false;
    if (isIE()) {
        Mes = WGUKey.UKVerifyPin(szPin);
        if (Mes == false) {
            Mes = WGUKey.Data1;
        }

    } else {
        var currentSessionKey = getCookie("SessionKey");
        if (currentSessionKey == "" || currentSessionKey == null) {
            currentSessionKey = getRandSeesionKey();
            setCookie("SessionKey", currentSessionKey);
        }
        var b = WallGreatUKey.UKVerifyPin(szPin, currentSessionKey);
        var jsonB = eval('(' + b + ')');
        if (jsonB.result == "true") {
            Mes = true;
        } else {
            Mes = utf8to16(base64decode(jsonB.Data1));
        }
    }
    return Mes;
	}
var $NEUCA = {
    GX_API: (function () {
        /**
         * 初始化
         * @param succFn 成功回调
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"},errorCode=0 未安装驱动，errorCode=1 未插入u key
         */

        var CurrentObj;

        function initCAObject(succFn, failFn) {
            CurrentObj = CreateXTXAppObject();
            // alert(CurrentObj)
            if (CurrentObj) {
                if (succFn) {
                    succFn();
                }
                return;
            }

            CurrentObj = CreateWebSocketObject(succFn, failFn);
            if (!CurrentObj && failFn) {
                failFn();
            }

        }


        /**
         * 加载证书
         * @param succFn 出参 证书数组[]
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function loadCert(succFn, failFn) {
            CurrentObj.SOF_GetUserList(function (data) {
                var ret = data.retVal;
                if (!ret) {
                    succFn([]);
                    return;
                }

                var retArray = [];
                var cert_array = ret.split("&&&");
                for (var i = 0; i < cert_array.length; i++) {
                    var content = cert_array[i];
                    if (!content) {
                        continue;
                    }

                    cert_info = content.split("||");
                    if (cert_info.length < 2) {
                        continue;
                    }

                    var obj = {};
                    obj.certid = cert_info[1];
                    obj.certcn = cert_info[0];
                    retArray.push(obj);
                }

                if (retArray.length == 0) {
                    succFn(retArray);
                    return;
                }

                var cert_number = retArray.length;
                var retCertArray = [];
                for (var i = 0; i < retArray.length; i++) {
                    CurrentObj.SOF_ExportUserCert(retArray[i].certid, function (data) {
                        var cert_content = data.retVal;
                        if (cert_content) {
                            CurrentObj.SOF_GetCertInfo(cert_content, 8, function (data) {
                                var issuer_cn = data.retVal;
                                if (issuer_cn == "Guangxi SM2 CA" || issuer_cn == "Guangxi CA") {
                                    retCertArray.push(data.ctx);
                                }

                                if (--cert_number == 0) {
                                    succFn(retCertArray);
                                    return;
                                }
                            }, data.ctx);
                        } else {
                            if (--cert_number == 0) {
                                succFn(retCertArray);
                                return;
                            }
                        }
                    }, retArray[i]);
                }
            });
        }


        /**
         * 加载单位编号
         * @param succFn 出参 单位编号数组[]
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function loadNumber(certID, succFn, failFn) {
            CurrentObj.SOF_ExportUserCert(certID, function (data) {
                var cert = data.retVal;
                if (!cert) {
                    if (failFn) {
                        var obj = {};
                        obj.errorCode = "1";
                        obj.errorMsg = "获取证书失败";
                        failFn(obj);
                        return;
                    }
                }

                CurrentObj.SOF_GetCertInfoByOid(cert, "1.2.156.112562.6.1.2", function (data) {
                    var ret = data.retVal;
                    if (ret) {
                        succFn(ret.split("&"));
                        return;
                    }


                    CurrentObj.SOF_GetCertInfo(cert, 35, function (data) {
                        var ret = data.retVal;
                        if (!ret) {
                            if (failFn) {
                                var obj = {};
                                obj.errorCode = "2";
                                obj.errorMsg = "获取证书dn失败";
                                failFn(obj);
                                return;
                            }


                        }

                        if (!succFn) {
                            return;
                        }

                        var key_value_list = ret.split(",");
                        for (var i = 0; i < key_value_list.length; i++) {
                            var key_value = key_value_list[i].split("=");
                            if (key_value.length < 2) {
                                continue;
                            }

                            var key = key_value[0];
                            var value = key_value[1];
                            if (key == "surname" || key == "OU") {
                                succFn(value.split("&"));
                                return;
                            }
                        }

                        if (failFn) {
                            var obj = {};
                            obj.errorCode = "3";
                            obj.errorMsg = "解析证书dn失败";
                            failFn(obj);
                            return;
                        }

                    });

                })


            });
        }

        /**
         * 数据签名
         * @param data 原文
         * @param succFn 成功回调 出参签名后的值
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function signData(certID, data, succFn, failFn) {
            CurrentObj.SOF_SignData(certID, data, function (data) {
                var ret = data.retVal;
                if (ret) {
                    succFn(ret);
                    return;
                }
                failFn(ret);
            });
        }

        /**
         * 附件签名
         * @param filePath 文件path
         * @param succFn 成功回调 出参签名后的值
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function signFile(certID, filePath, succFn, failFn) {
            CurrentObj.SOF_SignFile(certID, filePath, function (data) {
                var ret = data.retVal;
                if (ret) {
                    succFn(ret);
                    return;
                }
                failFn(ret);
            });
        }

        /**
         * 获取用户用户证书（验证需要的cert）
         * @param certId 证书唯一标识
         * @param succFn 出参 用户证书
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function exportUserCert(certId, succFn, failFn) {
            CurrentObj.SOF_ExportUserCert(certId, function (data) {
                var ret = data.retVal;
                if (ret) {
                    succFn(ret);
                    return;
                }
                failFn(ret);
            });
        }

        /**
         * 用户登录
         * @param certId 证书唯一标识
         * @param certId pin码
         * @param succFn 登录成功
         * @param failFn 登录失败 {errorCode:"0",errorMsg:"失败原因"}
         */
        function login(certId, pinPwd, succFn, failFn) {
            CurrentObj.SOF_Login(certId, pinPwd, function (data) {
                var ret = data.retVal;
                if (ret) {
                    succFn();
                } else {
                    CurrentObj.SOF_GetPinRetryCount(certId, function (data) {
                        var obj = {};
                        obj.errorCode = "1";
                        obj.errorMsg = "校验证书密码失败，密码剩余重试次数（" + data.retVal + "），请重新输入证书密码！";
                        if (failFn) {
                            failFn(obj);
                        }
                    });
                }
            });
        }

        return {
            initCAObject: initCAObject,
            loadCert: loadCert,
            loadNumber: loadNumber,
            signData: signData,
            signFile: signFile,
            exportUserCert: exportUserCert,
            login: login
        }
    })(),

    DF_API: (function () {

        /**
         * 初始化
         * @param succFn 成功回调
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"},errorCode=0 未安装驱动，errorCode=1 未插入u key
         */
        function initCAObject(succFn, failFn) {
            var retVal = false;
            var initErr = {};

            retVal = Connect("WALLGREAT");
            if (retVal === false) {
                initErr.errorCode = "0";
                initErr.errorMsg = "未插入uskey";
            } else if (retVal == "err") {
                initErr.errorCode = "1";
                initErr.errorMsg = "未安装驱动";
            } else {
                succFn();
                return;
            }

            failFn(initErr);
        }

        /**
         * 加载证书
         * @param succFn 出参 证书数组    [ {certid:"21313",certcn:"dasfaasfa"} ,{},.....]
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function loadCert(succFn, failFn) {
            var certArr = []
            var certData = {};
            var res = GetCertInfo(1, 1, "WALLGREATSM2");
            var cid = GetCertInfo(1, 5, "WALLGREATSM2");

            if (res != false && cid != false) {
                certData.certid = cid;
                certData.certcn = res;
                certArr.push(certData);
                succFn(certArr);
                return;

            }
            else {
                var certErr = {};
                certErr.errorCode = "0";
                certErr.errorMsg = "请确认uskey中是否有证书";
                failFn(certErr);
            }

        }

        /**
         * 加载单位编号
         * @param certId 证书唯一标识
         * @param succFn 出参 单位编号ou数组["123","234","445",....]
         * @param failFn 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function loadNumber(certId, succFn, failFn) {
            var numberData = [];

            var SM2szCertData = GetCertInfo(1, 18, "WALLGREATSM2");

            //如果是国密证书
            if (SM2szCertData == "sm3withsm2") {
                var certdata = getCertData("WALLGREATSM2");
                var res = GetCertExtensions(certdata, "1.34.56.876.3");

                //要求RSA证书更新过去的时候变成：20013336_20062546_20022222_20033333_20044444_20055555_20069666
                //res = "EmployeeID - 20013336&20062546&20022222&20033333&20044444&20055555&20069666";
                res = trim(res, "g");
                var arr = res.split("_");
                if (res != false) {
                    for (var i = 0; i < arr.length; i++) {
                        numberData.push(arr[i]);
                    }
                }
            } else {
                var certdata = getCertData("WALLGREAT");
                //RSA证书里面的OU字段，目前写入社保编号。
                var res = GetCertInfo(1, 12, "WALLGREAT");

                //res = "EmployeeID - 20013336&20062546&20022222&20033333&20044444&20055555&20069666";
                res = trim(res, "g");
                var arr = res.split("-");

                //SM2证书首先去掉EmployeeID -
                if (arr.length == 2) {
                    res = arr[1];
                } else {
                    res = false;
                }

                if (res != false) {
                    arr = res.split("&");
                    for (var i = 0; i < arr.length; i++) {
                        numberData.push(arr[i]);
                    }
                }
            }


            if (numberData.length > 0) {
                succFn(numberData);
            } else {
                var numberErr = {};
                numberErr.errorCode = "0";
                numberErr.errorMsg = "请确认uskey中是否有证书";
                failFn(numberErr)
            }
        }

        /**
         * 数据签名
         * @param certId 证书唯一标识
         * @param data 原文
         * @param succFn 成功回调 出参签名后的值
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function signData(certId, data, succFn, failFn) {
            var signErr = {};

            var SM2szCertData = GetCertInfo(1, 18, "WALLGREATSM2");

            if (data == "" || data == undefined) {
                signErr.errorCode = "0";
                signErr.errorMsg = "原文没有内容";
                failFn(signErr);
                return;
            } else {
                if (SM2szCertData == "sm3withsm2") {
                    var signData = SignData("WALLGREATSM2", data); //开始签名
                    if (signData == false) {
                        signErr.errorCode = "0";
                        signErr.errorMsg = "SSKey签名失败";
                        failFn(signErr);
                        return;
                    }

                    succFn(signData);
                    return;

                } else {
                    var RSAszCertData = getCertData("WALLGREAT");
                    if (RSAszCertData) {
                        var res = GetCertInfo(1, 0, "WALLGREAT");

                        var root = "DFCA Pubilc Root RSA";
                        if (res == root) {
                            var b = Connect("WALLGREATRSA");
                            var signData = SignData("WALLGREAT", data); //开始签名
                            if (signData == false) {
                                signErr.errorCode = "0";
                                signErr.errorMsg = "SSKey签名失败";
                                failFn(signErr);
                                return;
                            }

                            succFn(signData);
                            return;
                        } else {
                            signErr.errorCode = "0";
                            signErr.errorMsg = "该证书不是东方新诚信颁发的证书";
                            failFn(signErr);
                            return;
                        }
                    } else {
                        signErr.errorCode = "0";
                        signErr.errorMsg = "没有RSA证书";
                        failFn(signErr);
                        return;
                    }
                }

            }

        }

        /**
         * 附件签名
         * @param certId 证书唯一标识
         * @param filePath 文件path
         * @param succFn 成功回调 出参签名后的值
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function signFile(certId, filePath, succFn, failFn) {
            var signFileErr = {};

            var signFileData = "";
            var SM2szCertData = GetCertInfo(1, 18, "WALLGREATSM2");

            if (filePath == "" || filePath == undefined) {
                signFileErr.errorCode = "0";
                signFileErr.errorMsg = "文件路径没有内容";
                failFn(signFileErr);
                return;
            } else {
                if (SM2szCertData == "sm3withsm2") {
                    var signFileData = SignFile("WALLGREATSM2", filePath); //开始签名
                    if (signFileData == false) {
                        signFileErr.errorCode = "0";
                        signFileErr.errorMsg = "SSKey附件签名失败";
                        failFn(signFileErr);
                        return;
                    }

                    succFn(signFileData);
                    return;
                } else {
                    var b = Connect("WALLGREATRSA");
                    signFileData = SignFile("WALLGREAT", filePath); //开始签名
                    if (signFileData == false) {
                        signFileErr.errorCode = "0";
                        signFileErr.errorMsg = "SSKey附件签名失败";
                        failFn(signFileErr);
                        return;
                    }

                    succFn(signFileData);
                    return;
                }

            }

        }


        function exportUserCert(certId, succFn, failFn) {
            var keyErr = {};
            var keyData = "";
            var SM2szCertData = GetCertInfo(1, 18, "WALLGREATSM2");

            if (SM2szCertData == "sm3withsm2") {
                var ret = getCertData("WALLGREATSM2");
                if (ret === false) {
                    keyErr.errorCode = "0";
                    keyErr.errorMsg = "获取UKEY证书失败";
                    failFn(keyErr);
                    return;
                }
                keyData = ret;
                succFn(keyData);
                return;
            } else {
                var b = Connect("WALLGREATRSA");
                var ret = getCertData("WALLGREAT");
                if (ret === false) {
                    keyErr.errorCode = "0";
                    keyErr.errorMsg = "获取UKEY证书失败";
                    failFn(keyErr);
                    return;
                }
                keyData = ret;
                succFn(keyData);
                return;
            }

        }

        /**
         主动验证KEY  PIN 码  2018-10-12
         * @param userPin 输入的PIN码
         * @param succFn 成功回调 出参true
         * @param failFn 失败回调 出参: {errorCode:"0",errorMsg:"错误中文信息"}
         */
        function verifyUserPin(userPin, succFn, failFn) {
            var pinErr = {};
            if (userPin == "" || userPin == undefined) {
                pinErr.errorCode = "0";
                pinErr.errorMsg = "请输入用户PIN码";
                failFn(pinErr);
                return;
            } else {
                var isPin = verifyUPin(userPin);
                if (isPin === true) {
                    succFn();
                    return;
                } else {
                    pinErr.errorCode = "0";
                    pinErr.errorMsg = isPin;   //isPin验证错误信息
                    failFn(pinErr);
                    return;
                }
            }
        }

        /**
         * 用户登录
         * @param certId 证书唯一标识
         * @param certId pin码
         * @param succFn 登录成功
         * @param failFn 登录失败 {errorCode:"0",errorMsg:"失败原因"}
         */
        function login(certId, pinPwd, succFn, failFn) {
            verifyUserPin(pinPwd, succFn, failFn);
        }


        return {
            initCAObject: initCAObject,
            loadCert: loadCert,
            loadNumber: loadNumber,
            signData: signData,
            signFile: signFile,
            exportUserCert: exportUserCert,
            verifyUserPin: verifyUserPin,
            login: login
        }
    })()

};
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
/**
 */
'use strict';
angular.module('authserver.school')
    .factory('schoolService', ['$http', '$log', '$resource', function ($http, $log, $resource) {

        var factory = {};

        return factory;

    }]);
/**
 * @classDescription 学校前台路由
 */
angular.module('authserver.school').config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        // 登录
        .when('/siagentlogin', {
            //查询压缩后的路径
            templateUrl: 'modules/school/views/login.html',
            controller: 'schoolCtrl'
        })
}]);
/**
 * Created by xiaowzh on 2015/11/5.
 * 密码重置controller
 */
'use strict';
angular.module('authserver.expert')
    .controller('expertpasswordCtrl', ['CaptchaService', 'XSRFTokenService', '$rootScope', '$scope', '$location', '$log', 'IDCard', 'expertService', 'messageBox', '$http',
        function (CaptchaService, XSRFTokenService, $rootScope, $scope, $location, $log, IDCard, expertService, messageBox, $http) {
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
                expertService.mobileNumberGet($scope.passWordResetDetailDTO.name, $scope.passWordResetDetailDTO.idNumber).then(function (success) {
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
                expertService.restPasswordMethod($scope.MobileInfo, $scope.passWordResetDetailDTO).then(function () {
                    $log.info('密码重置成功！');
                    $location.path('/expertPassword_View');
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
                    expertService.mobileCodePostByPersonNumberxiaowzh($scope.passWordResetDetailDTO.idNumber, $scope.passWordResetDetailDTO.name, $scope.passWordResetDetailDTO.personNumber, $scope.captcha).then(function (success) {
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
/**
 * login controller
 * author:xiaowzh
 */
'use strict';
angular.module('authserver.expert')
    .controller('expertCtrl', ['$rootScope', 'XSRFTokenService', '$scope', '$location', '$log', 'CaptchaService', 'messageBox', '$cookies', '$cookieStore', 'girderConfig', '$http',
        function ($rootScope, XSRFTokenService, $scope, $location, $log, CaptchaService, messageBox, $cookies, $cookieStore, girderConfig, $http) {
            //messagebox
            $scope.messageBox = messageBox;

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
                    'username': encryptByDES($scope.personcredentials.idNum, $scope.personcredentials.randrom),
                    'password': encryptByDES($scope.personcredentials.password, $scope.personcredentials.randrom)
                };
                angular.forEach(formObj, function (value, key) {
                    fd += key + '=' + value + '&'
                });
                fd = fd.substr(0, fd.length - 1);
                $http.post(girderConfig.baseUrl + 'api/expert/login', fd,
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
/**
 * Created by xiaowzh on 2015/11/13.
 */
'use strict';
angular.module('authserver.expert')
    .factory('expertService', ['$http', '$log', '$resource', 'girderConfig', function ($http, $log, $resource, girderConfig) {
        var fatory = [];
        //重置密码URL
        var REST_PASSWORD_URL = girderConfig.baseUrl + 'ws/password/reset/expert';
        //发送短信URL
        var SEND_MOBILE_CODE_URL = girderConfig.baseUrl + 'captcha/sm';
        //修改手机号码URL
        var MOBILE_MODIFY_URL = girderConfig.baseUrl + 'ws/user/person/mobilenumber';
        //注册URL
        var PERSON_REGISTE_URL = girderConfig.baseUrl + 'ws/user/expert/active';
        //获取手机号URL
        var MOBILE_GET_URL = girderConfig.baseUrl + 'captcha/sm/idnumbername';
        //密码重置发送短信URL
        var SEND_MOBILE_BYNAME_URL = girderConfig.baseUrl + 'captcha/sm/byexpert';
        //根据手机号码发送短信
        var SEND_MOBILE_BYPHONE_URL = girderConfig.baseUrl + 'captcha/sm/web/';
        //获取密保问题列表
        var QUESTION_LIST = girderConfig.baseUrl + 'ws/question';
        //根据身份证获取密保问题列表
        var QUESTION_USER_LIST = girderConfig.baseUrl + 'ws/user/question';


        //发送密码重置短信获得验证码
        fatory.CodePostMethod = function (MobileDTO, captcha) {
            return $http({method: 'POST', url: SEND_MOBILE_CODE_URL, headers: captcha, data: MobileDTO});
        };

        //密码重置
        fatory.restPasswordMethod = function (passWordResetDetailDTO, mobileInfo) {
            //向PUT请求中定义headers

            return $http({method: 'PUT', url: REST_PASSWORD_URL, headers: passWordResetDetailDTO, data: mobileInfo});
        };
        //手机号码修改
        fatory.modifyMobileMethod = function (mobileDetails, mobileInfo) {
            //向PUT请求中定义headers
            return $http({method: 'PUT', url: MOBILE_MODIFY_URL, headers: mobileInfo, data: mobileDetails});
        };
        //注册用户
        fatory.registePerson = function (personUserDTO, mobileInfo) {

            return $http({method: 'POST', url: PERSON_REGISTE_URL, headers: mobileInfo, data: personUserDTO});

        };

        //获取手机号
        fatory.mobileNumberGet = function (name, idnumber) {
            $log.info('获取手机号', name, idnumber);
            return $http({
                method: 'POST',
                url: MOBILE_GET_URL,
                data: {mobilenumber: '', name: name, idNumber: idnumber}
            })
        };

        //获取手机号
        fatory.mobileCodePostByName = function (name, idnumber, captcha) {
            $log.info('发送验证码', name, idnumber);
            return $http({
                method: 'POST',
                url: SEND_MOBILE_BYNAME_URL,
                headers: captcha,
                data: {name: name, idNumber: idnumber}
            })
        };

        //根据手机号码发送短信
        fatory.mobileCodePostByPersonNumberxiaowzh = function (idNumber, name, personNumber, captcha) {
            $log.info('发送验证码', idNumber, name, personNumber, captcha);
            return $http({
                method: 'POST',
                url: SEND_MOBILE_BYNAME_URL,
                headers: captcha,
                data: {name: name, idNumber: idNumber, personNumber: personNumber}
            })
        };

        // 获取密保问题列表
        fatory.getQuestionList = function () {
            return $http({method: 'GET', url: QUESTION_LIST});
        };

        // 获取密保问题列表
        fatory.getQuestionListById = function (idNumber) {
            return $http({method: 'GET', url: QUESTION_USER_LIST + '/' + idNumber});
        };

        return fatory;
    }]);
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
/**
 * @author y_zhang.neu
 * time 2015.11.24
 * @classDescription 天津个人前台路由
 */
angular.module('authserver.expert')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
        // 登录
            .when('/expertlogin', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertlogin.html',
                controller: 'expertCtrl'
            })
            //注册
            .when('/expertRegister', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertregister.html',
                controller: 'expertregisterCtrl'
            })
            //注册成功
            .when('/expertRegister_View', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertregister_view.html'
            })
            //密码修改
            .when('/expertPassword', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertpassword.html',
                controller: 'expertpasswordCtrl'
            })
            //密码修改成功
            .when('/expertPassword_View', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertpassword_view.html'
            })
    }]);
// /**
//  * 单位激活controller
//  * @author y_zhang.neu
//  */
// 'use strict';
// angular.module('authserver.siagent')
//     .controller('siagentActiveCtrl', ['$scope', 'siagentService', 'messageBox','CaptchaService','$location','$log',
//         function ($scope, siagentService, messageBox,CaptchaService,$location,$log) {
//     	$scope.confirmPassword='';
//             $scope.messageBox = messageBox;
//             $scope.confirmPassword='';
//             $scope.protectDTO = {
//                 'birth': '',
//                 'motherName': '',
//                 'legal': '',
//                 'oldPwd': '123456',
//                 'newPwd': '',
//                 'companyNumber': ''
//             };
//             $scope.captcha = {
//                     'captchaUrl': '',
//                     'captchaId': '',
//                     'captchaWord': ''
//                 };
//             $scope.getCaptcha = function () {
//                 CaptchaService.getNextCaptcha().$promise.then(function (data) {
//                 	$scope.captchaUrl=data.getImageUrl();
//                   $scope.captcha.captchaUrl = data.getImageUrl();
//                   $scope.captcha.captchaId = data.id;
//                 });
//               };
//               $scope.getCaptcha();
//
//
//             $scope.userActive = function () {
//                 if($scope.protectDTO.newPwd.length<6){
//                     $scope.messageBox.showInfo('设置密码的位数不少于6位');
//                     return;
//                 }
//             	if(angular.element('#confirmPwd').hasClass("P_L_zc_tr")){
//              	   $scope.messageBox.showInfo('设置密码与重复密码不匹配');
//              	   return;
//                 }
//
//                 siagentService.activeUser($scope.protectDTO,$scope.captcha).then(function () {
//                 	$location.path('/siagentLogin');
//                     $scope.messageBox.showInfo('用户激活成功，请登录');
//                 }, function (err) {
//                     if (err.data.hasOwnProperty('detail')) {
//                         $scope.messageBox.showInfo('用户激活失败!' + err.data.detail);
//                     } else if (err.data.hasOwnProperty('message')) {
//                         $scope.messageBox.showInfo('用户激活失败!' + err.data.message);
//                     } else {
//                         $scope.messageBox.showInfo('用户激活失败！'+err.data);
//                     }
//                 });
//             }
//
//         }]);
// /**
//  * login controller
//  * author:y_zhang.neu
//  */
// 'use strict';
// angular.module('authserver.siagent')
//     .controller('siagentCtrl', ['$rootScope', '$scope', '$location', '$log', 'CaptchaService', 'siagentService', 'messageBox',
//         function ($rootScope, $scope, $location, $log, CaptchaService, siagentService, messageBox) {
//             //messagebox
//             $scope.messageBox = messageBox;
//             /**
//              * 登录
//              * @type {{username: string, taxcode: string, password: string, captchaId: null, captchaWord: null}}
//              */
//             $scope.credentials = {
//                 username: '',
//                 taxcode: '',
//                 password: '',
//                 captchaId: null,
//                 captchaWord: null
//             };
//             $scope.getCaptcha = function () {
//                 CaptchaService.getNextCaptcha().$promise.then(function (data) {
//                     $log.info('loginCtrl获取到验证码', data.id);
//                     $scope.captchaUrl = data.getImageUrl()+"?"+Math.random();
//                     $scope.credentials.captchaId = data.id;
//                 });
//             };
//             $scope.getCaptcha();
//             //添加到收藏夹
//             $scope.addFavorite = function () {
//                 try {
//                     window.external.addFavorite('http://221.180.249.108:12333/agentlogin', '沈阳市城镇居民医疗保险网上参保平台');
//                 }
//                 catch (e) {
//                     try {
//                         window.sidebar.addPanel('沈阳市城镇居民医疗保险网上参保平台', 'http://221.180.249.108:12333/agentlogin', "");
//                     }
//                     catch (e) {
//                         //跳转到添加收藏夹说明页
//                         $scope.messageBox.showInfo("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加\n\n收藏地址：http://221.180.249.108:12333/agentlogin");
//                     }
//                 }
//             };
//         }]);
// /**
//  * 密码设置controller
//  * @author y_zhang.neu
//  */
// 'use strict';
// angular.module('authserver.siagent')
//     .controller('siagentPwdCtrl', ['$scope', 'siagentService', 'messageBox','CaptchaService','$location',
//         function ($scope, siagentService, messageBox,CaptchaService,$location) {
//             $scope.messageBox = messageBox;
//             $scope.confirmPassword='';
//             $scope.protectDTO = {
//                 'birth': '',
//                 'motherName': '',
//                 'legal': '',
//                 'oldPwd': '',
//                 'newPwd': '',
//                 'companyNumber': ''
//             };
//             $scope.captcha = {
//                     'captchaUrl': '',
//                     'captchaId': '',
//                     'captchaWord': ''
//                 };
//             $scope.getCaptcha = function () {
//                 CaptchaService.getNextCaptcha().$promise.then(function (data) {
//                 	$scope.captchaUrl=data.getImageUrl();
//                   $scope.captcha.captchaUrl = data.getImageUrl();
//                   $scope.captcha.captchaId = data.id;
//                 });
//               };
//               $scope.getCaptcha();
//             $scope.pwdReset = function () {
//                 if($scope.protectDTO.newPwd.length<6){
//                     $scope.messageBox.showInfo('密码的位数不少于6位');
//                     return;
//                 }
//                 if(angular.element('#confirmPwd').hasClass("P_L_zc_tr")){
//                     $scope.messageBox.showInfo('设置密码与重复密码不匹配');
//                     return;
//                 }
//
//                 siagentService.restPasswordMethod($scope.protectDTO,$scope.captcha).then(function () {
//                 	$location.path('/siagentLogin');
//                     $scope.messageBox.showInfo('密码设置成功，请登录');
//                 }, function (err) {
//                     if (err.data.hasOwnProperty('detail')) {
//                         $scope.messageBox.showInfo('密码设置失败!' + err.data.detail);
//                     } else if (err.data.hasOwnProperty('message')) {
//                         $scope.messageBox.showInfo('密码设置失败!' + err.data.message);
//                     } else {
//                         $scope.messageBox.showInfo('密码设置失败！');
//                     }
//                 });
//             }
//         }]);
// /**
//  * Created by y_zhang.neu on 2015/11/13.
//  * 天津建筑相关服务，暂时只设定功能登录，待后期增加功能可在此处增加服务
//  */
// 'use strict';
// angular.module('authserver.siagent')
//     .factory('siagentService', ['$http', '$log', '$resource', function ($http, $log, $resource) {
//         var FACTORY = [];
//         //重置密码URL
//         var AGENT_REST_PASSWORD_URL = '/uaa/custom/password/reset';
//         //单位激活URL
//         var AGENT_ACTIVE_URL = '/uaa/custom/user/active';
//         //密码重置
//         FACTORY.restPasswordMethod = function (protectlDTO,headerInfo) {
//             //向PUT请求中定义headers
//             return $http({method: 'PUT', url: AGENT_REST_PASSWORD_URL,headers:headerInfo, data: protectlDTO});
//         };
//         //单位激活
//         FACTORY.activeUser = function (protectlDTO,headerInfo) {
//             return $http({method: 'PUT', url: AGENT_ACTIVE_URL,headers:headerInfo, data: protectlDTO});
//         };
//         return FACTORY;
//     }]);
// /**
//  * @author y_zhang.neu
//  * time 2015.11.24
//  * @classDescription 天津建筑前台路由
//  */
// angular.module('authserver.siagent').config(['$routeProvider', function ($routeProvider) {
//     $routeProvider
//     // 登录
//         .when('/siagentLogin', {
//             //查询压缩后的路径
//             templateUrl: 'modules/siagent/views/login.html',
//             controller: 'siagentCtrl'
//         })
//         //单位激活
//         .when('/siagentActive', {
//             //查询压缩后的路径
//             templateUrl: 'modules/siagent/views/active.html',
//             controller: 'siagentActiveCtrl'
//         })
//         //密码重置
//         .when('/siagentPwd', {
//             //查询压缩后的路径
//             templateUrl: 'modules/siagent/views/password.html',
//             controller: 'siagentPwdCtrl'
//         })
// }]);
angular.module('AuthUI').run(['$templateCache', function($templateCache) {
  'use strict';

  $templateCache.put('modules/ca/views/login.html',
    "<div class=\"L_bag\" id=\"caview\" ng-init=\"loginButton = '数据加载中。。。';initLogin()\"> <div class=\"L_three_enterprise\"> <div class=\"L_t_left\"></div> <div class=\"L_t_right\"> <form class=\"form-horizontal\" style=\"margin: 0 15px\" method=\"post\" id=\"loginForm\" name=\"loginForm\" ng-submit=\"loginSystem(loginForm)\"> <div class=\"L_o_text\">欢迎登录</div> <div class=\"fg\" style=\"height:10px\"></div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">选择证书：</div> <div class=\"L_T_adright\"> <select id=\"selectcert\" class=\"L_t_input\" ng-options=\"x.certcn for x in credentials.certsOptions track by x.certid\" data-ng-model=\"credentials.selectedOption\" required> </select> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_admin\">证书密码：</div> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"credentials.password\" required> </div> </div> <div class=\"fg\" style=\"height:20px\"></div> <button type=\"submit\" class=\"L_t_text_color hand\" style=\"margin-left: 11%\" ng-disabled=\"isSubmit\"> {{loginButton}} </button> </form> <div style=\"float: right;margin-right:10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/GXCA.zip\">广西CA驱动下载</a> </div> <div style=\"float: left;margin-left: 10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/DFCA.rar\">东方CA驱动下载</a> </div> <div style=\"float: left;margin-left: 10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/OperationInstruction.rar\">用户手册下载</a> </div> <div style=\"float: right;margin-right:10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/CAApplication.rar\">申请表格下载</a> </div> <div style=\"float: left;margin-left: 10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"javascript:alert('暂无文件');\">通知文件下载</a> </div> <div style=\"float: right;margin-right:10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/QQGroup.rar\">交流群QQ号</a> </div> <div style=\"float: left;margin-left: 10%; margin-top: 17px; background: #f0f0f0\"> <a href=\"/download/BusinessInstruction.rar\">（重要）单位网报业务须知</a> </div> </div> </div> </div> <div class=\"L_twofg\" style=\"border-bottom:#d4d4d4 1px solid;height:10px\"></div> <div class=\"P_L_twofg\"> <div class=\"P_L_twoleft\" style=\"width:auto\"> 建议使用Google Chrome 浏览器（Chrome 49.0及以上版本）、360浏览器（极速模式）访问系统。 <a target=\"_blank\" style=\"color:red\">Chrome 64位浏览器下载 <a style=\"bold\" href=\"http://dl.google.com/chrome/install/ChromeStandaloneSetup64.exe\">点击下载；</a> <a target=\"_blank\" style=\"color:red\">Chrome 32位浏览器下载<a style=\"bold\" href=\"http://dl.google.com/chrome/install/ChromeStandaloneSetup.exe\">点击下载；</a> <a target=\"_blank\" style=\"color:red\">360浏览器 <a style=\"bold\" href=\"https://browser.360.cn/ee/\">点击下载；</a> <div>版权所有：南宁市人力资源和社会保障局</div> </a></a></a></div> </div>"
  );


  $templateCache.put('modules/enterprise/views/bxlogin.html',
    "<div class=\"L_bag\"> <div class=\"L_three\"> <div class=\"L_t_right\"> <form name=\"loginForm\" id=\"loginForm\" ng-submit=\"loginSystem(loginForm)\"> <div class=\"L_o_text\">单位网厅账号登录</div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"username\" id=\"username\" autofocus data-ng-model=\"credentials.username\" required type=\"text\" placeholder=\"请输入用户名\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"credentials.password\" data-ng-minlength=\"6\" required placeholder=\"请输入密码\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{credentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"L_t_input\" data-ng-model=\"credentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\"> </div> <div class=\"L_t_Code\"> <img id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"84\" height=\"40\" alt=\"验证码\"> </div> </div> <button type=\"submit\" class=\"L_t_text_color hand\">登 录 系 统</button> </form> </div> </div> </div> <div class=\"L_twofg\"></div>"
  );


  $templateCache.put('modules/enterprise/views/login.html',
    "<div class=\"L_bag\"> <div class=\"L_three\"> <div class=\"L_t_right\"> <form name=\"loginForm\" id=\"loginForm\" ng-submit=\"loginSystem(loginForm)\"> <div class=\"L_o_text\">单位网厅账号登录</div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"username\" id=\"username\" autofocus data-ng-model=\"credentials.username\" required type=\"text\" placeholder=\"请输入用户名\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"credentials.password\" data-ng-minlength=\"6\" required placeholder=\"请输入密码\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{credentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"L_t_input\" data-ng-model=\"credentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\"> </div> <div class=\"L_t_Code\"> <img id=\"captchaImage\" ng-src=\"{{captchaUrl}}\" data-ng-click=\"getCaptcha()\" width=\"84\" height=\"40\" alt=\"验证码\"> </div> </div> <button type=\"submit\" class=\"L_t_text_color hand\">登 录 系 统</button> <div class=\"other_login\"> <div class=\"other_login_title\"> <span class=\"line\"></span> <span class=\"txt\">使用其他账号登录</span> <span class=\"line\"></span> </div> <a href=\"http://175.174.62.1:8081/uaa/idstools/getGssionid/enterprise\" class=\"link_btn2\" target=\"_self\"></a> </div> </form> </div> </div> <div class=\"pop_box\" ng-show=\"popShow\"> <a href=\"\" class=\"pop_close\" data-ng-click=\"popClose()\"></a> <div class=\"pop_head\">全市参保单位及参保人：</div> <div class=\"pop_con\"> <p> 您好！为了保证养老保险数据的准确性，我中心定于2021年7月9日（本周五）至2021年7月12日（下周一）晚，对养老保险省级回流数据进行更新，在更新期间网厅及APP内的数据不稳定，可能造成查询结果不准确，如发现此类问题，请在数据更新完毕后再重新登录查询。 对于此次更新给您带来的不便，敬请谅解！</p> <p>哈尔滨市社会保险事业管理中心 2021-7-9</p> </div> </div> </div> <div class=\"footer\"> <div class=\"P_L_twofg\"> <p>建议使用Google Chrome 浏览器（Chrome 49.0及以上版本）、360浏览器（极速模式）访问系统。</p> <p>主办单位：哈尔滨市人力资源和社会保障局 地址:哈尔滨市平山区水塔路22号</p> </div> </div>"
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
    "<div class=\"L_bag\"> <div class=\"L_three\"> <div class=\"L_t_right\"> <form ng-submit=\"loginSystem(loginForm)\" name=\"loginForm\" id=\"loginForm\"> <div class=\"L_o_text\">个人网厅账号登录</div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"username\" id=\"username\" autofocus data-ng-model=\"personcredentials.idNum\" required type=\"text\" placeholder=\"证件号码\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright\"> <input class=\"L_t_input\" name=\"password\" type=\"password\" id=\"inputPassword\" data-ng-model=\"personcredentials.password\" data-ng-minlength=\"6\" required placeholder=\"请输入密码\"> </div> </div> <div class=\"L_t_text\"> <div class=\"L_T_adright2\"> <input type=\"hidden\" id=\"captchaId\" name=\"captchaId\" value=\"{{personcredentials.captchaId}}\"> <input type=\"text\" id=\"captchaWord\" name=\"captchaWord\" class=\"L_t_input\" data-ng-model=\"personcredentials.captchaWord\" data-ng-minlength=\"4\" data-ng-maxlength=\"4\" required placeholder=\"点击图片更换验证码\"> </div> <div class=\"L_t_Code\"> <img id=\"captchaImage\" ng-src=\"{{personcaptchaUrl}}\" data-ng-click=\"persongetCaptcha()\" width=\"84\" height=\"40\" alt=\"验证码\"> </div> </div> <button type=\"submit\" class=\"L_t_text_color hand\">登 录 系 统</button> <div class=\"other_login\"> <div class=\"other_login_title\"> <span class=\"line\"></span> <span class=\"txt\">使用其他账号登录</span> <span class=\"line\"></span> </div> <a href=\"http://175.174.62.1:8081/uaa/idstools/getGssionid/person\" class=\"link_btn2\" target=\"_self\"></a> </div> </form> </div> </div> <div class=\"pop_box\" ng-show=\"popShow\"> <a href=\"\" class=\"pop_close\" data-ng-click=\"popClose()\"></a> <div class=\"pop_head\">全市参保单位及参保人：</div> <div class=\"pop_con\"> <p> 您好！为了保证养老保险数据的准确性，我中心定于2021年7月9日（本周五）至2021年7月12日（下周一）晚，对养老保险省级回流数据进行更新，在更新期间网厅及APP内的数据不稳定，可能造成查询结果不准确，如发现此类问题，请在数据更新完毕后再重新登录查询。 对于此次更新给您带来的不便，敬请谅解！</p> <p>哈尔滨市社会保险事业管理中心 2021-7-9</p> </div> </div> </div> <div class=\"footer\"> <div class=\"P_L_twofg\"> <p>建议使用Google Chrome 浏览器（Chrome 49.0及以上版本）、360浏览器（极速模式）访问系统。</p> <p>主办单位：哈尔滨市人力资源和社会保障局 地址:哈尔滨市平山区水塔路22号</p> </div> </div>"
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
