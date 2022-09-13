import java.io.*;
import java.util.ArrayList;

public class WriteFile {
    public static void WriteFilePath(String caminho, String texto) {
        try (
            FileWriter fileWriter = new FileWriter(caminho, false);
            BufferedWriter buffer = new BufferedWriter(fileWriter);
            PrintWriter pw = new PrintWriter(buffer))
        {
            pw.append(texto);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> ReadFile(String name) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(name));
        String linha;
        ArrayList<Integer> lista = new ArrayList<Integer>();

        while (br.readLine() != null){
            linha = br.readLine();
            Integer numero = Integer.valueOf(linha);
            lista.add(numero);
        }

        return lista;
    }

}