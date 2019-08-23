package maxheap;

import queque.Queue;

public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {

    //内部成员
    private MaxHeap<E> maxHeap;


    //构造函数
    public PriorityQueue() {
        maxHeap = new MaxHeap<>();
    }

    @Override
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }

    @Override
    public int getSize() {
        return maxHeap.getSize();
    }

    @Override
    public E dequeue() {
        return maxHeap.extractMax(); // 已经对空的情况做了处理
    }

    @Override
    public E getFront() {
        //获取优先级最大的元素
        return maxHeap.findMax(); //已经对空的情况作了处理
    }

    @Override
    public void enqueue(E o) {
        maxHeap.add(o);
    }
}
