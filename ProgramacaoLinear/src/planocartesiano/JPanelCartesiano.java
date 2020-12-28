package planocartesiano;

import planocartesiano.avaliador.Avaliador;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * JPane que elabora um gráfico de plano cartesiano de acordo com os seus atributos.
 * @author Lucas Paz
 */
public class JPanelCartesiano extends JPanel implements InterfaceGrafica {

    private ArrayList<String> equacoes = new ArrayList(); //equações com 'x', que farão o gráfico
    //private String equacao = "2.71^x";
    private Integer tamanhoDoGraficoEmPixels = 700; //pixels
    private Integer pixelsPorUnidade = 70; //quantos pixels formam um intervalo de uma unidade (zoom)
    private Float xCentral = 0f; //centralizar em x = xCentral
    private Float yCentral = 0f; //centralizar em y = yCentral
    private Float unidadesPorIntervaloEntreMarcacoes = 1f; //quantas unidades há entre cada marcação
    private Double margemDeErro = 0d; //tolerância para mais e para menos de 0 para encontrar raízes e pontos comuns de funções (ponha número negativo se não quiser)
    private Float passoXDaEquacao = 0.1f; //passo das abcissas do gráfico da função (evita granularidade mas torna mais lento)

    public JPanelCartesiano(){}

    public JPanelCartesiano(String equacao){
        this.equacoes.add(equacao);
    }

    public JPanelCartesiano(String equacao, int tamanhoEmPixels){
        this.setSize(tamanhoEmPixels, tamanhoEmPixels);
        this.equacoes.add(equacao);
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);
        Graphics g = (Graphics2D) graphics;

