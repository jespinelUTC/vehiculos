 
	 var decodificarEntidades = function (tx)
	 {
	 	var rp = String(tx);
	 	//
	 	rp = rp.replace(/&aacute;/g, 'á');
	 	rp = rp.replace(/&eacute;/g, 'é');
	 	rp = rp.replace(/&iacute;/g, 'í');
	 	rp = rp.replace(/&oacute;/g, 'ó');
	 	rp = rp.replace(/&uacute;/g, 'ú');
	 	rp = rp.replace(/&ntilde;/g, 'ñ');
	 	rp = rp.replace(/&uuml;/g, 'ü');	 	
	 	//
	 	rp = rp.replace(/&Aacute;/g, 'Á');
	 	rp = rp.replace(/&Eacute;/g, 'É');
	 	rp = rp.replace(/&Iacute;/g, 'Í');
	 	rp = rp.replace(/&Oacute;/g, 'Ó');
	 	rp = rp.replace(/&Uacute;/g, 'Ú');
	 	rp = rp.replace(/&Ñtilde;/g, 'Ñ');
	 	rp = rp.replace(/&Üuml;/g, 'Ü');
	 	//
	 	return rp;
	 };
	 
	 
	 
	 function Unidades(num){

		  switch(num)
		  {
		    case 1: return "UN";
		    case 2: return "DOS";
		    case 3: return "TRES";
		    case 4: return "CUATRO";
		    case 5: return "CINCO";
		    case 6: return "SEIS";
		    case 7: return "SIETE";
		    case 8: return "OCHO";
		    case 9: return "NUEVE";
		  }

		  return "";
		}

		function Decenas(num){

		  decena = Math.floor(num/10);
		  unidad = num - (decena * 10);

		  switch(decena)
		  {
		    case 1:   
		      switch(unidad)
		      {
		        case 0: return "DIEZ";
		        case 1: return "ONCE";
		        case 2: return "DOCE";
		        case 3: return "TRECE";
		        case 4: return "CATORCE";
		        case 5: return "QUINCE";
		        default: return "DIECI" + Unidades(unidad);
		      }
		    case 2:
		      switch(unidad)
		      {
		        case 0: return "VEINTE";
		        default: return "VEINTI" + Unidades(unidad);
		      }
		    case 3: return DecenasY("TREINTA", unidad);
		    case 4: return DecenasY("CUARENTA", unidad);
		    case 5: return DecenasY("CINCUENTA", unidad);
		    case 6: return DecenasY("SESENTA", unidad);
		    case 7: return DecenasY("SETENTA", unidad);
		    case 8: return DecenasY("OCHENTA", unidad);
		    case 9: return DecenasY("NOVENTA", unidad);
		    case 0: return Unidades(unidad);
		  }
		}//Unidades()

		function DecenasY(strSin, numUnidades){
		  if (numUnidades > 0)
		    return strSin + " Y " + Unidades(numUnidades)

		  return strSin;
		}//DecenasY()

		function Centenas(num){

		  centenas = Math.floor(num / 100);
		  decenas = num - (centenas * 100);

		  switch(centenas)
		  {
		    case 1:
		      if (decenas > 0)
		        return "CIENTO " + Decenas(decenas);
		      return "CIEN";
		    case 2: return "DOSCIENTOS " + Decenas(decenas);
		    case 3: return "TRESCIENTOS " + Decenas(decenas);
		    case 4: return "CUATROCIENTOS " + Decenas(decenas);
		    case 5: return "QUINIENTOS " + Decenas(decenas);
		    case 6: return "SEISCIENTOS " + Decenas(decenas);
		    case 7: return "SETECIENTOS " + Decenas(decenas);
		    case 8: return "OCHOCIENTOS " + Decenas(decenas);
		    case 9: return "NOVECIENTOS " + Decenas(decenas);
		  }

		  return Decenas(decenas);
		}//Centenas()

		function Seccion(num, divisor, strSingular, strPlural){
		  cientos = Math.floor(num / divisor)
		  resto = num - (cientos * divisor)

		  letras = "";

		  if (cientos > 0)
		    if (cientos > 1)
		      letras = Centenas(cientos) + " " + strPlural;
		    else
		      letras = strSingular;

		  if (resto > 0)
		    letras += "";

		  return letras;
		}//Seccion()

		function Miles(num){
		  divisor = 1000;
		  cientos = Math.floor(num / divisor)
		  resto = num - (cientos * divisor)

		  strMiles = Seccion(num, divisor, "UN MIL", "MIL");
		  strCentenas = Centenas(resto);

		  if(strMiles == "")
		    return strCentenas;

		  return strMiles + " " + strCentenas;

		  //return Seccion(num, divisor, "UN MIL", "MIL") + " " + Centenas(resto);
		}//Miles()

		function Millones(num){
		  divisor = 1000000;
		  cientos = Math.floor(num / divisor)
		  resto = num - (cientos * divisor)

		  strMillones = Seccion(num, divisor, "UN MILLON", "MILLONES");
		  strMiles = Miles(resto);

		  if(strMillones == "")
		    return strMiles;

		  return strMillones + " " + strMiles;

		  //return Seccion(num, divisor, "UN MILLON", "MILLONES") + " " + Miles(resto);
		}//Millones()

		
		function NumeroALetras(num){
		  var data = {
		    numero: num,
		    enteros: Math.floor(num),
		    centavos: (((Math.round(num * 100)) - (Math.floor(num) * 100))),
		    letrasCentavos: "",
		    letrasMonedaPlural: "",
		    letrasMonedaSingular: ""
		  };

		  if (data.centavos > 10)
		    data.letrasCentavos = "" + data.centavos + "/100";
		  if (data.centavos > 0 && data.centavos<10)
			    data.letrasCentavos = "0" + data.centavos + "/100";

		  if(data.enteros == 0)
		    return "CERO " + data.letrasMonedaPlural + " " + data.letrasCentavos;
		  if (data.enteros == 1)
		    return Millones(data.enteros) + " " + data.letrasMonedaSingular + " " + data.letrasCentavos;
		  else
		    return Millones(data.enteros) + " " + data.letrasMonedaPlural + " " + data.letrasCentavos;
		}//NumeroALetras()
 
		
		function fecha(){
			fecha = new Date()
			mes = fecha.getMonth()
			diaMes = fecha.getDate()
			diaSemana = fecha.getDay()
			anio = fecha.getFullYear()
			dias = new Array('Domingo','Lunes','Martes','Miercoles','Jueves','Viernes','Sábado')
			meses = new Array('Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre')
			document.write('<span>')
			document.write (dias[diaSemana] + ", " + diaMes + " de " + meses[mes] + " de " + anio)
			document.write ('</span>')
		}
		
		function obtenerFecha(){
			
			fecha = new Date();
			mes = fecha.getMonth();
			diaMes = fecha.getDate();
			diaSemana = fecha.getDay();
			anio = fecha.getFullYear();
			dias = new Array('Domingo','Lunes','Martes','Miercoles','Jueves','Viernes','Sábado');
			meses = new Array('Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre');
			
			return dias[diaSemana] + ", " + diaMes + " de " + meses[mes] + " de " + anio;

		}
		
		
		
		
		function hora(){
			var fecha = new Date()
			var hora = fecha.getHours()
			var minuto = fecha.getMinutes()
			var segundo = fecha.getSeconds()
			if (hora < 10) {hora = "0" + hora}
			if (minuto < 10) {minuto = "0" + minuto}
			if (segundo < 10) {segundo = "0" + segundo}
			document.write(hora + ":" + minuto + ":" + segundo)
		}
		
		
		function obtenerHora(){
			var fecha = new Date()
			var hora = fecha.getHours()
			var minuto = fecha.getMinutes()
			var segundo = fecha.getSeconds()
			if (hora < 10) {hora = "0" + hora}
			if (minuto < 10) {minuto = "0" + minuto}
			if (segundo < 10) {segundo = "0" + segundo}
			return hora + ":" + minuto + ":" + segundo
		}
		
		function convertirFechaCompleta(fecha){
			mes = fecha.getMonth();
			diaMes = fecha.getDate();
			diaSemana = fecha.getDay();
			anio = fecha.getFullYear();
			dias = new Array('Domingo','Lunes','Martes','Miercoles','Jueves','Viernes','Sábado');
			meses = new Array('Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre');
			
			return dias[diaSemana] + ", " + diaMes + " de " + meses[mes] + " de " + anio;

		}
		
		
