import java.util.Scanner;

public class Main {
	static int map[][];
	static boolean visit[][];
	static int M, N, K;	// 가로, 세로, 배추 갯수 
	static int X, Y;	// 배추의 위치
	static int dx[] = {0, -1, 0, 1};	// 상하좌우
	static int dy[] = {-1, 0, 1, 0};	// 상하좌우
	static int count;	// 최소 필요한 배추흰지렁이 마리 수
	
	public static void dfs(int y, int x) {
		visit[y][x] = true;
		for(int i=0; i<4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			// nx, ny = 좌표의 범위, M*N 크기이므로 x, y좌표가 0보단 커야하고 M, N보단 작아야함.
			if(nx>=0 && ny>=0 && nx<M && ny<N) {
				if(map[ny][nx] == 1 && !visit[ny][nx]) {
					dfs(ny, nx);
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int i=0; i<T; i++) {
			M = scan.nextInt();	// 가로
			N = scan.nextInt();	// 세로
			K = scan.nextInt();	// 배추 갯수
			map = new int[N][M];
			visit = new boolean[N][M];
			count = 0;
			
			for(int j=0; j<K; j++) {
				int a = scan.nextInt();	// 가로
				int b = scan.nextInt();	// 세로
				map[b][a] = 1;
			}
			
			for(int j=0; j<N; j++) {
				for(int k=0; k<M; k++) {
					if(map[j][k] == 1 && visit[j][k] == false) {
						dfs(j, k);
						count ++;
					}
				}
			}
			System.out.println(count);
		}
		scan.close();
	}
}