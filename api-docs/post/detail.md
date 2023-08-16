# 게시글 상세조회

**URL** : `/posts/{postId}`

**Method** : `GET`

**Auth required** : YES

## Request

```
Path Variable
`postId`
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
    "registeredAt": "2023-08-16T13:15:46.530Z",
    "updatedAt": "2023-08-16T13:15:46.530Z"
  }
}
```

## Success Response

**Code** : `200 OK`