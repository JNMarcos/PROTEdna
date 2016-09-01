/**
 * 
 */
package desenvolvimento;

import java.util.Arrays;
import java.util.List;

import console.ManipularArquivos;

/**
 * @author JN
 *
 */
public class AnalisarDNA {

	private Dados dados;
	public static final int TAMANHO_CODON = 3;
	private static final String LETRA_COMPLEMENTAR_TRINCA = "K";

	public AnalisarDNA (Dados dados){
		this.dados = dados;
	}

	public void analisar(){
		// usado para pegar o c�don
		String codon;

		// guarda o complemento de um c�don
		String complementoCodon;

		//guarda a sequ�ncia de bases modificada = seq original + complementoCodon
		String seq = "";

		// guarda a sequ�ncia original com espa�os, delimitando os c�dons
		String seqCodon;



		//delimita a posi��o de in�cio e de fim de um c�don
		int inicioCodon;
		int fimCodon;

		//quantidade de GCs na sequ�ncia
		int[] quantidadeGC;
		
		// diz o n�mero de bases que existem para cada posi��o de um c�don de uma seq
		int[] nBasesEmCadaPosicaoDeTamanhoCodon;

		
		// sequ�ncias de DNA de um arquivo
		List<SequenciaDNA> sequenciasArquivos;

		//tem o n�mero de c�dons de uma determinada sequencia
		int nCodons;

		//apenas conta o n�mero de itera��es, para n�o passar o tamanho da String = sequ�ncia
		int contadorVezes;

		// n�mero de itera��es
		int nVezes = 0;

		//os nomes do arquivos s�o nomes de cores
		for (String cor: ManipularArquivos.arquivosDNA){
			sequenciasArquivos = dados.getSequenciasDNA().get(cor);
			for (SequenciaDNA sequencia: sequenciasArquivos){

				quantidadeGC = new int[TAMANHO_CODON];
				nBasesEmCadaPosicaoDeTamanhoCodon = new int[TAMANHO_CODON];

				complementoCodon = "";
				seqCodon = "";

				//seta os valores para a sequ�ncia
				contadorVezes = 1;
				inicioCodon = 0;
				fimCodon = TAMANHO_CODON;

				//tem de arredondar para baixo caso o tammanho das sequ�ncias n�o sejam
				//necessariamente m�ltiplo de 3
				nCodons = sequencia.getSequencia().length()/TAMANHO_CODON;

				// adiciona uma string para complementar a sequ�ncia,
				// para que se tenha uma trinca completa
				// adiciona uma letra que n�o fa�a parte do conjunto original

				if (sequencia.getSequencia().length() % TAMANHO_CODON == 0){
					nVezes = nCodons;
					seq = sequencia.getSequencia();	
					
					for (int j = 0; j < TAMANHO_CODON; j++){
						nBasesEmCadaPosicaoDeTamanhoCodon[j] = nVezes;
					}
				} else{
					for (int i = 1; i < TAMANHO_CODON; i++){
						if (sequencia.getSequencia().length() % TAMANHO_CODON == i){
							nVezes = nCodons + 1; // adiciona mais um pq tem mais um c�don
							
							for (int j = 0; j < TAMANHO_CODON - i; j++){
								// adiciona letras complementares
								// para que haja um c�don
								complementoCodon = complementoCodon + LETRA_COMPLEMENTAR_TRINCA;
							}
							
							seq = sequencia.getSequencia() + complementoCodon;
							
							for (int j = i; j < TAMANHO_CODON; j++){
								nBasesEmCadaPosicaoDeTamanhoCodon[j] = nCodons;
							}
							for (int j = i - 1; j >= 0; j--){
								nBasesEmCadaPosicaoDeTamanhoCodon[j] = nVezes;
							}
							
							break;
						}
					}
				}

				sequencia.setnBasesEmCadaPosicaoDeTamanhoCodon(nBasesEmCadaPosicaoDeTamanhoCodon);


				//seta todos os valores do array para zero
				Arrays.fill(quantidadeGC, 0);

				while (contadorVezes <= nVezes){
					//obt�m-se um c�don
					codon = seq.substring(inicioCodon, fimCodon);
					seqCodon = seqCodon + codon + " ";
					//altera os valores de in�cio/fim para o pr�ximo c�don
					inicioCodon = inicioCodon + TAMANHO_CODON;
					fimCodon = fimCodon + TAMANHO_CODON;


					//verifica se h� as bases C ou G na primeira, na segunda e na terceira
					//posi��o do c�don
					for (int i = 0; i < TAMANHO_CODON; i++){
						if (codon.charAt(i) == 'G' || codon.charAt(i) == 'C'){
							quantidadeGC[i]++;
						} 
					}
					contadorVezes++;
				}

				//tira o �ltima espa�o em branco
				seqCodon = seqCodon.trim();

				//verifica se a seqCodon termina com k ou kk para remov�-los
				if (seqCodon.endsWith(complementoCodon)){
					seqCodon = seqCodon.substring(0, seqCodon.length() - (complementoCodon.length()));
				} 

				sequencia.setSequencia(seqCodon);
				sequencia.setQuantidadeGC(quantidadeGC);
			}

		}
	}
}