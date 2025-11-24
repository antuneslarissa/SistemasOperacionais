package Memoria;

public class MemoryMonitor {

    private static final Runtime runtime = Runtime.getRuntime();

    //retorna a porcentagem da memória usada pela JVM
    public static double obterPorcentagemMemoriaUsada() {
        long total = runtime.totalMemory();   // Memória total alocada pela JVM
        long livre = runtime.freeMemory();    // Espaço livre dentro da JVM

        long usada = total - livre;           // Memória realmente utilizada

        return (usada * 100.0) / total;       // Retorna a porcentagem
    }

    // Exibe informações da memória
    public static void exibirStatus() {
        long total = runtime.totalMemory() / (1024 * 1024); //mb p bs
        long livre = runtime.freeMemory() / (1024 * 1024);
        long usada = total - livre;

        System.out.println("Memória JVM → Usada: " + usada + "MB / Total: " + total + "MB");
    }
}
