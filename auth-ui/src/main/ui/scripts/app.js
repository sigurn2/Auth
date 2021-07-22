angular.module('AuthUI',
    [
        'ngRoute',
        'ngResource',
        'ngAnimate',
        'Validateform',
        'ui.bootstrap',
        'ngCookies',
        'captcha',
        'Directive',
        'xsrfToken',
        'authserver',
        'authserver.enterprise',
        'authserver.ca',
        'authserver.person',
        'authserver.resident',
        'authserver.school',
        'authserver.expert'
    ]);
angular.module('authserver.enterprise', []);//企业
angular.module('authserver.ca', []);//CA企业
angular.module('authserver.person', []);//个人
// angular.module('authserver.siagent', []);//建筑单位
angular.module('authserver.resident', []);//居民
angular.module('authserver.school', []);//学校
angular.module('authserver.expert', []);//专家

//工程配置
angular.module('AuthUI').config(['girderConfigProvider', function (girderConfigProvider) {
    girderConfigProvider.baseUrl = '/uaa/';
}]);


//允许访问区域配置(输入值可为：['person','enterprise','siagent','resident'])
angular.module('AuthUI')
    .constant('ALLOW_ACCESS_LIST', {
        //默认路由
        otherwiseRoute: 'enterprise',
        //系统允许列表
        systems: ['person', 'enterprise', 'ca', 'siagent', 'expert','bxenterprise']
    });

