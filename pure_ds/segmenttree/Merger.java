package segmenttree;

//线段树的根节点存储什么，怎么去融合左右子树
public interface Merger<E> {
    E merge(E left, E right);
}
