/**
 * This is a Queue data structure which is built on a linked list using front and rear pointers.
 *
 * @author Mia Zhang
 */
public class Queue<T> {

    private ListNode front;
    private ListNode rear;
    private int size;

    public Queue() {
        front = null;
        rear = null;
        size = 0;
    }

    // add any type of object at the end of the queue
    public void enqueue(T object) {
        if (front == null) {
            front = new ListNode(object);
            rear = front;
        } else {
            rear.next = new ListNode(object);
            rear = rear.next;
        }
        size++;
    }

    // remove first object from the queue front
    public T dequeue() {
        Object object;
        if (front == null) {
            return null;
        } else {
            object = front.data;
            front = front.next;
        }
        size--;
        return (T) object;

    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

