package com.aikan.sortdemo.sort;

/**
 * 归并排序
 * 是建立在归并操作上的一种有效的排序算法。
 * 该算法是采用分治法的一个非常典型的应用。
 * 首先考虑下如何将2个有序数列合并。这个非常简单，只要从比较2个数列的第一个数，谁小就先取谁，
 * 取了后就在对应数列中删除这个数。然后再进行比较，如果有数列为空，那直接将另一个数列的数据依次取出即可。
 * <p>
 * 解决了上面的合并有序数列问题，再来看归并排序，其的基本思路就是将数组分成2组A，B，如果这2组组内的数据都是有序的，
 * 那么就可以很方便的将这2组数据进行排序。如何让这2组组内数据有序了？
 * 可以将A，B组各自再分成2组。依次类推，当分出来的小组只有1个数据时，
 * 可以认为这个小组组内已经达到了有序，然后再合并相邻的2个小组就可以了。
 * 这样通过先递归的分解数列，再合并数列就完成了归并排序。
 * 归并排序
 * <p>
 * 平均时间复杂度：O(NlogN)
 * 归并排序的效率是比较高的，设数列长为N，将数列分开成小数列一共要logN步，每步都是一个合并有序数列的过程，时间复杂度可以记为O(N)，故一共为O(N*logN)。
 *
 * @author aikan
 */
public class MergeSort {
    public static void mergeSort(int a[], int first, int last, int temp[]) {
        if (first < last) {
            int middle = (first + last) / 2;
            //左半部分排好序
            mergeSort(a, first, middle, temp);
            //右半部分排好序
            mergeSort(a, middle + 1, last, temp);
            //合并左右部分
            mergeArray(a, first, middle, last, temp);
        }
    }

    public static void mergeArray(int a[], int first, int middle, int end, int temp[]) {
        //合并 ：将两个序列a[first-middle],a[middle+1-end]合并
        int i = first;
        int m = middle;
        int j = middle + 1;
        int n = end;
        int k = 0;
        while (i <= m && j <= n) {
            if (a[i] <= a[j]) {
                temp[k] = a[i];
                k++;
                i++;
            } else {
                temp[k] = a[j];
                k++;
                j++;
            }
        }
        while (i <= m) {
            temp[k] = a[i];
            k++;
            i++;
        }
        while (j <= n) {
            temp[k] = a[j];
            k++;
            j++;
        }

        for (int ii = 0; ii < k; ii++) {
            a[first + ii] = temp[ii];
        }
    }

    //// 这是初始解说样例
//    void MemeryArray(int[] a, int n, int[] b, int m, int[] c) {
//        //将有序数组a[]和b[]合并到c[]中
//        int i, j, k;
//        i = j = k = 0;
//        while (i < n && j < m) {
//            if (a[i] < b[j]) {
//                c[k++] = a[i++];
//            } else {
//                c[k++] = b[j++];
//            }
//        }
//        while (i < n) {
//            c[k++] = a[i++];
//            {
//
//                while (j < m) {
//                    c[k++] = b[j++];
//                }
//            }
//
//        }
//    }

}
