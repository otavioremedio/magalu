;(function() {
    angular
        .module('app')
        .controller('LojaController', ['$uibModal', 'SweetAlert', 'LojaService', function($uibModal, SweetAlert, LojaService) {
            var vm = this;

            function init() {  
	              
            }

            function salvarLoja() {
                var data = vm.loja;

                SweetAlert.swal({
                    title: "Carregando...",
                    showConfirmButton: false
                });

                LojaService.save(data).then(function(response) {
                    SweetAlert.close();

                    $uibModal.open({
                       ariaLabelledBy: 'modal-title',
                       ariaDescribedBy: 'modal-body',
                       templateUrl: 'components/modals/sucessoAoAdicionarLoja.html',
                       controllerAs: 'vm',
                       controller: ['$scope', function($scope) {                         
                    	   $scope.$on('modal.closing', function(event, reason, closed){                    		   
                    		   window.location = '/index.html';   
                    	   })                           
                       }]                       
                   });
                    
                }, function(response) {
                	var mensagem = '';
                	
                	if(response.data.mensagem != null){
                		mensagem = response.data.mensagem 
                	} else {
                		response.data.errors.forEach(function(e, i){
                			if(e != null){
                				mensagem += e + '\n';
                			}
                		})
                	}
                    SweetAlert.swal({
                        title: 'Erro ao criar loja',
                        text: mensagem,
                        type: 'error'
                    });
                });
            }

            init();

            vm.salvarLoja = salvarLoja;
            return vm;
        }]);
})();
