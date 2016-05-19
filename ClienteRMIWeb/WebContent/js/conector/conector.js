/**
 * 
 */
function sueldoTrabajador(){
	$.ajax({
		url: 'jsonSueldo.faces',
		dataType: "json",
		type: "POST",
		cache: false,
		data:{tipo:1,rut:"13974888-3"},
      	beforeSend:function(data){
      		alert("Antes");
      		},
      	success: function(data) {
      		
      	}
   
	});
}

function listaEmpleados(){
	$.ajax({
		url: 'listaEmpleados.faces',
		dataType: "json",
		type: "POST",
		cache: false,
		data:{tipo:2},
      	beforeSend:function(data){
      		
      	},
      	success: function(data) {
      		 if(data.Error=="OK"){
      			 if(data.total>0){
      				$("#tbodyid").empty();
      				 $.each(data.empleado, function(index, value) {
              		     //Cargar tabla
      					 var contenido='<tr>';
      					contenido+='<input type="hidden" id='+index+' value='+value.rut+' />';
      					 contenido+='<td align="left" class="ui-widget-content">'+value.rut+'</td>';
      					 contenido+='<td align="left" class="ui-widget-content">'+value.nombres+'</td>';
      					 contenido+='<td align="left" class="ui-widget-content">'+value.paterno+'</td>';
      					 contenido+='<td align="left" class="ui-widget-content">'+value.materno+'</td>';
      					 //contenido+='<td align="left" class="ui-widget-content"><img src="/ClienteRMIWeb/img/edit.png" height="32" width="32"></td>';
      					contenido+='<td align="left" class="ui-widget-content"><a onclick="eliminarEmpleado('+index+')"><img src="/ClienteRMIWeb/img/delete.png" height="32" width="32"></a></td>';
      					 contenido+='</tr>';
              		   $('#tblLista > tbody:last').append(contenido);
              		 });
      			 }else{
      				 alert("No existen empleados");
      				 $("#tbodyid").empty();
      			 }
      			
      		 }else{
      			 alert(data.Error);
      		 }
      		
      	}
   
	});
}

function listaLiquidaciones(){
	$.ajax({
		url: 'listaLiquidaciones.faces',
		dataType: "json",
		type: "POST",
		cache: false,
		data:{tipo:3},
      	beforeSend:function(data){
      		
      	},
      	success: function(data) {
      		 
      		 if(data.Error=="OK"){
      			 if(data.total > 0){
      				$("#tbodyid").empty();
      				 $.each(data.liquidacion, function(index, value) {
              		     //Cargar tabla
      					 var contenido='<tr>';
      					 contenido+='<input type="hidden" id='+index+' value='+value.rut+' />';
      					 contenido+='<td align="left" class="ui-widget-content">'+value.rut+'</td>';
      					 contenido+='<td align="left" class="ui-widget-content">'+value.monto+'</td>';
      					 //contenido+='<td align="left" class="ui-widget-content"><img src="/ClienteRMIWeb/img/edit.png" height="32" width="32"></td>';
      					 contenido+='<td align="left" class="ui-widget-content"><a onclick="eliminarLiquidacion('+index+')"><img src="/ClienteRMIWeb/img/delete.png" height="32" width="32"></a></td>';
      					 contenido+='</tr>';
              		   $('#tblLista > tbody:last').append(contenido);
              		 });
      			 }else{
      				 alert("No existen liquidaciones");
      				 $("#tbodyid").empty();
      			 }
      			
      		 }else{
      			 alert(data.Error);
      		 }
      		
      	}
   
	});
}

function crearLiquidacion(){
	
	var rut = $("#txtRut").val();
	var monto = $("#txtMonto").val();
	
	$.ajax({
		url: 'crearLiquidaciones.faces',
		dataType: "json",
		type: "POST",
		cache: false,
		data:{
			   tipo:4,
			   rut:rut,
			   monto:monto
		      },
      	beforeSend:function(data){
      		
      	},
      	success: function(data) {
      	  if(data.Error=="OK"){
      		  if(data.liquidacion=="1"){
      			 alert("Liquidacion registrada correctamente");
      			 listaLiquidaciones();  	  
      		  }else{
      			  alert(data.liquidacion);
      		  }
      	  }else{
      		  alert(data.Error);
      	  }
      	  
      	}
   
	});
}

function crearEmpleado(){
	var rut=$("#txtRut").val();
	var nombres=$("#txtNombres").val();
	var paterno=$("#txtPaterno").val();
	var materno=$("#txtMaterno").val();
	$.ajax({
		url: 'crearEmpleado.faces',
		dataType: "json",
		type: "POST",
		cache: false,
		data:{tipo:5,
			  rut:rut,
			  nombres:nombres,
			  paterno:paterno,
			  materno:materno
			  },
      	beforeSend:function(data){
      		
      	},
      	success: function(data) {
      	  if(data.Error=="OK"){
      		if(data.estado=="1"){
      		    alert("Empleado registrado correctamente");
      			listaEmpleados();
      		}else{
      			alert(data.estado);
      		}
      	  }else{
      		  alert(data.Error);
      	  }	
      	}
   
	});
}

function eliminarEmpleado(id){
	
	var rut = $("#"+id).val();
	
	
	$.ajax({
		url: 'eliminarEmpleado.faces',
		dataType: "json",
		type: "POST",
		cache: false,
		data:{tipo:6,
			  rut:rut
			  },
      	beforeSend:function(data){
      		
      	},
      	success: function(data) {
      	  if(data.Error=="OK"){
      		if(data.estado=="1"){
      		    alert("Empleado borrado correctamente");
      			listaEmpleados();
      		}else{
      			alert(data.estado);
      		}
      	  }else{
      		  alert(data.Error);
      	  }	
      	}
   
	});
}

function eliminarLiquidacion(id){
	
	var rut = $("#"+id).val();
	
	$.ajax({
		url: 'eliminarLiquidacion.faces',
		dataType: "json",
		type: "POST",
		cache: false,
		data:{tipo:7,
			  rut:rut
			  },
      	beforeSend:function(data){
      		
      	},
      	success: function(data) {
      	  if(data.Error=="OK"){
      		if(data.estado=="1"){
      		    alert("Empleado borrado correctamente");
      			listaLiquidaciones();
      		}else{
      			alert(data.estado);
      		}
      	  }else{
      		  alert(data.Error);
      	  }	
      	}
   
	});
}

