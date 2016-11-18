$(document).ready(function(){

});

function showMenu(){
	var box = $('.side-menu');
	if (box.hasClass('hidden')) {
	  	box.removeClass('hidden');
   		box.removeClass('visuallyhidden');
	} else {
	  box.addClass('visuallyhidden');
	  box.addClass('hidden');
	}
}


function somar(){
	var valor = parseFloat($("input[name='formFechar:valor']").val().replace(/,/g, "."))|| 0;
	var couver = parseFloat($("input[name='formFechar:couver']").val().replace(/,/g, "."))  || 0;
	var taxaServico = parseFloat($("input[name='formFechar:taxaServico']").val().replace(/,/g, "."))|| 0;
	var desconto = parseFloat($("input[name='formFechar:desconto']").val().replace(/,/g, "."))|| 0;
	var vTotal = valor + couver - desconto;
	if(taxaServico != 0){
		taxaServico = 1 + taxaServico/100;
		vTotal = vTotal * taxaServico;
	}

	vTotal = vTotal.toLocaleString('pt-BR', {minimumFractionDigits: 2});
	$("input[name='formFechar:total']").val(vTotal) ;
}

function verificaTroco(){
	// devido à utilização do selectOneRadio do JSF, não existe a propriedade name
	// portanto é necessário pegar o nome gerado após interpretação do browser - formFechar:j_idt43
	var checked = $("input[name='formFechar:j_idt46']:checked" ,"#formFechar").val();
	
	if(checked == 'dinheiro'){
		$("#valorPagoDiv").show();
		$("#trocoDiv").show();
	}else{
		$("#valorPagoDiv").hide();
		$("#trocoDiv").hide();
	}
	
}

function valorTroco(){
	var valor = parseFloat($("input[name='formFechar:total']").val().replace(/,/g, "."))|| 0;
	var valorPago = parseFloat($("input[name='formFechar:valorPago']").val().replace(/,/g, "."))  || 0;
	var vTroco = valorPago - valor;
	vTroco = vTroco.toLocaleString('pt-BR', {minimumFractionDigits: 2});
	$("input[name='formFechar:troco']").val(vTroco);
	
	var valor = parseFloat($("input[name='formFechar:troco']").val());
	// gato para comparação de dados funcionar com JSF
	//<![CDATA[
	if(parseFloat(valor) <= parseFloat('0')){
		$("input[name='formFechar:troco']").val(0);
	}
	//]]>
}