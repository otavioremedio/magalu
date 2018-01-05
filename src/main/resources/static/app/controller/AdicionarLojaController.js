;(function() {
    angular
        .module('app')
        .controller('AdicionarLojaController', ['$uibModal', 'SweetAlert', 'LojaService', function($uibModal, SweetAlert, LojaService) {
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
                       controller: function() {
                           var vm = this;
                           vm.loja = response.data;
                           return vm;
                       }
                   });
                }, function(response) {
                    SweetAlert.swal({
                        title: 'Erro ao criar nova loja',
                        text: response.data.mensagem,
                        type: 'error'
                    });
                });
            }

            init();

            vm.salvarLoja = salvarLoja;
            return vm;
        }]);
})();
