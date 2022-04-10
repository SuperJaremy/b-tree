import algorithms.BalancedTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestBalancedTree {
    BalancedTree tree;
    List<List<List<int[]>>> snapshots;

    @BeforeEach
    public void initTree() {
        snapshots = new ArrayList<>();
        tree = new BalancedTree(snapshots);
    }

    @Test
    public void addOneElem() {
        List<List<List<int[]>>> expected = new ArrayList<>();
        List<List<int[]>> step1 = new ArrayList<>();
        List<int[]> list = new ArrayList<>();
        int[] array = {1, 0, 0, 0, 0};
        list.add(array);
        step1.add(list);
        expected.add(step1);
        tree.insert(1);
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                for (int k = 0; k < expected.get(i).get(j).size(); k++)
                    Assertions.assertArrayEquals(expected.get(i).get(j).get(k), snapshots.get(i).get(j).get(k),
                            "step " + (i + 1));
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
        List<List<int[]>> step1 = List.of(List.of(new int[]{1, 0, 0, 0, 0}));
        List<List<int[]>> step2 = List.of(List.of(new int[]{0, 0, 0, 0, 0}));
        List<List<List<int[]>>> expected = List.of(step1, step2);
        tree.insert(1);
        tree.delete(1);
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                for (int k = 0; k < expected.get(i).get(j).size(); k++)
                    Assertions.assertArrayEquals(expected.get(i).get(j).get(k), snapshots.get(i).get(j).get(k),
                            "step " + (i + 1));
            }
        }
    }

    @Test
    public void testSplitting() {
        List<List<int[]>> step1 = List.of(List.of(new int[]{1, 0, 0, 0, 0}));
        List<List<int[]>> step2 = List.of(List.of(new int[]{1, 2, 0, 0, 0}));
        List<List<int[]>> step3 = List.of(List.of(new int[]{1, 2, 3, 0, 0}));
        List<List<int[]>> step4 = List.of(List.of(new int[]{1, 2, 3, 4, 0}));
        List<List<int[]>> step5 = List.of(List.of(new int[]{1, 2, 3, 4, 5}));
        List<List<int[]>> step6 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0})
        );
        List<List<List<int[]>>> expected = List.of(step1, step2, step3, step4, step5, step6);
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                for (int k = 0; k < expected.get(i).get(j).size(); k++) {
                    Assertions.assertArrayEquals(expected.get(i).get(j).get(k), snapshots.get(i).get(j).get(k),
                            "step " + (i + 1));
                }
            }
        }
    }

    @Test
    public void testStealing() {
        List<List<int[]>> step1 = List.of(List.of(new int[]{1, 0, 0, 0, 0}));
        List<List<int[]>> step2 = List.of(List.of(new int[]{1, 2, 0, 0, 0}));
        List<List<int[]>> step3 = List.of(List.of(new int[]{1, 2, 3, 0, 0}));
        List<List<int[]>> step4 = List.of(List.of(new int[]{1, 2, 3, 4, 0}));
        List<List<int[]>> step5 = List.of(List.of(new int[]{1, 2, 3, 4, 5}));
        List<List<int[]>> step6 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0})
        );
        List<List<int[]>> step7 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 0, 0})
        );
        List<List<int[]>> step8 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 2, 0, 0}, new int[]{4, 5, 6, 0, 0})
        );
        List<List<int[]>> step9 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 2, 0, 0}, new int[]{4, 6, 0, 0, 0})
        );
        List<List<int[]>> step10 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 2, 0, 0}, new int[]{6, 0, 0, 0, 0})
        );
        List<List<int[]>> step11 = List.of(
                List.of(new int[]{2, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{3, 6, 0, 0, 0})
        );
        List<List<List<int[]>>> expected = List.of(step1, step2, step3, step4, step5, step6, step7, step8, step9,
                step10, step11);
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(2);
        tree.delete(5);
        tree.delete(4);
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                for (int k = 0; k < expected.get(i).get(j).size(); k++) {
                    Assertions.assertArrayEquals(expected.get(i).get(j).get(k), snapshots.get(i).get(j).get(k),
                            "step " + (i + 1));

                }
            }
        }
    }

    @Test
    public void testMerging() {
        List<List<int[]>> step1 = List.of(List.of(new int[]{1, 0, 0, 0, 0}));
        List<List<int[]>> step2 = List.of(List.of(new int[]{1, 2, 0, 0, 0}));
        List<List<int[]>> step3 = List.of(List.of(new int[]{1, 2, 3, 0, 0}));
        List<List<int[]>> step4 = List.of(List.of(new int[]{1, 2, 3, 4, 0}));
        List<List<int[]>> step5 = List.of(List.of(new int[]{1, 2, 3, 4, 5}));
        List<List<int[]>> step6 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0})
        );
        List<List<int[]>> step7 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 0, 0})
        );
        List<List<int[]>> step8 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 6, 0, 0, 0})
        );
        List<List<int[]>> step9 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{6, 0, 0, 0, 0})
        );
        List<List<int[]>> step10 = List.of(List.of(new int[]{1, 2, 3, 6, 0}));
        List<List<List<int[]>>> expected = List.of(step1, step2, step3, step4, step5, step6, step7, step8, step9,
                step10);
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.delete(5);
        tree.delete(4);
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                for (int k = 0; k < expected.get(i).get(j).size(); k++) {
                    Assertions.assertArrayEquals(expected.get(i).get(j).get(k), snapshots.get(i).get(j).get(k),
                            "step " + (i + 1));
                }
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
        List<List<int[]>> step1 = List.of(List.of(new int[]{1, 0, 0, 0, 0}));
        List<List<int[]>> step2 = List.of(List.of(new int[]{1, 2, 0, 0, 0}));
        List<List<int[]>> step3 = List.of(List.of(new int[]{1, 2, 3, 0, 0}));
        List<List<int[]>> step4 = List.of(List.of(new int[]{1, 2, 3, 4, 0}));
        List<List<int[]>> step5 = List.of(List.of(new int[]{1, 2, 3, 4, 5}));
        List<List<int[]>> step6 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0})
        );
        List<List<int[]>> step7 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 0, 0})
        );
        List<List<int[]>> step8 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 7, 0})
        );
        List<List<int[]>> step9 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 7, 8})
        );
        List<List<int[]>> step10 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0})
        );
        List<List<int[]>> step11 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 0, 0})
        );
        List<List<int[]>> step12 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 10, 0})
        );
        List<List<int[]>> step13 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 10, 11})
        );
        List<List<int[]>> step14 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0})
        );
        List<List<int[]>> step15 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 0, 0})
        );
        List<List<int[]>> step16 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 13, 0})
        );
        List<List<int[]>> step17 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 13, 14})
        );
        List<List<int[]>> step18 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0})
        );
        List<List<int[]>> step19 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 0, 0})
        );
        List<List<int[]>> step20 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 16, 0})
        );
        List<List<int[]>> step21 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 16, 17})
        );
        List<List<int[]>> step22 = List.of(
                List.of(new int[]{3, 6, 9, 12, 15}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step23 = List.of(
                List.of(new int[]{9, 0, 0, 0, 0}),
                List.of(new int[]{3, 6, 0, 0, 0}, new int[]{12, 15, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<List<int[]>>> expected = List.of(step1, step2, step3, step4, step5, step6, step7, step8, step9,
                step10, step11, step12, step13, step14, step15, step16, step17, step18, step19, step20, step21, step22,
                step23);
        for (int i = 1; i < 18; i++) {
            tree.insert(i);
        }
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                for (int k = 0; k < expected.get(i).get(j).size(); k++) {
                    Assertions.assertArrayEquals(expected.get(i).get(j).get(k), snapshots.get(i).get(j).get(k),
                            "step " + (i + 1));
                }
            }
        }
    }

    @Test
    public void testDoubleMerging() {
        List<List<int[]>> step1 = List.of(List.of(new int[]{1, 0, 0, 0, 0}));
        List<List<int[]>> step2 = List.of(List.of(new int[]{1, 2, 0, 0, 0}));
        List<List<int[]>> step3 = List.of(List.of(new int[]{1, 2, 3, 0, 0}));
        List<List<int[]>> step4 = List.of(List.of(new int[]{1, 2, 3, 4, 0}));
        List<List<int[]>> step5 = List.of(List.of(new int[]{1, 2, 3, 4, 5}));
        List<List<int[]>> step6 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0})
        );
        List<List<int[]>> step7 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 0, 0})
        );
        List<List<int[]>> step8 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 7, 0})
        );
        List<List<int[]>> step9 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 7, 8})
        );
        List<List<int[]>> step10 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0})
        );
        List<List<int[]>> step11 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 0, 0})
        );
        List<List<int[]>> step12 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 10, 0})
        );
        List<List<int[]>> step13 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 10, 11})
        );
        List<List<int[]>> step14 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0})
        );
        List<List<int[]>> step15 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 0, 0})
        );
        List<List<int[]>> step16 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 13, 0})
        );
        List<List<int[]>> step17 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 13, 14})
        );
        List<List<int[]>> step18 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0})
        );
        List<List<int[]>> step19 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 0, 0})
        );
        List<List<int[]>> step20 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 16, 0})
        );
        List<List<int[]>> step21 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 16, 17})
        );
        List<List<int[]>> step22 = List.of(
                List.of(new int[]{3, 6, 9, 12, 15}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step23 = List.of(
                List.of(new int[]{9, 0, 0, 0, 0}),
                List.of(new int[]{3, 6, 0, 0, 0}, new int[]{12, 15, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step24 = List.of(
                List.of(new int[]{9, 0, 0, 0, 0}),
                List.of(new int[]{3, 6, 0, 0, 0}, new int[]{12, 15, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 0, 0, 0, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step25 = List.of(
                List.of(new int[]{9, 0, 0, 0, 0}),
                List.of(new int[]{3, 6, 0, 0, 0}, new int[]{15, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 13, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step26 = List.of(
                List.of(new int[]{3, 6, 9, 15, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 13, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<List<int[]>>> expected = List.of(step1, step2, step3, step4, step5, step6, step7, step8, step9,
                step10, step11, step12, step13, step14, step15, step16, step17, step18, step19, step20, step21, step22,
                step23, step24, step25, step26);
        for (int i = 1; i < 18; i++) {
            tree.insert(i);
        }
        tree.delete(14);
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                for (int k = 0; k < expected.get(i).get(j).size(); k++) {
                    Assertions.assertArrayEquals(expected.get(i).get(j).get(k), snapshots.get(i).get(j).get(k),
                            "step " + (i + 1));
                }
            }
        }
    }

    @Test
    public void testDeleteNotFromLeaf() {
        List<List<int[]>> step1 = List.of(List.of(new int[]{1, 0, 0, 0, 0}));
        List<List<int[]>> step2 = List.of(List.of(new int[]{1, 2, 0, 0, 0}));
        List<List<int[]>> step3 = List.of(List.of(new int[]{1, 2, 3, 0, 0}));
        List<List<int[]>> step4 = List.of(List.of(new int[]{1, 2, 3, 4, 0}));
        List<List<int[]>> step5 = List.of(List.of(new int[]{1, 2, 3, 4, 5}));
        List<List<int[]>> step6 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0})
        );
        List<List<int[]>> step7 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 0, 0})
        );
        List<List<int[]>> step8 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 7, 0})
        );
        List<List<int[]>> step9 = List.of(
                List.of(new int[]{3, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 6, 7, 8})
        );
        List<List<int[]>> step10 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0})
        );
        List<List<int[]>> step11 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 0, 0})
        );
        List<List<int[]>> step12 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 10, 0})
        );
        List<List<int[]>> step13 = List.of(
                List.of(new int[]{3, 6, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 9, 10, 11})
        );
        List<List<int[]>> step14 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0})
        );
        List<List<int[]>> step15 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 0, 0})
        );
        List<List<int[]>> step16 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 13, 0})
        );
        List<List<int[]>> step17 = List.of(
                List.of(new int[]{3, 6, 9, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 12, 13, 14})
        );
        List<List<int[]>> step18 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0})
        );
        List<List<int[]>> step19 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 0, 0})
        );
        List<List<int[]>> step20 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 16, 0})
        );
        List<List<int[]>> step21 = List.of(
                List.of(new int[]{3, 6, 9, 12, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 15, 16, 17})
        );
        List<List<int[]>> step22 = List.of(
                List.of(new int[]{3, 6, 9, 12, 15}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step23 = List.of(
                List.of(new int[]{9, 0, 0, 0, 0}),
                List.of(new int[]{3, 6, 0, 0, 0}, new int[]{12, 15, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 0, 0, 0}, new int[]{13, 14, 0, 0, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step24 = List.of(
                List.of(new int[]{9, 0, 0, 0, 0}),
                List.of(new int[]{3, 6, 0, 0, 0}, new int[]{11, 15, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 0, 0, 0, 0}, new int[]{13, 14, 0, 0, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step25 = List.of(
                List.of(new int[]{9, 0, 0, 0, 0}),
                List.of(new int[]{3, 6, 0, 0, 0}, new int[]{15, 0, 0, 0, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 13, 14, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<int[]>> step26 = List.of(
                List.of(new int[]{3, 6, 9, 15, 0}),
                List.of(new int[]{1, 2, 0, 0, 0}, new int[]{4, 5, 0, 0, 0}, new int[]{7, 8, 0, 0, 0}, new int[]{10, 11, 13, 14, 0}, new int[]{16, 17, 0, 0, 0})
        );
        List<List<List<int[]>>> expected = List.of(step1, step2, step3, step4, step5, step6, step7, step8, step9,
                step10, step11, step12, step13, step14, step15, step16, step17, step18, step19, step20, step21, step22,
                step23, step24, step25, step26);
        for (int i = 1; i < 18; i++) {
            tree.insert(i);
        }
        tree.delete(12);
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(i).size(); j++) {
                for (int k = 0; k < expected.get(i).get(j).size(); k++) {
                    Assertions.assertArrayEquals(expected.get(i).get(j).get(k), snapshots.get(i).get(j).get(k),
                            "step " + (i + 1));
                }
            }
        }
    }

}
