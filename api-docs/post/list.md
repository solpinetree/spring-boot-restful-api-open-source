# 게시글 목록 조회

**URL** : `/posts`

**Method** : `GET`

**Auth required** : YES

## Request

```
Request Parameter
`page`
`size`
`sort`
```

## Response

```json
{
  "resultCode": "string",
  "resultMessage": "string",
  "result": {
    "totalPages": 0,
    "totalElements": 0,
    "size": 0,
    "content": [
      {
        "id": 0,
        "title": "string",
        "body": "string",
        "user": {
          "userId": 0,
          "email": "string",
          "token": "string"
        },
        "registeredAt": "2023-08-16T13:13:22.610Z",
        "updatedAt": "2023-08-16T13:13:22.610Z"
      }
    ],
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": true,
      "unsorted": true
    },
    "numberOfElements": 0,
    "pageable": {
      "offset": 0,
      "sort": {
        "empty": true,
        "sorted": true,
        "unsorted": true
      },
      "pageNumber": 0,
      "pageSize": 0,
      "paged": true,
      "unpaged": true
    },
    "first": true,
    "last": true,
    "empty": true
  }
}
```

## Success Response

**Code** : `200 OK`