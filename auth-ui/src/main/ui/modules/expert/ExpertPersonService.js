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