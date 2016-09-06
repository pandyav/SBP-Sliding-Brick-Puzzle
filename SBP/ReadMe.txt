ReadME DOC

src:SBPMain.java, RealMove.java, GameState.java, Move.java, GameSolver.java

SBPMain.java contains the main()

All of the required functions and algorithms are in GameState.java and GameSolver.java

RealMove class is a ‘move’ object

Move class also contains its allMovesHelp() and allMoves() which will output to the console

NOTE on the evaluation function of A* with manhattan: So there are many nodes with the same f(n) so how I break the ties is by choosing the lowest h(n). Basically, if two nodes have the same f(n), whichever is the closest to the goal will be visited first.


NOTE on the second ADMISSIBLE heuristic for A*: the second heuristic that I created is my own. So the heuristic is the number of empty spots(or 0's) around the master block(2) which also includes the diagonals. Which ever state that has the max number of 0's around the master block, will be visited first. If there are ties, the manhattan distance will be used to break the ties. This heuristic provides a similar solution to the manhattan distance but the number of nodes visited is drastically less. I chose this because if there are more empty blocks near the master block, then there will be many chances for the master block to move near the goal

How to run?
- Run ‘make’ to build (if ‘make’ doesn’t work, do ‘javac *.java’)
- Run ‘java SBPMain’ to run
-enter the location of the input file
-choose a number ( 5 for random search, 6 for bfs, 7 for dfs, 8 for A* with manhattan, 9 for A* with custom heuristic)
-10 to load another file



