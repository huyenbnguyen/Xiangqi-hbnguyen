/**
 * 
 */
package xiangqi.studenthbnguyen.versions.otherxiangqiversions;

import static xiangqi.common.XiangqiColor.BLACK;
import static xiangqi.common.XiangqiColor.RED;
import static xiangqi.common.XiangqiPieceType.*;
import static xiangqi.studenthbnguyen.common.XiangqiPieceImpl.makePiece;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import xiangqi.common.XiangqiGameVersion;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validators.GameTerminationValidators;
import xiangqi.studenthbnguyen.validators.MoveValidator;
import xiangqi.studenthbnguyen.validators.MoveValidators;
import xiangqi.studenthbnguyen.validators.PieceValidators;

/**
 * @author huyennguyen
 *
 */
public class GammaInitializer {
	private XiangqiState state;
	private List<MoveValidator> moveValidators;
	private Map<XiangqiPieceType, List<MoveValidator>> pieceValidators;
	private List<Predicate> gameTerminationValidators;

	public GammaInitializer() {
		moveValidators = new LinkedList<MoveValidator>();
		pieceValidators = new HashMap<XiangqiPieceType, List<MoveValidator>>();
		gameTerminationValidators = new LinkedList<Predicate>();
		state = initializeState();
		addMoveValidators();
		addPiecevalidators();
		addGameTerminationValidators();
	}
	
	/**
	 * 
	 */
	private void addGameTerminationValidators() {
		gameTerminationValidators.add(GameTerminationValidators.gameNotInStalemate);
		gameTerminationValidators.add(GameTerminationValidators.gameNotInCheckmate);
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
		
		// elephant
		List<MoveValidator> elephantValidators = new LinkedList<MoveValidator>();
		elephantValidators.add(PieceValidators.moveDiagonallyTwoSteps);
		pieceValidators.put(ELEPHANT, elephantValidators);
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
		state.board = new XiangqiBoard(10, 9);
		state.maxMove = 25;
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
	
	/**
	 * @return
	 */
	public List<Predicate> getGameTerminationValidators() {
		return gameTerminationValidators;
	}

	private XiangqiBoard makeBoard() {
		XiangqiBoard board = new XiangqiBoard(10, 9);
		board.placePiece(makePiece(CHARIOT, RED, XNC.makeXNC(1, 1)), XNC.makeXNC(1, 1));
		board.placePiece(makePiece(ELEPHANT, RED, XNC.makeXNC(1, 3)), XNC.makeXNC(1, 3));
		board.placePiece(makePiece(ADVISOR, RED, XNC.makeXNC(1, 4)), XNC.makeXNC(1, 4));		
		board.placePiece(makePiece(GENERAL, RED, XNC.makeXNC(1, 5)), XNC.makeXNC(1, 5));
		board.placePiece(makePiece(ADVISOR, RED, XNC.makeXNC(1, 6)), XNC.makeXNC(1, 6));
		board.placePiece(makePiece(ELEPHANT, RED, XNC.makeXNC(1, 7)), XNC.makeXNC(1, 7));
		board.placePiece(makePiece(CHARIOT, RED, XNC.makeXNC(1, 9)), XNC.makeXNC(1, 9));
		board.placePiece(makePiece(SOLDIER, RED, XNC.makeXNC(4, 1)), XNC.makeXNC(4, 1));
		board.placePiece(makePiece(SOLDIER, RED, XNC.makeXNC(4, 3)), XNC.makeXNC(4, 3));
		board.placePiece(makePiece(SOLDIER, RED, XNC.makeXNC(4, 5)), XNC.makeXNC(4, 5));
		board.placePiece(makePiece(SOLDIER, RED, XNC.makeXNC(4, 7)), XNC.makeXNC(4, 7));
		board.placePiece(makePiece(SOLDIER, RED, XNC.makeXNC(4, 9)), XNC.makeXNC(4, 9));

		board.placePiece(makePiece(CHARIOT, BLACK, XNC.makeXNC(1, 1)), XNC.makeXNC(1, 1));
		board.placePiece(makePiece(ELEPHANT, BLACK, XNC.makeXNC(1, 3)), XNC.makeXNC(1, 3));
		board.placePiece(makePiece(ADVISOR, BLACK, XNC.makeXNC(1, 4)), XNC.makeXNC(1, 4));		
		board.placePiece(makePiece(GENERAL, BLACK, XNC.makeXNC(1, 5)), XNC.makeXNC(1, 5));
		board.placePiece(makePiece(ADVISOR, BLACK, XNC.makeXNC(1, 6)), XNC.makeXNC(1, 6));
		board.placePiece(makePiece(ELEPHANT, BLACK, XNC.makeXNC(1, 7)), XNC.makeXNC(1, 7));
		board.placePiece(makePiece(CHARIOT, BLACK, XNC.makeXNC(1, 9)), XNC.makeXNC(1, 9));
		board.placePiece(makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 1)), XNC.makeXNC(4, 1));
		board.placePiece(makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 3)), XNC.makeXNC(4, 3));
		board.placePiece(makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 5)), XNC.makeXNC(4, 5));
		board.placePiece(makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 7)), XNC.makeXNC(4, 7));
		board.placePiece(makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 9)), XNC.makeXNC(4, 9));

		return board;
	}
}
