package Interface;

import javax.swing.*;
import java.awt.*;

import Memoria.MemoryStressor;
import Memoria.MemoryMonitor;

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame {

    // Barra de progresso que mostra o uso da memória da JVM
    private final JProgressBar barra = new JProgressBar();

    // Botão para iniciar o estresse de memória
    private final JButton botao = new JButton("Iniciar Estresse");

    // Objeto que realiza o estresse de memória
    private MemoryStressor estressor;

    public JanelaPrincipal() {

        setTitle("Estressador de Memória");
        setSize(400, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Exibe o texto dentro da barra de progresso
        barra.setStringPainted(true);

        add(barra, BorderLayout.CENTER);
        add(botao, BorderLayout.SOUTH);

        // Ação do botão
        botao.addActionListener(e -> iniciarEstresse());

        // Começa a atualizar a barra de uso da memória
        iniciarMonitoramento();

        setVisible(true);
    }

    // Método chamado ao clicar no botão
    private void iniciarEstresse() {
        botao.setEnabled(false);  // Evita apertar duas vezes

        estressor = new MemoryStressor();

        estressor.iniciar(() -> {
            JOptionPane.showMessageDialog(
                    this,
                    "JVM atingiu 99% de uso! Estresse encerrado."
            );
            botao.setEnabled(true); // Reabilita o botão
        });
    }

    // Atualiza a barra de progresso automaticamente
    private void iniciarMonitoramento() {

        new Timer(500, e -> {

            double porcentagem = MemoryMonitor.obterPorcentagemMemoriaUsada();

            barra.setValue((int) porcentagem);

            barra.setString(String.format("Uso da JVM: %.2f%%", porcentagem));

        }).start();
    }
}
