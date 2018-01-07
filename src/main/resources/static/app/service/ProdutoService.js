;(function() {	
	angular
        .module('app')
        .service('ProdutoService', ['$http', function($http) {

            return {
                busca: function(data){
                	return $http.get('api/loja/');
                },
            	save: function(data) {
            		return $http({
                		method: 'POST',
                		url   : '/api/produto',
                		data  : data,
                		withCredentials : true,
                        headers : {Authorization : 'Bearer ' + localStorage.getItem('token')}
                	});              
                }
            };
        }]);
})();
