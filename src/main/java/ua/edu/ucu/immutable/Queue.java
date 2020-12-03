package ua.edu.ucu.immutable;

public class Queue {
    private ImmutableLinkedList queue;
    private int size;

    public Queue() {
        this.queue = new ImmutableLinkedList();
        this.size = 0;
    }

    public Queue(Object[] objects) {
        this.queue = new ImmutableLinkedList(objects);
    }

    public Object peek() {
        return queue.getFirst();
    }

    public Object dequeue() {
        Object ans = this.peek();
        queue = queue.removeFirst();
        this.size--;
        return ans;
    }

    public void enqueue(Object e) {
        this.size++;
        queue = queue.add(e);
    }

    public String toString() {
        return queue.toString();
    }

    public int getSize() {
        return this.size;
    }
}
