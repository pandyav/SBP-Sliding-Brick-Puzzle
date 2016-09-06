import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import org.omg.CORBA.Current;

/**
 * 
 */

/**
 * @author Vaibhav
 *
 */
public class GameState implements Cloneable {

	// current game state data structure in 2D array matrix
    int CurrentState[][];
	private int width, height;
	private File file;
	
	int g,h,h2;

	RealMove bp;// backpointer move of current state
	GameState bp2 = null;// predecessor of current state

	// this constructor clones the state
	public GameState(GameState gs) {
		CurrentState = new int[gs.CurrentState.length][];
		for (int i = 0; i < gs.CurrentState.length; i++)
			CurrentState[i] = gs.CurrentState[i].clone();

		this.width = gs.width;
		this.height = gs.height;
		this.file = gs.file;
	}

	public GameState() throws FileNotFoundException, UnsupportedEncodingException {

	}

	// load game state from file into the 2d array
	public void loadGameState(File inputFile) throws FileNotFoundException {
		file = inputFile;
		Scanner sc = new Scanner(inputFile);
		String[] s = sc.nextLine().trim().split("\\,");
		// System.out.println(s[2]);
		width = Integer.parseInt(s[0]);
		height = Integer.parseInt(s[1]);
		int x = 0;
		CurrentState = new int[height][width];
		while (sc.hasNextLine()) {
			s = sc.nextLine().trim().split("\\,");

			for (int a = 0; a < s.length; a++)
				CurrentState[x][a] = Integer.parseInt(s[a]);

			x++;
			// System.out.println(sc.nextLine());
		}

		sc.close();

	}

	// prints out the current state
	public void outputGameState() {
		System.out.println(width + "," + height + ",");

		for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				System.out.print(CurrentState[a][b] + ",");

			}
			System.out.println();

		}
	}

	// clones and returns the given state
	public GameState CloneState(GameState gs) {
		GameState gs2 = new GameState(gs);

		return gs2;
	}

	public boolean gameStateSolved() {
		for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				if (CurrentState[a][b] == -1)
					return false;
			}

		}

		return true;
	}

	// finds the moves of given piece and returns the list
	public LinkedList<RealMove> allMovesHelp(int i) {
		LinkedList<RealMove> ls = new LinkedList<RealMove>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		boolean zero = false, negOne = false;

		int z = 1, n = 1;

		int[] size = findMasterBrickSize(i);

		out: for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				if (CurrentState[a][b] == i) {
					// if(CurrentState[a][b]==2)
					// {
					for (int l = b; l < size[0] + b; l++) {

						temp.add(CurrentState[a - 1][l]);

					}

					if (checkZeroorOne(temp, i)) {
						ls.add(new RealMove(i, "up"));

					}
					
					
					temp.clear();
					for (int l = b; l < size[0] + b; l++) {
						temp.add(CurrentState[a + size[1]][l]);
					}

					if (checkZeroorOne(temp, i)) {
						ls.add(new RealMove(i, "down"));

					}

					temp.clear();

					for (int l = a; l < size[1] + a; l++) {
						temp.add(CurrentState[l][b - 1]);
					}

					if (checkZeroorOne(temp, i)) {
						ls.add(new RealMove(i, "left"));

					}

					

					temp.clear();
					for (int l = a; l < size[1] + a; l++) {
						temp.add(CurrentState[l][b + size[0]]);
					}

					if (checkZeroorOne(temp, i)) {
						ls.add(new RealMove(i, "right"));

					}

					break out;

				}

			}

		}

		// System.out.println(ls.toString());
		return ls;
		// findMasterBrickSize();

	}

	// finds all of the moves on board and returns the list
	public LinkedList<RealMove> allMoves() {
		ArrayList<Integer> al = new ArrayList<Integer>();
		LinkedList<RealMove> all = new LinkedList<RealMove>();
		LinkedList<RealMove> allSorted = new LinkedList<RealMove>();

		// all.add("Sorted By Piece #:");
		for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				if (CurrentState[a][b] >= 2) {

					if (!al.contains(CurrentState[a][b])) {
						al.add(CurrentState[a][b]);
					}
				}
			}
		}

		Collections.sort(al);
		for (Integer i : al) {
			all.addAll(allMovesHelp(i));
		}
		
		return all;
	}

	// checks if the given move is in the list
	public boolean checkMoveInList(LinkedList<RealMove> rml, RealMove rm) {
		for (RealMove r : rml) {
			if (r.getMoveDirection().equals(rm.getMoveDirection()))
				return true;
		}

		return false;
	}

	// applies the given move to the current state and returns true if applied,
	// else returns false
	public boolean applyMove(RealMove rm) {
		if (checkMoveInList(allMovesHelp(rm.getPieceNum()), rm)) {
			int[] size = findMasterBrickSize(rm.getPieceNum());

			out: for (int a = 0; a < height; a++) {
				for (int b = 0; b < width; b++) {
					if (CurrentState[a][b] == rm.getPieceNum()) {
						if (rm.getMoveDirection().equals("up")) {

							for (int l = b; l < size[0] + b; l++) {
								CurrentState[a - 1][l] = CurrentState[a + size[1] - 1][l];
								CurrentState[a + size[1] - 1][l] = 0;
							}

						} else if (rm.getMoveDirection().equals("left")) {

							for (int l = a; l < size[1] + a; l++) {
								CurrentState[l][b - 1] = CurrentState[l][b + size[0] - 1];
								CurrentState[l][b + size[0] - 1] = 0;
							}
						} else if (rm.getMoveDirection().equals("down")) {

							for (int l = b; l < size[0] + b; l++) {

								CurrentState[a + size[1]][l] = CurrentState[a + size[1] - 1][l];
								CurrentState[a][l] = 0;
							}
						} else if (rm.getMoveDirection().equals("right")) {

							for (int l = a; l < size[1] + a; l++) {

								CurrentState[l][b + size[0]] = CurrentState[l][b + size[0] - 1];
								CurrentState[l][b] = 0;
							}
						}

						break out;
					}

				}
			}
			return true;
		} else {
			// System.out.println("Error: move not possible!!");
			return false;
		}

		// outputGameState();
	}

	// clones the give state,applies the given move and returns the state
	public GameState applyMoveCloning(GameState g, RealMove rm) {
		GameState cl = CloneState(g);

		cl.applyMove(rm);
		return cl;
	}

	// checks if given state is equal the current state
	public boolean stateEqual(GameState gs) {

		// return Arrays.deepEquals(CurrentState, gs.CurrentState);
		try {
			for (int a = 0; a < height; a++) {
				for (int b = 0; b < width; b++) {
					if (CurrentState[a][b] != gs.CurrentState[a][b])
						return false;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public void normalizeState() {
		int nextIdx = 3;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (CurrentState[i][j] == nextIdx)
					nextIdx++;
				else if (CurrentState[i][j] > nextIdx) {
					swapIdx(nextIdx, CurrentState[i][j]);
					nextIdx++;
				}
			}
		}
	}

	public void swapIdx(int idx1, int idx2) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (CurrentState[i][j] == idx1)
					CurrentState[i][j] = idx2;
				else if (CurrentState[i][j] == idx2) {
					CurrentState[i][j] = idx1;

				}

			}
		}
	}

	// random walks
	public void RandomWalks(int N) {
		LinkedList<RealMove> rm;
		Random rn = new Random();
		int ran;
		outputGameState();
		System.out.println();

		for (int n = 0; n < N; n++) {
			rm = allMoves();
			ran = rn.nextInt(((rm.size() - 1) - 0) + 1) + 0;
			// System.out.println(ran);
			applyMove(rm.get(ran));
			normalizeState();

			System.out.println(rm.get(ran).toString());
			System.out.println();
			outputGameState();
			System.out.println();
			if (gameStateSolved())
				break;
		}

	}

