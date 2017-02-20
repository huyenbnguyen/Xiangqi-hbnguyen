/**
 * 
 */
package xiangqi.studenthbnguyen.versions.alphaxiangqi;

import xiangqi.common.MoveResult;
import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiCoordinate;
import xiangqi.common.XiangqiGame;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenthbnguyen.common.XiangqiPieceImpl;

/**
 * @author huyennguyen
 *
 */
public class AlphaXiangqi implements XiangqiGame {

	private int moveCount;
	private String moveMessage;
	
	public AlphaXiangqi() {
		moveCount = 0;
		moveMessage = "";
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#makeMove(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiCoordinate)
	 */
	@Override
	public MoveResult makeMove(XiangqiCoordinate source, XiangqiCoordinate destination) {
		if (source.getRank() != 1 || source.getFile() != 1
				|| destination.getRank() != 1 || destination.getFile() != 2) {
			moveMessage = "Illegal";
			return MoveResult.ILLEGAL;
		}
		return moveCount++ == 0? MoveResult.OK : MoveResult.RED_WINS;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getMoveMessage()
	 */
	@Override
	public String getMoveMessage() {
		return moveMessage;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiGame#getPieceAt(xiangqi.common.XiangqiCoordinate, xiangqi.common.XiangqiColor)
	 */
	@Override
	public XiangqiPiece getPieceAt(XiangqiCoordinate where, XiangqiColor aspect) {
		XiangqiPiece piece = XiangqiPieceImpl.makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE);
		return piece;
	}
}
