/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import xiangqi.common.MoveResult;

/**
 * @author huyennguyen
 *
 */
@FunctionalInterface
public interface MoveValidator<S, C, R> {
	public MoveResult apply(S s, C f, C t);
}
