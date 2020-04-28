

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class Bkt {

    /*  Enunt Problema

        In fisierul p3.in se genereaza un numar natural mai mare decat 4.
        Sa se gaseasca toate permutarile din multimea {1, 2,..., n} cu
        proprietatea ca modulul diferentei a doua elemente alaturate este
        cel putin 2 si sa se afiseze in p3.out. (3p)
        p3.in
        4
        p3.out
        2 4 1 3
        3 1 4 2
     */


    public static void main(String[] args) {

        (new Bkt()).rezolvareProblema();

    }

    public void rezolvareProblema() {

        int N = 0;

        try {
            Random randomTool = new Random();
            File in = new File("p3.in");
            if (in.exists()) {
                in.delete();
            }
            in.createNewFile();

            FileWriter fw = new FileWriter("p3.in");
            String run_value = String.valueOf(randomTool.nextInt(10) + 1);
            fw.write(run_value);
            fw.flush();
            fw.close();


            FileReader fr = new FileReader(in);
            BufferedReader br = new BufferedReader(fr);
            N = Integer.parseInt(br.readLine().trim());
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Exceptie la partea de citire " + e.getLocalizedMessage());
            System.exit(0);
        }

        int[][] elements = new int[N][2];

        for (int i = 0; i < elements.length; i++) {
            elements[i][0] = i + 1;
            elements[i][1] = 0; // coloana 1 este folosita ca vector de flag-uri pentru a marca daca elementul a fost deja folosit sau nu in secventa care se construieste
        }

        ArrayList<String> solutions_set = new ArrayList<>();

        for (int i = 0; i < elements.length; i++) {
            elements[i][1] = 1;
            compute(elements, String.valueOf(elements[i][0]), elements[i][0], solutions_set);
            elements[i][1] = 0;
        }

        try {
            File out = new File("p3.out");
            if (out.exists()) {
                out.delete();
            }
            out.createNewFile();
            FileWriter fw = new FileWriter(out);
            for (String solution : solutions_set) {
                fw.write(solution);
                fw.write("\r\n");
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("Exceptie la partea de scriere " + e.getLocalizedMessage());
            System.exit(0);

        }

    }

    public void compute(int[][] elements, String built_sequence, int last_element, ArrayList<String> solutions_set) {
        if (built_sequence == null || built_sequence.length() == 0) {
            System.out.println("built_sequence nu poate fi gol sau null");
            return;
        }
        if (built_sequence.length() == elements.length) {
            solutions_set.add(built_sequence);
            return;
        }
        for (int i = 0; i < elements.length; i++) {
            if (elements[i][1] == 0 && Math.abs(elements[i][0] - last_element) >= 2) {
                elements[i][1] = 1;
                compute(elements, built_sequence + String.valueOf(elements[i][0]), elements[i][0], solutions_set);
                elements[i][1] = 0;
            }
        }


    }


}
