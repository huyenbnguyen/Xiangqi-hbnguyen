/**
 * 
 */
package xiangqi.studenthbnguyen.validators;
import static xiangqi.common.XiangqiPieceType.*;

import java.util.List;
import java.util.ListIterator;

import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiGameVersion;

import static xiangqi.common.MoveResult.*;
import static xiangqi.common.XiangqiGameVersion.*;
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

	public static MoveValidator<XiangqiState, XNC, MoveResult> isNotCrossingRiver = (state, from, to) -> {
		boolean result = from.isNotCrossingRiver(to, state.onMove);
		if (!result) {
			state.moveMessage = "Red Elephant must not cross river";
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

	public static MoveValidator<XiangqiState, XNC, MoveResult> soldierValidator = (state, from, to) -> {

		if (from.isNotCrossingRiver(to, state.onMove)) {
			return isForwardOneStep.apply(state, from, to);
		}

		boolean rightVersion = (state.version != ALPHA_XQ && state.version != BETA_XQ);
		boolean isSoldierPiece = (state.board.getPieceAt(from).getPieceType() == SOLDIER);
		boolean isValidMove = from.moveLeftOrRightOrUpOneStep(to, state.onMove);
		if (!rightVersion || !isSoldierPiece || !isValidMove) {
			state.moveMessage = "ILLEGAL";
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



	public static MoveValidator<XiangqiState, XNC, MoveResult> isValidCannonMove = (state, from, to) -> {		
		XiangqiPiece destinationPiece = state.board.getPieceAt(to);

		// normal move
		if (destinationPiece.getPieceType() == NONE) {
			return isMoveOrthogonal.apply(state, from, to);
		} else { // capture move
			if (!from.isOrthogonal(to) ||
					(destinationPiece.getColor() == state.onMove) ||
					(countBlockingPiece(state, from, to) != 1)) {
				state.moveMessage = "ILLEGAL: Cannon made an invalid move";
				return ILLEGAL;
			}
			return OK;
		}
	};

	private static MoveValidator<XiangqiState, XNC, MoveResult> isValidVerticalHorseMove = (state, from, to) -> {		
		XiangqiPiece intermediatePiece;
		if (from.getRank() == to.getRank() - 2) { // to is above from
			intermediatePiece = state.board.getPieceAt(XNC.makeXNC(from.getRank()+1, from.getFile()));
			if (intermediatePiece.getPieceType() != NONE) {
				state.moveMessage = "ILLEGAL: Horse is blocked";
				return ILLEGAL;
			}
			return OK;
		} else { // to is below from
			intermediatePiece = state.board.getPieceAt(XNC.makeXNC(from.getRank()-1, from.getFile()));
			if (intermediatePiece.getPieceType() != NONE) {
				state.moveMessage = "ILLEGAL: Horse is blocked";
				return ILLEGAL;
			}
			return OK;
		}
	};

	private static MoveValidator<XiangqiState, XNC, MoveResult> isValidHorizontalHorseMove = (state, from, to) -> {		
		XiangqiPiece intermediatePiece;
		if (from.getRank() == to.getFile() - 2) { // to is right of from
			intermediatePiece = state.board.getPieceAt(XNC.makeXNC(from.getRank(), from.getFile()+1));
			if (intermediatePiece.getPieceType() != NONE) {
				state.moveMessage = "ILLEGAL: Horse is blocked";
				return ILLEGAL;
			}
			return OK;
		} else { // to is left of from
			intermediatePiece = state.board.getPieceAt(XNC.makeXNC(from.getRank(), from.getFile()-1));
			if (intermediatePiece.getPieceType() != NONE) {
				state.moveMessage = "ILLEGAL: Horse is blocked";
				return ILLEGAL;
			}
			return OK;
		}
	};

	public static MoveValidator<XiangqiState, XNC, MoveResult> isValidHorseMove = (state, from, to) -> {		
		if (Math.abs(to.getRank() - from.getRank()) == 2) {
			return (isValidVerticalHorseMove.apply(state, from, to));
		}

		if (Math.abs(to.getFile() - from.getFile()) == 2)
			return (isValidHorizontalHorseMove.apply(state, from, to));
		return ILLEGAL;
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
