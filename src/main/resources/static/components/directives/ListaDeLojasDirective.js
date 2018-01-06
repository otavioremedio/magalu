;(function() {
    'use strict';

    angular
        .module('app')
        .directive('listaDeLojas', function () {
        	
	        var controller = ['$scope', 'LojaService', 'SweetAlert', function($scope, LojaService, SweetAlert){
	        	
	        	function init() {
	        	}
	        	
	        	init();
	        	
	        	$scope.local = function(){
	        		if(window.location.hash === '#!/busca') return 1;
	        		
	        		return 0;
	        	}
	        	
	        	$scope.buscar = function(codigo){
	        		LojaService.find(codigo).then(function(response) {
	        			$scope.$parent.vm.produto.lojas.push(response.data.data);
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
	        	
	        	$scope.remover = function(codigo){
	        		$scope.$parent.vm.produto.lojas = $scope.$parent.vm.produto.lojas.filter(e => e.codigo !== codigo)	        		
	        	}
	        	
	        }];
	        
	        return {
	                restrict: 'E',
	                templateUrl: 'components/directives/listaDeLojas.html',
	                scope: true,
	                controller: controller
	            };
        });      
}());
