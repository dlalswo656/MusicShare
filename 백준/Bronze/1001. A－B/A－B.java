import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 정수 A 입력 받기
        int A = scanner.nextInt();

        // 정수 B 입력 받기
        int B = scanner.nextInt();

        // A - B 계산
        int result = A - B;

        // 결과 출력
        System.out.println(result);

        scanner.close();
    }
}
