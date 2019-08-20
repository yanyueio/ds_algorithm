package stack;

import linkedlist.LinkedList1;

public class LinkedListStack<E> implements Stack<E>{

    //链栈内部实际采用链表存储
    private LinkedList1<E> list;


    public LinkedListStack(){
        list = new LinkedList1<>();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public E pop() {
        return list.removeFirst();
    }

    @Override
    public E peek() {
        return list.getFirst();
    }

    @Override
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Stack: top [");
        res.append(list);
        res.append("]");
        return res.toString();
    }

    public static void main(String[] args) {
        LinkedListStack<Integer> stack = new LinkedListStack<>();
        //放入元素 0, 1, 2, 3, 4
        for(int i =0; i < 5; i++) {
            stack.push(i); //O(1)
            System.out.println(stack);
        }

        System.out.println(stack);
        System.out.println(stack.peek());

        //弹出一个元素
        stack.pop();
        System.out.println(stack);
    }
}
