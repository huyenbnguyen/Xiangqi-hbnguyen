/**
 * 
 */
package xiangqi.studenthbnguyen.validcoordinategenerators;

import java.util.LinkedList;

import xiangqi.common.XiangqiPiece;
import xiangqi.studenthbnguyen.common.XNC;

/**
 * @author huyennguyen
 *
 */
public class ValidCoordinateGenerators {
	public static ValidCoordinateGenerator<XNC> generalValidCoordinateGenerator = (coordinate) -> {
		LinkedList<XNC> validXNCs = new LinkedList<XNC>();
		int rank = coordinate.getRank();
		int file = coordinate.getFile();
		validXNCs.add(XNC.makeXNC(rank, file-1));
		validXNCs.add(XNC.makeXNC(rank, file+1));
		validXNCs.add(XNC.makeXNC(rank-1, file));
		validXNCs.add(XNC.makeXNC(rank+1, file));
		return validXNCs;
	};
}
