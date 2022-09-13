import java.util.ArrayList;
import java.util.Random;

public class Trabalhadora extends Thread{
    private int[] vector;
    private int[] sorted_vector;

    public void makeVector(int quant){
        int[] v = new int[quant];
        for (int i = 0; i<quant; i++){
            Random rand = new Random();
            v[i] = rand.nextInt(106);
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

    public Trabalhadora(){
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

    public void makeArquivoDesordenado(){
        WriteFile.WriteFilePath("numerosDesordenados.txt", getVector());
    }
    public void makeArquivoOrdenado(){
        WriteFile.WriteFilePath("numerosOrdenados.txt", getSortedVector());
    }

    public void run(){
        try{
            makeVector(1000);
            makeArquivoDesordenado();

            quicksort(0, this.sorted_vector.length - 1);
            makeArquivoOrdenado();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}