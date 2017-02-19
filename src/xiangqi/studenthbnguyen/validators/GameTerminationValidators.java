/**
 * 
 */
package xiangqi.studenthbnguyen.validators;
import static xiangqi.common.MoveResult.OK;

import java.util.Map.Entry;
import java.util.function.Predicate;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBaseGame;
import xiangqi.studenthbnguyen.common.XiangqiState;

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
	//	b) Some pieces could be protecting the king form check by standing in the way of enemy pieces. According to the chess rules this pieces can't be moved because the king would enter in check.
	//	c) The king might have all its surrounding squares under the control of enemy pieces, occupied with its own pieces or occupied with protected enemy pieces.
	public static Predicate<XiangqiState> gameNotInStalemate = (state) -> {
		XiangqiBaseGame gameCopy = XiangqiBaseGame.makeDeepCopy(state);
				
		XiangqiColor color = gameCopy.getState().onMove;
		if (!MoveValidators.generalNotInCheck.apply(state, null, null))
			return false;
		
		for (int i = 1; i <= state.board.ranks; i++) {
			for (int j = 1; j <= state.board.files; j++) {
				XNC coordinate = XNC.makeXNC(i, j);
				for (Entry<XNC, XiangqiPiece> entry : gameCopy.getState().board.boardMap.entrySet()) {
		    		if (entry.getValue().getColor() == color && 
		    				gameCopy.checkRules(entry.getKey(), coordinate) == OK) {
		    			return true;
		    		}  
		    	}
			}
		}
		return false;
	};
}
