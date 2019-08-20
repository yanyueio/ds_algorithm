import array.AdvanceArray;
import array.AdvanceDynamicArray;
import array.MyArray;
import array.Student;



public class Main {

    private static void test_1() {
        MyArray arr = new MyArray(20); //容量20
        //放入 10 个元素
        for (int i = 0; i < 10; i++) {
            arr.append(i);
        }
        System.out.println(arr);

        //插入试试
        arr.insert(1, 100);
        System.out.println(arr);

        //[0, 100, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        //删除试试
        arr.remove(1);
        System.out.println(arr); //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        //删除末尾试试
        arr.pop();
        System.out.println(arr); //[0, 1, 2, 3, 4, 5, 6, 7, 8]

        //删除头部试试
        arr.popLeft();
        System.out.println(arr); //[1, 2, 3, 4, 5, 6, 7, 8]

        //删除 4 这个数字
        arr.removeElem(4);
        System.out.println(arr); //[1, 2, 3, 5, 6, 7, 8]
    }

    private static void test_2() {
        //泛型不支持基本类型，所以这里写 Integer；使用时会自动box，unbox
        AdvanceArray<Integer> arr = new AdvanceArray<>(20); //容量20
        //放入 10 个元素
        for (int i = 0; i < 10; i++) {
            arr.append(i);
        }
        System.out.println(arr);

        //插入试试
        arr.insert(1, 100);
        System.out.println(arr);

        //[0, 100, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        //删除试试
        arr.remove(1);
        System.out.println(arr); //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        //删除末尾试试
        arr.pop();
        System.out.println(arr); //[0, 1, 2, 3, 4, 5, 6, 7, 8]

        //删除头部试试
        arr.popLeft();
        System.out.println(arr); //[1, 2, 3, 4, 5, 6, 7, 8]

        //删除 4 这个数字
        arr.removeElem(4);
        System.out.println(arr); //[1, 2, 3, 5, 6, 7, 8]
    }

    private static void test_3() {
        AdvanceArray<Student> arr = new AdvanceArray<>(); //默认容量是 10
        //添加测试数据
        arr.append(new Student("AAA", 100));
        arr.append(new Student("BBB", 90));
        arr.append(new Student("CCC", 60));

        System.out.println(arr);
    }

    private static void test_4() {
        //初始容量为 10，先初始化 10 个元素
        AdvanceDynamicArray<Integer> arr = new AdvanceDynamicArray<>(); //初始容量默认为 10
        for (int i = 0; i < 10; i++) {
            arr.append(i);
        }
        System.out.println(arr);

        //再添加一个元素，试试
        arr.presert(100);
        System.out.println(arr);

    }

    private static void test_5() {
        //初始容量为 10，先初始化 10 个元素
        AdvanceDynamicArray<Integer> arr = new AdvanceDynamicArray<>(); //初始容量默认为 10
        for (int i = 0; i < 10; i++) {
            arr.append(i);
        }
        System.out.println(arr);

        //再添加一个元素，试试 -- 此时容量变为 20，实际占用 11
        arr.presert(100);
        System.out.println(arr);

        //pop一个，容量立马缩减为 10 --- 延迟缩减的情况则不变
        arr.pop();
        System.out.println(arr);
    }


    //测试一下栈
    private static void test_stack_1() {
        ArrayStack<Integer> stack = new ArrayStack<>(); //默认内部动态数组容量 10
        //推入 5 个元素
        for(int i=0; i< 5; i++){
            stack.push(i);
            System.out.println(stack); //每次入栈，打印一次
        }
        System.out.println("---------");
        stack.pop();
        System.out.println(stack);
    }

    public static void main(String[] args) {
        /*
        test_1();
        test_2();
        test_3();
        test_4(); //测试扩容
        test_5(); //测试缩减
        */
        test_1();
    }
}
