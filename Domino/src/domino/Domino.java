package domino;
        
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

molde decrescente
6 | 6  :  1
6 | 5  :  2
6 | 4  :  3
6 | 3  :  4
6 | 2  :  5
6 | 1  :  6
6 | 0  :  7
5 | 5  :  8
5 | 4  :  9
5 | 3  :  10
5 | 2  :  11
5 | 1  :  12
5 | 0  :  13
4 | 4  :  14
4 | 3  :  15
4 | 2  :  16
4 | 1  :  17
4 | 0  :  18
3 | 3  :  19
3 | 2  :  20
3 | 1  :  21
3 | 0  :  22
2 | 2  :  23
2 | 1  :  24
2 | 0  :  25
1 | 1  :  26
1 | 0  :  27
0 | 0  :  28

molde crescente (agora usa-se o decrescente)
0 | 0 : 0
0 | 1 : 1  
0 | 2 : 2 
0 | 3 : 3 
0 | 4 : 4 
0 | 5 : 5  
0 | 6 : 6 
1 | 1 : 7 
1 | 2 : 8
1 | 3 : 9 
1 | 4 : 10   
1 | 5 : 11
1 | 6 : 12  
2 | 2 : 13
2 | 3 : 14  
2 | 4 : 15
2 | 5 : 16
2 | 6 : 17  
3 | 3 : 18  
3 | 4 : 19   
3 | 5 : 20   
3 | 6 : 21 
4 | 4 : 22 
4 | 5 : 23
4 | 6 : 24
5 | 5 : 25
5 | 6 : 26
6 | 6 : 27 

*/

/**
 *
 * @author Lucas Paz
 */
public class Domino extends javax.swing.JFrame {
    
    Random r = new Random();
    
    int molde[][] = new int[2][28];
    int jogo[][] = new int[2][28];
    int mesa[][] = new int[2][28];
    
    //humano
    int h[][] = new int[2][28];
    
    //computador
    int c[][] = new int[2][28];;
    
    //morto
    int m[][] = new int[2][28];
    int qnt_m = 22;
    
    int primeiroNum = 9;
    int ultimoNum = 9;
    
    char vez;
    char mortoDaVez = '6';
    
    int pedraJogadaA;
    int pedraJogadaB;
    
    int numPedrasH = 3;
    int numPedrasC = 3;
    
    int pontuacaoH = 0;
    int pontuacaoC = 0;
    
    String pedraDeComeco = null;
    
    int cimao;
    int baixao;
    
    DefaultListModel model_mesa = new DefaultListModel();
    DefaultListModel model_mesaInterface = new DefaultListModel();
    DefaultListModel model_h = new DefaultListModel();
    DefaultListModel model_c = new DefaultListModel();
    DefaultListModel model_mostraTudo = new DefaultListModel();
    DefaultListModel model_dorme = new DefaultListModel();
    
    /**
     * Creates new form Domino
     */
    
    public Domino() {
        
        initComponents();
        gerarMolde();
        mexerPedras();
        if (checaQuemVaiComecar() == 'c' ) preJogo_c();
        jList_jogo.setVisible(false);
        jScrollPane5.setVisible(false);
        jList_mesa.setVisible(false);
        jScrollPane1.setVisible(false);
        jList_c.setVisible(false);
        jScrollPane3.setVisible(false);
        jList_dorme.setVisible(false);
        jScrollPane6.setVisible(false);        
    }

    public void gerarMolde() {
        //gera o molde (sempre igual)
        int coluna = 0;
        /*for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {    
                    molde[0][coluna] = i;
                    molde[1][coluna] = j;
                    coluna = coluna + 1;
            }                
        }*/
        for (int i = 6; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {    
                    molde[0][coluna] = i;
                    molde[1][coluna] = j;
                    coluna = coluna + 1;
                    //System.out.println(i + " | " + j + "  :  " + coluna);
            }                
        }
    }

