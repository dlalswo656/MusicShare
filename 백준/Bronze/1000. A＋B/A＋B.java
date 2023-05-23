import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 두 정수 A와 B 입력 받기
        int A = scanner.nextInt();
        int B = scanner.nextInt();

        // A와 B의 합 계산
        int sum = A + B;

        // 합 출력
        System.out.println(sum);
    }
}
