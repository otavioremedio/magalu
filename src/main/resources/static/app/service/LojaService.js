;(function() {
    angular
        .module('app')
        .service('LojaService', ['$http', function($http) {
            return {
//                get: function() {
//                    return $http.get('/api/pedidos');
//                },
//                save: function(data) {
//                    return $http.post('/api/novo-pedido', data);
//                }
            };
        }]);
})();
