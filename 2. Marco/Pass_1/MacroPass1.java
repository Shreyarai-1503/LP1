import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class MacroPass1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("macro_input.asm"));

        FileWriter mnt = new FileWriter("mnt.txt");
        FileWriter mdt = new FileWriter("mdt.txt");
        FileWriter kpdt = new FileWriter("kpdt.txt");
        FileWriter pnt = new FileWriter("pnt.txt");
        FileWriter ir = new FileWriter("intermediate.txt");

        LinkedHashMap<String, Integer> pntab = new LinkedHashMap<>();
        String line;
        String macroname = null;
        int mdtp = 1, kpdtp = 0, paramNo = 1, pp = 0, kp = 0, flag = 0;  // pointers and flags

        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");

            if (parts[0].equalsIgnoreCase("MACRO")) {  // Starting a new macro definition
                flag = 1;
                line = br.readLine();  // Read the next line (macro name and parameters)
                parts = line.split("\\s+");
                macroname = parts[0];

                if (parts.length > 1) { // Handle macro parameters
                    for (int i = 1; i < parts.length; i++) {
                        parts[i] = parts[i].replaceAll("[&,]", "");
                        if (parts[i].contains("=")) {  // Keyword parameter
                            kp++;
                            String[] keywordParam = parts[i].split("=");
                            pntab.put(keywordParam[0], paramNo++);
                            kpdt.write(keywordParam[0] + "\t" + (keywordParam.length == 2 ? keywordParam[1] : "-") + "\n");
                        } else {  // Positional parameter
                            pntab.put(parts[i], paramNo++);
                            pp++;
                        }
                    }
                }
                // Write to MNT
                mnt.write(macroname + "\t" + pp + "\t" + kp + "\t" + mdtp + "\t" + (kp == 0 ? kpdtp : kpdtp + 1) + "\n");
                kpdtp += kp;

            } else if (parts[0].equalsIgnoreCase("MEND")) {  // End of macro definition
                mdt.write(line + "\n");
                flag = 0;
                mdtp++;
                paramNo = 1;

                // Write to PNT
                pnt.write(macroname + ":\t");
                for (String key : pntab.keySet()) {
                    pnt.write(key + "\t");
                }
                pnt.write("\n");

                // Clear the parameter table for the next macro
                pntab.clear();

            } else if (flag == 1) {  // Inside macro body
                for (String part : parts) {
                    if (part.contains("&")) {  // If it's a parameter
                        part = part.replaceAll("[&,]", "");
                        mdt.write("(P," + pntab.get(part) + ")\t");
                    } else {  // Otherwise, just write the part as is
                        mdt.write(part + "\t");
                    }
                }
                mdt.write("\n");
                mdtp++;

            } else {  // Regular source code line
                ir.write(line + "\n");
            }
        }

        br.close();
        mdt.close();
        mnt.close();
        pnt.close();
        kpdt.close();
        ir.close();
        System.out.println("Macro Pass1 Processing done. :)");
    }
}