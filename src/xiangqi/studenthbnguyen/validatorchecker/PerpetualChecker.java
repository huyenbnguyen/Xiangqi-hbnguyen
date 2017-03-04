/**
 * 
 */
package xiangqi.studenthbnguyen.validatorchecker;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.util.FixedSizeQueue;
import static xiangqi.common.XiangqiGameVersion.*;
/**
 * @author huyennguyen
 *
 */
public class PerpetualChecker {
	public static BiPredicate<XiangqiState, FixedSizeQueue> perpetualCheck = (state, boardConfig) -> {
		if (state.version != ALPHA_XQ && state.version != BETA_XQ && state.version != GAMMA_XQ) {
			for (int i = 0; i < boardConfig.getCapacity(); i++) {
				for (int j = i+1; j < boardConfig.getCapacity(); j++) {
					if (boardConfig.get(i) != null && 
							boardConfig.get(j) != null &&
							boardConfig.get(i).equals(boardConfig.get(j))) {
						for (int k = j+1; k < boardConfig.getCapacity(); k++) {
							if (boardConfig.get(j) != null && 
									boardConfig.get(k) != null &&
									boardConfig.get(k).equals(boardConfig.get(j)))
								return true;
						}
					}
				}
			}
		}
		return false;
	};
}
