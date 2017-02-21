/**
 * 
 */
package xiangqi.studenthbnguyen.versions.otherxiangqiversions;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import xiangqi.common.XiangqiGameVersion;
import static xiangqi.common.XiangqiPieceType.*;
import static xiangqi.common.XiangqiColor.*;

import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;

import static xiangqi.studenthbnguyen.common.XiangqiPieceImpl.*;
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
	private Map<XiangqiPieceImpl, List<MoveValidator>> pieceValidators;
	private List<Predicate> gameTerminationValidators;
	
	private static final XiangqiPieceImpl RED_CHARIOT1 = (XiangqiPieceImpl) makePiece(CHARIOT, RED, XNC.makeXNC(1, 1));
	private static final XiangqiPieceImpl RED_ELEPHANT1 = (XiangqiPieceImpl) makePiece(ELEPHANT, RED, XNC.makeXNC(1, 3));
	private static final XiangqiPieceImpl RED_ADVISOR1 = (XiangqiPieceImpl) makePiece(ADVISOR, RED, XNC.makeXNC(1, 4));
	private static final XiangqiPieceImpl RED_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, RED, XNC.makeXNC(1, 5));
	private static final XiangqiPieceImpl RED_ADVISOR2 = (XiangqiPieceImpl) makePiece(ADVISOR, RED, XNC.makeXNC(1, 6));
	private static final XiangqiPieceImpl RED_ELEPHANT2 = (XiangqiPieceImpl) makePiece(ELEPHANT, RED, XNC.makeXNC(1, 7));
	private static final XiangqiPieceImpl RED_CHARIOT2 = (XiangqiPieceImpl) makePiece(CHARIOT, RED, XNC.makeXNC(1, 9));
	private static final XiangqiPieceImpl RED_SOLDIER1 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, XNC.makeXNC(4, 1));
	private static final XiangqiPieceImpl RED_SOLDIER2 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, XNC.makeXNC(4, 3));
	private static final XiangqiPieceImpl RED_SOLDIER3 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, XNC.makeXNC(4, 5));
	private static final XiangqiPieceImpl RED_SOLDIER4 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, XNC.makeXNC(4, 7));
	private static final XiangqiPieceImpl RED_SOLDIER5 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, XNC.makeXNC(4, 9));
	
	private static final XiangqiPieceImpl BLACK_CHARIOT1 = (XiangqiPieceImpl) makePiece(CHARIOT, BLACK, XNC.makeXNC(1, 1));
	private static final XiangqiPieceImpl BLACK_ELEPHANT1 = (XiangqiPieceImpl) makePiece(ELEPHANT, BLACK, XNC.makeXNC(1, 3));
	private static final XiangqiPieceImpl BLACK_ADVISOR1 = (XiangqiPieceImpl) makePiece(ADVISOR, BLACK, XNC.makeXNC(1, 4));
	private static final XiangqiPieceImpl BLACK_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, BLACK, XNC.makeXNC(1, 5));
	private static final XiangqiPieceImpl BLACK_ADVISOR2 = (XiangqiPieceImpl) makePiece(ADVISOR, BLACK, XNC.makeXNC(1, 6));
	private static final XiangqiPieceImpl BLACK_ELEPHANT2 = (XiangqiPieceImpl) makePiece(ELEPHANT, BLACK, XNC.makeXNC(1, 7));
	private static final XiangqiPieceImpl BLACK_CHARIOT2 = (XiangqiPieceImpl) makePiece(CHARIOT, BLACK, XNC.makeXNC(1, 9));
	private static final XiangqiPieceImpl BLACK_SOLDIER1 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 1));
	private static final XiangqiPieceImpl BLACK_SOLDIER2 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 3));
	private static final XiangqiPieceImpl BLACK_SOLDIER3 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 5));
	private static final XiangqiPieceImpl BLACK_SOLDIER4 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 7));
	private static final XiangqiPieceImpl BLACK_SOLDIER5 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, XNC.makeXNC(4, 9));

	public GammaInitializer() {
		moveValidators = new LinkedList<MoveValidator>();
		pieceValidators = new HashMap<XiangqiPieceImpl, List<MoveValidator>>();
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
		pieceValidators.put(RED_GENERAL, generalValidators);
		pieceValidators.put(BLACK_GENERAL, generalValidators);

		// chariot
		List<MoveValidator> chariotValidators = new LinkedList<MoveValidator>();
		chariotValidators.add(PieceValidators.isMoveOrthogonal);
		pieceValidators.put(RED_CHARIOT1, chariotValidators);
		pieceValidators.put(RED_CHARIOT2, chariotValidators);
		pieceValidators.put(BLACK_CHARIOT1, chariotValidators);
		pieceValidators.put(BLACK_CHARIOT2, chariotValidators);

		// advisor
		List<MoveValidator> advisorValidators = new LinkedList<MoveValidator>();
		advisorValidators.add(PieceValidators.isMoveDiagonal);
		pieceValidators.put(RED_ADVISOR1, advisorValidators);
		pieceValidators.put(RED_ADVISOR2, advisorValidators);
		pieceValidators.put(BLACK_ADVISOR1, advisorValidators);
		pieceValidators.put(BLACK_ADVISOR2, advisorValidators);

		// red soldiers
		List<MoveValidator> redSoldierValidators = new LinkedList<MoveValidator>();
		redSoldierValidators.add(PieceValidators.isForwardOneStepRed);
		pieceValidators.put(RED_SOLDIER1, redSoldierValidators);
		pieceValidators.put(RED_SOLDIER2, redSoldierValidators);
		pieceValidators.put(RED_SOLDIER3, redSoldierValidators);
		pieceValidators.put(RED_SOLDIER4, redSoldierValidators);
		pieceValidators.put(RED_SOLDIER5, redSoldierValidators);
		
		// black soldiers
		List<MoveValidator> blackSoldierValidators = new LinkedList<MoveValidator>();
		blackSoldierValidators.add(PieceValidators.isForwardOneStepBlack);
		pieceValidators.put(BLACK_SOLDIER1, blackSoldierValidators);
		pieceValidators.put(BLACK_SOLDIER2, blackSoldierValidators);
		pieceValidators.put(BLACK_SOLDIER3, blackSoldierValidators);
		pieceValidators.put(BLACK_SOLDIER4, blackSoldierValidators);
		pieceValidators.put(BLACK_SOLDIER5, blackSoldierValidators);
		
		// elephant
		List<MoveValidator> elephantValidators = new LinkedList<MoveValidator>();
		elephantValidators.add(PieceValidators.moveDiagonallyTwoSteps);
		pieceValidators.put(RED_ELEPHANT1, elephantValidators);
		pieceValidators.put(RED_ELEPHANT2, elephantValidators);
		pieceValidators.put(BLACK_ELEPHANT1, elephantValidators);
		pieceValidators.put(BLACK_ELEPHANT2, elephantValidators);
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
	public Map<XiangqiPieceImpl, List<MoveValidator>> getPieceValidators() {
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
		board.placePiece(RED_CHARIOT1, XNC.makeXNC(1, 1));
		board.placePiece(RED_ELEPHANT1, XNC.makeXNC(1, 3));
		board.placePiece(RED_ADVISOR1, XNC.makeXNC(1, 4));		
		board.placePiece(RED_GENERAL, XNC.makeXNC(1, 5));
		board.placePiece(RED_ADVISOR2, XNC.makeXNC(1, 6));
		board.placePiece(RED_ELEPHANT2, XNC.makeXNC(1, 7));
		board.placePiece(RED_CHARIOT2, XNC.makeXNC(1, 9));
		board.placePiece(RED_SOLDIER1, XNC.makeXNC(4, 1));
		board.placePiece(RED_SOLDIER2, XNC.makeXNC(4, 3));
		board.placePiece(RED_SOLDIER3, XNC.makeXNC(4, 5));
		board.placePiece(RED_SOLDIER4, XNC.makeXNC(4, 7));
		board.placePiece(RED_SOLDIER5, XNC.makeXNC(4, 9));

		board.placePiece(BLACK_CHARIOT1, XNC.makeXNC(1, 1));
		board.placePiece(BLACK_ELEPHANT1, XNC.makeXNC(1, 3));
		board.placePiece(BLACK_ADVISOR1, XNC.makeXNC(1, 4));		
		board.placePiece(BLACK_GENERAL, XNC.makeXNC(1, 5));
		board.placePiece(BLACK_ADVISOR2, XNC.makeXNC(1, 6));
		board.placePiece(BLACK_ELEPHANT2, XNC.makeXNC(1, 7));
		board.placePiece(BLACK_CHARIOT2, XNC.makeXNC(1, 9));
		board.placePiece(BLACK_SOLDIER1, XNC.makeXNC(4, 1));
		board.placePiece(BLACK_SOLDIER2, XNC.makeXNC(4, 3));
		board.placePiece(BLACK_SOLDIER3, XNC.makeXNC(4, 5));
		board.placePiece(BLACK_SOLDIER4, XNC.makeXNC(4, 7));
		board.placePiece(BLACK_SOLDIER5, XNC.makeXNC(4, 9));

		return board;
	}
}
