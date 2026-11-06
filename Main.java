import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

/**
 * Compara Bubble Sort, Insertion Sort e Quick Sort nos 9 arquivos CSV do trabalho.
 *
 * Compilar: javac Main.java
 * Executar: java Main [pasta_dos_csv]   (padrao: pasta "conjuntosDeDados")
 */
public class Main {
    static final String[] TIPOS = {"aleatorio", "crescente", "decrescente"};
    static final int[] TAMANHOS = {100, 1000, 10000};
    static final String[] ALGORITMOS = {"Bubble Sort", "Insertion Sort", "Quick Sort"};
    static final int REPETICOES = 5;

    public static void main(String[] args) throws IOException {
        String pasta = args.length > 0 ? args[0] : "conjuntosDeDados";

        System.out.println("| Conjunto | Bubble Sort (ms) | Insertion Sort (ms) | Quick Sort (ms) |");
        System.out.println("|---|---:|---:|---:|");

        for (String tipo : TIPOS) {
            for (int tamanho : TAMANHOS) {
                String nome = tipo + "_" + tamanho + ".csv";
                int[] dados = lerCsv(Path.of(pasta, nome));

                StringBuilder linha = new StringBuilder("| " + nome + " |");
                for (int algoritmo = 0; algoritmo < ALGORITMOS.length; algoritmo++) {
                    double ms = mediaEmMs(algoritmo, dados);
                    linha.append(String.format(Locale.of("pt", "BR"), " %.3f |", ms));
                }
                System.out.println(linha);
            }
        }
    }

    /** Le todos os numeros inteiros do arquivo, ignorando cabecalho e separadores. */
    static int[] lerCsv(Path arquivo) throws IOException {
        return Arrays.stream(Files.readString(arquivo).split("[,;\\s]+"))
                .filter(valor -> valor.matches("-?\\d+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    /** Executa o algoritmo REPETICOES vezes, sempre sobre uma copia dos dados originais. */
    static double mediaEmMs(int algoritmo, int[] dados) {
        int[] esperado = dados.clone();
        Arrays.sort(esperado);

        long total = 0;
        for (int i = 0; i < REPETICOES; i++) {
            int[] copia = dados.clone();

            long inicio = System.nanoTime();
            ordenar(algoritmo, copia);
            total += System.nanoTime() - inicio;

            if (!Arrays.equals(copia, esperado)) {
                throw new IllegalStateException(ALGORITMOS[algoritmo] + " nao ordenou corretamente");
            }
        }
        return total / (double) REPETICOES / 1_000_000.0;
    }

    static void ordenar(int algoritmo, int[] v) {
        switch (algoritmo) {
            case 0 -> bubbleSort(v);
            case 1 -> insertionSort(v);
            default -> quickSort(v, 0, v.length - 1);
        }
    }

    static void bubbleSort(int[] v) {
        for (int i = 0; i < v.length - 1; i++) {
            boolean trocou = false;
            for (int j = 0; j < v.length - 1 - i; j++) {
                if (v[j] > v[j + 1]) {
                    trocar(v, j, j + 1);
                    trocou = true;
                }
            }
            if (!trocou) {
                return; // nenhuma troca: o vetor ja esta ordenado
            }
        }
    }

    static void insertionSort(int[] v) {
        for (int i = 1; i < v.length; i++) {
            int atual = v[i];
            int j = i - 1;
            while (j >= 0 && v[j] > atual) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = atual;
        }
    }

    /** Quick Sort usando o ultimo elemento como pivo. */
    static void quickSort(int[] v, int inicio, int fim) {
        if (inicio < fim) {
            int p = particionar(v, inicio, fim);
            quickSort(v, inicio, p - 1);
            quickSort(v, p + 1, fim);
        }
    }

    static int particionar(int[] v, int inicio, int fim) {
        int pivo = v[fim];
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (v[j] <= pivo) {
                i++;
                trocar(v, i, j);
            }
        }
        trocar(v, i + 1, fim);
        return i + 1;
    }

    static void trocar(int[] v, int a, int b) {
        int temp = v[a];
        v[a] = v[b];
        v[b] = temp;
    }
}