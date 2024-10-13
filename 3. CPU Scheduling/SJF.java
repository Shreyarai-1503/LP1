import java.util.Scanner;

public class SJF { // Preemptive - can be intrrupted
    public static void main(String[] args) {
        int currTime = 0, completed = 0;
        float total_TAT = 0, total_WT = 0;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] AT = new int[n];
        int[] BT = new int[n];
        int[] remBT = new int[n];
        int[] CT = new int[n];
        int[] TAT = new int[n];
        int[] WT = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            AT[i] = sc.nextInt();
            System.out.print("Enter CPU burst time for process " + (i + 1) + ": ");
            BT[i] = sc.nextInt();
            remBT[i] = BT[i];
        }

        while (completed < n) {
            int idx = -1; // find smallest remaining time
            int minRemBT = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (AT[i] <= currTime && remBT[i] > 0 && remBT[i] < minRemBT) {
                    minRemBT = remBT[i];
                    idx = i;
                }
            }
            if (idx != -1) { // process found
                remBT[idx]--;
                currTime++;

                if (remBT[idx] == 0) {
                    CT[idx] = currTime;
                    TAT[idx] = CT[idx] - AT[idx];
                    WT[idx] = TAT[idx] - BT[idx];
                    total_TAT += TAT[idx];
                    total_WT += WT[idx];
                    completed++;
                }
            } else { // no process found
                currTime++;
            }
        }
        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tTurn Around Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println((i+1) + "\t\t" + AT[i] + "\t\t" + BT[i] + "\t\t" + CT[i]+ "\t\t" + TAT[i] + "\t\t" + WT[i]);
        }

        float avg_TAT = total_TAT / n;
        float avg_WT = total_WT / n;

        System.out.println("\nAverage Turnaround Time: " + avg_TAT);
        System.out.println("\nAverage Waiting Time: " + avg_WT);

        sc.close();
    }
}

// Enter number of processes: 3
// Enter arrival time for process 1: 0
// Enter CPU burst time for process 1: 8
// Enter arrival time for process 2: 1
// Enter CPU burst time for process 2: 4
// Enter arrival time for process 3: 2
// Enter CPU burst time for process 3: 9

// Process Arrival Time    Burst Time      Completion Time Turn Around Time        Waiting Time
// 1               0               8               12              12              4
// 2               1               4               5               4               0
// 3               2               9               21              19              10

// Average Turnaround Time: 11.666667

// Average Waiting Time: 4.6666665