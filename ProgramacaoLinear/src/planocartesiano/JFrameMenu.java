package planocartesiano;

import java.util.ArrayList;

/**
 * JFrame que serve de interface com o usuário para a inserção de parâmetros para a geração de gráficos no plano cartesiano.
 * <p>O JFrameMenu controla os gráficos do JFrameGrafico.</p>
 * <p>O JFrameGrafico, por sua vez, gera seus gráficos por intermédio de seu atributo principal, o JPanelCartesiano.</p>
 * @author Lucas Paz
 */
public class JFrameMenu extends javax.swing.JFrame implements InterfaceGrafica {

    public JFrameGrafico jFrameGrafico = new JFrameGrafico();

    public JFrameMenu() {
        initComponents();
        jFrameGrafico.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }

    public JFrameMenu(ArrayList<String> equacoes) {
        initComponents();
        jFrameGrafico.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jFrameGrafico.painelCartesiano.setEquacoes(equacoes);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jTextFieldTamanho = new javax.swing.JTextField();
        jTextFieldPixelsPorUnidade = new javax.swing.JTextField();
        jTextFieldxCentral = new javax.swing.JTextField();
        jTextFieldyCentral = new javax.swing.JTextField();
        jTextFieldMarcacoes = new javax.swing.JTextField();
        jTextFieldMargemDeErro = new javax.swing.JTextField();
        jTextFieldPassoX = new javax.swing.JTextField();
        jTextFieldAddEquacao = new javax.swing.JTextField();
        jLabelTamanho = new javax.swing.JLabel();
        jLabelPixelsPorUnidade = new javax.swing.JLabel();
        jLabelxCentral = new javax.swing.JLabel();
        jLabelyCentral = new javax.swing.JLabel();
        jLabelPassoX = new javax.swing.JLabel();
        jLabelAddEquacao = new javax.swing.JLabel();
        jLabelXIgualA = new javax.swing.JLabel();
        jLabelIntervalo = new javax.swing.JLabel();
        jLabelMargemDeErro = new javax.swing.JLabel();
        jButtonGrafico = new javax.swing.JButton();
        jButtonAddEquacao = new javax.swing.JButton();
        jButtonRemoverEquacoes = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(700,500));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plano Cartesiano - Menu");
        setResizable(false);
        getContentPane().setLayout(null);

        jTextFieldTamanho.setText("700");
        getContentPane().add(jTextFieldTamanho);
        jTextFieldTamanho.setBounds(192, 47, 95, 30);

        jTextFieldPixelsPorUnidade.setText("70");
        getContentPane().add(jTextFieldPixelsPorUnidade);
        jTextFieldPixelsPorUnidade.setBounds(192, 83, 95, 30);

        jTextFieldxCentral.setText("0");
        getContentPane().add(jTextFieldxCentral);
        jTextFieldxCentral.setBounds(192, 119, 95, 30);

        jTextFieldyCentral.setText("0");
        getContentPane().add(jTextFieldyCentral);
        jTextFieldyCentral.setBounds(192, 155, 95, 30);

        jTextFieldMarcacoes.setText("1");
        getContentPane().add(jTextFieldMarcacoes);
        jTextFieldMarcacoes.setBounds(192, 191, 95, 30);

        jTextFieldMargemDeErro.setText("-01");
        getContentPane().add(jTextFieldMargemDeErro);
        jTextFieldMargemDeErro.setBounds(192, 227, 95, 30);

        jTextFieldPassoX.setText("0.1");
        getContentPane().add(jTextFieldPassoX);
        jTextFieldPassoX.setBounds(192, 263, 95, 30);

        getContentPane().add(jTextFieldAddEquacao);
        jTextFieldAddEquacao.setBounds(192, 11, 250, 30);

        jLabelTamanho.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelTamanho.setText("Tamanho do gráfico em pixels");
        getContentPane().add(jLabelTamanho);
        jLabelTamanho.setBounds(10, 47, 172, 30);

        jLabelPixelsPorUnidade.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelPixelsPorUnidade.setText("Pixels por unidade (zoom)");
        getContentPane().add(jLabelPixelsPorUnidade);
        jLabelPixelsPorUnidade.setBounds(10, 83, 172, 30);

