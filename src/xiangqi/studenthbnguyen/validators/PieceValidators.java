/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import java.util.function.BiFunction;

import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiState;
import static xiangqi.common.XiangqiPieceType.*;

/**
 * @author huyennguyen
 *
 */
public class PieceValidators {
	/**
	 * 
	 */
	private static BiFunction<XNC, XNC, Boolean> sourceIsCloserToOrigin = (source, destination) -> {
		double sourceDistanceToOrigin = Math.pow(source.getRank(), 2) + Math.pow(source.getFile(), 2); 
		double destinationDistanceToOrigin = Math.pow(destination.getRank(), 2) + Math.pow(destination.getFile(), 2); 
		return (sourceDistanceToOrigin < destinationDistanceToOrigin);
	};
	
	/**
	 * Determine whether there's a blocking piece from the source to the destination
	 * @param state the state of the game
	 * @param from the source
	 * @param to the destination
	 * @param rankIncrement how much to increment the rank
	 * @param fileIncrement how much to increment the file
	 * @return true if there's a blocking piece, false otherwise
	 */
	private static boolean hasNoBlockingPiece(XiangqiState state, XNC from, XNC to, int rankIncrement, int fileIncrement) {
		XNC coordinate = XNC.makeXNC(from.getRank() + rankIncrement, from.getFile() + fileIncrement);
		while (coordinate.getRank() != to.getRank() || coordinate.getFile() != to.getFile() ) {
			if (state.board.getPieceAt(coordinate).getPieceType() != NONE)
				return false;
			coordinate = XNC.makeXNC(coordinate.getRank() + rankIncrement, coordinate.getFile() + fileIncrement);
		}
		return true;
	}
	
	// Chariot validators
	public static MoveValidator<XiangqiState, XNC, Boolean> isMoveOrthogonal = (state, from, to) -> {
		boolean result = from.isOrthogonal(to);
		if (!result) {
			state.moveMessage = "Piece must move orthogonally";
			return false;
		} else { // make sure that there's no blocking piece
			if (sourceIsCloserToOrigin.apply(from, to)) {
				return (from.getRank() == to.getRank()) ? 
						hasNoBlockingPiece(state, from, to, 0, 1) : 
							hasNoBlockingPiece(state, from, to, 1, 0);
					
			} else {
				boolean a = (from.getRank() == to.getRank()) ? 
						hasNoBlockingPiece(state, to, from, 0, 1) : 
							hasNoBlockingPiece(state, to, from, 1, 0);
				return (from.getRank() == to.getRank()) ? 
						hasNoBlockingPiece(state, to, from, 0, 1) : 
							hasNoBlockingPiece(state, to, from, 1, 0);
			}
					
		}
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
