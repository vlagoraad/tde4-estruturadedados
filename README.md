# TDE 4 - Comparação de Algoritmos de Ordenação

Trabalho desenvolvido pelas alunas Amanda e Valentina, na disciplina de Resolução de Problemas Estruturados em Computação, do 4º período do curso de Bacharelado em Engenharia de Software.

O programa lê os conjuntos de dados da pasta `conjuntosDeDados`, ordena cada um com Bubble Sort, Insertion Sort e Quick Sort (pivô no último elemento) e imprime no console o tempo médio de 5 execuções, medido com `System.nanoTime()`.

## Como executar

```
javac -encoding UTF-8 Main.java
java Main
```

Requer Java 19 ou mais recente.

## Resultados (ms)

| Conjunto | Bubble Sort | Insertion Sort | Quick Sort |
|---|---:|---:|---:|
| aleatorio_100 | 0,155 | 0,041 | 0,029 |
| aleatorio_1000 | 2,141 | 1,669 | 0,069 |
| aleatorio_10000 | 62,880 | 10,617 | 0,572 |
| crescente_100 | 0,000 | 0,000 | 0,004 |
| crescente_1000 | 0,004 | 0,001 | 0,327 |
| crescente_10000 | 0,005 | 0,010 | 31,555 |
| decrescente_100 | 0,096 | 0,002 | 0,004 |
| decrescente_1000 | 1,708 | 0,172 | 0,346 |
| decrescente_10000 | 140,847 | 14,041 | 28,283 |

## Análise

- **Aleatório:** o Quick Sort foi o mais rápido em todos os tamanhos, porque faz em média O(n log n) operações, enquanto os outros dois fazem O(n²). Com 10.000 elementos, foi cerca de 19 vezes mais rápido que o Insertion Sort e 110 vezes mais rápido que o Bubble Sort.
- **Crescente:** Bubble Sort e Insertion Sort foram praticamente instantâneos, pois percorrem o vetor uma única vez, em O(n). Para o Quick Sort, este é o pior caso, O(n²): como o pivô é sempre o maior elemento, as partições ficam totalmente desbalanceadas.
- **Decrescente:** o Insertion Sort foi o mais rápido. O Quick Sort também caiu para O(n²) e ficou em segundo. O Bubble Sort foi o mais lento, porque precisa trocar praticamente todos os pares.
- **Tamanho:** com 100 elementos, as diferenças são mínimas. Elas só ficam claras com 10.000. Nenhum algoritmo é o melhor em todos os cenários: o Quick Sort é ideal para dados desordenados, mas, com o pivô no último elemento, perde para os algoritmos simples quando os dados já estão ordenados.