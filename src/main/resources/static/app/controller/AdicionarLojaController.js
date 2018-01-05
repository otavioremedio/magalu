;(function() {
    angular
        .module('app')
        .controller('AdicionarLojaController', ['$uibModal', 'SweetAlert', 'LojaService', function($uibModal, SweetAlert, LojaService) {
            var vm = this;

            function init() {
//                ProdutoService.get().then(function(response) {
//                    vm.produtos = response.data;
//                }, function(response) {
//                    SweetAlert.swal({
//                        title: 'Erro ao carregar produtos',
//                        text: response.data.mensagem,
//                        type: 'error'
//                    });
//                });
            }

            function salvarLoja() {
                var data = vm.loja.map(function(produto) {
                    return {descricao: loja.descricao,                      
                        	codigo: loja.codigo
                    };
                });

                SweetAlert.swal({
                    title: "Carregando...",
                    showConfirmButton: false
                });
//
//                PedidoService.save(data).then(function(response) {
//                    SweetAlert.close();
//
//                    $uibModal.open({
//                       ariaLabelledBy: 'modal-title',
//                       ariaDescribedBy: 'modal-body',
//                       templateUrl: 'components/modals/sucessoAoAdicionarPedido.html',
//                       controllerAs: 'vm',
//                       controller: function() {
//                           var vm = this;
//                           vm.pedidos = response.data;
//                           return vm;
//                       }
//                   });
//                }, function(response) {
//                    SweetAlert.swal({
//                        title: 'Erro ao criar novo pedido',
//                        text: response.data.mensagem,
//                        type: 'error'
//                    });
//                });
            }

            init();

            //vm.salvarPedido = salvarPedido;
            return vm;
        }]);
})();
