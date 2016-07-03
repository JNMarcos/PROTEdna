/**
 * 
 */
package desenvolvimento;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import console.Dados;
import console.ManipularArquivos;

/**
 * @author JN
 *
 */
public class AnalisarDNA {

	private Dados dados;
	private String codon;
	private static final int TRINCA = 3;
	private int[] quantidadeGC;

	public AnalisarDNA (Dados dados){
		this.dados = dados;
	}

	public void analisar(){
		int inicioCodon;
		int fimCodon;

		List<SequenciaDNA> sequenciasArquivos;

		//tem o número de códons de uma determinada sequencia
		int nCodons;

		//apenas conta o número de iterações, para não passar o tamanho da String = sequência
		int contadorVezes;
		//int nVezes = 0;
		
		//os nomes do arquivos são nomes de cores
		for (String cor: ManipularArquivos.arquivos){
			sequenciasArquivos = dados.getSequenciasDNA().get(cor);
			this.quantidadeGC = new int[TRINCA];
			for (SequenciaDNA sequencia: sequenciasArquivos){
				
				//seta os valores para a sequência
				contadorVezes = 1;
				inicioCodon = 0;
				fimCodon = TRINCA;
				
				//tem de arredondar para baixo caso o tammanho das sequências não sejam
				//necessariamente múltiplo de 3
				nCodons = sequencia.getSequencia().length()/TRINCA;
				
				//seta todos os valores do array para zero
				Arrays.fill(quantidadeGC, 0);

				while (contadorVezes <= nCodons){
					//obtém-se um códon
					codon = sequencia.getSequencia().substring(inicioCodon, fimCodon);
					System.out.println(codon);
					
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
				
				//nVezes++;
				System.out.println(quantidadeGC[0] + "   " + quantidadeGC[1] + "  " +
						 quantidadeGC[2]);
				sequencia.setQuantidadeGC(quantidadeGC);
			}
			
		}
		
		/*descomente caso queira
		 * número de vezes que entra no for, que deve ser o número total de sequências, se for
		 * diferente disso, tá errado
		 * lembre de descomentar a declaração e adição a cada iteração (nVezes++) para obter
		 * o resultado esperado
		System.out.println(nVezes);
		*/
	}
}