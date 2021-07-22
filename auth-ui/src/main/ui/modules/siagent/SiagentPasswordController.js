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