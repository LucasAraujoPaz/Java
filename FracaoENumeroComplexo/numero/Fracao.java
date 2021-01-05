package numero;

/**
 * n = numerador/denominador
 * @author Lucas Paz
 */
public class Fracao extends Number implements Comparable<Fracao> {

    private Long numerador = 0L;
    private Long denominador = 1L;
    
    public static final Fracao ZERO = new Fracao(0, 1);
    public static final Fracao UM = new Fracao(1, 1);;

    public Fracao(long numerador, long denominador) throws IllegalArgumentException {
        if (denominador == 0) throw new IllegalArgumentException("Denominador = 0.");

        long[] simplificado = simplificarFracao(numerador, denominador);

        this.numerador = simplificado[0];
        this.denominador = simplificado[1];
    }

    public static Fracao numeroParaFracao(Number n) throws IllegalArgumentException {
        if (n.doubleValue() > (double) Long.MAX_VALUE) throw new IllegalArgumentException("Número grande demais.");

        String classe = n.getClass().getSimpleName();
        long numerador = 0L;
        long denominador = 1L;

        if (classe.equals("Long") || classe.equals("Integer") || classe.equals("Byte") 
                || (double) (n.doubleValue() - n.longValue()) == 0d) {

            numerador = n.longValue();
            denominador = 1;
            return new Fracao(numerador, denominador);
        } else {
            return decimalParaFracao(n);
        }
    }

    private static Fracao decimalParaFracao(Number n) {
        String nToString = java.math.BigDecimal.valueOf(n.doubleValue()).toPlainString();

        if (nToString.length() > String.valueOf(Long.MAX_VALUE).length()) nToString = nToString.substring(0, String.valueOf(Long.MAX_VALUE).length() - 1);

        int indiceDoPonto = nToString.length();
        char c = '¬';
        for (int i = 0; i < nToString.length(); i++) {
            c = nToString.charAt(i);
            if (c == '.') {
                indiceDoPonto = i;
                break;
            }
        }

        if (indiceDoPonto == nToString.length()) return new Fracao(Long.valueOf(nToString), 1L);
        else if (indiceDoPonto == nToString.length()-1) return new Fracao(Long.valueOf(nToString.substring(0, indiceDoPonto)), 1L);
        else if (indiceDoPonto + 8 < nToString.length()) nToString = nToString.substring(0, indiceDoPonto + 8 + 1);

        return simplificarDizimasPeriodicas(nToString, indiceDoPonto);
    }

    private static Fracao simplificarDizimasPeriodicas(String nToString, int indiceDoPonto) {

        int qntDeCasasDecimais = nToString.length()-1 - indiceDoPonto;

        long numerador=0L;
        long denominador=1L;
        Fracao simplificada;

        String parteInteira = nToString.substring(0, indiceDoPonto);
        String parteDecimal = nToString.substring(indiceDoPonto + 1);

        int tamanhoDoAntiperiodo=0;
        int tamanhoDoPeriodo=0;

        for (tamanhoDoAntiperiodo = 0; tamanhoDoAntiperiodo <= 3; tamanhoDoAntiperiodo++) {
            String novoDecimal = parteDecimal.substring(tamanhoDoAntiperiodo);
            if (novoDecimal.length() < 3) break;

            if (novoDecimal.length() >= 3 && novoDecimal.charAt(0) == novoDecimal.charAt(1) 
                    && novoDecimal.charAt(1) == novoDecimal.charAt(2)) {
                //inteiro.a...nppp...
                tamanhoDoPeriodo = 1;
            } else if (novoDecimal.length() >= 4 && novoDecimal.substring(0, 2).equals(novoDecimal.substring(2, 4))) {
                //inteiro.a...npqpq...
                tamanhoDoPeriodo = 2;
            } else if (novoDecimal.length() >= 6 && novoDecimal.substring(0, 3).equals(novoDecimal.substring(3, 6))) {
                //inteiro.a...npqrpqr...
                tamanhoDoPeriodo = 3;
            }

            if (tamanhoDoPeriodo > 0) {
                String minuendo = parteInteira + parteDecimal.substring(0, tamanhoDoAntiperiodo + tamanhoDoPeriodo);
                String subtraendo = minuendo.substring(0, tamanhoDoAntiperiodo);
                numerador = Long.valueOf(minuendo) - Long.valueOf(subtraendo);

                Double d = Math.pow(10d, (double) tamanhoDoAntiperiodo) * ( Math.pow(10d, (double) tamanhoDoPeriodo) - 1 );
                denominador = d.longValue();

                simplificada = new Fracao(numerador, denominador);
                return simplificada;
            }
        }

        //se nada der certo:
        numerador = Double.valueOf(nToString.replaceFirst(".", "")).longValue();
        denominador = Double.valueOf(Math.pow(10, qntDeCasasDecimais)).longValue();

        simplificada = new Fracao(numerador, denominador);

        return simplificada;
    }

