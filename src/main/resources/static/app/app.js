;(function() {
    angular
        .module('app', ['ngRoute', 'ui.bootstrap', 'oitozero.ngSweetAlert'])
        .controller('headController', function($scope){
        	$scope.isLogged = function () { 
            	return (token != null);
            } 
        	
        	$scope.isAdmin = function (){
        		return (admin);
        	}
        })
        .config(['$routeProvider', function($routeProvider) {
            addRoutes($routeProvider);
        }]);
    
    let token = localStorage.getItem('token');
    let admin = localStorage.getItem('admin');
    
    function addRoutes($routeProvider) {
    	
    	if(token != null){
    		$routeProvider
            .when('/loja', {
                templateUrl: 'views/adicionarLoja.html',
                controller: 'LojaController',
                controllerAs: 'vm'
            })
            .when('/lojas', {
                templateUrl: 'views/listagemLojas.html',
                controller: 'ListagemLojasController',
                controllerAs: 'vm'
            })
            .when('/produto', {
                templateUrl: 'views/adicionarProduto.html',
                controller: 'ProdutoController',
                controllerAs: 'vm'
            })
            .otherwise({
                redirectTo: '/'
            });
    	} else {
    		$routeProvider    	
    		.when('/auth', {
                templateUrl: 'views/auth.html',
                controller: 'AuthController',
                controllerAs: 'vm'
            })
    		.otherwise({
                redirectTo: '/auth'
            }); 
    	}    	
    }
})();
