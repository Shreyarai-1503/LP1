import java.io.*;
import java.util.ArrayList;

class TableRow {
    String symbol;
    int address;
    int index;

    public TableRow(String symbol, int address, int index) {
        this.symbol = symbol;
        this.address = address;
        this.index = index;
    }
}

public class Pass_2 {
    private ArrayList<TableRow> SYMTAB = new ArrayList<>();
    private ArrayList<TableRow> LITTAB = new ArrayList<>();

    private void readTables() {
        try {
            SYMTAB = readTable("SYMTAB.txt");
            LITTAB = readTable("LITTAB.txt");
        } catch (Exception e) {
            System.out.println("Error reading tables: " + e.getMessage());
        }
    }

    private ArrayList<TableRow> readTable(String filename) throws IOException {
        ArrayList<TableRow> table = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                table.add(new TableRow(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[0])));
            }
        }
        return table;
    }

    private void generateCode(String filename) throws IOException {
        readTables();
        try (BufferedReader br = new BufferedReader(new FileReader(filename));
             BufferedWriter bw = new BufferedWriter(new FileWriter("PASS2.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                processInstruction(line.trim().split("\\s+"), bw);
            }
        }
    }

    private void processInstruction(String[] parts, BufferedWriter bw) throws IOException {
        if (parts[0].contains("AD") || parts[0].contains("DL,02")) {
            bw.newLine();
        } else if (parts[0].contains("DL") && parts.length == 2) {
            handleDeclarative(parts, bw);
        } else if (parts[0].contains("IS")) {
            handleImperative(parts, bw);
        }
    }

    private void handleDeclarative(String[] parts, BufferedWriter bw) throws IOException {
        int constant = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
        bw.write(String.format("00\t0\t%03d\n", constant));
    }

    private void handleImperative(String[] parts, BufferedWriter bw) throws IOException {
        int opcode = Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
        if (parts.length == 1) {
            bw.write(String.format("%02d\t0\t000\n", opcode));
        } else if (parts.length == 2) {
            bw.write(formatInstruction(opcode, 0, getAddress(parts[1])));
        } else if (parts.length == 3) {
            int regcode = Integer.parseInt(parts[1]);
            bw.write(formatInstruction(opcode, regcode, getAddress(parts[2])));
        }
    }

    private String formatInstruction(int opcode, int regcode, int address) {
        return String.format("%02d\t%d\t%03d\n", opcode, regcode, address);
    }

    private int getAddress(String part) {
        int index = Integer.parseInt(part.replaceAll("[^0-9]", "")) - 1;
        return part.contains("S") ? SYMTAB.get(index).address : LITTAB.get(index).address;
    }

	public static void main(String[] args) {
        try {
            new Pass_2().generateCode("IC.txt");
            System.out.println("PASS2.txt generated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}