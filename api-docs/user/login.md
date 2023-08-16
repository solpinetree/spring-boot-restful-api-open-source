# 로그인 

**URL** : `/users/login`

**Method** : `POST`

**Auth required** : NO

## Request

```json
{
  "email": "string",
  "password": "stringst"
}
```

## Response

```json
{
  "resultCode": "string",
  "resultMessage": "string",
  "result": {
    "userId": 0,
    "email": "string",
    "token": "string"
  }
}
```

## Success Response

**Code** : `201 CREATED`