Interpolador

Realiza Integração Numérica e encontra o Polinômio Interpolador: Regra do Trapézio, Polinômio interpolador de Newton,
Regra do Ponto Médio e Regra de Simpson através de Polinômios de Newton do segundo grau.

É possível ainda encontrar o valor f(x) de um x qualquer neste polinômio de Newton encontrado.

Polinômio de Newton: f[x0] + f[x1, x0]*(x - x0) + f[x2, x1, x0]*(x - x0)*(x - x1)*(x - x2) + ...
=  f[x0] + (x - x0)*{ f[x1, x0] + (x - x1)*[ f[x2, x1, x0] + ...]}

Para simplificar, nomeie-se:
f[x0] como f0
f[x1, x0] como f1
f[x2, x1, x0] como f2
f[xn, xn-1, ... , x1, x0] como fn.

Polinômio de Newton de grau 2, Ax² + Bx + C:  A = f2 , B = -f2*x1 - f2*x0 + f1 , C = f2*x1*x0 - f1*x0 + f0.

Integral do Polinômio de Newton de grau 2 é de grau 3, Ax³ + Bx² + Cx (neste caso D = 0):
A = (f2)/3 , B = (-f2*x1 - f2*x0 + f1)/2 , C = f2*x1*x0 - f1*x0 + f0 , D = (0)

A regra de Simpson aqui usará Polinômios de Newton de grau 2.
Dessa forma, os valores de x não precisam estar a uma mesma distância uns dos outros.
 
@author Lucas Paz.
