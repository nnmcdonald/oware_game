public class OwariGameState
{
  private int pits[];
  private OwariGameState parent;
  private OwariGameState children[];
  private int depth;

  public OwariGameState()
  {
  }
  
  //initial state
  public OwariGameState(OwariGameBoard gb, int north)
  {
    depth = 0;
    children = new OwariGameState[6];
    pits = new int[14];
    //copies the current board values with the computer's side indexed 0-5
    if(north == 0){
      for(int i = 0; i < 14; i++)
        pits[i] = gb.getSeeds(i);
    }
    else{
      for(int i = 0; i < 14; i++)
        pits[i] = gb.getSeeds((i + 7)%14);
    }
  }
  
  //Constuctor for children states
  public OwariGameState(OwariGameState p, int move, int minmax)
  {
    parent = p;
    depth = parent.depth + 1;
    
    //holds the children of this node
    children = new OwariGameState[6];
    pits = new int[14];
    //copies the current board values
    for(int i = 0; i < 14; i++)
      pits[i] = p.pits[i];
    //moves North on maximizing level
    if(minmax == 0)
      this.move(move, minmax);
    //moves South on minimizing level
    else
      this.move(move + 7, minmax);
  }
  
  //Minmax algorithm to determine computer move
  public double generateComputerMove(int minmax, double alpha, double beta)
  {
    //Searches to a max depth of 12, stops if winning state is found for either side
    if(this.depth < 12 && this.pits[6] < 19 && this.pits[13] < 19){
      //holds the values returned by the evaluate method and in minmax algorithm
      double childStateValues[] = new double[6];
      //loops through each possible move in each state
      for(int i = 0; i < 6; i++)
      {    
        int index = i;
        //loops through South's cups on minimizing level
        if(minmax == 1){
          index += 7;
        }
        //Invalid move
        if(this.pits[index] == 0)
          this.children[i] = null;
        //creates a child state if the current move is possible
        else
          this.children[i] = new OwariGameState(this, i, minmax);
        //evaluates the child state
        if(this.children[i] != null)
        {
          childStateValues[i] = this.children[i].generateComputerMove((minmax + 1)%2, alpha, beta);
          //Adds an extra value to the value returned by the evaluate() method if the child state is a winning 
          //or losing state, only looks ahead 2 levels
          if(this.depth < 3)
          {
            if(this.children[i].pits[6] > 18)
              childStateValues[i] += 1000;
            else if(this.children[i].pits[13] > 18)
              childStateValues[i] -= 1000;
          }
          
          //maximizing level, raises alpha
          if(minmax == 0){
            if(childStateValues[i] > alpha)
              alpha = childStateValues[i];            
          }
          //minimizing level, lowers beta
          else
          {
            if(childStateValues[i] < beta)
              beta = childStateValues[i];
          }
          //If the alpha beta values cross then the rest of the childStateValues that haven't been
          //evaluated in this loop are set to 0 to prune them from the tree
          if(alpha >= beta)
          {
            for(int j = i + 1; j < 6; j++)
              childStateValues[j] = 0;
            break;
          }
        }
        //sets the state value to 0 to indicate an invalid move
        else
          childStateValues[i] = 0;
      }
      //return max of children or index of best move if this is the root node
      if(minmax == 0){
        double max = Double.NEGATIVE_INFINITY;
        double maxAt = 0;
        for(int i = 0; i < 6; i++){
          if(childStateValues[i] != 0 && childStateValues[i] > max)
          {
            max = childStateValues[i];
            maxAt = i;
          }
        }    
        if(this.depth == 0)
          return maxAt;
        else
          return (int) max;
      }
      //returns min of children
      else{
        double min = Double.POSITIVE_INFINITY;
        for(int i = 0; i < 6; i++){
          if(childStateValues[i] < min && childStateValues[i] != 0)
          {
            min = childStateValues[i];
          }
        }
          return min;
      }
    }
    else{
      //leaf node
      return evaluate(this);  
    }
  }
  
  public double evaluate(OwariGameState state)
  {
    //I considered both players scores and how many seeds were on both sides of the board to evaluate board states
    //The look ahead is set in the generateComputerMove() method and is currently 12
    double score = pits[6];
    double oppScore = pits[13];

    double seedsSouth = 0;
    for(int i = 7; i < 13; i++)
    {
      seedsSouth += pits[i];
    }

    double seedsNorth = 0;
    for(int i = 0; i < 6; i++)
    {
      seedsNorth += pits[i];
    }
    
    double weightedScoreValue = (1000 * score) - (300 * oppScore);
    double weightedSeedCount = ((40*seedsNorth) - (10*seedsSouth))/36;

    return weightedScoreValue + weightedSeedCount;
  }
  
  public int getDepth()
  {
    return depth;
  }
  
  public void move(int pit, int minmax)
  {
    for(int i = 0; i < pits[pit]; i++)
    {
      if(minmax == 0)
      {
        if((pit + i)%14 != 13)
          pits[(pit + 1 + i)%14]++;
        else
          pits[pit]++;
      }
      else
      {
        if((pit + i)%14 != 6)
          pits[(pit + i + 1)%14]++;
        else
          pits[pit]++;
      }
    } 
    //Check for capture
    if(pit + pits[pit] < 6 && pits[pit + pits[pit]] == 1 && minmax == 0)
    {
      pits[6] += pits[12 - pit - pits[pit]];
      pits[12- pit - pits[pit]] = 0;
    }
    else if(minmax == 1 && pit + pits[pit] < 13 && pits[pit + pits[pit]] == 1)
    {
      pits[13] += pits[12 - pit - pits[pit]];
      pits[12- pit - pits[pit]] = 0;
    }
   
    pits[pit] = 0;
  }
}