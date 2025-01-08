Problemas a solucionar: 

1 - No corre el API Gateway: Aun teniendo levantados todos los microservicios (y habiendolos testeado) y la BBDD iniciada, el api gateway no levanta. Por ende tampoco puedo testear con Postman las peticiones desde el puerto del API Gateway.
2 - No toma las peticiones de la ruta "v1/user": Imagino que esto debe pasar porque tengo metido en el mismo microservicio "Auth" y "Users", y creo que es probable que como en el application.yaml del Gateway solo estoy autorizando la ruta "auth", no me lleguen para testear los metodos CRUD de usuarios. Una solucion creo que podria ser separar estos microservicios, pero no se bien como hacerlo.
