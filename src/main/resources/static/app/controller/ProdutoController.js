;(function() {
    angular
        .module('app')
        .controller('ProdutoController', ['$uibModal', 'SweetAlert', 'ProdutoService', 'LojaService', function($uibModal, SweetAlert, ProdutoService, LojaService) {
            var vm = this;

            function init() { 
            	vm.produto = {};
            	vm.produto.lojas = [];
            }
            
            function buscar(codigo){
        		LojaService.find(codigo).then(function(response) {
        			vm.produto.lojas.push(response.data.data);
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
                        title: 'Erro ao carregar loja',
                        text: mensagem,
                        type: 'error'
                    });
                });
        	};
        	
        	function remover(codigo){
        		vm.produto.lojas = vm.produto.lojas.filter(e => e.codigo !== codigo)	        		
        	}
        	
        	function buscarProduto(){
        		var data = vm.busca;
        		
        		SweetAlert.swal({
                    title: "Carregando...",
                    showConfirmButton: false
                }); 
        		
        		ProdutoService.busca(data).then(function(response) {
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
            vm.remover = remover;
            vm.buscar = buscar;
            vm.buscarProduto = buscarProduto;
            return vm;
        }]);
})();
