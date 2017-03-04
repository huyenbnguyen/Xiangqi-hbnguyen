/**
 * 
 */
package xiangqi.studenthbnguyen.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletionException;
import java.util.function.BiFunction;
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
import xiangqi.studenthbnguyen.util.FixedSizeQueue;
import xiangqi.studenthbnguyen.validatorchecker.PerpetualChecker;
import xiangqi.studenthbnguyen.validatorchecker.PieceChecker;
import xiangqi.studenthbnguyen.validatorchecker.PostMoveChecker;
import xiangqi.studenthbnguyen.validatorchecker.PreMoveChecker;
import xiangqi.studenthbnguyen.validators.MoveValidator;
import xiangqi.studenthbnguyen.validators.PreMoveValidators;
import xiangqi.studenthbnguyen.versions.otherxiangqiversions.BetaInitializer;


/**
 * @author huyennguyen
 *
 */
public class XiangqiBaseGame implements XiangqiGame {
	private XiangqiState state;
	private FixedSizeQueue<XiangqiBoard> redBoardConfigs;
	private FixedSizeQueue<XiangqiBoard> blackBoardConfigs;
	private static final int PERPETUAL_BOARD_CONFIG = 5;

	/**
	 * @param state
	 */
	public XiangqiBaseGame(XiangqiState state) {
		this.state = state;
		redBoardConfigs = new FixedSizeQueue<>(PERPETUAL_BOARD_CONFIG);
		blackBoardConfigs = new FixedSizeQueue<>(PERPETUAL_BOARD_CONFIG);
	}

	/**
	 * 
	 * @return
	 */
	public XiangqiState getState() {
		return state;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#makeMove(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public MoveResult makeMove(XiangqiCoordinate source, XiangqiCoordinate destination) {
		state.moveMessage = "";
		XNC sourceNormalized = XNC.makeXNC(source, state.onMove);
		XNC destinationNormalized = XNC.makeXNC(destination, state.onMove); 

		MoveResult preMove = PreMoveChecker.runChecker(state, sourceNormalized, destinationNormalized);

		if (preMove != OK) return preMove;	

		if (PieceChecker.runChecker(state, sourceNormalized, destinationNormalized) == ILLEGAL) 
			return ILLEGAL;

		// if the move is valid, make the move
		state.board.movePiece(sourceNormalized, destinationNormalized);
		state.moveCount++;

		// perpetual check
		if (state.onMove == RED) {
			redBoardConfigs.add(state.board);
			if (PerpetualChecker.perpetualCheck.test(state, redBoardConfigs))
				return BLACK_WINS;
		} else {
			blackBoardConfigs.add(state.board);
			if (PerpetualChecker.perpetualCheck.test(state, blackBoardConfigs))
				return RED_WINS;
		}
		
		switchTurn();

		MoveResult isDraw = PreMoveValidators.gameRanOutOfMove.apply(state, sourceNormalized, destinationNormalized);
		if (isDraw != OK) return isDraw;
		return PostMoveChecker.runChecker(state, sourceNormalized, destinationNormalized);
	}



	/**
	 * Switch move to the opponent
	 */
	public void switchTurn() {
		state.onMove = (state.onMove == RED) ? BLACK : RED; 
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
		if (!PreMoveValidators.checkBounds(where, state)) {
			throw new CompletionException(null);
		}
		return state.board.getPieceAt(XNC.makeXNC(where, aspect));
	}
}
