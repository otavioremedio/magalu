;(function() {
    angular
        .module('app', ['ngRoute', 'ui.bootstrap', 'oitozero.ngSweetAlert'])
        .config(['$routeProvider', function($routeProvider) {
            addRoutes($routeProvider);
        }]);

    function addRoutes($routeProvider) {
        $routeProvider
            .when('/lojas/nova', {
                templateUrl: 'views/adicionarLoja.html',
                controller: 'AdicionarLojaController',
                controllerAs: 'vm'
            })
            .when('/lojas', {
                templateUrl: 'views/listagemLojas.html',
                controller: 'ListagemLojasController',
                controllerAs: 'vm'
            })
            .when('/auth', {
                templateUrl: 'views/auth.html',
                controller: 'AuthController',
                controllerAs: 'vm'
            })
            .otherwise({
                redirectTo: '/auth'
            });       
    }
})();
