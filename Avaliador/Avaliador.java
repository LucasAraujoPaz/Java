package avaliador;

import java.util.ArrayList;
import java.util.Arrays;

/** Avalia uma expressão matemática com ou sem variáveis previamente definidas pelo usuário.
* <p>O modelo de número é tradicional: <i>+</i>(ou)<i>-123.456E+</i>(ou)<i>-789</i><br>
* Obs.: '+' e '-' e a notação científica são opcionais, 'E' sempre maiúsculo.</p>
* <p>As variáveis recebem como valor um número (double) ou uma expressão matemática (String), a qual pode até citar outras variáveis.</p>
* <br>Cuidado, pois pode haver loop se os valores das variáveis ficarem referenciando outras variáveis indefinidamente.
* <p>Operadores: ^ (Exponenciação), * , / , % (MOD), + , - </p>
* <p>Se o usuário cometer erros de sintaxe, esta classe lança erros tipo "Error" com mensagem personalizada.</p>
* 
* @author Lucas Paz
*/
public class Avaliador {

    private ArrayList<String> nomesDasVariaveis = new ArrayList(){};
    private ArrayList<String> valoresDasVariaveis = new ArrayList(){}; //<String>, pois pode receber até expressões

    private static final ArrayList<Character> NUMEROS = new ArrayList(Arrays.asList(new Character[]{'0','1','2','3','4','5','6','7','8','9'}));
    private static final ArrayList<Character> CARACTERES_ACEITOS_EM_NUMEROS = new ArrayList(Arrays.asList(new Character[]{'+','-','0','1','2','3','4','5','6','7','8','9','.','E'})); // E = (notação científica)
    private static final ArrayList<Character> CARACTERES_ACEITOS_EM_VARIAVEIS = new ArrayList(Arrays.asList(new Character[]{'_','$','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'}));
    private static final ArrayList<Character> OPERADORES = new ArrayList(Arrays.asList(new Character[]{'^','*','/','%','+','-'}));// "^" exponencia

    private static final ArrayList<Character> LETRAS = new ArrayList(Arrays.asList(new Character[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}));

    private static final ArrayList<Character> TODOS_OS_CARACTERES_ACEITOS_NO_AVALIADOR = new ArrayList(Arrays.asList(new Character[]{'+','-','0','1','2','3','4','5','6','7','8','9','.','E','(',')','^','*','/','%','+','-','$','_','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' '})); //Símbolos base da classe (inclui espaco, _ e $)

    /**Construtor vazio.<br>
     * Realize as avaliações de expressões matemáticas através do objeto instanciado.
     */
    public Avaliador() {
    }

    /**Método principal, avalia uma expressão matemática e retorna o valor resultante.
     * <p>Pode receber números, variáveis já declaradas e parêntesis.</p>
     * @param expressao Expressão matemática.
     * @return Resultado final.
     * @throws Error Erro personalizado se o usuário cometer erro.
     */
    public double avaliar(String expressao) throws Error {
        double resultadoFinal=0;

        preValidarExpressao(expressao); //Pré-validação para encontrar erros básicos

        expressao = expressao.replaceAll(" ", ""); //Tira os espaços

        expressao = converterTodasAsVariaveis(expressao); //Aplaina: substitui variáveis por números

        resultadoFinal = resolverExpressao(expressao); //resolver a matemática

        return resultadoFinal;
    }

    /**
     * Pre-validação. Não checa já números e variáveis, que são checados em outros métodos durante a avaliação.
     * @param expressao Expressão matemática.
     * @return Se a expressão está sem erros em pré-validação.
     */
    private boolean preValidarExpressao(String expressao) throws Error {
        boolean valida = true;//caso a expressão esteja errada, este método lançará um erro personalizado, senão retornará true
        String expressaoSemEspacos = expressao.replaceAll(" ", "");//tira os espaços

        if (expressaoSemEspacos.equals("")) throw new Error("Expressão vazia");

        Character primeiroCharAposEspacos = expressaoSemEspacos.charAt(0);        
        if ( ! CARACTERES_ACEITOS_EM_VARIAVEIS.contains(primeiroCharAposEspacos) && primeiroCharAposEspacos != '(' && primeiroCharAposEspacos != '+' && primeiroCharAposEspacos != '-') throw new Error("Primeiro caracter mal colocado");

        int parentesisAbertos = 0;
        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);
            Character charAntesDosEspacos = acharProximosCaracteresPulandoEspacos(i, expressao)[0];
            Character charDepoisDosEspacos = acharProximosCaracteresPulandoEspacos(i, expressao)[1];

            if (!TODOS_OS_CARACTERES_ACEITOS_NO_AVALIADOR.contains(c)) throw new Error("Caracter '" + c + "' não aceito");
            else if (c == ' '){
                if (i == 0 || i == expressao.length()-1) continue;
                else {
                    if (CARACTERES_ACEITOS_EM_VARIAVEIS.contains(charAntesDosEspacos) && CARACTERES_ACEITOS_EM_VARIAVEIS.contains(charDepoisDosEspacos)) throw new Error("Espaçamento mal colocado");
                }
            } else if (NUMEROS.contains(c) && i != expressao.length()-1){
                if (i == 0) acharPosicaoFinalDoNumero(i, expressao);
                else {
                    char charUmaAntes = expressao.charAt(i-1);
                    if (charUmaAntes == '(' || charUmaAntes == ' ' || OPERADORES.contains(charUmaAntes)) acharPosicaoFinalDoNumero(i, expressao);
                }
            } else if (c == '+' || c == '-') {
                if ((charAntesDosEspacos == '+' || charAntesDosEspacos == '-') && (charDepoisDosEspacos == '+' || charDepoisDosEspacos == '-')) throw new Error("Operador mal colocado");
                else if (charDepoisDosEspacos == '¬' || charDepoisDosEspacos == ')' || charDepoisDosEspacos == '^' || charDepoisDosEspacos == '*' || charDepoisDosEspacos == '/' || charDepoisDosEspacos == '%') throw new Error("Operador mal colocado");
                else if (expressao.charAt(i+1) == c) throw new Error("Operador mal colocado");
            } else if (c == '^' || c == '*' || c == '/' || c == '%') {
                if ( ! CARACTERES_ACEITOS_EM_VARIAVEIS.contains(charAntesDosEspacos) && charAntesDosEspacos != ')') throw new Error("Operador mal colocado");
                else if ( ! CARACTERES_ACEITOS_EM_VARIAVEIS.contains(charDepoisDosEspacos) && charDepoisDosEspacos != '(' && charDepoisDosEspacos != '+' && charDepoisDosEspacos != '-') throw new Error("Operador mal colocado");
            } else if (c == '('){
                parentesisAbertos++;
                acharPosicaoFinalDosParentesis(i, expressao);
                if (charAntesDosEspacos == ')' || charDepoisDosEspacos == ')' || (charAntesDosEspacos != '(' && charAntesDosEspacos != '¬' && !OPERADORES.contains(charAntesDosEspacos))) throw new Error("Parêntesis mal colocado");
            } else if (c == ')') {
                if (parentesisAbertos < 1) throw new Error("Parêntesis mal colocado");
                else parentesisAbertos--;

                if (charAntesDosEspacos == '(' || charDepoisDosEspacos == '(' || (charDepoisDosEspacos != ')' && charDepoisDosEspacos != '¬' && !OPERADORES.contains(charDepoisDosEspacos))) throw new Error("Parêntesis mal colocado");
            } else if (c == '.'){
                if ( ! NUMEROS.contains(charAntesDosEspacos) || ! NUMEROS.contains(charDepoisDosEspacos)) throw new Error("Ponto mal colocado");
            }
        }

        return valida;
    }

    /**
     * Dados um índice e uma expressão, acha o próximo char != ' ' à esquerda (será [0]) e à direita (será [1]).
     * @param posicaoInicial Posição inicial da busca.
     * @param expressao Expressão matemática.
     * @return EsquerdaEDireita[0] é o 1º char à esquerda, EsquerdaEDireita[1] é o 1º char à direita.
     */
    private Character[] acharProximosCaracteresPulandoEspacos(int posicaoInicial, String expressao) throws Error {
        Character[] EsquerdaEDireita = {'¬', '¬'}; //EsquerdaEDireita[0] é o 1º char à esquerda, EsquerdaEDireita[1] é o 1º char à direita

        for (int esquerda = posicaoInicial; esquerda >= 0; esquerda--) {
            if (esquerda == posicaoInicial) continue;
            if (expressao.charAt(esquerda) != ' ') {
                EsquerdaEDireita[0] = expressao.charAt(esquerda);
                break;
            }
        }

        for (int direita = posicaoInicial; direita < expressao.length(); direita++) {
            if (direita == posicaoInicial) continue;
            if (expressao.charAt(direita) != ' ') {
                EsquerdaEDireita[1] = expressao.charAt(direita);
                break;
            }
        }
        //pode retornar '¬' (simbolizando nulo) em qualquer dos 2 lados (ou nos 2) se acabar a expressão
        return EsquerdaEDireita;
    }

    /**
     * Encontra o parêntesis final ')' que fecha o inicial '('.
     * @param posicaoInicialDoParentesis Posição do parêntesis de abertura.
     * @param expressao Expressão matemática.
     * @return Posição do parêntesis final.
     */
    private int acharPosicaoFinalDosParentesis(int posicaoInicialDoParentesis, String expressao) throws Error {
        int posicaoFinalDoParentesis = posicaoInicialDoParentesis;
        int parentesisAbertos = 0;
        int parentesisFechados = 0;

        if (expressao.charAt(posicaoInicialDoParentesis) != '(') throw new Error("Erro interno: Não começou no parêntesis aberto");

        for (int i = posicaoInicialDoParentesis; i < expressao.length(); i++) {
            char c = expressao.charAt(i);
            if (c != '(' && c != ')') continue;
            
            if (c == '(') parentesisAbertos++;
            else if (c == ')') parentesisFechados++;

          //if (parentesisFechados > parentesisAbertos) throw new Error("Parêntesis mal colocado");desnecessario
            if (parentesisFechados == parentesisAbertos) {
                posicaoFinalDoParentesis = i;
                break;
            }
        }
        if (parentesisFechados != parentesisAbertos) throw new Error("Parêntesis incompletos");
        //else if (expressao.charAt(posicaoFinalDoParentesis) != ')') throw new Error("Não terminou no parêntesis fechado");desnecessario

        return posicaoFinalDoParentesis;
    }


    /** Converte todas as variáveis da expressão em seus valores numéricos.<br>
    * Se o valor registrado da variável for outra expressão, ele vai avaliá-la também.<br>
    * Cuidado, pois pode haver loop se os valores das variáveis ficarem referenciando outras variáveis indefinidamente.
    * @param expressao Expressão matemática (Possivelmente com variáveis).
    * @return Expressão matemática com as variáveis convertidas em números.
    */
    private String converterTodasAsVariaveis(String expressao) throws Error {
        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);
            if (LETRAS.contains(c) || c == '_' || c == '$'){//retira fora a variavel e coloca o valor dela no lugar
                if (c != 'E' || (c == 'E' && (i==0 || (i > 0 && !NUMEROS.contains(expressao.charAt(i-1)))))){//aqui é o cuidado com o E da notacao cientifica dos numeros "1E2"
                    int posicaoFinalDaVariavel = acharPosicaoFinalDaVariavel(i, expressao);
                    String primeiroPedacoDaString = expressao.substring(0, i);
                    String segundoPedacoDaString = (posicaoFinalDaVariavel != expressao.length()-1)?expressao.substring(posicaoFinalDaVariavel+1, expressao.length()):"";

                    String variavel = "(" + revelarValorDaVariavel(i, posicaoFinalDaVariavel, expressao) + ")";
                    expressao = primeiroPedacoDaString + variavel + segundoPedacoDaString;

                    if (segundoPedacoDaString.equals("")) break;
                }
            }
        }

