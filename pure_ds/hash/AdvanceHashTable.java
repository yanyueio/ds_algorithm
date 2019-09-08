package hash;

import java.util.TreeMap;

public class AdvanceHashTable<K extends Comparable<K>, V> {
    private TreeMap<K, V>[] table;
    private int M; //一个素数，其实也是 buckets 的目录(数组长度)
    private int size; //元素总个数

    private final int[] capacity
            = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
            49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
            12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

    //容量因子
    private static final int up_factor = 10; //表示 10 倍于 M，即数组长度
    private static final int low_factor = 2;
    private static int init_capacity_index = 0; //素数表中选择容量

    //构造器 (数组的初始长度从 capacity 数组中查找)
    public AdvanceHashTable() {
        this.M = capacity[init_capacity_index];
        size = 0;
        table = new TreeMap[M]; //数组长度也是 M
        //数组的元素也要初始化
        for(int i = 0; i < M; i++) {
            table[i] = new TreeMap<>();
        }
    }


    //Hashtable的 hash函数 (要直接拿到存储索引的，而不是只求出hash值)
    private int hash(K key) {
        // 0x7fffffff 相当于去掉整型的符号位
        //因为 java 本身实现的 hashCode 本身可能为负值(这里运算后最高位一定是0，即正数)
        //不用与运算，用 Math.abs 也是可以的，但效率不高
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize() {
        return size;
    }

    //添加
    public void add(K key, V value) {
        //先要看一下是否存在 (不存在才存储；否则是修改)
        TreeMap<K, V> map = table[hash(key)];
        map.put(key, value);
        if(!map.containsKey(key)) {
            //新添加
            size++;

            //添加完毕之后，要检查是否需要扩容
            if(size >= up_factor * M && init_capacity_index + 1 < capacity.length) {
                resize(capacity[++init_capacity_index]);
            }
        }
    }

    private void resize(int newCapacity) {
        //创建一个新的数组
        TreeMap<K, V>[] newHashTable = new TreeMap[newCapacity];
        //初始化每个 bucket 的查找表(TreeMap)
        for(int i = 0; i < newCapacity; i++) {
            newHashTable[i] = new TreeMap<>();
        }

        //然后遍历旧的数组，放入新的数组
        int oldCapacity = M;
        this.M = newCapacity;
        for(int i = 0; i < oldCapacity; i++) {
            //拿到当前的查找表
            TreeMap<K, V> map = table[i];
            TreeMap<K, V> newMap;
            //遍历这个查找表，放入新容器
            for(K key: map.keySet()) {
                newMap = newHashTable[hash(key)];
                newMap.put(key, map.get(key)); //放入的新 map
            }
        }
        this.table = newHashTable;
    }

    //删除
    public V remove(K key) {
        TreeMap<K, V> map = table[hash(key)];

        V ret = null;
        //在相应的 map 中查找
        if(map.containsKey(key)) {
            ret = map.remove(key);
            //删除之后要维护 size
            size--;

            if(size < low_factor * M && init_capacity_index - 1 >=0 ) {
                init_capacity_index--;
                resize(capacity[init_capacity_index]);
            }
        }
        return ret;
    }

    //修改
    public void set(K key, V value) {
        TreeMap<K, V> map = table[hash(key)];
        if(!map.containsKey(key)) {
            throw new IllegalArgumentException(key + "不存在");
        }
        map.put(key, value);
    }

    //查询
    public boolean contains(K key) {
        //TreeMap<K, V> map = table[hash(key)];
        return table[hash(key)].containsKey(key);
    }

    public V get(K key) {
        return table[hash(key)].get(key);
    }

    public static void main(String[] args) {
        AdvanceHashTable<String, Integer> hashTable = new AdvanceHashTable<>();
        hashTable.add("nihao", 1);
        hashTable.add("hello", 2);

        System.out.println(hashTable.contains("nihao")); //true
        hashTable.set("hello", 3);
        System.out.println(hashTable.get("hello")); //3
    }
}
