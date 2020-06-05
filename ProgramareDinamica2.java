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

    private int cookies_in_the_bag;
    private ArrayList<Integer> visited_houses;

    public GarfieldProblem(){
        N = 0;
        T = 0;
        cookies_in_the_bag = 0;

    }


    public void rezolva() {


        int[] computedCookies = computeRemainingCookies(this.cookies_per_house);


        System.out.println("\n**************");


        int start_house;
        if(computedCookies[1] > computedCookies[2]){
            start_house = 1;
        }else if( computedCookies[1] == computedCookies[2] && cookies_per_house[1] > cookies_per_house[2]){
            start_house = 1;
        }else{
            start_house = 2;
        }

        visited_houses  = new ArrayList<>();


        for(int i = start_house; i<cookies_per_house.length;i=i+2){
            cookies_in_the_bag += cookies_per_house[i];

            visited_houses.add(i);
            //daca mai sunt case SI daca merita sa treaca strada
            if(i+1 < cookies_per_house.length && T>0 && i+2 < cookies_per_house.length  && computedCookies[i+2]<computedCookies[i+1]){
                i--; //decrementarea indexului cu o unitate inseamna schimbarea de la par la impar si invers (adica trecerea strazii)
                T--; //contorizam de cate ori a trecut strada
            }else if(i+1 < cookies_per_house.length && T>0 && i+2 == cookies_per_house.length  && computedCookies[i+1] > 0){
                i--;
                T--;
            }
        }

        System.out.println("Prajituri primite: "+cookies_in_the_bag);
        System.out.print("Case vizitate: ");
        visited_houses.forEach(x->{
            System.out.print(x+" ");
        });
        System.out.println("\n");


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

In caz ca la un anumit punct partea cealalta devine mai profitabila (factorul de "profitabilitate" reprezinta 
cantitatea de biscuiti obtinuta de la acea casa pana la sfarsit mergand numai pe acea parte a strazii) acesta
va trece strada daca este posibil (T>0).


Executarea programului:

*Primul argument pentru executare se refera la generarea automata a datelor de input (1 sau absenta oricarui parametru inseamna ca generarea de date va fi activata)
*Al doilea argument se refera la modul de citire al datelor (1 inseamna citire de la tastatura si 0 din fisierul data.in)

Demo1 (input + output) comanda de executare: "java ProgramareDinamica2.java 0"
------------------------------------------------------------------------------
    Citirea se face din fisierul data.in...success!!

    N si T: 4 1
    Prajituri per casa: 1 0 6 3 
    ************** 

                                    House 4 with 3 cookies, I will gain 3 if I stay on this side from now 'till the end
    House 3 with 6 cookies, I will gain 6 if I stay on this side from now 'till the end
                                    House 2 with 0 cookies, I will gain 3 if I stay on this side from now 'till the end
    House 1 with 1 cookies, I will gain 7 if I stay on this side from now 'till the end

    **************
    Prajituri primite: 10
    Case vizitate: 1 3 4 

    Scriere date in fisierul data.out...success!!
------------------------------------------------------------------------------
Demo2 (Datele oferite ca exemplu in enuntul problemei) "java ProgramareDinamica2.java 0 1"
------------------------------------------------------------------------------
    Dati N si T separate printr-un spatiu
    6 2
    Dati prajiturile pentru fiecare casa
    7 5 3 8 9 6

    ************** 

                                    House 6 with 6 cookies, I will gain 6 if I stay on this side from now 'till the end
    House 5 with 9 cookies, I will gain 9 if I stay on this side from now 'till the end
                                    House 4 with 8 cookies, I will gain 14 if I stay on this side from now 'till the end
    House 3 with 3 cookies, I will gain 12 if I stay on this side from now 'till the end
                                    House 2 with 5 cookies, I will gain 19 if I stay on this side from now 'till the end
    House 1 with 7 cookies, I will gain 19 if I stay on this side from now 'till the end

    **************
    Prajituri primite: 29
    Case vizitate: 1 2 4 5 
------------------------------------------------------------------------------
Demo3 "java ProgramareDinamica2.java"
------------------------------------------------------------------------------
    Datele de intrare sunt generate in fisierul data.in...success!!
    Citirea se face din fisierul data.in...success!!

    N si T: 8 1
    Prajituri per casa: 6 6 7 2 4 2 1 6 
    ************** 

                                    House 8 with 6 cookies, I will gain 6 if I stay on this side from now 'till the end
    House 7 with 1 cookies, I will gain 1 if I stay on this side from now 'till the end
                                    House 6 with 2 cookies, I will gain 8 if I stay on this side from now 'till the end
    House 5 with 4 cookies, I will gain 5 if I stay on this side from now 'till the end
                                    House 4 with 2 cookies, I will gain 10 if I stay on this side from now 'till the end
    House 3 with 7 cookies, I will gain 12 if I stay on this side from now 'till the end
                                    House 2 with 6 cookies, I will gain 16 if I stay on this side from now 'till the end
    House 1 with 6 cookies, I will gain 18 if I stay on this side from now 'till the end

    **************
    Prajituri primite: 22
    Case vizitate: 1 2 4 6 8 

    Scriere date in fisierul data.out...success!!
------------------------------------------------------------------------------
*/
