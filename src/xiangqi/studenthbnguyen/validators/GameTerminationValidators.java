/**
 * 
 */
package xiangqi.studenthbnguyen.validators;
import static xiangqi.common.MoveResult.*;
import static xiangqi.common.XiangqiPieceType.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.function.Predicate;

import static xiangqi.common.XiangqiColor.*;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBaseGame;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validcoordinategenerators.ValidCoordinateGenerators;

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
	public static Predicate<XiangqiState> gameNotInCheckmate = (state) -> {

		XiangqiBaseGame gameCopy = XiangqiBaseGame.makeDeepCopy(state);
		XiangqiColor color = gameCopy.getState().onMove;
		
		// General is not in check
		if (MoveValidators.generalNotInCheck.apply(state, null, null))
			return true;
		
		// General can move out of check
		XNC generalXNC = gameCopy.getState().board.findPiece(GENERAL, color);
		LinkedList<XNC> validCoordinates = ValidCoordinateGenerators.generalValidCoordinateGenerator.apply(generalXNC);
		ListIterator<XNC> validListIterator = validCoordinates.listIterator();
		while (validListIterator.hasNext()) {
			if (gameCopy.checkRules(generalXNC, validListIterator.next()) == OK)
				return true;
		}
		
		// Check can be blocked
		XiangqiColor attackerColor = (color == RED) ? BLACK : RED;
		XNC attackerCoordinate = gameCopy.getState().board.findPiece(state.generalAttacker.getPieceType(), attackerColor);
		List<XNC> intermediateCoordinates = XNC.generateIntermediateCoordinates(generalXNC, attackerCoordinate);		
		ListIterator<XNC> intermediateListIterator = intermediateCoordinates.listIterator();
		while (intermediateListIterator.hasNext()) {
			XNC coordinate = intermediateListIterator.next();
			if (hasPathTo(gameCopy, color, coordinate)) return true;
		}
		
		// checking piece can be captured
		if (hasPathTo(gameCopy, color, attackerCoordinate)) return true;
		return false;
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
		
		// General must not be in check
		if (!MoveValidators.generalNotInCheck.apply(state, null, null))
			return false;
		
		// Check to see whether a valid move can be made
		for (int i = 1; i <= state.board.ranks; i++) {
			for (int j = 1; j <= state.board.files; j++) {
				XNC coordinate = XNC.makeXNC(i, j);
				if (hasPathTo(gameCopy, color, coordinate)) return true;
			}
		}
		return false;
	};
	
	/**
	 * Determine whether there's a way for any pieces of the given color to move to the given coordinate
	 * @param game the game
	 * @param color the color
	 * @param coordinate the coordinate 
	 * @return true if there's a way for any pieces of the given color to move to the given coordinate, false otherwise
	 */
	private static boolean hasPathTo(XiangqiBaseGame game, XiangqiColor color, XNC coordinate) {
		for (Entry<XNC, XiangqiPiece> entry : game.getState().board.boardMap.entrySet()) {
    		if (entry.getValue().getColor() == color && 
    				game.checkRules(entry.getKey(), coordinate) == OK) {
    			return true;
    		}  
    	}
		return false;
	}
}
