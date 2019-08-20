package queque;

import java.util.Objects;

public class LoopQueue<E> implements Queue<E> {
    //内部自己维护一个数组
    private E[] data;
    private int front, tail; //front 指向头，tail 指向队尾的下一个元素
    private int size; //其实可以用通过 front, tail 实现，但复杂，容易出错


    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1]; //因为要故意浪费一个空间
        front = tail = 0;
        size = 0;
    }

    public LoopQueue() {
        data = (E[]) new Object[10 + 1]; //因为要故意浪费一个空间，默认存储10个元素
        front = tail = 0;
        size = 0;
    }

    //外部能感知的实际能存储的 capacity
    public int getCapacity() {
        return data.length - 1; //注意是 data.length 少一个
    }

    //快捷方法，判断队列满 -- 用户不用关心，client始终可以放入 (因为会动态扩容)
    private boolean isFull() {
        //return (tail+1)%getCapacity() == front;
        return (tail + 1) % data.length == front; //判断队列满，索引移动，用实际的 data.length 判断
    }

    @Override
    public boolean isEmpty() {
        //return size == 0;
        return front == tail; //特别注意队列为空的条件
    }

    @Override
    public int getSize() {
        return size; //专门有一个变量维护
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("队列为空，不能出队");
        }
        return data[front];
    }

    @Override
    public E dequeue() {
        //先看看是否为空
        if (isEmpty()) {
            throw new IllegalArgumentException("队列为空，不能出队");
        }

        E ret = data[front];
        //最好还是把 data[front]  处理一下
        data[front] = null;
        front = (front + 1) % data.length;
        size--;

        //缩减容量 -- lazy 缩减，当实际存储为 1/4 capacity时，capacity缩减为一半
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2); //缩减后的容量不能为0
        }

        return ret;
    }

    @Override
    public void enqueue(E e) {
        //添加之前，先要看看队列是否是满的
        if (isFull()) {
            //抛出异常 or 动态扩容(包括移动元素)
            resize(2 * getCapacity()); //当前实际占用空间*2
        }

        //入队列
        //data[tail++] = e; // tail++ 可能超过了 data.length
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }

    private void resize(int newCapacity) {
        //改变容量，然后移动元素，重置索引
        E[] newData = (E[]) new Object[newCapacity + 1];

        //复制: 把旧的元素，放入新的数组
        //新数组的索引是从 0 -> size 的
        for (int i = 0; i < size; i++) {
            //newData[i] = data[?];
            newData[i] = data[(front + i) % data.length]; //索引移动，用的是data.length 判断
        }

        //重置索引
        front = 0;
        tail = size; //实际个数是不变的
        data = newData; //data.length 变化了，所以 getCapacity() 自然也变了
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size=%d, capacity=%d\n", size, getCapacity()));

        res.append("front [");
        for (int i = front; i != tail; i = (i+1)%data.length) {
            res.append(data[i]);
            if ((i+1)%data.length != tail) { //不是最后一个元素之前的一个元素
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args) {
        LoopQueue<Integer> queue = new LoopQueue<>(); //默认实际存储 10 个元素
        //存储  11 个元素看看
        for(int i=0; i<11; i++){
            queue.enqueue(i);
            System.out.println(queue); // 在 10 个元素满的时候回扩容
        }
        //出队试试
        System.out.println("------");
        queue.dequeue();
        System.out.println(queue);
        //出队到只剩 5 个元素，即 20/4 时，缩减容量
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        // 6, 7, 8, 9 10
        System.out.println(queue); //此时容量变为 10 了
    }
}
