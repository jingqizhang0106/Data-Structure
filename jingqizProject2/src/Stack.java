/**
 * This is a Stack data structure which is built on a linked list with only a front pointer.
 *
 * @author Mia Zhang
 */
public class Stack<T> {
    private ListNode front;
    private int size;

    public Stack() {
        front = null;
        size = 0;
    }

    // push any type of object into the stack
    public void push(T object) {
        if (front == null) {
            front = new ListNode(object);
        } else {
            ListNode newHead = new ListNode(object);
            newHead.next = front;
            front = newHead;
        }
        size++;
    }

    // poll the first object from the stack
    public T poll() {
        if (front == null) {
            return null;
        }
        ListNode head = front;
        front = front.next;
        size--;
        return (T) head.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
