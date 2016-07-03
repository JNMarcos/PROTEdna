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

		//tem o n�mero de c�dons de uma determinada sequencia
		int nCodons;

		//apenas conta o n�mero de itera��es, para n�o passar o tamanho da String = sequ�ncia
		int contadorVezes;
		//int nVezes = 0;
		
		//os nomes do arquivos s�o nomes de cores
		for (String cor: ManipularArquivos.arquivos){
			sequenciasArquivos = dados.getSequenciasDNA().get(cor);
			this.quantidadeGC = new int[TRINCA];
			for (SequenciaDNA sequencia: sequenciasArquivos){
				
				//seta os valores para a sequ�ncia
				contadorVezes = 1;
				inicioCodon = 0;
				fimCodon = TRINCA;
				
				//tem de arredondar para baixo caso o tammanho das sequ�ncias n�o sejam
				//necessariamente m�ltiplo de 3
				nCodons = sequencia.getSequencia().length()/TRINCA;
				
				//seta todos os valores do array para zero
				Arrays.fill(quantidadeGC, 0);

				while (contadorVezes <= nCodons){
					//obt�m-se um c�don
					codon = sequencia.getSequencia().substring(inicioCodon, fimCodon);
					System.out.println(codon);
					
					//altera os valores de in�cio/fim para o pr�ximo c�don
					inicioCodon = inicioCodon + TRINCA;
					fimCodon = fimCodon + TRINCA;


					//verifica se h� as bases C ou G na primeira, na segunda e na terceira
					//posi��o do c�don
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
		 * n�mero de vezes que entra no for, que deve ser o n�mero total de sequ�ncias, se for
		 * diferente disso, t� errado
		 * lembre de descomentar a declara��o e adi��o a cada itera��o (nVezes++) para obter
		 * o resultado esperado
		System.out.println(nVezes);
		*/
	}
}