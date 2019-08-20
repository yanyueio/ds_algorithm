package queque;

public interface Queue<E> {
    boolean isEmpty();
    int getSize();

    E dequeue();
    E getFront();

    void enqueue(E e);
}
