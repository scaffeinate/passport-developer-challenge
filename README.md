# passport-developer-challenge
Passport.inc Developer challenge - Live updating Tree application - Full Stack
Deployed Version at: https://passport-factory.herokuapp.com/

Documentation: Passport.inc-Developer-Challenge.pdf

Setting up:
-----------

Clone it: git clone https://sudharti@bitbucket.org/sudharti/passport-developer-challenge.git

###Dependencies
- **redis-server**
- **mongodb**
- **nodejs**
- **java**
- **maven**
- **npm**

Make sure to run the redis-server and mongodb before running the client/server.

### Before running the Client:
```
npm install -g grunt
```

Start Redis at 6379 and MongoDB database in default port.

###To run the Client:
```
cd factory-tree-client
npm install
grunt
node server.js
```

###To run the Server:
```
cd factory-tree-api
mvn clean install
java -jar target/factory-tree.jar
```


## Configuring for Production
For Production there are a few variables to set in the configuration:

### Client:
Under factory-tree-client/config/production.json set the following under <>
```
{
  "redisHost": "<host>",
  "redisPort": 6379,
  "redisPassword": "<password>",
  "serverHost": "<serverHost>"
}
```

Visit http://localhost:3000

Run the app for production using
```
NODE_ENV=production node server.js
```

### Server API:
Under factory-tree-api/src/main/resources/application-prod.properties set the missing variables under []

```
spring.data.mongodb.host=[hostIp]
spring.data.mongodb.database=[dbName]
spring.data.mongodb.username=[username]
spring.data.mongodb.password=[password]

cors.allowed-origins=[client_base]

jedis.host=[redisHost]
jedis.port=6379
jedis.password=[redisPassword]
```

Build using ```mvn clean install```

To run use
```java -jar target/factory-tree.jar --spring.profiles.active=prod```


### Procfile:
Also check the Procfile under factory-tree-api and factory-tree-client to check for Production commands.