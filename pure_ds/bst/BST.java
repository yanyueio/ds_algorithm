package bst;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//二分搜索树
public class BST<E extends Comparable<E>> {

    //声明节点类
    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    //成员变量
    private Node root;
    private int size;

    //构造函数
    public BST() {
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    //向二分搜索树中添加元素
    public void add(E e) {
        root = add(e, root);
    }

    //返回插入新节点后BST的根 (每层调用都是如此)
    private Node add(E e, Node root) {
        if (root == null) {
            size++;
            return new Node(e);
        }
        if (e.compareTo(root.e) > 0) {
            //放在右子树
            root.right = add(e, root.right); //因为add 函数返回的就是 插入新节点后BST的根

        } else if (e.compareTo(root.e) < 0) {
            root.left = add(e, root.left);
        }        //相等的情况，啥也做

        return root;
    }

    //查询元素是否存在
    public boolean contains(E e) {
        return contains(root, e);
    }

    //以 root 为根的BST中是否存在 e
    private boolean contains(Node root, E e) {
        if (root == null) {
            return false;
        }
        if (e.compareTo(root.e) > 0) {
            //去查右子树
            return contains(root.right, e);
        } else if (e.compareTo(root.e) < 0) {
            return contains(root.left, e);
        }
        //return root.e.compareTo(e) == 0;
        return true;
    }


    //先根遍历
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root.e);
        preOrder(root.left);
        preOrder(root.right);
    }

    //中序遍历
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node root) {
        if (root == null) {
            return;
        }

        inOrder(root.left);
        System.out.println(root.e);
        inOrder(root.right);
    }

    //后序遍历
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node root) {
        if (root == null) {
            return;
        }

        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.e);
    }

    //先序遍历，非递归写法
    public void preOrderNR() {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            System.out.println(curr.e);
            if (curr.right != null) {
                stack.push(curr.right);
            }
            if (curr.left != null) {
                stack.push(curr.left);
            }
        }
    }

    //广度优先-层序遍历
    public void levelOrder() {
        Queue<Node> queue = new LinkedList<>(); //util linkedlist 实现了 queue 接口
        queue.add(root);
        while (!queue.isEmpty()) {
            Node curr = queue.remove();
            System.out.println(curr.e);
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 1, res);
        return res.toString();
    }

    //生成以 root 为根节点，深度为 i 的字符串 (基于前序遍历)
    private void generateBSTString(Node root, int depth, StringBuilder res) {
        if (root == null) {
            res.append("depth:" + depth + "，null\n");
            return;
        }
        res.append("depth:" + depth + "，" + root.e + "\n");
        generateBSTString(root.left, depth + 1, res);
        generateBSTString(root.right, depth + 1, res);
    }

    //获取树的最小值 (递归写法)
    public E getMin() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Tree is Empty");
        }
        return getMin(root).e;//调用递归的写法
    }

    private Node getMin(Node node) {
        if (node.left == null) {
            return node;
        }
        return getMin(node.left);
    }

    //树的最大值(非递归写法)
    public E getMax() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Tree is Empty");
        }
        Node curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.e;
    }

    //删除最小值 (递归写法)
    public E removeMin() {
        E ret = getMin(); //不需要 isEmpty判断了
        root = removeMin(root); //操作完毕返回新树的根
        return ret;
    }

    //删除以 node 为根的子树的最小节点；返回该子树的根
    private Node removeMin(Node node) {
        //base 情况
        if (node.left == null) {
            //左子树空了，如果有右子树，那么要把右子树嫁接到父节点
            //因为这里是以 node 为根的子树，所以直接返回这个右子树的根给上级节点做左子树，就是嫁接
            //还要把当前节点置空，即删除
            //return node.right;
            Node rightNode = node.right;
            node.right = null; //(它的右子树会被返回，接到父节点的左子树上，所以这里设置它的 left 和 right即可)
            size--;
            return rightNode;//如果右子树是空则直接返回 null 给父节点的左子树，所以这里返回 rightNode 即可
        }

        //一般情况 (以 node 为根，还有左子树)
        node.left = removeMin(node.left); //递归过程返回的根作本本次node为根的树的左节点
        return node; //每次都返回该子树删除最小值之后的结果
    }


    //删除最大值 (递归写法)
    public E removeMax() {
        E ret = getMax();
        root = removeMax(root);
        return ret;
    }

    //返回删除最大值后的根节点(子树)给上级节点
    private Node removeMax(Node root) {
        //Base 情况(末尾节点)
        if (root.right == null) {
            //判断其是否还有左子树，然后把左子树返回给上级节点(的右子树接收)
            Node leftNode = root.left;
            root.left = null;
            size--;
            return leftNode;
        }
        root.right = removeMax(root.right);
        return root;
    }

    //用户指定删除某个值 e 的节点
    public void remove(E e) {
        root = remove(root, e); //每次递归都是返回操作后的子树(根节点)
    }

    //删除已 root 为根的子树中值为 e 的节点
    //返回操作完毕后子树的根
    private Node remove(Node root, E e) {
        //判断左右子树的情况，写 base 和 一般情况

        //base 情况 (前一次递归调用返回的是 null 的情况)
        if (root == null) {
            return null;
        }

        //一般情况，没有找到 (继续找)
        if (e.compareTo(root.e) > 0) {
            //在右子树上找
            root.right = remove(root.right, e);

        } else if (e.compareTo(root.e) < 0) {
            //在左子树上找
            root.left = remove(root.left, e);
        } else { // 找到的情况 e.compareTo(root.e) == 0
            //然后判断 root 的左右子树，根据进行选择嫁接方式，同时需要返回新的根节点给上级
            //1.左右子树都在 (融合的情况) -- 找到右子树上最小元素进行替代
            //2.左子树在，但是没有右子树 (类似于删除最大值的逻辑)
            //3.右子树在，但是没有左子树 (类似于删除最小值的逻辑)
            //4.左右子树都空了，叶子节点(直接 return null) 即把空子树返回给上级节点
            //这一种情况已经包含在2, 3的写法中了

            //先写 2, 3, 4 这类简单的情况 (返回值接在上层左子树还是右子树不确定，这要看上层的递归调用是哪种情况)
            if (root.right == null) {
                Node leftNode = root.left;
                root.left = null;
                size--;
                return leftNode; //包含了 leftNode 也为 null 的情况
            }

            if (root.left == null) {
                //类似于查找最小值的情况
                Node rightNode = root.right;
                root.right = null;
                size--;
                return rightNode;
            }

            //都存在的情况(先找到替代节点 successorNode )
            Node successorNode = getMin(root.right);
            successorNode.right = removeMin(root.right); //返回的是删除最小元素后的根节点
            successorNode.left = root.left;

            //置空原来 root 的 left 和 right
            root.left = root.right = null;
            return successorNode;

        }

        return root;
    }


    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }
        bst.levelOrder();

        System.out.println(bst);
        System.out.println(bst.getMin());
        System.out.println(bst.getMax());
        System.out.println();
        bst.removeMin();
        bst.preOrder();

        System.out.println();
        bst.removeMax();
        bst.preOrder();

        System.out.println();
        bst.removeMax();
        bst.preOrder();


        System.out.println();
        bst.remove(3);
        bst.preOrder();
    }

}
