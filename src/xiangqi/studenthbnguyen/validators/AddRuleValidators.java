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
		if (state.version == ALPHA_XQ || state.version == BETA_XQ) return moveValidators;
		moveValidators = addRuleToSoldierHelper(state, moveValidators, RED);
		moveValidators = addRuleToSoldierHelper(state, moveValidators, BLACK);
		return moveValidators;
	};

	private static Map<XiangqiPieceImpl, List<MoveValidator>> addRuleToSoldierHelper(XiangqiState state, Map<XiangqiPieceImpl, List<MoveValidator>> moveValidators, XiangqiColor color) {
		for (Entry<XNC, XiangqiPieceImpl> entry : state.board.boardMap.entrySet()) {
			XiangqiPieceImpl piece = entry.getValue();
			XNC coordinate = entry.getKey();
			boolean crossedRiver = (color == RED) ? coordinate.getRank() >= 6 : coordinate.getRank() <= 5; 
			if (crossedRiver && piece.getColor() == color && piece.getPieceType() == SOLDIER) { 
				return ValidatorAdder.addValidator(moveValidators, piece, PieceValidators.moveLeftOrRightOrUpOneStep);
			}  
		}
		return moveValidators;
	}
}
