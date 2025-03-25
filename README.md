# Hugo_Ski_Game_Finnish_24-2-2023
THIS VERSION IS OUTDATED BUT CAN BE ENJOYED BY THOSE WHO DO NOT WANT ENGLISH TEXTS
----
NEW VERSION: https://github.com/humbertodias/hugo-ski-game-en
(25.3.2025)
----

Java skiing game - Hugo the Troll fan game - I do not own the Hugo franchise - support the official releases! 

Version: 1.0.0.4 (November 8, 2023). 

Recommended Java: "jdk-18.0.2.1" or newer. JVM does matter. If the game does not start up, you have to install JDK and make sure the Operating System is not blocking Java.
https://www.oracle.com/java/technologies/downloads/ 

Having a possible laptop charging cable plugged in is highly recommended. The battery status affects the game on some laptop PCs. The folders "lib" and "src" are important.

Google Drive has the JDK (for Windows x64) and also the original MP4 cutscenes available: 
https://drive.google.com/drive/folders/110sPxB1HPm08zQFlkPZfCN3-ZpUfN7zv 

No matter what GitHub informs you, this is made 100 %:tly with the Java programming language. "Shell" and "Html" probably come from the "Java Media Framework" library that has been used.

Also, for some reason some people think this is an old Java Mobile Phone game. That is just wrong. Those were used in about 2000-2009 and I am not interested in developing old cell phone applications. Java is a programming language by Oracle / Sun Microsystems.

IF A VIDEO CUTSCENE DOES NOT START, MOVE THE MOUSE CURSOR ON THE WINDOW. The visuals depend on your own PC. The speed of the graphic objects might be extremely slow or fast, depending on the computer.

---

Bugs and improvement ideas:
- Holding down Enter and keeping it pressed down at some points (during or just before a game over, for example) creates multiple game instances and the game will be messed up for a while.
- The video ".gif"s are large and they might still flicker and the sounds might be a bit misplaced during the animations. The game is missing the actual (.mp4) video support.
- Removing a laptop's charging cable causes some graphic threads to slow down a bit.
- The 4 movable objects on the ski tracks sometimes flash on the screen for a very short time before they are set correctly.
- The RAM seems to fill up too much if the player is playing the game for hours, even when the Java Garbage Collector is called sometimes.
- There's no real hit detection with cartesian x-y-coordinates, sometimes the player thinks about getting the money even when it is not possible anymore (there is exactly 1 hit detection moment that has nothing to do with the graphics).
- It is possible to get stuck in between tracks 3 & 4 (the last two 2 & 3 when numbered 0-3) by pressing left+right at the same time. However, pressing left gets you out of stuck.
- Having to press Enter all the time after every video might be annoying and automatic proceeding would be nice.
- The window size (630x500 resolution) cannot be resized, would be nice to play on full screen mode.
- Some people have had problems with installing the correct Java and starting up the application. Perhaps JDK 17 or older should be set as the requirement?

---

Instructions: 
1. Install the newest JDK (or "JDK-18.0.2.1").
2. Choose the Code button -> download ZIP. 
3. Extract the Zip to a folder you want. 
4. Double-click "HugoHiihto.jar" and the game should start up. The file "run.bat" has the command "java -jar HugoHiihto.jar". The Operating System might warn you of executing unknown exe and bat files. 

On Linux, you might have to make the JAR file executable: 
https://askubuntu.com/questions/270172/how-can-i-make-a-jar-file-executable 
