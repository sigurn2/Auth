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