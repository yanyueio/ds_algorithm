package stack;

import array.AdvanceDynamicArray;

public class ArrayStack<E> implements Stack<E> {
    //底层实现是动态数组，所以内部直接引用动态数组就好了
    AdvanceDynamicArray<E> array;

    public ArrayStack(int capacity) {
        array = new AdvanceDynamicArray<>(capacity);
    }

    public ArrayStack() {
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
    public E pop() {
        return array.pop();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public void push(E e) {
        array.append(e);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Stack [");
        for (int i = 0; i < array.getSize(); i++) {
            res.append(array.get(i));
            if (i != array.getSize() - 1) {
                res.append(", ");
            }
        }
        res.append("]，top right");
        return res.toString();
    }
}