        jLabelxCentral.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelxCentral.setText("Centralizar em x =");
        getContentPane().add(jLabelxCentral);
        jLabelxCentral.setBounds(10, 119, 172, 30);

        jLabelyCentral.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelyCentral.setText("Centralizar em y = ");
        getContentPane().add(jLabelyCentral);
        jLabelyCentral.setBounds(10, 155, 172, 30);

        jLabelPassoX.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelPassoX.setText("Passo x da equação");
        getContentPane().add(jLabelPassoX);
        jLabelPassoX.setBounds(10, 263, 172, 30);

        jLabelAddEquacao.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelAddEquacao.setText("Adicionar equação (use x).   y = ");
        getContentPane().add(jLabelAddEquacao);
        jLabelAddEquacao.setBounds(10, 11, 172, 30);

        jLabelXIgualA.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jLabelXIgualA.setText("Opcionalmente, digite \"x = n\", n ∈ R.");
        getContentPane().add(jLabelXIgualA);
        jLabelXIgualA.setBounds(450, 70, 210, 30);
        
        jLabelIntervalo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelIntervalo.setText("Intervalo das marcações");
        getContentPane().add(jLabelIntervalo);
        jLabelIntervalo.setBounds(10, 191, 172, 30);

        jLabelMargemDeErro.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelMargemDeErro.setText("Margem de erro");
        getContentPane().add(jLabelMargemDeErro);
        jLabelMargemDeErro.setBounds(10, 227, 172, 30);

        jButtonGrafico.setText("Gerar gráfico");
        jButtonGrafico.addActionListener(this::jButtonGraficoActionPerformed);
        getContentPane().add(jButtonGrafico);
        jButtonGrafico.setBounds(192, 311, 150, 30);

        jButtonAddEquacao.setText("Adicionar equação");
        jButtonAddEquacao.addActionListener(this::jButtonAddEquacaoActionPerformed);
        getContentPane().add(jButtonAddEquacao);
        jButtonAddEquacao.setBounds(448, 11, 150, 30);

        jButtonRemoverEquacoes.setText("Remover equações");
        jButtonRemoverEquacoes.addActionListener(this::jButtonRemoverEquacoesActionPerformed);
        getContentPane().add(jButtonRemoverEquacoes);
        jButtonRemoverEquacoes.setBounds(448, 47, 150, 30);

