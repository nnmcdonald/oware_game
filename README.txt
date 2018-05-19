Oware is a variation of Mancalla, and it's my understanding that there are many variations of Oware. 

Game Rules(for this variation):
Each player has 6 cups with 3 stones in each initially and a goal cup arranged like so,
		0 0 0 0 0 0
p1 goal cup			p2 goal cup
		0 0 0 0 0 0
On a player's turn the player chooses a cup from his/her side and redistributes the stones counterclockwise one at a time, if the opponent's goal cup is reached it is skipped. When one player has no stones remaining in his/her cups the game ends and the remaining stones are added to the other player's goal cup. The player with the most stones in their goal cup wins.

Example(Player one's turn):
	0 0 0 1 0 3 <-player one chooses this cup with 3 stones
p1 goal			p2 goal
	0 1 1 1 0 0

Result:
	0 0 1 2 1 0
p1 goal			p2 goal
	0 1 1 1 0 0

This program runs a game of Oware between a human and computer opponent. It prompts the user to choose to go first or second then begins the game.
The computer move is selected by comparing possible moves using a minmax algorithm with alpha beta pruning. Moves are compared by using a heuristic to determine the value of the board position.
