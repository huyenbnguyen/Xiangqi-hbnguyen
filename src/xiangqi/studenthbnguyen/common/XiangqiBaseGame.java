/**
 * 
 */
package xiangqi.studenthbnguyen.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static xiangqi.common.MoveResult.*;
import static xiangqi.common.XiangqiColor.*;
import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenthbnguyen.validators.MoveValidator;


/**
 * @author huyennguyen
 *
 */
public class XiangqiBaseGame implements XiangqiGame {
	private XiangqiState state;
	private List<MoveValidator> moveValidators;
	private Map<XiangqiPieceType, List<MoveValidator>> pieceValidators;

	/**
	 * @param state
	 */
	public XiangqiBaseGame(XiangqiState state) {
		this.state = state;
		moveValidators = new LinkedList<MoveValidator>();
	}



	/**
	 * Setter for moveValidators
	 * @param moveValidators a list of move validators
	 */
	public void setMoveValidators(List<MoveValidator> moveValidators) {
		this.moveValidators = moveValidators;
	}

	/**
	 * Setter for pieceValidators
	 * @param pieceValidators a list of piece validators
	 */
	public void setPieceValidators(Map<XiangqiPieceType, List<MoveValidator>> pieceValidators) {
		this.pieceValidators = pieceValidators;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#makeMove(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public MoveResult makeMove(XiangqiCoordinate source, XiangqiCoordinate destination) {
		MoveResult moveResult = OK;
		state.moveMessage = "";
		XNC sourceNormalized = XNC.makeXNC(source, state.onMove);
		XNC destinationNormalized = XNC.makeXNC(destination, state.onMove);
		
		// check valid coordinates 
		if (!checkBounds(source) || !checkBounds(destination)) {
			state.moveMessage = "Invalid coordinates given";
			return ILLEGAL;
		}
		
		// Validate the move according the rules of the individual piece
		moveResult = validatePieceRules(sourceNormalized, destinationNormalized);
		if (moveResult == ILLEGAL) return ILLEGAL;

		// Validate the move according the rules of the game
		moveResult = validateGameRules(sourceNormalized, destinationNormalized);
		if (moveResult == ILLEGAL) return ILLEGAL;
		
		// if the move is valid, make the move
		state.board.movePiece(sourceNormalized, destinationNormalized);

		// check the state of the game after the move
		if (moveResult == OK) switchTurn();
		return moveResult;
	}



	/**
	 * @param sourceNormalized
	 * @param destinationNormalized
	 */
	private MoveResult validatePieceRules(XNC sourceNormalized, XNC destinationNormalized) {
		XiangqiPieceType pieceType = state.board.getPieceAt(sourceNormalized).getPieceType();
		for (MoveValidator<XiangqiState, XNC, Boolean> mv : pieceValidators.get(pieceType)) {
			if (!mv.apply(state, sourceNormalized, destinationNormalized)) return ILLEGAL;
		}
		return OK;
	}



	/**
	 * @param sourceNormalized
	 * @param destinationNormalized
	 */
	private MoveResult validateGameRules(XNC sourceNormalized, XNC destinationNormalized) {
		for (MoveValidator<XiangqiState, XNC, Boolean> mv : moveValidators) {
			if (!mv.apply(state, sourceNormalized, destinationNormalized)) return ILLEGAL;
		}
		return OK;
	}



	/**
	 * Switch move to the opponent
	 */
	private void switchTurn() {
		state.onMove = (state.onMove == RED) ? BLACK : RED; 
	}



	/**
	 * @param source
	 * @return
	 */
	private boolean checkBounds(XiangqiCoordinate coordinate) {
		final int rank = coordinate.getRank();
		final int file = coordinate.getFile();
		final XiangqiBoard board = state.board;
		return !(rank < 1 || rank > board.ranks || file < 1 || file > board.files);
	}



	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getMoveMessage()
	 */
	@Override
	public String getMoveMessage() {
		return state.moveMessage;
	}



	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getPieceAt(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiColor)
	 */
	@Override
	public XiangqiPiece getPieceAt(XiangqiCoordinate where, XiangqiColor aspect) {
		if (!checkBounds(where)) {
			throw new XiangqiRuntimeException("Invalid coordinate passed to getPieceAt()");
		}
		return state.board.getPieceAt(XNC.makeXNC(where, aspect));
	}
}
