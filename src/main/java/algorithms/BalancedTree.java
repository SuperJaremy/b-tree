package algorithms;

import java.util.*;

public class BalancedTree {
    private final static int MAX_VALUES = 5;
    private TreeNode root;

    public BalancedTree() {
        root = new TreeNode();
    }

    public List<List<int[]>> traverse() {
        List<List<int[]>> list = new ArrayList<>(1);
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        traverse(list, nodes);
        return list;
    }

    public void insert(int value) {
        if (root.isLeaf()) {
            root.addValue(value);
        } else insert(value, root);
        splitRoot();
    }

    public boolean find(int value) {
        TreeNode node = find(value, root);
        return node != null;
    }

    public void delete(int value) {
        if (root.isLeaf()) {
            root.deleteValue(value);
        } else
            delete(value, root);
    }

    private void traverse(List<List<int[]>> arrays, List<TreeNode> nodes) {
        List<int[]> list = new ArrayList<>();
        List<TreeNode> nextNodes = new ArrayList<>();
        for (TreeNode i : nodes) {
            int[] array = new int[MAX_VALUES];
            System.arraycopy(i.values, 0, array, 0, i.valueNumber);
            list.add(array);
            nextNodes.addAll(Arrays.asList(i.neighbours)
                    .subList(0, i.neighbourNumber));
        }
        arrays.add(list);
        if (!nextNodes.isEmpty())
            traverse(arrays, nextNodes);
    }

    private void insert(int value, TreeNode node) {
        TreeNode child = node.getSuitableNeighbour(value);
        if (child.isLeaf()) {
            child.addValue(value);
        } else
            insert(value, child);
        splitChild(node, child);
    }

    private void splitChild(TreeNode parent, TreeNode child) {
        if (child.hasSpace())
            return;
        int centralIndex = MAX_VALUES % 2 == 0 ? MAX_VALUES / 2 - 1 : MAX_VALUES / 2;
        TreeNode left = child.leftNode(centralIndex);
        TreeNode right = child.rightNode(centralIndex);
        parent.addValue(child.values[centralIndex]);
        parent.addNeighbours(child.values[centralIndex], left, right);
    }

    private void splitRoot() {
        if (root.hasSpace())
            return;
        TreeNode newRoot = new TreeNode();
        splitChild(newRoot, root);
        root = newRoot;
    }

    private TreeNode find(int value, TreeNode node) {
        if (node.containsValue(value) != -1)
            return node;
        else if (!node.isLeaf())
            return find(value, node.getSuitableNeighbour(value));
        else
            return null;
    }

    private void delete(int value, TreeNode node) {
        TreeNode child = node.getSuitableNeighbour(value);
        if (child.containsValue(value) == -1)
            delete(value, child);
        else if (!child.isLeaf()) {
            child.deleteValue(value);
            int predecessor = deleteReplaceWithPredecessor(child, value);
            child.addValue(predecessor);
            findAndCompleteLeafNode(predecessor, child);
        } else {
            child.deleteValue(value);
        }
        complete(node, child);
    }

    private int deleteReplaceWithPredecessor(TreeNode node, int value) {
        TreeNode child = node.getSuitableNeighbour(value);
        int predecessor;
        if (!child.isLeaf())
            predecessor = deleteReplaceWithPredecessor(child, value);
        else {
            predecessor = child.values[child.valueNumber - 1];
            child.deleteValue(predecessor);
        }
        return predecessor;
    }

    private void findAndCompleteLeafNode(int value, TreeNode node) {
        TreeNode child = node.getSuitableNeighbour(value);
        if (!child.isLeaf())
            findAndCompleteLeafNode(value, child);
        else {
            complete(node, child);
        }
    }

    private void complete(TreeNode parent, TreeNode child) {
        if (child.valueNumber >= MAX_VALUES / 2)
            return;
        int idx = parent.containsNeighbour(child);
        if (idx == -1)
            throw new IllegalArgumentException();

        TreeNode leftSibling;
        TreeNode rightSibling;
        if (idx > 0)
            leftSibling = parent.neighbours[idx - 1];
        else
            leftSibling = null;
        if (idx < parent.neighbourNumber - 1)
            rightSibling = parent.neighbours[idx + 1];
        else
            rightSibling = null;
        if (leftSibling != null && leftSibling.valueNumber > MAX_VALUES / 2) {
            int predecessor = leftSibling.values[leftSibling.valueNumber - 1];
            leftSibling.deleteValue(predecessor);
            int intervening = parent.getInterveningValue(predecessor);
            parent.deleteValue(intervening);
            parent.addValue(predecessor);
            child.addValue(intervening);
        } else if (rightSibling != null && rightSibling.valueNumber > MAX_VALUES / 2) {
            int successor = rightSibling.values[0];
            rightSibling.deleteValue(successor);
            int intervening = parent.getInterveningValue(successor);
            parent.deleteValue(intervening);
            parent.addValue(successor);
            child.addValue(intervening);
        } else if (leftSibling != null) {
            mergeNodes(leftSibling, child, parent);
        } else if (rightSibling != null) {
            mergeNodes(child, rightSibling, parent);
        }
    }

