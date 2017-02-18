/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

/**
 * @author huyennguyen
 *
 */
@FunctionalInterface
public interface MoveValidator<S, C, R> {
	public R apply(S s, C f, C t);
}
