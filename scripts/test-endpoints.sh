#!/bin/bash

# Base URL
BASE_URL="http://localhost:8080/api/v1"

# Headers comunes
X_GUID="550e8400-e29b-41d4-a716-446655440000"
X_APP="test-app"

echo "=== 1. Crear una cuenta ==="
curl -X POST "$BASE_URL/accounts" \
  -H "Content-Type: application/json" \
  -H "x-guid: $X_GUID" \
  -H "x-app: $X_APP" \
  -d '{
    "clientId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "accountNumber": "1234567890",
    "accountType": "SAVINGS",
    "initialBalance": 1000.50,
    "status": true
  }' -v

echo -e "\n\n=== 2. Listar todas las cuentas ==="
curl -X GET "$BASE_URL/accounts" \
  -H "x-guid: $X_GUID" \
  -H "x-app: $X_APP" -v

echo -e "\n\n=== 3. Listar cuentas por clientId ==="
curl -X GET "$BASE_URL/accounts?clientId=f47ac10b-58cc-4372-a567-0e02b2c3d479" \
  -H "x-guid: $X_GUID" \
  -H "x-app: $X_APP" -v

echo -e "\n\n=== 4. Obtener cuenta por ID (Usar un ID real de la respuesta 1 o 2) ==="
# Reemplazar ACCOUNT_ID con un valor real
ACCOUNT_ID="PONER_AQUI_EL_ACCOUNT_ID"
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID" \
  -H "x-guid: $X_GUID" \
  -H "x-app: $X_APP" -v

echo -e "\n\n=== 5. Reemplazar cuenta (PUT) ==="
curl -X PUT "$BASE_URL/accounts/$ACCOUNT_ID" \
  -H "Content-Type: application/json" \
  -H "x-guid: $X_GUID" \
  -H "x-app: $X_APP" \
  -d '{
    "accountType": "CURRENT",
    "status": true
  }' -v

echo -e "\n\n=== 6. Actualizar parcialmente (PATCH) ==="
curl -X PATCH "$BASE_URL/accounts/$ACCOUNT_ID" \
  -H "Content-Type: application/json" \
  -H "x-guid: $X_GUID" \
  -H "x-app: $X_APP" \
  -d '{
    "accountType": "SAVINGS"
  }' -v

echo -e "\n\n=== 7. Eliminar/Desactivar cuenta (DELETE) ==="
curl -X DELETE "$BASE_URL/accounts/$ACCOUNT_ID" \
  -H "x-guid: $X_GUID" \
  -H "x-app: $X_APP" -v