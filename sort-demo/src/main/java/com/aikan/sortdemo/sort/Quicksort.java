package com.aikan.sortdemo.sort;

/**
 * 快速排序
 * <p>
 * 基本思想：（分治）
 * <p>
 * 先从数列中取出一个数作为key值；
 * 将比这个数小的数全部放在它的左边，大于或等于它的数全部放在它的右边；
 * 对左右两个小数列重复第二步，直至各区间只有1个数。
 * <p>
 * 辅助理解：挖坑填数 https://www.runoob.com/w3cnote/quick-sort.html
 * <p>
 * 初始时 i = 0; j = 9; key=72
 * 由于已经将a[0]中的数保存到key中，可以理解成在数组a[0]上挖了个坑，可以将其它数据填充到这来。
 * 从j开始向前找一个比key小的数。当j=8，符合条件，a[0] = a[8] ; i++ ; 将a[8]挖出再填到上一个坑a[0]中。
 * 这样一个坑a[0]就被搞定了，但又形成了一个新坑a[8]，这怎么办了？简单，再找数字来填a[8]这个坑。
 * 这次从i开始向后找一个大于key的数，当i=3，符合条件，a[8] = a[3] ; j-- ; 将a[3]挖出再填到上一个坑中。
 * 数组：72 - 6 - 57 - 88 - 60 - 42 - 83 - 73 - 48 - 85
 * 下标：0   1   2    3    4    5    6    7    8    9
 * <p>
 * 此时 i = 3; j = 7; key=72
 * 再重复上面的步骤，先从后向前找，再从前向后找。
 * 从j开始向前找，当j=5，符合条件，将a[5]挖出填到上一个坑中，a[3] = a[5]; i++;
 * <p>
 * 从i开始向后找，当i=5时，由于i==j退出。
 * 此时，i = j = 5，而a[5]刚好又是上次挖的坑，因此将key填入a[5]。
 * 数组：48 - 6 - 57 - 88 - 60 - 42 - 83 - 73 - 88 - 85
 * 下标：0   1   2    3    4    5    6    7    8    9
 * <p>
 * <p>
 * 可以看出a[5]前面的数字都小于它，a[5]后面的数字都大于它。因此再对a[0…4]和a[6…9]这二个子区间重复上述步骤就可以了。
 * <数组：48 - 6 - 57 - 42 - 60 - 72 - 83 - 73 - 88 - 85
 * 下标： 0   1   2    3    4    5    6    7    8    9
 * <p>
 * 平均时间复杂度：O(N*logN)
 *
 * @author aikan
 */
public class Quicksort {

    public static void quickSort(int a[], int l, int r) {
        //    key值的选取可以有多种形式，例如中间数或者随机数，分别会对算法的复杂度产生不同的影响。
        if (l >= r) {
            return;
        }
        int i = l;
        int j = r;
        //选择第一个数为key
        int key = a[l];
        while (i < j) {
//从右向左找第一个小于key的值
            while (i < j && a[j] >= key) {
                j--;
            }
            if (i < j) {
                a[i] = a[j];
                i++;
            }

//从左向右找第一个大于key的值
            while (i < j && a[i] < key) {
                i++;
            }
            if (i < j) {
                a[j] = a[i];
                j--;
            }

        }
        //i == j
        a[i] = key;
        //递归调用
        quickSort(a, l, i - 1);
        //递归调用
        quickSort(a, i + 1, r);
    }

    public static void main(String[] args) {
        int[] it = {2, 42, 31, 25, 6, 2, 1, 4, 7};
        System.out.println(it);
        quickSort(it, 0, 8);
        for (int i : it) {
            System.out.println(i);
        }
    }
}
