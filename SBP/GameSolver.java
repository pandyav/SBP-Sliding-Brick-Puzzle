import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 
 */

/**
 * @author Vaib
 *
 */
public class GameSolver {
	
	public GameSolver()
	{
		
	}
	
	
	// bfs caller method takes initial state
		public void BFS(GameState g) {
			Queue<GameState> q = new LinkedList<GameState>();
			q.add(g);
			HashSet<GameState> ls = new HashSet<GameState>();
			ls.add(g);

			BFS2(q, ls, g.CloneState(g));
			// BFSHelper(q,ls);
		}

		// dfs caller method takes inital state
		public void DFS(GameState g) {
			Stack<GameState> s = new Stack<GameState>();
			s.push(g);

			HashSet<GameState> ls = new HashSet<GameState>();
			ls.add(g);

			DFS2(s, ls, g.CloneState(g));
		}
		
		//A* search method takes initial state
		public void AStar(GameState start, int choice)
		{
			LinkedList<GameState> open = new LinkedList<GameState>();
			HashSet<GameState> closed = new HashSet<GameState>();
			
			GameState copy =start.CloneState(start);
			
			int x=0;
			GameState N=start;
			GameState M;
			
			start.g=0;
			start.h=ManhattanHeuristic(start);
			start.h2=SecondHeuristic(start);
			open.add(start);
			closed.add(start);
			long startTime = System.currentTimeMillis();
			while(!open.isEmpty())
			{
				
				// System.out.println(x);
				if(choice==1)
					N = removeLowestF(open);
				else
					N=returnNewLowest(open);
				x++;
				//N.outputGameState();
				if(N.gameStateSolved())
					break;
				
				//closed.add(N);
				
				rm=N.allMoves();
			
				for (RealMove r : rm) {
					M = N.applyMoveCloning(N, r);
					
					M.bp = r;

					M.bp2 = N;
					
//					if(M.gameStateSolved())
//					{
//						N=M;
//						break out;
//					}
					
					M.g=N.g+1;
					
					
					M.h=ManhattanHeuristic(M);
					if(!M.gameStateSolved())
						M.h2=SecondHeuristic(M);
					
					M.normalizeState();

					if (checkVisited(closed, M) == false) {
						
						open.add(M);
						closed.add(M);
					
					}
					// x++;
				}
				//x++;
			}
			
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			LinkedList<RealMove> path = new LinkedList<RealMove>();

			backtrack(N, path);
			printSolutionInOrder(path, copy);
			 System.out.println("Nodes visited: "+x);
			 System.out.println("Time taken: "+elapsedTime+" milliseconds");
			 System.out.println("Length of solution: "+path.size()+" moves");
		}
		
		public GameState removeLowestF(LinkedList<GameState> open)
		{
			int f=open.getFirst().g+open.getFirst().h;
			GameState temp=open.getFirst();
			int indexR=0;
			int x=0;
			
			for(GameState g:open){
						
				if(g.h==0)
					return g;
				if((g.g+g.h)<f)	{				
					f=g.g+g.h;
					temp=g;
					indexR=x;
				}
				else if((g.g+g.h)==f)
				{
					if(g.h<temp.h){
						temp=g;
					indexR=x;
					}
				}
					x++;
			}
		open.remove(indexR);
		return temp;
		}
		
