/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import java.util.function.BiFunction;

import java.awt.Point;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiState;
import static xiangqi.common.XiangqiPieceType.*;

/**
 * @author huyennguyen
 *
 */
public class PieceValidators {

	/**
	 * Determine whether there's a blocking piece from the source to the destination
	 * @param state the state of the game
	 * @param from the source
	 * @param to the destination
	 * @param rankIncrement how much to increment the rank
	 * @param fileIncrement how much to increment the file
	 * @return true if there's no blocking piece, false otherwise
	 */
	private static boolean hasNoBlockingPiece(XiangqiState state, XNC from, XNC to) {
		// calculate direction vector
		Point direction = new Point((int) Math.signum(to.getRank()-from.getRank()), 
                (int) Math.signum(to.getFile() - from.getFile()));
		XNC coordinate = XNC.makeXNC(from.getRank() + direction.x, from.getFile() + direction.y);
		while (!coordinate.equals(to)) {
			if (state.board.getPieceAt(coordinate).getPieceType() != NONE)
				return false;
			coordinate = XNC.makeXNC(coordinate.getRank() + direction.x, coordinate.getFile() + direction.y);
		}
		return true;
	}

	public static MoveValidator<XiangqiState, XNC, Boolean> isMoveOrthogonal = (state, from, to) -> {
		if (!from.isOrthogonal(to)) {
			state.moveMessage = "Piece must move orthogonally";
			return false;
		} else { // make sure that there's no blocking piece
			return hasNoBlockingPiece(state, from, to);			
		}
	};

	public static MoveValidator<XiangqiState, XNC, Boolean> isMoveDiagonal = (state, from, to) -> {
		if (!from.isDiagonalTo(to)) {
			state.moveMessage = "Piece must move orthogonally";
			return false;
		} else { // make sure that there's no blocking piece
			return hasNoBlockingPiece(state, from, to);	
		}
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
