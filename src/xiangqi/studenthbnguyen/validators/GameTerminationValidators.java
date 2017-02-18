/**
 * 
 */
package xiangqi.studenthbnguyen.validators;
import java.util.function.Predicate;

import xiangqi.studenthbnguyen.common.XiangqiBaseGame;
import xiangqi.studenthbnguyen.common.XiangqiState;

/**
 * @author huyennguyen
 *
 */
public class GameTerminationValidators {

	// General is under attack and has no means of escape
	public static Predicate<XiangqiState> gameInCheckmate = (state) -> {
		XiangqiBaseGame gameCopy = XiangqiBaseGame.makeDeepCopy(state);
		
		
		return true;
	};

	// General is under attack and has no means of escape
	public static Predicate<XiangqiState> gameInStalemate = (state) -> {

		return true;
	};
}