    public void atualizarMesa(){
        //zera a mesa
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j < 28; j++) {
                mesa[i][j] = 9;
            }
        }
        // atribui valores para a mesa
        for (int j = 0; j < model_mesa.size(); j++) {
            mesa[0][j] = Integer.valueOf(model_mesa.elementAt(j).toString().substring(0, 1));
            mesa[1][j] = Integer.valueOf(model_mesa.elementAt(j).toString().substring(4, 5));
        }
    }
    
    public void mexerPedras() {
        int sorte;
        int i;
        String valor = "";

        //zera as matrizes
        for (i = 0; i <= 1; i++) {
            for (int j = 0; j < 28; j++) {
                mesa[i][j] = 9;
                h[i][j] = 9;
                c[i][j] = 9;
                m[i][j] = 9;
            }
        }    
        
        //gera jogo novo (mexe pedras)
        for (int coluna = 0; coluna <= 27; coluna++) {
            sorte = r.nextInt(28);        
            
            do{
                if (molde[0][sorte] != 9){                    
                  jogo[0][coluna] = molde[0][sorte];
                  jogo[1][coluna] = molde[1][sorte];
                  molde[0][sorte] = 9;
                  sorte = -1;
                }
                else {sorte = r.nextInt(28);}
            } while (sorte != -1);
            //System.out.println(String.valueOf(jogo[0][coluna]) + " | " + String.valueOf(jogo[1][coluna]) + "  :  " + coluna);
            model_mostraTudo.addElement(String.valueOf(jogo[0][coluna]) + " | " + String.valueOf(jogo[1][coluna]) + "  :  " + coluna);
        }
        
        //cria jogo do humano
        for  (int j = 0; j <= (numPedrasH -1); j++){
            for (i = 0; i <= 1; i++) {
                h[i][j] = jogo[i][j];
                valor = valor.concat(Integer.toString(h[i][j]));
                if (i != 1) valor += " | ";
            }
            model_h.addElement(valor);
            valor = "";
        }
        
        //cria jogo do computador
        for (int j = numPedrasH; j <= (numPedrasH + numPedrasC -1); j++) {
            for (i = 0; i <= 1; i++) {
                c[i][j] = jogo[i][j];
                valor = valor.concat(Integer.toString(c[i][j]));
                if (i != 1) valor += " | ";
            }
            model_c.addElement(valor);
            valor = "";
        }
        
        //cria dorme
        for (int j = numPedrasH + numPedrasC; j < 28; j++) {
            model_dorme.addElement(model_mostraTudo.get(j).toString().substring(0, 5));
        }
        lblDorme.setText(String.valueOf(model_dorme.getSize()));
                
        gerarMolde();
    }
    
    public char checaQuemVaiComecar() {
        char quemVaiComecar;
        int carrocasH = 0;
        int carrocasC = 0;

        //checa se tem mais de 4 carrocas
        if (model_h.indexOf("6 | 6") != -1) {
            carrocasH++;
        }
        if (model_c.indexOf("6 | 6") != -1){
            carrocasC++;
        }
        if (model_h.indexOf("5 | 5") != -1){
            carrocasH++;
        }
        if (model_c.indexOf("5 | 5") != -1){
            carrocasC++;
        }
        if (model_h.indexOf("4 | 4") != -1){
            carrocasH++;
        }
        if (model_c.indexOf("4 | 4") != -1){
            carrocasC++;
        }
        if (model_h.indexOf("3 | 3") != -1){
            carrocasH++;
        }
        if (model_c.indexOf("3 | 3") != -1){
            carrocasC++;
        }
        if (model_h.indexOf("2 | 2") != -1){
            carrocasH++;
        }
        if (model_c.indexOf("2 | 2") != -1){
            carrocasC++;
        }
        if (model_h.indexOf("1 | 1") != -1){
            carrocasH++;
        }
        if (model_c.indexOf("1 | 1") != -1){
            carrocasC++;
        }
        if (model_h.indexOf("0 | 0") != -1){            
            carrocasH++;
        }
        if (model_c.indexOf("0 | 0") != -1){    
            carrocasC++;
        }
        //Verifica numero de carrocas
        if (carrocasH >= 4 || carrocasC >= 4) {
            //Nao se preocupe com nomes de metodos aqui
            alguemVenceuRound();
            alguemVenceuJogoTodo();
            mexerPedras();
            jTextArea1.setText(jTextArea1.getText() + "\n4 ou mais carrocas reiniciam o jogo");
            System.out.println("4 ou mais carrocas reiniciam o jogo");
        }

        //checa quem vai comecar
        if (model_h.indexOf("6 | 6") != -1) {
            quemVaiComecar = 'h';
            pedraDeComeco = "6 | 6";
        } else if (model_c.indexOf("6 | 6") != -1){
            quemVaiComecar = 'c';
            pedraDeComeco = "6 | 6";
        } else if (model_h.indexOf("5 | 5") != -1){
            quemVaiComecar = 'h';
            pedraDeComeco = "5 | 5";
        } else if (model_c.indexOf("5 | 5") != -1){
            quemVaiComecar = 'c';
            pedraDeComeco = "5 | 5";
        } else if (model_h.indexOf("4 | 4") != -1){
            quemVaiComecar = 'h';
            pedraDeComeco = "4 | 4";
        } else if (model_c.indexOf("4 | 4") != -1){
            quemVaiComecar = 'c';
            pedraDeComeco = "4 | 4";
        } else if (model_h.indexOf("3 | 3") != -1){
            quemVaiComecar = 'h';
            pedraDeComeco = "3 | 3";
        } else if (model_c.indexOf("3 | 3") != -1){
            quemVaiComecar = 'c';
            pedraDeComeco = "3 | 3";
        } else if (model_h.indexOf("2 | 2") != -1){
            quemVaiComecar = 'h';
            pedraDeComeco = "2 | 2";
        }  else if (model_c.indexOf("2 | 2") != -1){
            quemVaiComecar = 'c';
            pedraDeComeco = "2 | 2";
        } else if (model_h.indexOf("1 | 1") != -1){
            quemVaiComecar = 'h';
            pedraDeComeco = "1 | 1";
        } else if (model_c.indexOf("1 | 1") != -1){
            quemVaiComecar = 'c';
            pedraDeComeco = "1 | 1";
        } else if (model_h.indexOf("0 | 0") != -1){
            quemVaiComecar = 'h';
            pedraDeComeco = "0 | 0";
        } else if (model_c.indexOf("0 | 0") != -1){
            quemVaiComecar = 'c';
            pedraDeComeco = "0 | 0";
        } else {
            //Ninguém tem carroça (quemVaiComecar é alterada no metodo)
            jTextArea1.setText(jTextArea1.getText() + "\nNinguém tem carroça");
            System.out.println("Ninguém tem carroça");
            quemVaiComecar = checaQuemVaiComecarSemCarroca();
        }
        if (quemVaiComecar == 'h') System.out.println("Humano vai começar");
        else {
            jTextArea1.setText(jTextArea1.getText() + "\nComputador vai começar");
            System.out.println("Computador vai começar");
        }
        
        vez = quemVaiComecar;
        return quemVaiComecar;
    }
    
    public char checaQuemVaiComecarSemCarroca(){
        char quemVaiComecarSemCarroca = '-';
        
        for (int cont = 0; cont < 28; cont++){
            if (quemVaiComecarSemCarroca != '-') break;
            for (int j = 0; j < 2; j++) {
                if (h[0][j] == molde[0][cont] && h[1][j] == molde[1][cont]) {
                    quemVaiComecarSemCarroca = 'h';
                    System.out.println("Humano tem melhor pedra");
                    jTextArea1.setText(jTextArea1.getText() + "\nHumano tem melhor pedra");
                    pedraDeComeco = String.valueOf(h[0][j]) + " | " + String.valueOf(h[1][j]);
                    break;
                } else if (c[0][j+numPedrasH] == molde[0][cont] && c[1][j+numPedrasH] == molde[1][cont]){
                    quemVaiComecarSemCarroca = 'c';
                    System.out.println("Computador tem melhor pedra");
                    jTextArea1.setText(jTextArea1.getText() + "\nComputador tem melhor pedra");
                    pedraDeComeco = String.valueOf(c[0][j+numPedrasH]) + " | " + String.valueOf(c[1][j+numPedrasH]);
                    break;
                }
            }
        }
        return quemVaiComecarSemCarroca;
    }
  
    public int checaBatida() {
        //simples
        //carroca
        //la e lo
        //cruzada
        int pontos = 1;
        
        if (jToggleButton1.isSelected() == false){
        //jogou em cima
            //cruzada
            if (pedraJogadaA == pedraJogadaB && pedraJogadaA == ultimoNum) {
                System.out.println("Cruzada!");
                jTextArea1.setText(jTextArea1.getText() + "\nCruzada!");
                pontos = 4;
            } else if (pedraJogadaA == pedraJogadaB) {
                System.out.println("Carroça!");
                jTextArea1.setText(jTextArea1.getText() + "\nCarroça!");
                pontos = 2;
            } else if (pedraJogadaA == ultimoNum) {
                System.out.println("Lá e lô!");
                jTextArea1.setText(jTextArea1.getText() + "\nLá e lô!");
                pontos = 3;
            } else{
                System.out.println("Batida simples");
                jTextArea1.setText(jTextArea1.getText() + "\nBatida simples");
            }
        } else { //jogou em baixo
            if (pedraJogadaA == pedraJogadaB && pedraJogadaB == primeiroNum) {
                System.out.println("Cruzada!");
                jTextArea1.setText(jTextArea1.getText() + "\nCruzada!");
                pontos = 4;
            } else if (pedraJogadaA == pedraJogadaB) {
                System.out.println("Carroça!");
                jTextArea1.setText(jTextArea1.getText() + "\nCarroça!");
                pontos = 2;
            } else if (pedraJogadaB == primeiroNum) {
                System.out.println("Lá e lô!");
                jTextArea1.setText(jTextArea1.getText() + "\nLá e lô!");
                pontos = 3;
            } else {
                System.out.println("Batida simples");
                jTextArea1.setText(jTextArea1.getText() + "\nBatida simples");
            }
            
        }
        
        return pontos;
    }
    
    public boolean preValidacao(javax.swing.JList jList){
        //Atencao:  A lista é do jogador da vez
        boolean retorno;
        
        if (model_mesa.isEmpty()){
            primeiroNum = Integer.parseInt(jList.getSelectedValue().toString().substring(0, 1));
            ultimoNum = Integer.parseInt(jList.getSelectedValue().toString().substring(4, 5));
            retorno = true;
        }
        else {
            if (jToggleButton1.isSelected() == false) {
                if (primeiroNum == Integer.parseInt(jList.getSelectedValue().toString().substring(0, 1)) 
                        || primeiroNum == Integer.parseInt(jList.getSelectedValue().toString().substring(4, 5))) {
                    retorno = true;
                } else {
                    System.out.println("Problema com o primeiroNum");
                    jTextArea1.setText(jTextArea1.getText() + "\nProblema com o primeiro número");
                    retorno = false;
                }
            }
            else { // (jToggleButton1.isSelected() == true) 
                if (ultimoNum == Integer.parseInt(jList.getSelectedValue().toString().substring(0, 1)) 
                        || ultimoNum == Integer.parseInt(jList.getSelectedValue().toString().substring(4, 5))) {
                    retorno = true;
                } else {
                    System.out.println("Problema com o ultimoNum");
                    jTextArea1.setText(jTextArea1.getText() + "\nProblema com o último número");
                    retorno = false;
                }
            }
        }
       
        
        return retorno;
    }
    
    public String ordenaPedraFolha(String pedraDesordenada, javax.swing.JList jList, char inicioOuFim){
        String retorno = pedraDesordenada;
        switch (inicioOuFim) {
            case 'i':
                if (primeiroNum == Integer.parseInt(pedraDesordenada.substring(0, 1)) && model_mesaInterface.getSize() > 0){
                    retorno = inverterPedra(jList.getSelectedValue().toString());
                }   break;
            case 'f':
                if (ultimoNum == Integer.parseInt(pedraDesordenada.substring(4, 5)) && model_mesaInterface.getSize() > 0){
                    retorno = inverterPedra(jList.getSelectedValue().toString());
                }   break;
            default:
                retorno = "Erro no ordenamento";
                break;
        }

        //obtém os valores da pedra jogada (lado A e lado B já ordenados)

        pedraJogadaA = Integer.parseInt(retorno.substring(0, 1));
        pedraJogadaB = Integer.parseInt(retorno.substring(4, 5));
        
        return retorno;
    }
    
    public String inverterPedra(String pedraParaInverter){
        String a, b;
        
        a = pedraParaInverter.substring(0, 1);
        b = pedraParaInverter.substring(4, 5);
        
        return (b + " | " + a);
    }
    
    public void addNaInterface(javax.swing.JList jList){
        char i_f = (jToggleButton1.isSelected())?'f':'i';
        
        if (i_f == 'i') model_mesaInterface.add(0, ordenaPedraFolha(jList.getSelectedValue().toString(), jList, i_f));
        else model_mesaInterface.addElement(ordenaPedraFolha(jList.getSelectedValue().toString(), jList, i_f));
        
        primeiroNum = Integer.parseInt(model_mesaInterface.firstElement().toString().substring(0, 1));
        ultimoNum = Integer.parseInt(model_mesaInterface.lastElement().toString().substring(4, 5));
        
        System.out.println(primeiroNum + " : " + ultimoNum);
        jTextArea1.setText(jTextArea1.getText() + "\n" + primeiroNum + " : " + ultimoNum);
    }
    
    public void jogo_h(){
        
        //jogar
        if (jToggleButton1.isSelected() == false) {
            model_mesa.add(0, jList_h.getSelectedValue());
            addNaInterface(jList_h);
            
            for (int j = 0; j < 28; j++) {
                    if (h[0][j] == Integer.valueOf(jList_h.getSelectedValue().substring(0, 1)) &&
                        h[1][j] == Integer.valueOf(jList_h.getSelectedValue().substring(4, 5))) {
                        h[0][j] = 9;
                        h[1][j] = 9;
                        break;
                    }
            }

            atualizarMesa();            
            model_h.removeElementAt(jList_h.getSelectedIndex());
        } else {
            model_mesa.addElement(jList_h.getSelectedValue());
            addNaInterface(jList_h);
            
            for (int j = 0; j < 28; j++) {
                    if (h[0][j] == Integer.valueOf(jList_h.getSelectedValue().substring(0, 1)) &&
                        h[1][j] == Integer.valueOf(jList_h.getSelectedValue().substring(4, 5))) {
                        h[0][j] = 9;
                        h[1][j] = 9;
                        break;
                    }
            }
            atualizarMesa();
            model_h.removeElementAt(jList_h.getSelectedIndex());
        }
        posValidacao();
    }
    
    public boolean tenhoCima() {
        boolean tenhoCima = false;
        //avalia se tem como jogar em cima
        for (int n = 0; n < model_c.getSize(); n++) {
            if (Integer.parseInt(model_c.get(n).toString().substring(0, 1)) == primeiroNum ||
                    Integer.parseInt(model_c.get(n).toString().substring(4, 5)) == primeiroNum){
                cimao = n;
                //jList_c.setSelectedIndex(n);
                tenhoCima = true;
                break;
            }
        }
        
        return tenhoCima;
    }
    
    public boolean tenhoBaixo() {
        boolean tenhoBaixo = false;

        for (int n = 0; n < model_c.getSize(); n++) {
                if (Integer.parseInt(model_c.get(n).toString().substring(0, 1)) == ultimoNum ||
                        Integer.parseInt(model_c.get(n).toString().substring(4, 5)) == ultimoNum){
                    //jList_c.setSelectedIndex(n);
                    baixao = n;
                    tenhoBaixo = true;
                    break;
                }
            }
        
        return tenhoBaixo;
    }
    
    public void preJogo_c(){
    //siga estritamente o ritual do humano para evitar erros    
    //com temporizador
    //cima é 1, baixo é 0
        
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        public void run() {
        //System.out.println("tempo");            

        int cimaOuBaixo = r.nextInt(2);
        boolean tenhoCima = false;
        boolean tenhoBaixo = false;
      
        
        if (vez != 'c') {
            System.out.println("absurdo");
            vez = 'h';
        } else {
            //testa se é a primeira jogada (mesa limpa)
            if (model_mesaInterface.isEmpty()){
            //caso a mesa esteja limpa:
                //caso esteja à discricao do pc, sorteie
                if (pedraDeComeco == null) {
                    jList_c.setSelectedIndex(r.nextInt(model_c.getSize()));
                    //tenhoCima = true; tenhoBaixo = true;
                    if (preValidacao(jList_c)) jogo_c();
                } else { //caso nao esteja à discricao, escolha a primeira segundo as regras
                    for (int n = 0; n < model_c.getSize(); n++) {
                        if (pedraDeComeco.equals(model_c.get(n).toString())){
                            jList_c.setSelectedIndex(n);
                            //tenhoCima = true; tenhoBaixo = true;
                            if (preValidacao(jList_c)) jogo_c();
                            break;
                        }
                    }
                }
            } else { //caso não seja a primeira jogada
            //avalia se tem como jogar em cima ou embaixo
            tenhoCima = tenhoCima();
            tenhoBaixo = tenhoBaixo();

            //escolhe onde jogar
            switch (cimaOuBaixo) {
                case 1:
                    if (tenhoCima == true) {
                        jList_c.setSelectedIndex(cimao);
                        jToggleButton1.setSelected(false);
                        if (preValidacao(jList_c)) jogo_c();
                    } else if (tenhoBaixo == true) {
                        jList_c.setSelectedIndex(baixao);
                        jToggleButton1.setSelected(true);
                        if (preValidacao(jList_c)) jogo_c();
                    } else {
                        System.out.println("Não tenho");
                        jTextArea1.setText(jTextArea1.getText() + "\nNão tenho");
                        dorme();
                    }
                    break;
                case 0:
                    if (tenhoBaixo == true) {
                        jList_c.setSelectedIndex(baixao);
                        jToggleButton1.setSelected(true);
                        if (preValidacao(jList_c)) jogo_c();
                    } else if (tenhoCima == true) {
                        jList_c.setSelectedIndex(cimao);
                        jToggleButton1.setSelected(false);
                        if (preValidacao(jList_c)) jogo_c();
                    } else {
                        System.out.println("Não tenho");
                        jTextArea1.setText(jTextArea1.getText() + "\nNão tenho");
                        dorme();
                    }                
                    break;
            }
        }
        System.out.println("Computador agiu");
        }
      }
     }, 1000);

    }
    
    public void jogo_c(){
        
        //jogar
        if (jToggleButton1.isSelected() == false) {
            model_mesa.add(0, jList_c.getSelectedValue());
            addNaInterface(jList_c);
            
            for (int j = 0; j < 28; j++) {
                    if (c[0][j] == Integer.valueOf(jList_c.getSelectedValue().substring(0, 1)) &&
                        c[1][j] == Integer.valueOf(jList_c.getSelectedValue().substring(4, 5))) {
                        c[0][j] = 9;
                        c[1][j] = 9;
                        break;
                    }
            }

            atualizarMesa();            
            model_c.removeElementAt(jList_c.getSelectedIndex());
        } else {
            model_mesa.addElement(jList_c.getSelectedValue());
            addNaInterface(jList_c);
            
            for (int j = 0; j < 28; j++) {
                    if (c[0][j] == Integer.valueOf(jList_c.getSelectedValue().substring(0, 1)) &&
                        c[1][j] == Integer.valueOf(jList_c.getSelectedValue().substring(4, 5))) {
                        c[0][j] = 9;
                        c[1][j] = 9;
                        break;
                    }
            }
            atualizarMesa();
            model_c.removeElementAt(jList_c.getSelectedIndex());
        }
        posValidacao();
    }
    
    public void contagemDePontos(){
        char vencedor;
        int contadorH = 0;
        int contadorC = 0;
        //contar humano
        for (int i = 0; i < model_h.getSize(); i++) {
            contadorH += Integer.parseInt(model_h.get(i).toString().substring(0,1)) + Integer.parseInt(model_h.get(i).toString().substring(4,5));
        }
        //contar computador
        for (int i = 0; i < model_c.getSize(); i++) {
            contadorC += Integer.parseInt(model_c.get(i).toString().substring(0,1)) + Integer.parseInt(model_c.get(i).toString().substring(4,5));
        }
        //ver vencedor
        if (contadorH < contadorC){ //caso humano venca
            vencedor = 'h';
            pontuacaoH++;
            lblPontuacaoH.setText(String.valueOf(pontuacaoH));            
            ++numPedrasC;
            //checa se acabou o jogo inteiro
            if (pontuacaoH >= 7) {
                JOptionPane.showMessageDialog(null, "Você venceu o jogo!");
                alguemVenceuRound();
                alguemVenceuJogoTodo();
            } else if (pontuacaoH >= 6 && pontuacaoC == 0){
                JOptionPane.showMessageDialog(null, "Você venceu de buchuda!");
                alguemVenceuRound();
                alguemVenceuJogoTodo();
            } else {
                JOptionPane.showMessageDialog(null, "Você venceu o round!");
                alguemVenceuRound();
            }
            vez = 'h';
            mexerPedras();
            
        }
        else if (contadorH > contadorC){ //caso computador venca
            vencedor = 'c';
            vez = 'c';
            pontuacaoC++;
            lblPontuacaoC.setText(String.valueOf(pontuacaoC));                        
            ++numPedrasH;
            
            //checa se acabou o jogo inteiro
            if (pontuacaoC >= 7){
                JOptionPane.showMessageDialog(null, "Você perdeu o jogo!");
                alguemVenceuRound();
                alguemVenceuJogoTodo();
            } else if (pontuacaoC >= 6 && pontuacaoH == 0) {
                JOptionPane.showMessageDialog(null, "Você perdeu de buchuda!");
                alguemVenceuRound();
                alguemVenceuJogoTodo();
            } else {
                JOptionPane.showMessageDialog(null, "Você perdeu o round!");
                alguemVenceuRound();
            }               
            vez = 'c';
            mexerPedras();
            preJogo_c();
            
        }        
        else { //empate
            vencedor = '0';
            System.out.println("Empate");
            jTextArea1.setText(jTextArea1.getText() + "\nEmpate");
            alguemVenceuRound();
            mexerPedras();
            trocaVez();
        }
        
    }

    public void posValidacao(){
        System.out.println(vez + " jogou " + pedraJogadaA + " | " + pedraJogadaB);
        jTextArea1.setText(jTextArea1.getText() + "\n" + vez + " jogou " + pedraJogadaA + " | " + pedraJogadaB);
        if (model_h.size() == 0){
        //humano vencendo
            //checa se acabou o round
            pontuacaoH += checaBatida();
            lblPontuacaoH.setText(String.valueOf(pontuacaoH));            
            ++numPedrasC;
            
            //checa se acabou o jogo inteiro
            if (pontuacaoH >= 7) {
                JOptionPane.showMessageDialog(null, "Você venceu o jogo!");
                alguemVenceuRound();
                alguemVenceuJogoTodo();
            } else if (pontuacaoH >= 6 && pontuacaoC == 0){
                JOptionPane.showMessageDialog(null, "Você venceu de buchuda!");
                alguemVenceuRound();
                alguemVenceuJogoTodo();
            } else {
                JOptionPane.showMessageDialog(null, "Você venceu o round!");
                alguemVenceuRound();
            }
            vez = 'h';
            mexerPedras();

        } else if (model_c.size() == 0){
        //computador vencendo
            //checa se acabou o round
            pontuacaoC += checaBatida();
            lblPontuacaoC.setText(String.valueOf(pontuacaoC));                        
            ++numPedrasH;
            
            //checa se acabou o jogo inteiro
            if (pontuacaoC >= 7){
                JOptionPane.showMessageDialog(null, "Você perdeu o jogo!");
                alguemVenceuRound();
                alguemVenceuJogoTodo();
            } else if (pontuacaoC >= 6 && pontuacaoH == 0) {
                JOptionPane.showMessageDialog(null, "Você perdeu de buchuda!");
                alguemVenceuRound();
                alguemVenceuJogoTodo();
            } else {
                JOptionPane.showMessageDialog(null, "Você perdeu o round!");
                alguemVenceuRound();
            }               
            vez = 'c';
            mexerPedras();
            preJogo_c();
        } else {
            trocaVez();
        }
        
    }
    public void alguemVenceuRound(){
        //tambem usado para reordenamento
        gerarMolde();
        model_h.clear();
        model_c.clear();
        model_mesa.clear();
        model_mesaInterface.clear();
        model_mostraTudo.clear();
        model_dorme.clear();
        primeiroNum = 9;
        ultimoNum = 9;
        pedraDeComeco = null;
        jTextArea1.setText("");
    }
    
    public void alguemVenceuJogoTodo(){
        //tambem usado para reordenamento
        qnt_m = 22;

        mortoDaVez = '6';

        numPedrasH = 3;
        numPedrasC = 3;

        pontuacaoH = 0;
        pontuacaoC = 0;
        
        lblPontuacaoH.setText(String.valueOf(pontuacaoH));
        lblPontuacaoC.setText(String.valueOf(pontuacaoC));

        pedraDeComeco = null;
        
        jTextArea1.setText("");
    }
    
    public void trocaVez(){
        switch (vez){
            case 'h':
                vez = 'c';
                preJogo_c();
                break;
            case 'c':
                vez = 'h';
                break;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList_mesa = new javax.swing.JList<>();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList_h = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList_c = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList_mesaInterface = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList_jogo = new javax.swing.JList<>();
        lblPontuacaoH = new javax.swing.JLabel();
        lblPontuacaoC = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTocarMesa = new javax.swing.JLabel();
        btnDorme = new javax.swing.JButton();
        lblDorme = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList_dorme = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dominó");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_mesa.setModel(model_mesa);
        jList_mesa.setName(""); // NOI18N
        jScrollPane1.setViewportView(jList_mesa);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 401, 113, 140));

        jToggleButton1.setText("Cima/Baixo");
        getContentPane().add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 110, -1, -1));

        jList_h.setModel(model_h);
        jScrollPane2.setViewportView(jList_h);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(301, 65, 113, 325));

        jList_c.setModel(model_c);
        jScrollPane3.setViewportView(jList_c);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 65, 113, 325));

        jButton1.setText("Jogar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 151, 85, -1));

        jList_mesaInterface.setModel(model_mesaInterface);
        jList_mesaInterface.setName(""); // NOI18N
        jScrollPane4.setViewportView(jList_mesaInterface);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 65, 113, 325));

        jList_jogo.setModel(model_mostraTudo);
        jList_jogo.setName(""); // NOI18N
        jScrollPane5.setViewportView(jList_jogo);

        getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(792, 223, 113, 336));

        lblPontuacaoH.setText("0");
        getContentPane().add(lblPontuacaoH, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 0, 54, 28));

        lblPontuacaoC.setText("0");
        getContentPane().add(lblPontuacaoC, new org.netbeans.lib.awtextra.AbsoluteConstraints(623, 0, 54, 28));

        jLabel1.setText("x");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 0, -1, 28));

        lblTocarMesa.setBackground(new java.awt.Color(153, 0, 0));
        lblTocarMesa.setForeground(new java.awt.Color(255, 255, 255));
        lblTocarMesa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTocarMesa.setText("Mesa");
        lblTocarMesa.setOpaque(true);
        lblTocarMesa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTocarMesaMouseClicked(evt);
            }
        });
        getContentPane().add(lblTocarMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 268, 98, 93));

        btnDorme.setText("Dorme");
        btnDorme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDormeActionPerformed(evt);
            }
        });
        getContentPane().add(btnDorme, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 401, -1, 111));

        lblDorme.setText("22");
        getContentPane().add(lblDorme, new org.netbeans.lib.awtextra.AbsoluteConstraints(489, 430, 27, 36));

        jList_dorme.setModel(model_dorme);
        jScrollPane6.setViewportView(jList_dorme);

        getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 401, 111, 158));

        jButton2.setText("Contagem de Pontos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 396, -1, -1));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane7.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 570, 698, -1));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("<html><i>Autor: Lucas Paz</i></html>");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(813, 637, 111, 29));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tabuleiro");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 120, 20));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Você");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 40, 120, 20));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Computador");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(629, 40, 120, 20));

        jButton3.setText("Mostrar/ocultar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 133, 33));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (model_h.isEmpty() == true) {
            System.out.println("Vc está sem pedras");
            jTextArea1.setText(jTextArea1.getText() + "\nVc está sem pedras");
        }
        else if (jList_h.isSelectionEmpty() == true) {
            System.out.println("Selecione alguma pedra");
            jTextArea1.setText(jTextArea1.getText() + "\nSelecione alguma pedra");
        } 
        else if (model_mesa.isEmpty() && pontuacaoH == 0 && pontuacaoC == 0){
            if (jList_h.getSelectedValue().equals(pedraDeComeco)) jogo_h();
            else {
                System.out.println("Comece com a maior carroça. Se não tem carroca, a maior pedra.");
                jTextArea1.setText(jTextArea1.getText() + "\nComece com a maior carroça. Se não tem carroca, a maior pedra.");
            }
        }
        else {if (preValidacao(jList_h)) jogo_h(); else {
            System.out.println("Erro");
            jTextArea1.setText(jTextArea1.getText() + "\nErro");
        }}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lblTocarMesaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTocarMesaMouseClicked
        // TODO add your handling code here:
        trocaVez();
    }//GEN-LAST:event_lblTocarMesaMouseClicked

    public void dorme() { //método para o computador
        //checa se há dorme
        if (!model_dorme.isEmpty()) { //se houver dorme:
            model_c.addElement(model_dorme.get(0));
            System.out.println(vez + " pegou " + model_dorme.remove(0).toString() + " do dorme");
            jTextArea1.setText(jTextArea1.getText() + "\n" + vez + " pegou " + model_dorme.remove(0).toString() + " do dorme");
            lblDorme.setText(String.valueOf(model_dorme.getSize()));
            preJogo_c();
        }

        else { //caso dorme vazio:
            System.out.println("Dorme vazio, não tenho");
            jTextArea1.setText(jTextArea1.getText() + "\nDorme vazio, não tenho");
            //verificar se "trancou"       
            //Humano tem em cima?
            boolean tenhoCima = false;
            //avalia se tem como jogar em cima
            for (int n = 0; n < model_h.getSize(); n++) {
                if (Integer.parseInt(model_h.get(n).toString().substring(0, 1)) == primeiroNum ||
                        Integer.parseInt(model_h.get(n).toString().substring(4, 5)) == primeiroNum){
                    tenhoCima = true;
                    break;
                }
            }
            //Humano tem embaixo?
            boolean tenhoBaixo = false;
            //avalia se tem como jogar embaixo
            for (int n = 0; n < model_h.getSize(); n++) {
                    if (Integer.parseInt(model_h.get(n).toString().substring(0, 1)) == ultimoNum ||
                            Integer.parseInt(model_h.get(n).toString().substring(4, 5)) == ultimoNum){
                        tenhoBaixo = true;
                        break;
                    }
                }
            //caso humano não tenha (e nao tem dorme), entao trancou, pois a maquina ja nao tinha de antemao
            if (tenhoCima == false && tenhoBaixo == false) {
                System.out.println("Trancou");
                jTextArea1.setText(jTextArea1.getText() + "\nTrancou");
                contagemDePontos();
            } else { //caso humano tenha (mesmo sem dorme), a vez vai para o humano
                trocaVez();
            }
        }        
    }
    
    private void btnDormeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDormeActionPerformed
        // TODO add your handling code here:
    //aqui é para o humano
        //checa se há dorme
        if (!model_dorme.isEmpty()) { //se houver dorme:
            model_h.addElement(model_dorme.get(0));
            System.out.println(vez + " pegou " + model_dorme.get(0).toString() + " do dorme");
            jTextArea1.setText(jTextArea1.getText() + "\n" + vez + " pegou " + model_dorme.remove(0).toString() + " do dorme");
            lblDorme.setText(String.valueOf(model_dorme.getSize()));
        }
        //caso dorme vazio:
        else {
            System.out.println("Dorme vazio");
            jTextArea1.setText(jTextArea1.getText() + "\nDorme vazio");
        }        
    }//GEN-LAST:event_btnDormeActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        contagemDePontos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (jList_c.isVisible()){
            jList_c.setVisible(false);
            jScrollPane3.setVisible(false);
        } else {
            jList_c.setVisible(true);
            jScrollPane3.setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Domino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Domino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Domino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Domino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Domino().setVisible(true);

            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDorme;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList_c;
    private javax.swing.JList<String> jList_dorme;
    private javax.swing.JList<String> jList_h;
    private javax.swing.JList<String> jList_jogo;
    private javax.swing.JList<String> jList_mesa;
    private javax.swing.JList<String> jList_mesaInterface;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblDorme;
    private javax.swing.JLabel lblPontuacaoC;
    private javax.swing.JLabel lblPontuacaoH;
    private javax.swing.JLabel lblTocarMesa;
    // End of variables declaration//GEN-END:variables
}
