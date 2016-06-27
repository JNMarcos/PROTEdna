/**
 * 
 */
package desenvolvimento;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import console.Dados;
import console.LerArquivos;

/**
 * @author JN
 *
 */
public class AnalisarDNA {

	private Dados dados;
	private String codon;
	private static final int TRINCA = 3;
	private HashMap<String, int[]> quantidadeGC;

	public AnalisarDNA (Dados dados){
		this.dados = dados;
		this.quantidadeGC = new HashMap<String, int[]>();
	}

	public void analisar(){
		int inicioCodon;
		int fimCodon;
		int[] quantidadeGC = new int[TRINCA];

		List<String> sequenciasArquivos;

		//tem o número de códons de uma determinada sequencia
		int nCodons;

		//apenas conta o número de iterações, para não passar o tamanho da String = sequência
		int contadorVezes;

		//os nomes do arquivos são nomes de cores
		for (String cor: LerArquivos.arquivos){
			sequenciasArquivos = dados.getSequenciasDNA().get(cor);
			for (String sequencia: sequenciasArquivos){
				//seta os valores para a sequência

				contadorVezes = 1;
				inicioCodon = 0;
				fimCodon = TRINCA;

				//tem de arredondar para baixo caso o tammanho das sequências não sejam
				//necessariamente múltiplo de 3
				nCodons = sequencia.length()/TRINCA;
				
				//seta todos os valores do array para zero
				Arrays.fill(quantidadeGC, 0);

				while (contadorVezes <= nCodons){
					//System.out.println(codon);

					//obtém-se um códon
					codon = sequencia.substring(inicioCodon, fimCodon);

					//altera os valores de início/fim para o próximo códon
					inicioCodon = inicioCodon + TRINCA;
					fimCodon = fimCodon + TRINCA;


					//verifica se há as bases C ou G na primeira, na segunda e na terceira
					//posição do códon
					if (codon.charAt(0) == 'G' || codon.charAt(0) == 'C'){
						quantidadeGC[0]++;
					} 
					if (codon.charAt(1) == 'G' || codon.charAt(1) == 'C'){
						quantidadeGC[1]++;
					}
					if (codon.charAt(2) == 'G' || codon.charAt(2) == 'C'){
						quantidadeGC[2]++;
					}
					contadorVezes++;
				}
				
				this.quantidadeGC.put(cor, quantidadeGC);
			}
		}
	}
}