public class Main {

    public static void main(String[] args) {
        QueueADT queue = new ArrayListQueue();
        queue.enqueue(10);
        queue.enqueue(20);
        System.out.println(queue.dequeue());
    }
}
