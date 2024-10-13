import java.util.Arrays;
import java.util.Scanner;

public class FIFO {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();
        int pages[] = new int[n];

        System.out.println("Enter the pages:");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        System.out.print("Enter the frame size: ");
        int capacity = sc.nextInt();
        
        int frames[] = new int[capacity];
        Arrays.fill(frames, -1);
        int hit = 0, fault = 0, index = 0;

        System.out.println("\n----------------------------------------------------------------------");

        for (int page : pages) {
            boolean isHit = false;

            for (int frame : frames) {
                if (frame == page) {
                    isHit = true;
                    hit++;
                    //System.out.print("H ");
                    System.out.println( page + "\tHit\t" + Arrays.toString(frames));
                    break;
                }
            }

            if (!isHit) {
                frames[index] = page;
                fault++;
                //System.out.print("F ");
                index = (index + 1) % capacity;
                System.out.println( page + "\tFault\t" + Arrays.toString(frames));
            }
        }

        System.out.println("\n----------------------------------------------------------------------");
        double faultRatio = ((double) fault / n) * 100;
        double hitRatio = ((double) hit / n) * 100;
        System.out.println("Page Fault: " + fault + "\nPage Hit: " + hit);
        System.out.println("Hit Ratio: " + hitRatio + "\nFault Ratio: " + faultRatio);
        sc.close();
    }
}
