# PruebaTecnica 

-[Descripcion](Descripcion)
-[Instalacion](Instalacion)

# Descripcion
***
Proyecto de ATM, usando spring boot 3 y mysql 8 
El proyecto consta de lo siguiente:
El cliente tiene que registrarse en el endpoint /bank/auth/nuevoCliente el cual crea una cuenta con el saldo colocado en la creacion del usuario.
luego de la creacion de la cuenta para poder realizar transacciones el cliente tiene que iniciar sesion desde el enpoint /bank/auth/login, si todo esta
correcto el servicio mostrara el token que tiene que usar para las transacciones

Los endpoint /bank/atm/mostrarSaldo y /bank/atm//transaccionAccount/ tendran que usar el token que se proporciono en la respuesta del login 
y colocar ese token en los header de los endpoint para realizar transacciones.

En el endpoint de mostrarSaldo colocar la cuenta que se solicita en el parametro de la url y el token en los headers.
En el endpoint de transaccion colocar la cuenta que se solicita en el parametro de la url, el token en los headers y los parametros en el cuerpo de la peticion
las transacciones que se pueden hacer son las siguiente:
- Debito para retirar efectivo.
- credito para abonar.


# Instalacion
***
Para poder realizar la ejecucion del proyecto en **local** se tiene que tener los siguientes recursos
1. JDK
2. MYSQL

Posteriormente antes de ejecutar, crear la base de datos y luego se tiene que colocar las variables de ejecucion siguientes

${hostDb}
${userDb}
${passwordDb}
Con todo lo anteriormente dicho configurado se puede realizar la ejecucion local

Para poder realizar la ejecucion de proyecto en **Docker** se tiene que tener docker compose. Si lo tiene instalado 
el paso siguiente que se tiene que realizar es colocarse en la carpeta del proyecto, abrir un terminal y ejecutar en el terminal 
docker compose up, el creara todos los recursos del proyecto

los puertos establecidos son los siguiente:
aplicacion spring boot 8080
MYSQL 3307

