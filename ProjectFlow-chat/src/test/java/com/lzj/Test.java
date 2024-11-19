package com.lzj;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个字符串 s。
 * 英文字母中每个字母的 镜像 定义为反转字母表之后对应位置上的字母。例如，'a' 的镜像是 'z'，'y' 的镜像是 'b'。
 * 最初，字符串 s 中的所有字符都 未标记 。
 * 字符串 s 的初始分数为 0 ，你需要对其执行以下过程：
 * 从左到右遍历字符串。
 * 对于每个下标 i ，找到距离最近的 未标记 下标 j，下标 j 需要满足 j < i 且 s[j] 是 s[i] 的镜像。然后 标记 下标 i 和 j，总分加上 i - j 的值。
 * 如果对于下标 i，不存在满足条件的下标 j，则跳过该下标，继续处理下一个下标，不需要进行标记。
 * 返回最终的总分。
 * 输入： s = "aczzx"
 * 输出： 5
 * i = 0。没有符合条件的下标 j，跳过。
 * i = 1。没有符合条件的下标 j，跳过。
 * i = 2。距离最近的符合条件的下标是 j = 0，因此标记下标 0 和 2，然后将总分加上 2 - 0 = 2 。
 * i = 3。没有符合条件的下标 j，跳过。
 * i = 4。距离最近的符合条件的下标是 j = 1，因此标记下标 1 和 4，然后将总分加上 4 - 1 = 3 。
 * 示例 2：

 * 对于每个下标 i，都不存在满足条件的下标 j。
 */
public class Test {
    private final static char[] ENGLISHARR={
            'a','b','c','d','e','f',
            'g','h','i','j','k','l',
            'm','n','o','p','q','r',
            's','t','u','v','w','x',
            'y','z'};
    private final static Map<Integer, Integer> BIAOJI = new HashMap<>();
    private final static int LENGTH= ENGLISHARR.length;
    public static void main(String[] args) {
        long morrirSum = calculateScore("eockppxdqclkhjgvnw");//eockppxdqclkhjgvnw
        System.out.println("morrirSum = " + morrirSum);

    }
    public static long calculateScore(String a){
        int count=0;
        Map<Integer, Boolean> marked = new HashMap<>(); // 用来标记已经匹配的字符索引
//        String a="aczzx";
        for(int i=0;i<a.length();i++){
            if (i > 0) { // 这里的判断没必要，其实 i=0 进去后不会进入 j 循环
                char chr = a.charAt(i);
                for (int j = 0; j < i; j++) {  // 这里保留你的顺序遍历
                    char c = a.charAt(j);

                    if (getMrrirStr(c) == chr) {
                        if (BIAOJI.get(j) == null || BIAOJI.get(j) != 1) { // j 位置没有被标记
                            count += (i - j);
                            BIAOJI.put(j, 1); // 标记 j
                            BIAOJI.put(i, 1); // 也要标记 i
                            break; // 找到最近的就跳出内循环
                        }
                    }
                }
            }
        }
        return count;
    }
        //方法，通过字符找镜像字符
        public static char getMrrirStr(char c){
            char mirror='-';
            for(int i=0;i<LENGTH;i++){
                if(c==ENGLISHARR[i]){
                    mirror=ENGLISHARR[LENGTH-i-1];
                }
            }
            return mirror;
        }
}
