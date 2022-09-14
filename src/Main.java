import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int limit = 4;

        int[] counter = new int[1];
        int[] counterTrabalhadora = new int[1];
        int[] counterCombinadora = new int[1];

        ArrayList<String> fila_arquivos_gerados = new ArrayList<String>();

        GestorSemaforo gestorSemaforo = new GestorSemaforo(counter, limit);

        Trabalhadora trabalhadora1 = new Trabalhadora(fila_arquivos_gerados, counterTrabalhadora, gestorSemaforo);
        Trabalhadora trabalhadora2 = new Trabalhadora(fila_arquivos_gerados, counterTrabalhadora, gestorSemaforo);
        Trabalhadora trabalhadora3 = new Trabalhadora(fila_arquivos_gerados, counterTrabalhadora, gestorSemaforo);
        Trabalhadora trabalhadora4 = new Trabalhadora(fila_arquivos_gerados, counterTrabalhadora, gestorSemaforo);

        Combinadora combinadora = new Combinadora(fila_arquivos_gerados, counterCombinadora, gestorSemaforo);

        trabalhadora1.start();
        trabalhadora2.start();
        trabalhadora3.start();
        trabalhadora4.start();

        combinadora.start();
    }
}
