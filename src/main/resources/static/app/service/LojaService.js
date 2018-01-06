;(function() {	
	angular
        .module('app')
        .service('LojaService', ['$http', function($http) {

            return {
                find: function(codigo){
                	return $http.get('api/loja/' + codigo);
                },
            	save: function(data) {
            		return $http({
                		method: 'POST',
                		url   : '/api/loja',
                		data  : data,
                		withCredentials : true,
                        headers : {Authorization : 'Bearer ' + localStorage.getItem('token')}
                	});              
                }
            };
        }]);
})();
