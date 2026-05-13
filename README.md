# Ays Microservice Account - Back-end

Este es el microservicio encargado de la gestión de Clientes, Cuentas y Movimientos financieros para la plataforma Ays.

## 🚀 Despliegue en Digital Ocean

El sistema se encuentra desplegado y puede ser accedido a través del Shell (Frontend) en la siguiente URL:
👉 [https://ays-shl-account-manage-35jnj.ondigitalocean.app/login](https://ays-shl-account-manage-35jnj.ondigitalocean.app/login)

## 🌐 Ecosistema de Proyectos

Este microservicio forma parte de una arquitectura distribuida compuesta por los siguientes repositorios:

### Microfrontends (Frontend)
- **[Shell Account Management](https://github.com/chili7777/ays-shl-account-management)**: Orquestador principal de la aplicación.
- **[MFA Customer](https://github.com/chili7777/ays-mfa-customer)**: Microfrontend para la gestión de clientes.
- **[MFA Account](https://github.com/chili7777/ays-mfa-account)**: Microfrontend para la gestión de cuentas.
- **[MFA Movements](https://github.com/chili7777/ays-mfa-movements)**: Microfrontend para la gestión de movimientos.

### Microservicios (Backend)
- **[MSA Account (Core)](https://github.com/chili7777/ays-msa-dm-cuaa-cr-account)**: (Este repositorio) Gestión de clientes, cuentas y lógica de negocio.
- **[MSA Movement](https://github.com/chili7777/ays-msa-dm-pain-cr-movement)**: Procesamiento especializado de transacciones.

### Otros
- **[Custom Instructions](https://github.com/chili7777/ays-custom-instructions)**: Guías y configuraciones compartidas del proyecto.

## 🏗️ Arquitectura

El proyecto sigue una **Arquitectura Hexagonal (Puertos y Adaptadores)** combinada con principios de **DDD (Domain-Driven Design)** para garantizar un desacoplamiento claro entre la lógica de negocio y los detalles de infraestructura.

- **Domain**: Entidades, Objetos de Valor y Excepciones de negocio.
- **Application**: Casos de uso (Input Ports) y definiciones de salida (Output Ports).
- **Infrastructure**: Implementaciones de persistencia (JPA/PostgreSQL), controladores REST, adaptadores de PDF (OpenPDF) y configuraciones.

## ✨ Características Principales

1.  **Gestión de Clientes**: CRUD completo con contraseñas hasheadas y validación de identificación única.
2.  **Gestión de Cuentas**: Creación y administración de cuentas asociadas a clientes, con validación de existencia de cliente y número de cuenta único.
3.  **Movimientos y Validaciones**:
    - **Saldos**: Validación de saldo disponible para débitos (retornos con error "Saldo no disponible").
    - **Límite Diario**: Límite de retiro diario configurable (por defecto $1,000). Si se excede, retorna "Cupo diario Excedido".
    - **Montos Firmados**: Los depósitos se almacenan como valores positivos y los débitos como negativos.
4.  **Reportes**:
    - Generación de estados de cuenta por rango de fechas y cliente.
    - Formatos disponibles: **JSON**, **PDF (Binario)** y **Base64**.
5.  **Robustez**: Manejo global de excepciones para transformar errores de integridad de base de datos en respuestas HTTP 400 amigables.

## 🔑 Credenciales de Prueba

Para probar las funcionalidades de la aplicación, puede utilizar las siguientes credenciales:

### Admin (BackOffice)
- **Usuario**: `0103322228`
- **Contraseña**: `asdfghjk`

### Usuarios de la App (Transferencias)
1. **Usuario**: `1718048232` | **Contraseña**: `qwertyui`
2. **Usuario**: `1718048315` | **Contraseña**: `zxcvbnmq`

> **Nota**: El registro de nuevos usuarios está habilitado. Las cuentas se inicializan con saldo para facilitar las pruebas de transferencias inmediatas.

## 🛠️ Requisitos Técnicos

- **Java 21** (JDK 21)
- **Gradle 8.x**
- **Base de Datos**:
  - Local: **H2** (en memoria).
  - Producción/Staging: **PostgreSQL**.

## 🏃 Cómo Correr el Proyecto

### Localmente

1.  Clonar el repositorio.
2.  Ejecutar el comando de construcción:
    ```bash
    ./gradlew clean build
    ```
3.  Lanzar la aplicación:
    ```bash
    ./gradlew bootRun
    ```
    La aplicación estará disponible en `http://localhost:8080` (por defecto).

### Pruebas

Para ejecutar la suite completa de pruebas unitarias e integración (más de 75 tests):
```bash
./gradlew test
```

## ⚙️ Configuración (Variables de Entorno)

La aplicación es configurable mediante variables de entorno para facilitar el despliegue en contenedores:

- `AYS_MSA_CR_ACCOUNT_PROFILE`: Perfil activo (`default`, `staging`, `production`).
- `DATABASE_URL`: URL de la base de datos (Soporta formato Digital Ocean `postgresql://`).
- `DB_USER`: Usuario de BD.
- `DB_PASSWORD`: Contraseña de BD.
- `AYS_MSA_CR_ACCOUNT_PORT`: Puerto de escucha (por defecto 8080).

## 📄 Documentación API

La API está documentada siguiendo el estándar OpenAPI. Puedes encontrar la especificación en:
- Archivo local: `src/main/resources/openapi.yaml`
- Colección de Postman: `Account Microservice Full CRUD API.postman_collection.json` (ubicada en la raíz).

## 📝 Consideraciones Importantes

- **Validación de Unicidad**: El backend previene la creación de identificaciones o números de cuenta duplicados, retornando un error 400 controlado.
- **Formato de Montos**: Al realizar un débito, el sistema valida que el monto enviado sea positivo y luego lo transforma internamente a negativo para su almacenamiento.
- **Configuración Dinámica**: El límite diario se lee de la tabla `system_parameters`. Si el parámetro `DAILY_DEBIT_LIMIT` no existe en la BD, el sistema utiliza el valor de $1,000 por defecto.