/**
 * login controller
 * author:y_zhang.neu
 */
'use strict';
angular.module('authserver.uic')
  .controller('uicCtrl', ['$rootScope', '$scope', '$location', '$log', 'CaptchaService','siagentService','messageBox',
    function ($rootScope, $scope, $location, $log, CaptchaService,siagentService,messageBox) {
      //messagebox
      $scope.messageBox=messageBox;
      /**
       * 登录
       * @type {{username: string, taxcode: string, password: string, captchaId: null, captchaWord: null}}
       */
      $scope.credentials = {
        username: '46010896440183',
        taxcode: '46010028406390X2',
        password: '123456',
        captchaId: null,
        captchaWord: null
      };
      $scope.getCaptcha = function () {
        CaptchaService.getNextCaptcha().$promise.then(function (data) {
          $log.info('loginCtrl获取到验证码', data.id);
          $scope.captchaUrl = data.getImageUrl();
          $scope.credentials.captchaId = data.id;
        });
      };
      $scope.getCaptcha();
    }]);