// /**
//  * login controller
//  * author:y_zhang.neu
//  */
// 'use strict';
// angular.module('authserver.resident')
//     .controller('residentCtrl', ['$rootScope', '$scope', '$location', '$log', 'CaptchaService', 'residentService', 'messageBox', 'IDCard',
//         function ($rootScope, $scope, $location, $log, CaptchaService, residentService, messageBox, IDCard) {
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
//                     $scope.captchaUrl = data.getImageUrl();
//                     $scope.credentials.captchaId = data.id;
//                 });
//             };
//             $scope.getCaptcha();
//         }]);