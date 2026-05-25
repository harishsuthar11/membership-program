
# Membership Program

## Features

- Monthly / Quarterly / Yearly Plans
- Silver / Gold / Platinum Tiers
- Upgrade / Downgrade Membership
- Cancel Membership
- Membership Expiry Tracking
- Strategy Pattern
- Concurrency Handling using @Version
- Swagger Support

## APIs

- GET /api/memberships/plans
- GET /api/memberships/tiers
- POST /api/memberships/subscribe
- PUT /api/memberships/{userId}/upgrade/{tierId}
- PUT /api/memberships/{userId}/downgrade/{tierId}
- DELETE /api/memberships/{userId}

## Run

mvn clean install
mvn spring-boot:run

## Swagger

http://localhost:8080/swagger-ui/index.html
