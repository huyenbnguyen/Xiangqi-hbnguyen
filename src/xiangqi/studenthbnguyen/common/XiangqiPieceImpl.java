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
	private XNC coordinate;
	
	private XiangqiPieceImpl(XiangqiPieceType pieceType, XiangqiColor color, XNC coordinate) {
		this.pieceType = pieceType;
		this.color = color;
		this.coordinate = coordinate;
	}
	
	private XiangqiPieceImpl(XiangqiPieceType pieceType, XiangqiColor color) {
		this.pieceType = pieceType;
		this.color = color;
	}
	
	public static XiangqiPiece makePiece(XiangqiPieceType pieceType, XiangqiColor color, XNC coordinate) {
		return new XiangqiPieceImpl(pieceType, color, coordinate);
	}
	
	public static XiangqiPiece makePiece(XiangqiPieceType pieceType, XiangqiColor color) {
		return new XiangqiPieceImpl(pieceType, color);
	}
	
	/**
	 * @param coordinate the coordinate to set
	 */
	public void setCoordinate(XNC coordinate) {
		this.coordinate = coordinate;
	}
	
	/**
	 * @param coordinate the coordinate to set
	 */
	public XNC getCoordinate() {
		return coordinate;
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