        return expressao;
    }

    /**
     * Retorna o índice do último símbolo da variavel (zero-based index do último elemento do variável).
     * @param posicaoInicialDaVariavel Posição do começo da variável.
     * @param expressao Expressão matemática.
     * @return Posição do fim da variável (inclusive).
     */
    private int acharPosicaoFinalDaVariavel(int posicaoInicialDaVariavel, String expressao) throws Error {
        int posicaoFinalDaVariavel = posicaoInicialDaVariavel;

        char primeiroCharDaVariavel = expressao.charAt(posicaoInicialDaVariavel);
        if (primeiroCharDaVariavel != '_' && primeiroCharDaVariavel != '$' && !LETRAS.contains(primeiroCharDaVariavel)) throw new Error("Erro interno: Primeiro char da variável errado");//caso o primeiro char seja errado
        else if (posicaoInicialDaVariavel != 0 && expressao.charAt(posicaoInicialDaVariavel - 1) == ')') throw new Error("Parêntesis mal colocado");

        for (int i = posicaoInicialDaVariavel + 1; i < expressao.length(); i++) {
            char c = expressao.charAt(i);

            if (CARACTERES_ACEITOS_EM_VARIAVEIS.contains(c)) {
                posicaoFinalDaVariavel = i;
            } else break; //caso terminou a variável
        }

        if (posicaoFinalDaVariavel != expressao.length()-1) {
            char charDepoisDoNumero = expressao.charAt(posicaoFinalDaVariavel + 1);
            if (charDepoisDoNumero != ')' && !OPERADORES.contains(charDepoisDoNumero)) throw new Error("'" + charDepoisDoNumero + "'" + " mal colocado");
        }

        return posicaoFinalDaVariavel;
    }

    /**
     * Revela o valor da variável avaliando a expressão da própria variável e transforma essa expressão em número se não era.<br>
     * Se o valor registrado da variável for uma expressão citando outras variáveis, ele vai avaliá-las também.<br>
     * Cuidado, pois pode haver loop se os valores das variáveis ficarem referenciando outras variáveis indefinidamente.
     * @param posicaoInicialDaVariavel Posição do começo da variável.
     * @param posicaoFinalDaVariavel Posição do fim da variável (inclusive).
     * @param expressao Expressão matemática.
     * @return Valor numérico da variável.
     * @throws Error 
     */
    private double revelarValorDaVariavel(int posicaoInicialDaVariavel, int posicaoFinalDaVariavel, String expressao) throws Error {
        String nomeDaVariavel = expressao.substring(posicaoInicialDaVariavel, posicaoFinalDaVariavel+1);
        if (nomesDasVariaveis.indexOf(nomeDaVariavel) == -1) throw new Error("Variável '" + nomeDaVariavel + "' não declarada");
        String expressaoDoValorDaVariavel = valoresDasVariaveis.get(nomesDasVariaveis.indexOf(nomeDaVariavel));
        double valorJaAvaliadoDaVariavel=0;
        try {
            valorJaAvaliadoDaVariavel = Double.valueOf(expressaoDoValorDaVariavel);
        } catch (NumberFormatException exception){
            valorJaAvaliadoDaVariavel = avaliar(expressaoDoValorDaVariavel);
        }

        mudarValorDeVariavel(nomeDaVariavel, String.valueOf(valorJaAvaliadoDaVariavel));

        return valorJaAvaliadoDaVariavel;
    }

    /**
     * Método base que começa a resolução de uma expressão matemática.
     * @param expressao Expressão matemática.
     * @return Resultado.
     */
    private Double resolverExpressao(String expressao) throws Error {
        Double resultado = 0d;
        int posicaoFinal = -1; //posicao final do resultado ou da variavel (lembrar de pular mais uma casa se necessario)

        char c = expressao.charAt(0);

        if (c == '('){//atencao aqui!
            int posicaoDoParentesisFinal = acharPosicaoFinalDosParentesis(0, expressao);

            if (posicaoDoParentesisFinal == expressao.length()-1 || expressao.charAt(posicaoDoParentesisFinal + 1) == ')') {//caso depois do parêntesis final acaba tudo ou fecha outro parêntesis
                return resolverExpressao(expressao.substring(1, posicaoDoParentesisFinal));
            } else {//caso depois do parêntesis final ainda há contas a fazer 
                int posicaoDoOperador = posicaoDoParentesisFinal + 1;
                char operador = expressao.charAt(posicaoDoOperador);

                if (operador == '^') return resolverCadeiaDeExpoentes(0, posicaoDoOperador, expressao);
                else if (operador == '*' || operador == '/' || operador == '%') return resolverCadeiaDeMultiDivEMod(0, posicaoDoOperador, expressao);
                else if (operador == '+' || operador == '-') return resolverCadeiaDeMaisEMenos(0, posicaoDoOperador, expressao);
            }
        } else if (NUMEROS.contains(c)){//se for um numero
            posicaoFinal = acharPosicaoFinalDoNumero(0, expressao); //posicao final do numero
            return resolverNumero(0, posicaoFinal, expressao); //metodo interno que se encarrega de fazer as contas de acordo com o operador
        } else if (c == '+' || c == '-'){//se for + ou - pode ser numero ou +() ou -()
            char cMaisUm = expressao.charAt(1); //o que vem depois
            if (NUMEROS.contains(cMaisUm) || cMaisUm == '+' || cMaisUm == '-'){//se for um numero: +n ou -n ou +-n ou -+n (++n e --n não são aceitos). Tome cuidado e verifique se a sua plataforma aceita tais formas
                posicaoFinal = acharPosicaoFinalDoNumero(1, expressao); //posicao final do numero
                return resolverNumero(0, posicaoFinal, expressao); //metodo interno que se encarrega de fazer as contas de acordo com o operador
            } else if (cMaisUm == '(') {
                return (c=='+') ? resolverExpressao(expressao.substring(1)) : -1 * resolverExpressao(expressao.substring(1)); //checa se antes do parentesis é +(... ou -(...
            }
        }

        return resultado;
    }

    /**
     * Retorna o indice do último símbolo do número (zero-based index da último elemento do número).<br>
     * Modelo de número: +(ou)-123.456E+(ou)-789 //'+', '-' e parte da notação científica opcionais.<br>
     * ++n, --n, +-n, -+n e "coisas piores" não aceitos neste método. No entanto, o avaliador como um todo aceita certas formas.
     * @param posicaoInicialDoNumero Posição do começo do número.
     * @param expressao Expressão matemática.
     * @return Posição do fim do número (inclusive).
     */
    private int acharPosicaoFinalDoNumero(int posicaoInicialDoNumero, String expressao) throws Error {
        int posicaoFinalDoNumero = posicaoInicialDoNumero;

        char primeiroCharDoNumero = expressao.charAt(posicaoInicialDoNumero);
        if ( ! CARACTERES_ACEITOS_EM_NUMEROS.contains(primeiroCharDoNumero)) throw new Error("Erro interno: Primeiro char errado");//caso o primeiro char seja errado
        else if (posicaoInicialDoNumero != 0 && expressao.charAt(posicaoInicialDoNumero - 1) == ')') throw new Error("Parêntesis mal colocado");

        for (int i = posicaoInicialDoNumero + 1; i < expressao.length(); i++) {
            char c = expressao.charAt(i);

            if (NUMEROS.contains(c) || c == '.'){//se for número puro ou ponto
                posicaoFinalDoNumero = i;
            } else if (c == 'E'){//senao, se for E (notação científica)...
                //aqui é o cuidado com eventuais erros do usuário com a notação científica
                if (i == expressao.length()-1 || (expressao.charAt(i+1) != '+' && expressao.charAt(i+1) != '-' && !NUMEROS.contains(expressao.charAt(i+1)))) throw new Error("Erro na notação científica 'E'");
                else if (expressao.charAt(i+1) == '+' || expressao.charAt(i+1) == '-'){
                    if (i+1 == expressao.length()-1 || !NUMEROS.contains(expressao.charAt(i+2))) throw new Error("Erro na notação científica 'E'");
                }
                //caso tudo ok:
                i++;
                posicaoFinalDoNumero = i;
            } else {//caso o número acabou
                posicaoFinalDoNumero = i-1;
                break;
            }
        }

        if (posicaoFinalDoNumero != expressao.length()-1) {
            char charDepoisDoNumero = expressao.charAt(posicaoFinalDoNumero + 1);
            if (charDepoisDoNumero != ' ' && charDepoisDoNumero != ')' && !OPERADORES.contains(charDepoisDoNumero)) throw new Error("'" + charDepoisDoNumero + "'" +" mal colocado");
        }
        
        return posicaoFinalDoNumero;
    }
    
    /**
     * Método interno que se encarrega de fazer as contas de acordo com o primeiro operador (se houver) que estiver depois do primeiro número,
     * jogando para a cadeia de operadores adequada.
     * @param posicaoInicial Posição do começo do primeiro número a ser tratado.
     * @param posicaoFinal Posição do fim do primeiro número a ser tratado (inclusive).
     * @param expressao Expressão matemática.
     * @return Resultado das contas.
     */
    private double resolverNumero(int posicaoInicial, int posicaoFinal, String expressao) throws Error {
        if (expressao.charAt(0) == '+' || expressao.charAt(0) == '-'){//trata + +n, + -n, - +n, e - -n
            if (expressao.charAt(0) == '+') {
                expressao = expressao.substring(1);
                posicaoFinal--;
            }
            else if (expressao.charAt(1) == '+') {
                expressao = "-" + expressao.substring(2);
                posicaoFinal--;
            }
            else if (expressao.charAt(1) == '-') {
                expressao = expressao.substring(2);
                posicaoFinal -= 2;
            }
        }
        double resultado = revelarNumero(posicaoInicial, posicaoFinal, expressao); //resultado será o numero caso só haja ele

        //caso depois do numero não acabou tudo nem o parentesis fechou:
        if ( ! (posicaoFinal+1 >= expressao.length() || expressao.charAt(posicaoFinal+1) == ')')){
            char operador = expressao.charAt(posicaoFinal+1);
            if (!OPERADORES.contains(operador)) throw new Error("Erro no operador");
            
            if (operador == '^') return resolverCadeiaDeExpoentes(posicaoInicial, posicaoFinal+1, expressao);
            else if (operador == '*' || operador == '/' || operador == '%') return resolverCadeiaDeMultiDivEMod(posicaoInicial, posicaoFinal+1, expressao);
            else if (operador == '+' || operador == '-') return resolverCadeiaDeMaisEMenos(posicaoInicial, posicaoFinal+1, expressao);
        }

        return resultado;
    }

    /**
     * Revela o valor de um número imerso em uma cadeia de caracteres.
     * @param posicaoInicialDoNumero Posição do começo do número.
     * @param posicaoFinalDoNumero Posição do fim do número (inclusive).
     * @param expressao Expressão matemática.
     * @return Valor do número.
     */
    private double revelarNumero(int posicaoInicialDoNumero, int posicaoFinalDoNumero, String expressao){//cuidado com posicao final
        return Double.valueOf(expressao.substring(posicaoInicialDoNumero, posicaoFinalDoNumero+1));
    }

    /**
     * Resolve uma cadeia de expoentes: x^(...)^y^z^...<br>
     * Caso após esta cadeia haja outros operadores, recorre aos métodos das outras cadeias.
     * @param posicaoInicialDaCadeia Posição de início de cadeia (primeiro índice do primeiro número).
     * @param posicaoDoPrimeiroOperador Posição do primeiro '^'.
     * @param expressao Expressão matemática.
     * @return Resultado.
     */
    private double resolverCadeiaDeExpoentes(int posicaoInicialDaCadeia, int posicaoDoPrimeiroOperador, String expressao) throws Error {
        //cuidado com "-x^n" (vs (-x)^n)
        if (expressao.charAt(posicaoInicialDaCadeia) == '-' && (posicaoInicialDaCadeia == 0 || expressao.charAt(posicaoInicialDaCadeia - 1) != '^')){
            return -1 * resolverCadeiaDeExpoentes(posicaoInicialDaCadeia + 1, posicaoDoPrimeiroOperador, expressao);
        }

        double resultado=0;
        //Primeiro operador naturalmente é ^
        Character segundoOperador = '0'; //pode nao existir //acharOSegundoOperadorAdiante(posicaoDoPrimeiroOperador, expressao);
        //posicaoDoPrimeiroOperador já é parâmetro
        Integer posicaoDoSegundoOperador = -1;
        Double primeiroTermo = 0d;
        Double segundoTermo = 0d;
        
        char primeiroCharDoPrimeiroTermo = expressao.charAt(posicaoInicialDaCadeia);
        //ver se o primeiro termo é numero ou parentesis, e obtê-lo
        if (primeiroCharDoPrimeiroTermo == '+' || primeiroCharDoPrimeiroTermo == '-' || NUMEROS.contains(primeiroCharDoPrimeiroTermo)){//caso numero
            primeiroTermo = revelarNumero(posicaoInicialDaCadeia, posicaoDoPrimeiroOperador-1, expressao);
        } else if (primeiroCharDoPrimeiroTermo == '('){//caso parentesis
            String conteudoDoParentesis = expressao.substring(posicaoInicialDaCadeia+1, posicaoDoPrimeiroOperador-1);
            primeiroTermo = resolverExpressao(conteudoDoParentesis);
        }

        char primeiroCharDoSegundoTermo = expressao.charAt(posicaoDoPrimeiroOperador+1);
        //ver se o segundo termo é numero ou parentesis e obter o primeiro termo e o segundo operador (pode não existir, '0')
        if (primeiroCharDoSegundoTermo == '+' || primeiroCharDoSegundoTermo == '-' || NUMEROS.contains(primeiroCharDoSegundoTermo)){
            posicaoDoSegundoOperador = acharPosicaoFinalDoNumero(posicaoDoPrimeiroOperador+1, expressao) + 1;
            if (posicaoDoSegundoOperador < expressao.length()) segundoOperador = expressao.charAt(posicaoDoSegundoOperador);

            segundoTermo = revelarNumero(posicaoDoPrimeiroOperador+1, posicaoDoSegundoOperador-1, expressao);
        } else if (primeiroCharDoSegundoTermo == '('){
            posicaoDoSegundoOperador = acharPosicaoFinalDosParentesis(posicaoDoPrimeiroOperador+1, expressao) + 1;
            if (posicaoDoSegundoOperador < expressao.length()) segundoOperador = expressao.charAt(posicaoDoSegundoOperador);

            String conteudoDoParentesis = expressao.substring(posicaoDoPrimeiroOperador+2, posicaoDoSegundoOperador-1);
            segundoTermo = resolverExpressao(conteudoDoParentesis);
        }

        if (segundoOperador != '^'){//se o segundo operador nao for ^ (ou se nem mesmo houver e for '(' ou '0' (nulo), por ter acabado a expressao), acaba a cadeia
            //resultado = primeiro^segundo;
            resultado = Math.pow(primeiroTermo, segundoTermo);
            //no final ver o que há no "segundo operador", que pode ser um operador diferente, um parentesis ou até '0' (nulo), e jogar para a cadeia adequada se for o caso
            if (segundoOperador == '0' || segundoOperador == ')') return resultado; //caso depois acabe a expressao
            else {//caso ainda haja contas a fazer
                String novaExpressao = String.valueOf(resultado) + expressao.substring(posicaoDoSegundoOperador);
                int posicaoDoNovoPrimeiroOperador = String.valueOf(resultado).length();
                //o segundo operador ainda é o mesmo antigo
                if (segundoOperador == '*' || segundoOperador == '/' || segundoOperador == '%') resultado = resolverCadeiaDeMultiDivEMod(0, posicaoDoNovoPrimeiroOperador, novaExpressao);
                else if (segundoOperador == '+' || segundoOperador == '-') resultado = resolverCadeiaDeMaisEMenos(0, posicaoDoNovoPrimeiroOperador, novaExpressao);
            }
        } else {//se o segundo operador ainda for outro ^, a cadeia continua a recursividade
            resultado = Math.pow(primeiroTermo, resolverCadeiaDeExpoentes(posicaoDoPrimeiroOperador+1, posicaoDoSegundoOperador, expressao));

            int posicaoDoOperadorDepoisDaCadeiaDeExpoentes = acharPosicaoDoOperadorDepoisDaCadeiaDeExpoentes(posicaoDoPrimeiroOperador, expressao);
            char operadorDepoisDaCadeiaDeExpoentes = '0';

            if (posicaoDoOperadorDepoisDaCadeiaDeExpoentes < expressao.length()) operadorDepoisDaCadeiaDeExpoentes = expressao.charAt(posicaoDoOperadorDepoisDaCadeiaDeExpoentes);
            else operadorDepoisDaCadeiaDeExpoentes = '0';

            if (OPERADORES.contains(operadorDepoisDaCadeiaDeExpoentes)){//operadorDepoisDaCadeia pode ser '0' (nulo) ou ')'
                String novaExpressao = String.valueOf(resultado) + expressao.substring(posicaoDoOperadorDepoisDaCadeiaDeExpoentes);
                int posicaoDoNovoPrimeiroOperador = String.valueOf(resultado).length();

                if (operadorDepoisDaCadeiaDeExpoentes == '*' || operadorDepoisDaCadeiaDeExpoentes == '/' || operadorDepoisDaCadeiaDeExpoentes == '%'){
                    resultado = resolverCadeiaDeMultiDivEMod(0, posicaoDoNovoPrimeiroOperador, novaExpressao);
                } else if (operadorDepoisDaCadeiaDeExpoentes == '+' || operadorDepoisDaCadeiaDeExpoentes == '-'){
                    resultado = resolverCadeiaDeMaisEMenos(0, posicaoDoNovoPrimeiroOperador, novaExpressao);
                }
            }
        }

        return resultado;
    }

    /**
     * Acha a posição do primeiro operador (ou ')' ou '0' nulo) diferente de '^'. Método feito para auxiliar a resolução da cadeia de expoentes.
     * @param posicaoDoPrimeiroOperador Posição do primeiro operador '^'.
     * @param expressao Expressão matemática.
     * @return Posição do primeiro "operador" diferente de '^'.
     */
    private int acharPosicaoDoOperadorDepoisDaCadeiaDeExpoentes(int posicaoDoPrimeiroOperador, String expressao){
        char operadorDepoisDaCadeiaDeExpoentes = '0';//pode ser '0' (nulo), ')', ou um operador que não seja '^'
        int posicaoDoOperadorDepoisDaCadeiaDeExpoentes = posicaoDoPrimeiroOperador;

        do{
            posicaoDoOperadorDepoisDaCadeiaDeExpoentes = acharPosicaoDoSegundoOperadorAdiante(posicaoDoOperadorDepoisDaCadeiaDeExpoentes, expressao);
            if (posicaoDoOperadorDepoisDaCadeiaDeExpoentes < expressao.length()) operadorDepoisDaCadeiaDeExpoentes = expressao.charAt(posicaoDoOperadorDepoisDaCadeiaDeExpoentes);
            else operadorDepoisDaCadeiaDeExpoentes = '0';

        } while(operadorDepoisDaCadeiaDeExpoentes == '^');

        return posicaoDoOperadorDepoisDaCadeiaDeExpoentes;
    }

    /**
     * Resolve uma cadeia de '*', '/' e '%': x*(...)/y%...<br>
     * Caso após esta cadeia haja outros operadores, recorre aos métodos das outras cadeias.
     * @param posicaoInicialDaCadeia Posição de início de cadeia (primeiro índice do primeiro número).
     * @param posicaoDoPrimeiroOperador Posição do primeiro operador.
     * @param expressao Expressão matemática.
     * @return Resultado.
     */
    private double resolverCadeiaDeMultiDivEMod(int posicaoInicialDaCadeia, int posicaoDoPrimeiroOperador, String expressao) throws Error {
        Double resultado = 0d; //o proprio resultado vira o primeiro termo na operacao seguinte
        Character primeiroOperador = expressao.charAt(posicaoDoPrimeiroOperador);
        Character segundoOperador = '0'; //acharOSegundoOperadorAdiante(posicaoDoPrimeiroOperador, expressao); //pode nao existir
        //posicaoDoPrimeiroOperador já é parâmetro
        Integer posicaoDoSegundoOperador = -1;
        Double primeiroTermo = 0d;
        Double segundoTermo = 0d;

        char primeiroCharDoPrimeiroTermo = expressao.charAt(posicaoInicialDaCadeia);
        char primeiroCharDoSegundoTermo = expressao.charAt(posicaoDoPrimeiroOperador+1);

        do {//percorre a expressao resolvendo
            //caso seja o primeiro round, ver se o primeiro termo é numero ou parentesis
            if (resultado == 0d && primeiroTermo == 0d && posicaoDoSegundoOperador == -1) {
                if (primeiroCharDoPrimeiroTermo == '+' || primeiroCharDoPrimeiroTermo == '-' || NUMEROS.contains(primeiroCharDoPrimeiroTermo)){//caso numero
                    primeiroTermo = revelarNumero(posicaoInicialDaCadeia, posicaoDoPrimeiroOperador-1, expressao);
                } else if (primeiroCharDoPrimeiroTermo == '('){//caso parentesis
                    primeiroTermo = resolverExpressao(expressao.substring(posicaoInicialDaCadeia+1, posicaoDoPrimeiroOperador-1));
                }
                resultado = primeiroTermo;//caso seja o primeiro round, o resultado recebe o primeiro termo
            }

            //ver se o segundo termo é numero ou parentesis
            if (primeiroCharDoSegundoTermo == '+' || primeiroCharDoSegundoTermo == '-' || NUMEROS.contains(primeiroCharDoSegundoTermo)){
                posicaoDoSegundoOperador = acharPosicaoFinalDoNumero(posicaoDoPrimeiroOperador+1, expressao) + 1;
                if (posicaoDoSegundoOperador < expressao.length()) segundoOperador = expressao.charAt(posicaoDoSegundoOperador);

                segundoTermo = revelarNumero(posicaoDoPrimeiroOperador+1, posicaoDoSegundoOperador-1, expressao);
            } else if (primeiroCharDoSegundoTermo == '('){
                posicaoDoSegundoOperador = acharPosicaoFinalDosParentesis(posicaoDoPrimeiroOperador+1, expressao) + 1;
                if (posicaoDoSegundoOperador < expressao.length()) segundoOperador = expressao.charAt(posicaoDoSegundoOperador);

                String conteudoDoParentesis = expressao.substring(posicaoDoPrimeiroOperador+2, posicaoDoSegundoOperador-1);
                segundoTermo = resolverExpressao(conteudoDoParentesis);
            }

            //resolver as contas
            if (segundoOperador == '0' || segundoOperador == ')') return operar(resultado, primeiroOperador, segundoTermo);
            else if (segundoOperador == '^') {
                int posicaoDoOperadorDepoisDaCadeiaDeExpoentes = acharPosicaoDoOperadorDepoisDaCadeiaDeExpoentes(posicaoDoPrimeiroOperador, expressao);
                char operadorDepoisDaCadeiaDeExpoentes = '0';

                if (posicaoDoOperadorDepoisDaCadeiaDeExpoentes < expressao.length()) operadorDepoisDaCadeiaDeExpoentes = expressao.charAt(posicaoDoOperadorDepoisDaCadeiaDeExpoentes);

                if (OPERADORES.contains(operadorDepoisDaCadeiaDeExpoentes)){//operadorDepoisDaCadeia pode ser '0' (nulo) ou ')'
                    String expressaoAteOFinalDaCadeiaDeExpoentes = expressao.substring(0, posicaoDoOperadorDepoisDaCadeiaDeExpoentes); //do começo da expressao toda até o fim dos expoentes
                    segundoTermo = resolverCadeiaDeExpoentes(posicaoDoPrimeiroOperador+1, posicaoDoSegundoOperador, expressaoAteOFinalDaCadeiaDeExpoentes);
                    primeiroCharDoSegundoTermo = String.valueOf(segundoTermo).charAt(0);
                    segundoOperador = operadorDepoisDaCadeiaDeExpoentes;
                    posicaoDoSegundoOperador = posicaoDoOperadorDepoisDaCadeiaDeExpoentes;
                    //resultado = operar(resultado, primeiroOperador, segundoTermo);
                    //expressao = resultado + expressaoAteOFinalDaCadeiaDeExpoentes.substring(posicaoDoSegundoOperador);
                } else return operar(resultado, primeiroOperador, resolverCadeiaDeExpoentes(posicaoDoPrimeiroOperador + 1, posicaoDoSegundoOperador, expressao));
            }
            //manter esta separação porque o segundo operador pode mudar acima
            if (segundoOperador == '*' || segundoOperador == '/' || segundoOperador == '%') resultado = operar(resultado, primeiroOperador, segundoTermo);
            else if (segundoOperador == '+' || segundoOperador == '-') {
                resultado = operar(resultado, primeiroOperador, segundoTermo);
                String novaExpressao = String.valueOf(resultado) + expressao.substring(posicaoDoSegundoOperador);
                int novaPosicaoDoPrimeiroOperador = String.valueOf(resultado).length();
                return resolverCadeiaDeMaisEMenos(0, novaPosicaoDoPrimeiroOperador, novaExpressao);
            }

            if (posicaoDoSegundoOperador >= expressao.length()) break;            
            //atualiza todos para o proximo round
            posicaoInicialDaCadeia = posicaoDoPrimeiroOperador+1;
            primeiroOperador = segundoOperador;
            posicaoDoPrimeiroOperador = posicaoDoSegundoOperador;
            primeiroTermo = segundoTermo;
            primeiroCharDoPrimeiroTermo = String.valueOf(primeiroTermo).charAt(0);
            //segundoOperador = acharPosicaoDoSegundoOperadorAdiante(posicaoDoSegundoOperador, expressao); //o segundo operador antigo é referência para achar o novo
            primeiroCharDoSegundoTermo = expressao.charAt(posicaoDoPrimeiroOperador+1);
        } while (primeiroOperador == '*' || primeiroOperador == '/' || primeiroOperador == '%');

        return resultado;
    }

     /**
     * Resolve uma cadeia de '+' e '-': x+(...)-y+...<br>
     * Caso após esta cadeia haja outros operadores, recorre aos métodos das outras cadeias.
     * @param posicaoInicialDaCadeia Posição de início de cadeia (primeiro índice do primeiro número).
     * @param posicaoDoPrimeiroOperador Posição do primeiro operador '+' ou '-'.
     * @param expressao Expressão matemática.
     * @return Resultado.
     */
    private double resolverCadeiaDeMaisEMenos(int posicaoInicialDaCadeia, int posicaoDoPrimeiroOperador, String expressao) throws Error {
        Double resultado = 0d;//o proprio resultado vira o primeiro termo na operacao seguinte
        Character primeiroOperador = expressao.charAt(posicaoDoPrimeiroOperador);
        Character segundoOperador = '0'; //acharOSegundoOperadorAdiante(posicaoDoPrimeiroOperador, expressao); //pode nao existir
        //posicaoDoPrimeiroOperador já é parâmetro
        Integer posicaoDoSegundoOperador = -1;
        Double primeiroTermo = 0d;
        Double segundoTermo = 0d;

        char primeiroCharDoPrimeiroTermo = expressao.charAt(posicaoInicialDaCadeia);
        char primeiroCharDoSegundoTermo = expressao.charAt(posicaoDoPrimeiroOperador+1);

        do {//percorre a expressao resolvendo
            //caso seja o primeiro round, ver se o primeiro termo é numero ou parentesis
            if (resultado == 0d && primeiroTermo == 0d && posicaoDoSegundoOperador == -1) {
                if (primeiroCharDoPrimeiroTermo == '+' || primeiroCharDoPrimeiroTermo == '-' || NUMEROS.contains(primeiroCharDoPrimeiroTermo)){//caso numero
                    primeiroTermo = revelarNumero(posicaoInicialDaCadeia, posicaoDoPrimeiroOperador-1, expressao);
                } else if (primeiroCharDoPrimeiroTermo == '('){//caso parentesis
                    primeiroTermo = resolverExpressao(expressao.substring(posicaoInicialDaCadeia+1, posicaoDoPrimeiroOperador-1));
                }
                resultado = primeiroTermo;//caso seja o primeiro round, o resultado recebe o primeiro termo
            }

            //ver se o segundo termo é numero ou parentesis
            if (primeiroCharDoSegundoTermo == '+' || primeiroCharDoSegundoTermo == '-' || NUMEROS.contains(primeiroCharDoSegundoTermo)){
                posicaoDoSegundoOperador = acharPosicaoFinalDoNumero(posicaoDoPrimeiroOperador+1, expressao) + 1;
                if (posicaoDoSegundoOperador < expressao.length()) segundoOperador = expressao.charAt(posicaoDoSegundoOperador);

                segundoTermo = revelarNumero(posicaoDoPrimeiroOperador+1, posicaoDoSegundoOperador-1, expressao);
            } else if (primeiroCharDoSegundoTermo == '('){
                posicaoDoSegundoOperador = acharPosicaoFinalDosParentesis(posicaoDoPrimeiroOperador+1, expressao) + 1;
                if (posicaoDoSegundoOperador < expressao.length()) segundoOperador = expressao.charAt(posicaoDoSegundoOperador);

                String conteudoDoParentesis = expressao.substring(posicaoDoPrimeiroOperador+2, posicaoDoSegundoOperador-1);
                segundoTermo = resolverExpressao(conteudoDoParentesis);
            }

            //resolver as contas
            if (segundoOperador == '0' || segundoOperador == ')') return operar(resultado, primeiroOperador, segundoTermo);
            else if (segundoOperador == '^') {
                //abaixo é o cuidado com o '-' ex.: a-b*c+d, a-+b^c-d, a--b*c+d, etc. O '-' não pode influenciar todo o resto da conta após ele
                if (primeiroOperador == '+') return resultado + resolverCadeiaDeExpoentes(posicaoDoPrimeiroOperador + 1, posicaoDoSegundoOperador, expressao);
                else if (primeiroOperador == '-' && expressao.charAt(posicaoDoPrimeiroOperador+1) == '-') {
                    return resultado + resolverCadeiaDeExpoentes(posicaoDoPrimeiroOperador + 2, posicaoDoSegundoOperador, expressao);
                } else if (primeiroOperador == '-' && expressao.charAt(posicaoDoPrimeiroOperador+1) == '+') {
                    String novaExpressao = "-"+expressao.substring(posicaoDoPrimeiroOperador+2);
                    int novaPosicaoDoPrimeiroOperador = posicaoDoSegundoOperador-posicaoDoPrimeiroOperador-1;//acharPosicaoDoSegundoOperadorAdiante(0, novaExpressao);
                    return resultado + resolverCadeiaDeExpoentes(0, novaPosicaoDoPrimeiroOperador, novaExpressao);
                } else {
                    return resultado + resolverCadeiaDeExpoentes(posicaoDoPrimeiroOperador, posicaoDoSegundoOperador, expressao);
                }
            }
            else if (segundoOperador == '*' || segundoOperador == '/' || segundoOperador == '%') {
                //abaixo é o cuidado com o '-' ex.: a-b*c+d, a-+b^c-d, a--b*c+d, etc.
                if (primeiroOperador == '+') return resultado + resolverCadeiaDeMultiDivEMod(posicaoDoPrimeiroOperador + 1, posicaoDoSegundoOperador, expressao);
                else if (primeiroOperador == '-' && expressao.charAt(posicaoDoPrimeiroOperador+1) == '-') {
                    return resultado + resolverCadeiaDeMultiDivEMod(posicaoDoPrimeiroOperador + 2, posicaoDoSegundoOperador, expressao);
                } else if (primeiroOperador == '-' && expressao.charAt(posicaoDoPrimeiroOperador+1) == '+') {
                    String novaExpressao = "-"+expressao.substring(posicaoDoPrimeiroOperador+2);
                    int novaPosicaoDoPrimeiroOperador = posicaoDoSegundoOperador-posicaoDoPrimeiroOperador-1;//acharPosicaoDoSegundoOperadorAdiante(0, novaExpressao);
                    return resultado + resolverCadeiaDeMultiDivEMod(0, novaPosicaoDoPrimeiroOperador, novaExpressao);
                } else {
                    return resultado + resolverCadeiaDeMultiDivEMod(posicaoDoPrimeiroOperador, posicaoDoSegundoOperador, expressao);
                }
                //return operar(resultado, primeiroOperador, resolverCadeiaDeMultiDivEMod(posicaoDoPrimeiroOperador + 1, posicaoDoSegundoOperador, expressao));
            }
            else if (segundoOperador == '+' || segundoOperador == '-') resultado = operar(resultado, primeiroOperador, segundoTermo);

            if (posicaoDoSegundoOperador >= expressao.length()) break;
            //atualiza todos para o proximo round
            posicaoInicialDaCadeia = posicaoDoPrimeiroOperador+1;
            primeiroOperador = segundoOperador;
            posicaoDoPrimeiroOperador = posicaoDoSegundoOperador;
            primeiroTermo = segundoTermo;
            primeiroCharDoPrimeiroTermo = String.valueOf(primeiroTermo).charAt(0);
            //segundoOperador = acharPosicaoDoSegundoOperadorAdiante(posicaoDoSegundoOperador, expressao); //o segundo operador antigo é referência para achar o novo
            primeiroCharDoSegundoTermo = expressao.charAt(posicaoDoPrimeiroOperador+1);
        } while (primeiroOperador == '+' || primeiroOperador == '-');

        return resultado;
    }

    /**
     * Pula o número/parêntesis e diz qual é a posição do operador adiante ex.: ...+(...)*...
     * @param posicaoDoPrimeiroOperador Posição do primeiro operador.
     * @param expressao Expressão matemática.
     * @return Posição do segundo operador.
     */
    private int acharPosicaoDoSegundoOperadorAdiante(int posicaoDoPrimeiroOperador, String expressao){
        //pode ser que nem exista o segundo operador ou seja ')'
        int posicaoDoSegundoOperador = -1;
        
        char charAposPrimeiroOperador = expressao.charAt(posicaoDoPrimeiroOperador+1);
        if (charAposPrimeiroOperador == '+' || charAposPrimeiroOperador == '-' || NUMEROS.contains(charAposPrimeiroOperador)){
            posicaoDoSegundoOperador = acharPosicaoFinalDoNumero(posicaoDoPrimeiroOperador+1, expressao) + 1;
            //if (posicaoDoSegundoOperador < expressao.length()) segundoOperador = expressao.charAt(posicaoDoSegundoOperador);
        } else if (charAposPrimeiroOperador == '('){
            posicaoDoSegundoOperador = acharPosicaoFinalDosParentesis(posicaoDoPrimeiroOperador+1, expressao) + 1;
            //if (posicaoDoSegundoOperador < expressao.length()) segundoOperador = expressao.charAt(posicaoDoSegundoOperador);
        }

        return posicaoDoSegundoOperador;
    }

    /**
     * Opera dois números.
     * @param n1 Primeiro número.
     * @param operador Operador ('^', '*', '/', '%', '+' ou '-').
     * @param n2 Segundo número.
     * @return Resultado.
     */
    private double operar(double n1, char operador, double n2) throws Error {
        double resultado=0;
        switch (operador){
            case '^':
                resultado = Math.pow(n1, n2);
                break;
            case '*':
                resultado = n1*n2;
                break;
            case '/':
                resultado = n1/n2;
                break;
            case '%':
                resultado = n1%n2;
                break;                
            case '+':
                resultado = n1+n2;
                break;
            case '-':
                resultado = n1-n2;
                break;
            default:
                throw new Error ("Erro no operador");
        }

        return resultado;
    }

    /**
     * @param nomeDaVariavel O nome da variável cujo valor se quer obter.
     * @return O valor (número ou expressão matemática) da variável.
     * @throws Error Erro personalizado se a variável não existir.
     */
    public String obterValorDaVariavel(String nomeDaVariavel)throws Error {
        if (!nomesDasVariaveis.contains(nomeDaVariavel)) throw new Error("Não existe variável com esse nome");
        return valoresDasVariaveis.get(nomesDasVariaveis.indexOf(nomeDaVariavel));
    }

    /**
     * @return Uma lista com os nomes das variáveis.*/
    public ArrayList<String> obterNomesDasVariaveis(){
        return nomesDasVariaveis;
    }

    /**
     * @return Uma lista com os valores das variáveis.*/
    public ArrayList<String> obterValoresDasVariaveis(){
        return valoresDasVariaveis;
    }

    /**
     * @return Um vetor cuja posição: <br>[0] é a lista dos nomes das variáveis; e<br>[1] é a lista dos valores das variáveis.*/
    public ArrayList[] obterVariaveis(){
        //retorna um vetor com a lista dos nomes e lista das variaveis
        ArrayList[] vetorComAListaDosNomesEAListaDosValores = new ArrayList[2];

        vetorComAListaDosNomesEAListaDosValores[0] = nomesDasVariaveis;
        vetorComAListaDosNomesEAListaDosValores[1] = valoresDasVariaveis;

        return vetorComAListaDosNomesEAListaDosValores;
    }

    /**
     * @param nomeAntigo Nome da variável a ter seu nome mudado.
     * @param nomeNovo Novo nome da variável.
     * @throws Error Erro personalizado se houver nome vazio ou inválido.
     */
    public void mudarNomeDeVariavel(String nomeAntigo, String nomeNovo) throws Error {
        if (nomeAntigo.length() == 0 || nomeNovo.length() == 0) throw new Error("Campo vazio");

        char primeiroChar = nomeNovo.charAt(0);

        if (!nomesDasVariaveis.contains(nomeAntigo)) throw new Error("Não existe variável com esse nome");
        else if (nomesDasVariaveis.contains(nomeNovo)) throw new Error("Já existe variável com esse nome");
        else if ((primeiroChar != '_' && primeiroChar != '$' && !LETRAS.contains(primeiroChar))) throw new Error("Primeiro char do nome da variável inválido");
        else {
            for (char c : nomeNovo.toCharArray()) {
                if (!CARACTERES_ACEITOS_EM_VARIAVEIS.contains(c)) throw new Error("Nome da variável inválido");
            }
        }
        //caso tudo ok:
        nomesDasVariaveis.set(nomesDasVariaveis.indexOf(nomeAntigo), nomeNovo);
    }

    /**Muda o valor de uma variável já criada para um novo número.
     * <p>Para mudar o valor para uma nova expressão matemática, use o método homônimo.</p>
     * @see #mudarValorDeVariavel(String, String)
     * @param nomeDaVariavel Nome da variável a ter seu valor mudado.
     * @param valorNovo Número que será o novo valor da variável.
     * @throws Error Erro personalizado se o nome vier vazio ou a variável não existir.
     */
    public void mudarValorDeVariavel(String nomeDaVariavel, double valorNovo) throws Error {
        if (nomeDaVariavel.length() == 0) throw new Error("Campo vazio");
        else if (!nomesDasVariaveis.contains(nomeDaVariavel)) throw new Error("Não existe variável com esse nome");
        else {
        //caso tudo ok:
        valoresDasVariaveis.set(nomesDasVariaveis.indexOf(nomeDaVariavel), String.valueOf(valorNovo));
        }
    }

    /**Muda o valor de uma variável já criada para uma nova expressão matemática.
     * <p>Cuidado, pois pode haver loop se os valores das variáveis ficarem referenciando outras variáveis indefinidamente.</p>
     * <p>Para mudar o valor para um novo número, use o método homônimo.</p>
     * @see #mudarValorDeVariavel(String, double)
     * @param nomeDaVariavel Nome da variável a ter seu valor mudado.
     * @param expressaoNova Expressão matemática que será o novo valor da variável.
     * @throws Error Erro personalizado se o nome vier vazio ou a variável não existir.
     */
    public void mudarValorDeVariavel(String nomeDaVariavel, String expressaoNova) throws Error {
        if (nomeDaVariavel.length() == 0 || expressaoNova.length() == 0) throw new Error("Campo vazio");
        else if (!nomesDasVariaveis.contains(nomeDaVariavel)) throw new Error("Não existe variável com esse nome");
        else {
            preValidarExpressao(expressaoNova);
        }
        //caso tudo ok:
        valoresDasVariaveis.set(nomesDasVariaveis.indexOf(nomeDaVariavel), expressaoNova);
    }

    /**Cria nova variável e declara seu valor como um número.
     * <p>Para declarar o valor como uma expressão matemática, use o método homônimo.</p>
     * @see #criarVariavel(String, String)
     * @param nomeDaVariavel Nome da nova variável a ser criada.
     * @param valorDaVariavel Número que será o valor da nova variável.
     * @throws Error Erro personalizado se o nome for inválido ou a variável já existir.
     */
    public void criarVariavel(String nomeDaVariavel, double valorDaVariavel) throws Error {
        if (nomeDaVariavel.length() == 0) throw new Error("Campo vazio");

        char primeiroChar = nomeDaVariavel.charAt(0);
        //if (nomeDaVariavel.equals("")) throw new Error("Já existe variável com esse nome");

        if (nomesDasVariaveis.contains(nomeDaVariavel)) throw new Error("Já existe variável com esse nome");
        else if ((primeiroChar != '_' && primeiroChar != '$' && !LETRAS.contains(primeiroChar))) throw new Error("Primeiro char do nome da variável inválido");
        else {
            for (char c : nomeDaVariavel.toCharArray()) {
                if (!CARACTERES_ACEITOS_EM_VARIAVEIS.contains(c)) throw new Error("Nome da variável inválido");
            }
        }
        //caso tudo ok:
        nomesDasVariaveis.add(nomeDaVariavel);
        valoresDasVariaveis.add(String.valueOf(valorDaVariavel));
    }

    /**Cria nova variável e declara seu valor como uma expressão matemática (a qual pode aceitar até outras variáveis).
     * <p>Cuidado, pois pode haver loop se os valores das variáveis ficarem referenciando outras variáveis indefinidamente.</p>
     * <p>Para declarar o valor como um número, use o método homônimo.</p>
     * @see #criarVariavel(String, double)
     * @param nomeDaVariavel Nome da nova variável a ser criada.
     * @param expressaoDaVariavel Expressão matemática que será o valor da nova variável.
     * @throws Error Erro personalizado se o nome for inválido ou a variável já existir.
     */
    public void criarVariavel(String nomeDaVariavel, String expressaoDaVariavel) throws Error {
        if (nomeDaVariavel.length() == 0 || expressaoDaVariavel.length() == 0) throw new Error("Campo vazio");
        char primeiroChar = nomeDaVariavel.charAt(0);

        if (nomesDasVariaveis.contains(nomeDaVariavel)) throw new Error("Já existe variável com esse nome");
        else if ((primeiroChar != '_' && primeiroChar != '$' && !LETRAS.contains(primeiroChar))) throw new Error("Primeiro char do nome da variável inválido");
        else {
            for (char c : nomeDaVariavel.toCharArray()) {
                if (!CARACTERES_ACEITOS_EM_VARIAVEIS.contains(c)) throw new Error("Nome da variável inválido");
            }

            preValidarExpressao(expressaoDaVariavel);
        }
        //caso tudo ok:
        nomesDasVariaveis.add(nomeDaVariavel);
        valoresDasVariaveis.add(expressaoDaVariavel);
    }

    /**
     * @param nomeDaVariavel Nome da variável a ser apagada.
     * @throws Error "Não existe variável com esse nome".
     */
    public void apagarVariavel(String nomeDaVariavel) throws Error {
        if (!nomesDasVariaveis.contains(nomeDaVariavel)) throw new Error("Não existe variável com esse nome");
        else { //caso tudo ok
            valoresDasVariaveis.remove(nomesDasVariaveis.indexOf(nomeDaVariavel));
            nomesDasVariaveis.remove(nomeDaVariavel);
        }
    }
}
