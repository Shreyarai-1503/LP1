import java.util.Arrays;

public class BankersAlgo {
    private static boolean canAllocate(int process, int[][] need, int[] work, int numResources) {
        for (int j = 0; j < numResources; j++)
            if (need[process][j] > work[j])
                return false;
        return true;
    }
    public static void main(String[] args) {
        int[][] allocation = {{0, 1, 0}, {2, 0, 0}, {3, 0, 2}, {2, 1, 1}, {0, 0, 2}};
        int[][] max_need = {{7, 5, 3}, {3, 2, 2}, {9, 0, 2}, {4, 2, 2}, {5, 3, 3}};
        int[] available = {3, 3, 2};
        int numProcesses = allocation.length, numResources = available.length;

        int[][] need = new int[numProcesses][numResources];  //remaining need
        int[] work = Arrays.copyOf(available, numResources);  //current available 
        int[] safeSequence = new int[numProcesses];
        boolean[] finish = new boolean[numProcesses];

        // Calculate the need matrix
        for (int i = 0; i < numProcesses; i++)
            for (int j = 0; j < numResources; j++)
                need[i][j] = max_need[i][j] - allocation[i][j];

        int count = 0;
        while (count < numProcesses) {
            boolean found = false;
            for (int p = 0; p < numProcesses; p++) {
                if (!finish[p] && canAllocate(p, need, work, numResources)) {
                    for (int j = 0; j < numResources; j++)
                        work[j] += allocation[p][j];
                    safeSequence[count++] = p+1;
                    finish[p] = true;
                    found = true;
                }
            }
            if (!found) {
                System.out.println("System is in an unsafe state.");
                return;
            }
        }

        System.out.println("System is in a safe state. Safe sequence: " + Arrays.toString(safeSequence));
    }
}