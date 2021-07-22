/**
 * Created by Erwin on xx/xx/xx.
 * XSRF security token
 */
'use strict';
angular.module('xsrfToken', [])
    .factory('XSRFTokenService', ['$log', '$resource', 'girderConfig','$http',
        function ($log, $resource, girderConfig,$http) {
            //xsrf地址
            var url = girderConfig.baseUrl + 'prevent/token ';

            //定义工厂对象
            var factory = {
                //
            };
            /**
             * 获取xsrfToken
             */
            factory.getXsrfToken = function () {
                return  $http({ method: 'GET', url:url });
            };

            return factory;
        }]);

