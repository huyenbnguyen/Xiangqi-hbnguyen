/**
 * 
 */
package xiangqi.studenthbnguyen.validators;
import java.util.function.Predicate;
import xiangqi.studenthbnguyen.common.XiangqiBaseGame;

/**
 * @author huyennguyen
 *
 */
public class GameTerminationValidators {

	// Checkmate Conditions
	//	a) King is in check
	//	b) King can't move out of check.
	//	c) Check can't be blocked
	//	d) checking piece can't be captured
	public static Predicate<XiangqiBaseGame> gameInCheckmate = (game) -> {

		XiangqiBaseGame gameCopy = XiangqiBaseGame.makeDeepCopy(game.getState());
		//	a) King is in check => easy
		//	b) King can't move out of check => generate moves and check whether king is in check for any
		//	c) Check can't be blocked => get series of coordinates, determine of any piece can go to each corrdiante
		//	d) checking piece can't be captured => make all the other pieces go to that coordinate

		return true;
	};

	// stalemate conditions:
	// 1) King is NOT in check
	// 2) there are no legal moves: 
	//     for each piece, generate all possible moves.
	// 			for each move, check if making that move is valid
	// a) Some might be blocked by other pieces. 
	// The squares on which to move might be occupied with other pieces of the same player or sometimes with enemy pieces.
	//	Some pieces could be protecting the king form check by standing in the way of enemy pieces. According to the chess rules this pieces can't be moved because the king would enter in check.
	//	The king might have all its surrounding squares under the control of enemy pieces, occupied with its own pieces or occupied with protected enemy pieces.
	public static Predicate<XiangqiBaseGame> gameInStalemate = (game) -> {

		return true;
	};
}
