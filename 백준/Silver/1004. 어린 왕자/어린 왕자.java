import java.util.Scanner;
/**
 * 백준 전체 1004 문제 알고리즘 클래스
 *
 * @author RWB
 * @see <a href="https://blog.itcode.dev/posts/2021/05/22/a1004">1004 풀이</a>
 * @since 2021.04.24 Sat 02:15:31
 */
public class Main
{
    /**
     * 메인 함수
     *
     * @param args: [String[]] 매개변수
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        
        int length = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < length; i++)
        {
            String base = scanner.nextLine();
            
            int x_start = Integer.parseInt(base.split(" ")[0]);
            int y_start = Integer.parseInt(base.split(" ")[1]);
            
            int x_end = Integer.parseInt(base.split(" ")[2]);
            int y_end = Integer.parseInt(base.split(" ")[3]);
            
            int through = 0;
            
            int count = scanner.nextInt();
            scanner.nextLine();
            
            for (int j = 0; j < count; j++)
            {
                String circle = scanner.nextLine();
                
                int x = Integer.parseInt(circle.split(" ")[0]);
                int y = Integer.parseInt(circle.split(" ")[1]);
                int r = Integer.parseInt(circle.split(" ")[2]);
                
                boolean hasStartContain = hasContain(x_start, y_start, x, y, r);
                boolean hasEndContain = hasContain(x_end, y_end, x, y, r);
                
                // 해당 행성이 출발 혹은 도착점 중 하나만을 포함할 경우
                if (!(hasStartContain && hasEndContain) && (hasStartContain || hasEndContain))
                {
                    through++;
                }
            }
            
            System.out.println(through);
        }
        
        scanner.close();
    }
    
    /**
     * 출발/도착점 포함 여부 반환 함수
     *
     * @param xo: [int] 출발/도착점의 x좌표
     * @param yo: [int] 출발/도착점의 y좌표
     * @param x: [int] 행성의 x좌표
     * @param y: [int] 행성의 y좌표
     * @param r: [int] 행성의 반지름
     *
     * @return [boolean] 출발/도착점 포함 여부
     */
    private static boolean hasContain(int xo, int yo, int x, int y, int r)
    {
        return Math.sqrt(Math.pow(xo - x, 2) + Math.pow(yo - y, 2)) < r;
    }
}
