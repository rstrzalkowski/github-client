Tech stack: Java 21, Spring Boot 3.3.2
Testing tools: JUnit 5, WireMock, RestAssured

To list all github user repositories use following REST endpoint

`GET /api/github/{username}/repositories`

{username} - username of github user

Example response:

`GET /api/github/rstrzalkowski/repositories`

```json
[
  {
    "owner": "rstrzalkowski",
    "name": "DESX",
    "branches": [
      {
        "name": "main",
        "lastCommitSHA": "c4ad1ad9c3fbe0b3c59fd9e1ed7e163953107923"
      }
    ]
  },
  {
    "owner": "rstrzalkowski",
    "name": "E-Market",
    "branches": [
      {
        "name": "master",
        "lastCommitSHA": "9817425f992ff6d4cad0f2dac9ba639a27804924"
      }
    ]
  },
  {
    "owner": "rstrzalkowski",
    "name": "Non-relational-databases",
    "branches": [
      {
        "name": "CASSANDRA",
        "lastCommitSHA": "8edc1dc8a83692b8d3a902f531f941298d3dd93f"
      },
      {
        "name": "KAFKA",
        "lastCommitSHA": "23376de6cf22b00c8c762d3a9ce7e8b57096ae3d"
      },
      {
        "name": "MONGO",
        "lastCommitSHA": "c6cbe39bc95deed2704fbc01f179626fef05bef1"
      },
      {
        "name": "ORM",
        "lastCommitSHA": "2e11ca7f531f3f54ae46e0f9c912e96c910e1958"
      },
      {
        "name": "REDIS",
        "lastCommitSHA": "64a2fee914cc7c25df607e59cd834cb9f7bb04c1"
      },
      {
        "name": "main",
        "lastCommitSHA": "889ec109ea4bd4031209812227ebd2c64e860f50"
      }
    ]
  },
  ...
]
```

When given non existent user, response will be the following

```json
{
  "status": 404,
  "message": "User not found"
}
```

