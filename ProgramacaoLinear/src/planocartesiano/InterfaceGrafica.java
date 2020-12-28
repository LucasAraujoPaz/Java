package planocartesiano;

import java.util.ArrayList;

/**
 * Interface para a geração de gráficos no plano cartesiano.
 * @author Lucas Paz
 */
public interface InterfaceGrafica {

    public ArrayList<String> getEquacoes();

    public void setEquacoes(ArrayList<String> equacoes);

    public String getEquacao(int indice);

    public void setEquacao(int indice, String equacao);

    public void addEquacao(String equacao);
    
    public void removerEquacao(int indice);

    public Integer getTamanhoDoGraficoEmPixels();

    public void setTamanhoDoGraficoEmPixels(Integer tamanhoDoGraficoEmPixels);

    public Integer getPixelsPorUnidade();

    public void setPixelsPorUnidade(Integer pixelsPorUnidade);

    public Float getxCentral();

    public void setxCentral(Float xCentral);

    public Float getyCentral();

    public void setyCentral(Float yCentral);

    public Float getUnidadesPorIntervaloEntreMarcacoes();

    public void setUnidadesPorIntervaloEntreMarcacoes(Float unidadesPorIntervaloEntreMarcacoes);

    public Double getMargemDeErro();

    public void setMargemDeErro(Double margemDeErro);

    public Float getPassoXDaEquacao();

    public void setPassoXDaEquacao(Float passoXDaEquacao);

}