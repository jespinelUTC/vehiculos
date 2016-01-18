jQuery.validator.addMethod("lettersonly", function(value, element) {
		  //return this.optional(element) || /^[a-z]+$/i.test(value);
		  return this.optional(element) || /^[A-Za-zÁÉÍÑÓÚáé íñó]*$/.test(value);		 
		}, "Este campo solo acepta letras"); 

jQuery.validator.addMethod("positivo",function(value,element){	
	return value>0;
	},"El valor del número ingresado debe ser mayor que cero.");


jQuery.validator.addMethod("cedula",function(value,element){
	  var cedula = value;
	  array = cedula.split( "" );
	  num = array.length;
	  if ( num == 10 )
	  {
	    total = 0;
	    digito = (array[9]*1);
	    for( i=0; i < (num-1); i++ )
	    {
	      mult = 0;
	      if ( ( i%2 ) != 0 ) {
	        total = total + ( array[i] * 1 );
	      }
	      else
	      {
	        mult = array[i] * 2;
	        if ( mult > 9 )
	          total = total + ( mult - 9 );
	        else
	          total = total + mult;
	      }
	    }
	    decena = total / 10;
	    decena = Math.floor( decena );
	    decena = ( decena + 1 ) * 10;
	    final = ( decena - total );
	    if ( ( final == 10 && digito == 0 ) || ( final == digito ) ) {
	     // alert( "La c\xe9dula ES v\xe1lida!!!" );
	      return true;
	    }
	    else
	    {
	      //alert( "La c\xe9dula NO es v\xe1lida!!!" );
	      return false;
	    }
	  }
	  else
	  {
	    //alert("La c\xe9dula no puede tener menos de 10 d\xedgitos");
	    return false;
	  }
	
}, "Cedula Incorrecta");


$.validator.addMethod('decimal', function(value, element) {
    return /^[0-9.]+$/.test(value); 
}, "Por favor ingrese un número correcto, Para separar decimales utilize el punto (.)");
