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
public class GammaInitializer extends InitializerTemplate {
	// Change these 3 variables to change the size of the board
	private static int maxRank = 10;
	private static int maxFile = 9;
	
	// Change this variable to change the maximum number of moves allowed
	private static int maxMove = 25;

	private static final XiangqiPieceImpl RED_CHARIOT1 = (XiangqiPieceImpl) makePiece(CHARIOT, RED, 1);
	private static final XiangqiPieceImpl RED_ELEPHANT1 = (XiangqiPieceImpl) makePiece(ELEPHANT, RED, 1);
	private static final XiangqiPieceImpl RED_ADVISOR1 = (XiangqiPieceImpl) makePiece(ADVISOR, RED, 1);
	private static final XiangqiPieceImpl RED_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, RED, 1);
	private static final XiangqiPieceImpl RED_ADVISOR2 = (XiangqiPieceImpl) makePiece(ADVISOR, RED, 2);
	private static final XiangqiPieceImpl RED_ELEPHANT2 = (XiangqiPieceImpl) makePiece(ELEPHANT, RED, 2);
	private static final XiangqiPieceImpl RED_CHARIOT2 = (XiangqiPieceImpl) makePiece(CHARIOT, RED, 2);
	private static final XiangqiPieceImpl RED_SOLDIER1 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, 1);
	private static final XiangqiPieceImpl RED_SOLDIER2 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, 2);
	private static final XiangqiPieceImpl RED_SOLDIER3 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, 3);
	private static final XiangqiPieceImpl RED_SOLDIER4 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, 4);
	private static final XiangqiPieceImpl RED_SOLDIER5 = (XiangqiPieceImpl) makePiece(SOLDIER, RED, 5);

	private static final XiangqiPieceImpl BLACK_CHARIOT1 = (XiangqiPieceImpl) makePiece(CHARIOT, BLACK, 1);
	private static final XiangqiPieceImpl BLACK_ELEPHANT1 = (XiangqiPieceImpl) makePiece(ELEPHANT, BLACK, 1);
	private static final XiangqiPieceImpl BLACK_ADVISOR1 = (XiangqiPieceImpl) makePiece(ADVISOR, BLACK, 1);
	private static final XiangqiPieceImpl BLACK_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, BLACK, 1);
	private static final XiangqiPieceImpl BLACK_ADVISOR2 = (XiangqiPieceImpl) makePiece(ADVISOR, BLACK, 2);
	private static final XiangqiPieceImpl BLACK_ELEPHANT2 = (XiangqiPieceImpl) makePiece(ELEPHANT, BLACK, 2);
	private static final XiangqiPieceImpl BLACK_CHARIOT2 = (XiangqiPieceImpl) makePiece(CHARIOT, BLACK, 2);
	private static final XiangqiPieceImpl BLACK_SOLDIER1 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, 1);
	private static final XiangqiPieceImpl BLACK_SOLDIER2 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, 2);
	private static final XiangqiPieceImpl BLACK_SOLDIER3 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, 3);
	private static final XiangqiPieceImpl BLACK_SOLDIER4 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, 4);
	private static final XiangqiPieceImpl BLACK_SOLDIER5 = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK, 5);

	public GammaInitializer() {
		super();
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
		// generals
		List<MoveValidator> generalValidators = new LinkedList<MoveValidator>();
		generalValidators.add(PieceValidators.isInPalace);
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

		// soldiers
		List<MoveValidator> soldierValidators = new LinkedList<MoveValidator>();
		soldierValidators.add(PieceValidators.isForwardOneStep);
		pieceValidators.put(RED_SOLDIER1, soldierValidators);
		pieceValidators.put(RED_SOLDIER2, soldierValidators);
		pieceValidators.put(RED_SOLDIER3, soldierValidators);
		pieceValidators.put(RED_SOLDIER4, soldierValidators);
		pieceValidators.put(RED_SOLDIER5, soldierValidators);
		pieceValidators.put(BLACK_SOLDIER1, soldierValidators);
		pieceValidators.put(BLACK_SOLDIER2, soldierValidators);
		pieceValidators.put(BLACK_SOLDIER3, soldierValidators);
		pieceValidators.put(BLACK_SOLDIER4, soldierValidators);
		pieceValidators.put(BLACK_SOLDIER5, soldierValidators);

		// elephants
		List<MoveValidator> elephantValidators = new LinkedList<MoveValidator>();
		elephantValidators.add(PieceValidators.moveDiagonallyTwoSteps);
		elephantValidators.add(PieceValidators.isNotCrossingRiver);
		pieceValidators.put(RED_ELEPHANT1, elephantValidators);
		pieceValidators.put(RED_ELEPHANT2, elephantValidators);
		pieceValidators.put(BLACK_ELEPHANT1, elephantValidators);
		pieceValidators.put(BLACK_ELEPHANT2, elephantValidators);
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

	@Override
	protected void addAddRuleValidators() {
		addRuleValidators.add(AddRuleValidators.addRuleToSoldier);
	}

	/**
	 * @return
	 */
	@Override
	protected XiangqiState initializeState() {
		XiangqiState state = new XiangqiState();
		state.version = XiangqiGameVersion.GAMMA_XQ;
		state.board = new XiangqiBoard(maxRank, maxFile);
		state.maxMove = maxMove;
		XNC.setRanks(maxRank);
		XNC.setFiles(maxFile);
		state.board = makeBoard();
		return state;
	}

	

	private XiangqiBoard makeBoard() {
		XiangqiBoard board = new XiangqiBoard(maxRank, maxFile);
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
