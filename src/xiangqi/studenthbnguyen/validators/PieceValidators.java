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

	public static MoveValidator<XiangqiState, XNC, Boolean> isDistanceOneAndOrthogonal = (state, from, to) -> {
		boolean result = from.isDistanceOneAndOrthogonal(to);
		if (!result) 
			state.moveMessage = "Piece must move forward one step";
		return result;
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isForwardOneStepRed = (state, from, to) -> {
		boolean result = from.isForwardOneStepRed(to);
		if (!result) 
			state.moveMessage = "Piece must move forward one step";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isForwardOneStepBlack = (state, from, to) -> {
		boolean result = from.isForwardOneStepBlack(to);
		if (!result) 
			state.moveMessage = "Piece must move forward one step";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> moveDiagonallyTwoSteps = (state, from, to) -> {
		boolean result = from.moveDiagonallyTwoSteps(to);
		if (!result) 
			state.moveMessage = "Piece must move diagonally two steps";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> moveLeftOrRightOrUpOneStepRed = (state, from, to) -> {
		boolean result = from.moveLeftOrRightOrUpOneStepRed(to);
		if (!result) 
			state.moveMessage = "Piece must move left or righ one step";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> moveLeftOrRightOrUpOneStepBlack = (state, from, to) -> {
		boolean result = from.moveLeftOrRightOrUpOneStepBlack(to);
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
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isNotCrossingRiverRed = (state, from, to) -> {
		boolean result = from.isNotCrossingRiverRed(to);
		if (!result) 
			state.moveMessage = "Red Elephant must not cross river";
		return result;
	};
	
	public static MoveValidator<XiangqiState, XNC, Boolean> isNotCrossingRiverBlack = (state, from, to) -> {
		boolean result = from.isNotCrossingRiverBlack(to);
		if (!result) 
			state.moveMessage = "Black Elephant must not cross river";
		return result;
	};
}
