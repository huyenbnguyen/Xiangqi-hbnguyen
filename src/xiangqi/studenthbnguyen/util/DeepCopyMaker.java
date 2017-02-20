/**
 * 
 */
package xiangqi.studenthbnguyen.util;

import java.util.HashMap;

import java.util.Map;


import xiangqi.studenthbnguyen.common.XNC;

/**
 * @author huyennguyen
 *
 */
public class DeepCopyMaker {
	
	/**
	 * Make a deep copy of a generic type
	 * @param input a generic object
	 * @return a deep copy of the generic object
	 */
	public static <X> X makeDeepCopy(final X input) {
		if (input instanceof Map<?, ?>) {
			return (X) deepCopyMap((Map<?, ?>) input);
		} else if (input instanceof XNC) {
			return (X) deepCopyXNC((XNC) input);
		}
		return input;
	}

	/**
	 * Create a deep copy for an XNC object
	 * @param input an XNC object
	 * @return a deep copy for an XNC object
	 */
	private static XNC deepCopyXNC(XNC input) {
		XNC clone = XNC.makeXNC(input.getRank(), input.getFile());
		return clone;
	}

	/**
	 * Create a deep copy for a map
	 * @param map the original map
	 * @return a deep copy of the map
	 */
	private static <K, V> Map<K, V> deepCopyMap(final Map<K, V> map) {
		Map<K, V> clone = new HashMap<K, V>();
		for (Map.Entry<K, V> entry : map.entrySet()) {
			clone.put(makeDeepCopy(entry.getKey()), makeDeepCopy(entry.getValue()));
		}
		return clone;
	}
}
