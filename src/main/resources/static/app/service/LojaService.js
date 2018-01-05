;(function() {	
	angular
        .module('app')
        .service('LojaService', ['$http', function($http) {

            return {
//                get: function() {
//                    return $http.get('/api/pedidos');
//                },
            	
            	
                save: function(data) {
                	$http.post('/auth', {'email':'admin@magalu.com.br', 'senha':'123456'}).then(function(response){
                		let headers = new Headers({ 'Authorization': 'Bearer ' + response.data.data.token});
                		let options = new RequestOptions({ headers: headers });
                        return $http.post('/api/loja', data, options);                
                	});
                }
            };
        }]);
})();
