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