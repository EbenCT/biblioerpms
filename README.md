# Sistema de Biblioteca - Backend

Backend para sistema de gestión de biblioteca desarrollado con Spring Boot, GraphQL y PostgreSQL.

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.3.12
- Spring Data JPA
- Spring Security
- GraphQL
- PostgreSQL
- MapStruct
- JWT
- Lombok
- Maven

## Configuración

### Base de Datos

1. Crear base de datos PostgreSQL:
```sql
CREATE DATABASE biblioerpdb;
```

2. Configurar credenciales en `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/biblioerpdb
    username: postgres
    password: password
```

### Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

## Endpoints

### Autenticación REST
- `POST /auth/signin` - Iniciar sesión
- `POST /auth/signup` - Registrar usuario

### GraphQL
- `POST /graphql` - Endpoint principal de GraphQL
- `GET /graphiql` - Interfaz GraphiQL para pruebas

## Usuarios de Prueba

Después de la primera ejecución, se crearán automáticamente:

- **Admin**: admin@biblioteca.com / admin123
- **Bibliotecario**: librarian@biblioteca.com / librarian123
- **Usuario**: user@biblioteca.com / user123

## Consultas GraphQL de Ejemplo

### Obtener todos los ejemplares
```graphql
query {
  ejemplares(page: 0, size: 10) {
    content {
      id
      nombre
      stock
      editorial
      tipo {
        nombre
      }
      autor {
        nombre
        nacionalidad
      }
    }
    totalElements
    totalPages
  }
}
```

### Crear un préstamo
```graphql
mutation {
  crearPrestamo(input: {
    fechaInicio: "2024-01-15"
    fechaDevolucion: "2024-01-30"
    miembroId: 1
    detalles: [
      {
        cantidad: 1
        ejemplarId: 1
      }
    ]
  }) {
    id
    fechaInicio
    fechaDevolucion
    miembro {
      nombre
    }
    detalles {
      cantidad
      ejemplar {
        nombre
      }
    }
  }
}
```

### Buscar miembros
```graphql
query {
  buscarMiembros(nombre: "Juan", page: 0, size: 10) {
    content {
      id
      ci
      nombre
      direccion
      celular
      edad
    }
  }
}
```

### Obtener préstamos vencidos
```graphql
query {
  prestamosVencidos {
    id
    fechaDevolucion
    miembro {
      nombre
      ci
    }
    detalles {
      cantidad
      ejemplar {
        nombre
      }
    }
  }
}
```

## Estructura del Proyecto

```
src/main/java/com/biblioteca/
├── config/              # Configuraciones
├── controller/          # Controladores GraphQL y REST
├── dto/                # DTOs y clases de transferencia
├── entity/             # Entidades JPA
├── mapper/             # Mappers MapStruct
├── repository/         # Repositorios JPA
├── security/           # Configuración de seguridad JWT
└── service/            # Servicios de negocio
```

## Características

- **Autenticación JWT**: Sistema completo de autenticación
- **Autorización por roles**: Admin, Bibliotecario, Usuario
- **API GraphQL**: Consultas flexibles y eficientes
- **Paginación**: Soporte para paginación en listas
- **Gestión de stock**: Control automático de stock en préstamos
- **Validaciones**: Validaciones completas en entidades y DTOs
- **Mapeo automático**: MapStruct para conversión entity-DTO
- **Datos iniciales**: Carga automática de datos de prueba

## Funcionalidades Implementadas

### Gestión de Usuarios
- Registro y autenticación
- Roles y permisos
- Perfil de usuario

### Gestión de Miembros
- Registro de miembros
- Búsqueda por nombre y CI
- Historial de préstamos

### Gestión de Ejemplares
- Catálogo de libros y materiales
- Control de stock
- Búsqueda por nombre, autor, tipo
- Estados de ejemplares

### Sistema de Préstamos
- Crear préstamos múltiples
- Control de fechas
- Actualización automática de stock
- Consulta de préstamos vencidos

### Catálogos
- Autores con nacionalidad
- Tipos de materiales
- Estados de ejemplares

## Seguridad

- Contraseñas encriptadas con BCrypt
- Tokens JWT para autenticación
- CORS configurado para desarrollo
- Filtros de seguridad personalizados

## Base de Datos

El sistema utiliza PostgreSQL con las siguientes tablas principales:
- `usuario` - Usuarios del sistema
- `rol` - Roles de usuario
- `miembro` - Miembros de la biblioteca
- `ejemplar` - Libros y materiales
- `prestamo` - Préstamos realizados
- `detalle_prestamo` - Detalles de cada préstamo
- `autor` - Autores de los ejemplares
- `tipo` - Tipos de materiales
- `estado` - Estados de los ejemplares

## Desarrollo

Para agregar nuevas funcionalidades:

1. Crear/modificar entidades en `entity/`
2. Actualizar repositorios en `repository/`
3. Crear DTOs en `dto/`
4. Implementar mappers en `mapper/`
5. Desarrollar servicios en `service/`
6. Crear controladores GraphQL en `controller/`
7. Actualizar esquema GraphQL en `resources/graphql/`

## Contribución

1. Fork el proyecto
2. Crear branch para feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push al branch (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request