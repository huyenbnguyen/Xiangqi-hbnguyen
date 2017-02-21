/**
 * 
 */
package xiangqi.studenthbnguyen.common;
import java.util.HashMap;
import java.util.Map;

import xiangqi.common.XiangqiColor;
import xiangqi.common.XiangqiPiece;
import xiangqi.common.XiangqiPieceType;

/**
 * @author huyennguyen
 *
 */
public class XiangqiBoard {
	public Map<XNC, XiangqiPiece> boardMap;
	
	public int ranks;
	public int files;

	/**
	 * Constructor
	 * @param ranks the number of ranks
	 * @param files the number of files
	 */
	public XiangqiBoard(int ranks, int files) {
		boardMap = new HashMap<XNC, XiangqiPiece>();
		this.ranks = ranks;
		this.files = files;
	}

	/**
	 * Get the piece at a coordinate
	 * @param sourceNormalized the normalized coordinate
	 * @return the piece at the coordinate
	 */
	public XiangqiPiece getPieceAt(XNC sourceNormalized) {
		XiangqiPiece piece; 
		piece = boardMap.get(sourceNormalized);
		return (piece != null) ? piece : XiangqiPieceImpl.makePiece(XiangqiPieceType.NONE, XiangqiColor.NONE, XNC.makeXNC(sourceNormalized.getRank(), sourceNormalized.getFile())); 
	}

	/**
	 * Move the piece to a new coordinate
	 * @param sourceNormalized source coordinate
	 * @param destinationNormalized destination coordinate
	 */
	public void movePiece(XNC sourceNormalized, XNC destinationNormalized) {
		XiangqiPieceImpl piece = (XiangqiPieceImpl) boardMap.get(sourceNormalized);
		piece.setCoordinate(destinationNormalized);
		boardMap.remove(sourceNormalized);
		boardMap.put(destinationNormalized, piece);
	}

	/**
	 * Place a piece at a specific coordinate
	 * ASSUMPTION: coordinates are NOT normalized 
	 * @param xiangqiPiece the piece
	 * @param coordinate the coordinate
	 */
	public void placePiece(XiangqiPiece xiangqiPiece, XNC coordinate) {
		XiangqiColor color = xiangqiPiece.getColor();
		coordinate = XNC.makeXNC(coordinate, color);
		boardMap.put(coordinate, xiangqiPiece);
	}
	
	/**
	 * Find the coordinate general of the given color
	 * @param color the color 
	 * @return the XNC of the general
	 */
	public XNC findPiece(XiangqiPieceType pieceType, XiangqiColor color) {
		for (Map.Entry<XNC, XiangqiPiece> entry : boardMap.entrySet()) {
    		if (entry.getValue().getPieceType() == pieceType && 
    				entry.getValue().getColor() == color) {
    			return entry.getKey();
    		}
    	}
		return null;
	}
}
