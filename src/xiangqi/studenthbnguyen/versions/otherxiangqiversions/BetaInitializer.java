/**
 * 
 */
package xiangqi.studenthbnguyen.versions.otherxiangqiversions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import xiangqi.common.XiangqiGameVersion;
import xiangqi.common.XiangqiPieceType;
import static xiangqi.common.XiangqiPieceType.*;
import static xiangqi.common.XiangqiColor.*;

import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import static xiangqi.studenthbnguyen.common.XiangqiPieceImpl.*;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validators.MoveValidator;
import xiangqi.studenthbnguyen.validators.MoveValidators;
import xiangqi.studenthbnguyen.validators.PieceValidators;

/**
 * @author huyennguyen
 *
 */
public class BetaInitializer {
	private XiangqiState state;
	private List<MoveValidator> moveValidators;
	private Map<XiangqiPieceType, List<MoveValidator>> pieceValidators;

	public BetaInitializer() {
		moveValidators = new LinkedList<MoveValidator>();
		pieceValidators = new HashMap<XiangqiPieceType, List<MoveValidator>>();
		state = initializeState();
		addMoveValidators();
		addPiecevalidators();
	}
	/**
	 * 
	 */
	private void addPiecevalidators() {
		// general
		List<MoveValidator> generalValidators = new LinkedList<MoveValidator>();
		generalValidators.add(PieceValidators.isDistanceOne);
		pieceValidators.put(GENERAL, generalValidators);

		// chariot
		List<MoveValidator> chariotValidators = new LinkedList<MoveValidator>();
		chariotValidators.add(PieceValidators.isMoveOrthogonal);
		pieceValidators.put(CHARIOT, chariotValidators);

		// advisor
		List<MoveValidator> advisorValidators = new LinkedList<MoveValidator>();
		advisorValidators.add(PieceValidators.isMoveDiagonal);
		pieceValidators.put(ADVISOR, advisorValidators);

		// soldier
		List<MoveValidator> soldierValidators = new LinkedList<MoveValidator>();
		soldierValidators.add(PieceValidators.isForwardOneStep);
		pieceValidators.put(SOLDIER, soldierValidators);
	}
	
	/**
	 * 
	 */
	private void addMoveValidators() {
		moveValidators.add(MoveValidators.isDestinationValid);
		moveValidators.add(MoveValidators.isCorrectColor);
		moveValidators.add(MoveValidators.hasNoBlockingPiece);
		moveValidators.add(MoveValidators.generalNotInCheck);
	}
	
	/**
	 * @return
	 */
	private XiangqiState initializeState() {
		XiangqiState state = new XiangqiState();
		state.version = XiangqiGameVersion.BETA_XQ;
		state.board = new XiangqiBoard(5, 5);
		XNC.setState(state);
		state.board = makeBoard();
		return state;
	}
	
	/**
	 * @return
	 */
	public XiangqiState getState() {
		return state;
	}

	/**
	 * @return
	 */
	public List<MoveValidator> getMoveValidators() {
		return moveValidators;
	}

	/**
	 * @return
	 */
	public Map<XiangqiPieceType, List<MoveValidator>> getPieceValidators() {
		return pieceValidators;
	}

	private XiangqiBoard makeBoard() {
		XiangqiBoard board = new XiangqiBoard(5, 5);
		board.placePiece(makePiece(CHARIOT, RED), XNC.makeXNC(1, 1));
		board.placePiece(makePiece(ADVISOR, RED), XNC.makeXNC(1, 2));		
		board.placePiece(makePiece(GENERAL, RED), XNC.makeXNC(1, 3));
		board.placePiece(makePiece(ADVISOR, RED), XNC.makeXNC(1, 4));
		board.placePiece(makePiece(CHARIOT, RED), XNC.makeXNC(1, 5));
		board.placePiece(makePiece(SOLDIER, RED), XNC.makeXNC(2, 3));

		board.placePiece(makePiece(CHARIOT, BLACK), XNC.makeXNC(1, 1));
		board.placePiece(makePiece(ADVISOR, BLACK), XNC.makeXNC(1, 2));		
		board.placePiece(makePiece(GENERAL, BLACK), XNC.makeXNC(1, 3));
		board.placePiece(makePiece(ADVISOR, BLACK), XNC.makeXNC(1, 4));
		board.placePiece(makePiece(CHARIOT, BLACK), XNC.makeXNC(1, 5));
		board.placePiece(makePiece(SOLDIER, BLACK), XNC.makeXNC(2, 3));

		return board;
	}
}
