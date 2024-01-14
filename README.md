# gameof3
Game between 2 players

Problem Statement:

Goal:
The goal is to implement a game with two independent units – the players –
communicating with each other using an API.

Description:
When a player starts, it incepts a random (whole) number and sends it to the
second
player as an approach to starting the game. The receiving player can now
always choose between adding one of {-1, 0, 1} to get to a number that is
divisible by 3. Divide it by three. The resulting whole number is then sent back
to the original sender.
The same rules are applied until one player reaches the number 1(after the
division). See the example below.

Notes:
Each player runs on its own (independent programs, two browsers,
web-workers, ...).
● Communication via an API (REST, Sockets, WebRTC, ...).
● A player may not be available when the other one starts.
● If you are applying for a frontend position, think of a fancy easily
configurable layout.
Otherwise, the terminal output is okay.
● Please share your project on GitHub and send us the link.
● Try to be platform independent, in other words, the project must be
runnable easily in every environment.


Check configurability
● Review your concepts from DDD
● Watch out for the anemic domain model
● Using events will be considered a plus



Technologies used:

Application Layer
Language: Java
Framework: SpringBoot

Data Layer
Database: TBD
Queue: TBD

Orchestration:
Zookeeper

Messaging:
Kafka

Note: Added Kafka to handle the case of offline users. When 1st user sends messages to 2nd user and 2nd user is offline, we won't process the messages until the 2nd user comes online, messages will stay in kafka topic.
**Deployment and Hosting:**
Pre-requiste

Step 1:
You must install Docker with the steps mentioned below to work.

Install Docker Desktop on Mac.

Install Docker Desktop on Windows.

Install Docker on Linux (choose your distro on the lefthand side menu).

Installing Docker Compose
For Mac & Windows, if you have installed Docker Desktop, then Docker Compose is included as part of those desktop installs.

For Linux, follow the steps here (and do all the steps)


Commands used:
First, we need to start the zookeeper and then Kafka.
To start zookeeper:
Step 1: Go inside the folder where zookeeper tar was extracted and go to bin folder.
Step 2: Run command: zkServer start 
Note:(zkServer.sh start did not work on my machine)

To start kafka server: 
Step 1: Go inside the folder where kafka zip file extracted and go to bin folder.
Step 2: Run command : kafka-server-start ../config/server.properties 


Issues and their solution(contains possible duplicate instructions):
After downloading zookeeper, when I tried to run it, it failed.
Path where below commands needs to be run: Inside the <unzipped tar folder>/bin folder where zkServer.sh is present
Example: /Users/naveenluhach/Downloads/apache-zookeeper-3.8.3-bin/bin
The command which worked is : zkServer start instead of zkServer.sh start 
How to check if zookeeper is running? Run the command: zkServer status

