/**
 * Created by wuyf on 2014/7/4.
 *
 * messageBox组件
 * -------------
 */
'use strict';
// app/girder/ui/messagebox/messagebox.js

angular.module('AuthUI')
    .service('messageService', ['$modal', function ($modal) {
        //默认参数配置
        var modalDefaults = {
            backdrop: true,
            keyboard: true,
            modalFade: true,
            templateUrl: 'modules/support/template/messageview.html'
        };

        //默认view参数配置
        var modalOptions = {
            closeButtonText: 'Close',
            actionButtonText: 'OK',
            headerText: 'Proceed?',
            bodyText: 'Perform this action?'
        };

        this.showModal = function (customModalDefaults, customModalOptions) {
            if (!customModalDefaults) {
                customModalDefaults = {};
            }
            customModalDefaults.backdrop = 'static';
            return this.show(customModalDefaults, customModalOptions);
        };

        this.show = function (customModalDefaults, customModalOptions) {
            //Create temp objects to work with since we're in a singleton service
            var tempModalDefaults = {};
            var tempModalOptions = {};

            //Map angular-ui modal custom defaults to modal defaults defined in service
            angular.extend(tempModalDefaults, modalDefaults, customModalDefaults);

            //Map modal.html $scope custom properties to defaults defined in service
            angular.extend(tempModalOptions, modalOptions, customModalOptions);

            if (!tempModalDefaults.controller) {
                tempModalDefaults.controller = 'messageServiceController';
            }

            tempModalDefaults.resolve = {
                modalOptions: function () {
                    return tempModalOptions;
                }
            };
            //显示模态对话框
            return $modal.open(tempModalDefaults).result;
        };

    }])
    .controller('messageServiceController', ['$scope', '$modalInstance', 'modalOptions', '$timeout',
        function ($scope, $modalInstance, modalOptions, $timeout) {
            $scope.modalOptions = modalOptions;

            //是否定时关闭
            if ($scope.modalOptions.hasOwnProperty('timeClose')) {
                var time = $scope.modalOptions['timeClose'];
                if (time) {
                    $timeout(function () {
                        $modalInstance.dismiss('cancel');
                    }, time);
                }
            }
            //确定
            $scope.modalOptions.ok = function (result) {
                $modalInstance.close(result);
            };
            //取消
            $scope.modalOptions.close = function () {
                $modalInstance.dismiss('cancel');
            };
        }])
    //--------------------------
    //显示提示信息组件
    //
    //messageBox.showError('真心保存失败!!').then(function (result) {
    // $log.debug('messagebox then ',result);
    //});
    //--------------------------
    .service('messageBox', ['messageService', function (messageService) {

        var _showMsg = function (title, message, time) {
            var modalOptions = {
                closeButtonText: null,
                actionButtonText: '确定',
                headerText: title,
                bodyText: message,
                timeClose: time
            };

            return messageService.showModal({}, modalOptions);
        };

        //显示 标题 / 提示消息
        this.showInfo = function (title, message, time) {
            var t = (title === null) ? '消息提示' : title;
            return _showMsg(t, message, time);
        };

        // 显示提示消息
        this.showInfo = function (message, time) {
            return _showMsg('消息提示', message, time);
        };

        // 显示 标题 / 错误详情
        this.showError = function (title, message, time) {
            var t = (title === null) ? '错误提示' : title;
            return _showMsg(t, message, time);
        };

        // 显示 错误信息
        this.showError = function (message, time) {
            return _showMsg('错误提示', message, time);
        };

    }])
    //--------------------------
    //显示选择信息组件
    //
    //confirmBox.showInfo('这次要选择yes吗？').then(function (result) {
    //  $log.debug('这次选了yes!');
    //});
    //
    //--------------------------
    .service('confirmBox', ['messageService', function (messageService) {

        var _showMsg = function (title, message) {
            var modalOptions = {
                closeButtonText: '否',
                actionButtonText: '是',
                headerText: title,
                bodyText: message
            };
            return messageService.showModal({}, modalOptions);
        };

        //显示 标题 / 提示消息
        this.showInfo = function (title, message) {
            var t = (title === null) ? '消息提示' : title;
            return _showMsg(t, message);
        };

        // 显示提示消息
        this.showInfo = function (message) {
            return _showMsg('消息提示', message);
        };

    }]);

