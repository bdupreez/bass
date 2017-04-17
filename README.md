# B.A.S.S - Boot Angular Secure Starter

This stater uses the latest (and I'll attempt to keep it up to date) Spring Boot, Spring Cloud, Angular and the Angular CLI.

It configures a full stack example using an Angular UI, with authentication and authorization using 
OAuth and JWT allowing access to application resources.

Hopefully it proves helpful to someone that requires an quickstart full stack Angular / Spring application.
It can extended and deployed as required.

It is split into 3 boot applications:
 - The **Auth Service**  will be available at `http://localhost:9999`. 
 - The **Resource Service**  will be available at `http://localhost:9000`. 
 - The **UI**  will be available at `http://localhost:9090`. 

## The Authorization Service
This is currently a very simple implementation, as it's implementation will be very dependant on the environment where it is required.
Custom DB, Active Directory, LDAP or any 3rd party authentication component can be implemented.

This example creates 2 users in an in-memory H2 DB user:password -> test:password & admin:password.

The code is available at `auth-service/src/main/java`.
Under resources `auth-service/src/main/resources` there is the boot application yml as well as an Java keystore.

#### JWT and Java Keystore

For more information on JWT as well as a handy JWT decoder goto: [https://jwt.io](https://jwt.io)

With JWT we can use asymmetric keys (Public and Private keys) to sign the token, 
this allows us to add a little extra security on our resource service calls as only calls that include a JWT token that has been 
encrypted with the public key are allowed.

To generate the key:

`keytool -genkeypair -alias example-keystore 
                     -keyalg RSA 
                     -keypass ThePassword 
                     -keystore example.jks 
                     -storepass ThePassword` .

You will then be prompted for a couple inputs before it generates example.jks:

`  [Unknown]:
 What is the name of your organizational unit?
   [Unknown]:
 What is the name of your organization?
   [Unknown]:
 What is the name of your City or Locality?
   [Unknown]:
 What is the name of your State or Province?
   [Unknown]:
 What is the two-letter country code for this unit?
   [Unknown]:
 Is CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown correct?`.
     
                    

## The Resource Service

This is where the business logic of the application will reside, and access to functionality is made available via rest endpoints.
As this is a resource server and spring oauth security is configured, we will need the Public key from the example.jks 
to allow Spring to validate the token the call.

To export the Public Key run the following and enter the password "ThePassword" as above:

`keytool -list -rfc --keystore example.jks | openssl x509 -inform pem -pubkey`

This will print out the public key to be used in the application.yml under resources `resouce/src/main/resources`.

## The UI

#### Running the backend (ui boot app)
Can be run as any boot app, just ensure a build has been executed before... see below.

#### Running the ui with hot reload
This application can also be run with angular live reload.

For the boot app:
add the spring profile of **ng** as this changes the callback url for be that of the angular live reload server.

Then go to the "angular project" in `src/main/frontend` and run `ng serve --env=local`.

Now we should work with `http://localhost:4200` since this is where the Livereload server will serve the content. 
All the requests will be proxied to the Spring Boot backend at `http://localhost:9090`.


#### How the build process works
There are only  configuration files involved:

- [package.json](ui/src/main/frontend/package.json) defines `ng build`.
- [pom.xml](ui/pom.xml) a exec-maven-plugin is configured to run "build" script during the `generate-sources` phase.
- [angular-cli.json](ui/src/main/frontend/angular-cli.json) file where we specify the output directory for the compiled frontend files, which is `src/main/resources/static`.

So after running a maven build the frontend source files will be compiled and left at `src/main/resources/static` 
a default location that Spring Boot uses for static content and is included the Spring boot runnable jar.


