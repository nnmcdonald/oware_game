//Nathaniel McDonald
//
//This program runs a game of Owari against a computer opponent.
//When the game board is printed to the screen 1st player is always displayed as NORTH and second player as SOUTH,
//and NORTH is above SOUTH. For the purposes of move selection the cups are numbered 0-5 on both sides like so:
//
//                                                                                NORTH
//                                                                             5 4 3 2 1 0
//                                                              (North goal cup)          (South Goal Cup)
//                                                                             0 1 2 3 4 5 
//                                                                                SOUTH
//Player1 cup 0 ALWAYS corresponds to NORTH 0
//Player2 cup 0 ALWAYS corresponds to SOUTH 0 
//

import java.util.Scanner;

public class Owari
{
  private static OwariPlayer p1;
  private static OwariPlayer p2;
  
  public static void main(String args[])
  {
    int turnOrder = getTurnOrder();

    OwariGameBoard gb = new OwariGameBoard();
    //Human goes first
    if(turnOrder == 1){   
      p1 = new OwariPlayer(1, gb, 0);
      p2 = new OwariPlayer(0, gb, 1);
    }
    //Human goes second
    else
    {
      p1 = new OwariPlayer(0, gb, 0);
      p2 = new OwariPlayer(1, gb, 1);
    }
       
    playGame(p1, p2, gb);
  }
  
  //Runs the game
  private static void playGame(OwariPlayer player1, OwariPlayer player2, OwariGameBoard gameBoard)
  {
    while(true)
    {
      //Player 1's turn
      gameBoard.printBoard();
      System.out.print("Player 1: ");
      player1.playerTurn();
      if(gameBoard.checkForWin())
        break;
      //Player 2's turn
      gameBoard.printBoard();
      System.out.print("Player 2: ");
      player2.playerTurn();
      if(gameBoard.checkForWin())
        break;
    }
  }
  
  private static int getTurnOrder(){
    Scanner keyboard = new Scanner(System.in);
    int turnOrder = 0;
    
    do{
    System.out.println("Would you like to go first or second? Enter 1 for 1st and 2 for second.");
    turnOrder = keyboard.nextInt();
    }while(turnOrder != 1 && turnOrder != 2);
    
    keyboard.close();
    return turnOrder;
  }
}
