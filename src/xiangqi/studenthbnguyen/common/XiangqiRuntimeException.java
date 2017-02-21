/**
 * 
 */
package xiangqi.studenthbnguyen.common;

/**
 * @author huyennguyen
 *
 */
public class XiangqiRuntimeException extends RuntimeException {
	private String error;
	/**
	 * @param string
	 */
	public XiangqiRuntimeException(String error) {
		this.error = error;
	}

}