    public static long[] simplificarFracao(long numerador, long denominador) {
        long mdc = mdc(numerador, denominador);

        numerador = numerador/mdc;
        denominador = denominador/mdc;

        return new long[] {numerador, denominador};
    }

    public Fracao mais(Fracao parcela) {
        Fracao soma;
        long mmcDenominador = mmc(this.denominador, parcela.denominador);
        long novoNumerador1 = (mmcDenominador/this.denominador) * this.numerador;
        long novoNumerador2 = (mmcDenominador/parcela.denominador) * parcela.numerador;
        long novoNumeradorFinal = novoNumerador1 + novoNumerador2;

        soma = new Fracao(novoNumeradorFinal, mmcDenominador);

        return soma;
    }

    public Fracao menos(Fracao subtraendo) {
        subtraendo.numerador = - subtraendo.numerador;

        return this.mais(subtraendo);
    }

    public Fracao vezes(Fracao fator) {
        Fracao produto;
        long novoNumerador = this.numerador * fator.numerador;
        long novoDenominador = this.denominador * fator.denominador;
        produto = new Fracao(novoNumerador, novoDenominador);
        
        return produto;
    }

    public Fracao divididoPor(Fracao divisor) throws ArithmeticException {
        if (divisor.numerador == 0L) throw new ArithmeticException("Divisão por 0.");
        
        long numeradorInvertido = divisor.denominador;
        long denominadorInvertido = divisor.numerador;

        return this.vezes(new Fracao(numeradorInvertido, denominadorInvertido));
    }

    public Fracao elevadoA(Fracao expoente) throws IllegalArgumentException {
        Fracao base = new Fracao(this.numerador, this.denominador);
        Fracao potencia;
        double valorDoExpoente = expoente.doubleValue();

        if (valorDoExpoente < 0d) {
            base.numerador = this.denominador;
            base.denominador = this.numerador;
            expoente.numerador = Math.abs(expoente.numerador);
            expoente.denominador = Math.abs(expoente.denominador);
        } else if (valorDoExpoente == 0d) {
            return UM;
        } else if (valorDoExpoente == 1d) {
            return this;
        }

        Long novoNumerador = Double.valueOf(Math.pow(base.numerador.doubleValue(), expoente.numerador.doubleValue())).longValue();
        Long novoDenominador = Double.valueOf(Math.pow(base.denominador, expoente.numerador)).longValue();
        double raizDoNumerador = Math.pow(novoNumerador.doubleValue(), 1d/expoente.denominador.doubleValue());
        double raizDoDenominador = Math.pow(novoDenominador.doubleValue(), 1d/expoente.denominador.doubleValue());
        double raiz = raizDoNumerador/raizDoDenominador;
        //double raiz = Math.pow(novoNumerador.doubleValue()/novoDenominador.doubleValue(), 1d/expoente.denominador.doubleValue());

        potencia = numeroParaFracao(raiz);

        return potencia;
    }

    public static long mdc(long a, long b) {
        long mod = a % b;
        while (mod != 0) {
            a = b;
            b = mod;
            mod = a % b;
        }
        return b;
    }

    public static long mmc(long a, long b) {
        //mmc(a, b) = (a*b)/mdc(a, b) = a*(b/mdc(a, b) = b*(a/mdc(a, b)
        long maior = Math.max(a, b);//para evitar overflow
        long menor = Math.min(a, b);
        return menor * (maior / mdc(a, b));
    }

    @Override
    public int intValue() {
        return Long.valueOf(numerador / denominador).intValue();
    }

    @Override
    public long longValue() {
        return (numerador / denominador);
    }

    @Override
    public float floatValue() {
        return (numerador.floatValue() / denominador.floatValue());
    }

    @Override
    public double doubleValue() {
        return (numerador.doubleValue() / denominador.doubleValue());
    }

    public Long getNumerador() {
        return numerador;
    }

    public void setNumerador(Long numerador) {
        this.numerador = numerador;
    }

    public Long getDenominador() {
        return denominador;
    }

    public void setDenominador(Long denominador) throws IllegalArgumentException {
        if (denominador == 0) throw new IllegalArgumentException("Denominador = 0.");

        this.denominador = denominador;
    }

    @Override
    public int compareTo(Fracao fracao) {
        Double este = this.doubleValue();
        Double aquele = fracao.doubleValue();
        //double diferenca = this.doubleValue() - fracao.doubleValue();

        return este.compareTo(aquele);
    }
    
    @Override
    public String toString() {
        return (this.numerador + "/" + this.denominador);
    }

}
