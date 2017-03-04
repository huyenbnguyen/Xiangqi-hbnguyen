/**
 * 
 */
package xiangqi.studenthbnguyen.versions.otherxiangqiversions;

import static xiangqi.common.XiangqiColor.BLACK;
import static xiangqi.common.XiangqiColor.RED;
import static xiangqi.common.XiangqiGameVersion.DELTA_XQ;
import static xiangqi.common.XiangqiPieceType.*;
import static xiangqi.studenthbnguyen.common.XiangqiPieceImpl.makePiece;

import java.util.LinkedList;
import java.util.List;

import xiangqi.common.XiangqiGameVersion;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validators.PostMoveValidators;
import xiangqi.studenthbnguyen.validators.MoveValidator;
import xiangqi.studenthbnguyen.validators.PreMoveValidators;
import xiangqi.studenthbnguyen.validators.PieceValidators;

/**
 * @author huyennguyen
 *
 */
public class DeltaInitializer extends InitializerTemplate {

	// Change these 3 variables to change the size of the board
	private static int maxRank = 10;
	private static int maxFile = 9;

	// Change this variable to change the maximum number of moves allowed
	private static int maxMove = 10000;

	// Change this to change the version of the game 
	private static XiangqiGameVersion version = DELTA_XQ;

	private static final XiangqiPieceImpl RED_CHARIOT = (XiangqiPieceImpl) makePiece(CHARIOT, RED);
	private static final XiangqiPieceImpl RED_ELEPHANT = (XiangqiPieceImpl) makePiece(ELEPHANT, RED);
	private static final XiangqiPieceImpl RED_ADVISOR = (XiangqiPieceImpl) makePiece(ADVISOR, RED);
	private static final XiangqiPieceImpl RED_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, RED);
	private static final XiangqiPieceImpl RED_SOLDIER = (XiangqiPieceImpl) makePiece(SOLDIER, RED);
	private static final XiangqiPieceImpl RED_CANNON = (XiangqiPieceImpl) makePiece(CANNON, RED);
	private static final XiangqiPieceImpl RED_HORSE = (XiangqiPieceImpl) makePiece(HORSE, RED);

	private static final XiangqiPieceImpl BLACK_CHARIOT = (XiangqiPieceImpl) makePiece(CHARIOT, BLACK);
	private static final XiangqiPieceImpl BLACK_ELEPHANT = (XiangqiPieceImpl) makePiece(ELEPHANT, BLACK);
	private static final XiangqiPieceImpl BLACK_ADVISOR = (XiangqiPieceImpl) makePiece(ADVISOR, BLACK);
	private static final XiangqiPieceImpl BLACK_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, BLACK);
	private static final XiangqiPieceImpl BLACK_SOLDIER = (XiangqiPieceImpl) makePiece(SOLDIER, BLACK);
	private static final XiangqiPieceImpl BLACK_CANNON = (XiangqiPieceImpl) makePiece(CANNON, BLACK);
	private static final XiangqiPieceImpl BLACK_HORSE = (XiangqiPieceImpl) makePiece(HORSE, BLACK);

	/**
	 * 
	 */
	public DeltaInitializer() {
		super();
	}

	/* (non-Javadoc)
	 * @see xiangqi.studenthbnguyen.versions.otherxiangqiversions.InitializerTemplate#initializeState()
	 */
	@Override
	protected void initializeState() {
		state.version = version;
		state.board = new XiangqiBoard(maxRank, maxFile);
		state.maxMove = maxMove;
		XNC.setRanks(maxRank);
		XNC.setFiles(maxFile);
	}

	/**
	 * @return
	 */
	@Override
	protected void initializeBoard() {
		XiangqiBoard board = state.board;
		board.placePiece(RED_CHARIOT, XNC.makeXNC(1, 1));
		board.placePiece(RED_ELEPHANT, XNC.makeXNC(1, 3));
		board.placePiece(RED_ADVISOR, XNC.makeXNC(1, 4));		
		board.placePiece(RED_GENERAL, XNC.makeXNC(1, 5));
		board.placePiece(RED_ADVISOR, XNC.makeXNC(1, 6));
		board.placePiece(RED_ELEPHANT, XNC.makeXNC(1, 7));
		board.placePiece(RED_CHARIOT, XNC.makeXNC(1, 9));
		board.placePiece(RED_SOLDIER, XNC.makeXNC(4, 1));
		board.placePiece(RED_SOLDIER, XNC.makeXNC(4, 3));
		board.placePiece(RED_SOLDIER, XNC.makeXNC(4, 5));
		board.placePiece(RED_SOLDIER, XNC.makeXNC(4, 7));
		board.placePiece(RED_SOLDIER, XNC.makeXNC(4, 9));
		board.placePiece(RED_CANNON, XNC.makeXNC(3, 2));
		board.placePiece(RED_CANNON, XNC.makeXNC(3, 8));
		board.placePiece(RED_HORSE, XNC.makeXNC(1, 2));
		board.placePiece(RED_HORSE, XNC.makeXNC(1, 8));

		board.placePiece(BLACK_CHARIOT, XNC.makeXNC(1, 1));
		board.placePiece(BLACK_ELEPHANT, XNC.makeXNC(1, 3));
		board.placePiece(BLACK_ADVISOR, XNC.makeXNC(1, 4));		
		board.placePiece(BLACK_GENERAL, XNC.makeXNC(1, 5));
		board.placePiece(BLACK_ADVISOR, XNC.makeXNC(1, 6));
		board.placePiece(BLACK_ELEPHANT, XNC.makeXNC(1, 7));
		board.placePiece(BLACK_CHARIOT, XNC.makeXNC(1, 9));
		board.placePiece(BLACK_SOLDIER, XNC.makeXNC(4, 1));
		board.placePiece(BLACK_SOLDIER, XNC.makeXNC(4, 3));
		board.placePiece(BLACK_SOLDIER, XNC.makeXNC(4, 5));
		board.placePiece(BLACK_SOLDIER, XNC.makeXNC(4, 7));
		board.placePiece(BLACK_SOLDIER, XNC.makeXNC(4, 9));
		board.placePiece(BLACK_CANNON, XNC.makeXNC(3, 2));
		board.placePiece(BLACK_CANNON, XNC.makeXNC(3, 8));
		board.placePiece(BLACK_HORSE, XNC.makeXNC(1, 2));
		board.placePiece(BLACK_HORSE, XNC.makeXNC(1, 2));
	}

	/* (non-Javadoc)
	 * @see xiangqi.studenthbnguyen.versions.otherxiangqiversions.InitializerTemplate#addMoveValidators()
	 */
	@Override
	protected void addPreMoveValidators() {
		preMoveValidators.add(PreMoveValidators.isDestinationValid);
		preMoveValidators.add(PreMoveValidators.isValidSource);
		preMoveValidators.add(PreMoveValidators.isGeneralInCheck);
	}

	/* (non-Javadoc)
	 * @see xiangqi.studenthbnguyen.versions.otherxiangqiversions.InitializerTemplate#addPieceValidators()
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
		pieceValidators.put(RED_CHARIOT, chariotValidators);
		pieceValidators.put(BLACK_CHARIOT, chariotValidators);

		// advisor
		List<MoveValidator> advisorValidators = new LinkedList<MoveValidator>();
		advisorValidators.add(PieceValidators.isMoveDiagonal);
		advisorValidators.add(PieceValidators.isInPalace);
		pieceValidators.put(RED_ADVISOR, advisorValidators);
		pieceValidators.put(BLACK_ADVISOR, advisorValidators);

		// soldiers
		List<MoveValidator> soldierValidators = new LinkedList<MoveValidator>();
		soldierValidators.add(PieceValidators.soldierValidator);
		pieceValidators.put(RED_SOLDIER, soldierValidators);
		pieceValidators.put(BLACK_SOLDIER, soldierValidators);

		// elephants
		List<MoveValidator> elephantValidators = new LinkedList<MoveValidator>();
		elephantValidators.add(PieceValidators.moveDiagonallyTwoSteps);
		elephantValidators.add(PieceValidators.isNotCrossingRiver);
		pieceValidators.put(RED_ELEPHANT, elephantValidators);
		pieceValidators.put(BLACK_ELEPHANT, elephantValidators);

		// cannons
		List<MoveValidator> cannonValidators = new LinkedList<MoveValidator>();
		cannonValidators.add(PieceValidators.isValidCannonMove);
		pieceValidators.put(RED_CANNON, cannonValidators);
		pieceValidators.put(BLACK_CANNON, cannonValidators);
		
		// horses
		List<MoveValidator> horseValidators = new LinkedList<MoveValidator>();
		horseValidators.add(PieceValidators.isValidHorseMove);
		pieceValidators.put(RED_HORSE, horseValidators);
		pieceValidators.put(BLACK_HORSE, horseValidators);
	}

	/* (non-Javadoc)
	 * @see xiangqi.studenthbnguyen.versions.otherxiangqiversions.InitializerTemplate#addGameTerminationValidators()
	 */
	@Override
	protected void addPostMoveValidators() {
		postMoveValidators.add(PostMoveValidators.gameInStalemate);
		postMoveValidators.add(PostMoveValidators.gameInCheckmate);
	}
}
