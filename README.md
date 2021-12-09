# Platform login

Este proyecto permite gestionar los nombres de usuario y contraseñas de una plataforma. Existen dos tipos de usuarios posibles: un administrador y los usuarios comunes. El usuario administrador puede consultar los nombres de los usuarios existentes, eliminar un usuario o poner en blanco la contraseña de un usuario. Los usuarios comunes pueden consultar su última fecha/hora de login, y cambiar su contraseña. Además, se dispone de la funcionalidad de crear un nuevo usuario.

## Proceso de implementación: 
Primero, investigamos cómo se debía emplear el algoritmo PBKDF2 y de esta manera, construimos el código contando con una clase PasswordGenerator que permite hacer el hashing de las contraseñas de los usuarios. Después, realizamos la conexión con la base de datos Postgres en donde se almacena la información de cada nombre de usuario con su contraseña respectiva. 
Al contar con las anteriores implementaciones, se realizó un diseño de interfaz en donde tanto el administrador como el usuario común pudieran realizar las acciones que le corresponde a cada uno. De esta manera, se implementó las clases Controller que permiten la interacción para cada función del proyecto teniendo la conexión correspondiente con la base de datos.

## Dificultades presentadas
*Implementación del algoitmo PBKDF2: la investigación requirió que se comprendiera muy bien cómo emplearlo, por lo que se dedicó ciertas horas para saber cómo podríamos anexarlo de la mejor manera para que no se presentaran errores al momento de unirlo con las clases controller para la interfaz.
*Conexión base de datos Postgres: hubo problemas al conectar la base de datos por medio de Eclipse que fue el IDE que utilizamos, y luego se presentaron algunos problemas de sintaxis en el URL de conexión.

## Conclusiones
Construir un programa que cuente con una seguridad robusta es una tarea compleja, puesto que al principio intentamos hacer una implementación con una aplicación web y, en ese caso, debíamos tener en cuenta que como estabamos trabajando tanto en front como en back, la información que se enviaba entre ellos, debía protegerse tanto al consumir como al exponer los servicios. Así que, escogimos priorizar el desarrollo de un programa que cumpliera con las expectativas y el alcance del proyecto final definidos para este curso. De esta manera, pudimos emplear el algoritmo PBKDF2 para el hashing de las constraseñas.
