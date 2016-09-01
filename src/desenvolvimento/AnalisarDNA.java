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
		// usado para pegar o códon
		String codon;

		// guarda o complemento de um códon
		String complementoCodon;

		//guarda a sequência de bases modificada = seq original + complementoCodon
		String seq = "";

		// guarda a sequência original com espaços, delimitando os códons
		String seqCodon;



		//delimita a posição de início e de fim de um códon
		int inicioCodon;
		int fimCodon;

		//quantidade de GCs na sequência
		int[] quantidadeGC;
		
		// diz o número de bases que existem para cada posição de um códon de uma seq
		int[] nBasesEmCadaPosicaoDeTamanhoCodon;

		
		// sequências de DNA de um arquivo
		List<SequenciaDNA> sequenciasArquivos;

		//tem o número de códons de uma determinada sequencia
		int nCodons;

		//apenas conta o número de iterações, para não passar o tamanho da String = sequência
		int contadorVezes;

		// número de iterações
		int nVezes = 0;

		//os nomes do arquivos são nomes de cores
		for (String cor: ManipularArquivos.arquivosDNA){
			sequenciasArquivos = dados.getSequenciasDNA().get(cor);
			for (SequenciaDNA sequencia: sequenciasArquivos){

				quantidadeGC = new int[TAMANHO_CODON];
				nBasesEmCadaPosicaoDeTamanhoCodon = new int[TAMANHO_CODON];

				complementoCodon = "";
				seqCodon = "";

				//seta os valores para a sequência
				contadorVezes = 1;
				inicioCodon = 0;
				fimCodon = TAMANHO_CODON;

				//tem de arredondar para baixo caso o tammanho das sequências não sejam
				//necessariamente múltiplo de 3
				nCodons = sequencia.getSequencia().length()/TAMANHO_CODON;

				// adiciona uma string para complementar a sequência,
				// para que se tenha uma trinca completa
				// adiciona uma letra que não faça parte do conjunto original

				if (sequencia.getSequencia().length() % TAMANHO_CODON == 0){
					nVezes = nCodons;
					seq = sequencia.getSequencia();	
					
					for (int j = 0; j < TAMANHO_CODON; j++){
						nBasesEmCadaPosicaoDeTamanhoCodon[j] = nVezes;
					}
				} else{
					for (int i = 1; i < TAMANHO_CODON; i++){
						if (sequencia.getSequencia().length() % TAMANHO_CODON == i){
							nVezes = nCodons + 1; // adiciona mais um pq tem mais um códon
							
							for (int j = 0; j < TAMANHO_CODON - i; j++){
								// adiciona letras complementares
								// para que haja um códon
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
					//obtém-se um códon
					codon = seq.substring(inicioCodon, fimCodon);
					seqCodon = seqCodon + codon + " ";
					//altera os valores de início/fim para o próximo códon
					inicioCodon = inicioCodon + TAMANHO_CODON;
					fimCodon = fimCodon + TAMANHO_CODON;


					//verifica se há as bases C ou G na primeira, na segunda e na terceira
					//posição do códon
					for (int i = 0; i < TAMANHO_CODON; i++){
						if (codon.charAt(i) == 'G' || codon.charAt(i) == 'C'){
							quantidadeGC[i]++;
						} 
					}
					contadorVezes++;
				}

				//tira o última espaço em branco
				seqCodon = seqCodon.trim();

				//verifica se a seqCodon termina com k ou kk para removê-los
				if (seqCodon.endsWith(complementoCodon)){
					seqCodon = seqCodon.substring(0, seqCodon.length() - (complementoCodon.length()));
				} 

				sequencia.setSequencia(seqCodon);
				sequencia.setQuantidadeGC(quantidadeGC);
			}

		}
	}
}