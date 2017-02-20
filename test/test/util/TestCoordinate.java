/**
 * 
 */
package test.util;

import xiangqi.common.XiangqiCoordinate;
import xiangqi.studenthbnguyen.common.XNC;

/**
 * @author huyennguyen
 *
 */
public class TestCoordinate implements XiangqiCoordinate {
	 	private final int rank;
	    private final int file;

	    public TestCoordinate(int rank, int file) {
	        this.rank = rank;
	        this.file = file;
	    }

	    public static XNC makeCoordinate(int rank, int file) {
	        return XNC.makeXNC(rank, file);
	    }

	    @Override
	    public int getRank() {
	        return rank;
	    }

	    @Override
	    public int getFile() {
	        return file;
	    }

}
