# 회원가입

**URL** : `/users/join`

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
    "email": "string"
  }
}
```

## Success Response

**Code** : `201 CREATED`