# lovey

This is the readme for lovey, try to keep it up to date with any information future-you will wish past-you
remembered to write down

# LORE!!!!

Backstory on Lovey for the uncultured and uneducated
    - Lovey is a gentoo penguin who shows up at Mr. Popper's house in Manhattan
    - The premise of the game is Lovey's adventure from the Palmer Peninsula to Manhattan
    - okay time for levels

## LEVELS
* - [ ] Palmer Peninsula
    - Easy
    - Icy, rocky mountain backdrop.
    - snow element later on in the level to add some difficulty?
    - gentle, non-suspenseful, relaxed music
        - think just come chill beats
    - level animation end: lovey jumps into the southern ocean
* - [ ] Punta Arenas
    - level starts with lovey climbing up onto land
    - ever so slightly harder
    - similar music (cuz penguins still live there)
    - level end animation shows lovey scaling the andes and then sliding down on his belly on the snow capped mountains and then he lands in the amazon
* - [ ] Amazon
    - starts to get harder (yk cuz penguins dont rlly belong in dense rainforest)
    - idk how to explain but like tropical sounding music. jungle vibes
    - level animation end idk (goes thru the colombian cartel /s)
* - [ ] Panama
    - similar difficulty
    - industrial kinda music. like ship horns blaring, uncoordinated mess
    - level animation end: lovey finds himself in cancun. in awe
* - [ ] Yucatan/Cancun
    - turn up the difficulty a bit
    - music is vacationy, calm, cool, chill. sounds of vacation and fun
    - level animation end: lovey hops on a random ferry and waves goodbye to cancun
* - [ ] CUBA
    - - [ ] PT 1: CUBA
        - level enter animation: lovey hops off the boat. looks around, shrugs and continues
        - turn up the difficulty 
        - bit more intense, fast paced music. still like tropical and nature sounds
    - - [ ] PT 2: GUANTANAMO
        - Lovey sees an American Flag and a McDonalds and is happy cuz he "made it" to america. walks inside and sees everything in spanish
        - hardest level yet. kinda significant jump in difficulty
        - intense music. idk how else to describe u got this ayushi
        - ends with lovey escaping guantanamo frantically swimming away
* - [ ] Cayman Islands
    - lovey realises he went the wrong way. ends up in the cayman islands 
    - same if not easier difficulty than g.b
    - still intense music cuz lovey thinks hes being chased
* - [ ] Cancun cutscene
    - Lovey is back!
    - he's sweaty and tired and says in penguin talk, "can't believe im back here"
    - reuse cancun music it'll be funny
* - [ ] La Frontera
    - setting, Rio Grande : The US Mexico border
    - on the Mexican side
    - cutscene is lovey jumping over the wall and being chased by border patrol
    - idk iconic mexican music idrc if that is racist
* - [ ] Wild West
    - corn, corn, corn, random old western town, corn
    - wild west music have fun with that
    - cutscene of another penguin say "This town ain't big enough for the two of us
        - lovey surprised pickachu face
        - runs away
* - [ ] Chicago
    - lowk dont even know atp
    - improv

* - [ ] New York
  - coming of age music
  - hardest level yet (aka very fast)
  - cutscene of lovey and mr popper reunite

## Project set up
This is a gradle project using JMonkey Engine and other java libraries
![image](https://github.com/stanleymw/lovey/assets/96892261/17dd98f4-6975-4877-b4b7-d3740ef13699)

# Modules : 

Game module `:game` : holds `build.gradle` dependencies for the game code & should hold your code.

Desktop module `:desktop` : holds `build.gradle` for desktop dependencies & implements the `:game` module, this module can hold the desktop gui.


# Running Game : 

### Desktop : 

```gradle
./gradlew run
```


# Building Game :

### Desktop :

```bash
    $./gradlew :desktop:copyJars
```

### Distribute with a JRE

Distributing with a JRE means you'll need to provide an operating specific bundle for each OS you are
targeting (which is a disadvantage) but your end use will not have to have a JRE locally installed
(which is an advantage).

Either:

In your IDE execute the gradle task distZip (which you'll find under gradle > distributions > buildAllDistributions)

Or:

In the command line open at the root of this project enter the following command: gradlew buildAllDistributions

Then you will find a series of zip in the build/distributions folder. These zip(s) will contain your game, all the libraries to run it and an
OS specific JRE. (The same files will also be available unzipped in a folder, which may be useful if distributing via steampipe or similar).


References : 

=> gradlew for android:

=> Gradle DSL : https://docs.gradle.org/current/dsl/index.html

=> Gradle for java : https://docs.gradle.org/current/userguide/multi_project_builds.html

=> Gradle/Groovy Udacity course by google : https://github.com/udacity/ud867/blob/master/1.11-Exercise-ConfigureFileSystemTasks/solution.gradle


=> See JMonkeyEngine Desktop Example : https://github.com/Scrappers-glitch/basic-gradle-template

=> See JMonkeyEngine RPI armhf Desktop Example : https://github.com/Scrappers-glitch/JmeCarPhysicsTestRPI
