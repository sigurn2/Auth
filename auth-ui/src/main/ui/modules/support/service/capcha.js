/**
 * 用户安全信息查询资源服务模块
 *
 * @author wuyf
 * @date 2015-03-16
 */
'use strict';
angular.module('captcha', [])
    .factory('CaptchaService', ['$log', '$resource', 'girderConfig',
        function ($log, $resource, girderConfig) {
            //验证码地址
            var url = girderConfig.baseUrl + 'captcha/img';
            var Resource = $resource(url, {}, {
                getCaptcha: {
                    method: 'GET',
                    url: url,
                    isArray: false
                }
            });
            Resource.prototype.getImageUrl = function () {
                return url + '/' + this.id;
            };
            //定义工厂对象
            var factory = {
                //
            };
            /**
             * 获取下一个验证码
             * 返回对象{id,url}
             */
            factory.getNextCaptcha = function () {
                return Resource.getCaptcha();
            };
            return factory;
        }]);

