package Memoria;

import java.util.ArrayList;
import java.util.List;

//manual e controlado
public class MemoryStressor {
	
	//lista para armazenar blocos de memoria
    private final List<byte[]> blocosMemoria = new ArrayList<>();
    private boolean executando = false; 	//controla o loop de estresse

    // Configurações
    private final int tamanhoBlocoMB = 8;   //tamanho de cada bloco em MB
    private final int pausaMs = 50;        //tempo entre alocações

    //inicia o estresse 
    public void iniciar(Runnable aoParar) {
        executando = true;

        new Thread(() -> {
            try {
                while (executando) {

                    //alocação de blocos (manual)
                    byte[] bloco = new byte[tamanhoBlocoMB * 1024 * 1024];

                    //preenchimento, evitando otimizações da maquina virtual do java
                    for (int i = 0; i < bloco.length; i++) {
                        bloco[i] = (byte) (i % 127);
                    }

                    //guarda bloco na lista para manter o consumo da memoria
                    blocosMemoria.add(bloco);

                    //monitoramento da memória
                    double porcentagem = MemoryMonitor.obterPorcentagemMemoriaUsada();
                    System.out.printf("Uso da JVM: %.2f%%\n", porcentagem); //Maquina Virtual do JAVA

                    //sobrecarga na CPU (leve)
                    for (int i = 0; i < 5_000_000; i++) {
                        int x = i * 3; // trabalho inútil propositalmente
                    }

                    //condição de segurança
                    if (porcentagem > 99) {
                        System.out.println("LIMITE DE MEMÓRIA ATINGIDO! Encerrando...");
                        parar();
                        aoParar.run();
                        break;
                    }

                    Thread.sleep(pausaMs);
                }
            }
            catch (OutOfMemoryError e) {
            	 //caso a JVM chegue no limite real de memória
                System.out.println("OutOfMemory! Limpando memória imediatamente");
                parar();
                aoParar.run();
            }
            catch (Exception e) {
            	 //ou qualquer erro inesperado encerra o processo
                parar();
            }
        }).start();
    }

    //para o estresse e limpa a memória
    public void parar() {
        executando = false;
        blocosMemoria.clear();	//limpa blocos da lista
        System.gc();		//solicita que a JVM limpe os objetos removidos
    }
}
