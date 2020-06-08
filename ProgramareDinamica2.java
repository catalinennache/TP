import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;




/*
Enunt Problema:

Garfield, motanul veşnic răpus de "oboseală", s-a gândit să viziteze o
parte din (sau chiar toate) casele de pe strada sa - nu neapărat pentru
a socializa, cât mai ales pentru biscuiţii pe care îi primeşte după fiecare
vizită! Casele sunt identificate prin numere de la 1 la N, cele cu numere
impare fiind pe partea stângă, iar cele cu numere pare pe partea
dreaptă (în ordine); el are o listă cu numarul de biscuiţi pe care îi
primeşte de la fiecare casă.
Să traversezi strada înseamnă mult efort şi bătaie de cap, însă motanul
nostru e hotărât să se autodepăşească; dar ... nu chiar dintr-o dată - în
periplul de care v-am povestit el va traversa strada de exact T ori. Ca
stresul să nu fie prea mare, Garfield vizitează casele doar in ordine
strict crescătoare a numarului lor şi poate începe de la oricare dintre
acestea. El va ţine cont de planul făcut în prealabil şi se va afla de la
început pe trotuarul pe care se află prima casă pe care o va vizita, iar in
finalul apoteotic al acţiunii va rămâne pe partea pe care se află ultima
casă vizitată. Orice intenţie de transformare în bine merită răsplătită:
ajută-l pe Garfield să obţină un numar maxim de biscuiţi!
Date de intrare: fisierul date.in contine
- pe prima linie N şi T (numărul de case şi numărul de traversări) -
numere naturale separate printr-un spaţiu
- pe a doua linie B 1 B 2 ... B N (B i este numărul de biscuiţi primiţi la casa cu
numarul i) -numere naturale separate prin câte un spaţiu
Date de ieșire: fișierul date.out conține
- pe prima linie: numărul maxim de biscuiţi primiţi în total
- pe a doua linie: numerele caselor vizitate, în ordine

Date exemplu
-----------------------------
Input
6 2
7 5 3 8 9 6
Output
29
1 2 4 5
Explicatie: a vizitat casa 1, a traversat la 2
şi 4, apoi a traversat la casa cu
numărul 5; a obţinut
29=7+5+8+9 biscuiţi
-----------------------------
Input
6 1
7 5 3 8 9 6
Output
26
1 2 4 6
*/

public class ProgramareDinamica2 {

