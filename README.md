# JCCact4_t4

API REST con Spring Boot, Spring Security y JWT. Maneja libros y autores (un autor tiene muchos libros) y solo responde JSON. Las pruebas están en una colección de Bruno incluida en la carpeta bruno.

API en el VPS: http://20.119.81.138:8086

## Tecnologías

- Spring Boot 4, Spring Web, Spring Data JPA, Bean Validation
- Spring Security con JWT (librería jjwt)
- MySQL
- Bruno para las pruebas

## Estructura

```
com.example.demoCrud
├── models/       Usuario, Autor, Libro
├── repository/   repositorios JPA
├── service/      lógica de negocio, JwtService, AuthService
├── security/     SecurityConfig y JwtAuthFilter
├── controllers/  AuthController, AutorController, LibroController
├── dto/          objetos de entrada y salida
└── exception/    manejo de errores
bruno/            colección de Bruno
```

## Cómo funciona la seguridad

1. El usuario se registra en /api/auth/register. La contraseña se guarda cifrada con BCrypt.
2. Con /api/auth/login manda su usuario y contraseña y recibe un token JWT.
3. En las demás peticiones envía el header Authorization: Bearer TOKEN.
4. El filtro JwtAuthFilter revisa el token en cada petición. Si es válido, deja pasar. Si falta o no sirve, responde 401.

## Endpoints

| Método | Ruta | Requiere token | Descripción |
|--------|------|----------------|-------------|
| POST | /api/auth/register | No | Registrar usuario (201) |
| POST | /api/auth/login | No | Iniciar sesión y recibir el token (200) |
| GET | /api/libros?page=0&size=10 | Sí | Listar libros con paginación (200) |
| GET | /api/libros/{id} | Sí | Ver un libro (200, 404) |
| POST | /api/libros | Sí | Crear libro (201, 400) |
| PUT | /api/libros/{id} | Sí | Actualizar libro (200, 400, 404) |
| DELETE | /api/libros/{id} | Sí | Eliminar libro (204, 404) |
| GET, POST, PUT, DELETE | /api/autores | Sí | Mismo CRUD para autores |

Códigos usados: 200, 201, 204, 400 (validación fallida), 401 (sin token o token inválido), 404, 409 (duplicado o autor con libros).

Ejemplo de registro:

```json
{ "username": "jesus", "email": "jesus@correo.com", "password": "123456" }
```

Respuesta (nunca incluye la contraseña):

```json
{ "id": 1, "username": "jesus", "email": "jesus@correo.com" }
```

Ejemplo de error de validación (400):

```json
{
  "status": 400,
  "error": "Validación fallida",
  "errores": { "titulo": "El título es obligatorio" }
}
```

## Configuración

Se define con variables de entorno.

| Variable | Descripción |
|----------|-------------|
| SERVER_PORT | Puerto de la API (8086 en el VPS) |
| DB_URL | URL de la base de datos (opcional) |
| DB_USER | Usuario de la base de datos |
| DB_PASS | Contraseña de la base de datos |
| JWT_SECRET | Clave para firmar los tokens (mínimo 32 caracteres) |
| JWT_EXPIRATION_MS | Duración del token en milisegundos (opcional) |

## Colección de Bruno

Está en la carpeta bruno. Para usarla: abrir Bruno, Open Collection, elegir la carpeta bruno y seleccionar el entorno vps. Orden recomendado: Registro, Login (guarda el token solo), crear autor, y luego las peticiones de libros. La carpeta 4-Seguridad prueba que sin token responde 401.

## Despliegue en el VPS

```
mvn clean package -DskipTests
scp target/act4-0.0.1-SNAPSHOT.jar azureuser@20.119.81.138:~/act4/
```

En el VPS se crea la base de datos act4_db, un usuario de MySQL y un servicio systemd que ejecuta el jar en el puerto 8086. Los pasos completos están en instrucciones_vps_act4.txt.

## Capturas de pantalla

Registro:

![Registro](docs/capturas/01-registro.png)

Login con token:

![Login](docs/capturas/02-login.png)

Crear:

![Crear](docs/capturas/03-crear.png)

Listar con paginación:

![Listar](docs/capturas/04-listar.png)

Actualizar:

![Actualizar](docs/capturas/05-actualizar.png)

Eliminar:

![Eliminar](docs/capturas/06-eliminar.png)

Petición sin token rechazada con 401:

![Sin token](docs/capturas/07-sin-token-401.png)

Validación fallida con 400:

![Validación](docs/capturas/08-validacion-400.png)
