/**
 * 
 */
package xiangqi.studenthbnguyen.common;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

/**
 * @author huyennguyen
 *
 */
public class XiangqiPieceImpl implements XiangqiPiece {
	private XiangqiPieceType pieceType;
	private XiangqiColor color;
	
	private XiangqiPieceImpl(XiangqiPieceType pieceType, XiangqiColor color) {
		this.pieceType = pieceType;
		this.color = color;
	}
	
	public static XiangqiPiece makePiece(XiangqiPieceType pieceType, XiangqiColor color) {
		return new XiangqiPieceImpl(pieceType, color);
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiPiece#getColor()
	 */
	@Override
	public XiangqiColor getColor() {
		return color;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiPiece#getPieceType()
	 */
	@Override
	public XiangqiPieceType getPieceType() {
		return pieceType;
	}
}
