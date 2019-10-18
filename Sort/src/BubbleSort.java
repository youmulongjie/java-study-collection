/**
 * 冒泡排序
 * 基本思想：两个数比较大小，较大的数下沉，较小的数冒起来。
 * <p>
 * 过程：
 * <p>
 * 比较相邻的两个数据，如果第二个数小，就交换位置。
 * 从后向前两两比较，一直到比较最前两个数据。最终最小数被交换到起始的位置，这样第一个最小数的位置就排好了。
 * 继续重复上述过程，依次将第2.3...n-1个最小数排好位置。
 * <p>
 * 平均时间复杂度：O(n2)
 *
 * @author aikan
 */
public class BubbleSort {

    public static void BubbleSort(int[] arr) {
        int temp;//临时变量
        //表示趟数，一共arr.length-1次。
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j > i; j--) {

                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }

//    优化：
//
//    针对问题：
//    数据的顺序排好之后，冒泡算法仍然会继续进行下一轮的比较，直到arr.length-1次，后面的比较没有意义的。
//
//    方案：
//    设置标志位flag，如果发生了交换flag设置为true；如果没有交换就设置为false。
//    这样当一轮比较结束后如果flag仍为false，即：这一轮没有发生交换，说明数据的顺序已经排好，没有必要继续进行下去。

    public static void BubbleSort1(int[] arr) {
        int temp;//临时变量
        boolean flag; //是否交换的标志
        //表示趟数，一共 arr.length-1 次
        for (int i = 0; i < arr.length - 1; i++) {
            // 每次遍历标志位都要先置为false，才能判断后面的元素是否发生了交换
            flag = false;
            //选出该趟排序的最大值往后移动
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    //只要有发生了交换，flag就置为true
                    flag = true;
                }
            }
            // 判断标志位是否为false，如果为false，说明后面的元素已经有序，就直接return
            if (!flag) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] it={2,42,31,25,6,2,1,4,7};
        System.out.println(it);
        BubbleSort1(it);
        System.out.println(it);
    }
}
