# Update Tourist Record with Token Validation (Spring Boot)

## Run
Requires JDK 17+ and Maven.

    mvn spring-boot:run

Swagger UI: http://localhost:8080/swagger-ui.html
H2 console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:touristdb, user: sa, no password)

Two tourists (id 1 and 2) are seeded at startup.

## Valid token (pre-generated, in application.properties)
    HOTEL-CHECKIN-TOKEN-12345

## Test in Swagger UI
PUT /api/tourists/1  -> Token: HOTEL-CHECKIN-TOKEN-12345
Body:
    {
      "visitedDestination": "Kerala",
      "contactNumber": "9999988888"
    }
Result: 200 OK  "Tourist Updated Successfully"

Same request with Token empty / wrong (e.g. WRONG-TOKEN) -> 500 Internal Server Error
("Invalid or missing request token"). Nothing is changed in the DB.
Unknown id with valid token -> 404.

Verify with GET /api/tourists.

## Viva answers
1. PUT vs PATCH
   - PUT replaces the whole resource: the client sends the complete representation and
     anything omitted is conceptually overwritten/reset. It is idempotent.
   - PATCH applies a partial modification: only the fields sent are changed, the rest
     stay as they are. It is not guaranteed to be idempotent (e.g. "increment count").
   - Example: to change only a tourist's phone number, PATCH sends {"contactNumber": "..."};
     strict PUT would require sending name, destination, email too.
     (This project's PUT service updates the fields supplied in the payload.)

2. Why verify the token before the database mutation?
   - Authorization must be decided before any state change; once data is written it may be
     too late (or costly) to undo.
   - Prevents unauthorized/anonymous users from modifying or corrupting records
     (data integrity and security).
   - Fail fast: rejecting bad requests early saves DB reads/writes/connections and
     reduces the attack surface (e.g. brute-force id enumeration, DoS).
   - Avoids leaking information, e.g. whether a record with a given id exists.
   - Keeps transactions clean: no partial updates or rollbacks for requests that were never allowed.
