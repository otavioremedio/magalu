;(function() {
    'use strict';

    angular
        .module('app')
        .directive('listaDeLojas', [function () {
            return {
                restrict: 'E',
                templateUrl: 'components/directives/listaDeLojas.html',
                scope: {
                    'lojas': '='
                }
            }
        }]);

})();
