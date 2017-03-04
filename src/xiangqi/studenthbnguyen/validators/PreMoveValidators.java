/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;

import static xiangqi.common.XiangqiColor.*;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

import static xiangqi.common.XiangqiPieceType.*;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBaseGame;
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.util.DeepCopyMaker;
import xiangqi.studenthbnguyen.validatorchecker.PieceChecker;

import static xiangqi.common.MoveResult.*;
import static xiangqi.common.XiangqiPieceType.*;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Validators BEFORE making a move.
 * These are driven by the rules of the game.
 * @author huyennguyen
 *
 */
public class PreMoveValidators {
	public static MoveValidator<XiangqiState, XNC, MoveResult> isValidSource = (state, from, to) -> {
		XiangqiPiece pieceSource = state.board.getPieceAt(from);
		boolean result = (pieceSource.getPieceType() != XiangqiPieceType.NONE && pieceSource.getColor() == state.onMove);
		if (!result) {
			state.moveMessage = "ILLEGAL: Attempt to move from an empty location or move the opponent's piece";
			return ILLEGAL;
		}
		return OK; 
	};

	public static MoveValidator<XiangqiState, XNC, MoveResult> isDestinationValid = (state, from, to) -> {
		XiangqiPiece piece = state.board.getPieceAt(to);
		boolean result = (piece.getPieceType() == XiangqiPieceType.NONE || piece.getColor() != state.onMove);
		if (!result) { 
			state.moveMessage = "ILLEGAL: Attempt to capture your own piece";
			return ILLEGAL;
		}
		return OK;
	};



	public static MoveValidator<XiangqiState, XNC, MoveResult> isGeneralInCheck = (state, from, to) -> {
		XiangqiState stateCopy = XiangqiState.makeDeepCopy(state);
		XiangqiBoard boardCopy = stateCopy.board;
		boardCopy.movePiece(from, to);
		stateCopy.onMove = (state.onMove == RED) ? BLACK : RED;
		
		XNC generalCoordinate = boardCopy.findPiece(GENERAL, state.onMove);
		for (Entry<XNC, XiangqiPieceImpl> entry : boardCopy.boardMap.entrySet()) {
			XiangqiPieceImpl piece = entry.getValue();
			if (piece.getColor() != state.onMove && 
					PieceChecker.runChecker(stateCopy, entry.getKey(), generalCoordinate) == OK) { 
				stateCopy.moveMessage = "ILLEGAL: Move puts the general in check";
				return ILLEGAL;
			}  
		}
		return OK;
	};

	public static MoveValidator<XiangqiState, XNC, MoveResult> gameRanOutOfMove = (state, from, to) -> {
		if (state.moveCount >= state.maxMove * 2) {
			state.moveMessage = "Game ended. Result: A draw";
			return DRAW;
		}
		return OK;	
	};


	public static MoveValidator<XiangqiState, XNC, MoveResult> isValidCoordinate = (state, from, to) -> {
		// check valid coordinates 
		if (!checkBounds(from, state) || !checkBounds(to, state)) {
			state.moveMessage = "ILLEGAL: Invalid coordinates given";
			return ILLEGAL;
		}
		return OK;
	};

	/**
	 * Check whether is given coordinate is within the board
	 * @param coordinate the coordinate
	 * @return true if the given coordinate is within the board, false otherwise
	 */
	public static boolean checkBounds(XiangqiCoordinate coordinate, XiangqiState state) {
		final int rank = coordinate.getRank();
		final int file = coordinate.getFile();
		final XiangqiBoard board = state.board;
		return !(rank < 1 || rank > board.ranks || file < 1 || file > board.files);
	}
}
