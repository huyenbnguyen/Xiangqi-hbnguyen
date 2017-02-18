/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import xiangqi.common.XiangqiPiece;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiState;
import static xiangqi.common.XiangqiPieceType.*;

import xiangqi.common.XiangqiCoordinate;

/**
 * 
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
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isDestinationClear = (state, from, to) -> {
		XiangqiPiece piece = state.board.getPieceAt(to);
		boolean result = (piece.getPieceType() == NONE || piece.getColor() != state.onMove);
		if (!result) 
			state.moveMessage = "Attempt to capture your own piece";
		return result;
	};
}
