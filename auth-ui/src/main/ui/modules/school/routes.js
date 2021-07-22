/**
 * @classDescription 学校前台路由
 */
angular.module('authserver.school').config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        // 登录
        .when('/siagentlogin', {
            //查询压缩后的路径
            templateUrl: 'modules/school/views/login.html',
            controller: 'schoolCtrl'
        })
}]);