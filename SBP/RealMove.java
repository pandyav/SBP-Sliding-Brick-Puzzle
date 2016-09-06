/**
 * 
 */

/**
 * @author Vaibhav
 *
 */

//move object class
public class RealMove {
	
	private int pieceNum;
	private String moveDirection;
	
	public RealMove(int i, String d)
	{
		pieceNum=i;
		moveDirection=d;
	}
	
	public int getPieceNum() {
		return pieceNum;
	}
	public String getMoveDirection() {
		return moveDirection;
	}
	
	public String toString()
	{
		return "move("+pieceNum+","+moveDirection+")";
	}
	
	

}
