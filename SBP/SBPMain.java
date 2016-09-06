import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Vaibhav Pandya
 * DREXEL CS510 Prof Geib, Assn 1
 *
 */
public class SBPMain {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws CloneNotSupportedException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		GameState gs = new GameState();
		GameState gs2 = new GameState();
		GameSolver solver = new GameSolver();
		Move m = new Move();
		RealMove rm = new RealMove(2,"left");
		RealMove rm2 = new RealMove(2,"left");
		
		String x="hi",y="hi";
		
		
		
		File f;// = new File(args[1]);
	//	File f2 = new File(args[2]);
		//gs.loadGameState(f);
		
//		gs.loadGameState(f);
//		gs2.loadGameState(f);
//		
//		
//		System.out.println(Arrays.deepHashCode(gs.CurrentState));
//		gs.applyMove(rm);
//		System.out.println(Arrays.deepHashCode(gs.CurrentState));
		
		Scanner sc = new Scanner(System.in);
		
		
		do {
			//Scanner sc = new Scanner(System.in);
			gs=new GameState();
			System.out.println("Put file address");
			//if(sc.hasNextLine());
				String option3 = sc.next();
			f = new File(option3);
			gs.loadGameState(f);
			System.out.println("File has been loaded. Methods are assigend numbers, pick one below");
//			System.out.println("1: outputGameState()");
//			System.out.println("2: gameStateSolved()");
//			System.out.println("3: allMovesHelp()");
//			System.out.println("4: allMoves()");
//			System.out.println("5: Run Random Walk");
//			System.out.println("6: Run BFS");
//			System.out.println("7: Run DFS");
			
			

			
			
			do {
				System.out.println("-------------------------\nWhich option? (10=load file)");
				System.out.println("1: outputGameState()");
				System.out.println("2: gameStateSolved()");
				System.out.println("3: allMovesHelp()");
				System.out.println("4: allMoves()");
				System.out.println("5: Run Random Walk");
				System.out.println("6: Run Breadth First Search");
				System.out.println("7: Run Depth First Search");
				System.out.println("8: A* search with manhattan heuristic");
				System.out.println("9: A* search with my own heuristic");
				System.out.println("10: Load another file");
				int option2 = sc.nextInt();
				//String option = sc.next();

				if (option2==1) {

					gs.outputGameState();
					

				

				} else if (option2==2) {
					System.out.println(gs.gameStateSolved());
				} else if (option2==3)
				{
					System.out.println("which piece?");
					m.allMovesHelp(gs, sc.nextInt());
				}					
				else if (option2==4)
				{
					m.allMoves(gs);
				}
				else if(option2==5)
				{
					gs=new GameState();				
					//f = new File(option3);
					gs.loadGameState(f);
					
					System.out.println("What does N=? ");
					gs.RandomWalks(sc.nextInt());
				}
				else if(option2==6)
				{
					gs=new GameState();				
					//f = new File(option3);
					gs.loadGameState(f);
					
					
					solver.BFS(gs);
				}
				else if(option2==7)
				{
					gs=new GameState();				
					//f = new File(option3);
					gs.loadGameState(f);
					
					
					solver.DFS(gs);
				}
				else if(option2==8)
				{
					gs=new GameState();				
					//f = new File(option3);
					gs.loadGameState(f);
					
					
					solver.AStar(gs,1);
				}
				else if(option2==9)
				{
					gs=new GameState();				
					//f = new File(option3);
					gs.loadGameState(f);
					
					
					solver.AStar(gs,2);
				}
				else
					break;
				
				
			} while (true);

			//sc.close();
			
		} while (true);
		
		
		
		//gs.RandomWalks(5);;
	//	gs.BFS(gs);
		//gs.DFS(gs);
		
			

	}

}
