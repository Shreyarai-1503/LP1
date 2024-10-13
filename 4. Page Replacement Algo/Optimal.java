import java.util.Arrays;
import java.util.Scanner;

public class Optimal {
    private static int emptyIndex(int[] frames) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    private static int findOptimalPageToReplace(int[] frames, int[] pages, int currentIndex, int n) {
        int indexToReplace = -1;
        int farthest = -1;

        for (int i = 0; i < frames.length; i++) {
            int j;

            for (j = currentIndex + 1; j < n; j++) { 
                if (pages[j] == frames[i]) {
                    break;
                }
            }

            if (j == n) {  //not found
                return i;
            }

            if (j > farthest) {
                farthest = j;
                indexToReplace = i;
            }
        }

        return indexToReplace != -1 ? indexToReplace : 0;
    }

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

        int hit = 0, fault = 0;

        System.out.println("\n----------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            int page = pages[i];
            boolean isHit = false;

            for (int j = 0; j < capacity; j++) {
                if (frames[j] == page) {
                    isHit = true;
                    hit++;
                    System.out.println(page + "\tHit\t" + Arrays.toString(frames));
                    break;
                }
            }

            if (!isHit) {
                fault++;

                if (emptyIndex(frames) != -1) {
                    frames[emptyIndex(frames)] = page;
                } else {// optimal replacement
                    int idx = findOptimalPageToReplace(frames, pages, i, n);
                    frames[idx] = page;
                }

                System.out.println(page + "\tFault\t" + Arrays.toString(frames));
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