package queque;

import array.AdvanceDynamicArray;


public class ArrayQueue<E> implements Queue<E> {

    private AdvanceDynamicArray<E> array;

    public ArrayQueue(int capacity) {
        array = new AdvanceDynamicArray<>(capacity);
    }

    public ArrayQueue() {
        array = new AdvanceDynamicArray<>();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public E dequeue() {
        return array.popLeft();
    }

    @Override
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public void enqueue(E e) {
        array.append(e);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue: [");
        for(int i = 0; i< array.getSize(); i++) {
            res.append(array.get(i));
            if(i != array.getSize() - 1) {
                res.append(", ");
            }
        }
        res.append("]，tail");
        return res.toString();
    }


    //测试看看
    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        //放入元素
        for(int i = 0; i< 10; i++) {
            queue.enqueue(i);
            System.out.println(queue); //放入一个元素，查看一次队列
        }

        //出栈试试
        System.out.println("--------");
        queue.dequeue();
        System.out.println(queue);
    }
}
