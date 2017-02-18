/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import java.util.function.BiFunction;

import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiState;

/**
 * @author huyennguyen
 *
 */
public class PieceValidators {

	
	// Chariot validators
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
	
}
