package stack;

public interface Stack<E> {
    //接口中声明相关方法即可
    boolean isEmpty();
    int getSize();

    E pop();
    E peek();
    void push(E e);

}
