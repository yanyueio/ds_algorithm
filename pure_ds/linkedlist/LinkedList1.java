package linkedlist;

//在 LinkedList 的基础上，引入 dummyHead 虚拟头结点
public class LinkedList1<E> {


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
    private Node dummyHead; //此时表示虚拟头结点

    //构造函数
    public LinkedList1() {
        dummyHead = new Node(null, null);
        size = 0;
    }


    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    //指定的 index 位置添加元素 (先要找到 index 前一个位置)
    // index 从 0 ~ size-1
    public void add(int index, E e) {
        // 索引有问题
        if (index < 0 || index > size) { //当 index == size 时，表示在末尾添加
            throw new IllegalArgumentException("Add Failed, Illegal index");
        }

        //因为在实际 index 取值范围内，总能找到相关节点的前一个节点
        Node prev = dummyHead;
        //找 index 之前的节点
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        prev.next = new Node(e, prev.next);
        size++;
    }

    //在末尾添加元素
    public void addLast(E e) {
        add(size, e);
    }

    //获取某元素
    public E get(int index) {
        //先检查索引的合法性
        if (index < 0 || index > size - 1) {
            throw new IllegalArgumentException("Get Failed, Illegal index");
        }

        // 和前面找 index 节点前一个节点不同(那里是从第一个节点前面的虚拟节点开始)
        // 这里就要找 index 节点，索引从 dummyHead.next 开始，即真正的第一个节点开始
        Node ret = dummyHead.next;
        for (int i = 0; i < index; i++) {
            ret = ret.next;
        }
        return ret.e;
    }

    //获取第一个
    public E getFirst() {
        return get(0);
    }

    //获取最后一个
    public E getLast() {
        return get(size - 1);
    }


    //更新节点元素
    public void set(int index, E e) {
        //先检查索引的合法性
        if (index < 0 || index > size - 1) {
            throw new IllegalArgumentException("Set Failed, Illegal index");
        }
        //找到节点，然后替换里面的元素
        Node curr = dummyHead.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        curr.e = e;
    }

    //查找元素
    public boolean contains(E e) {
        Boolean ret = false;
        //在 size 范围内遍历查找
        Node curr = dummyHead.next;
        /*for(int i=0; i<size; i++){
            if(curr.e.equals(e)){
                ret = true;
                break;
            }
            curr = curr.next;
        }*/

        //其实可以用 while 循环 (多判断一次 size 位置)
        while (curr != null) {
            //当前节点是有效节点
            if (curr.e.equals(e)) {
                ret = true;
                break;
            }
            curr = curr.next;
        }
        return ret;
    }

    //打印方法
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        //从头遍历到尾巴
        /*Node curr = dummyHead.next;
        while(curr != null) {
            res.append(curr + "->");
            curr = curr.next;
        }*/
        //简写
        for (Node curr = dummyHead.next; curr != null; curr = curr.next) {
            res.append(curr + "->");
        }
        res.append("null");
        return res.toString();
    }


    //删除元素
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IllegalArgumentException("Delete Failed, Illegal index");
        }

        //找到相关节点的前一个节点
        Node curr = dummyHead;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        Node delNode = curr.next;
        //删除
        curr.next = delNode.next;
        delNode.next = null;

        //必须维护 size
        size--;

        return delNode.e;
    }

    //删除第一个节点
    public E removeFirst() {
        return remove(0);
    }

    //删除最后一个节点
    public E removeLast() {
        return remove(size - 1);
    }

    //删除指定元素
    public void removeElem(E e) {
        //从 dummyHead 开始找，找到就删除，否则就不删除
        Node curr = dummyHead;
        boolean found = false;
        while (curr.next != null) {
            if (curr.next.e.equals(e)) {
                found = true;
                //删除操作
                Node delNode = curr.next;
                curr.next = delNode.next;
                delNode.next = null;
                size--;
                break;
            }
            curr = curr.next;
        }

        if (!found) {
            throw new RuntimeException("要删除的元素不存在");
        }
    }


    //测试元素
    public static void main(String[] args) {
        LinkedList1<Integer> linkedlist = new LinkedList1<>();
        //放入元素 0, 1, 2, 3, 4
        for (int i = 0; i < 5; i++) {
            linkedlist.addFirst(i); //O(1)
            System.out.println(linkedlist);
        }

        System.out.println(linkedlist);

        //尝试插入一个元素
        linkedlist.add(1, 100); // 4, 100, 2, 3, 1, 0, null
        System.out.println(linkedlist);


        //尝试删除 index = 1 位置的 100
        linkedlist.remove(1);
        System.out.println(linkedlist); //4->3->2->1->0->null

        //删除最后一个元素 0
        linkedlist.removeLast();
        System.out.println(linkedlist); //4->3->2->1->null

        //删除第一个元素
        linkedlist.removeFirst();
        System.out.println(linkedlist); //3->2->1->null

        //删除指定元素
        linkedlist.removeElem(3);
        linkedlist.removeElem(1);
        //linkedlist.removeElem(null);
        System.out.println(linkedlist);
    }
}
