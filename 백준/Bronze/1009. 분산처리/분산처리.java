import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int T = s.nextInt();
		for(int j=0;j<T;j++) {
			int a=s.nextInt();
			int b=s.nextInt();
			int c=0;
			int answer=1;
			
			if(a%10 == 0 || a%10 == 1 || a%10 == 5 || a%10 == 6) {
				answer=a%10;
			}else if(a%10 == 4 || a%10 == 9) {
				c = b%2;
				if (c==0) c=2;
			}else {
				c = b%4;
				if (c==0) c=4;
			}
			
			for (int i=0;i<c;i++) answer=(answer*a)%10;
			if (answer==0) answer = 10;
			System.out.println(answer);
		}
		s.close();
	}
}