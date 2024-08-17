import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.math.BigInteger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App {

    // Este programa apresenta uma solução para o gerador de números pseudoaleatórios
    // usando a fórmula Xn+1 = (a * Xn + c) % M
    public static void main(String[] args) {

        // Define os parâmetros
        BigInteger sementeXo = new BigInteger("1024");
        BigInteger a = new BigInteger("720");
        BigInteger c = new BigInteger("4048");
        BigInteger M = new BigInteger("54541048576");

        String nomeArquivo = "pseudosPuros.txt";
        
        int i = 0;
        MathContext mathContext = new MathContext(20, RoundingMode.HALF_UP);

        // Usando try-with-resources para garantir que o BufferedWriter seja fechado corretamente
        try (BufferedWriter preenche = new BufferedWriter(new FileWriter(nomeArquivo))) {
            while (i <1000) {
                // Calcula o próximo valor de Xn+1 usando a fórmula
                sementeXo = (sementeXo.multiply(a).add(c)).mod(M);

                // Converte o valor de Xn+1 para BigDecimal e o divide por M para obter o valor na faixa [0, 1)
                BigDecimal result = new BigDecimal(sementeXo).divide(new BigDecimal(M), mathContext);

                // Escreve o resultado no arquivo
                preenche.write(sementeXo.toString());
                preenche.newLine();  // Adiciona uma nova linha no arquivo após cada número

                // Exibe o resultado no console
                System.out.println(i);

                i++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}

