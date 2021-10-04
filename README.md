# Bookmarx - The ultimate Bookmark Management Solution

The vision of this solution is to provide a browser independent bookmark management solution. It can be run on any platform that supports Java. 

Its internal architecture is based on the ideas of Clean Architecture. For this reason it also serves as an example implementation of our understanding of Clean Architecture. 

## Build and run locally

### Prerequisites
- Java 11 
- Maven 
- Docker
- node/npm - and setup as described by section [ Setting up npm settings](#Setting-up-npm-settings) 


### Build
```
cd bookmarx-parent 
mvn clean install
```

### Build (skipping some tests)
```
# Skip integrations tests
mvn clean install -DskipITs

# Skip unit tests
mvn clean install -DskipUTs

# Skip ALL Tests
mvn clean install -DskipTests
```

### Run
```
# Startup a local database
docker-compose -f docker-compose.yml up -d

# Run the application
java -jar ./bookmarx-backend/target/bookmarx-backend-0.0.1-SNAPSHOT.jar
```

### Try it out 
GoTo: http://localhost:8080

## Development

### Backend

#### Start Backend on your development machine
Run `schwarz.it.ae.bookmarx.BookmarxBackendApplication` it in your favourite IDE.





### Frontend

#### Start Frontend on your development machine
`ng serve --proxy-config proxy.conf.json`

#### Setting up npm settings
The User Interface uses the Schwarz Core UI that is located in a private repository. Therefore, you need to add the following file (if not already present)
to your user directory. [More information on confluence](https://confluence.schwarz/display/LCU/CoreUI).
```shell
touch .npmrc
```
The content should be the following, so you can access the private repository that is configured in ```frontend/.npmrc```.
```
@scu:registry=https://schwarzit.jfrog.io/schwarzit/api/npm/npm/
//schwarzit.jfrog.io/schwarzit/api/npm/npm/:always-auth=true
//schwarzit.jfrog.io/schwarzit/api/npm/npm/:email=<<YOUR_EMAIL>>
//schwarzit.jfrog.io/schwarzit/api/npm/npm/:username=<<YOUR_USERNAME>>
//schwarzit.jfrog.io/schwarzit/api/npm/npm/:_password=<<YOUR_JFROG_AUTH_TOKEN_AS_BASE64_STRING>>
```
To encode your API token from JFrog you can use the following command (on macOS):
```shell
echo -n <<YOUR_JFROG_API_TOKEN>> | base64
```



### Database

#### Start/Stop Databases on your development machine

Start: `docker-compose -f docker-compose.yml up -d`

Stop: `docker-compose -f docker-compose.yml down`

#### Access MongoDB

Via CLI: `docker exec -it bmx-mongodb mongo`

Web Client: `docker run -p 3000:3000 --name mongoclient mongoclient/mongoclient`


## Further Information

### Clean Architecture
- https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- https://www.youtube.com/watch?v=Nsjsiz2A9mg

### Schwarz Core UI
- https://confluence.schwarz/display/LCU
- https://core-ui.schwarz/

### Angular
- https://material.angular.io/
- https://github.com/angular/flex-layout

#### Upgrade Angular Project:
- https://stackoverflow.com/questions/43931986/how-to-upgrade-angular-cli-to-the-latest-version

#### ReSetup Angular Project
1) run `ng update @angular/cli @angular/core`
2) Create new empty Maven Module via Spring Initializer (e. g. bookmarx-fe-ng-mat-v12v2v1).
3) Rename Delete everything except of `pom.xml`
4) Create new Angular app `ng new bookmarx-fe-ng-mat-v12v2v1`
5) Install Material `ng add @angular/material`
6) Install Angular Flex Layout `npm i -s @angular/flex-layout @angular/cdk`
7) Change Title in index.html
8) Set baseURL in angular.json
9) Delete App Folder in new Project
10) Copy App Folder from previous Project
11) Copy Proxy.conf.json from previous Project



