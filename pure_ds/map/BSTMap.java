package map;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {
    //定义 Node
    private class Node {
        public K key;
        public V value;

        public Node left, right;

        //构造函数
        public Node(K key, V value) {
            this.key = key;
            this.value = value;

            left = right = null;
        }
    }

    //定义成员
    private Node root;
    private int size;


    //定义构造器
    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // 其他函数和 BST 的实现保持一致

    @Override
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    //返回操作后的子树 (根节点)
    private Node add(Node root, K key, V value) {
        if(root == null) {
            //找到了相应插入的位置，那么返回 (上层调用会接收这个子树)
            size++;
            return new Node(key, value);
        }

        //找到相应需要插入的位置
        if(key.compareTo(root.key) < 0) {
            //左子树上递归查找相关位置
            root.left = add(root.left, key, value);
        } else if(key.compareTo(root.key) > 0) {
            //右子树上递归查找需要插入的位置
            root.right = add(root.right, key, value);
        } else {
            //已经存在了？抛异常，还是更新
            throw new IllegalArgumentException("要添加的 Key 已经存在了");
        }
        return root; //返回操作完毕后的子树给上级 (这棵子树的 right 或者 left 已经添加了新元素)
    }


    //查询方法，一般需要借助，找到该节点的 私有方法
    //返回 key 所在的节点
    private Node getNode(Node root, K key) {
        //以当前节点作为 root 开始查询
        //还是用递归的写法
        if(root == null) {
            // 没有找到
            return null;
        }
        if(key.compareTo(root.key) == 0) {
            //找到了
            return root;
        } else if (key.compareTo(root.key) < 0) {
            //在左子树上去找
            return getNode(root.left, key);//返回从 root.left 这颗子树上的节点
        } else {
            return getNode(root.right, key);
        }
    }



    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node != null ? node.value : null;
    }


    @Override
    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if(node != null) {
            //存在，就更新
            node.value = newValue;
        } else {
            throw new IllegalArgumentException("要更新的 Key 不存在");
        }

    }

    //删除操作比较复杂 (这边需要使用融合技术，即找前驱或者后继元素)
    //先写4个辅助函数 (找前驱的 getMax, 找后继的 getMin )
    // 删除 max 并返回相应节点的 removeMax 或者 删除 min 并返回相应节点的 removeMin
    private Node getMin(Node root) {
        if(root.left == null) {
            return root;
        }
        //其他情况一直在左子树上查找
        return getMin(root.left);
    }

    //删除最小元素，然后返回这个子树 (根节点)
    private Node removeMin(Node root) {
        //最小元素一定在左子树上，让 root 的左子树接收即可
        if(root.left == null) {
            //左子树空了，这个时候需要把右子树嫁接到父节点上 (也就是返回给上级调用的 left)
            //此时最小值就是当前这个节点 root
            Node rightNode = root.right; //可能为空
            root.right = null; //把当前这个节点置空
            size--;

            return rightNode;
        }

        //左子树不空，继续找
        root.left = removeMin(root.left);
        return root;
    }


    private Node getMax(Node root){
        if(root.right == null) {
            return root;
        }
        //否咋一直找右子树
        return getMax(root.right);
    }

    //删除最大元素，然后返回这个子树 (根节点)
    private Node removeMax(Node root) {
        if(root.right == null) {
            //此时 root 就是最大节点了
            //把左子树嫁接到父节点吧 (即返回给上层调用)
            Node leftNode = root.left; //可能为 null，但返回给上层调用的 right
            root.left = null;
            size--;
            return leftNode;
        }

        //否则接续找
        root = root.right;

        return root;
    }

    //辅助函数写完，再来写真正的删除任意 key 的情况
    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if(node != null) {
            //存在采取删除
            root = remove(key, root);
            return node.value;
        }

        return null; //不存在，则删除不了，应该抛异常的，这里就返回 null 算了
    }

    //返回操作完毕的相关子树 (根节点)
    private Node remove(K key, Node root) {
        //要操作的子树为空的时候，表明已经到了树的叶子下了
        if(root == null) {
            return null;
        }
        //其他情况，则递归的在 相关左右子树上进行相关删除操作 (返回操作后的子树)
        if(key.compareTo(root.key) < 0) {
            //左子树上删除，然后子树给 root.left
            root.left = remove(key, root.left);
        } else if(key.compareTo(root.key) > 0) {
            //右子树上删除，然后返回结果给 root.right
            root.right = remove(key, root.right);
        } else {
            //找了要删除的节点 compare 相等的情况
            // 这里还是要分情况处理一下: 左子树为空或者右子树为空，嫁接另一半子树
            //如果左右子树都不为空，那么久需要处理融合问题

            //简单的情况: 有一边子树空的情况
            if(root.left == null) {
                //嫁接右子树部分即可 (意思就是返回给上一级，自然有递归接收)
                Node rightNode = root.right;
                root.right = null;
                size--;
                return rightNode;
            }

            if(root.right == null) {
                //嫁接左子树部分即可
                Node leftNode = root.left;
                root.left = null;
                size--;
                return leftNode;
            }

            //先找后继，即右子树上查找最接近的节点 (右子树上查找最小)
            Node subcessorNode = getMin(root.right); //替代当前节点
            subcessorNode.right = removeMin(root.right); //返回右子树操作后的子树 (根节点)
            subcessorNode.left = root.left;

            //置空这个要删除的节点
            root.left = root.right = null;
            return subcessorNode;

        }
        return root;
    }

    private void inOrder(Node root) {
        //实现一个中序遍历方法
        if(root == null) {
            //以 root 为根的这颗子树空的, 不必打印直接返回
            return;
        }
        inOrder(root.left);
        System.out.print(root.key + ":" + root.value + " ");
        inOrder(root.right);
    }

    @Override
    public String toString() {
        inOrder(root);
        System.out.println();
        return super.toString();
    }

    public static void main(String[] args) {
        BSTMap<Integer, String> map = new BSTMap<>();
        map.add(2, "two");
        map.add(1, "one");
        map.add(3, "three");
        map.add(5, "five");

        System.out.println(map.getSize());
        System.out.println(map.contains(3));
        System.out.println(map);
    }
}
