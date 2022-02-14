# Detector-Mutantes

Esta aplicacion se detecta los Humanos que segun su secuencia de ADN son mutantes y se registra si son o no en una base de datos.

#  API REST

La API REST de la aplicación de ejemplo se describe a continuación.

##  Obtener lista de humanos registrados 

###  Solicitud

  `GET /human`

    https://detector-mutante.rj.r.appspot.com/human
    
   ### Response

      []
      
## Crear Humano 
  
  Este servicio nos permite guardar una secuencia de ADN de un humano y determina si es o no mutante

### Request

`POST /human`

    https://detector-mutante.rj.r.appspot.com/human
    
    #### Body
    { 
      "adn":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
    }
 
### Response
  
    {
        "id": 1,
        "mutant": true,
        "secuenciaADN": "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG"
    }
    
## Obtener un humano por id

### Request

`GET /human/{id}`

    https://detector-mutante.rj.r.appspot.com/human/{id}

### Response

    {
    "id": 1,
    "mutant": true,
    "secuenciaADN": "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG"
}

## Verificar si es mutante

Este servicio identifica si la secuencia de ADN ingresada es de un mutante, 
esto verificando que en la matriz de ADN exista mas serie de cuatro 
caracteres iguales en horizontal, vertical y diagonal.

para esto primero consulta si esta secuencia ya ha sido verificada si es asi solo consulta el valor is_mutant del humano
si no la procesa y la guarda en la base de datos y arroja el resultado de la validacion

### Request

`POST /human/mutant`

    https://detector-mutante.rj.r.appspot.com/human/mutant
    
    #### Body
    { 
      "adn":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
    }

### Response

  true







