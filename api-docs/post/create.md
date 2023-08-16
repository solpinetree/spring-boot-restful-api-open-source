# 회원가입

**URL** : `/posts`

**Method** : `POST`

**Auth required** : YES

## Request

```json
{
  "title": "string",
  "body": "stringstri"
}
```

## Response

```json
{
  "resultCode": "string",
  "resultMessage": "string",
  "result": {
    "id": 0,
    "title": "string",
    "body": "string",
    "user": {
      "userId": 0,
      "email": "string",
      "token": "string"
    },
    "registeredAt": "2023-08-16T13:09:29.086Z",
    "updatedAt": "2023-08-16T13:09:29.086Z"
  }
}
```

## Success Response

**Code** : `201 CREATED`