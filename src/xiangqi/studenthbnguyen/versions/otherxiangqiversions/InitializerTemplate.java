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
	protected List<MoveValidator> preMoveValidators;
	protected Map<XiangqiPieceImpl, List<MoveValidator>> pieceValidators;
	protected List<Predicate> postMoveValidators;
	
	public InitializerTemplate() {
		preMoveValidators = new LinkedList<MoveValidator>();
		pieceValidators = new HashMap<XiangqiPieceImpl, List<MoveValidator>>();
		postMoveValidators = new LinkedList<Predicate>();
		state = new XiangqiState();
		initializeState();
		addPreMoveValidators();
		addPieceValidators();
		addPostMoveValidators();
		initializeBoard();
	}

	/**
	 * @return
	 */
	protected abstract void initializeState();
	
	/**
	 * @return
	 */
	protected abstract void addPreMoveValidators();
	
	/**
	 * @return
	 */
	protected abstract void addPieceValidators();
	
	/**
	 * @return
	 */
	protected abstract void addPostMoveValidators();
	
	/**
	 * @return
	 */
	protected abstract void initializeBoard();
	
	/**
	 * @return
	 */
	public XiangqiState getState() {
		return state;
	}

	/**
	 * @return
	 */
	public List<MoveValidator> getPreMoveValidators() {
		return preMoveValidators;
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
	public List<Predicate> getPostMoveValidators() {
		return postMoveValidators;
	}
}
