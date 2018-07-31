# HSPC Reference Auth Parent

Welcome to the HSPC Reference Authorization server!  The HSPC Reference Authorization server contains a MITRE OpenID Connect server in two flavors, a MySQL-backed and an LDAP-backed web application.  This version is the LDAP-backed version.

# HSPC Sandbox

*Note:* If you are wanting to build and test SMART on FHIR Apps, it is recommended that you use the free cloud-hosted version of the HSPC Sandbox.

[HSPC Sandbox](https://sandbox.hspconsortium.org)

## reference-auth-server-webapp

The auth server can be run in three modes, OIC (local mysql), Firebase (test account server), and LDAP.  A run-local script exists for each of these modes:

* "run-local.sh firebase" (preferred, uses the hspc-test account system) 
* "run-local.sh local" (uses the OIC support for user accounts in the local mysql database) 
* "run-local.sh ldap" (uses an external LDAP system for user accounts) 

## Maven Build
    mvn package

## Docker Install
    cd docker/nginx
    ./build.sh
    cd ..
    ./build.sh
    docker-compose up
    
The service is available at: 
    http://localhost:8060/

