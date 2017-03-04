/**
 * 
 */
package xiangqi.studenthbnguyen.common;
import static xiangqi.common.MoveResult.ILLEGAL;
import static xiangqi.common.MoveResult.OK;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;
import xiangqi.studenthbnguyen.util.DeepCopyMaker;
import xiangqi.studenthbnguyen.validatorchecker.PieceChecker;

/**
 * @author huyennguyen
 *
 */
public class XiangqiBoard {
	public Map<XNC, XiangqiPieceImpl> boardMap;
	
	public int ranks;
	public int files;

	/**
	 * Constructor
	 * @param ranks the number of ranks
	 * @param files the number of files
	 */
	public XiangqiBoard(int ranks, int files) {
		boardMap = new HashMap<XNC, XiangqiPieceImpl>();
		this.ranks = ranks;
		this.files = files;
	}

	/**
	 * Get the piece at a coordinate
	 * @param sourceNormalized the normalized coordinate
	 * @return the piece at the coordinate
	 */
	public XiangqiPiece getPieceAt(XNC sourceNormalized) {
		XiangqiPiece piece = boardMap.get(sourceNormalized);
		return (piece != null) ? piece : XiangqiPieceImpl.makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE); 
	}

	/**
	 * Move the piece to a new coordinate
	 * @param sourceNormalized source coordinate
	 * @param destinationNormalized destination coordinate
	 */
	public void movePiece(XNC sourceNormalized, XNC destinationNormalized) {
		XiangqiPieceImpl piece = (XiangqiPieceImpl) boardMap.get(sourceNormalized);
		boardMap.remove(sourceNormalized);
		boardMap.put(destinationNormalized, piece);
	}

	/**
	 * Place a piece at a specific coordinate
	 * ASSUMPTION: coordinates are NOT normalized 
	 * @param xiangqiPiece the piece
	 * @param normalizedCoordinate the coordinate
	 */
	public void placePiece(XiangqiPiece xiangqiPiece, XNC normalizedCoordinate) {
		XiangqiColor color = xiangqiPiece.getColor();
		normalizedCoordinate = XNC.makeXNC(normalizedCoordinate, color);
		boardMap.put(normalizedCoordinate, (XiangqiPieceImpl) xiangqiPiece);
	}
	
	/**
	 * Find the coordinate of a piece given its color and piece type
	 * @param color the color 
	 * @return the XNC of the general
	 */
	public XNC findPiece(XiangqiPieceType pieceType, XiangqiColor color) {
		for (Map.Entry<XNC, XiangqiPieceImpl> entry : boardMap.entrySet()) {
			XiangqiPieceImpl piece = entry.getValue();
    		if (piece.getPieceType() == pieceType && 
    				piece.getColor() == color) {
    			return entry.getKey();
    		}
    	}
		return null;
	}
	
	public XiangqiBoard makeCopyBoard() {
		XiangqiBoard clone = new XiangqiBoard(ranks, files);
		clone.boardMap = DeepCopyMaker.makeDeepCopy(boardMap);
		return clone;
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
		XiangqiBoard other = (XiangqiBoard) obj;
		if (boardMap == null) {
			if (other.boardMap != null)
				return false;
		} else {
			for (Entry<XNC, XiangqiPieceImpl> entry : boardMap.entrySet()) {
				XiangqiPieceImpl piece = entry.getValue();
				XNC coordinate = entry.getKey();
				XiangqiPieceImpl pieceInOtherBoard = other.boardMap.get(coordinate);
				if (pieceInOtherBoard == null || 
						(pieceInOtherBoard != null && !pieceInOtherBoard.equals(piece)))
					return false;
			}
		}
		return true;
	}
}
