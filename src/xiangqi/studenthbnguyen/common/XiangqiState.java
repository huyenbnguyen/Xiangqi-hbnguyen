/**
 * 
 */
package xiangqi.studenthbnguyen.common;

import xiangqi.common.XiangqiColor;
import xiangqi.studenthbnguyen.util.DeepCopy;

import static xiangqi.common.XiangqiColor.*;

/**
 * @author huyennguyen
 *
 */
public class XiangqiState {

	public String moveMessage = "";
	public XiangqiColor onMove = RED;
	public XiangqiBoard board;
	
	public XiangqiState() {
		
	}
	
	public static XiangqiState makeDeepCopy(XiangqiState state) {
		XiangqiState stateCopy = new XiangqiState();
		stateCopy.board = new XiangqiBoard(state.board.ranks, state.board.files);
		stateCopy.board.boardMap = DeepCopy.makeDeepCopy(state.board.boardMap);
		return stateCopy;
	}
}
