package maxheap;

import array.AdvanceDynamicArray;

import java.util.Random;

public class MaxHeap<E extends Comparable<E>> {

    //用动态数组进行实现
    private AdvanceDynamicArray<E> data;

    //构造函数
    public MaxHeap(int capacity) {
        data = new AdvanceDynamicArray<>(capacity);
    }

    public MaxHeap() {
        data = new AdvanceDynamicArray<>();
    }


    public MaxHeap(E[] arr) {
        data = new AdvanceDynamicArray<>(arr);
        //把数组折腾成大顶堆
        for (int i = parent(getSize() - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    //2个简单的方法
    public int getSize() {
        return data.getSize();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    //三个辅助函数，根据索引计算父，子存储位置索引
    private int parent(int index) {
        if (index == 0) {
            //根索引没有父亲
            throw new IllegalArgumentException("index 0 没有父亲节点");
        }
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    //存取元素
    //外部的 add，对于堆来说就是 sift up (上浮)
    public void add(E e) {
        data.append(e); //先向数组末尾添加元素

        //维护上浮 (队数组进行交换)
        siftUp(data.getSize() - 1);
    }

    private void siftUp(int index) {
        //给定 index 的元素不断和父节点比较
        while (index > 0 && data.get(parent(index)).compareTo(data.get(index)) < 0) {
            //父节点比子节点小，交换 (上浮)
            data.swap(parent(index), index);

            //然后再向上找
            index = parent(index);
        }
    }

    public E findMax() {
        if (isEmpty()) {
            throw new IllegalArgumentException("这是个空堆");
        }
        return data.get(0);
    }

    public E extractMax() {
        E ret = findMax();
        data.swap(0, getSize() - 1);
        data.pop(); //删除末尾的元素
        siftDown(0);
        return ret;
    }

    private void siftDown(int index) {

        //叶子节点时不用再交换了，或者，当前节点还是小于 max(其子节点)
        //某个节点的左孩子都超过最大索引 (数组越界了，说明其就是叶子节点了)
        while (leftChild(index) < getSize()) {
            //找到孩子中的大的一个

            int maxIndex = leftChild(index); //默认先认为左变大
            //如果右孩子存在，那就和右边比一下
            int rightIndex = rightChild(index);
            if (rightIndex < getSize() && data.get(rightIndex).compareTo(data.get(maxIndex)) > 0) {
                //说明确实右边大
                maxIndex = rightIndex;
            }

            //此时 maxIndex 就代表了孩子中大的一方的索引
            if (data.get(index).compareTo(data.get(maxIndex)) >= 0) {
                break; //不用比了，已经是大顶堆了
            }
            data.swap(index, maxIndex);
            index = maxIndex; //接着进行下一轮比较
        }
    }


    //用新元素直接替代 top 元素，而不是先删后添加
    public E replace(E e) {
        E ret = findMax();
        data.set(0, e); //替换堆顶为新元素
        siftDown(0); // O(logn)
        return ret;
    }


    public static void main(String[] args) {
        int n = 1000000; //100万

        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        //放入堆中 (需要不断的 sift up)
        for (int i = 0; i < n; i++) {
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));
        }

        //然后取出来放入 arr 中
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = maxHeap.extractMax();
        }

        //检查一下这个 arr 是否是降序的
        for (int i = 1; i < n; i++) {
            if (arr[i - 1] < arr[i]) {
                //说明不是降序的，堆实现有问题
                throw new IllegalArgumentException("Error");
            }
        }
        //全部检查完毕还没有异常，就说明 OK
        System.out.println("OK");
    }


}
