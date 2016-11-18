
<%@ attribute name="label" 		required="true"%>
<%@ attribute name="bundle" 	required="false"%>
<%@ attribute name="id" 		required="true"  rtexprvalue="true"  	description="ID do componente"%>
<%@ attribute name="data"		required="false" rtexprvalue="false" 	description="Propriedade da data"	
	type="java.lang.String"%>
<%@ attribute name="hora" 		required="false" rtexprvalue="false" 	description="Propriedade da hora"	
	type="java.lang.String"%>
<%@ attribute name="dateEhora"	required="false" rtexprvalue="false"	description="Adicionar Hora"		
	type="java.lang.String"%>
<%@ attribute name="periodo"	required="false" rtexprvalue="false"	description="Caso seja data por periodo"	
	type="java.lang.Boolean"%>
<%@ attribute name="dataEOuHoraFinal" required="false" rtexprvalue="false" 	description="Propriedade de data e ou de hora final do período"	
	type="java.lang.String"%>

<c:set var="_bundle"value="${empty bundle? 'messages.documento' : bundle}" />
<c:set var="_tipo"	value="${(dateEhora==true)? 'datetime': 'date' }" />

<div class="${periodo == true ? 'four' : 'eight'} wide field">
	<label><bean:message bundle="${_bundle}" key="${label}" /></label>
	<div class="ui calendar" id="${not empty data ? 'data_' : 'hora_'}${id}">
	    <div class="ui ${empty periodo || periodo == false ? 'action' : '' } input left icon">
	      	<i class="${not empty data ? 'calendar' : 'time'} icon"></i>
	      	<nested:text property="${not empty data ? data : hora}" styleClass="campoData campo_${id}"/>
	      	<c:if test="${empty periodo || periodo == false}">
	      		<a class="ui icon button" onclick="limpar_${id}('campo_${id}')"><i class="remove icon"></i></a>
	      	</c:if>
	    </div>
	</div>
</div>

<c:if test="${periodo == true}">
	<div class="four wide field">
		<label>Até</label>
		<div class="ui calendar" id="${not empty data ? 'dataFim_' : 'horaFim_'}${id}">
		    <div class="ui input left icon">
		      	<i class="${not empty data ? 'calendar' : 'time'} icon"></i>
		      	<nested:text property="${dataEOuHoraFinal}" styleClass="campoData campo_${id}"/>
				<a class="ui button" onclick="limpar_${id}('campo_${id}')" style="margin-left: 1em">Limpar</a>
		    </div>
		</div>
	</div>
</c:if>

<script type="text/javascript">
	$(document).ready(function(){
		$('.campoData').attr('readonly', true);

		$('#data_${id}').calendar({
			type: '${_tipo}',
			endCalendar: $('#dataFim_${id}')
		});
		$('#dataFim_${id}').calendar({
			type: '${_tipo}',
			startCalendar: $('#data_${id}')
		});
		
		$('#hora_${id}').calendar({
			type: 'time',
			ampm: false,
			endCalendar: $('#horaFim_${id}')
		});
		$('#horaFim_${id}').calendar({
			type: 'time',
			ampm: false,
			startCalendar: $('#hora_${id}')
		});
	});
	
	function limpar_${id}(campo) {
		$("."+ campo).val("");
	}
</script>