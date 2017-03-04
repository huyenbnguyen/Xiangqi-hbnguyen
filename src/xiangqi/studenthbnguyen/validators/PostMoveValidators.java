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
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.util.FindPieceToMakeMove;
import xiangqi.studenthbnguyen.util.FixedSizeQueue;
import xiangqi.studenthbnguyen.validatorchecker.PieceChecker;
import xiangqi.studenthbnguyen.validcoordinategenerators.ValidCoordinateGenerators;

/**
 * @author huyennguyen
 *
 */
public class PostMoveValidators {

	private static Predicate<XiangqiState> generalInCheck = (state) -> {
		XNC generalCoordinate = state.board.findPiece(GENERAL, state.onMove);
		for (Entry<XNC, XiangqiPieceImpl> entry : state.board.boardMap.entrySet()) {
			XiangqiPieceImpl piece = entry.getValue();
			boolean a = true;
			if (piece.getColor() != state.onMove && 
					PieceChecker.runChecker(state, entry.getKey(), generalCoordinate) == OK) { 
				state.generalAttacker = piece;
//				stateCopy.moveMessage = "ILLEGAL: Move puts the general in check";
				return true;
			}  
		}
		state.generalAttacker = null;
		return false;
	};
	
	// Checkmate Conditions
	//	a) King is in check
	//	b) King can't move out of check.
	//	c) Check can't be blocked
	//	d) checking piece can't be captured
	public static Predicate<XiangqiState> gameInCheckmate = (state) -> {

//		XiangqiBaseGame gameCopy = XiangqiBaseGame.makeDeepCopy(state);
//		XiangqiColor color = gameCopy.getState().onMove;
		FindPieceToMakeMove findPieceToMakeMove;
		// General in check
		if (!generalInCheck.test(state))
			return false; 
//		
//		 General can move out of check
		XNC generalXNC = state.board.findPiece(GENERAL, state.onMove);
		LinkedList<XNC> validCoordinates = ValidCoordinateGenerators.generalValidCoordinateGenerator.apply(generalXNC);
		ListIterator<XNC> validListIterator = validCoordinates.listIterator();
		while (validListIterator.hasNext()) {
			XNC newCoordinate = validListIterator.next();
			XiangqiState stateCopy = XiangqiState.makeDeepCopy(state);
			if (PreMoveValidators.checkBounds(newCoordinate, stateCopy) &&
					PreMoveValidators.isGeneralInCheck.apply(stateCopy, generalXNC, newCoordinate) == OK)
				return false;
		}
//		
		// Check can be blocked
		XiangqiPieceImpl attacker = (XiangqiPieceImpl) state.generalAttacker;
		XNC attackerCoordinate = state.board.findPiece(attacker.getPieceType(), attacker.getColor());
		List<XNC> intermediateCoordinates = XNC.generateIntermediateCoordinates(generalXNC, attackerCoordinate);		
		ListIterator<XNC> intermediateListIterator = intermediateCoordinates.listIterator();
		while (intermediateListIterator.hasNext()) {
			XNC coordinate = intermediateListIterator.next();
			findPieceToMakeMove = hasPathTo(state, state.onMove, coordinate);
			if (findPieceToMakeMove.hasPiece && !generalInCheck.test(findPieceToMakeMove.newState)) 
				return false;
		}
		
		// checking piece can be captured
		findPieceToMakeMove = hasPathTo(state, state.onMove, attackerCoordinate);
		if (findPieceToMakeMove.hasPiece && !generalInCheck.test(findPieceToMakeMove.newState)) return false;
		return true;
	};

	// not in stalemate conditions:
	// 1) King is in check
	// OR
	// 2) there's a legal move: 
	//     for each piece, generate all possible moves.
	// 			for each move, check if making that move is valid
	// a) Some might be blocked by other pieces. 
	// The squares on which to move might be occupied with other pieces of the same player or sometimes with enemy pieces.
	//	b) Some pieces could be protecting the king form check by standing in the way of enemy pieces. According to the chess rules this pieces can't be moved because the king would enter in check.
	//	c) The king might have all its surrounding squares under the control of enemy pieces, occupied with its own pieces or occupied with protected enemy pieces.
	public static Predicate<XiangqiState> gameInStalemate = (state) -> {
//		XiangqiBaseGame gameCopy = XiangqiBaseGame.makeDeepCopy(state);	
//		XiangqiColor color = gameCopy.getState().onMove;
//		
		// General must not be in check
		if (generalInCheck.test(state))
			return false;
		
		// Check to see whether a valid move can be made by any pieces
		for (int i = 1; i <= state.board.ranks; i++) {
			for (int j = 1; j <= state.board.files; j++) {
				XNC coordinate = XNC.makeXNC(i, j);
				FindPieceToMakeMove findPieceToMakeMove = hasPathTo(state, state.onMove, coordinate);
				if (findPieceToMakeMove.hasPiece && !generalInCheck.test(findPieceToMakeMove.newState)) 
					return false;
			}
		}
		return true;
	};
	
	/**
	 * Determine whether there's a way for any pieces of the given color to move to the given coordinate
	 * @param game the game
	 * @param color the color
	 * @param coordinate the coordinate 
	 * @return true if there's a way for any pieces of the given color to move to the given coordinate, false otherwise
	 */
	private static FindPieceToMakeMove hasPathTo(XiangqiState state, XiangqiColor color, XNC coordinate) {
		
		for (Entry<XNC, XiangqiPieceImpl> entry : state.board.boardMap.entrySet()) {
    		if (entry.getValue().getColor() == color && 
    				PieceChecker.runChecker(state, entry.getKey(), coordinate) == OK) {
    			XiangqiState stateCopy = XiangqiState.makeDeepCopy(state);
    			stateCopy.board.movePiece(entry.getKey(), coordinate);
    			return new FindPieceToMakeMove(true, stateCopy);
    		}  
    	}
		return new FindPieceToMakeMove(false, state);
	}
}