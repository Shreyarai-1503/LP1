import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class LRU {
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

        HashMap<Integer, Integer> pageMap = new HashMap<>(); // Stores page and its index
        LinkedList<Integer> lruQueue = new LinkedList<>(); // Keeps track of page access order

        int hit = 0, fault = 0;

        System.out.println("\n----------------------------------------------------------------------");

        for (int page : pages) {
            if (pageMap.containsKey(page)) {
                hit++;
                lruQueue.remove((Integer) page); // Recently used
                lruQueue.add(page); //add to the end
                System.out.println(page + "\tHit\t" + Arrays.toString(frames));
            } else {
                fault++;

                if (lruQueue.size() == capacity) {
                    int lruPage = lruQueue.removeFirst();
                    int lruIndex = pageMap.get(lruPage);
                    frames[lruIndex] = page;
                    pageMap.remove(lruPage);
                } else {
                    frames[lruQueue.size()] = page;
                }
                lruQueue.add(page);
                pageMap.put(page, lruQueue.size() - 1);
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