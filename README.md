# HSPC Reference Auth Parent

Welcome to the HSPC Reference Authorization server!  The HSPC Reference Authorization server contains a MITRE OpenID Connect server in two flavors, a MySQL-backed and an LDAP-backed web application.  This version is the LDAP-backed version.

# HSPC Sandbox

*Note:* If you are wanting to build and test SMART on FHIR Apps, it is recommended that you use the free cloud-hosted version of the HSPC Sandbox.

[HSPC Sandbox](https://sandbox.hspconsortium.org)

## reference-auth-server-webapp

The auth server can be run in three modes, OIC (local mysql), Firebase (test account server), and LDAP.  A run-local script exists for each of these modes:

* run-local_firebase.sh (preferred, uses the hspc-test account system) 
* run-local_local.sh (uses the OIC support for user accounts in the local mysql database) 
* run-local_ldap.sh (uses an external LDAP system for user accounts) 

## How do I get set up?

### Preconditions
    
* Install MySQL 5.7
* Will need to use creds root/password
* Create oic schema. Use "latin1 - default collation"

### (Optional) Install ApacheDS LDAP Server

Home:
    https://directory.apache.org/apacheds/

Download:
    https://directory.apache.org/apacheds/downloads.html

Apache Directory Studio (UI For ApacheDS, distributes with it's own ApacheDS)
    https://directory.apache.org/studio/downloads.html


Create User account/ Group etc...
    export from \reference-auth\ldap\src\main\resources\ldap\hspc.ldif

### Build and Run
    mvn clean install
    ./run-local_firebase.sh (or the mode of your choice)

### Verify
* http://localhost:8060/

## Where to go from here
https://healthservices.atlassian.net/wiki/display/HSPC/Healthcare+Services+Platform+Consortium