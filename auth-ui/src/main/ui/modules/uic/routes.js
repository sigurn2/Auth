/**
 * @author y_zhang.neu
 * time 2015.11.24
 * @classDescription 天津用户中心路由
 */
angular.module('authserver.uic').config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        // 登录
        .when('/uicLogin', {
            //查询压缩后的路径
            templateUrl: 'views/uic/login.html',
            module: 'authserver.uic',
            controller: 'uicCtrl'
        })
        .otherwise({
            redirectTo: '/uicLogin'
        });
}]);