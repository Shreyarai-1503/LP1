import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Vector;

class MNTEntry {
    String name;
    int pp, kp, mdtp, kpdtp;

    public MNTEntry(String name, int pp, int kp, int mdtp, int kpdtp) {
        this.name = name;
        this.pp = pp;
        this.kp = kp;
        this.mdtp = mdtp;
        this.kpdtp = kpdtp;
    }
}

public class MacroPass2 {
    public static void main(String[] args) throws Exception {
        // File Readers and Writers
        BufferedReader irb = new BufferedReader(new FileReader("intermediate.txt"));
        BufferedReader mdtb = new BufferedReader(new FileReader("mdt.txt"));
        BufferedReader kpdtb = new BufferedReader(new FileReader("kpdt.txt"));
        BufferedReader mntb = new BufferedReader(new FileReader("mnt.txt"));
        FileWriter fr = new FileWriter("pass2.txt");

        // Data Structures
        HashMap<String, MNTEntry> mnt = new HashMap<>();
        HashMap<Integer, String> aptab = new HashMap<>();        //actual parameter values
        HashMap<String, Integer> aptabInverse = new HashMap<>(); //actual parameter names
        Vector<String> mdt = new Vector<>();
        Vector<String> kpdt = new Vector<>();

        // Reading MDT and KPDT files
        String line;
        while ((line = mdtb.readLine()) != null) {
            if (!line.trim().isEmpty()) { // Avoid empty lines
                mdt.add(line);
            }
        }

        while ((line = kpdtb.readLine()) != null) {
            if (!line.trim().isEmpty()) { // Avoid empty lines
                kpdt.add(line);
            }
        }

        // Reading MNT file into HashMap
        while ((line = mntb.readLine()) != null) {
            String[] parts = line.split("\\s+");
            mnt.put(parts[0], new MNTEntry(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
        }
        // Processing Intermediate File
        while ((line = irb.readLine()) != null) {
            String[] parts = line.split("\\s+");

            if (mnt.containsKey(parts[0])) { // Macro encountered
                MNTEntry entry = mnt.get(parts[0]);
                int pp = entry.pp, kp = entry.kp, mdtp = entry.mdtp, kpdtp = entry.kpdtp;
                int paramNo = 1;

                // Process Positional Parameters
                for (int i = 0; i < pp; i++) {
                    parts[paramNo] = parts[paramNo].replace(",", "");
                    aptab.put(paramNo, parts[paramNo]);
                    aptabInverse.put(parts[paramNo], paramNo);
                    paramNo++;
                }

                // Process Keyword Parameters
                int j = kpdtp - 1;
                for (int i = 0; i < kp; i++) {
                    String[] temp = kpdt.get(j).split("\t");
                    aptab.put(paramNo, temp[1]);
                    aptabInverse.put(temp[0], paramNo);
                    j++;
                    paramNo++;
                }

                // Handle remaining parameters with default values (name=value)
                for (int i = pp + 1; i < parts.length; i++) {
                    parts[i] = parts[i].replace(",", "");
                    String[] splits = parts[i].split("=");
                    String name = splits[0].replaceAll("&", "");
                    aptab.put(aptabInverse.get(name), splits[1]);
                }

                // Writing the Macro Definition (MDT) to the output file
                int i = mdtp - 1;
                while (i < mdt.size() && !mdt.get(i).equalsIgnoreCase("MEND")) {
                    String[] splits = mdt.get(i).split("\\s+");
                    fr.write("+");
                    for (String token : splits) {
                        if (token.contains("(P,")) { // Parameter reference
                            token = token.replaceAll("[^0-9]", "");
                            String value = aptab.get(Integer.parseInt(token));
                            fr.write(value + "\t");
                        } else {
                            fr.write(token + "\t");
                        }
                    }
                    fr.write("\n");
                    i++;
                }

                // Clear the temporary parameter table for next macro
                aptab.clear();
                aptabInverse.clear();

            } else { // If it's not a macro, just write the line to the output
                fr.write(line + "\n");
            }
        }

        // Close all resources
        fr.close();
        mntb.close();
        mdtb.close();
        kpdtb.close();
        irb.close();

        System.out.println("Macro Pass2 Processing done.");
    }
}