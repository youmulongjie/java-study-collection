

/**
 * 希尔排序
 * <p>
 * 前言：
 * 数据序列1： 13-17-20-42-28 利用插入排序，13-17-20-28-42. Number of swap:1;
 * 数据序列2： 13-17-20-42-14 利用插入排序，13-14-17-20-42. Number of swap:3;
 * 如果数据序列基本有序，使用插入排序会更加高效。
 * <p>
 * 基本思想：
 * 在要排序的一组数中，根据某一增量分为若干子序列，并对子序列分别进行插入排序。
 * 然后逐渐将增量减小,并重复上述过程。直至增量为1,此时数据序列基本有序,最后进行插入排序。
 *
 * @author aikan
 */
public class ShellSort {

    public static void shellSort(int[] array, int lenth) {
        int temp = 0;
        int incre = lenth;

        while (true) {
            incre = incre / 2;
            //根据增量分为若干子序列
            for (int k = 0; k < incre; k++) {

                for (int i = k + incre; i < lenth; i += incre) {

                    for (int j = i; j > k; j -= incre) {
                        if (array[j] < array[j - incre]) {
                            temp = array[j - incre];
                            array[j - incre] = array[j];
                            array[j] = temp;
                        } else {
                            break;
                        }
                    }
                }
            }

            if (incre == 1) {
                break;
            }
        }
    }
    public static void main(String[] args) {

        int[] it={2,42,31,25,6,2,1,4,7};
        System.out.println(it);
        shellSort(it, 9);
        System.out.println(it);
    }

}
