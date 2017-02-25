/**
 * 
 */
package xiangqi.studenthbnguyen.versions.otherxiangqiversions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;
import xiangqi.studenthbnguyen.common.XiangqiState;
import xiangqi.studenthbnguyen.validators.MoveValidator;

/**
 * The template class for initializers.
 * @author huyennguyen
 *
 */
public abstract class InitializerTemplate {
	protected XiangqiState state;
	protected List<MoveValidator> moveValidators;
	protected Map<XiangqiPieceImpl, List<MoveValidator>> pieceValidators;
	protected List<Predicate> gameTerminationValidators;
	protected List<BiFunction> addRuleValidators;
	
	public InitializerTemplate() {
		moveValidators = new LinkedList<MoveValidator>();
		pieceValidators = new HashMap<XiangqiPieceImpl, List<MoveValidator>>();
		gameTerminationValidators = new LinkedList<Predicate>();
		addRuleValidators = new LinkedList<BiFunction>();
		state = initializeState();
		addMoveValidators();
		addPieceValidators();
		addGameTerminationValidators();
		addAddRuleValidators();
	}

	/**
	 * @return
	 */
	protected abstract XiangqiState initializeState();
	
	/**
	 * @return
	 */
	protected abstract void addMoveValidators();
	
	/**
	 * @return
	 */
	protected abstract void addPieceValidators();
	
	/**
	 * @return
	 */
	protected abstract void addGameTerminationValidators();
	
	/**
	 * @return
	 */
	protected abstract void addAddRuleValidators();
	
	/**
	 * @return
	 */
	public XiangqiState getState() {
		return state;
	}

	/**
	 * @return
	 */
	public List<MoveValidator> getMoveValidators() {
		return moveValidators;
	}

	/**
	 * @return
	 */
	public Map<XiangqiPieceImpl, List<MoveValidator>> getPieceValidators() {
		return pieceValidators;
	}

	/**
	 * @return
	 */
	public List<Predicate> getGameTerminationValidators() {
		return gameTerminationValidators;
	}
	
	/**
	 * @return
	 */
	public List<BiFunction> getAddRuleValidators() {
		return addRuleValidators;
	}
}
