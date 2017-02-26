/**
 * 
 */
package xiangqi.studenthbnguyen.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.validators.MoveValidator;

/**
 * @author huyennguyen
 *
 */
public class ValidatorReplacer {

	/**
	 * Add validators to a specific piece
	 * @param pieceValidators the existing move validators of all the pieces
	 * @param piece the piece
	 * @param newValidators the new validator to be added
	 * @return new validators of all the pieces
	 */
	public static Map<XiangqiPieceImpl, List<MoveValidator>> replaceValidator(
			Map<XiangqiPieceImpl, List<MoveValidator>> pieceValidators, 
			XiangqiPieceImpl piece,
			List<MoveValidator> newValidators) {
		for (Map.Entry<XiangqiPieceImpl, List<MoveValidator>> entry : pieceValidators.entrySet()) {
			XiangqiPiece pieceEntry = entry.getKey();
			if (piece.equals(pieceEntry)) {
				pieceValidators.remove(entry);
				pieceValidators.put(piece, newValidators);
			}
		}
		return pieceValidators;
	}
}
