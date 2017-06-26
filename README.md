# wardrobe
Simple application for presentation purposes

This project consists of base android components.

Description:
 
  the main aim of application is to help you to organize you clothers.
  You can create wardrobes and fill them with clothers,
  also you can make looks with you clothers and attach to wardropes. 

Aproaches:
  - I tried to make architecture of application close to "Clean architecture" and use Reactive style in some places.
  - For DI I choose Dagger2
  - For MVP structure I decided to take MOXY (https://github.com/Arello-Mobile/Moxy)
  - For database access I decide to use StorIO (https://github.com/pushtorefresh/storio)

Application allows you:
 - make a pictures
 - group them
 - make collage
 - delete, update
 
Database structure:
 
 There are three main entities:
 1) Wardrope
 2) Look
 3) Thing
 
  - Things and Looks tables have relation "many to many"
  - Things and Wardrope tables have relation "many to many"
  - Wardrope and Looks tables have relation "one to many"
 
P.S. - in future releases I will make more "clean structure",
add dataSource for repository where I plan to hide StorIO realisiton.
Also add new business logic to app.
 
