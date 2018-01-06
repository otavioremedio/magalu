;(function() {
    angular
        .module('app')
        .controller('ProdutoController', ['$uibModal', 'SweetAlert', 'ProdutoService', function($uibModal, SweetAlert, ProdutoService) {
            var vm = this;

            function init() { 
            	produto = {};
            	produto.lojas = [];
            }
            
            function salvarProduto() {
                var data = vm.produto;

                SweetAlert.swal({
                    title: "Carregando...",
                    showConfirmButton: false
                });

                ProdutoService.save(data).then(function(response) {
                    SweetAlert.close();

                    $uibModal.open({
                       ariaLabelledBy: 'modal-title',
                       ariaDescribedBy: 'modal-body',
                       templateUrl: 'components/modals/sucessoAoAdicionarProduto.html',
                       controllerAs: 'vm',
                       controller: ['$scope', function($scope) {                         
                    	   $scope.$on('modal.closing', function(event, reason, closed){                    		   
                    		   window.location = '/';   
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
                        title: 'Erro ao criar produto',
                        text: mensagem,
                        type: 'error'
                    });
                });
            }

            init();

            vm.salvarProduto = salvarProduto;
            vm.produto = produto;
            return vm;
        }]);
})();
