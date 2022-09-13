import java.util.ArrayList;
import java.util.HashSet;

public class Combinadora extends Thread{

    private ArrayList<String> fila_arquivos_gerados;
    private HashSet<Integer> lista_final;

    private int contador = 0;

    public Combinadora(ArrayList<String> fila_arquivos_gerados) {
        this.fila_arquivos_gerados = fila_arquivos_gerados;
    }

    public void run(){
        for(int i = 0; i < fila_arquivos_gerados.size(); i++){
            ArrayList<Integer> arquivo;
            try {
                arquivo = WriteFile.ReadFile(fila_arquivos_gerados.get(i));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            for (int count = 0; count < arquivo.size(); count++){
                lista_final.add(arquivo.get(count));
            }
        }

        excluiArquivoLista();
        WriteFile.WriteFilePath(nomeiaArquivo(contador), lista_final.toString());
        contador++;
    }

    public void excluiArquivoLista(){
        for (int i = 0; i <= fila_arquivos_gerados.size(); i++){
            fila_arquivos_gerados.remove(i);
        }
    }

    private String nomeiaArquivo(int contador){
        String arqName = "Resultado";
        arqName += contador;
        arqName += ".txt";
        return arqName;
    }
    
}