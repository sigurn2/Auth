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