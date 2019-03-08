package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;




public class TicTocTest {
	
	
	public static int UserInput(Board m)
	{
	//UserINput
	
	Scanner sc=new Scanner(System.in);
	System.out.print("Enter Your Prefered Position To Play in format A<row><coloum> :");
	String position = sc.next();
	

	
	if(position.equalsIgnoreCase("A11"))
		{if(m.getA11()==7) m.setA11(0); 
		else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else if(position.equalsIgnoreCase("A12"))
		{if(m.getA12()==7) m.setA12(0); 
			else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else if(position.equalsIgnoreCase("A13"))
		{if(m.getA13()==7) m.setA13(0); 
		else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else if(position.equalsIgnoreCase("A21"))
		{if(m.getA21()==7) m.setA21(0); 
		else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else if(position.equalsIgnoreCase("A22"))
		{if(m.getA22()==7) m.setA22(0); 
		else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else if(position.equalsIgnoreCase("A23"))
		{if(m.getA23()==7) m.setA23(0); 
		else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else if(position.equalsIgnoreCase("A31"))
		{if(m.getA31()==7) m.setA31(0); 
		else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else if(position.equalsIgnoreCase("A32"))
		{if(m.getA32()==7) m.setA32(0); 
		else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else if(position.equalsIgnoreCase("A33"))
		{if(m.getA33()==7) m.setA33(0); 
		else {System.out.println("You cant enter here.Please Restart Game");sc.close();return 0;}}
	else
	{
	System.out.println(	"Please enter a Valid Position in Format A[row][coloum] example A12. Exiting Game");
	sc.close();
	return 0;
	}

	//sc.close();
	
	//System.out.println("You Played: "+position+"\n"+"\ncurrent status is: \n");
	
	return 1;

	}
	

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");
        	
        	Board board = new Board();
        	
        	System.out.println("\n\n\n ....PLAY TIC TAC TOE....\n");


        	int nextGameFor=0;
        	
        	System.out.println("A11 A12 A13");
        	System.out.println("A21 A22 A23");
        	System.out.println("A31 A32 A33");
        	
        	System.out.println("\n");


        	
        	//This one takes the User's input (first Input) and set the Board Positions
        	int statuscode=UserInput(board);
        	if(statuscode==0) { System.exit(1);}
        	
        	
        	
        	//Display the Board Status to User after first input
            board.showCurrentBoardStatus();
            
            System.out.println("***Computer Calculating The Next Move****\n")	;
       	 	Thread.sleep(4000);
            

           
            // go ! Pass the board object to Rules Engine
            
            kSession.insert(board);
         //   kSession.fireAllRules();
            
            kSession.getAgenda().getAgendaGroup( "OppPlayFirst_FirstMove" ).setFocus();
          //  kSession.fireAllRules();
            kSession.fireAllRules(1);
            
            board.showCurrentBoardStatus();

            
            
        //    board.showCurrentBoardStatus();
            
            
            //*************Second input from user*************

          //This one takes the User's Second input and set the Board Positions
        	statuscode=UserInput(board);
        	if(statuscode==0) { System.exit(1);}
        	
        	System.out.println("***Computer Calculating The Next Move****\n")	;
       	 	Thread.sleep(4000);

            board.showCurrentBoardStatus();

          
            
            //update board after each input
            FactHandle boardHandlerInRule = kSession.getFactHandle(board);
            kSession.update(boardHandlerInRule,board);
            
            
            
            kSession.getAgenda().getAgendaGroup( "OppPlayFirst_SecondMove" ).setFocus();
            
            int noOfRulesFiredonSecondINput=kSession.fireAllRules(1);
            
          //  board.showCurrentBoardStatus();
            
            
        	if(noOfRulesFiredonSecondINput>0)
        	{
        		
        		//ifa rule has matched, that is computer has played, then set that the next game is for user 
        			
        		//do nthign
        		
        		// if nextGameFor is 1, it is chance for computer and if 0, chance for user
        		//keep increasing or decreasing after playing a game
        
          
        	}
            
        	else {nextGameFor=nextGameFor+1;}// indicating computer has to play
            
            
            //**************************/
            
            
            // Start the continuos game
            boolean GameOver=false;
            
           do
   	
            
            {
            	
            	//Check Game Over
        	   
        	   int checkGameOverreturn = CheckGameOver(board);
        	   
        	  // System.out.printf("checkGameOverreturn vlue is %d \n",checkGameOverreturn);
               
        	   if (checkGameOverreturn==1) {
                	//stop Game
                	GameOver=true;
                }
                
              //If Game Over exit and print Game over and who wins else Continue
            	
            	if (GameOver==true)
            	{	
            		
            	// Print Who Won the Game
                    
                String gameresult= whoWonTheGame(board);
                System.out.println(gameresult);
            		
            	break;
            	
            	}
            	
            	
            	// in case no rules where fired, that is when Ai coudnot make the best game, 
            	//don't take User input
            	
            //	System.out.printf("noOfRulesFiredonSecondINput is:%d\n",noOfRulesFiredonSecondINput);
            	
            	if(nextGameFor==0) // play only if it is players chance.
            	{
            		
            		
            	
            	
            	//choose User Input and set it
            	
            	statuscode=UserInput(board);
            	if(statuscode==0) { System.exit(1);}

                board.showCurrentBoardStatus();
            	
                //reset to 0 other wise, Userinput will not be allowed other wise
                
            //	System.out.printf(" value of noOfRulesFiredonSecondINput after resetting to 0 is: %d",noOfRulesFiredonSecondINput);

            	nextGameFor++;
                
            	}
            
            	
            	
            	//Check Whether you can win in current move.
              //Play there if you can win
            	
            	if(nextGameFor==1) {//computer play when nextGameFor=1
            		
            	System.out.println("***Computer Calculating The Next Move****\n")	;
            	 Thread.sleep(4000);
                
                int canWinInCurrentMoveLocation=CheckIfYouCanWinInCurrentMove(board);
                
                if (canWinInCurrentMoveLocation!=999) {
                	
                	playCurrentMove(board,canWinInCurrentMoveLocation) ;
                    board.showCurrentBoardStatus();
                    
                    nextGameFor--; // reducing so that it become 0 and user can play 
	
                }
                
                else {
              
                	//Check if Opponent can win in next move
            		//Play there to block his move
                	
                
                	int	OpponentCanWinInNextMoveLocation=CheckIfOpponentCanWinInNextMove(board);
                	
                	if(OpponentCanWinInNextMoveLocation!=888) {
                		
                    	playCurrentMove(board,OpponentCanWinInNextMoveLocation) ;

                        board.showCurrentBoardStatus();

                        nextGameFor--;
                	}
            	
                	
                	else {
            	// If there is no chance for opponet winning, then find cell with maximum chance to win
            	
            	//	Play there
                		
                 int PlaceWithMaxChanceToWinLocation=FindThePlaceWithMaxChanceToWin(board);
                 
                 System.out.printf("PlaceWithMaxChanceToWinLocation is: %d",PlaceWithMaxChanceToWinLocation);
                 
                 if(PlaceWithMaxChanceToWinLocation!=666) {
             		
                 	playCurrentMove(board,PlaceWithMaxChanceToWinLocation) ;
                    board.showCurrentBoardStatus();
                    nextGameFor--; //to make it possible for user to lay

             		
             	}
                
                		
                		
                	
                	
                }
            	
            	
            }
           

            
            
            }
            	
            }
           while (!GameOver);
            
        } 
            
            catch (Throwable t) {
            t.printStackTrace();
        }
        
      
        
        

    }
    
   static int CheckGameOver(Board m)
    {
    	int GameOver=0;

    	if (
    		 m.getA11()!=7 && m.getA12()!=7 && m.getA13()!=7 &&
    		 m.getA21()!=7 && m.getA22()!=7 && m.getA23()!=7 &&
    	     m.getA31()!=7 && m.getA32()!=7 && m.getA33()!=7
    	     )
    		
    		GameOver=1;
    	
    	//horizontal check for all 1 and 0
    	if (
    			(m.getA11()==1 && m.getA12()==1 && m.getA13()==1) ||
        		(m.getA21()==1 && m.getA22()==1 && m.getA23()==1)||
        	    (m.getA31()==1 && m.getA32()==1 && m.getA33()==1)
        	    )
        		
        		GameOver=1;
    	
    	if (
    			(m.getA11()==0 && m.getA12()==0 && m.getA13()==0) ||
        		(m.getA21()==0 && m.getA22()==0 && m.getA23()==0)||
        	    (m.getA31()==0 && m.getA32()==0 && m.getA33()==0)
        	    )
        		
        		GameOver=1;
    	
    	
    	//Vertical check for all 1s and 0s in case you or opponent win
    	
    	if (
    			(m.getA11()==0 && m.getA21()==0 && m.getA31()==0 )||
        		(m.getA12()==0 && m.getA22()==0 && m.getA32()==0)||
        	    (m.getA13()==0 && m.getA23()==0 && m.getA33()==0)
        	    )
        		
        		GameOver=1;
    	
    	if (
    			(m.getA11()==1 && m.getA21()==1 && m.getA31()==1 )||
        		(m.getA12()==1 && m.getA22()==1 && m.getA32()==1)||
        	    (m.getA13()==1 && m.getA23()==1 && m.getA33()==1)
        	    )
        		
        		GameOver=1;
    	
    	
    	//Diagonal cells check
    	
    	if ((m.getA11()==0 && m.getA22()==0 && m.getA33()==0) ||
        		(m.getA31()==0 && m.getA22()==0 && m.getA13()==0))
    		GameOver=1;
    	
    	if ((m.getA11()==1 && m.getA22()==1 && m.getA33()==1) ||
        		(m.getA31()==1 && m.getA22()==1 && m.getA13()==1))
    		GameOver=1;
    	
    	
    	// implemented
    	
        	    	
    	
    	return GameOver;
    }
  
 static int CheckIfYouCanWinInCurrentMove(Board m)
    
    {
	 
 	System.out.println("Executing whether AI can win in current move");

    	int locationToPlay=999;
    	//to implement
    	
    	
    	
    	//For corner cell. First diagonal match. each has 3 possibilities
    	
    	if (m.getA11()==1 && m.getA22()==1 && m.getA33()==7)
    	{
    		locationToPlay=33;
    		return locationToPlay;
    	}
    	
    	if (m.getA11()==1 && m.getA22()==7 && m.getA33()==1)
    		{
    		locationToPlay=22;
    		return locationToPlay;

    		}
    	
    	
    	if (m.getA11()==7 && m.getA22()==1 && m.getA33()==1)
    		{ 
    		locationToPlay=11;
    		return locationToPlay;
    		}
    	
    		
    	//For corner cell. Second diagonal match. each has 3 possibilities

    	if (m.getA31()==1 && m.getA22()==1 && m.getA13()==7)
    		{
    		locationToPlay=13;
    		return locationToPlay;
    		
    		}
    	
    	if (m.getA31()==1 && m.getA22()==7 && m.getA13()==1)
    	{
    		locationToPlay=22;	
    		return locationToPlay;
    	}
    	
    	
    	if (m.getA31()==7 && m.getA22()==1 && m.getA13()==1)
    	{
    		locationToPlay=31;	
    		return locationToPlay;
    	}
    	
    	//For Top horizontal match
    	
    	if (m.getA11()==1 && m.getA12()==1 && m.getA13()==7)
    	{
    		locationToPlay=13;
    		return locationToPlay;

    	}
    	
    	if (m.getA11()==1 && m.getA12()==7 && m.getA13()==1)
    		{
    		locationToPlay=12;
    		return locationToPlay;

    		}
    	
    	if (m.getA11()==7 && m.getA12()==1 && m.getA13()==1)
    		{
    		
    		locationToPlay=11;
    		return locationToPlay;

    		}
    	
    	
    	//For Middle horizontal match
    	
    	if (m.getA21()==1 && m.getA22()==1 && m.getA23()==7)
    		{
    		
    		locationToPlay=23;
    		return locationToPlay;

    		}
    	
    	if (m.getA21()==1 && m.getA22()==7 && m.getA23()==1)
    		{
    		locationToPlay=22;
    		return locationToPlay;

    		}
    	
    	if (m.getA21()==7 && m.getA22()==1 && m.getA23()==1)
    		{
    		locationToPlay=21;
    		return locationToPlay;

    		}
    	
    	
    	//For Bottom horizontal match
    	
    	
    	if (m.getA31()==1 && m.getA32()==1 && m.getA33()==7)
    		{
    		locationToPlay=33;
    		return locationToPlay;

    		}
    	
    	if (m.getA31()==1 && m.getA32()==7 && m.getA33()==1)
    		locationToPlay=32;
    	
    	if (m.getA31()==7 && m.getA32()==1 && m.getA33()==1)
    		{
    		locationToPlay=31;
    		return locationToPlay;

    		}
    	
    	
    	
    	//For Left Vertical match
    	
    	if (m.getA11()==1 && m.getA21()==1 && m.getA31()==7)
    		{
    		locationToPlay=31;
    		return locationToPlay;

    		}
    	
    	if (m.getA11()==1 && m.getA21()==7 && m.getA31()==1)
    		{
    		
    		locationToPlay=21;
    		
    		return locationToPlay;

    		}
    	
    	if (m.getA11()==7 && m.getA21()==1 && m.getA31()==1)
    		{
    		
    		locationToPlay=11;
    		return locationToPlay;

    		}
    	
    	
    	//For Middle Vertical Match
    	
    	if (m.getA12()==1 && m.getA22()==1 && m.getA32()==7)
    		{
    		locationToPlay=32;
    		return locationToPlay;

    		}
    	
    	if (m.getA12()==1 && m.getA22()==7 && m.getA32()==1)
    		{
    		locationToPlay=22;
    		return locationToPlay;

    		}
    	
    	if (m.getA12()==7 && m.getA22()==1 && m.getA32()==1)
    		{
    		locationToPlay=12;
    		return locationToPlay;

    		}
    	
    	
    	
    	//For right vertical match

    	if (m.getA13()==1 && m.getA23()==1 && m.getA33()==7)
    		{
    		locationToPlay=32;
    		return locationToPlay;

    		}
    	
    	
    	if (m.getA13()==1 && m.getA23()==7 && m.getA33()==1)
    		{
    		locationToPlay=23;
    		return locationToPlay;

    		}
    	
    	
    	if (m.getA13()==7 && m.getA23()==1 && m.getA33()==1)
    		{
    		locationToPlay=13;
    		return locationToPlay;

    		}
    	
    	
    	
    	System.out.println("Executed whether AI can win in current move");
    	//give back the location where you can win
    	return locationToPlay;
    	
    }
    
   static void playCurrentMove(Board m,int locToPlay)
   {
	   if (locToPlay==11) {m.setA11(1);}
	   if (locToPlay==12) {m.setA12(1);}

	   if (locToPlay==13) {m.setA13(1);}

	   if (locToPlay==21) {m.setA21(1);}

	   if (locToPlay==22) {m.setA22(1);}

	   if (locToPlay==23) {m.setA23(1);}

	   if (locToPlay==31) {m.setA31(1);}

	   if (locToPlay==32) {m.setA32(1);}

	   if (locToPlay==33) {m.setA33(1);}
   
   }
    
    
  static int CheckIfOpponentCanWinInNextMove(Board m)
    {
	   //to implement
	   
	  // Find the location which opponent can win, if he place there , so that you can play there
	  
  	System.out.println("Executing whether Opponent can win in his next move");
 
	  
	   int locationToPlay=888;
   	//to implement
   	
   	
   	
   	//For corner cell. First diagonal match. each has 3 possibilities
   	
   	if (m.getA11()==0 && m.getA22()==0 && m.getA33()==7)
   	{
   		locationToPlay=33;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;
   	}
   	
   	if (m.getA11()==0 && m.getA22()==7 && m.getA33()==0)
   		{
   		locationToPlay=22;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
   	if (m.getA11()==7 && m.getA22()==0 && m.getA33()==0)
   		{ 
   		locationToPlay=11;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;
   		}
   	
   		
   	//For corner cell. Second diagonal match. each has 3 possibilities

   	if (m.getA31()==0 && m.getA22()==0 && m.getA13()==7)
   		{
   		locationToPlay=13;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;
   		
   		}
   	
   	if (m.getA31()==0 && m.getA22()==7 && m.getA13()==0)
   	{
   		locationToPlay=22;	
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;
   	}
   	
   	
   	if (m.getA31()==7 && m.getA22()==0 && m.getA13()==0)
   	{
   		locationToPlay=31;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;
   	}
   	
   	//For Top horizontal match
   	
   	if (m.getA11()==0 && m.getA12()==0 && m.getA13()==7)
   	{
   		locationToPlay=13;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   	}
   	
   	if (m.getA11()==0 && m.getA12()==7 && m.getA13()==0)
   		{
   		locationToPlay=12;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	if (m.getA11()==7 && m.getA12()==0 && m.getA13()==0)
   		{
   		
   		locationToPlay=11;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
   	//For Middle horizontal match
   	
   	if (m.getA21()==0 && m.getA22()==0 && m.getA23()==7)
   		{
   		
   		locationToPlay=23;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	if (m.getA21()==0 && m.getA22()==7 && m.getA23()==0)
   		{
   		locationToPlay=22;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	if (m.getA21()==7 && m.getA22()==0 && m.getA23()==0)
   		{
   		locationToPlay=21;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
   	//For Bottom horizontal match
   	
   	
   	if (m.getA31()==0 && m.getA32()==0 && m.getA33()==7)
   		{
   		locationToPlay=33;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	if (m.getA31()==0 && m.getA32()==7 && m.getA33()==0)
   		locationToPlay=32;
   	
   	if (m.getA31()==7 && m.getA32()==0 && m.getA33()==0)
   		{
   		locationToPlay=31;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
   	
   	//For Left Vertical match
   	
   	if (m.getA11()==0 && m.getA21()==0 && m.getA31()==7)
   		{
   		locationToPlay=31;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	if (m.getA11()==0 && m.getA21()==7 && m.getA31()==0)
   		{
   		
   		locationToPlay=21;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	if (m.getA11()==7 && m.getA21()==0 && m.getA31()==0)
   		{
   		
   		locationToPlay=11;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
   	//For Middle Vertical Match
   	
   	if (m.getA12()==0 && m.getA22()==0 && m.getA32()==7)
   		{
   		locationToPlay=32;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	if (m.getA12()==0 && m.getA22()==7 && m.getA32()==0)
   		{
   		locationToPlay=22;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	if (m.getA12()==7 && m.getA22()==0 && m.getA32()==0)
   		{
   		locationToPlay=12;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
   	
   	//For right vertical match

   	if (m.getA13()==0 && m.getA23()==0 && m.getA33()==7)
   		{
   		locationToPlay=32;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
   	if (m.getA13()==0 && m.getA23()==7 && m.getA33()==0)
   		{
   		locationToPlay=23;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
   	if (m.getA13()==7 && m.getA23()==0 && m.getA33()==0)
   		{
   		locationToPlay=13;
   		System.out.println("Executed whether Opponent can win in his next move");
   		return locationToPlay;

   		}
   	
   	
  	System.out.println("Executed whether Opponent can win in his next move");
	
   	
   	//give back the location where you can win
   	return locationToPlay;   
	   
	  
    }
   
   
   static int FindThePlaceWithMaxChanceToWin(Board m)
   {
	   //implement
	   
	   // this one, out of time limitation , changing to random pick instead of finding
	   // the place with two match
	   
	  	System.out.println("Executing FindThePlaceWithMaxChanceToWin");
  
	   int locationToPlay=666;
	   
	  // find empty locations;
	   
	   List<String> list = new ArrayList<String>();

	   if (m.getA11()==7) list.add("A11");
	   if (m.getA12()==7) list.add("A12");
	   if (m.getA13()==7) list.add("A13");
	   if (m.getA21()==7) list.add("A21");
	   if (m.getA22()==7) list.add("A22");
	   if (m.getA23()==7) list.add("A23");
	   if (m.getA31()==7) list.add("A31");
	   if (m.getA32()==7) list.add("A32");
	   if (m.getA33()==7) list.add("A33");

	   if(!list.isEmpty())  
		   
	   {
		   //find the size of list
		   int itemCount = list.size();
		   
		   
		   
		   // Pick a random one from empty locations
		   
		   Random rand = new Random(); 
		   
	     String randomString=  list.get(rand.nextInt(itemCount));
	       
		  
	     if(randomString.equals("A11")) locationToPlay=11;
	     if(randomString.equals("A12")) locationToPlay=12;
	     if(randomString.equals("A13")) locationToPlay=13;
	     if(randomString.equals("A21")) locationToPlay=21;
	     if(randomString.equals("A22")) locationToPlay=22;
	     if(randomString.equals("A23")) locationToPlay=23;
	     if(randomString.equals("A31")) locationToPlay=31;
	     if(randomString.equals("A32")) locationToPlay=32;
	     if(randomString.equals("A33")) locationToPlay=33;
	   
		   
	   }
	   
	  	System.out.println("The List Values are: "+list.toString());

	   
	  	System.out.println("Executed FindThePlaceWithMaxChanceToWin");
 
	   
	   return locationToPlay;
   }
   
   static String whoWonTheGame(Board m) {
	   
	   
	  	System.out.println("Executing whoWonTheGame");

	   
	   String S="\nNO ONE WINS. IT IS A TIE\n";
	   
	   //Horizontal check
	   
	   if (m.getA11()==1 && m.getA12()==1 && m.getA13()==1) {S="AI Wins!!";}
	   if (m.getA21()==1 && m.getA22()==1 && m.getA23()==1) {S="AI Wins!!";}
	   if (m.getA31()==1 && m.getA32()==1 && m.getA33()==1) {S="AI Wins!";}

	   
	   if (m.getA11()==0 && m.getA12()==0 && m.getA13()==0) {S="You Win. Intelligent than AI!!!";}
	   if (m.getA21()==0 && m.getA22()==0 && m.getA23()==0) {S="You Win. Intelligent than AI!!!";}
	   if (m.getA31()==0 && m.getA32()==0 && m.getA33()==0) {S="You Win. Intelligent than AI!!!";}
	   
	   
	   //Vertical check
	   if (m.getA11()==1 && m.getA21()==1 && m.getA31()==1) {S="AI Wins!!";}
	   if (m.getA12()==1 && m.getA22()==1 && m.getA32()==1) {S="AI Wins!!";}
	   if (m.getA13()==1 && m.getA23()==1 && m.getA33()==1) {S="AI Wins!!";}

	   if (m.getA11()==0 && m.getA21()==0 && m.getA31()==0) {S="You Win. Intelligent than AI!!!";}
	   if (m.getA12()==0 && m.getA22()==0 && m.getA32()==0) {S="You Win. Intelligent than AI!!!";}
	   if (m.getA13()==0 && m.getA23()==0 && m.getA33()==0) {S="You Win. Intelligent than AI!!!";}
	   
	   //Diagonal Check
	   
	   if (m.getA11()==1 && m.getA22()==1 && m.getA33()==1) {S="AI Wins!!";}
	   if (m.getA11()==0 && m.getA22()==0 && m.getA33()==0) {S="You Win. Intelligent than AI!!!";}

	   
	   if (m.getA31()==1 && m.getA22()==1 && m.getA13()==1) {S="AI Wins!!";}
	   if (m.getA31()==0 && m.getA22()==0 && m.getA13()==0) {S="You Win. Intelligent than AI!!!";}

	  	System.out.println("Executed whoWonTheGame\n");

	   
	   return S;
	   
   }
   
   public static void clearScreen() {  
	   System.out.print((char)900);
	   }
  
    

    public static class Board {

       
        
        private int A11=7, A12=7, A13=7;
        private int A21=7, A22=7,A23=7;
        private int A31=7,A32=7,A33=7;
        


		public int getA12() {
			return this.A12;
		}

		public void setA12(int a12) {
			A12 = a12;
		}

		public int getA11() {
			return this.A11;
		}

		public void setA11(int a11) {
			A11 = a11;
		}

		public int getA13() {
			return this.A13;
		}

		public void setA13(int a13) {
			A13 = a13;
		}

		public int getA21() {
			return this.A21;
		}

		public void setA21(int a21) {
			A21 = a21;
		}

		public int getA22() {
			return this.A22;
		}

		public void setA22(int a22) {
			A22 = a22;
		}

		public int getA23() {
			return this.A23;
		}

		public void setA23(int a23) {
			A23 = a23;
		}

		public int getA31() {
			return this.A31;
		}

		public void setA31(int a31) {
			A31 = a31;
		}

		public int getA32() {
			return this.A32;
		}

		public void setA32(int a32) {
			A32 = a32;
		}

		public int getA33() {
			return this.A33;
		}

		public void setA33(int a33) {
			A33 = a33;
		}
		
		
		public void showCurrentBoardStatus() {
			
			System.out.print("\nBoard Status is now:\n");

			
			System.out.print(A11);System.out.print(" ");System.out.print(A12);System.out.print(" ");System.out.println(A13);
			
			System.out.print(A21);System.out.print(" ");System.out.print(A22);System.out.print(" ");System.out.println(A23);

			System.out.print(A31);System.out.print(" ");System.out.print(A32);System.out.print(" ");System.out.println(A33);


			System.out.print("\n");


			
			
		}

    }
	
	
	

}
