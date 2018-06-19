### ktor-backend-simple

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