    private void mergeNodes(TreeNode left, TreeNode right, TreeNode parent) {
        TreeNode merged = new TreeNode();
        if (left.valueNumber > 0)
            System.arraycopy(left.values, 0, merged.values, 0, left.valueNumber);
        int intervening = parent.getInterveningValue(left.values[left.valueNumber - 1]);
        merged.values[left.valueNumber] = intervening;
        if (right.valueNumber > 0)
            System.arraycopy(right.values, 0, merged.values, left.valueNumber + 1,
                    right.valueNumber);
        merged.valueNumber = left.valueNumber + right.valueNumber + 1;
        if (left.neighbourNumber > 0)
            System.arraycopy(left.neighbours, 0, merged.neighbours, 0, left.neighbourNumber);
        if (right.neighbourNumber > 0)
            System.arraycopy(right.neighbours, 0, merged.neighbours, left.neighbourNumber, right.neighbourNumber);
        merged.neighbourNumber = left.neighbourNumber + right.neighbourNumber;
        parent.deleteValue(intervening);
        parent.mergeNeighbours(left, merged);
        if (parent.valueNumber == 0) {
            parent.valueNumber = merged.valueNumber;
            parent.values = merged.values;
            parent.neighbourNumber = merged.neighbourNumber;
            parent.neighbours = merged.neighbours;
        }
    }

    private class TreeNode {
        private int[] values;
        private int valueNumber;
        private TreeNode[] neighbours;
        private int neighbourNumber;

        private TreeNode() {
            values = new int[MAX_VALUES];
            neighbours = new TreeNode[MAX_VALUES + 1];
            valueNumber = neighbourNumber = 0;
        }

        private void addValue(int value) {
            if (valueNumber == MAX_VALUES)
                throw new ArrayIndexOutOfBoundsException();
            if (valueNumber == 0) {
                values[0] = value;
                valueNumber++;
                return;
            }
            for (int i = 0; i < valueNumber; i++) {
                if (values[i] > value) {
                    System.arraycopy(values, i, values, i + 1, valueNumber - i);
                    values[i] = value;
                    valueNumber++;
                    return;
                }
            }
            values[valueNumber] = value;
            valueNumber++;
        }

        private void addNeighbours(int value, TreeNode left, TreeNode right) {
            int srcPos = containsValue(value);
            if (srcPos == -1)
                throw new IllegalArgumentException();
            if (neighbourNumber > MAX_VALUES)
                throw new IndexOutOfBoundsException();
            if (neighbourNumber == 0) {
                neighbours[0] = left;
                neighbours[1] = right;
                neighbourNumber += 2;
                return;
            }
            neighbourNumber++;
            System.arraycopy(neighbours, srcPos + 1, neighbours, srcPos + 2,
                    neighbourNumber - srcPos - 2);
            neighbours[srcPos] = left;
            neighbours[srcPos + 1] = right;
        }

        //Replace first node and its right neighbour with merged node
        private void mergeNeighbours(TreeNode first, TreeNode merged) {
            for (int i = 0; i < neighbourNumber; i++) {
                if (neighbours[i].equals(first)) {
                    neighbours[i] = merged;
                    System.arraycopy(neighbours, i + 2, neighbours, i + 1,
                            neighbourNumber - i - 2);
                    neighbourNumber--;
                    return;
                }
            }
        }

        private int containsValue(int value) {
            for (int i = 0; i < valueNumber; i++) {
                if (values[i] == value)
                    return i;
            }
            return -1;
        }

        private void deleteValue(int value) {
            for (int i = 0; i < valueNumber - 1; i++) {
                if (values[i] == value) {
                    System.arraycopy(values, i + 1, values, i,
                            valueNumber - i - 1);
                    valueNumber--;
                    return;
                }
            }
            if (values[valueNumber - 1] == value)
                valueNumber--;
        }

        private boolean isLeaf() {
            return neighbourNumber == 0;
        }

        private TreeNode getSuitableNeighbour(int value) {
            int i = 0;
            for (; i < valueNumber; i++) {
                if (value <= values[i]) {
                    return neighbours[i];
                }
            }
            return neighbours[i];
        }

        private int getInterveningValue(int value) {
            for (int i = 0; i < valueNumber; i++) {
                if (value <= values[i]) {
                    return values[i];
                }
            }
            return values[valueNumber - 1];
        }

        private boolean hasSpace() {
            return valueNumber < MAX_VALUES;
        }

        private TreeNode leftNode(int centralIndex) {
            TreeNode left = new TreeNode();
            System.arraycopy(values, 0, left.values, 0, centralIndex);
            left.valueNumber = centralIndex;
            centralIndex = neighbourNumber == 0 ? -1 : centralIndex;
            System.arraycopy(neighbours, 0, left.neighbours, 0, centralIndex + 1);
            left.neighbourNumber = centralIndex + 1;
            return left;
        }

        private TreeNode rightNode(int centralIndex) {
            TreeNode right = new TreeNode();
            System.arraycopy(values, centralIndex + 1, right.values, 0,
                    MAX_VALUES - centralIndex - 1);
            right.valueNumber = MAX_VALUES - centralIndex - 1;
            centralIndex = neighbourNumber == 0 ? MAX_VALUES : centralIndex;
            System.arraycopy(neighbours, centralIndex + 1, right.neighbours, 0,
                    MAX_VALUES - centralIndex);
            right.neighbourNumber = MAX_VALUES - centralIndex;
            return right;
        }

        private int containsNeighbour(TreeNode node) {
            for (int i = 0; i < neighbourNumber; i++) {
                if (neighbours[i].equals(node))
                    return i;
            }
            return -1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TreeNode treeNode = (TreeNode) o;
            return Arrays.equals(values, treeNode.values) && Arrays.equals(neighbours, treeNode.neighbours);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(values);
            result = 31 * result + Arrays.hashCode(neighbours);
            return result;
        }
    }
}
