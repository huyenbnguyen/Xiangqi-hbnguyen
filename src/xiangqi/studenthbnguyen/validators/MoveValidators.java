/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBaseGame;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import static xiangqi.common.MoveResult.*;
import static xiangqi.common.XiangqiPieceType.*;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

/**
 * Validators BEFORE making a move.
 * These are driven by the rules of the game.
 * @author huyennguyen
 *
 */
public class MoveValidators {
	public static MoveValidator<XiangqiState, XNC, Boolean> isCorrectColor = (state, from, to) -> {
		XiangqiPiece pieceSource = state.board.getPieceAt(from);
		boolean result = (pieceSource.getPieceType() != NONE && pieceSource.getColor() == state.onMove);
		if (!result) 
			state.moveMessage = "Attempt to move from an empty location or move the opponent's piece";
		return result; 
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isDestinationValid = (state, from, to) -> {
		XiangqiPiece piece = state.board.getPieceAt(to);
		boolean result = (piece.getPieceType() == NONE || piece.getColor() != state.onMove);
		if (!result) 
			state.moveMessage = "Attempt to capture your own piece";
		return result;
	};


	
	public static MoveValidator<XiangqiState, XNC, Boolean> generalNotInCheck = (state, from, to) -> {
		// pretend like you're creating a new game with the move made
		XiangqiBaseGame gameCopy = XiangqiBaseGame.makeDeepCopy(state);
		
		gameCopy.getState().board.movePiece(from, to);	
		gameCopy.switchTurn();
		
		// for the new game, see if any opponent pieces can capture the general
		XiangqiColor color = gameCopy.getState().onMove;
		XNC generalCoordinate = gameCopy.getState().board.findPiece(GENERAL, state.onMove, 1);
    	for (Entry<XNC, XiangqiPieceImpl> entry : gameCopy.getState().board.boardMap.entrySet()) {
    		if (entry.getValue().getColor() == color && 
    				gameCopy.validatePieceRules(entry.getKey(), generalCoordinate) == OK) { 
    			// if there's not a move to be made, it means we're dealing with the game itself, not a copy
    			state.generalAttacker = entry.getValue();
    			return false;
    		}  
    	}
    	state.generalAttacker = null;
    	return true;
	};
	
	
}
