package map;

public class LinkedListMap<K, V> implements Map<K, V>{
    //先重新实现 节点内部类
    private class Node {
        public K key;
        public V value;

        public Node next;

        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key, V value) {
            this(key, value, null);
        }

        public Node() {
            this(null, null, null);
        }

        @Override
        public String toString() {
            return key.toString() + ":" + value.toString();
        }
    }

    //成员 (和单链表一样)
    private int size;
    private Node dummyHead;

    public LinkedListMap() {
        dummyHead = new Node(); //用户并不清楚 dummyNode 的存在
        size = 0;
    }

    //私有函数 (拿到 key 所对应的 Node)
    // contains 要用到
    // 拿到 key 所对应的 value
    private Node getNode(K key) {
        //遍历，返回 key 所对应的 Node
        Node cur = dummyHead.next;
        while(cur != null) {
            if(cur.key.equals(key)) {
                return cur;
            } else {
                cur = cur.next;
            }
        }
        return null;
    }


    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void add(K key, V value) {
        //添加新的节点 (key 必须唯一)
        if(!contains(key)) {
            //直接在链表头部添加
            dummyHead.next = new Node(key, value, dummyHead.next);

            //特别注意: size++
            size++;

        } else {
            //存在了就抛出异常 (你也可以去更新)
            throw new IllegalArgumentException("要新增的 Key 已经存在了");
        }
    }


    @Override
    public void set(K key, V newValue) {
        //找到 key 然后更新
        Node node = getNode(key);
        if(node != null) {
            node.value = newValue;
        } else {
            //要更新的 key 不存在，抛出异常
            throw new IllegalArgumentException("要更新的 Key 不已经");
        }

    }

    @Override
    public V remove(K key) {
        //类似单链表里面删除 elem 逻辑
        //从 dummyHead 开始找到相应节点的前一个节点
        Node prev = dummyHead; //这里的 prev 其实代表的是找到的节点前一个节点
        while(prev.next != null) {
            if(prev.next.key.equals(key)) {
                break;
            }
            prev = prev.next;
        }

        //找到了 break 的，还是自然结束的?
        if(prev.next != null) {
            //表明是找到的，break出来的
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
            return delNode.value;
        }

        //自然结束的，说明没有找到要删除的元素
        return null;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("{");

        for(Node curr = dummyHead.next; curr != null; curr = curr.next) {
            res.append(curr.key + ":\"" + curr.value + "\"");
            if(curr.next != null) {
                res.append(", ");
            }
        }

        res.append("}");
        return res.toString();
    }

    public static void main(String[] args) {
        Map<Integer, String> map = new LinkedListMap<>();

        //放入一些元素
        map.add(1, "one");
        map.add(2, "two");
        map.add(3, "three");
        System.out.println(map); //{3:"three", 2:"two", 1:"one"}，和添加顺序一致

        System.out.println(map.contains(3)); //true
        System.out.println(map.getSize()); //3
        System.out.println(map.get(1)); //one
    }
}
