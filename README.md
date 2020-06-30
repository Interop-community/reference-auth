# Logica Reference Auth Parent

Welcome to the Logica Reference Authorization server!  The Logica Reference Authorization server contains MITRE OpenID Connect server as a MySQL-backed web application.

# Logica Sandbox

*Note:* If you are wanting to build and test SMART on FHIR Apps, it is recommended that you use the free cloud-hosted version of the Logica Sandbox.

[Logica Sandbox](https://sandbox.logicahealth.org)

## reference-auth-server-webapp

### How do I set up?
This project uses Maven and Java 11.  Please make sure that your Project SDK is set to use Java 11.

### Preconditions
Need a mysql an empty mysql schema named `oic`. The default Character Set for the schema needs to be `Latin1`. This schema will be populated as you start your server.

Set up your local keycloak server and add reference-auth as a client 

Edit the following keycloak settings in auth.properties to match your local keycloak settings

    keycloak.realm=YOUR KEYCLOAK REALM
    keycloak.authServerUrl=REPLACE THIS WITH KEYCLOAK URL
    keycloak.sslRequired=external
    keycloak.resource=reference-auth
    keycloak.verifyTokenAudience=true
    keycloak.credentials.secret=REPLACE THIS WITH KEYCLOAK SECRETE
    keycloak.logoutSuccessUrl=KEYCLOAK_URL/auth/realms/NAME_OF_THE_REALM/protocol/openid-connect/logout?redirect_uri=http://localhost:3001
    

#### Step 1: Maven Build
In the terminal, run the following command:

    mvn package

#### Step 2: Run the application by executing the following command from the reference-auth-server-webapp directory:
 
`mvn jetty:run -Dspring.profiles.active=users-keycloak,local`

###### For Docker Installation

    cd docker/nginx
    ./build.sh
    cd ..
    ./build.sh
    docker-compose up
    
The set up process is complete now. You are now able to run the project. 
The service is available at: 
    http://localhost:8060/

