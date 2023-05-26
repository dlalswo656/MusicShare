import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static Integer dp[][] = new Integer[41][2];
    //  N은 40보다 작거나 같은 자연수 또는 0 -> 배열의 크기를 41로 한다.(0 <= N <= 40)
    /*
        int가 아니라 Integer인 경우 -> 추후에 배열이 비었을 경우에만 계산을 해야하는데(dp의 목적) int로 하면 비어있다는 뜻이 0이 되므로 제대로 비어있는지 정말 배열의 값이 0인지 분간 불가
        Integer로 해서 비어있음이 null로 표현되도록 해야 한다.
    */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine()); // 테스트 케이스의 개수
        StringBuilder sb = new StringBuilder(); // 문자열 연산일 경우에는 String 보다는 StringBuilder

        // 피보나치 수: 결국엔 0과 1의 합으로 이루어졌다고 할 수 있음
        dp[0][0] = 1; // 0이 0번 등장 -> 1
        dp[0][1] = 0; // 0이 1번 등장 -> 0
        dp[1][0] = 0; // 1이 0번 등장 -> 0
        dp[1][1] = 1; // 1이 1번 등장 -> 1

        for (int i = 0; i < T; i++) {
            int n = Integer.parseInt(br.readLine());

            Integer[] fibonacci = fibonacci(n); // fibonacci 재귀 함수 호출
            sb.append(fibonacci[0] + " " + fibonacci[1] + '\n');
        }
        System.out.println(sb);
    }

    static Integer[] fibonacci(int n) {
        if (dp[n][0] == null && dp[n][1] == null) { // 배열이 비어있을 경우에만 연산
            dp[n][0] = fibonacci(n - 1)[0] + fibonacci(n - 2)[0]; // n에서 0이 출력되는 횟수
            dp[n][1] = fibonacci(n - 1)[1] + fibonacci(n - 2)[1]; // n에서 1이 출력되는 횟수
        }

        return dp[n];
    }
}