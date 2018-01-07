;(function() {
    angular
        .module('app')
        .controller('AuthController', ['$uibModal', 'SweetAlert', 'AuthService', function($uibModal, SweetAlert, AuthService) {
            var vm = this;

            function init() {  
            }

            function login() {
                var data = vm.usuario;

                SweetAlert.swal({
                    title: "Carregando...",
                    showConfirmButton: false
                });

                AuthService.logar(data).then(function(response) {
                    SweetAlert.close(); 
                    localStorage.setItem('token', response.data.data.token);
                    
                    if(response.data.data.authorities.some(x => x.authority === "ROLE_ADMIN")){
                    	localStorage.setItem('admin', true);                    
                    } else {
                    	localStorage.setItem('admin', false);                      	
                    }  
                    
                    window.location = '/index.html';
                    
                }, function(response) {
                    SweetAlert.swal({
                        title: 'Erro ao tentar logar',
                        text: response.data.mensagem,
                        type: 'error'
                    });
                });
            }

            init();

            vm.login = login;
            return vm;
        }]);
})();
