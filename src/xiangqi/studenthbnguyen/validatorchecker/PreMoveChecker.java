/**
 * 
 */
package xiangqi.studenthbnguyen.validatorchecker;

import static xiangqi.common.MoveResult.ILLEGAL;
import static xiangqi.common.MoveResult.OK;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import xiangqi.common.MoveResult;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validators.MoveValidator;

/**
 * @author huyennguyen
 *
 */
public class PreMoveChecker {
	private static List<MoveValidator> preMoveValidators = new LinkedList<>();
	
	/**
	 * @param piecevalidators the piecevalidators to set
	 */
	public static void setPreMoveValidators(List<MoveValidator> premovevalidators) {
		preMoveValidators = premovevalidators;
	}

	public static MoveResult runChecker(XiangqiState state, XNC from, XNC to) {
		XiangqiPieceImpl piece = (XiangqiPieceImpl) state.board.getPieceAt(from);
		for (MoveValidator<XiangqiState, XNC, Boolean> mv : preMoveValidators) {
			if (mv.apply(state, from, to) == ILLEGAL) return ILLEGAL;
		}
		return OK;
	}
}
