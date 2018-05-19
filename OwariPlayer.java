import java.util.Scanner;

public class OwariPlayer
{
  private int playerType;
  private int north;
  private OwariGameBoard gameBoard;
  
  public OwariPlayer(int pt, OwariGameBoard gb, int n)
  {
    //1 for human player, 0 for computer
    playerType = pt;
    gameBoard = gb;
    //0 for first player(north), 1 for second player(south)
    north = n;
  }
  
  public void playerTurn()
  {
    Scanner keyboard = new Scanner(System.in);
    //human 1st player
    if(playerType == 1 && north == 0)
    {
      System.out.println("Enter pit selection.");
      int pit = keyboard.nextInt();
      while(pit >= 6 || gameBoard.getSeeds(pit) == 0)
      {
        System.out.println("Error");
        pit = keyboard.nextInt();
      }      
      gameBoard.move(pit);
    }
    //human 2nd player
    else if(playerType == 1 && north == 1)
    {
      System.out.println("Enter pit selection.");
      int pit = keyboard.nextInt() + 7;
      while(pit >= 13 || gameBoard.getSeeds(pit) == 0)
      {
        System.out.println("Error");
        pit = keyboard.nextInt() + 7;
      }      
      gameBoard.move(pit);
    }
    //computer
    else
    {
      //Computer move
      if(north == 0)
      {
        OwariGameState gs = new OwariGameState(gameBoard, north);
        int move = 0;
        move = (int) gs.generateComputerMove(0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        
        gameBoard.move(move);
        System.out.println("Computer chooses pit " + move);
      }
      else
      {
        OwariGameState gs = new OwariGameState(gameBoard, north);
        int move = 0;
        move = (int) gs.generateComputerMove(0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        gameBoard.move(move + 7);
        System.out.println("Computer chooses pit " + move);
      }
    }
    keyboard.close();
  }
}