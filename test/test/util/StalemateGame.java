/**
 * 
 */
package test.util;

import static xiangqi.common.XiangqiColor.BLACK;
import static xiangqi.common.XiangqiColor.RED;
import static xiangqi.common.XiangqiGameVersion.*;
import static xiangqi.common.XiangqiPieceType.ADVISOR;
import static xiangqi.common.XiangqiPieceType.CHARIOT;
import static xiangqi.common.XiangqiPieceType.ELEPHANT;
import static xiangqi.common.XiangqiPieceType.GENERAL;
import static xiangqi.common.XiangqiPieceType.SOLDIER;
import static xiangqi.studenthbnguyen.common.XiangqiPieceImpl.makePiece;

import java.util.LinkedList;
import java.util.List;

import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiGameVersion;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiBaseGame;
import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validatorchecker.PieceChecker;
import xiangqi.studenthbnguyen.validatorchecker.PostMoveChecker;
import xiangqi.studenthbnguyen.validatorchecker.PreMoveChecker;
import xiangqi.studenthbnguyen.validators.MoveValidator;
import xiangqi.studenthbnguyen.validators.PieceValidators;
import xiangqi.studenthbnguyen.validators.PostMoveValidators;
import xiangqi.studenthbnguyen.validators.PreMoveValidators;
import xiangqi.studenthbnguyen.versions.otherxiangqiversions.InitializerTemplate;

/**
 * @author huyennguyen
 *
 */
public class StalemateGame {
	private static XiangqiGame game;
	private static XiangqiState state;
	private static InitializerTemplate initializer;
	
	public static XiangqiGame makeStalemateGame() {
		initializer = new StalemateInitializer();
		state = initializer.getState();
		game = new XiangqiBaseGame(state);
		setCheckers(initializer);
		return game;
	}
	
	private static void setCheckers(InitializerTemplate init) {
		PieceChecker.setPiecevalidators(init.getPieceValidators());
		PostMoveChecker.setPostMoveValidators(init.getPostMoveValidators());
		PreMoveChecker.setPreMoveValidators(init.getPreMoveValidators());
	}
}

class StalemateInitializer extends InitializerTemplate {

	// Change these 3 variables to change the size of the board
	private static int maxRank = 10;
	private static int maxFile = 9;

	// Change this variable to change the maximum number of moves allowed
	private static int maxMove = 25;

	// Change this to change the version of the game 
	private static XiangqiGameVersion version = DELTA_XQ;

	private static final XiangqiPieceImpl RED_CHARIOT = (XiangqiPieceImpl) makePiece(CHARIOT, RED);
	private static final XiangqiPieceImpl RED_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, RED);
	private static final XiangqiPieceImpl BLACK_GENERAL = (XiangqiPieceImpl) makePiece(GENERAL, BLACK);

	public StalemateInitializer() {
		super();
	}

	/**
	 * 
	 */
	@Override
	protected void addPostMoveValidators() {
		postMoveValidators.add(PostMoveValidators.gameInStalemate);
		postMoveValidators.add(PostMoveValidators.gameInCheckmate);
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
		pieceValidators.put(RED_CHARIOT, chariotValidators);
	}

	/**
	 * 
	 */
	@Override
	protected void addPreMoveValidators() {
		preMoveValidators.add(PreMoveValidators.isDestinationValid);
		preMoveValidators.add(PreMoveValidators.isValidSource);
		preMoveValidators.add(PreMoveValidators.isGeneralInCheck);
	}

	/**
	 * @return
	 */
	@Override
	protected void initializeState() {
		state.version = version;
		state.board = new XiangqiBoard(maxRank, maxFile);
		state.maxMove = maxMove;
		XNC.setRanks(maxRank);
		XNC.setFiles(maxFile);
	}


	@Override
	protected void initializeBoard() {
		XiangqiBoard board = state.board;
		board.placePiece(RED_CHARIOT, XNC.makeXNC(1, 4));
		board.placePiece(RED_CHARIOT, XNC.makeXNC(1, 6));
		board.placePiece(RED_CHARIOT, XNC.makeXNC(8, 1));
		board.placePiece(RED_GENERAL, XNC.makeXNC(1, 5));	
		board.placePiece(BLACK_GENERAL, XNC.makeXNC(1, 5));
	}
}
