import algorithms.BalancedTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestBalancedTree {
    BalancedTree tree;

    @BeforeEach
    public void initTree() {
        tree = new BalancedTree();
    }

    @Test
    public void addOneElem() {
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> list = new ArrayList<>();
        int[] array = {1, 0, 0, 0, 0};
        list.add(array);
        expected.add(list);
        tree.insert(1);
        List<List<int[]>> actual = tree.traverse();
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                Assertions.assertArrayEquals(expected.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void findElemInRoot() {
        tree.insert(1);
        Assertions.assertTrue(tree.find(1));
    }

    @Test
    public void deleteElemFromRoot() {
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{0, 0, 0, 0, 0});
        expected.add(list);
        tree.insert(1);
        tree.delete(1);
        List<List<int[]>> actual = tree.traverse();
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                Assertions.assertArrayEquals(expected.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void testSplitting() {
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> level1 = new ArrayList<>();
        level1.add(new int[]{3, 0, 0, 0, 0});
        expected.add(level1);
        List<int[]> level2 = new ArrayList<>();
        Collections.addAll(level2,
                new int[]{1, 2, 0, 0, 0},
                new int[]{4, 5, 0, 0, 0});
        expected.add(level2);
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        List<List<int[]>> actual = tree.traverse();
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                Assertions.assertArrayEquals(expected.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void testStealing() {
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> level1 = new ArrayList<>();
        List<int[]> level2 = new ArrayList<>();
        Collections.addAll(level1,
                new int[]{2, 0, 0, 0, 0});
        Collections.addAll(level2,
                new int[]{1, 2, 0, 0, 0},
                new int[]{3, 6, 0, 0, 0});
        Collections.addAll(expected, level1, level2);
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(2);
        tree.delete(5);
        tree.delete(4);
        List<List<int[]>> actual = tree.traverse();
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                Assertions.assertArrayEquals(expected.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void testMerging() {
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> level1 = new ArrayList<>();
        Collections.addAll(level1,
                new int[]{1, 2, 3, 6, 0});
        expected.add(level1);
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.delete(5);
        tree.delete(4);
        List<List<int[]>> actual = tree.traverse();
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                Assertions.assertArrayEquals(expected.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void testFind() {
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        Assertions.assertTrue(tree.find(1));
        Assertions.assertTrue(tree.find(2));
        Assertions.assertTrue(tree.find(3));
        Assertions.assertTrue(tree.find(4));
        Assertions.assertTrue(tree.find(5));
        Assertions.assertTrue(tree.find(6));
    }

    @Test
    public void testDoubleSplitting() {
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> level1 = new ArrayList<>();
        List<int[]> level2 = new ArrayList<>();
        List<int[]> level3 = new ArrayList<>();
        Collections.addAll(level1,
                new int[]{9, 0, 0, 0, 0});
        Collections.addAll(level2,
                new int[]{3, 6, 0, 0, 0},
                new int[]{12, 15, 0, 0, 0});
        Collections.addAll(level3,
                new int[]{1, 2, 0, 0, 0},
                new int[]{4, 5, 0, 0, 0},
                new int[]{7, 8, 0, 0, 0},
                new int[]{10, 11, 0, 0, 0},
                new int[]{13, 14, 0, 0, 0},
                new int[]{16, 17, 0, 0, 0});
        Collections.addAll(expected, level1, level2, level3);
        for (int i = 1; i < 18; i++) {
            tree.insert(i);
        }
        List<List<int[]>> actual = tree.traverse();
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                Assertions.assertArrayEquals(expected.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void testDoubleMerging() {
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> level1 = new ArrayList<>();
        List<int[]> level2 = new ArrayList<>();
        Collections.addAll(level1,
                new int[]{3, 6, 9, 15, 0});
        Collections.addAll(level2,
                new int[]{1, 2, 0, 0, 0},
                new int[]{4, 5, 0, 0, 0},
                new int[]{7, 8, 0, 0, 0},
                new int[]{10, 11, 12, 13, 0},
                new int[]{16, 17, 0, 0, 0});
        Collections.addAll(expected, level1, level2);
        for (int i = 1; i < 18; i++) {
            tree.insert(i);
        }
        tree.delete(14);
        List<List<int[]>> actual = tree.traverse();
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                Assertions.assertArrayEquals(expected.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

    @Test
    public void testDeleteNotFromLeaf() {
        List<List<int[]>> expected = new ArrayList<>();
        List<int[]> level1 = new ArrayList<>();
        List<int[]> level2 = new ArrayList<>();
        Collections.addAll(level1,
                new int[]{3, 6, 9, 15, 0});
        Collections.addAll(level2,
                new int[]{1, 2, 0, 0, 0},
                new int[]{4, 5, 0, 0, 0},
                new int[]{7, 8, 0, 0, 0},
                new int[]{10, 11, 13, 14, 0},
                new int[]{16, 17, 0 ,0, 0});
        Collections.addAll(expected, level1, level2);
        for (int i = 1; i < 18; i++) {
            tree.insert(i);
        }
        tree.delete(12);
        List<List<int[]>> actual = tree.traverse();
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                Assertions.assertArrayEquals(expected.get(i).get(j), actual.get(i).get(j));
            }
        }
    }

}
