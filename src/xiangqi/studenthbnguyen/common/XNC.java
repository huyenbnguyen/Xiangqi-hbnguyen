/**
 * 
 */
package xiangqi.studenthbnguyen.common;

import xiangqi.common.XiangqiColor;
import static xiangqi.common.XiangqiColor.*;
import static xiangqi.common.XiangqiPieceType.NONE;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import xiangqi.common.XiangqiCoordinate;

/**
 * @author huyennguyen
 *
 */
public class XNC implements XiangqiCoordinate {
	private int rank, file;
	private static int ranks;
	private static int files;
	
	private XNC(int rank, int file) {
		this.rank = rank;
		this.file = file;
	}
	
	/**
	 * make a XNC 
	 * @param rank the rank 
	 * @param file the file
	 * @return an XNC
	 */
	public static XNC makeXNC(int rank, int file) {
		return new XNC(rank, file);
	}

	/**
	 * @param ranks the ranks to set
	 */
	public static void setRanks(int maxRank) {
		ranks = maxRank;
	}

	/**
	 * @param files the files to set
	 */
	public static void setFiles(int maxFile) {
		files = maxFile;
	}

	/**
	 * make an XNC
	 * @param coordinate the original coordinate 
	 * @param aspect the aspect
	 * @return the XNC of the original coordinate
	 */
	public static XNC makeXNC(XiangqiCoordinate coordinate, XiangqiColor aspect) {
		XNC result = makeXNC(coordinate.getRank(), coordinate.getFile());
		if (aspect == BLACK) 
			result = makeXNC(ranks + 1 - coordinate.getRank(), files - coordinate.getFile() + 1);
		return result; 
	}

	/**
	 * Check to see whether the piece moves orthogonally
	 * @param to the destination coordinates
	 * @return true if the piece moves orthogonally, false otherwise
	 */
	public boolean isOrthogonal(XNC to) {
		return to.getFile() == file || to.getRank() == rank;
	} 

	/**
	 * Check to see whether the piece moves diagonally
	 * @param to the destination coordinates
	 * @return true if the piece moves diagonally, false otherwise
	 */
	public boolean isDiagonalTo(XNC to) {
		return Math.abs(to.getFile() - file) == Math.abs(to.getRank() - rank);
	}
	
	/**
	 * Check to see whether the piece moves forward one step
	 * @param to the destination coordinates
	 * @return true if the piece moves forward one step, false otherwise
	 */
	public boolean isForwardOneStep(XNC to, XiangqiColor color) {
		if (to.getFile() == file) 
			return (color == RED) ?  (to.getRank() - rank) == 1 : (rank - to.getRank()) == 1;
		return false;
	}
	
	/**
	 * Check to see whether the piece moves one step
	 * @param to the destination coordinates
	 * @return true if the piece moves one step, false otherwise
	 */
	public boolean isDistanceOneAndOrthogonal(XNC to) {
		return (Math.abs(to.getFile() - file) == 1 && Math.abs(to.getRank() - rank) == 0) || 
				(Math.abs(to.getFile() - file) == 0 && Math.abs(to.getRank() - rank) == 1);
	}
	
	/**
	 * Get a list of intermediate coordinates 
	 * @param from source 
	 * @param to destination
	 * @return a list of intermediate coordinates 
	 */
	public static List<XNC> generateIntermediateCoordinates(XNC from, XNC to) {
		List<XNC> intermediateCoordinates = new LinkedList<XNC>();
		Point directionVector = new Point((int) Math.signum(to.getRank() - from.getRank()), 
				(int) Math.signum(to.getFile() - from.getFile()));
		XNC coordinate = XNC.makeXNC(from.getRank() + directionVector.x, from.getFile() + directionVector.y);
		while (!coordinate.equals(to)) {
			intermediateCoordinates.add(coordinate);
			coordinate = XNC.makeXNC(coordinate.getRank() + directionVector.x, coordinate.getFile() + directionVector.y);
		}
		return intermediateCoordinates; 
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiCoordinate#getRank()
	 */
	@Override
	public int getRank() {
		return rank;
	}

	/* (non-Javadoc)
	 * @see xiangqi.common.XiangqiCoordinate#getFile()
	 */
	@Override
	public int getFile() {
		return file;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + file;
        result = prime * result + rank;
        return result;
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof XNC)) {
            return false;
        }
        XNC other = (XNC) obj;
        if (file != other.getFile()) {
            return false;
        }
        if (rank != other.getRank()) {
            return false;
        }
        return true;
    }

	/**
	 * Check whether the piece is moving diagonally by two steps
	 * @param to the destination
	 * @return true if the piece is moving diagonally by two steps, false otherwise
	 */
	public boolean moveDiagonallyTwoSteps(XNC to) {
		return (Math.abs(to.getFile() - file) == 2 && 
				Math.abs(to.getRank() - rank) == 2);
	}

	/**
	 * Check whether the Red Soldier is moving left or right or up by one step
	 * @param to the destination
	 * @return true if the piece is moving left or right by one step, false otherwise
	 */
	public boolean moveLeftOrRightOrUpOneStep(XNC to, XiangqiColor color) {	
		return (rank == to.getRank() && Math.abs(file - to.getFile()) == 1) || 
				isForwardOneStep(to, color);
	}

	/**
	 * Check whether the Red General is in the palace
	 * @param to the destination
	 * @param color the color
	 * @return true if the general is in the palace after making the move, false otherwise
	 */
	public boolean isInPalace(XNC to, XiangqiColor color) {
		int maxRank = (color == RED) ? 3 : 10;
		int minRank = (color == RED) ? 1 : 8;
		return (to.getRank() >= minRank && to.getRank() <= maxRank &&
				to.getFile() >= 4 && to.getFile() <= 6);
	}

	/**
	 * Check whether the Elephant is crossing the river
	 * @param to the destination
	 * @param color the color
	 * @return true if the Red Elephant is crossing the river after making the move, false otherwise
	 */
	public boolean isNotCrossingRiver(XNC to, XiangqiColor color) {
		return (color == RED) ? (to.getRank() <= 5) : (to.getRank() >= 6);
	}
}
