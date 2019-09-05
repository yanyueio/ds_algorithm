package segmenttree;

//底层以数组存储的线段树
public class SegmentTree<E> {

    //成员
    private E[] data; //存储区间元素
    private E[] tree; //存储线段树的数组 (当做满二叉树开辟空间)
    private Merger<E> merger; //线段树的根节点如何存储 (如何融合两个子树)

    //构造器
    public SegmentTree(E[] arr, Merger<E> merger) {

        this.merger = merger;

        data = (E[]) new Object[arr.length];
        //然后把数组拷贝到内部数组中
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }

        //初始化线段树
        tree = (E[]) new Object[4 * arr.length];
        //数组放入线段树
        buildSegmentTree(0, 0, data.length - 1);
    }

    //传入当前子树的根节点的索引 (不必返回给父节点的左右子树，因为左右子树是可以通过 rootIndex 计算的)
    //每次递归调用要传入相应的区间
    private void buildSegmentTree(int rootIndex, int rangeLeft, int rangeRight) {
        //base 情况 (区间长度为1，这颗子树)
        if (rangeLeft == rangeRight) {
            //直接存储就好了
            tree[rootIndex] = data[rangeLeft]; //写 data[rangRight] 也可以
            return;
        }

        //其他子树的情况，即 rangeLeft < rangeRight
        //先创建好其左右子树即可，即 leftIndex, rightIndex 为根的子树
        int leftIndex = leftChild(rootIndex);
        int rightIndex = rightChild(rootIndex);

        //区间中间切割
        //int mid = (rangeLeft + rangeRight) / 2; // 这么写可能会出现整型溢出
        int mid = rangeLeft + (rangeRight - rangeLeft) / 2; //自动取整了

        //此时分为了两个区间 leftIndex: [rangeLeft, mid]; rightIndex: [mid+1, rangeRight]
        buildSegmentTree(leftIndex, rangeLeft, mid);
        buildSegmentTree(rightIndex, mid + 1, rangeRight);


        //根节点存储什么，也存储区间么? 根据业务需求而定
        //比如求和，那么每个子树根就存储子树的和 (或者求最大值，最小值，那么次数就存储结果)
        //把接口留出来吧，接口来定义如何融合
        tree[rootIndex] = merger.merge(tree[leftIndex], tree[rightIndex]);
    }

    public int getSize() {
        return data.length;
    }

    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal");
        }
        return data[index];
    }


    //辅助方法 (获取线段树的父子关系的)

    //返回完全二叉树的数组表示中，左孩子索引
    private int leftChild(int index) {
        //当前节点的左子节点的索引
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    /*线段树一般不要去找其父节点*/
    /*
    private int parent(int index) {
        if (index == 0) {
            //根索引没有父亲
            throw new IllegalArgumentException("index 0 没有父亲节点");
        }
        return (index - 1) / 2;
    }*/


    //返回区间 [queryL, queryR] 的结果值
    public E query(int queryL, int queryR) {
        //检查索引
        if (queryL < 0 || queryR < 0 || queryL > getSize() || queryR > getSize()
                || queryL > queryR) {
            throw new IllegalArgumentException("Query segment illegal");
        }
        return query(0, 0, getSize() - 1, queryL, queryR);
    }

    //搜索 rootIndex 这颗子树 (其区间范围是 [fromIndex, toIndex])，查找给定的 [queryL, queryR] 值
    private E query(int rootIndex, int fromIndex, int toIndex, int queryL, int queryR) {
        //当前区间和查询区间完全吻合不需要拆分，直接返回当前节点存储的值
        if (fromIndex == queryL && toIndex == queryR) {
            //正好是这区间
            return tree[rootIndex];
        }
        //当前区间和查询区间不吻合，此时需要分情况进行判断查找。
        //查询区间是当前区间子区间的情况，查询区间落在以 mid 区分的左右两个区间的情况
        int midIndex = fromIndex + (toIndex - fromIndex) / 2;
        int leftIndex = leftChild(rootIndex);
        int rightIndex = rightChild(rootIndex);

        if (queryL >= midIndex + 1) {
            //此时再右子树上查找
            return query(rightIndex, midIndex + 1, toIndex, queryL, queryR);
        } else if (queryR <= midIndex) {
            //在左子树上查找
            return query(leftIndex, fromIndex, midIndex, queryL, queryR);
        }
        //如果分别散落在两个区间时，那么分别查找，然后合并
        E leftReuslt = query(leftIndex, fromIndex, midIndex, queryL, midIndex);
        E rightReuslt = query(rightIndex, midIndex + 1, toIndex, midIndex + 1, queryR);
        return merger.merge(leftReuslt, rightReuslt);
    }

    //更新子树操作
    private void set(int index, E e) {
        //检查索引
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal");
        }
        //更新原数组, 区间的值
        data[index] = e;
        //更新线段树数组的值 (递归调用查找，然后更新，先要确定是左还是右子树)
        set(0, 0, getSize()-1, index, e); // 在线段树数组中tree查找这个 index 对应的位置，并更新
    }

    //在以 rootIndex 为根的线段树中查找 index 应该放置的位置，并更新值
    private void set(int rootIndex, int leftRange, int rightRange, int index, E e) {
        //base 情况 (区间为 1，找到了)
        if(leftRange == rightRange) {
            tree[rootIndex] = e;
            return;
        }

        //在 tree[] 中位置
        int midIndex = leftRange + (rightRange - leftRange)/2;
        int leftIndex = leftChild(rootIndex);
        int rightIndex = rightChild(rootIndex);

        if(index >= midIndex + 1) {
            //右子树上查找
            set(rightIndex, midIndex+1, rightRange, index, e);
        } else if (index <= midIndex) {
            //左子树上查找
            set(leftIndex, leftRange, midIndex, index, e);
        }
        //左右子树完毕之后，还要更新一下父节点 (它的子元素更新了，父节点一定受到影响)
        tree[rootIndex] = merger.merge(tree[leftIndex], tree[rightIndex]);
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("[ ");
        for (E num : tree) {
            if (num == null) {
                res.append("null ");
            } else {
                res.append(num + " ");
            }
        }
        res.append("]");
        return res.toString();
    }

    public static void main(String[] args) {
        Integer[] nums = {-2, 0, 3, -5, 2, -1};
        /*SegmentTree<Integer> tree = new SegmentTree<>(nums, new Merger<Integer>() {
            @Override
            public Integer merge(Integer left, Integer right) {
                //这里定义 merger 的逻辑
                //比如求和的方式
                return left + right;
            }
        });*/

        //用 lambda 表达式代替接口的匿名实例
        SegmentTree<Integer> tree = new SegmentTree<>(nums, (left, right) -> left + right);
        System.out.println(tree);

        System.out.println(tree.query(0, 2));
        System.out.println(tree.query(2, 5));
        System.out.println(tree.query(0, 5));
    }
}
