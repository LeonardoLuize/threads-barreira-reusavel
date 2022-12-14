import java.util.concurrent.Semaphore;

public class GestorSemaforo {
    private Semaphore trabalhadoraSemaphore;
    private Semaphore combinadoraSemaphore;
    private Semaphore esperaCombinadora;
    private Semaphore combinadoraAlert;
    private Semaphore vetor;
    private Semaphore counterSemaphore;
    private Semaphore barreiraEntrada;
    private Semaphore barreiraSaida;
    private int[] counter;
    private int limit;

    public GestorSemaforo(int[] counter, int limit){
        this.trabalhadoraSemaphore = new Semaphore(1);
        this.combinadoraSemaphore = new Semaphore(1);
        this.combinadoraAlert = new Semaphore(0);
        this.esperaCombinadora = new Semaphore(0);
        this.vetor = new Semaphore(1);

        this.counterSemaphore = new Semaphore(1);

        this.barreiraEntrada = new Semaphore(0);
        this.barreiraSaida = new Semaphore(1);

        this.counter = counter;
        this.limit = limit;
    }

    public void executaBarreiraEntrada(){
        try {
            counterSemaphore.acquire();
            counter[0]++;

            System.out.println("Contador barreira entrada: " + counter[0]);

            if (counter[0] == limit) {
                System.out.println("\nFecha Saída");
                System.out.println("Libera Entrada\n");
                barreiraSaida.acquire();
                barreiraEntrada.release();
            }
            counterSemaphore.release();

            barreiraEntrada.acquire();
            barreiraEntrada.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void executaBarreiraSaida(){
        try {
            counterSemaphore.acquire();
            counter[0]--;
            System.out.println("Contador barreira saida: " + counter[0]);
            if (counter[0] == 0) {
                combinadoraAlert.release();
                System.out.println("\nSinaliza Combinadora...");
                esperaCombinadora.acquire();

                System.out.println("Libera Saida");
                System.out.println("Fecha Entrada\n");

                barreiraSaida.release();
                barreiraEntrada.acquire();
            }
            counterSemaphore.release();

            barreiraSaida.acquire();
            barreiraSaida.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Semaphore getSemaphoreByIndex(int index){
        return switch (index) {
            case 0 -> trabalhadoraSemaphore;
            case 1 -> combinadoraSemaphore;
            case 2 -> vetor;
            case 3 -> barreiraEntrada;
            case 4 -> barreiraSaida;
            case 5 -> counterSemaphore;
            case 6 -> combinadoraAlert;
            case 7 -> esperaCombinadora;
            default -> barreiraEntrada;
        };
    }
}
