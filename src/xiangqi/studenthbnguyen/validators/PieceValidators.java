/**
 * 
 */
package xiangqi.studenthbnguyen.validators;
import static xiangqi.common.XiangqiPieceType.NONE;

import java.util.List;
import java.util.ListIterator;

import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiState;

/**
 * Validators BEFORE making a move.
 * These are driven by the rules of the individual piece.
 * @author huyennguyen
 *
 */
public class PieceValidators {
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isMoveOrthogonal = (state, from, to) -> {
		boolean result = from.isOrthogonal(to);
		if (!result) 
			state.moveMessage = "Piece must move orthogonally";
		return result;
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isMoveDiagonal = (state, from, to) -> {
		boolean result = from.isDiagonalTo(to);
		if (!result) 
			state.moveMessage = "Piece must move diagonally";
		return result;
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isDistanceOne = (state, from, to) -> {
		boolean result = from.isDistanceOne(to);
		if (!result) 
			state.moveMessage = "Piece must move forward one step";
		return result;
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isForwardOneStep = (state, from, to) -> {
		boolean result = from.isForwardOneStep(to);
		if (!result) 
			state.moveMessage = "Piece must move forward one step";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> moveDiagonallyTwoSteps = (state, from, to) -> {
		boolean result = from.moveDiagonallyTwoSteps(to);
		if (!result) 
			state.moveMessage = "Piece must move forward one step";
		return result;
	};
}
