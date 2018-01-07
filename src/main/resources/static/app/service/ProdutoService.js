;(function() {	
	angular
        .module('app')
        .service('ProdutoService', ['$http', function($http) {

            return {
                busca: function(data){
                	//corrigir querystring
                	return $http.get('api/produto/' + data.codigo + '/' + 'X' + '/' + data.origem
                			, {headers : {Authorization : 'Bearer ' + localStorage.getItem('token')}});
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