    public static void main(String[] args) {
        boolean citire_de_la_tastatura = false;
        boolean generare_de_date = false;

        generare_de_date = args.length == 0;

        if(!generare_de_date){
            generare_de_date = args[0].equals("1");
            citire_de_la_tastatura = args.length >= 2 && args[1].equals("1");
        }
        GarfieldProblem gfp =  new GarfieldProblem();

        try{

          if(generare_de_date && !citire_de_la_tastatura) {
              gfp.genereazaDatele();
          }
            gfp.citesteDatele(citire_de_la_tastatura);
            gfp.rezolva();

           if(!citire_de_la_tastatura)
             gfp.scriereDate();
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

}


class GarfieldProblem {

    private int N;
    private int T;
    private int[] cookies_per_house; //util in a stii cate case sunt si ce prajituri ofera fiecare
    private int[] computed_remaining_cookies;
    private int cookies_in_the_bag;
    private int[][] case_si_treceri;
    private ArrayList<Integer> visited_houses;

    public GarfieldProblem(){
        N = 0;
        T = 0;
        cookies_in_the_bag = 0;

    }


    public void rezolva() {



        computed_remaining_cookies = computeRemainingCookies(cookies_per_house);
        case_si_treceri = new int[T+1][cookies_per_house.length];
        for(int i = 0;i< case_si_treceri.length;i++)
            for(int j =1; j<case_si_treceri[0].length;j++)
                case_si_treceri[i][j] = 0;



        System.out.println("\n**************");


        int start_house;


        if(f(1,T) > f(2,T)){
            start_house = 1;
        }else{
            start_house = 2;
        }

        visited_houses  = new ArrayList<>();

        // De aici 

        for(int i = start_house; i<cookies_per_house.length;i=i+2){
            cookies_in_the_bag += cookies_per_house[i];
            visited_houses.add(i);
            int res1 = f(i+2,T);
            int res2 = f(i+1,T-1);
            System.out.println("Comparing f("+(i+2)+","+T+") = "+res1+" and  f("+(i+1)+","+(T-1)+") = "+res2);
            boolean trec_strada = res1 <res2;
            //daca merita sa treaca strada
            if(trec_strada){
                i--; //decrementarea indexului cu o unitate inseamna schimbarea de la par la impar si invers (adica trecerea strazii)
                T--; //contorizam de cate ori a trecut strada
            }
        }

        //Pana aici avem O(n*n) (for-ul care executa de n ori functia f de complexitate n)
        //Functia f are complexitate O(n) pentru ca in bucla recursiva argumentele ei se modifica printr-o operatie de gradul 1
        //iar costul unei executii este O(1)

        System.out.println("Domeniile si codomeniul functiei f");

        for(int i = 0;i< case_si_treceri.length;i++) {
            for (int j = 1; j < case_si_treceri[0].length; j++)
                System.out.print(" [f("+j+","+i+") = " + case_si_treceri[i][j]+"] ");
            System.out.println();
        }

        System.out.println();


        System.out.println("Prajituri primite: "+cookies_in_the_bag);
        System.out.print("Case vizitate: ");
        visited_houses.forEach(x->{
            System.out.print(x+" ");
        });
        System.out.println("\n");

        //Complexitatea problemei este O(n^2)
    }

    //pentru fiecare pozitie posibila calculeaza cate prajituri va obtine daca din acel moment va merge numai pe acea parte pana la final
    public int[] computeRemainingCookies(int[] houses){
        int[] remCookies = new int[houses.length+1];

        for(int i = houses.length-1; i>=1;  i--){
            remCookies[i] = houses[i] +(i<houses.length-2?  remCookies[i+2] : 0);
            System.out.println(((i%2==0)?"                                   ":"")+"House "+i+" with "+houses[i]+" cookies, I will gain "+remCookies[i]+" if I stay on this side from now 'till the end");
        }

        return  remCookies;
    }


    public int f(int x,int T) {
       // System.out.print("computing f("+(x)+","+T+") = ");
        if(x < case_si_treceri[0].length && case_si_treceri.length > T && T>=0) {
            if (case_si_treceri[T][x] == 0) {
                if (T == 0) {
                    // prima ramura a functiei f din poza atasata
                    case_si_treceri[T][x] = computed_remaining_cookies[x];
                //    System.out.println("comp_r_c >> "+case_si_treceri[T][x]+"<<");
                    return computed_remaining_cookies[x];
                } else {
                    // ramura 2 a functiei f din poza atasata
                    case_si_treceri[T][x] = cookies_per_house[x] + max(f(x + 1, T - 1), f(x + 2, T));
                 //   System.out.println("<<"+case_si_treceri[T][x]+"="+(case_si_treceri[T][x]-cookies_per_house[x])+"+"+cookies_per_house[x]);
                    return case_si_treceri[T][x];
                }
            } else {
               // System.out.println(case_si_treceri[T][x]);
                return case_si_treceri[T][x];
            }
        }
      //  System.out.print("overflow");
        return 0;


    }
    public int max(int a, int b){
        return a>b?a:b;
    }
    public void genereazaDatele() throws IOException {
        System.out.print("Datele de intrare sunt generate in fisierul data.in...");
        File f = new File("data.in");
        if (f.exists())
            f.delete();

        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        Random randomTool = new Random();
        int N = randomTool.nextInt(12) + 1;
        this.N = N;
        int T = randomTool.nextInt(N);
        this. T = T;
        cookies_per_house = new int[N+1];
        for(int i =0; i<N;i++){
            this.cookies_per_house[i] = randomTool.nextInt(10);
        }
        bw.write(String.valueOf(N)+" "+String.valueOf(T));
        bw.newLine();
        for(int i = 0;i<N;i++){
            bw.write(this.cookies_per_house[i]+" ");
        }
        bw.flush();
        bw.close();
        fw.close();
        System.out.println("success!!");
    }

    public void scriereDate() throws IOException {
        System.out.print("Scriere date in fisierul data.out...");
        File f = new File("data.out");
        if (f.exists())
            f.delete();

        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(String.valueOf(this.cookies_in_the_bag));
        for(int i = 0; i < visited_houses.size(); i++){
            bw.write(visited_houses.get(i)+" ");
        }
        bw.flush();
        bw.close();
        fw.close();
        System.out.println("success!!");
    }

    public void citesteDatele(boolean citire_de_la_tastatura) throws IOException {
        if(!citire_de_la_tastatura) {
            System.out.print("Citirea se face din fisierul data.in...");
            File f = new File("data.in");
            if(!f.exists()){
                System.out.println("eroare >> fisierul nu exista");
                System.exit(0);

            }
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine().trim();
           
            String[] split_line = line.split(" ");
            this.N = Integer.parseInt(split_line[0]);
            this.T = Integer.parseInt(split_line[1]);
            
            line = br.readLine();
           
            split_line = line.trim().split(" ");
            cookies_per_house = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                this.cookies_per_house[i] = Integer.parseInt(split_line[i - 1]);
            }

            br.close();
            System.out.println("success!!\n");
            System.out.println("N si T: "+ this.N+" "+this.T+"\nPrajituri per casa: "+line+"\n************** \n");
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Dati N si T separate printr-un spatiu");
            String line = sc.nextLine().trim();
            String[] split_line = line.split(" ");
            this.N = Integer.parseInt(split_line[0]);
            this.T = Integer.parseInt(split_line[1]);
            System.out.println("Dati prajiturile pentru fiecare casa");
            line = sc.nextLine().trim();
            split_line = line.trim().split(" ");
            cookies_per_house = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                this.cookies_per_house[i] = Integer.parseInt(split_line[i - 1]);
            }
            System.out.println("\n************** \n");
        }
    }
}

