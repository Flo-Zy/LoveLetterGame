# Love Letter
### Wichtiges zum Spiel
- Geduldig sein mit der Eingabe. Das Spiel wurde so entwickelt, dass die Ausgaben nicht direkt kommen
- \playcard sollte nie ausgeführt werden, wenn nicht davor mit \start eine Runde initialisiert wurde.
- \start nur zum Initialisieren einer neun Runde verwenden, also ganz am Anfang und wenn die Runde abgeschlossen wurde und die neue beginnt.
- \start nicht während der laufenden Runde verwenden. 
- Ganz wichtig, dass die neue Runde mit \start angefangen wird und nicht mit \playcard,
- Wen ein Spieler ausgeschieden wurde dann trotzdem \playcard verwenden. Das Programm erkennt dann automatisch, dass du ausgeschieden bist.
- Denk daran! Wenn du die Gräfin mit einem König oder Prinzen in der Hand hast, so musst du die Gräfin spielen.
### Project description
Preliminary project for the software development internship in the winter semester 2023-2024.
### About the game
The card game "Love Letter" by Japanese game designer Seiji Kanai. The object of this game is to send (as many as possible) love letters to a princess in order to seek her favor.
Different characters in the royal court strive to achieve this goal, with varying chances based on their status and abilities. The rules can be found in detail at following link:

http://alderac.com/wp-content/uploads/2017/11/Love-Letter-Premium_Rulebook.pdf
## Task definition in the first part of the preliminary project
- The implementation will be limited to playing in the local console by means of game commands. After the start of the game, the players will switch in turn per round. They start their actions from the same console.
- Start the implementation of your game "Love Letter" by thinking about a data model for the game. What objects do you need? How do they interact with each other? You can create a UML class diagram for this purpose.
- Your current version of the game must meet the following requirements:
- Your program should ask for the desired number of players at the beginning (2-4 players).
- Then you should be able to assign names for each player.
- The game should be controlled directly from the console by special commands (use the escape character 'Backslash', which marks the game command).
- With \help all available commands shall be issued with a short explanation for your program. Don't forget to extend this command in the course of your implementation.
- Prepare the following commands as the basic framework of your game:
    - \start to start the game.
    - \playCard to play a card.
    - \showHand to show the hand of the current player.
    - \showScore to show the score for each player.
## Task definition in the second part of the preliminary project
- If not done yet, complete your code with meaningful Javadoc comments. Use Javadoc to create detailed documentation for your project.
  Put this documentation in the Git repository.
- Now extend your program with the logic of the game "Love Letter". The following requirements should also be met:
    - (a) Now implement all the game cards. Keep in mind that game cards have commonalities, which you can use to improve the structure of your program. Also, consider how to implement the card effects, since each card has a different effect.
    - (b) Also note that some cards require you to select players or other cards.
    - (c) You need to be able to see what happened at any time during the game, so output enough information to the console.
    - (d) Your program should catch all possible error cases (e.g. invalid commands) and display appropriate error messages.
    - (e) Extend your commands to include the command show players, which shows all players including a marker indicating whether the player is still active in the current round or has already been eliminated.
- By the end of this exercise sheet, your program should be playable. Use the remaining time to test your program and fix bugs.
## Author
Florian Zymeri