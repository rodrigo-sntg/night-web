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

function ativarAba(element){
	$('.step').removeClass('active');
	$('.abas').hide();
	$(this).addClass('active');
	$(element).show();
}

function pesquisar(e){
    if(OnEnter(e)){
	    if($("#campoPesquisa").val() == "007")
			$("#contato").show();	
    }
    else
        return true;
}

function OnEnter(evt){
    var key_code = evt.keyCode ? 
    	evt.keyCode : evt.charCode ? 
    		evt.charCode : evt.which ? 
    			evt.which : void 0;
    if (key_code == 13)
        return true;
}