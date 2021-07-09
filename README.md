# Bookmarx - The ultimate Bookmark Management Solution

This vision of this solution is to provide a browser independend bookmark management solution. It can be run on any platform that supports Java. 

Its internal architecture is based on the ideas of Clean Architecture. For this reason it also serves as an example implementation of our understanding of Clean Architecture. 

## Build and run locally

### Prerequisites
- Java 11 
- Maven 
- Docker 


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
docker-compose -f docker-compose.yml up -d
java -jar ./bookmarx-backend/target/bookmarkx-backend-0.0.1-SNAPSHOT.jar
```

### Try it out 
GoTo: http://localhost:8080

## Development

### Backend

#### Start Backend on dev machine
Start: Run `schwarz.it.ae.bookmarx.BookmarxBackendApplication` it in your favourite IDE.





### Frontend

#### Start Frontend on dev machine
`ng serve --proxy-config proxy.conf.json`





### Database

#### Start/Stop Databases on dev machine

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
2) Create new empty Maven Module via Spring Initializer (eg. bookmarx-fe-ng-mat-v12v2v1).
3) Rename Delete everything except of `pom.xml`
4) Create new Angular app `ng new bookmarx-fe-ng-mat-v12v2v1`
5) Install Material `ng add @angular/material`
6) Install Angular Flex Layout `npm i -s @angular/flex-layout @angular/cdk`
7) Change Title in index.html
8) Set baseURL in angular.json
9) Delete App Folder in new Project
10) Copy App Folder from previous Project
11) Copy Proxy.conf.json from previous Project



