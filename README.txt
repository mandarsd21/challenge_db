The Asset Management Digital Challenge
======================================

This is a Spring Boot RESTful application which can serve the following Account operations:

 * Creating a new account
 * Reading an account
 * Making a transfer between two accounts

Run the application
----------

Build the project using gradle. From project root:

```
./gradlew clean build
```

Once the project is built, you can run the jar as follows:

```
java -jar build/libs/Java\ Challenge-0.0.1-SNAPSHOT.jar
```

Example request:

```
curl -i -X PUT \
   -H "Content-Type:application/json" \
   -d \
'{
  "accountFromId":"Id-1",
  "accountToId":"Id-2",
  "amount": "1.55"
}' \
 'http://localhost:18080/v1/accounts/transfer'
```

Assumptions

* You cannot make a transfer to the same account ID. 

Further improvements
--------------------
* In production we could have a database for storing the accounts. Another advantage of using a database is that we can handle transactional atomic account updates.

* Creation of transaction ID - 
In the first step we create a transaction ID then we request a transfer given this transaction ID. Once a transfer is made the transaction ID cannot be used

* I assumed that an amount can be transferred regardless of the number of decimal places the amount has.
Ideally, there should be some business logic around handling requests with more than 2 decimal places (for example 100.254). That would probably be a Bad Request. 