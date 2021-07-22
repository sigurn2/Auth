/**
 * @author y_zhang.neu
 * time 2015.11.24
 * @classDescription 天津个人前台路由
 */
angular.module('authserver.expert')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
        // 登录
            .when('/expertlogin', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertlogin.html',
                controller: 'expertCtrl'
            })
            //注册
            .when('/expertRegister', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertregister.html',
                controller: 'expertregisterCtrl'
            })
            //注册成功
            .when('/expertRegister_View', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertregister_view.html'
            })
            //密码修改
            .when('/expertPassword', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertpassword.html',
                controller: 'expertpasswordCtrl'
            })
            //密码修改成功
            .when('/expertPassword_View', {
                //查询压缩后的路径
                templateUrl: 'modules/expert/views/expertpassword_view.html'
            })
    }]);