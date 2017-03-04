/**
 * 
 */
package xiangqi.studenthbnguyen.common;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiGameVersion;
import xiangqi.common.XiangqiPiece;
import xiangqi.studenthbnguyen.util.DeepCopyMaker;

import static xiangqi.common.XiangqiColor.*;

/**
 * @author huyennguyen
 *
 */
public class XiangqiState {

	public String moveMessage = "";
	public XiangqiColor onMove = RED;
	public XiangqiBoard board;
	public XiangqiGameVersion version;
	public XNC generalAttackerCoordinate;
	public int moveCount = 0;
	public int maxMove;
	
	public XiangqiState() {
		
	}
	
	/**
	 * make a deep copy of the state 
	 * @param state the state of the original game
	 * @return a deep copy of the game
	 */
	public static XiangqiState makeDeepCopy(XiangqiState state) {
		XiangqiState stateCopy = new XiangqiState();
		stateCopy.onMove = state.onMove;
		stateCopy.moveMessage = state.moveMessage;
		stateCopy.generalAttackerCoordinate = state.generalAttackerCoordinate;
		stateCopy.version = state.version;
		stateCopy.maxMove = state.maxMove;
		stateCopy.board = new XiangqiBoard(state.board.ranks, state.board.files);
		stateCopy.board.boardMap = DeepCopyMaker.makeDeepCopy(state.board.boardMap);
		return stateCopy;
	}
}
