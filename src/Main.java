import java.util.ArrayList;

public class Main {
    public ArrayList<String> fila_arquivos_gerados = new ArrayList<String>();
    public static void main(String[] args) {
        int limit = 4;
        int[] counter = new int[1];

        GestorSemaforo gestorSemaforo = new GestorSemaforo(counter, limit);
    }
}
