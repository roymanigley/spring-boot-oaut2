# Oauth2 and Exception Handling

## Setup
1. Start the docker env  

        docker-compose up -d -f docker
        docker-compose.yml

2. Setup Keycloak ([keycloak guide](https://www.keycloak.org/docs/latest/getting_started/index.html#creating-a-realm-and-a-user))

    ![keycloak-client](docs/keycloak-client.png)

3. Start spring boot app  

        ./mvnw spring-boot:run

4. Login http://localhost:8080