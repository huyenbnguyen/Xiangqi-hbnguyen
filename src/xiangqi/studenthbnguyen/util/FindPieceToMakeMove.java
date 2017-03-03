/**
 * 
 */
package xiangqi.studenthbnguyen.util;

import xiangqi.studenthbnguyen.common.XiangqiState;

/**
 * @author huyennguyen
 *
 */
public class FindPieceToMakeMove {
	public boolean hasPiece;
	public XiangqiState newState;
	
	public FindPieceToMakeMove(boolean hasPiece, XiangqiState newState) {
		this.hasPiece = hasPiece;
		this.newState = newState;
	}
}
