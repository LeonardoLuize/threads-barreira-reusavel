import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Combinadora extends Thread{

    private ArrayList<String> fila_arquivos_gerados;
    private HashSet<Integer> lista_final = new HashSet<>();
    private int[] contador;
    private GestorSemaforo gestorSemaforo;

    public Combinadora(ArrayList<String> fila_arquivos_gerados, int[] contador, GestorSemaforo gestorSemaforo) {
        this.fila_arquivos_gerados = fila_arquivos_gerados;
        this.gestorSemaforo = gestorSemaforo;
        this.contador = contador;
    }

    public void run(){
        try {
            Semaphore combinadoraSemaphore = gestorSemaforo.getSemaphoreByIndex(1);
            Semaphore combinadoraAlert = gestorSemaforo.getSemaphoreByIndex(6);
            Semaphore vetorSemaphore = gestorSemaforo.getSemaphoreByIndex(2);

            combinadoraAlert.acquire();
            for (String fila_arquivos_gerado : fila_arquivos_gerados) {
                ArrayList<Integer> arquivo;
                try {
                    arquivo = WriteFile.ReadFile(fila_arquivos_gerado);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                lista_final.addAll(arquivo);
            }

            vetorSemaphore.acquire();
            excluiArquivoLista();
            vetorSemaphore.release();

            WriteFile.WriteFilePath(nomeiaArquivo(contador[0]), lista_final.toString());

            combinadoraSemaphore.acquire();
            contador[0]++;
            combinadoraSemaphore.release();

            combinadoraAlert.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluiArquivoLista(){
        fila_arquivos_gerados.clear();
    }

    private String nomeiaArquivo(int contador){
        String arqName = "Resultado";
        arqName += contador;
        arqName += ".txt";
        return arqName;
    }
    
}