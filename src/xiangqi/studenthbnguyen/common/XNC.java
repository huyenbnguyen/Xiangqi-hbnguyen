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
	private static XiangqiState state;
	
	private XNC(int rank, int file) {
		this.rank = rank;
		this.file = file;
	}
	
	public static XNC makeXNC(int rank, int file) {
		return new XNC(rank, file);
	}
	
	public static void setState(XiangqiState stateParam) {
		state = stateParam;
	}

	/**
	 * @param destination
	 * @param onMove
	 * @return
	 */
	public static XNC makeXNC(XiangqiCoordinate coordinate, XiangqiColor aspect) {
		XNC result = makeXNC(coordinate.getRank(), coordinate.getFile());
		if (aspect == BLACK) 
			result = makeXNC(state.board.ranks + 1 - coordinate.getRank(), state.board.files - coordinate.getFile() + 1);
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
	public boolean isForwardOneStepRed(XNC to) {
		return (to.getFile() == file && (to.getRank() - rank) == 1);
	}
	
	/**
	 * Check to see whether the piece moves forward one step
	 * @param to the destination coordinates
	 * @return true if the piece moves forward one step, false otherwise
	 */
	public boolean isForwardOneStepBlack(XNC to) {
		return (to.getFile() == file && (rank - to.getRank()) == 1);
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
        if (this == obj) {
            return true;
        }
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
	 * @param to
	 * @return
	 */
	public boolean moveDiagonallyTwoSteps(XNC to) {
		return (Math.abs(to.getFile() - file) == 2 && 
				Math.abs(to.getRank() - rank) == 2);
	}

	/**
	 * @param to
	 * @return
	 */
	public boolean moveLeftOrRightOneStepRed(XNC to) {
		return (rank == to.getRank() && Math.abs(file - to.getFile()) == 1) || 
				(file == to.getFile() && to.getRank() - rank == 1);
	}

	/**
	 * @param to
	 * @return
	 */
	public boolean isInPalaceRed(XNC to) {
		return (to.getRank() >= 1 && to.getRank() <= 3 &&
				to.getFile() >= 4 && to.getFile() <= 6);
	}

	/**
	 * @param to
	 * @return
	 */
	public boolean isInPalaceBlack(XNC to) {
		return (to.getRank() >= 8 && to.getRank() <= 10 &&
				to.getFile() >= 4 && to.getFile() <= 6);
	}

	/**
	 * @param to
	 * @return
	 */
	public boolean isNotCrossingRiverRed(XNC to) {
		return to.getRank() <= 5;
	}

	/**
	 * @param to
	 * @return
	 */
	public boolean isNotCrossingRiverBlack(XNC to) {
		return to.getRank() >= 6;
	}

	/**
	 * @param to
	 * @return
	 */
	public boolean moveLeftOrRightOneStepBlack(XNC to) {
		return (rank == to.getRank() && Math.abs(file - to.getFile()) == 1) || 
				(file == to.getFile() && rank - to.getRank() == 1);
	}
}
