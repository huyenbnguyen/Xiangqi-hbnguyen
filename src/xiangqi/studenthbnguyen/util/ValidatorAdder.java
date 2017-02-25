/**
 * 
 */
package xiangqi.studenthbnguyen.util;

import java.util.List;
import java.util.Map;

import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.validators.MoveValidator;

/**
 * @author huyennguyen
 *
 */
public class ValidatorAdder {
	
	/**
	 * Add validators to a specific piece
	 * @param moveValidators the existing move validators of all the pieces
	 * @param piece the piece
	 * @param newValidator the new validator to be added
	 * @return new validators of all the pieces
	 */
	public static Map<XiangqiPieceImpl, List<MoveValidator>> addValidator(
			Map<XiangqiPieceImpl, List<MoveValidator>> moveValidators, 
			XiangqiPiece piece,
			MoveValidator newValidator) {
		List<MoveValidator> validators = moveValidators.get(piece);
		validators.clear();
		validators.add(newValidator);
		return moveValidators;
	}
}