//	// bfs caller method takes initial state
//	public void BFS(GameState g) {
//		Queue<GameState> q = new LinkedList<GameState>();
//		q.add(g);
//		LinkedList<GameState> ls = new LinkedList<GameState>();
//		ls.add(g);
//
//		BFS2(q, ls, CloneState(g));
//		// BFSHelper(q,ls);
//	}
//
//	// dfs caller method takes inital state
//	public void DFS(GameState g) {
//		Stack<GameState> s = new Stack<GameState>();
//		s.push(g);
//
//		LinkedList<GameState> ls = new LinkedList<GameState>();
//		ls.add(g);
//
//		DFS2(s, ls, CloneState(g));
//	}
//
//	int x = 1;
//	LinkedList<RealMove> rm;
//
//	// dfs method takes the stack with root,visited list and root copy
//	public void DFS2(Stack<GameState> s, LinkedList<GameState> ls, GameState copy) {
//
//		GameState current;
//		GameState tempApp;
//		// current.normalizeState();
//		long startTime = System.currentTimeMillis();
//		while (s.peek().gameStateSolved() == false) {
//			// System.out.println(x);
//			current = s.pop();
//
//			//Collections.reverse(current.allMoves());
//			rm=current.allMoves();
//			//Collections.reverse(rm);
//			for (RealMove r : rm) {
//				tempApp = applyMoveCloning(current, r);
//
//				tempApp.bp = r;
//
//				tempApp.bp2 = current;
//
//				tempApp.normalizeState();
//
//				if (checkVisited(ls, tempApp) == false) {
//					ls.add(tempApp);
//					s.push(tempApp);
//					// tempApp.normalizeState();
//				}
//				// x++;
//			}
//			x++;
//		}
//		// x++;
//		// }
//
//		long stopTime = System.currentTimeMillis();
//		long elapsedTime = stopTime - startTime;
//		LinkedList<RealMove> path = new LinkedList<RealMove>();
//
//		backtrack(s.peek(), path);
//		printSolutionInOrder(path, copy);
//		 System.out.println("Nodes visited: "+x);
//		 System.out.println("Time taken: "+elapsedTime+" milliseconds");
//		 System.out.println("Length of solution: "+path.size()+" moves");
//
//	}
//
//	// bfs method takes queue with root, visited list, root copy
//	public void BFS2(Queue<GameState> q, LinkedList<GameState> ls, GameState copy) {
//
//		GameState current;
//		GameState tempApp;
//		// current.normalizeState();
//
//		long startTime = System.currentTimeMillis();
//
//		while (q.peek().gameStateSolved() == false) {
//			// System.out.println(x);
//			current = q.remove();
//			// GameState tempApp;
//
//			// current.outputGameState();
//
//			rm = current.allMoves();
//			for (RealMove r : rm) {
//				tempApp = applyMoveCloning(current, r);
//
//				tempApp.bp = r;
//
//				tempApp.bp2 = current;
//
//				// System.out.println("unnorm");
//				// tempApp.outputGameState();
//
//				tempApp.normalizeState();
//
//				if (checkVisited(ls, tempApp) == false) {
//					ls.add(tempApp);
//					q.offer(tempApp);
//					// tempApp.normalizeState();
//				}
//				// x++;
//			}
//			x++;
//		}
//		// x++;
//		// }
//
//		long stopTime = System.currentTimeMillis();
//		long elapsedTime = stopTime - startTime;
//
//		LinkedList<RealMove> path = new LinkedList<RealMove>();
//		backtrack(q.peek(), path);
//		printSolutionInOrder(path, copy);
//
//		 System.out.println("Nodes visited: "+x);
//		 System.out.println("Time taken: "+elapsedTime+" milliseconds");
//		 System.out.println("Length of solution: "+path.size()+" moves");
//
//	}

