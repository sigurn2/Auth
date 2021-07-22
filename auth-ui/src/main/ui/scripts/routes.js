'use strict';
// scripts/insure.js
angular.module('AuthUI').config(['$routeProvider', '$locationProvider',function ($routeProvider,$locationProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: 'modules/login/login.html',
            controller: 'LoginCtrl'
        })
        //---------------默认路由
        .otherwise({
            redirectTo: '/login'
        });

    $locationProvider.html5Mode(false);
}]);



//TODO 路由变换检查
angular.module('AuthUI').run(function ($log, $rootScope, $location, ALLOW_ACCESS_LIST) {
    $rootScope.$on('$routeChangeStart', function (event, next, current) {

        //如果不是angular初始化
        if (next.hasOwnProperty('$$route')) {
            //允许访问路由名单
            var allowAccessList = ALLOW_ACCESS_LIST.systems;
            //当前将要访问的路由
            var currentPath = next.$$route.originalPath;
            //当前路由是否允许访问(默认不允许)
            var isAllowAccess = false;

            //检查当前路由是否允许跳转
            angular.forEach(allowAccessList, function (flag) {
                if (currentPath.indexOf(flag) === 1 || currentPath === '/login') {
                    // console.log(currentPath);
                    $rootScope.$broadcast('changeSystemFlag',flag);
                    isAllowAccess = true;
                }
            });

            //如果当前路由未经允许
            if (!isAllowAccess) {
                if (angular.isDefined(current)) {
                    //如果从其他路由跳转
                    $location.path(current.$$route.originalPath);
                } else {
                    $location.path('/');
                }
            }
        }
    });
    $rootScope.$on('$routeChangeSuccess', function (evt, next, current) {
        console.log("请求成功：");
        var firsttitle = '哈尔滨市人社网上经办大厅';
        if(next.$$route.originalPath==="/bxenterpriselogin"){
            firsttitle="哈尔滨市养老保障公共服务平台";
        }else{
            firsttitle="哈尔滨市人社网上经办大厅";
        };

        $rootScope.$watch('title',function(){ document.title = firsttitle;});

    });

});


