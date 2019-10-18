

/**
 * 基本思想：
 * 在要排序的一组数中，假定前n-1个数已经排好序，
 * 现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。
 * 如此反复循环，直到全部排好顺序。
 * <p>
 * 平均时间复杂度：O(n2)
 *
 * @author aikan
 */
public class InsertionSort {

    public static void insert_sort(int array[], int lenth) {

        int temp;

        for (int i = 0; i < lenth - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                } else {         //不需要交换
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] it={2,42,31,25,6,2,1,4,7};
        System.out.println(it);
        insert_sort(it, it.length);
        System.out.println(it);
    }
}
