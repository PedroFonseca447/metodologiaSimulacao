import java.util.LinkedList;
import java.util.Queue;

public class CafeFilaSimulacao {

    // Classe para representar eventos na simulação
    static class Evento implements Comparable<Evento> {
        double tempo;
        String tipo; // "CHEGADA" ou "SAIDA"

        public Evento(double tempo, String tipo) {
            this.tempo = tempo;
            this.tipo = tipo;
        }

        @Override
        public int compareTo(Evento outro) {
            return Double.compare(this.tempo, outro.tempo);
        }
    }

    public static void main(String[] args) {
        double[] pseudoAleatorios = {0.7, 0.1, 0.1, 0.9, 0.2, 0.7};
        double tempoAtual = 0.0;
        double[] temposEstados = new double[6]; // Para armazenar tempos de 0, 1, 2, 3 clientes
        int estadoAtual = 0;
        double ultimoTempo = 0.0;

        Queue<Evento> eventos = new LinkedList<>();
        eventos.add(new Evento(2.0, "CHEGADA")); // Evento inicial de chegada no tempo 1.0 minuto

        int indiceAleatorio = 0;

        while (!eventos.isEmpty() && indiceAleatorio < pseudoAleatorios.length) {
            Evento eventoAtual = eventos.poll();
            tempoAtual = eventoAtual.tempo;

            // Atualiza tempo nos estados da fila
            temposEstados[estadoAtual] += tempoAtual - ultimoTempo;
            ultimoTempo = tempoAtual;

            if (eventoAtual.tipo.equals("CHEGADA")) {
                if (estadoAtual < 5) { // A fila suporta até 3 clientes
                    estadoAtual++;
                    if (indiceAleatorio < pseudoAleatorios.length) {
                        double proximaChegada = tempoAtual + uniforme(1.0, 3.0, pseudoAleatorios[indiceAleatorio++]);
                        eventos.add(new Evento(proximaChegada, "CHEGADA"));
                    }
                }

                if (estadoAtual <= 1) { // Os atendentes podem atender se há 1 ou 2 clientes na fila
                    if (indiceAleatorio < pseudoAleatorios.length) {
                        double tempoSaida = tempoAtual + uniforme(2.0, 4.0, pseudoAleatorios[indiceAleatorio++]);
                        eventos.add(new Evento(tempoSaida, "SAIDA"));
                    }
                }

            } else if (eventoAtual.tipo.equals("SAIDA")) {
                estadoAtual--; // Cliente sai, diminuindo o número na fila

                if (estadoAtual > 0) {
                    if (indiceAleatorio < pseudoAleatorios.length) {
                        double tempoSaida = tempoAtual + uniforme(2.0, 4.0, pseudoAleatorios[indiceAleatorio++]);
                        eventos.add(new Evento(tempoSaida, "SAIDA"));
                    }
                }
            }
        }

        // Geração do relatório final
        double tempoTotal = tempoAtual;
        System.out.println("Relatório da Simulação:");
        for (int i = 0; i < temposEstados.length; i++) {
            System.out.printf("Tempo com %d cliente(s): %.2f minutos (%.2f%%)\n", i, temposEstados[i], (temposEstados[i] / tempoTotal) * 100);
        }
    }

    // Método para gerar número uniformemente distribuído entre 'a' e 'b' usando 'x'
    public static double uniforme(double a, double b, double x) {
        return a + (b - a) * x;
    }
}
