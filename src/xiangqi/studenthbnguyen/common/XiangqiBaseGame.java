/**
 * 
 */
package xiangqi.studenthbnguyen.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static xiangqi.common.MoveResult.*;
import static xiangqi.common.XiangqiColor.*;

import xiangqi.XiangqiGameFactory;
import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenthbnguyen.validators.MoveValidator;
import xiangqi.studenthbnguyen.versions.otherxiangqiversions.BetaInitializer;


/**
 * @author huyennguyen
 *
 */
public class XiangqiBaseGame implements XiangqiGame {
	private XiangqiState state;
	private List<MoveValidator> moveValidators;
	private List<Predicate> gameTerminationValidators;
	private Map<XiangqiPieceType, List<MoveValidator>> pieceValidators;

	/**
	 * @param state
	 */
	public XiangqiBaseGame(XiangqiState state) {
		this.state = state;
		moveValidators = new LinkedList<MoveValidator>();
	}

	/**
	 * 
	 * @return
	 */
	public XiangqiState getState() {
		return state;
	}

	/**
	 * 
	 * @return
	 */
	public void setState(XiangqiState state) {
		this.state = state;
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

	/**
	 * Setter for moveValidators
	 * @param moveValidators a list of move validators
	 */
	public void setGameTerminationValidators(List<Predicate> gameTerminationValidators) {
		this.gameTerminationValidators = gameTerminationValidators;
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

		if (state.moveCount > state.maxMove * 2) {
			state.moveMessage = "Game ended. Result: A draw";
			return DRAW;
		}
		
		// check valid coordinates 
		if (!checkBounds(source) || !checkBounds(destination)) {
			state.moveMessage = "Invalid coordinates given";
			return ILLEGAL;
		} 
		
		// check the source is not empty
		if (state.board.getPieceAt(sourceNormalized).getPieceType() == XiangqiPieceType.NONE) {
			state.moveMessage = "The source is empty";
			return ILLEGAL;
		}

		// check the piece rules, and the game rules
		if (checkRules(sourceNormalized, destinationNormalized) == ILLEGAL) 
			return ILLEGAL;
			

		// if the move is valid, make the move
		state.board.movePiece(sourceNormalized, destinationNormalized);

		// check the state of the game after the move
		switchTurn();
		moveResult = validateTerminationRules();
		if (moveResult != OK) return moveResult;
		state.moveCount++;
		return OK; 
	}

	/**
	 * @param sourceNormalized
	 * @param destinationNormalized
	 */
	public MoveResult checkRules(XNC sourceNormalized, XNC destinationNormalized) {
		// Validate the move according the rules of the individual piece
		if (validatePieceRules(sourceNormalized, destinationNormalized) == ILLEGAL) {
			state.moveMessage = "Invalid move according to the piece rules";
			return ILLEGAL;
		}

		// Validate the move according the rules of the game
		if (validateGameRules(sourceNormalized, destinationNormalized) == ILLEGAL) { 
			state.moveMessage = "Invalid move according to the game rules";
			return ILLEGAL;
		}
		return OK;  
	}
	
	



	/**
	 * @param sourceNormalized
	 * @param destinationNormalized
	 */
	public MoveResult validatePieceRules(XNC sourceNormalized, XNC destinationNormalized) {
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
	 * @param sourceNormalized
	 * @param destinationNormalized
	 */
	private MoveResult validateTerminationRules() {
		for (Predicate<XiangqiState> tv : gameTerminationValidators) {
			if (!tv.test(state)) 
				return (state.onMove == RED) ? BLACK_WINS : RED_WINS;
		}
		return OK; 
	} 


	/**
	 * Switch move to the opponent
	 */
	public void switchTurn() {
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

	/**
	 * Make a deep copy of the game
	 * @param state the state of the game
	 * @return a deep copy of the game
	 */
	public static XiangqiBaseGame makeDeepCopy(XiangqiState state) {
		XiangqiState stateCopy = (XiangqiState) XiangqiState.makeDeepCopy(state);
		XiangqiBaseGame gameCopy = (XiangqiBaseGame) XiangqiGameFactory.makeXiangqiGame(state.version);
		gameCopy.setState(stateCopy);
		return gameCopy;
	}
}
