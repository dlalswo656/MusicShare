import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 백준 전체 1006 문제 알고리즘 클래스
 *
 * @author RWB
 * @see <a href="https://blog.itcode.dev/posts/2021/06/06/a1006">1006 풀이</a>
 * @since 2021.06.06 Sun 22:44:45
 */
public class Main
{
    private static int N;
    private static int W;
    
    private static int[][] e;
    
    private static int[] a;
    private static int[] b;
    private static int[] c;
    
    /**
     * 메인 함수
     *
     * @param args: [String[]] 매개변수
     *
     * @throws IOException 데이터 입출력 예외
     */
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        // 케이스 수
        int T = Integer.parseInt(reader.readLine());
        
        for (int i = 0; i < T; i++)
        {
            int result = 2147483647;
            
            String[] temp = reader.readLine().split(" ");
            
            // 행 수
            N = Integer.parseInt(temp[0]);
            
            // 특수소대원 수
            W = Integer.parseInt(temp[1]);
            
            // 구역별 적 배열
            e = new int[2][N];
            
            for (int j = 0; j < 2; j++)
            {
                temp = reader.readLine().split(" ");
                
                for (int k = 0; k < N; k++)
                {
                    e[j][k] = Integer.parseInt(temp[k]);
                }
            }
            
            a = new int[N];
            b = new int[N];
            c = new int[N + 1];
            
            a[0] = 1;
            b[0] = 1;
            c[0] = 0;
            
            // 인덱스 0부터 시작
            solve(0);
            
            result = Math.min(result, c[N]);
            
            // 두 행 이상일 경우
            if (N > 1)
            {
                // 두 행 모두 걸칠 경우
                if (e[0][0] + e[0][N - 1] <= W && e[1][0] + e[1][N - 1] <= W)
                {
                    a[1] = 1;
                    b[1] = 1;
                    c[1] = 0;
                    
                    // 인덱스 1부터 시작 (1까지 초기값이 있기 때문)
                    solve(1);
                    
                    result = Math.min(result, c[N - 1] + 2);
                }
                
                // 윗 행만 걸칠 경우
                if (e[0][0] + e[0][N - 1] <= W)
                {
                    a[1] = 2;
                    b[1] = e[1][0] + e[1][1] > W ? 2 : 1;
                    c[1] = 1;
                    
                    // 인덱스 1부터 시작 (1까지 초기값이 있기 때문)
                    solve(1);
                    
                    result = Math.min(result, b[N - 1] + 1);
                }
                
                // 아래 행만 걸칠 경우
                if (e[1][0] + e[1][N - 1] <= W)
                {
                    a[1] = e[0][0] + e[0][1] > W ? 2 : 1;
                    b[1] = 2;
                    c[1] = 1;
                    
                    // 인덱스 1부터 시작 (1까지 초기값이 있기 때문)
                    solve(1);
                    
                    result = Math.min(result, a[N - 1] + 1);
                }
            }
            
            System.out.println(result);
        }
        
        reader.close();
    }
    
    /**
     * 알고리즘 함수
     *
     * @param num: [int] 시작 인덱스
     */
    private static void solve(int num)
    {
        for (int i = num; i < N; i++)
        {
            c[i + 1] = Math.min(a[i] + 1, b[i] + 1);
            
            // c팀이 인접한 두 개의 구역을 점령할 수 있을 경우
            if (e[0][i] + e[1][i] <= W)
            {
                c[i + 1] = Math.min(c[i + 1], c[i] + 1);
            }
            
            // c팀이 인접한 두개의 구역 2개를 점령할 수 있을 경우
            if (i > 0 && e[0][i - 1] + e[0][i] <= W && e[1][i - 1] + e[1][i] <= W)
            {
                c[i + 1] = Math.min(c[i + 1], c[i - 1] + 2);
            }
            
            // a, b팀의 인덱스 보정 (c팀은 인덱스가 하나 더 많음)
            if (i < N - 1)
            {
                a[i + 1] = c[i + 1] + 1;
                b[i + 1] = c[i + 1] + 1;
                
                // a팀이 인접한 두 개의 구역을 점령할 수 있을 경우
                if (e[0][i] + e[0][i + 1] <= W)
                {
                    a[i + 1] = Math.min(a[i + 1], b[i] + 1);
                }
                
                // b팀이 인접한 두 개의 구역을 점령할 수 있을 경우
                if (e[1][i] + e[1][i + 1] <= W)
                {
                    b[i + 1] = Math.min(b[i + 1], a[i] + 1);
                }
            }
        }
    }
}
