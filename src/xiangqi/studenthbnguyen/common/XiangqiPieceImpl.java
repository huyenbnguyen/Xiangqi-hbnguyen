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
	
	public static XiangqiPieceImpl makePiece(XiangqiPieceType pieceType, XiangqiColor color, XNC coordinate) {
		return new XiangqiPieceImpl(pieceType, color, coordinate);
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
	
	/*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof XNC)) {
            return false;
        }
        XiangqiPieceImpl other = (XiangqiPieceImpl) obj;
        if (pieceType != other.getPieceType()) {
            return false;
        }
        if (color != other.getColor()) {
            return false;
        }
        if (!coordinate.equals(other.getCoordinate())) {
            return false;
        }
        return true;
    }
}
