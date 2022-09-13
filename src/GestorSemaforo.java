import java.util.concurrent.Semaphore;

public class GestorSemaforo {
    private Semaphore s1;
    private Semaphore s2;
    private Semaphore vetor;
    private Semaphore counterSemaphore;
    private Semaphore barreiraEntrada;
    private Semaphore barreiraSaida;
    private int[] counter;
    private int limit;

    public GestorSemaforo(int[] counter, int limit){
        this.s1 = new Semaphore(1);
        this.s2 = new Semaphore(1);
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

            if (counter[0] == limit) {
                barreiraEntrada.release();
                barreiraSaida.acquire();
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

            if (counter[0] == 0) {
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
            case 0 -> s1;
            case 1 -> s2;
            case 2 -> vetor;
            case 3 -> barreiraEntrada;
            case 4 -> barreiraSaida;
            case 5 -> counterSemaphore;
            default -> barreiraEntrada;
        };
    }
}
