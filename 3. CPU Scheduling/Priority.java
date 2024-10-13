import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Priority {  //lower number means higher priority
    public static void main(String[] args) {
        int n, currTime = 0;
        float total_TAT = 0, total_WT = 0;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();

        int[][] processes = new int[n][4];  //[ID, AT, BT, Priority]
        int[] CT = new int[n];
        int[] TAT = new int[n];
        int[] WT = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("\nProcess " + (i + 1) + ": ");
            processes[i][0] = i + 1;
            System.out.print("Enter arrival time: ");
            processes[i][1] = sc.nextInt();
            System.out.print("Enter CPU burst time: ");
            processes[i][2] = sc.nextInt();
            System.out.print("Enter Priority: ");
            processes[i][3] = sc.nextInt();
        }

        Arrays.sort(processes, Comparator.comparingInt((int[] p) -> p[1])  //Arrival time of each sub-array (p[1]).
        .thenComparingInt(p -> p[3]));  //If two sub-arrays have the same value for p[1], they are then sorted based on the Priority.

        for (int i = 0; i < n; i++) {
            if(currTime < processes[i][1]){
                currTime = processes[i][1];
            }

            currTime += processes[i][2];
            CT[i] = currTime;
            TAT[i] = CT[i] - processes[i][1];
            WT[i] = TAT[i] - processes[i][2];

            total_TAT += TAT[i];
            total_WT += WT[i];
        }
        
        System.out.println("\nProcess\tArrival Time\tBurst Time\tPriority\tCompletion Time\tTurn Around Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println(processes[i][0] + "\t\t" + processes[i][1] + "\t\t" + processes[i][2] + "\t\t" + processes[i][3] + "\t\t" + CT[i]
                    + "\t\t" + TAT[i] + "\t\t" + WT[i]);
        }

        float avg_TAT = total_TAT / n;
        float avg_WT = total_WT / n;

        System.out.println("\nAverage Turnaround Time: " + avg_TAT);
        System.out.println("\nAverage Waiting Time: " + avg_WT);

        sc.close();
    }
}

// Enter number of processes: 3

// Process 1: Enter arrival time: 1
// Enter CPU burst time: 3
// Enter Priority: 1

// Process 2: Enter arrival time: 0
// Enter CPU burst time: 4
// Enter Priority: 3

// Process 3: Enter arrival time: 4
// Enter CPU burst time: 3
// Enter Priority: 2

// Process Arrival Time    Burst Time      Priority        Completion Time Turn Around Time        Waiting Time
// 2               0               4               3               4               4               0
// 1               1               3               1               7               6               3
// 3               4               3               2               10              6               3

// Average Turnaround Time: 5.3333335

// Average Waiting Time: 2.0