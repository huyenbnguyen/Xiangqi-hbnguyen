/**
 * 
 */
package xiangqi.studenthbnguyen.validatorchecker;

import static xiangqi.common.MoveResult.BLACK_WINS;
import static xiangqi.common.MoveResult.ILLEGAL;
import static xiangqi.common.MoveResult.OK;
import static xiangqi.common.MoveResult.RED_WINS;
import static xiangqi.common.XiangqiColor.RED;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import xiangqi.common.MoveResult;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validators.MoveValidator;

/**
 * @author huyennguyen
 *
 */
public class PostMoveChecker {
	private static List<Predicate> postMoveValidators = new LinkedList<>();
	
	/**
	 * @param piecevalidators the piecevalidators to set
	 */
	public static void setPostMoveValidators(List<Predicate> postmovevalidators) {
		postMoveValidators = postmovevalidators;
	}

	public static MoveResult runChecker(XiangqiState state, XNC from, XNC to) {
		for (Predicate<XiangqiState> tv : postMoveValidators) {
			if (tv.test(state)) 
				return (state.onMove == RED) ? BLACK_WINS : RED_WINS;
		}
		return OK;
	}
}
