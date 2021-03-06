package array;

//在 Array 的基础上，加上泛型支持
public class AdvanceArray<E> {

    //先声明一下相应的变量, 不暴露给外部 (内部维护保持两者一致)
    private E[] data; //capacity 就是 data 的 length
    private int size;

    // 构造函数，传入数组容量
    public AdvanceArray(int capacity) {
        data = (E[])new Object[capacity];
        size = 0; //初始情况下，实际有 0 个元素
    }

    //提供一个默认的吧, 不需要用户手动提供 capacity, 无参
    public AdvanceArray() {
        this(10);
    }


    //获取数组元素个数
    public int getSize() {
        return size;
    }

    //返回动态数组的容量
    public int getCapacity() {
        return data.length;
    }

    //数组此刻是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //末尾添加
    public void append(E elem) {
        insert(size, elem);
    }

    //头部添加
    public void presert(E elem) {
        insert(0, elem);
    }

    //指定位置插入
    public void insert(int index, E elem) {
        //TODO: 考虑一下是否超过容量
        if (size == data.length) {
            throw new IllegalArgumentException("append failed");
        }
        //检查 index 是合法的
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("insert failed, require: index >=0 and index <= size ");
        }

        //先移动元素，从后面开始 (size-1 移动到 size --> index 移动到 index+1；腾出 index)
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = elem; //覆盖原来的 index 处的内容
        size++;
    }

    //获取数组整体，即打印时需要显示的信息
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("MyArray: size=%d, capacity=%d\n", size, data.length));

        res.append("[");
        //只遍历现有元素，而不是容量
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(", ");
            }
        }
        res.append("]");
        return res.toString();
    }

    //获取某个具体元素: 通过封装，加入自定义 index 检查(保证获取数据安全)
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed, Index is illegal");
        }
        return data[index];
    }

    //更新元素
    public void set(int index, E elem) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed, Index is illegal");
        }
        data[index] = elem;
    }

    //是否包含
    public boolean contains(E elem) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(elem)) {
                return true;
            }
        }
        return false;
    }

    //搜索元素
    public int find(E elem) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(elem)) {
                return i;
            }
        }
        return -1; //没有找到返回 -1
    }

    //删除元素 (通常要返回相应的元素)
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Remove failed, Index is illegal");
        }
        //先把要返回的值存储起来
        E ret = data[index];
        //覆盖删除: 把 index+1 的值移动到 index --> 把 size-1 的值移动到 size-2
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }

        size--;
        //data[size] 不必清空，因为用户取不到 data[size]
        //当使用泛型，对对象元素支持时
        data[size] = null; //方便 java 回收 loitering objects (非必须，闲逛对象 != memory leak)
        return ret;
    }

    //快捷删除尾部元素
    public E pop() {
        return remove(size - 1);
    }

    //快捷删除头部元素
    public E popLeft() {
        return remove(0);
    }

    //删除指定元素 (不必返回，因为用户已经知道 elem)
    public void removeElem(E elem) {
        int index = find(elem);
        if (-1 == index) {
            throw new IllegalArgumentException("Remove failed, cannot find this elem");
        }
        //成功了什么都不提示，出错才提示
        remove(index);
    }
}