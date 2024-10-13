import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class FCFS {
    public static void main(String[] args) {
        int n, currTime = 0;
        float total_TAT = 0, total_WT = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of processes");
        n = sc.nextInt();

        int[][] processes = new int[n][3];
        int[] CT = new int[n];
        int[] TAT = new int[n];
        int[] WT = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nProcess " + (i + 1) + ": ");
            processes[i][0] = i + 1;
            System.out.println("Enter arrival time: ");
            processes[i][1] = sc.nextInt();
            System.out.println("Enter CPU burst time: ");
            processes[i][2] = sc.nextInt();
        }

        Arrays.sort(processes, Comparator.comparingInt(p -> p[1])); // sort based on arrival time

        for (int i = 0; i < n; i++) {
            if (currTime < processes[i][1]) {
                currTime = processes[i][1];
            }

            CT[i] = currTime + processes[i][2];
            TAT[i] = CT[i] - processes[i][1]; // TAT = CT-AT
            WT[i] = TAT[i] - processes[i][2]; // WT = TAT-BT

            total_TAT += TAT[i];
            total_WT += WT[i];

            currTime = CT[i];
        }

        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tTurn Around Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println(processes[i][0] + "\t\t" + processes[i][1] + "\t\t" + processes[i][2] + "\t\t" + CT[i]
                    + "\t\t" + TAT[i] + "\t\t" + WT[i]);
        }

        float avg_TAT = total_TAT / n;
        float avg_WT = total_WT / n;

        System.out.println("\nAverage Turnaround Time: " + avg_TAT);
        System.out.println("\nAverage Waiting Time: " + avg_WT);

        sc.close();

    }
}

// Enter number of processes
// 3

// Process 1: 
// Enter arrival time: 
// 2
// Enter CPU burst time: 
// 5

// Process 2: 
// Enter arrival time: 
// 0
// Enter CPU burst time: 
// 3

// Process 3: 
// Enter arrival time: 
// 1
// Enter CPU burst time:
// 4

// Process Arrival Time    Burst Time      Completion Time Turn Around Time        Waiting Time
// 2               0               3               3               3               0
// 3               1               4               7               6               2
// 1               2               5               12              10              5

// Average Turnaround Time: 6.3333335

// Average Waiting Time: 2.3333333