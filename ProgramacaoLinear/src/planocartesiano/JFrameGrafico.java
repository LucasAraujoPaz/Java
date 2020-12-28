package planocartesiano;

import javax.swing.JFrame;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Este frame gera gráficos no plano cartesiano por intermédio de seu atributo principal, o JPanelCartesiano.
 * <p>O JFrameMenu é o que serve de interface com o usuário para a inserção de parâmetros que guiam o JFrameGrafico.</p>
 * @author Lucas Paz
 */
public class JFrameGrafico extends JFrame {

    public JPanelCartesiano painelCartesiano = new JPanelCartesiano();

    public JFrameGrafico() {
        initComponents();
    }

    public JFrameGrafico(String equacao) {
        initComponents();
        this.painelCartesiano.addEquacao(equacao);
    }

    public JFrameGrafico(ArrayList<String> equacoes) {
        initComponents();
        this.painelCartesiano.setEquacoes(equacoes);
    }

    public JFrameGrafico(JPanelCartesiano painelCartesiano) {
        initComponents();
        this.painelCartesiano = painelCartesiano;
    }

    public JFrameGrafico(JPanelCartesiano painelCartesiano, ArrayList<String> equacoes) {
        initComponents();
        this.painelCartesiano = painelCartesiano;
        this.painelCartesiano.setEquacoes(equacoes);
    }
    
    private void initComponents(){
        setSize(700, 700);
        setPreferredSize(new java.awt.Dimension(700, 700));
        setTitle("GráficosDeFuncoes");
        setResizable(false);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setContentPane(painelCartesiano);
    }
}