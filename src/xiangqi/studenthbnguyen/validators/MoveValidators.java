/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBaseGame;
import xiangqi.studenthbnguyen.common.XiangqiState;
import static xiangqi.common.MoveResult.*;
import static xiangqi.common.XiangqiPieceType.*;
import java.awt.Point;
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

	public static MoveValidator<XiangqiState, XNC, Boolean> hasNoBlockingPiece = (state, from, to) -> {
		Point directionVector = new Point((int) Math.signum(to.getRank() - from.getRank()), 
				(int) Math.signum(to.getFile() - from.getFile()));
		XNC coordinate = XNC.makeXNC(from.getRank() + directionVector.x, from.getFile() + directionVector.y);
		while (!coordinate.equals(to)) {
			if (state.board.getPieceAt(coordinate).getPieceType() != NONE)
				return false;
			coordinate = XNC.makeXNC(coordinate.getRank() + directionVector.x, coordinate.getFile() + directionVector.y);
		}
		return true;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> generalNotInCheck = (state, from, to) -> {
		// pretend like you're creating a new game with the move made
		XiangqiBaseGame gameCopy = XiangqiBaseGame.makeDeepCopy(state);
		if (from != null && to != null) 
			gameCopy.getState().board.movePiece(from, to);
		
		// for the new game, see if any opponent pieces can capture the general
		XiangqiColor color = gameCopy.getState().onMove;
		XNC generalCoordinate = gameCopy.getState().board.findGeneral(color);
    	for (Entry<XNC, XiangqiPiece> entry : gameCopy.getState().board.boardMap.entrySet()) {
    		if (entry.getValue().getColor() != color && 
    				gameCopy.makeMove(entry.getKey(), generalCoordinate) == OK) {
    			return false;
    		}  
    	}
    	return true;
	};
}
