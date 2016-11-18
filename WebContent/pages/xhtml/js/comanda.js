$(document).ready(function(){
	//Card de foto
	$('.special.cards .image').dimmer({
		on: 'hover'
	});
	
	ativarAba('#primeiraAba');

	$(".togglecomanda").click(function(){
		$(this).next('table').toggle();
	});

});

function tipoEntrada(){
	$('#valorEntrada').val('R$0');
	$('#valorConsumo').val('R$60');
}

function ativarAba(element){
	$('.step').removeClass('active');
	$('.abas').hide();
	$(this).addClass('active');
	$(element).show();
	if(element == '#primeiraAba'){
		$("#contato").hide();
		$("#contatoComanda").hide();
	}
}

function pesquisar(e){
    if(OnEnter(e)){
	    if($("#campoPesquisa").val() == "007"){
			$("#contato").hide();	
			$("#contatoComanda").show();
	    }
		else if($("#campoPesquisa").val() == "Bianca"){
			$("#contatoComanda").hide();
			$("#contato").show();	
		}
		$("#campoPesquisa").val("");
    }
    else
        return true;
}

function hide(element){
	$(element).hide();
}

function OnEnter(evt){
    var key_code = evt.keyCode ? 
    	evt.keyCode : evt.charCode ? 
    		evt.charCode : evt.which ? 
    			evt.which : void 0;
    if (key_code == 13)
        return true;
}

function formaDePagamento(forma){
	$(".btnFormaPagamento").hide();
	if(forma == 'dinheiro'){
		$("#bandeiraCartao").hide();
		$("#camposPagDinheiro").show();
	}
	else{
		$("#camposPagDinheiro").hide();
		$("#bandeiraCartao").show();
	}
	$(".formaDePagamento").show('slow');
}


$('ui.dropdown').dropdown();
function somar(){
	var valor = parseFloat($("#valor").val().replace(/,/g, "."))|| 0;
	var couver = parseFloat($("#couver").val().replace(/,/g, "."))  || 0;
	var taxaServico = parseFloat($("#taxaServico").val().replace(/,/g, "."))|| 0;
	var desconto = parseFloat($("#desconto").val().replace(/,/g, "."))|| 0;
	var vTotal = valor + couver - desconto;
	if(taxaServico != 0){
		taxaServico = 1 + taxaServico/100;
		vTotal = vTotal * taxaServico;
	}
	$("#total").val(vTotal) ;
}

function valorTroco(){
	var valor = parseFloat($("#total").val().replace(/,/g, "."))|| 0;
	var valorPago = parseFloat($("#valorPago").val().replace(/,/g, "."))  || 0;
	var vTroco = valorPago - valor;
	$("#troco").val(vTroco) ;
}