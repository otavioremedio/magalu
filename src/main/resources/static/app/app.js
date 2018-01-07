;(function() {
	
    let token = localStorage.getItem('token');
    let admin = localStorage.getItem('admin');
    
	angular
        .module('app', ['ngRoute', 'ui.bootstrap', 'oitozero.ngSweetAlert'])
        .controller('headController', function($scope){
        	$scope.isLogged = function () { 
            	return (token != null);
            } 
        	
        	$scope.isAdmin = function (){
        		return (token != null && admin === 'true');
        	}
        	
        	$scope.sair = function(){
        		localStorage.removeItem('token');
        		window.location = '/';
        	}
        })
        .config(['$routeProvider', function($routeProvider) {
            addRoutes($routeProvider);
        }]);
    
    function addRoutes($routeProvider) {
    	
    	if(token != null){
    		if(admin === 'true'){
    			$routeProvider
                .when('/loja', {
                    templateUrl: 'views/adicionarLoja.html',
                    controller: 'LojaController',
                    controllerAs: 'vm'
                })
                .when('/produto', {
                    templateUrl: 'views/adicionarProduto.html',
                    controller: 'ProdutoController',
                    controllerAs: 'vm'
                })
                .when('/busca', {
                    templateUrl: 'views/buscarProduto.html',
                    controller: 'ProdutoController',
                    controllerAs: 'vm'
                })
                .otherwise({
                    redirectTo: '/'
                });
    		} else {
    			$routeProvider
    			.when('/busca', {
                    templateUrl: 'views/buscarProduto.html',
                    controller: 'ProdutoController',
                    controllerAs: 'vm'
                })
                .otherwise({
                    redirectTo: '/busca'
                });
    		}
    		
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
