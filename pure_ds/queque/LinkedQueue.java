package queque;

//内部采用链式存储的队列
public class LinkedQueue<E> implements Queue<E> {

    //定义一个内部类，作为节点类
    private class Node {
        public E e;
        public Node next; //便于 LinkedList 访问

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node head, tail;
    private int size;

    //构造器
    public LinkedQueue() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public E dequeue() {
        //出队操作，在队首
        //没有元素肯定就不能出队
        if (isEmpty()) {
            //或者 head = null
            throw new IllegalArgumentException("Cannot dequeue from an empty queue");
        }
        //正常出队，提取 head
        Node retNode = head; //tail，考虑只有一个元素的队列
        head = retNode.next;
        retNode.next = null;

        //仅在只有一个元素的队列，需要维护 tail
        if (head == null) {
            tail = null;
        }

        size--;
        return retNode.e;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            //或者 head = null
            throw new IllegalArgumentException("Cannot dequeue from an empty queue");
        }
        return head.e; // 返回队首即可
    }

    @Override
    public void enqueue(E e) {
        //入队操作，在尾部操作
        if (tail == null) { //说明此时队列是空的，即 tail 和 head 都为空
            tail = new Node(e);
            head = tail;
        } else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue: front[ ");
        for(Node curr = head; curr != null; curr = curr.next){
            res.append(curr.e + "->");
        }
        res.append("null ] tail");
        return res.toString();
    }

    public static void main(String[] args) {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        //存储  11 个元素看看
        for(int i=0; i<11; i++){
            queue.enqueue(i);
            System.out.println(queue); // 在 10 个元素满的时候回扩容
        }
        //出队试试
        System.out.println("------出队");
        queue.dequeue();
        System.out.println(queue);
    }
}
