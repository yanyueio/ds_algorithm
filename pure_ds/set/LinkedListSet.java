package set;

import linkedlist.LinkedList1;

public class LinkedListSet<E> implements Set<E> {

    private LinkedList1<E> list;

    public LinkedListSet() {
        list = new LinkedList1<>();
    }

    @Override
    public void add(E e) {
        //不存在才添加
        if (!list.contains(e)) {
            list.addFirst(e); //O(1)，因为有头指针
        }
    }

    @Override
    public void remove(E e) {
        list.removeElem(e);
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("{ ");
        res.append(list.toString());
        res.append("} ");
        return res.toString();
    }

    public static void main(String[] args) {
        LinkedListSet<Integer> set = new LinkedListSet<>();

        //添加一些元素 2, 3, 2, 5
        set.add(2);
        set.add(3);
        set.add(2);
        set.add(5);
        set.add(5);
        System.out.println(set); //{ 5->3->2->null}
    }
}
