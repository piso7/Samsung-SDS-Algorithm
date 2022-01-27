import java.io.FileInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    // 좌, 우, 위, 아래
    static final int[] MX = {-1, 1, 0, 0};
    static final int[] MY = {0, 0, -1, 1};

    static int R, C;
    static char[][] map;
    static int[][] dp;
    static Queue<Point> queue;
    static boolean foundAnswer;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        R = sc.nextInt();
        C = sc.nextInt();

        map = new char[R][C];
        dp = new int[R][C];
        queue = new LinkedList<>();

        Point st = null;
        for (int r = 0; r < R; r++) {
            String line = sc.next();
            for (int c = 0; c < C; c++) {
                map[r][c] = line.charAt(c);
                if (map[r][c] == 'S') {
                    st = new Point(r, c, 'S');
                } else if (map[r][c] == '*') {
                    queue.add(new Point(r, c, '*'));
                }
            }
        }
        queue.add(st);

//        for (int r = 0; r < R; r++) {
//            System.out.println(Arrays.toString(map[r]));
//        }

        while (!queue.isEmpty()) {
            // 1. 큐에서 꺼내옴 -> S, ., D, *
            Point p = queue.poll();
            // 2. 목적지인가? -> D
            if (p.type == 'D') {
                System.out.println(dp[p.y][p.x]);
                foundAnswer = true;
                break;
            }
            // 3. 연결된 곳을 순회 -> 좌, 우, 위, 아래
            for (int i = 0; i < 4; i++) {
                int ty = p.y + MY[i];
                int tx = p.x + MX[i];
                // 4. 갈수있는가? ( 공통 ) -> 맵을 벗어나지 않고
                if (0 <= ty && ty < R && 0 <= tx && tx < C) {
                    if (p.type == '.' || p.type == 'S') {
                        // 갈수있는가? ( 고슴도치 ) -> . or D, 방문하지 않은곳
                        if ((map[ty][tx] == '.' || map[ty][tx] == 'D') && dp[ty][tx] == 0) {
                            // 5. 체크인 -> dp에 현재 + 1 을 기록
                            dp[ty][tx] = dp[p.y][p.x] + 1;
                            // 6. 큐에 넣음
                            queue.add(new Point(ty, tx, map[ty][tx]));
                        }
                    } else if (p.type == '*') {
                        // 4. 갈수있는가? ( 물 )  .
                        if (map[ty][tx] == '.' || map[ty][tx] == 'S') {
                            // 5. 체크인 -> 지도에 * 표기
                            map[ty][tx] = '*';
                            // 6. 큐에 넣음
                            queue.add(new Point(ty, tx, '*'));
                        }
                    }
                }
            }
        }

        if (foundAnswer == false) {
            System.out.println("KAKTUS");
        }
    }
}

class Point {
    int y;
    int x;
    char type;

    public Point(int y, int x, char type) {
        super();
        this.y = y;
        this.x = x;
        this.type = type;
    }

    @Override
    public String toString() {
        return "[y=" + y + ", x=" + x + ", type=" + type + "]";
    }
}
