import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static int N, A, B;

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();

        // X : 인당 나눠줄 사탕의 수
        // Y : 사탕 봉지의 수
        // A * x + 1 = B * y
        // Ax + By = C 의 형태로 변환.
        // -Ax + By = 1
        // A(-x) + By = 1 의 형태로 변환
        for (int i = 0; i < N; i++) {
            A = sc.nextInt();
            B = sc.nextInt();

            EGResult result = extendedGcd(A, B);
//          System.out.println(result);
            // D = gcd(A,B)
            // Ax + By = C 일때 C % D == 0 이어야 해를 가질 수 있음 : 베주 항등식
            if (result.r != 1) {
                System.out.println("IMPOSSIBLE");
            } else {
                // x0 = s * C/D
                // y0 = t * C/D
                long x0 = result.s;
                long y0 = result.t;

                // x = x0 + B/D * k
                // y = y0 - A/D * k

                // x < 0
                // x0 + B * k < 0
                // k < - x0 / B
                // 0 < y <= 1e9
                // 0 < y0 - A * k <= 1e9
                // - y0 < - A * k <= 1e9 - y0

                // ( y0 - 1e9) / A  <= k < y0 / A
                //                     k < - x0 / B

                long kFromY = (long) Math.ceil((double) y0 / (double) A) - 1;
                long kFromX = (long) Math.ceil((double) -x0 / (double) B) - 1;
                long k = Math.min(kFromX, kFromY);
                long kLimitFromY = (long) Math.ceil((y0 - 1e9) / (double) A);
                if (kLimitFromY <= k) {
                    System.out.println(y0 - A * k);
                } else {
                    System.out.println("IMPOSSIBLE");
                }
            }
        }
    }

    public static EGResult extendedGcd(long a, long b) {
        long s0 = 1, t0 = 0, r0 = a;
        long s1 = 0, t1 = 1, r1 = b;

        long temp;
        while (r1 != 0) {
            long q = r0 / r1;

            temp = r0 - q * r1;
            r0 = r1;
            r1 = temp;

            temp = s0 - q * s1;
            s0 = s1;
            s1 = temp;

            temp = t0 - q * t1;
            t0 = t1;
            t1 = temp;
        }
        return new EGResult(s0, t0, r0);
    }
}

class EGResult {
    long s;
    long t;
    long r;

    public EGResult(long s, long t, long r) {
        super();
        this.s = s;
        this.t = t;
        this.r = r;
    }

    @Override
    public String toString() {
        return "ExtendedGcdResult [s=" + s + ", t=" + t + ", gcd=" + r + "]";
    }

}