//	// prints the normalized solution in the original order
//	public void printSolutionInOrder(LinkedList<RealMove> l, GameState copy) {
//		LinkedList<GameState> v = new LinkedList<GameState>();
//		GameState temp;
//		v.add(copy);
//
//		temp = CloneState(copy);
//		for (RealMove r : l) {
//
//			// findOriginalLabel(copy, temp, r.getPieceNum());
//			RealMove ab = new RealMove(findOriginalLabel(copy, temp, r.getPieceNum()), r.getMoveDirection());
//			copy.applyMove(r);
//			copy.normalizeState();
//
//			temp.applyMove(ab);
//
//			System.out.println(ab.toString());
//
//			// System.out.println("------------------");
//
//		}
//
//		temp.outputGameState();
//
//	}
//
//	// finds the normalized piece equivalant of the original piece
//	public int findOriginalLabel(GameState orig, GameState temp, int m) {
//		for (int x = 0; x < height; x++) {
//			for (int y = 0; y < width; y++) {
//				if (orig.CurrentState[x][y] == m)
//					return temp.CurrentState[x][y];
//			}
//		}
//
//		return 0;
//	}
//
//	// backtracks from the solution state to the root finding all the moves
//	public LinkedList<RealMove> backtrack(GameState q, LinkedList<RealMove> path) {
//		// System.out.println(q.bp.toString());
//		if (q.bp2 == null) {
//			// System.out.println(q.bp2.bp.toString());
//			// path.add(q.bp);
//			return path;
//		} else {
//			// RealMove t=
//			// path.add(q.bp);
//
//			backtrack(q.bp2, path);
//			// q.bp2.outputGameState();
//			// System.out.println(q.bp.toString());
//			path.add(q.bp);
//
//			return path;
//
//		}
//
//	}
//
//	// checks if the given state is in the list
//	public boolean checkVisited(LinkedList<GameState> v, GameState current) {
//		for (GameState g : v) {
//			if (g.stateEqual(current))
//				return true;
//
//			// x++;
//		}
//
//		return false;
//	}

	// returns true if p is 0 or -1
	public boolean checkZeroorOne(ArrayList<Integer> al, int p) {
		int z = 0, n = 0;
		for (Integer i : al) {
			if (i == 0)
				z = 1;
			else {
				z = 2;
				break;
			}

		}
		if (p == 2) {
			for (Integer i : al) {
				if (i == -1)
					n = 1;
				else {
					n = 2;
					break;
				}

			}
		}
		if (p == 2) {
			if (z == 1)
				return true;
			else if (n == 1)
				return true;
			else
				return false;
		} else {
			if (z == 1)
				return true;
			else
				return false;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	// find the size of the given piece in width and height [w][h];disregard the
	// method name
	public int[] findMasterBrickSize(int p) {
		int w = 0, h = 0;
		int[] size = new int[2];
		out: for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				if (CurrentState[a][b] == p) {
					w++;
					// h++;
				} else if (w != 0)
					break out;

			}

		}

		for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				if (CurrentState[a][b] == p) {

					h++;
					break;
				}

			}

		}

		size[0] = w;
		size[1] = h;

		return size;
		// System.out.println(h);
	}
	
	@Override
	public int hashCode()
	{
		return Arrays.deepHashCode(CurrentState);
		
	}
	
	@Override
	public boolean equals(Object o)
	{
		GameState temp=(GameState)o;
		//return Arrays.deepEquals(CurrentState, temp.CurrentState);
		return stateEqual(temp);
	}
	
	
	
	
}
