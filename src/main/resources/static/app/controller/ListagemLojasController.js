;(function() {
    angular
        .module('app')
        .controller('ListagemLojasController', ['SweetAlert', 'LojaService', function(SweetAlert, LojaService) {
            var vm = this;

            function init() {
//                PedidoService.get().then(function(response) {
//                    vm.pedidos = response.data;
//                }, function(response) {
//                    SweetAlert.swal({
//                        title: 'Erro ao carregar pedidos',
//                        text: response.data.mensagem,
//                        type: 'error'
//                    });
//                });
            }

            init();

            return vm;
        }]);
})();
