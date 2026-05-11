# Comandos CURL para probar los Endpoints de Cuentas

Este archivo contiene ejemplos de comandos `curl` para probar cada una de las operaciones del microservicio de cuentas.

**Nota:** Asegúrate de tener la aplicación ejecutándose en `http://localhost:8080`.

## Variables de Entorno Comunes
* **URL Base:** `http://localhost:8080/api/v1`
* **X-Guid:** `550e8400-e29b-41d4-a716-446655440000`
* **X-App:** `curl-test`

---

### 1. Crear Cuenta (POST)
```bash
curl -X POST "http://localhost:8080/api/v1/accounts" \
  -H "Content-Type: application/json" \
  -H "x-guid: 550e8400-e29b-41d4-a716-446655440000" \
  -H "x-app: curl-test" \
  -d '{
    "clientId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "accountNumber": "1234567890",
    "accountType": "SAVINGS",
    "initialBalance": 1000.50,
    "status": true
  }'
```

### 2. Listar Cuentas (GET)
```bash
curl -X GET "http://localhost:8080/api/v1/accounts" \
  -H "x-guid: 550e8400-e29b-41d4-a716-446655440000" \
  -H "x-app: curl-test"
```

### 3. Listar Cuentas por Cliente (GET con query param)
```bash
curl -X GET "http://localhost:8080/api/v1/accounts?clientId=f47ac10b-58cc-4372-a567-0e02b2c3d479" \
  -H "x-guid: 550e8400-e29b-41d4-a716-446655440000" \
  -H "x-app: curl-test"
```

### 4. Obtener Cuenta por ID (GET)
*Reemplazar `{accountId}` por un UUID real.*
```bash
curl -X GET "http://localhost:8080/api/v1/accounts/{accountId}" \
  -H "x-guid: 550e8400-e29b-41d4-a716-446655440000" \
  -H "x-app: curl-test"
```

### 5. Reemplazar Cuenta (PUT)
```bash
curl -X PUT "http://localhost:8080/api/v1/accounts/{accountId}" \
  -H "Content-Type: application/json" \
  -H "x-guid: 550e8400-e29b-41d4-a716-446655440000" \
  -H "x-app: curl-test" \
  -d '{
    "accountType": "CURRENT",
    "status": true
  }'
```

### 6. Actualización Parcial (PATCH)
```bash
curl -X PATCH "http://localhost:8080/api/v1/accounts/{accountId}" \
  -H "Content-Type: application/json" \
  -H "x-guid: 550e8400-e29b-41d4-a716-446655440000" \
  -H "x-app: curl-test" \
  -d '{
    "accountType": "SAVINGS"
  }'
```

### 7. Eliminar/Desactivar Cuenta (DELETE)
```bash
curl -X DELETE "http://localhost:8080/api/v1/accounts/{accountId}" \
  -H "x-guid: 550e8400-e29b-41d4-a716-446655440000" \
  -H "x-app: curl-test"
```