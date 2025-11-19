import org.springframework.cloud.contract.spec.Contract

Contract.make {
  description "Get user by id returns details when API key is present"
  request {
    method GET()
    urlPath(
      value(
        consumer(regex("/internal/user/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")),
        producer("/internal/user/123e4567-e89b-12d3-a456-426614174000")
      )
    )
    headers {
      header "X-API-Key", equalTo("test-api-key")
      header "Accept", "application/json"
    }
  }
  response {
    status OK()
    headers {
      contentType(applicationJson())
    }
    body([
      // Echo the id from the request path segment: /internal/user/{id}
      id   : fromRequest().path(2),
      name : "John Doe",
      email: "john.doe@example.com",
      role : "ROLE_HOTEL_OWNER"
    ])
  }
}