		public GameState returnNewLowest(LinkedList<GameState> open)
		{
			int f=open.getFirst().h;
			GameState temp=open.getFirst();
			int indexR=0;
			int x=0;
			
			for(GameState g:open){
						
				if(g.h==0)
					return g;
				if(g.h<f)	{				
					f=g.h;
					temp=g;
					indexR=x;
				}
				else if(g.h==f)
				{
					if(g.h2>temp.h2){
						temp=g;
					indexR=x;
					}
				}
					x++;
			}
		open.remove(indexR);
		return temp;
		}
		public int SecondHeuristic(GameState start)
		{
			int[] size=start.findMasterBrickSize(2);
			int zeros=0;
			for (int x = 0; x < start.getHeight(); x++) {
				for (int y = 0; y < start.getWidth(); y++) {
					if (start.CurrentState[x][y] == 2)
					{
						for(int a=x-1;a<size[1]+2+x-1;a++)
						{
							for(int b=y-1;b<size[0]+2+y-1;b++)
							{
								if (start.CurrentState[a][b] == 0||start.CurrentState[a][b] == -1)
									zeros++;
							}
						}
						
//						if (start.CurrentState[x-1][y] == 0||start.CurrentState[x-1][y] == -1)
//							zeros++;
//						if (start.CurrentState[x+1][y] == 0||start.CurrentState[x+1][y] == -1)
//							zeros++;
//						if (start.CurrentState[x][y-1] == 0||start.CurrentState[x][y-1] == -1)
//							zeros++;
//						if (start.CurrentState[x-1][y+1] == 0||start.CurrentState[x-1][y+1] == -1)
//							zeros++;
//						if (start.CurrentState[x+1][y+1] == 0||start.CurrentState[x+1][y+1] == -1)
//							zeros++;
//						if (start.CurrentState[x][y+1] == 0||start.CurrentState[x][y+1] == -1)
//							zeros++;
//						if (start.CurrentState[x-1][y-1] == 0||start.CurrentState[x-1][y-1] == -1)
//							zeros++;
//						if (start.CurrentState[x+1][y-1] == 0||start.CurrentState[x+1][y-1] == -1)
//							zeros++;
//						if (start.CurrentState[x-1][y+2] == 0||start.CurrentState[x-1][y+2] == -1)
//							zeros++;
//						if (start.CurrentState[x+1][y+2] == 0||start.CurrentState[x+1][y+2] == -1)
//							zeros++;
						
						return zeros;
					}
						
				}
			}
			
			
			return zeros;
		}
		public int ManhattanHeuristic(GameState start)
		{
			int x1=0,x0=0,y1=0,y0=0;
			int distance;
			boolean two=false,goal=false;
			boolean left=false,right=false,top=false,bottom=false;
			
			if(start.gameStateSolved())
				return 0;
			
			for(int a=0;a<start.getHeight();a++)
			{
				//System.out.println(start.CurrentState[a][0]);
				if(start.CurrentState[a][0]==-1){
					left=true;
				break;
				}
			}
			
			for(int a=0;a<start.getHeight();a++)
			{
				//System.out.println(start.CurrentState[a][start.getWidth()-1]);
				if(start.CurrentState[a][start.getWidth()-1]==-1){
					right=true;
				break;}
			}
			
			for(int a=0;a<start.getWidth();a++)
			{
				//System.out.println(start.CurrentState[0][a]);
				if(start.CurrentState[0][a]==-1){
					top=true;
				break;}
			}
			
			for(int a=0;a<start.getWidth();a++)
			{
				//System.out.println(start.CurrentState[start.getHeight()-1][a]);
				if(start.CurrentState[start.getHeight()-1][a]==-1){
					bottom=true;
				break;}
			}
			for (int a = 0; a < start.getHeight(); a++) {
				for (int b = 0; b < start.getWidth(); b++) {
					if(left==true){
					if(start.CurrentState[a][b]==-1&&goal==false)
					{
						x1=a;
						y1=b;
						goal=true;
					}
					else if(start.CurrentState[a][b] ==2&&two==false)
					{
						x0=a;
						y0=b;
						two=true;
					}
					}
					else if(right==true)
					{
						if(start.CurrentState[a][b]==-1&&goal==false)
						{
							x1=a;
							y1=b;
							goal=true;
						}
						else if(start.CurrentState[a][b] ==2&&two==false)
						{
							int s[]=start.findMasterBrickSize(2);
							x0=a;
							y0=b+s[0]-1;
							two=true;
						}
					}
					else if(top==true)
					{
						if(start.CurrentState[a][b]==-1&&goal==false)
						{
							x1=a;
							y1=b;
							goal=true;
						}
						else if(start.CurrentState[a][b] ==2&&two==false)
						{
							//int s[]=start.findMasterBrickSize(2);
							x0=a;
							y0=b;
							two=true;
						}
					}
					else if(bottom==true)
					{
						if(start.CurrentState[a][b]==-1&&goal==false)
						{
							x1=a;
							y1=b;
							goal=true;
						}
						else if(start.CurrentState[a][b] ==2&&two==false)
						{
							int s[]=start.findMasterBrickSize(2);
							x0=a+s[1]-1;
							y0=b;
							two=true;
						}
					}

					
				}
			

			}
			distance=Math.abs(x1-x0)+Math.abs(y1-y0);
			
			//System.out.println(distance);
			return distance;
			
		}

		
		LinkedList<RealMove> rm;

