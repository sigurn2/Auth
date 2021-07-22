/**
 * @author y_zhang.neu
 * time 2015.11.24
 * @classDescription 天津个人前台路由
 */
angular.module('authserver.person')
    .config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        // 登录
        .when('/personlogin', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/login.html',
            controller: 'personCtrl'
        })
        //注册协议
        .when('/personAgree', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/agreement.html',
            controller: 'registerCtrl'
        })
        //注册
        .when('/personRegister', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/register.html',
            controller: 'registerCtrl'
        })
        //注册成功
        .when('/personRegister_View', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/register_view.html'
        })
        //密码修改
        .when('/personPassword', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/password.html',
            controller: 'passwordCtrl'
        })
        //密码修改成功
        .when('/personPassword_View', {
            //查询压缩后的路径
            templateUrl: 'modules/person/views/password_view.html'
        })
}]);