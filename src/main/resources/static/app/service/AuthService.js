;(function() {	
	angular
        .module('app')
        .service('AuthService', ['$http', function($http) {

            return {
                logar: function(data) {
                	return $http.post('api/auth', data);
                }
            };
        }]);
})();
