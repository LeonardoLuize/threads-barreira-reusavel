import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Trabalhadora extends Thread{
    private int[] vector;
    private int[] sorted_vector;
    private int[] index;
    private GestorSemaforo gestorSemaforo;
    private ArrayList<String> fila_arquivos;

    private int loopLimit;
    private int loopCounter = 0;

    public Trabalhadora(ArrayList<String> fila_arquivos, int[] index, GestorSemaforo gestorSemaforo, int loopLimit){
        this.index = index;
        this.gestorSemaforo = gestorSemaforo;
        this.fila_arquivos = fila_arquivos;
    }

    public void run(){
        while(true) {
            try {
                makeVector(1000);
                Semaphore trabalhadoraSemaphore = gestorSemaforo.getSemaphoreByIndex(0);
                Semaphore vetorSemaphore = gestorSemaforo.getSemaphoreByIndex(2);

                trabalhadoraSemaphore.acquire();
                String path = getCaminho();
                this.index[0]++;
                trabalhadoraSemaphore.release();

                WriteFile.WriteFilePath(path, getVector());
                quicksort(0, this.sorted_vector.length - 1);
                WriteFile.WriteFilePath(path, getSortedVector());

                vetorSemaphore.acquire();
                fila_arquivos.add(path);
                vetorSemaphore.release();

                gestorSemaforo.executaBarreiraEntrada();

                gestorSemaforo.executaBarreiraSaida();
                loopCounter++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void makeVector(int quant){
        int[] v = new int[quant];
        for (int i = 0; i<quant; i++){
            Random rand = new Random();
            v[i] = rand.nextInt(10000000);
        }
        this.vector = v;
        this.sorted_vector = vector;
    }

    public void bubble_sort(){
        for (int i = 0; i < this.sorted_vector.length; i++){
            for (int j = 0; j < this.sorted_vector.length - 1; j++){
                if (this.sorted_vector[j] > this.sorted_vector[j+1]){
                    exchange(i, j);
                }
            }
        }
    }

    private void quicksort(int low, int high) {
        int i = low, j = high;
        int pivot = this.sorted_vector[low + (high-low)/2];
        while (i <= j) {
            while (this.sorted_vector[i] < pivot) {
                i++;
            }
            while (this.sorted_vector[j] > pivot) {
                j--;
            }
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursivamente
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }
    private void exchange(int i, int j) {
        int temp = this.sorted_vector[i];
        this.sorted_vector[i] = this.sorted_vector[j];
        this.sorted_vector[j] = temp;
    }

    public String getVector() {
        String str = "";
        for (int i=0; i<this.vector.length; i++){
            str += this.vector[i] + " ";
        }
        return str;
    }

    public String getSortedVector() {
        String str = "";
        for (int i = 0; i<this.sorted_vector.length; i++)str+=this.sorted_vector[i] + " ";
        return str;
    }

    public void setVector(int[] vector) {
        this.vector = vector;
    }

    private String getCaminho(){
        String arqName = "Lista";
        arqName += getIndex();
        arqName += ".txt";
        return arqName;
    }
    private int getIndex(){
        return this.index[0];
    }
}