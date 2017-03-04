/**
 * 
 */
package xiangqi.studenthbnguyen.validatorchecker;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import xiangqi.studenthbnguyen.common.XiangqiBoard;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.util.FixedSizeQueue;
import static xiangqi.common.XiangqiGameVersion.*;
/**
 * @author huyennguyen
 *
 */
public class PerpetualChecker {
	public static BiPredicate<XiangqiState, FixedSizeQueue> perpetualCheck = (state, boardConfig) -> {
		boolean a = true;
		if (state.version != ALPHA_XQ && state.version != BETA_XQ && state.version != GAMMA_XQ && boardConfig.getSize() == boardConfig.getCapacity()) {
			for (int i = 0; i < boardConfig.getSize(); i++) {
				for (int j = i+1; j < boardConfig.getSize(); j++) {
					if (boardConfig.get(i).equals(boardConfig.get(j))) {
						for (int k = j+1; k < boardConfig.getSize(); k++) {
							if (boardConfig.get(k).equals(boardConfig.get(j)))
								return true;
						}
					}
				}
			}
		}
		return false;
	};
}
