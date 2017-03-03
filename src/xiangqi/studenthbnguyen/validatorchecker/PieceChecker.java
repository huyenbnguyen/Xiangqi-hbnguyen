/**
 * 
 */
package xiangqi.studenthbnguyen.validatorchecker;

import static xiangqi.common.MoveResult.ILLEGAL;
import static xiangqi.common.MoveResult.OK;

import java.util.HashMap;
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
public class PieceChecker {
	private static Map<XiangqiPieceImpl, List<MoveValidator>> pieceValidators = new HashMap<>();

	
	/**
	 * @param piecevalidators the piecevalidators to set
	 */
	public static void setPiecevalidators(Map<XiangqiPieceImpl, List<MoveValidator>> piecevalidators) {
		pieceValidators = piecevalidators;
	}

	public static MoveResult runChecker(XiangqiState state, XNC from, XNC to) {
		XiangqiPieceImpl piece = (XiangqiPieceImpl) state.board.getPieceAt(from);
		List<MoveValidator> list = pieceValidators.get(piece);
		for (MoveValidator<XiangqiState, XNC, Boolean> mv : pieceValidators.get(piece)) {
			if (mv.apply(state, from, to) == ILLEGAL) return ILLEGAL;
		}
		return OK;
	}
}
