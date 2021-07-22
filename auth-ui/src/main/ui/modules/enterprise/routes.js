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