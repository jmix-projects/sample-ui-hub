# Sample UI Hub

## Running Locally

1. Create different host names for the appplications running on localhost. Add the following lines to your `hosts` file:
    ```
    127.0.0.1       host0
    127.0.0.1       host1
    127.0.0.1       host2
    ```

2. Run Keycloak on port 8180:
    ```
    docker run -p 8180:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin --name=keycloak quay.io/keycloak/keycloak:18.0.2 start-dev
    ```
    Admin UI will be available at `http://localhost:8180/admin`. Log in as admin/admin.

3. Configure Keycloak
   1. Create `sample-hub` realm.
   2. Create `hub` and `customers` clients. Set Access Type to `confidential`.
   3. For both clients, create a mapper on Mappers tab:
      - Type: `User Realm Role`
      - Token Claim Name: `roles`    
   4. Create `system-full-access`, `ui-minimal`, `worker`, `manager` roles.
   5. Create users: 
      - `admin` with `system-full-access` role
      - `alice` with `ui-minimal` and `manager` roles
      - `bob` with `ui-minimal` and `worker` roles
    
        When setting passwords for users, turn off Temporary flag.
        Roles are assigned on the Role Mappings tab.

4. Open `hub` project in IDE and set `spring.security.oauth2.client.registration.keycloak.client-secret` property to the value obtained from the Credentials tab of the `hub` application in Keycloak. Do the same with `customers` project.

5. Run both `hub` and `customers` projects.

6. Open `http://host0:8080` in web browser. You will be redirected to Keycloak login form.
