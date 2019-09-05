package trie;

import java.util.TreeMap;

//默认存储的是英文语种的环境，用 Character 而没有用泛型
public class Trie {
    private class Node {
        public boolean isWord;
        public TreeMap<Character, Node> next; //使用 Character 而没有用泛型

        //构造器
        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>(); //仅创建一个新 map
        }

        //默认构造器
        public Node() {
            this(false);
        }
    }

    //Trie 的基本属性
    private int size; //内部有多少单词 (从上往下的分支路径)
    private Node root; //根节点不存储字符

    //Trie构造器
    public Trie() {
        root = new Node();
        size = 0;
    }

    //获取单词总的数量
    public int getSize() {
        return this.size;
    }

    //添加一个新的单词 (字符串)
    //非递归写法
    public void add(String newWord) {
        //把当前单词拆了，一个个字符往树上追加
        Node cur = root;
        for(int i = 0; i < newWord.length(); i++) {
            char c = newWord.charAt(i);
            if(cur.next.get(c) == null) {
                //不已经存在时，需要添加一个节点
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        //此时不管是不是叶子，都是单词末尾
        //cur.isWord = true;
        //考虑到对 size 的维护问题，即仅在之前没有添加过此单词时 size++
        if(!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }


    //查询操纵
    //非递归写法
    public boolean contains(String word) {
        Node cur = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        //return true; //还要确定这个尾节点就是单词结尾
        return cur.isWord;
    }

    //前缀查询 (只要有单词以此为前缀，那么就返回 True)
    public boolean isPrefix(String prefix) {
        //基本类似 contains，而不用检查 isWord
        //单词本身也认为是前缀
        Node cur = root;
        for(int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }


    public static void main(String[] args) {
        //存储相关单词
        String[] strs = {"pan", "panda", "hello", "world", "hi"};
        Trie trie = new Trie();
        //放入 Trie 集合中
        for(String word:strs) {
            trie.add(word);
        }
        //然后随机检查一下
        System.out.println(trie.contains("hi"));
        System.out.println(trie.contains("hello"));
        System.out.println(trie.contains("world"));
        System.out.println(trie.contains("more"));
    }


}
