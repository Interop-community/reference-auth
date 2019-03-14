# HSPC Reference Auth Parent

Welcome to the HSPC Reference Authorization server!  The HSPC Reference Authorization server contains a MITRE OpenID Connect server in two flavors, a MySQL-backed and an LDAP-backed web application.  This version is the LDAP-backed version.

# HSPC Sandbox

*Note:* If you are wanting to build and test SMART on FHIR Apps, it is recommended that you use the free cloud-hosted version of the HSPC Sandbox.

[HSPC Sandbox](https://sandbox.hspconsortium.org)

## reference-auth-server-webapp

The auth server can be run in three modes:
 1. OIC (local mysql)
 2. LDAP
 3. Firebase (test account server)  
 
 A run-local script exists for each of these modes:

* "run-local.sh local" (uses the OIC support for user accounts in the local mysql database) 
* "run-local.sh ldap" (uses an external LDAP system for user accounts) 
* "run-local.sh firebase" (preferred, uses the hspc-test account system) 

### How do I set up?
This project uses Java 8.  Please make sure that your Project SDK is set to use Java 8.

### Preconditions
Need a mysql an empty mysql schema named `oic`. The default Character Set for the schema needs to be `Latin1`. This schema will be populated as you start your server.

#### Step 1: Maven Build
In the terminal, run the following command:

    mvn package

#### Step 2: Run the following command depending on the mode you want:
    
###### For Local Installation
 
`./run-local.sh local`

###### For LDAP Installation
`./run-local.sh ldap`

###### For Firebase installation
`./run-local.sh firebase`

###### For Docker Installation

    cd docker/nginx
    ./build.sh
    cd ..
    ./build.sh
    docker-compose up
    
The set up process is complete now. You are now able to run the project. 
The service is available at: 
    http://localhost:8060/