/* 
Logica programului:

Am considerat ca cea mai buna metoda de abordare a problemei este sa precalculez pentru fiecare casa
cat va mai avea de castigat Garfield daca ramane pe aceeasi parte de la acea casa pana la final.

De asemenea fiecare casa vizitata, Garfield va apela functia f cu optiunile pe care le are.
Sa continue pastrandu-si numarul de traversari sau sa treaca si sa piarda o traversare.

Functia f intoarce numarul de prajituri care pot fi obtinute pentru casa x, tinand cont de numarul de treceri ramase.
Am retinut intr-o matrice, ale carei coloane reprezinta casele si ale carei linii reprezinta traversarile ramase dupa ce garfield a ajung in fata casei x,
pentru a curata arborele de executie de apelurile redundante.

Link cu pozele facute la ce am schitat pe foaie, pentru usurarea evaluarii:

https://drive.google.com/folderview?id=1gRyOI0MyinFgNjH3yylxphKC2Iu1gcHW

Executarea programului:

*Primul argument pentru executare se refera la generarea automata a datelor de input (1 sau absenta oricarui parametru inseamna ca generarea de date va fi activata)
*Al doilea argument se refera la modul de citire al datelor (1 inseamna citire de la tastatura si 0 din fisierul data.in)


------------------------------------------------------------------------------
Demo1 java ProgramareDinamica2.java 0 1
------------------------------------------------------------------------------
Dati N si T separate printr-un spatiu
6 4
Dati prajiturile pentru fiecare casa
1 1 1 1 1 1

**************

                                   House 6 with 1 cookies, I will gain 1 if I stay on this side from now 'till the end
House 5 with 1 cookies, I will gain 1 if I stay on this side from now 'till the end
                                   House 4 with 1 cookies, I will gain 2 if I stay on this side from now 'till the end
House 3 with 1 cookies, I will gain 2 if I stay on this side from now 'till the end
                                   House 2 with 1 cookies, I will gain 3 if I stay on this side from now 'till the end
House 1 with 1 cookies, I will gain 3 if I stay on this side from now 'till the end

**************
Comparing f(4,4) = 3 and  f(3,3) = 4
Comparing f(5,3) = 2 and  f(4,2) = 3
Comparing f(6,2) = 1 and  f(5,1) = 2
Comparing f(7,1) = 0 and  f(6,0) = 1
Comparing f(8,0) = 0 and  f(7,-1) = 0
Domeniile si codomeniul functiei f
 [f(1,0) = 0]  [f(2,0) = 0]  [f(3,0) = 0]  [f(4,0) = 0]  [f(5,0) = 1]  [f(6,0) = 1]
 [f(1,1) = 0]  [f(2,1) = 0]  [f(3,1) = 0]  [f(4,1) = 2]  [f(5,1) = 2]  [f(6,1) = 1]
 [f(1,2) = 0]  [f(2,2) = 0]  [f(3,2) = 3]  [f(4,2) = 3]  [f(5,2) = 2]  [f(6,2) = 1]
 [f(1,3) = 0]  [f(2,3) = 4]  [f(3,3) = 4]  [f(4,3) = 3]  [f(5,3) = 2]  [f(6,3) = 1]
 [f(1,4) = 5]  [f(2,4) = 5]  [f(3,4) = 4]  [f(4,4) = 3]  [f(5,4) = 2]  [f(6,4) = 1]

Prajituri primite: 5
Case vizitate: 2 3 4 5 6
------------------------------------------------------------------------------
------------------------------------------------------------------------------
Demo2 java ProgramareDinamica2.java 0 1
------------------------------------------------------------------------------
Dati N si T separate printr-un spatiu
8 2
Dati prajiturile pentru fiecare casa
2 4 8 2 0 1 2 6

**************

                                   House 8 with 6 cookies, I will gain 6 if I stay on this side from now 'till the end
House 7 with 2 cookies, I will gain 2 if I stay on this side from now 'till the end
                                   House 6 with 1 cookies, I will gain 7 if I stay on this side from now 'till the end
House 5 with 0 cookies, I will gain 2 if I stay on this side from now 'till the end
                                   House 4 with 2 cookies, I will gain 9 if I stay on this side from now 'till the end
House 3 with 8 cookies, I will gain 10 if I stay on this side from now 'till the end
                                   House 2 with 4 cookies, I will gain 13 if I stay on this side from now 'till the end
House 1 with 2 cookies, I will gain 12 if I stay on this side from now 'till the end

**************
Comparing f(4,2) = 11 and  f(3,1) = 17
Comparing f(5,1) = 8 and  f(4,0) = 9
Comparing f(6,0) = 7 and  f(5,-1) = 0
Comparing f(8,0) = 6 and  f(7,-1) = 0
Comparing f(10,0) = 0 and  f(9,-1) = 0
Domeniile si codomeniul functiei f
 [f(1,0) = 0]  [f(2,0) = 0]  [f(3,0) = 10]  [f(4,0) = 9]  [f(5,0) = 2]  [f(6,0) = 7]  [f(7,0) = 2]  [f(8,0) = 6]
 [f(1,1) = 0]  [f(2,1) = 14]  [f(3,1) = 17]  [f(4,1) = 9]  [f(5,1) = 8]  [f(6,1) = 7]  [f(7,1) = 8]  [f(8,1) = 6]
 [f(1,2) = 19]  [f(2,2) = 21]  [f(3,2) = 17]  [f(4,2) = 11]  [f(5,2) = 8]  [f(6,2) = 9]  [f(7,2) = 8]  [f(8,2) = 6]

Prajituri primite: 21
Case vizitate: 2 3 4 6 8
*/
