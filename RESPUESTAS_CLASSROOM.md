a) ¿Qué es un JWT y qué información contiene normalmente?

JWT significa JSON Web Token. Es un texto que el servidor entrega al usuario cuando inicia sesión y que el usuario manda en cada petición para demostrar quién es, sin que el servidor tenga que guardar una sesión. Tiene tres partes separadas por puntos: el header (el algoritmo de firma), el payload (los datos) y la firma. El payload normalmente lleva el usuario (subject), la fecha de emisión y la fecha de expiración, y a veces roles. La firma se hace con una clave secreta del servidor, así nadie puede modificar el token sin que se detecte. No debe llevar contraseñas, porque el payload se puede leer.

b) ¿Cómo protege Spring Security tus endpoints? ¿Qué pasa si intentas llamar uno protegido sin token?

Spring Security pone una cadena de filtros antes de los controladores. En mi proyecto configuré que /api/auth/** sea público y que todo lo demás requiera autenticación. Agregué un filtro (JwtAuthFilter) que lee el header Authorization, valida el token y, si es correcto, marca la petición como autenticada. Si llamo a un endpoint protegido sin token, o con uno falso o vencido, la petición no queda autenticada y el servidor responde 401 con un mensaje en JSON, sin llegar al controlador.

c) ¿Por qué usaste un DTO en vez de exponer directamente tu Entity en las respuestas de la API?

Porque el DTO me deja controlar qué datos entran y salen. Por ejemplo, la entidad Usuario tiene el password, y con un DTO de respuesta nunca se expone. También evita problemas con las relaciones (un autor con libros y cada libro con su autor generaría un ciclo infinito en el JSON), permite validar solo lo que el cliente puede enviar con @Valid, y separa la API de la base de datos: si cambio la tabla, no tengo que cambiar lo que ve el cliente.

d) Enlaces

Repositorio de GitHub: https://github.com/JesusCortes-designer/JCCact4_t4
Link base de la API en el VPS: http://20.119.81.138:8086
La colección de Bruno está en la carpeta bruno del repositorio: https://github.com/JesusCortes-designer/JCCact4_t4/tree/main/bruno
