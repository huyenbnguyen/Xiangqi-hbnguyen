/**
 * 
 */
package xiangqi.studenthbnguyen.validators;
import static xiangqi.common.XiangqiPieceType.NONE;

import java.util.List;
import java.util.ListIterator;

import xiangqi.common.MoveResult;
import static xiangqi.common.MoveResult.*;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiState;

/**
 * Validators BEFORE making a move.
 * These are driven by the rules of the individual piece.
 * @author huyennguyen
 *
 */
public class PieceValidators {
	public static MoveValidator<XiangqiState, XNC, MoveResult> hasNoBlockingPiece = (state, from, to) -> {
		return (countBlockingPiece(state, from, to) == 0) ? OK : ILLEGAL;
	};
	
	public static MoveValidator<XiangqiState, XNC, MoveResult> isMoveOrthogonal = (state, from, to) -> {
		boolean result = from.isOrthogonal(to) && (hasNoBlockingPiece.apply(state, from, to) == OK);
		if (!result) { 
			state.moveMessage = "Piece must move orthogonally";
			return ILLEGAL;
		}
		return OK;
	};

	public static MoveValidator<XiangqiState, XNC, MoveResult> isMoveDiagonal = (state, from, to) -> {
		boolean result = from.isDiagonalTo(to) && (hasNoBlockingPiece.apply(state, from, to) == OK);
		if (!result) {
			state.moveMessage = "Piece must move diagonally";
			return ILLEGAL;
		}
		return OK;
	};

	public static MoveValidator<XiangqiState, XNC, MoveResult> isDistanceOneAndOrthogonal = (state, from, to) -> {
		boolean result = from.isDistanceOneAndOrthogonal(to);
		if (!result) {
			state.moveMessage = "Piece must move forward one step";
			return ILLEGAL;
		}
		return OK;
	};

	public static MoveValidator<XiangqiState, XNC, MoveResult> isForwardOneStep = (state, from, to) -> {
		boolean result = from.isForwardOneStep(to, state.onMove);
		if (!result) {
			state.moveMessage = "Piece must move forward one step";
			return ILLEGAL;
		}
		return OK;
	};
	
	public static MoveValidator<XiangqiState, XNC, MoveResult> moveDiagonallyTwoSteps = (state, from, to) -> {
		boolean result = from.moveDiagonallyTwoSteps(to) && (hasNoBlockingPiece.apply(state, from, to) == OK);
		if (!result) {
			state.moveMessage = "Piece must move diagonally two steps";
			return ILLEGAL;
		}
		return OK;
	};
	
	public static MoveValidator<XiangqiState, XNC, MoveResult> moveLeftOrRightOrUpOneStep = (state, from, to) -> {
		boolean result = from.moveLeftOrRightOrUpOneStep(to, state.onMove);
		if (!result) {
			state.moveMessage = "Piece must move left or righ one step";
			return ILLEGAL;
		}
		return OK;
	};
	
	public static MoveValidator<XiangqiState, XNC, MoveResult> isInPalace = (state, from, to) -> {
		boolean result = from.isInPalace(to, state.onMove);
		if (!result) {
			state.moveMessage = "Red General must stay in the palace";
			return ILLEGAL;
		}
		return OK;
	};
	
	public static MoveValidator<XiangqiState, XNC, MoveResult> isNotCrossingRiver = (state, from, to) -> {
		boolean result = from.isNotCrossingRiver(to, state.onMove);
		if (!result) {
			state.moveMessage = "Red Elephant must not cross river";
			return ILLEGAL;
		}
		return OK;
	};
	
	public static MoveValidator<XiangqiState, XNC, MoveResult> isValidCannonMove = (state, from, to) -> {		
		XiangqiPiece destinationPiece = state.board.getPieceAt(to);
		
		// normal move
		if (destinationPiece.getPieceType() == NONE) {
			return isMoveOrthogonal.apply(state, from, to);
		} else { // capture move
			if (!(from.isOrthogonal(to)) &&
					(destinationPiece.getPieceType() != NONE) &&
					(destinationPiece.getColor() != state.onMove) &&
					(countBlockingPiece(state, from, to) == 1)) {
				state.moveMessage = "ILLEGAL: Cannon made an invalid move";
				return ILLEGAL;
			}
			return OK;
		}
	};
	
	private static int countBlockingPiece(XiangqiState state, XNC from, XNC to) {
		int blockingPieceNum = 0;
		List<XNC> intermediateCoordinates = XNC.generateIntermediateCoordinates(from, to);		
		ListIterator<XNC> listIterator = intermediateCoordinates.listIterator();
		while (listIterator.hasNext()) {
			if (state.board.getPieceAt(listIterator.next()).getPieceType() != NONE)
				blockingPieceNum++;
		}
		return blockingPieceNum;
	}
}