        pack();
        setLocationRelativeTo(null);
    }

    private void jButtonGraficoActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        try {
            jFrameGrafico.setVisible(false);
            setTamanhoDoGraficoEmPixels(Integer.valueOf(jTextFieldTamanho.getText()));
            setPixelsPorUnidade(Integer.valueOf(jTextFieldPixelsPorUnidade.getText()));
            setPassoXDaEquacao(Float.valueOf(jTextFieldPassoX.getText()));
            setUnidadesPorIntervaloEntreMarcacoes(Float.valueOf(jTextFieldMarcacoes.getText()));
            setxCentral(Float.valueOf(jTextFieldxCentral.getText()));
            setyCentral(Float.valueOf(jTextFieldyCentral.getText()));
            setMargemDeErro(Double.valueOf(jTextFieldMargemDeErro.getText()));
            jFrameGrafico.painelCartesiano.repaint();
            jFrameGrafico.setVisible(true);
        } catch (Exception | java.lang.Error e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Confira os dados.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }                                              

    private void jButtonAddEquacaoActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        addEquacao(jTextFieldAddEquacao.getText());
        jTextFieldAddEquacao.setText("");
    }                                                 

    private void jButtonRemoverEquacoesActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        // TODO add your handling code here:
        setEquacoes(new ArrayList<String>());
    }                                                      

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JFrameMenu().setVisible(true);
        });
    }

     @Override
    public ArrayList<String> getEquacoes() {
        return jFrameGrafico.painelCartesiano.getEquacoes();
    }

    @Override
    public void setEquacoes(ArrayList<String> equacoes) {
        jFrameGrafico.painelCartesiano.setEquacoes(equacoes);
    }

    @Override
    public String getEquacao(int indice) {
        return jFrameGrafico.painelCartesiano.getEquacoes().get(indice);
    }

    @Override
    public void setEquacao(int indice, String equacao) {
        jFrameGrafico.painelCartesiano.getEquacoes().set(indice, equacao);
    }

    @Override
    public void addEquacao(String equacao) {
        jFrameGrafico.painelCartesiano.getEquacoes().add(equacao);
    }

    @Override
    public void removerEquacao(int indice) {
        jFrameGrafico.painelCartesiano.getEquacoes().remove(indice);
    }

    @Override
    public Integer getTamanhoDoGraficoEmPixels() {
        return jFrameGrafico.painelCartesiano.getTamanhoDoGraficoEmPixels();
    }

    @Override
    public void setTamanhoDoGraficoEmPixels(Integer tamanhoDoGraficoEmPixels) {
        jFrameGrafico.painelCartesiano.setTamanhoDoGraficoEmPixels(tamanhoDoGraficoEmPixels);
    }

    @Override
    public Integer getPixelsPorUnidade() {
        return jFrameGrafico.painelCartesiano.getPixelsPorUnidade();
    }

    @Override
    public void setPixelsPorUnidade(Integer pixelsPorUnidade) {
        jFrameGrafico.painelCartesiano.setPixelsPorUnidade(pixelsPorUnidade);
    }

    @Override
    public Float getxCentral() {
        return jFrameGrafico.painelCartesiano.getxCentral();
    }

    @Override
    public void setxCentral(Float xCentral) {
        jFrameGrafico.painelCartesiano.setxCentral(xCentral);
    }

    @Override
    public Float getyCentral() {
        return jFrameGrafico.painelCartesiano.getyCentral();
    }

    @Override
    public void setyCentral(Float yCentral) {
        jFrameGrafico.painelCartesiano.setyCentral(yCentral);
    }

    @Override
    public Float getUnidadesPorIntervaloEntreMarcacoes() {
        return jFrameGrafico.painelCartesiano.getUnidadesPorIntervaloEntreMarcacoes();
    }

    @Override
    public void setUnidadesPorIntervaloEntreMarcacoes(Float unidadesPorIntervaloEntreMarcacoes) {
        jFrameGrafico.painelCartesiano.setUnidadesPorIntervaloEntreMarcacoes(unidadesPorIntervaloEntreMarcacoes);
    }

    @Override
    public Double getMargemDeErro() {
        return jFrameGrafico.painelCartesiano.getMargemDeErro();
    }

    @Override
    public void setMargemDeErro(Double margemDeErro) {
        jFrameGrafico.painelCartesiano.setMargemDeErro(margemDeErro);
    }

    @Override
    public Float getPassoXDaEquacao() {
        return jFrameGrafico.painelCartesiano.getPassoXDaEquacao();
    }

    @Override
    public void setPassoXDaEquacao(Float passoXDaEquacao) {
        jFrameGrafico.painelCartesiano.setPassoXDaEquacao(passoXDaEquacao);
    }

    private javax.swing.JButton jButtonAddEquacao;
    private javax.swing.JButton jButtonGrafico;
    private javax.swing.JButton jButtonRemoverEquacoes;
    private javax.swing.JLabel jLabelTamanho;
    private javax.swing.JLabel jLabelPixelsPorUnidade;
    private javax.swing.JLabel jLabelxCentral;
    private javax.swing.JLabel jLabelyCentral;
    private javax.swing.JLabel jLabelPassoX;
    private javax.swing.JLabel jLabelAddEquacao;
    private javax.swing.JLabel jLabelXIgualA;
    private javax.swing.JLabel jLabelIntervalo;
    private javax.swing.JLabel jLabelMargemDeErro;
    private javax.swing.JTextField jTextFieldAddEquacao;
    private javax.swing.JTextField jTextFieldMarcacoes;
    private javax.swing.JTextField jTextFieldMargemDeErro;
    private javax.swing.JTextField jTextFieldPassoX;
    private javax.swing.JTextField jTextFieldPixelsPorUnidade;
    private javax.swing.JTextField jTextFieldTamanho;
    private javax.swing.JTextField jTextFieldxCentral;
    private javax.swing.JTextField jTextFieldyCentral;
}
