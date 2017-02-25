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
	private int index; // used to differentiate different pieces of the same type and color
	
	private XiangqiPieceImpl(XiangqiPieceType pieceType, XiangqiColor color, int index) {
		this.pieceType = pieceType;
		this.color = color;
		this.index = index;
	}
	
	private XiangqiPieceImpl(XiangqiPieceType pieceType, XiangqiColor color) {
		this.pieceType = pieceType;
		this.color = color;
	}
	
	/**
	 * make a XiangqiPiece 
	 * @param pieceType piece type
	 * @param color color
	 * @param index index to differentiate between different pieces of the same type
	 * @return a chess piece
	 */
	public static XiangqiPiece makePiece(XiangqiPieceType pieceType, XiangqiColor color, int index) {
		return new XiangqiPieceImpl(pieceType, color, index);
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
	
	/**
	 * 
	 */
	public int getIndex() {
		return index;
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
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        result = prime * result + pieceType.getPrintableName().length();
        result = prime * result + color.name().length();
        return result;
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
        if (!(obj instanceof XiangqiPieceImpl)) {
            return false;
        }
        XiangqiPieceImpl other = (XiangqiPieceImpl) obj;
        if (color != other.getColor()) {
            return false;
        }
        if (pieceType != other.getPieceType()) {
            return false;
        }
        if (index != other.getIndex()) {
            return false;
        }
        return true;
    }
}
