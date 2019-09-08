package hash;

import java.util.TreeMap;

public class HashTable<K extends Comparable<K>, V> {
    private TreeMap<K, V>[] table;
    private int M; //一个素数，其实也是 buckets 的目录(数组长度)
    private int size; //元素总个数

    //指定数组长度的构造器
    public HashTable(int M) {
        this.M = M;
        size = 0;
        table = new TreeMap[M]; //数组长度也是 M
        //数组的元素也要初始化
        for(int i = 0; i < M; i++) {
            table[i] = new TreeMap<>();
        }
    }

    //默认数组长度 97，当然也可以是其他素数
    public HashTable() {
        this(97);
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

    public void add(K key, V value) {
        //先要看一下是否存在 (不存在才存储；否则是修改)
        TreeMap<K, V> map = table[hash(key)];
        map.put(key, value);
        if(!map.containsKey(key)) {
            //新添加
            size++;
        }
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
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.add("nihao", 1);
        hashTable.add("hello", 2);

        System.out.println(hashTable.contains("nihao")); //true
        hashTable.set("hello", 3);
        System.out.println(hashTable.get("hello")); //3
    }
}
