# Healthier local demo API

This contract belongs to the local development server only. It is not a production API.

## `GET /v1/cities/{cityId}/clinics`

Returns demo clinics for a city from the server seed catalog.

Success (`200 OK`):

```json
{
  "version": 1,
  "cityId": "tbilisi",
  "clinics": [
    {
      "id": "tbilisi-central",
      "cityId": "tbilisi",
      "name": "Healthier Central",
      "specialization": "Multidisciplinary clinic",
      "address": "Demo address, Tbilisi",
      "latitude": 41.7151,
      "longitude": 44.8271,
      "imageUrl": null
    }
  ]
}
```

Unknown city (`404 Not Found`):

```json
{
  "code": "city_not_found",
  "message": "No demo clinics are configured for this city"
}
```

The client treats non-2xx responses, timeouts, connectivity failures and invalid payloads as
typed failures. Cancellation is always rethrown. No authorization header is currently required.
