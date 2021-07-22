/**
 * Controller For Home Page
 */
'use strict';
angular.module('authserver', [])
    .controller('LoginCtrl', ['$log', '$scope', '$location', 'ALLOW_ACCESS_LIST',
        function ($log, $scope, $location, ALLOW_ACCESS_LIST) {

            var keepGoing = true;
            angular.forEach(ALLOW_ACCESS_LIST.systems, function (value) {
                if (keepGoing) {
                    if ($location.absUrl().indexOf(value) != -1) {
                        $location.path('/' + value + 'login');
                        keepGoing = false;
                    }
                }
            });
            if (keepGoing) {
                $location.path('/' + ALLOW_ACCESS_LIST.otherwiseRoute + 'login');
            }
        }]);

