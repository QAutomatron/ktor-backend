### ktor-backend-simple

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/eeb5ddd17aa34e58882dce0bb2a82626)](https://app.codacy.com/app/QAutomatron/ktor-backend?utm_source=github.com&utm_medium=referral&utm_content=QAutomatron/ktor-backend&utm_campaign=Badge_Grade_Settings)

How to run:

`./gradlew clean run` to run

##### Project Info:

This is simple ktor based backend written in Kotlin with some JWT auth.
After run server will available on 8090 port

##### Endpoints:

`/status` - return alive message

`/login` - POST with json body: `{ name: "Bob", password: "SuperDuper" }` to receive JWT token

`/auth/jwt` - GET jwt auth protected. Pass `Bearer + jwtToken` as auth header

`/auth/basic` - GET basic auth protected `(test:test)`

`/usr/info` - GET will return JSON with user info (all data except password) based on jwt token


