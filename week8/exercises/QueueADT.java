public interface QueueADT {

    void enqueue(int element);

    int dequeue();

    boolean isEmpty();

    int size();
}

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
    }
}
