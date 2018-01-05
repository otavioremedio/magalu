;(function() {	
	angular
        .module('app')
        .service('LojaService', ['$http', function($http) {

            return {
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
