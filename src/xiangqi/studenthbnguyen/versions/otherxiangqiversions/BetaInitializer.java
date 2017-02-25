/**
 * 
 */
package xiangqi.studenthbnguyen.versions.otherxiangqiversions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import xiangqi.common.XiangqiGameVersion;
import static xiangqi.common.XiangqiPieceType.*;
import static xiangqi.common.XiangqiColor.*;

import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;

import static xiangqi.studenthbnguyen.common.XiangqiPieceImpl.*;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validators.AddRuleValidators;
import xiangqi.studenthbnguyen.validators.GameTerminationValidators;
import xiangqi.studenthbnguyen.validators.MoveValidator;
import xiangqi.studenthbnguyen.validators.MoveValidators;
import xiangqi.studenthbnguyen.validators.PieceValidators;

/**
 * @author huyennguyen
 *
 */
public class BetaInitializer extends InitializerTemplate {

	// Change these 3 variables to change the size of the board
	private static int maxRank = 5;
	private static int maxFile = 5;

	// Change this variable to change the maximum number of moves allowed
	private static int maxMove = 10;

	private static final XiangqiPieceImpl RED_CHARIOT1 = (XiangqiPieceImpl) makePiece(CHARIOT, RED, 1);
	private static final XiangqiPieceImpl RED_ADVISOR1 = (XiangqiPieceImpl) makePiece(ADVISOR, RED, 1);
	private static final XiangqiPieceImpl RED_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, RED, 1);
	private static final XiangqiPieceImpl RED_ADVISOR2 = (XiangqiPieceImpl) makePiece(ADVISOR, RED, 2);
	private static final XiangqiPieceImpl RED_CHARIOT2 = (XiangqiPieceImpl) makePiece(CHARIOT, RED, 2);
	private static final XiangqiPieceImpl RED_SOLDIER = (XiangqiPieceImpl) makePiece(SOLDIER, RED, 1);

	private static final XiangqiPieceImpl BLACK_CHARIOT1 = (XiangqiPieceImpl) makePiece(CHARIOT, BLACK, 1);
	private static final XiangqiPieceImpl BLACK_ADVISOR1 = (XiangqiPieceImpl) makePiece(ADVISOR, BLACK, 1);
	private static final XiangqiPieceImpl BLACK_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, BLACK, 1);
	private static final XiangqiPieceImpl BLACK_ADVISOR2 = (XiangqiPieceImpl) makePiece(ADVISOR, BLACK, 2);
	private static final XiangqiPieceImpl BLACK_CHARIOT2 = (XiangqiPieceImpl) makePiece(CHARIOT, BLACK, 2);
	private static final XiangqiPieceImpl BLACK_SOLDIER = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, 1);

	/**
	 * Default constructor
	 */
	public BetaInitializer() {
		super();
	}



	/**
	 * 
	 */
	@Override
	protected void addAddRuleValidators() {
		addRuleValidators.add(AddRuleValidators.addRuleToSoldier);
	}

	/**
	 * 
	 */
	@Override
	protected void addGameTerminationValidators() {
		gameTerminationValidators.add(GameTerminationValidators.gameNotInStalemate);
		gameTerminationValidators.add(GameTerminationValidators.gameNotInCheckmate);
	} 

	/**
	 * 
	 */
	@Override
	protected void addPieceValidators() {
		// general
		List<MoveValidator> generalValidators = new LinkedList<MoveValidator>();
		generalValidators.add(PieceValidators.isDistanceOneAndOrthogonal);
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

		// soldier
		List<MoveValidator> soldierValidators = new LinkedList<MoveValidator>();
		soldierValidators.add(PieceValidators.isForwardOneStep);
		pieceValidators.put(RED_SOLDIER, soldierValidators);
		pieceValidators.put(BLACK_SOLDIER, soldierValidators);
	}

	/**
	 * 
	 */
	@Override
	protected void addMoveValidators() {
		moveValidators.add(MoveValidators.isDestinationValid);
		moveValidators.add(MoveValidators.isCorrectColor);
		moveValidators.add(MoveValidators.hasNoBlockingPiece);
		moveValidators.add(MoveValidators.generalNotInCheck);
	}

	/**
	 * @return
	 */
	@Override
	protected XiangqiState initializeState() {
		XiangqiState state = new XiangqiState();
		state.version = XiangqiGameVersion.BETA_XQ;
		state.board = new XiangqiBoard(maxRank, maxFile);
		state.maxMove = maxMove;
		XNC.setRanks(maxRank);
		XNC.setFiles(maxFile);
		state.board = makeBoard();
		return state;
	}

	private XiangqiBoard makeBoard() {
		XiangqiBoard board = new XiangqiBoard(5, 5);
		board.placePiece(RED_CHARIOT1, XNC.makeXNC(1, 1));
		board.placePiece(RED_ADVISOR1, XNC.makeXNC(1, 2));		
		board.placePiece(RED_GENERAL, XNC.makeXNC(1, 3));
		board.placePiece(RED_ADVISOR2, XNC.makeXNC(1, 4));
		board.placePiece(RED_CHARIOT2, XNC.makeXNC(1, 5));
		board.placePiece(RED_SOLDIER, XNC.makeXNC(2, 3));

		board.placePiece(BLACK_CHARIOT1, XNC.makeXNC(1, 1));
		board.placePiece(BLACK_ADVISOR1, XNC.makeXNC(1, 2));		
		board.placePiece(BLACK_GENERAL, XNC.makeXNC(1, 3));
		board.placePiece(BLACK_ADVISOR2, XNC.makeXNC(1, 4));
		board.placePiece(BLACK_CHARIOT2, XNC.makeXNC(1, 5));
		board.placePiece(BLACK_SOLDIER, XNC.makeXNC(2, 3));

		return board;
	}
}
