;(function() {
    angular
        .module('app')
        .controller('ProdutoController', ['$uibModal', 'SweetAlert', 'ProdutoService', function($uibModal, SweetAlert, ProdutoService) {
            var vm = this;

            function init() {  
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
                    SweetAlert.swal({
                        title: 'Erro ao criar novo produto',
                        text: response.data.mensagem,
                        type: 'error'
                    });
                });
            }

            init();

            vm.salvarProduto = salvarProduto;
            return vm;
        }]);
})();
