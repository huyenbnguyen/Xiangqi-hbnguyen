/**
 * 
 */
package xiangqi.studenthbnguyen.validcoordinategenerators;

import java.util.LinkedList;

/**
 * @author huyennguyen
 *
 */
@FunctionalInterface
public interface ValidCoordinateGenerator<C> {
	public LinkedList<C> apply(C c);
}
