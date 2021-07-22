/**
 * Created by y_zhang.neu on 2015/11/14.
 */
angular.module('Directive', [])
    //密码重复输入比对自定义指令
    .directive('girderValidPassword', function () {
        return {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                reference: '=girderValidPassword'  //双向绑定
            },
            link: function (scope, elm, attrs, ctrl) {
                ctrl.$parsers.unshift(function (viewValue) {
                    if (viewValue === '') {
                        ctrl.$setValidity('_required', false);
                    } else {
                        ctrl.$setValidity('_required', true);
                    }
                    var noMatch = viewValue !== scope.reference;
                    ctrl.$setValidity('notEqual', !noMatch);
                });
                //监听
                scope.$watch('reference', function (value) {
                    ctrl.$setValidity('notEqual', value === ctrl.$viewValue);
                });
            }
        };
    });