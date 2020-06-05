import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ProgramareDinamica1 {
    /* Enunt

    Sa se determine numarul de cuvinte de n litere care se pot construi cu
    literele x,y si z, astfel incat literele x si y sa nu fie alături, si nicio litera sa
    nu apara pe pozitii consecutive
    Date de intrare: fișierul date.in conține numărul natural n
    Date de ieșire: fișierul date.out conține valoarea ceruta.
    Restricții și precizări:
    n apartine [1,40]
    Exemplu:
    date.in date.out explicatie
    4 8 Cuvintele sunt
    zxzx, zyzx
    zxzy, zyzy
    xzxz, yzxz, xzyz, yzyz


    */

    public static void main(String[] args) {

        int N = 0;
        try {
            N = genereazaDateProblema("date.in");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        try {

            int raspuns = new WordsGeneratorProblem().run("xyz", N);
            System.out.println(raspuns);
            scrieRezolvare("date.out", raspuns);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int genereazaDateProblema(String file_name) throws IOException {
        File f = new File(file_name);
        if (f.exists())
            f.delete();

        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        Random randomTool = new Random();
        int N = randomTool.nextInt(9) + 1;
        bw.write(String.valueOf(N));
        bw.flush();
        bw.close();
        fw.close();
        return N;
    }

    private static void scrieRezolvare(String file_name, int raspuns) throws IOException {
        File f = new File(file_name);
        if (f.exists())
            f.delete();

        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(String.valueOf(raspuns));
        bw.flush();
        bw.close();
        fw.close();
    }

}

class WordsGeneratorProblem {
    String[] symbols;
    ArrayList<String> results;
    int n;

    public int run(String dict, int N) {
        n = N;
        symbols = dict.split("");
        results = new ArrayList<>();
        generateWords("");
        return results.size();
    }

    private void generateWords(String current_seq) {
        if (current_seq.length() == n) {
            results.add(current_seq);
            System.out.println(current_seq);
            return;
        }
        String last_element = "";
        if (current_seq.length() > 0) {
            last_element = "" + current_seq.charAt(current_seq.length() - 1);
        }

        for (int i = 0; i < symbols.length; i++) {

            //conditia de validitate pe care intentionam sa-l adaugam la secventa pe care o construim
            // sa nu fie la fel ca ultimul element, sa nu se formeze combinatia de x & y si invers
            if (!symbols[i].equals(last_element)
                    && !(symbols[i].equals("x") && last_element.equals("y"))
                    && !(symbols[i].equals("y") && last_element.equals("x"))) {

                generateWords(current_seq + symbols[i]);
            }
        }

    }
}

