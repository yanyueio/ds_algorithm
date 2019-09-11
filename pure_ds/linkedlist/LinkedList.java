package linkedlist;

//不适用虚拟头结点的情况
public class LinkedList<E> {

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

    //操作链表的辅助变量
    private int size;
    private Node head; //头结点，就是索引为0位置的节点

    //构造函数
    public LinkedList() {
        head = null;
        size = 0;
    }


    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(E e) {
        /*
        Node node = new Node(e);
        node.next = head;
        head = node;
        */

        //简写
        head.next = new Node(e, head);

        //维护链表长度
        size++;
    }


    //指定的 index 位置添加元素 (先要找到 index 前一个位置)
    // index 从 0 ~ size-1
    public void add(int index, E e) {
        // 索引有问题
        if (index < 0 || index > size) { //当 index == size 时，表示在末尾添加
            throw new IllegalArgumentException("Add Failed, Illegal index");
        }
        if (index == 0) {
            addFirst(e);
        } else {
            Node prev = head;
            //找到指定位置前一个节点
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            //创建一个新节点
            /*Node node = new Node(e);
            node.next = prev.next;
            prev.next = node;*/

            //简写
            prev = new Node(e, prev.next);
            size++;
        }
    }

    //在末尾添加元素
    public void addLast(E e){
        add(size, e);
    }


}
