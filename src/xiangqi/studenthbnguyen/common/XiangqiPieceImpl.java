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
	
	/**
	 * make a XiangqiPiece 
	 * @param pieceType piece type
	 * @param color color
	 * @return a chess piece
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XiangqiPieceImpl other = (XiangqiPieceImpl) obj;
		if (color != other.color)
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}	
}
