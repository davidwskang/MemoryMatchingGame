# Memory Matching Game Planning

## Screens

1. Splash Screen
- A user should be able to open the app successfully-
    - A user may not open the app if there is no internet connection

2. Home Screen
- A user should be able to select a game difficulty mode: easy (5x5), medium (7x7), hard (9x9).
- A user should be able to view their high scores
- (extra) A user should be able to return to their game that they quit

3. Game Screen
- A user should be able to see a set of cards face down, 
    - Number of cards correspond to the difficulty they have chosen
- A user should be able to flip 2 cards face up which constitutes a 'turn'
    - If 2 cards are matching, they remain face up at the end of the turn
    - If 2 cards are not matching, they will be automatically flipped face down at the end of the turn
- A user should be able to complete the game if they have correctly flipped all matching pairs
- A user should be able to view the number of turns they have completed
- A user should be notified once they have completed a game by a modal, and be able to enter their name to the high scores
- A user should be able to quit the game during play

4. High Scores Screen
- A user should be able to view a list scores of previously completed games