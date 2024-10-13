import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RoundRobin { // Preemptive - intrrupted
    public static void main(String[] args) {
        int completed = 0, currTime = 0;
        float total_TAT = 0, total_WT = 0;
        Queue<Integer> q = new LinkedList<>();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter Time Quantum: ");
        int t = sc.nextInt();

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

        while(completed < n){
            for (int i = 0; i < n; i++) {
                if(AT[i] <= currTime && remBT[i] > 0 && !q.contains(i)){
                    q.add(i);
                }
            }

            if(!q.isEmpty()){
                int currProcess = q.poll();

                if(remBT[currProcess] > t){
                    currTime += t;
                    remBT[currProcess] -= t;
                    q.add(currProcess);
                } else{
                    currTime += remBT[currProcess];
                    CT[currProcess] = currTime;
                    TAT[currProcess] = CT[currProcess] - AT[currProcess];
                    WT[currProcess] = TAT[currProcess] - BT[currProcess];
                    remBT[currProcess] = 0;
                    total_TAT += TAT[currProcess];
                    total_WT += WT[currProcess];
                    completed++;
                }
            } else {
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
// Enter Time Quantum: 2
// Enter arrival time for process 1: 0
// Enter CPU burst time for process 1: 10
// Enter arrival time for process 2: 0
// Enter CPU burst time for process 2: 5
// Enter arrival time for process 3: 0
// Enter CPU burst time for process 3: 8

// Process Arrival Time    Burst Time      Completion Time Turn Around Time   Waiting Time
// 1               0               10              23              23         13
// 2               0               5               15              15         10
// 3               0               8               21              21         13

// Average Turnaround Time: 19.666666

// Average Waiting Time: 12.0