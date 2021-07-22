/**
 * Created by Erwin on 2017/9/30 0030.
 * 登录工程config
 */
angular.module('AuthUI')
    .provider('girderConfig', {
        //可以配置的属性
        baseUrl: 'please config baseUrl before use this frameWork', //全局后台服务路径 示例 /sipub
        //可以访问到的属性
        $get: [function () {
            var service = {
                baseUrl: this.baseUrl
            };
            return service;
        }]
    });
    // .factory('authGlobalInterceptor', function($q,$cookies) {
    //     return {
    //         // 可选，拦截成功的请求
    //         request: function(config) {
    //             // 进行预处理
    //             $cookies.get('X-XSRF-TOKEN');
    //
    //             return config || $q.when(config);
    //         }

            // // 可选，拦截失败的请求
            // requestError: function(rejection) {
            //     // 对失败的请求进行处理
            //     // ...
            //     if (canRecover(rejection)) {
            //         return responseOrNewPromise
            //     }
            //     return $q.reject(rejection);
            // },
            //
            //
            //
            // // 可选，拦截成功的响应
            // response: function(response) {
            //     // 进行预处理
            //     // ....
            //     return response || $q.when(reponse);
            // },
            //
            // // 可选，拦截失败的响应
            // responseError: function(rejection) {
            //     // 对失败的响应进行处理
            //     // ...
            //     if (canRecover(rejection)) {
            //         return responseOrNewPromise
            //     }
            //     return $q.reject(rejection);
            // }
    //     };
    // })
    // .config(function($httpProvider) {
    //     $httpProvider.interceptors.push('authGlobalInterceptor');
    // });