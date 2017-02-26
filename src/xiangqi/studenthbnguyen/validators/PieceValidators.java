/**
 * 
 */
package xiangqi.studenthbnguyen.validators;
import static xiangqi.common.XiangqiPieceType.NONE;

import java.util.List;
import java.util.ListIterator;

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
	public static MoveValidator<XiangqiState, XNC, Boolean> hasNoBlockingPiece = (state, from, to) -> {
		return (countBlockingPiece(state, from, to) == 0);
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isMoveOrthogonal = (state, from, to) -> {
		boolean result = from.isOrthogonal(to) && hasNoBlockingPiece.apply(state, from, to);
		if (!result) 
			state.moveMessage = "Piece must move orthogonally";
		return result;
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isMoveDiagonal = (state, from, to) -> {
		boolean result = from.isDiagonalTo(to) && hasNoBlockingPiece.apply(state, from, to);
		if (!result) 
			state.moveMessage = "Piece must move diagonally";
		return result;
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isDistanceOneAndOrthogonal = (state, from, to) -> {
		boolean result = from.isDistanceOneAndOrthogonal(to);
		if (!result) 
			state.moveMessage = "Piece must move forward one step";
		return result;
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isForwardOneStep = (state, from, to) -> {
		boolean result = from.isForwardOneStep(to, state.onMove);
		if (!result) 
			state.moveMessage = "Piece must move forward one step";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> moveDiagonallyTwoSteps = (state, from, to) -> {
		boolean result = from.moveDiagonallyTwoSteps(to) && hasNoBlockingPiece.apply(state, from, to);
		if (!result) 
			state.moveMessage = "Piece must move diagonally two steps";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> moveLeftOrRightOrUpOneStep = (state, from, to) -> {
		boolean result = from.moveLeftOrRightOrUpOneStep(to, state.onMove);
		if (!result) 
			state.moveMessage = "Piece must move left or righ one step";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isInPalace = (state, from, to) -> {
		boolean result = from.isInPalace(to, state.onMove);
		if (!result) 
			state.moveMessage = "Red General must stay in the palace";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isNotCrossingRiver = (state, from, to) -> {
		boolean result = from.isNotCrossingRiver(to, state.onMove);
		if (!result) 
			state.moveMessage = "Red Elephant must not cross river";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isValidCannonMove = (state, from, to) -> {		
		XiangqiPiece destinationPiece = state.board.getPieceAt(to);
		
		// normal move
		if (destinationPiece.getPieceType() == NONE) {
			return isMoveOrthogonal.apply(state, from, to);
		} else { // capture move
			return (from.isOrthogonal(to)) &&
					(destinationPiece.getPieceType() != NONE) &&
					(destinationPiece.getColor() != state.onMove) &&
					(countBlockingPiece(state, from, to) == 1);
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
