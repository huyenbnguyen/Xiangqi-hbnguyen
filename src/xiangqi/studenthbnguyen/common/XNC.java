/**
 * 
 */
package xiangqi.studenthbnguyen.common;

import xiangqi.common.XiangqiColor;
import static xiangqi.common.XiangqiColor.*;
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
			result = makeXNC(state.board.ranks + 1 - coordinate.getRank(), coordinate.getFile());
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
	public boolean isForwardOneStep(XNC to) {
		return (to.getFile() == file && Math.abs(to.getRank() - rank) == 1);
	}
	
	/**
	 * Check to see whether the piece moves one step
	 * @param to the destination coordinates
	 * @return true if the piece moves one step, false otherwise
	 */
	public boolean isDistanceOne(XNC to) {
		return (Math.abs(to.getFile() - file) == 1 && Math.abs(to.getRank() - rank) == 0) || 
				(Math.abs(to.getFile() - file) == 0 && Math.abs(to.getRank() - rank) == 1);
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
}