        pintar(g);
    }

    public void pintar(Graphics g) {
        Float coordenada0x = tamanhoDoGraficoEmPixels / 2f - (xCentral) * pixelsPorUnidade; //abcissa do computador para x = 0
        Float coordenada0y = tamanhoDoGraficoEmPixels / 2f + (yCentral) * pixelsPorUnidade; //ordenada do computador para y = 0

        //eixos x e y
        g.setColor(Color.BLACK);
        g.drawLine(coordenada0x.intValue(), 0, coordenada0x.intValue(), tamanhoDoGraficoEmPixels);
        g.drawLine(0, coordenada0y.intValue(), tamanhoDoGraficoEmPixels, coordenada0y.intValue());

        //marcações
        float passoMarcacoes = pixelsPorUnidade * unidadesPorIntervaloEntreMarcacoes;
        for (float i = 0; i <= tamanhoDoGraficoEmPixels; i+= passoMarcacoes){
            g.setColor(Color.GREEN);
            Float iX = i + (coordenada0x % passoMarcacoes);
            Float iY = i + (coordenada0y % passoMarcacoes);

            g.fillOval(iX.intValue(), coordenada0y.intValue(), 2, 2);
            g.fillOval(coordenada0x.intValue(), iY.intValue(), 2, 2);

            g.setColor(Color.BLACK);
            g.drawString(Float.toString((iX - coordenada0x) / (float) pixelsPorUnidade), iX.intValue(), coordenada0y.intValue());
            g.drawString(Float.toString((-iY + coordenada0y) / (float) pixelsPorUnidade), coordenada0x.intValue(), iY.intValue());
        }

        //desenha os gráficos das funções
        g.setColor(Color.BLUE);

        //equacao pode ser x=n ou x==n(espaços opcionais)
        for (int i = 0; i < equacoes.size(); i++) {
            if (equacoes.get(i).charAt(0) == 'x'){
                Float x = xIgualA(equacoes.get(i));
                g.drawLine(coordenada0x.intValue() + Float.valueOf(x*pixelsPorUnidade).intValue(), 0, coordenada0x.intValue() + Float.valueOf(x*pixelsPorUnidade).intValue(), tamanhoDoGraficoEmPixels);
            }
        }
        
        Avaliador a = new Avaliador();
        a.criarVariavel("x", 0d);
        ArrayList<Double> resultados = new ArrayList();
        String equacao = "";
        for (Double coordenadaDoPixel = 0d; coordenadaDoPixel <= tamanhoDoGraficoEmPixels; coordenadaDoPixel += passoXDaEquacao){

            Double x = (coordenadaDoPixel - coordenada0x.doubleValue()) / (double) pixelsPorUnidade;

            a.mudarValorDeVariavel("x", x);

            resultados = new ArrayList<Double>();
            equacao = "";
            for (int i = 0; i < equacoes.size(); i++) {
                equacao = equacoes.get(i);
                
                if (equacao.charAt(0) == 'x') continue; //equacao pode ser x=n ou x==n(espaços opcionais)

                Double fDeX = a.avaliar(equacao);
                resultados.add(fDeX);
                Double y = coordenada0y.doubleValue() - (fDeX * pixelsPorUnidade);


                g.fillOval(coordenadaDoPixel.intValue(), y.intValue(), 2, 2);
                
                if (margemDeErro >= 0d){

                    if (Math.abs(fDeX) <= margemDeErro) {
                        g.drawString(String.valueOf(x.floatValue()), coordenadaDoPixel.intValue(), y.intValue());
                    }

                    for (int j = 0; j < resultados.size() - 1 ; j++) {
                        if (Math.abs(fDeX - resultados.get(j)) <= margemDeErro) {
                            g.drawString("(" + String.valueOf(x.floatValue()) + " , " + String.valueOf(fDeX.floatValue()) + ")", coordenadaDoPixel.intValue(), y.intValue());
                        }
                    }

                }
            }
        }

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                //float x = (evt.getX() - coordenada0x) / pixelsPorUnidade;
                //float y = (- evt.getY() + coordenada0y) / pixelsPorUnidade;
                //setToolTipText("(" + x + " , " + y + ")");
                setToolTipText("(" + (evt.getX() - coordenada0x) / pixelsPorUnidade + " , " + (- evt.getY() + coordenada0y) / pixelsPorUnidade + ")");
            }
        });

    }
    
    private float xIgualA(String equacao) {
        //caso equacao seja x=n ou x==n (espacos ja estao tirados)
        float x = (equacao.charAt(2) == '=')?Float.valueOf(equacao.substring(3)):Float.valueOf(equacao.substring(2));

        return x;
    }

    @Override
    public ArrayList<String> getEquacoes() {
        return this.equacoes;
    }

    @Override
    public String getEquacao(int indice) {
        return this.equacoes.get(indice);
    }

    @Override
    public void setEquacoes(ArrayList<String> equacoes) {
        for (int i = 0; i < equacoes.size(); i++) {
            equacoes.set(i, equacoes.get(i).replaceAll(" ", ""));
        }
        this.equacoes = equacoes;
    }

    @Override
    public void setEquacao(int indice, String equacao) {
        equacao = equacao.replaceAll(" ", "");
        this.equacoes.set(indice, equacao);
    }

    @Override
    public void addEquacao(String equacao) {
        this.equacoes.add(equacao);
    }

    @Override
    public void removerEquacao(int indice) {
        this.equacoes.remove(indice);
    }

    @Override
    public Integer getTamanhoDoGraficoEmPixels() {
        return this.tamanhoDoGraficoEmPixels;
    }

    @Override
    public void setTamanhoDoGraficoEmPixels(Integer tamanhoDoGraficoEmPixels) {
        this.tamanhoDoGraficoEmPixels = tamanhoDoGraficoEmPixels;
    }

    @Override
    public Integer getPixelsPorUnidade() {
        return this.pixelsPorUnidade;
    }

    @Override
    public void setPixelsPorUnidade(Integer pixelsPorUnidade) {
        this.pixelsPorUnidade = pixelsPorUnidade;
    }

    @Override
    public Float getxCentral() {
        return this.xCentral;
    }

    @Override
    public void setxCentral(Float xCentral) {
        this.xCentral = xCentral;
    }

    @Override
    public Float getyCentral() {
        return this.yCentral;
    }

    @Override
    public void setyCentral(Float yCentral) {
        this.yCentral = yCentral;
    }

    @Override
    public Float getUnidadesPorIntervaloEntreMarcacoes() {
        return this.unidadesPorIntervaloEntreMarcacoes;
    }

    @Override
    public void setUnidadesPorIntervaloEntreMarcacoes(Float unidadesPorIntervaloEntreMarcacoes) {
        this.unidadesPorIntervaloEntreMarcacoes = unidadesPorIntervaloEntreMarcacoes;
    }

    @Override
    public Double getMargemDeErro() {
        return this.margemDeErro;
    }

    @Override
    public void setMargemDeErro(Double margemDeErro) {
        this.margemDeErro = margemDeErro;
    }

    @Override
    public Float getPassoXDaEquacao() {
        return this.passoXDaEquacao;
    }

    @Override
    public void setPassoXDaEquacao(Float passoXDaEquacao) {
        this.passoXDaEquacao = passoXDaEquacao;
    }

}