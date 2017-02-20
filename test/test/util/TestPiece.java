/**
 * 
 */
package test.util;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

/**
 * @author huyennguyen
 *
 */
public class TestPiece implements XiangqiPiece {

	private final XiangqiColor color;
	private final XiangqiPieceType pieceType;
	
	public TestPiece(XiangqiPieceType pieceType, XiangqiColor color)
	{
		this.pieceType = pieceType;
		this.color = color;
	}
	
	/*
	 * @see xiangqi.common.XiangqiPiece#getColor()
	 */
	@Override
	public XiangqiColor getColor()
	{
		return color;
	}

	/*
	 * @see xiangqi.common.XiangqiPiece#getPieceType()
	 */
	@Override
	public XiangqiPieceType getPieceType()
	{
		return pieceType;
	}

	@Override
	public boolean equals(Object obj)
	{
		XiangqiPiece other = (XiangqiPiece) obj;
		if (color != other.getColor())
			return false;
		if (pieceType != other.getPieceType())
			return false;
		return true;
	}
}
