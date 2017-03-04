/**
 * 
 */
package xiangqi.studenthbnguyen.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

/**
 * @author huyennguyen
 *
 */
public class FixedSizeQueue<T> {
	private int capacity;
	private Queue<T> queue;
	private int size = 0;
	
	public FixedSizeQueue(int capacity) {
		this.capacity = capacity;
		this.queue = new LinkedList<T>();
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void add(T element) {
		if (size == capacity) {
			queue.poll();
		}
		queue.add(element);
		size++;
	}
	
	public T get(int index) {
		if (index > capacity - 1 || index > size - 1 || index < 0)
			return null;
		Iterator<T> iterator = queue.iterator();
		int indexCount = 0;
		while (iterator.hasNext() && indexCount != index) {
			indexCount++;
			iterator.next();
		}
		return iterator.next();
	}
	
	public int getSize() {
		return size;
	}
}