		// dfs method takes the stack with root,visited list and root copy
		public void DFS2(Stack<GameState> s, HashSet<GameState> ls, GameState copy) {

			int x=0;
			GameState current;
			GameState tempApp;
			// current.normalizeState();
			long startTime = System.currentTimeMillis();
			while (s.peek().gameStateSolved() == false) {
				// System.out.println(x);
				current = s.pop();

				//Collections.reverse(current.allMoves());
				rm=current.allMoves();
				//Collections.reverse(rm);
				for (RealMove r : rm) {
					tempApp = current.applyMoveCloning(current, r);

					tempApp.bp = r;

					tempApp.bp2 = current;

					tempApp.normalizeState();

					if (checkVisited(ls, tempApp) == false) {
						ls.add(tempApp);
						s.push(tempApp);
						// tempApp.normalizeState();
					}
					// x++;
				}
				x++;
			}
			// x++;
			// }

			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			LinkedList<RealMove> path = new LinkedList<RealMove>();

			backtrack(s.peek(), path);
			printSolutionInOrder(path, copy);
			 System.out.println("Nodes visited: "+x);
			 System.out.println("Time taken: "+elapsedTime+" milliseconds");
			 System.out.println("Length of solution: "+path.size()+" moves");

		}

		// bfs method takes queue with root, visited list, root copy
		public void BFS2(Queue<GameState> q, HashSet<GameState> ls, GameState copy) {

			int x=0;
			GameState current;
			GameState tempApp;
			// current.normalizeState();

			long startTime = System.currentTimeMillis();

			while (q.peek().gameStateSolved() == false) {
				// System.out.println(x);
				current = q.remove();
				// GameState tempApp;

				// current.outputGameState();

				rm = current.allMoves();
				for (RealMove r : rm) {
					tempApp = current.applyMoveCloning(current, r);

					tempApp.bp = r;

					tempApp.bp2 = current;

					// System.out.println("unnorm");
					// tempApp.outputGameState();

					tempApp.normalizeState();

					if (checkVisited(ls, tempApp) == false) {
						ls.add(tempApp);
						q.offer(tempApp);
						// tempApp.normalizeState();
					}
					// x++;
				}
				x++;
			}
			// x++;
			// }

			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;

			LinkedList<RealMove> path = new LinkedList<RealMove>();
			backtrack(q.peek(), path);
			printSolutionInOrder(path, copy);

			 System.out.println("Nodes visited: "+x);
			 System.out.println("Time taken: "+elapsedTime+" milliseconds");
			 System.out.println("Length of solution: "+path.size()+" moves");

		}
		
		
		// prints the normalized solution in the original order
		public void printSolutionInOrder(LinkedList<RealMove> l, GameState copy) {
			LinkedList<GameState> v = new LinkedList<GameState>();
			GameState temp;
			v.add(copy);

			temp = copy.CloneState(copy);
			for (RealMove r : l) {

				// findOriginalLabel(copy, temp, r.getPieceNum());
				RealMove ab = new RealMove(findOriginalLabel(copy, temp, r.getPieceNum()), r.getMoveDirection());
				copy.applyMove(r);
				copy.normalizeState();

				temp.applyMove(ab);

				System.out.println(ab.toString());

				// System.out.println("------------------");

			}

			temp.outputGameState();

		}

		// finds the normalized piece equivalant of the original piece
		public int findOriginalLabel(GameState orig, GameState temp, int m) {
			for (int x = 0; x < orig.getHeight(); x++) {
				for (int y = 0; y < orig.getWidth(); y++) {
					if (orig.CurrentState[x][y] == m)
						return temp.CurrentState[x][y];
				}
			}

			return 0;
		}

		// backtracks from the solution state to the root finding all the moves
		public LinkedList<RealMove> backtrack(GameState q, LinkedList<RealMove> path) {
			// System.out.println(q.bp.toString());
			if (q.bp2 == null) {
				// System.out.println(q.bp2.bp.toString());
				// path.add(q.bp);
				return path;
			} else {
				// RealMove t=
				// path.add(q.bp);

				backtrack(q.bp2, path);
				// q.bp2.outputGameState();
				// System.out.println(q.bp.toString());
				path.add(q.bp);

				return path;

			}

		}
//int check=0;
		// checks if the given state is in the list
		public boolean checkVisited(HashSet<GameState> v, GameState current) {
//			for (GameState g : v) {
//				if (g.stateEqual(current))
//					return true;
				
			//}

			//return false;
		
			
			if(v.contains(current))
				return true;
			else 
				return false;
			
			
			
		}

}
