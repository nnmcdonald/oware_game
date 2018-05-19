public class OwariGameBoard
{
  private int north[];
  private boolean northsTurn;//Alternates accordingly on playerTurn() calls to maintain turn order
  
  public OwariGameBoard()
  {
    north = new int[14];
    //initial board state
    for(int i = 0; i < 14; i++)
    {
      if(i != 6 && i != 13)
        north[i] = 3;
      else
        north[i] = 0;
    }

    northsTurn = true;
  }
  
  public void printBoard()
  {
    System.out.println("   " + north[5] + " " + north[4] + " " + north[3] + " " + north[2] + " " +
                       north[1] + " " + north[0] + " ");
    System.out.println(north[6] + "                " + north[13]);
    System.out.println("   " + north[7] + " " + north[8] + " " + north[9] + " " + north[10] + " " +
                       north[11] + " " + north[12] + " ");
  }
  
  //Distributes the seeds in pit counter clockwise
  public void move(int pit)
  {  
    //Make sure move is valid
    if(north[pit] == 0)
      System.out.println("No seeds");
    else{
      int index = pit;
      int i = 0;
      for(i = 0; i < north[pit]; i++)
      {
        if(index + i == 14)
          index = 0 - i;
        //Skips the opponents goal cup
        if(!(northsTurn && index + i == 13) && !(!northsTurn && index + i == 6))
          north[index + i]++;
        else
          north[pit]++;
      } 
      i--;
      //Check for capture and maintains turn order
      if(northsTurn)
      {
        if(index + i < 6 && north[index + i] == 1)
        {
          north[6] += north[12 - index - i];
          north[12- index - i] = 0;
        }
        northsTurn = false;
      }
      else
      {
        if(index + i > 6 && index + i < 13 && north[index + i] == 1)
        {
          north[13] += north[12 - index -i];
          north[12- index - i] = 0;
        }
        northsTurn = true;
      }
      north[pit] = 0;
    }
  }
  
  //returns true if a win is detected
  public boolean checkForWin()
  {
    //More than 18 seeds in the goal cup wins
    if(north[6] > 18 || north[13] > 18)
    {
      gameOver();
      return true;
    }
    
    //Ends the game if either side is out of seeds
    for(int i = 0; i < 6; i++)
    {
      if(north[i] != 0)
      {
        for(int j = 7; j < 13; j++)
        {
          if(north[j] != 0)
          {
            return false;
          }
        }
        gameOver();
      }
    }
    gameOver();
    return true;
    }
  
  //End the game after a win is detected
  public void gameOver()
  {
    //Add the remaining seeds to the respective goal cup
    for(int i = 7; i < 13; i++)
    {
      north[13] += north[i];
      north[i] = 0;
    }
      
    for(int i = 0; i < 6; i++)
    {
      north[6] += north[i];
      north[i] = 0;
    }
    
    printBoard();
    //Displays the game result
    if(north[6] > north[13])
      System.out.println("Player 1 wins!");
    else if(north[13] > north[6])
      System.out.println("Player 2 wins!");
    else
      System.out.println("Draw."); 
  }
  
  public int getSeeds(int pit)
  {
    return north[pit];
  }
}
