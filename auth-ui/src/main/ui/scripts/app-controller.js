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
