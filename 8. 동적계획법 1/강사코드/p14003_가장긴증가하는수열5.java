package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/14003
 * 가장 긴 증가하는 부분수열 5 - DP + 이분탐색 (BinarySearch)
 * 시간복잡도 O(N log N)
 */

public class P_14003_가장긴증가하는부분수열5 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int Numbers [] = new int [N+1]; // 숫자를 입력받는 배열
        int D [] = new int [N + 1]; // LIS 의 배열을 찾기 위한 DP 배열, 실제 LIS 를 나타내는 
        int IndexOrder [] = new int [N + 1]; // 실제 LIS 를 Tracking 하기 위한 배열, IndexOrder[i] = i 번쨰 숫자가 D 배열의 어떤 Index 에 들어갔는지 저장한다.
        int Tracking [] = new int [N + 1]; // IndexOrder 를 기반으로 실제 LIS 를 저장하는 배열 최종 출력을 한다.

        int Length = 0;
        int Answer = 0;
        // 입력
        st = new StringTokenizer(br.readLine(), " ");
        for(int i = 1 ; i <= N ; i++) {
            Numbers[i] = Integer.parseInt(st.nextToken());
        }

        // 0. 초기값 셋팅
        Length++;   
        D[1] = Numbers[1]; 
        IndexOrder[1] = 1; 

        // 1. DP + BinarySearch 를 이용해서 D 배열을 채워서 LIS 의 길이를 찾는다.
        for(int i = 2 ; i <= N ; i++) { 
            int searchIndex = doBinarySearch(D,Numbers[i],1,Length);
            IndexOrder[i] = searchIndex;
            if(searchIndex > Length) {
                Length++;
                D[Length] = Numbers[i];
            } else {
                D[searchIndex] = Numbers[i];
            }
        }

        Answer = Length; // LIS 길이 저장

        // 2. LIS 의 길이에서 내려오면서 실제 LIS 를 찾는다.
        for(int i = N ; i >= 1 ; i--) {
            if(Length == IndexOrder[i]) {
                Tracking[Length] = Numbers[i];
                Length--;
            }
        }

        // 정답출력
        bw.write(Answer + "\n");
        for(int i = 1; i <= Answer; i++) {
            bw.write(Tracking[i] + " ");
        }
        bw.flush();
        bw.close();

    }

    public static int doBinarySearch(int[] array, int key, int from, int to) {
        int low = from; // 왼쪽 범위
        int high = to; // 오른쪽 범위

        while (low <= high) { // 좌우가 뒤집어 질때까지 찾는다.
            int mid = (low + high) / 2; // 중간값 찾기
            if (array[mid] > key) {
                high = mid - 1; // 찾고자 하는 값 보다 중간이 클경우
            } else if (array[mid] < key) {
                low = mid + 1; // 찾고자 하는 값 보다 중간이 작은경우
            } else {
                return mid; // key found.
            }
        }
        return low; // key not found.
    }
}
