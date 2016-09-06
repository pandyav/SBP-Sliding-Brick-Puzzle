import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Vaibhav
 *
 */
public class Move {

	public Move() {

	}

	//prints all moves done by this piece
	public void allMovesHelp(GameState g, int i) {
		for (RealMove s : g.allMovesHelp(i)) {
			System.out.println(s.toString());
		}

	}

	//prints all moves done in the board,first sortd by # then by direction
	public void allMoves(GameState g) {
		LinkedList<RealMove> all = g.allMoves();

		System.out.println("Moves sorted by piece #:");

		LinkedList<RealMove> allSorted = new LinkedList<RealMove>();

		for (RealMove s : all) {
			if (s.getMoveDirection().equals("up"))
				allSorted.add(s);
		}

		for (RealMove s : all) {
			if (s.getMoveDirection().equals("down"))
				allSorted.add(s);
		}

		for (RealMove s : all) {
			if (s.getMoveDirection().equals("left"))
				allSorted.add(s);
		}

		for (RealMove s : all) {
			if (s.getMoveDirection().equals("right"))
				allSorted.add(s);
		}
		for (RealMove s : all) {

			System.out.println(s.toString());

		}

		System.out.println("--------------------------------------");
		System.out.println("Moves sorted by direction:");
		for (RealMove s : allSorted) {

			System.out.println(s.toString());

		}

	}

	//actual applyMove() is found in GameState class
	public void applyMove(GameState g, RealMove rm) {
		g.applyMove(rm);
	}
}
