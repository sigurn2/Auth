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