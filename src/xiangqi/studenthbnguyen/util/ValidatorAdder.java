/**
 * 
 */
package xiangqi.studenthbnguyen.util;

import java.util.List;
import java.util.Map;

import xiangqi.common.XiangqiPieceType;
import xiangqi.studenthbnguyen.validators.MoveValidator;

/**
 * @author huyennguyen
 *
 */
public class ValidatorAdder {
	public static Map<XiangqiPieceType, List<MoveValidator>> addValidator(
			Map<XiangqiPieceType, List<MoveValidator>> pieceValidators, 
			XiangqiPieceType pieceType,
			MoveValidator newValidator) {
		pieceValidators.get(pieceType).add(newValidator);
		return pieceValidators;
	}
}
