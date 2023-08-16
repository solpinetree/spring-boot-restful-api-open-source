# 게시글 삭제

**URL** : `/posts/{postId}`

**Method** : `DELETE`

**Auth required** : YES

## Request

```
Request Parameter
`postId`
```

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
  "result": {}
}
```

## Success Response

**Code** : `204 NO CONTENT`