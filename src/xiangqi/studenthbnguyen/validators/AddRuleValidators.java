/**
 * 
 */
package xiangqi.studenthbnguyen.validators;

import static xiangqi.common.MoveResult.OK;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import xiangqi.common.XiangqiColor;
import static xiangqi.common.XiangqiColor.*;
import static xiangqi.common.XiangqiGameVersion.*;
import xiangqi.common.XiangqiPiece;

import static xiangqi.common.XiangqiPieceType.*;
import xiangqi.studenthbnguyen.common.XNC;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.util.ValidatorAdder;

/**
 * @author huyennguyen
 *
 */
public class AddRuleValidators {
	
	public static BiFunction<XiangqiState, Map<XiangqiPieceImpl, List<MoveValidator>>, Map<XiangqiPieceImpl, List<MoveValidator>>> addRuleToSoldier = (state, moveValidators) -> {
		if (state.version != GAMMA_XQ) return moveValidators;
		moveValidators = addRuleToSoldierHelperRed(state, moveValidators);
		moveValidators = addRuleToSoldierHelperBlack(state, moveValidators);
		return moveValidators;
	};

	private static Map<XiangqiPieceImpl, List<MoveValidator>> addRuleToSoldierHelperBlack(XiangqiState state, Map<XiangqiPieceImpl, List<MoveValidator>> moveValidators) {
		for (Entry<XNC, XiangqiPieceImpl> entry : state.board.boardMap.entrySet()) {
			XiangqiPiece piece = entry.getValue();
			XNC coordinate = entry.getKey();
			if (piece.getColor() == BLACK && 
					piece.getPieceType() == SOLDIER && 
					coordinate.getRank() <= 5) { 
				return ValidatorAdder.addValidator(moveValidators, piece, PieceValidators.moveLeftOrRightOrUpOneStepBlack);
			}  
		}
		return moveValidators;
	}

	private static Map<XiangqiPieceImpl, List<MoveValidator>> addRuleToSoldierHelperRed(XiangqiState state, Map<XiangqiPieceImpl, List<MoveValidator>> moveValidators) {
		for (Entry<XNC, XiangqiPieceImpl> entry : state.board.boardMap.entrySet()) {
			XiangqiPiece piece = entry.getValue();
			XNC coordinate = entry.getKey();
			if (piece.getColor() == RED && 
					piece.getPieceType() == SOLDIER && 
					coordinate.getRank() >= 6) { 
				return ValidatorAdder.addValidator(moveValidators, piece, PieceValidators.moveLeftOrRightOrUpOneStepRed);
			}  
		}
		return moveValidators;
	}
}
