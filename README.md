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



**Technologies used:**
1. Application Layer : Java, SpringBoot
2. Data Layer : MongoDB (for offline users, we fetch all the messages as soon as user logs in/becomes online) 2. Queue: Not Used (will be more light weight and real time)

**Deployment and Hosting:**
Pre-requiste

Step 1:
Install Docker

Step 2:
Run Docker 

Step 3: docker-compose up


Step 4:
Check if you can see mongo db data at: http://localhost:8081/db/chat_app/

Step 5: 
Run spring boot application.

Step 6:
Go to http://localhost:8088/ and enter:
For 1st player: user1 for both real name and nick name.
For 2nd player: user2 for both real name and nick name.

Step 7: Only when you press -1, game will start.

Step 8: Toggle the mode from the UI, initial mode is manual always. (TODO: Integration of Client Toggle UI Button with Backend API so that we can fetch the last mode of user)
Backend API developed for the same to create/edit mode:

Set players mode using API to either: auto/man for both players.
For simplicity: use - user1 and user2

curl --location --request PUT 'http://localhost:8088/playerMode' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic Og==' \
--data '{
    "player": "user2",
    "playerMode": "auto"
}'



**Common issues**
1. Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
Resulution: Perform step 2.



