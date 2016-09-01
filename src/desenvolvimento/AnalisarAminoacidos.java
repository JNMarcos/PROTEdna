/**
 * 
 */
package desenvolvimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import console.ManipularArquivos;

/**
 * @author JN
 *
 */
public class AnalisarAminoacidos {
	private Dados dados;

	// endereço para traduzir as sequencias de dna para aminoacidos

	/*
	private static String enderecoAminoacido;

	// endereco para alinhar duas sequencias
	private static String enderecoProteina;
	 */

	public AnalisarAminoacidos(Dados dados, String enderecoAminoacido, String enderecoProteina){
		this.setDados(dados);
		//this.setEnderecoAminoacido(enderecoAminoacido);
		//this.setEnderecoProteina(enderecoProteina);
	}

	public Dados getDados() {
		return dados;
	}

	public void setDados(Dados dados) {
		this.dados = dados;
	}

	/*
	public static String getEnderecoAminoacido() {
		return enderecoAminoacido;
	}

	public static void setEnderecoAminoacido(String enderecoAminoacido) {
		AnalisarAminoacidos.enderecoAminoacido = enderecoAminoacido;
	}

	public static String getEnderecoProteina() {
		return enderecoProteina;
	}

	public static void setEnderecoProteina(String enderecoProteina) {
		AnalisarAminoacidos.enderecoProteina = enderecoProteina;
	}


	public void tranformarDNAemAminoacidos(){
		// usada para armazenar uma seq de aminoácido
		String sequenciaAminoacido;

		//contém uma lista das sequências de aminoácidos, é provisória, são strings
		List<String> sequenciasAminoacidosArquivo;

		//contém alinhamentos entre sequências de um arquivo, um objeto Alinham...
		List<AlinhamentoAminoacido> alinhamentosArquivo;

		/*
		HttpsURLConnection urlConnection;
		URL urlAminoacido = null;
		Scanner escaner;

		// tenta criar uma instancia da URL
		try {
			urlAminoacido = new URL(enderecoAminoacido);
		}catch (java.net.MalformedURLException e){
			System.out.println("Erro ao criar URL. Formato inválido.");
		}		

		for (String cor : ManipularArquivos.arquivos){

			// cria-se uma lista vazia para armazenar as sequencias e alinhamentos entre sequências de um
			// arquivo
			sequenciasAminoacidosArquivo = new ArrayList<String>();
			alinhamentosArquivo = new ArrayList<AlinhamentoAminoacido>();

			// for para 'transformar' cada seq DNA em seq aminoacido
			for (SequenciaDNA sequencia : dados.getSequenciasDNA().get(cor)){
				try {
					urlConnection = (HttpsURLConnection) urlAminoacido.openConnection();
					escaner = new Scanner((Readable) urlConnection);
				} catch (IOException e) {
					System.out.println("Erro ao acessar URL.");
				}
				// passa a sequência para o blastx
				// tem de fazer uma conexão, ou tem de pegar por um arquivo a ser criado
				// obtém-se as respostas, após isso

				// sequenciaAminoacido recebe aqui a sequência após o BLASTX e remove todos
				// os espaços em branco finais e iniciais e deixa tudo em maiúsucla
				sequenciaAminoacido = null; // trocar
				sequenciaAminoacido = sequenciaAminoacido.trim();
				sequenciaAminoacido = sequenciaAminoacido.toUpperCase();

				// a sequência obtida é adicionada na lista de seq de aminoácidos do arquivo
				sequenciasAminoacidosArquivo.add(sequenciaAminoacido);				
			}

			// cria-se os alinhamentos possíveis
			for (String sequenciaAArquivo : sequenciasAminoacidosArquivo){
				// remove a sequência da vez da lista de sequências
				sequenciasAminoacidosArquivo.remove(sequenciaAArquivo);

				// isso é para não criar Alinhamentos com a mesma seq. 
				// ex.: ALBCVGBBST com ALBCVGBBST
				for (String sequenciaBArquivo : sequenciasAminoacidosArquivo){
					alinhamentosArquivo.add(new AlinhamentoAminoacido(sequenciaAArquivo,
							sequenciaBArquivo));
				}
			}

			//adiciona a lista de alinhamentos referente ao arquivo
			dados.getAlinhamentosAminoacidos().put(cor, alinhamentosArquivo);
		}
	}
*/
	public void obterAlinhamento(){
		for (String cor : ManipularArquivos.arquivosAmino){
			for (AlinhamentoAminoacido alinhamentoArquivo : dados.getAlinhamentosAminoacidos().get(cor)){
				// passa as duas sequências dentro de alinhamento e
				// alinha no blast

				// obtém as duas sequências alinhadas e coloca no próprio objeto alinhamento
			Alinhar alinharSequencias = new Alinhar(alinhamentoArquivo);
			alinharSequencias.inicializarArrayList();
			alinharSequencias.inicializarMatriz();
			alinharSequencias.preencherMatriz();
			alinharSequencias.construirCaminho();	
			}
		}
	}

	private void encontrarPareamentoEntreRegioes(RegiaoBaixaSimilaridade regiaoA,
			RegiaoBaixaSimilaridade regiaoB){

		// mantém as sequências de baixa similaridade
		String regiaoBaixaSimilaridadeA = regiaoA.getRegiao();
		String regiaoBaixaSimilaridadeB = regiaoB.getRegiao();

		// a sequência a ser usada
		String sequenciaPareamento = "";

		int iteradorPareamento = dados.getTamanhoMinimoPareamento();

		//indica o nº de vezes que houve um match para um segmento da região a na
		// região b
		int contadorPareamento = 0;

		// usada para procurar regiões de semelhança entre as regiões
		Hashtable<String, Integer> pareamentoTamanho3Inferior = new Hashtable<>();
		Hashtable<String, Integer> pareamentoTamanho4 = new Hashtable<>();
		Hashtable<String, Integer> pareamentoTamanho5 = new Hashtable<>();
		Hashtable<String, Integer> pareamentoTamanho6Superior = new Hashtable<>();


		while(iteradorPareamento <= dados.getTamanhoMaximoPareamento()){
			if (iteradorPareamento < regiaoBaixaSimilaridadeA.length() + 1){
				for (int j = 0; j < regiaoBaixaSimilaridadeA.length() - 
						(iteradorPareamento - 1); j++){

					//armazena a sequência de pareamento de tamanho  iteradorPareamento
					sequenciaPareamento = regiaoBaixaSimilaridadeA.substring(j, 
							j + iteradorPareamento);

					contadorPareamento = 0;

					//compara com a região B
					for (int k = 0; k < regiaoBaixaSimilaridadeB.length()
							- (iteradorPareamento - 1);	k++){

						if ((regiaoBaixaSimilaridadeB.
								substring(k, k + iteradorPareamento)).
								equals(sequenciaPareamento) || 
								regiaoBaixaSimilaridadeB.
								substring(k, k + iteradorPareamento).
								equals(new StringBuffer(sequenciaPareamento).reverse())){
							contadorPareamento++;
						}
					}

					switch(iteradorPareamento){
					case 1:
					case 2:
					case 3:
						pareamentoTamanho3Inferior.put(sequenciaPareamento, contadorPareamento);
						break;
					case 4:
						pareamentoTamanho4.put(sequenciaPareamento, contadorPareamento);
						break;
					case 5:
						pareamentoTamanho5.put(sequenciaPareamento, contadorPareamento);
						break;
					case 6:
						pareamentoTamanho6Superior.put(sequenciaPareamento, contadorPareamento);
						break;
					default:
						pareamentoTamanho6Superior.put(sequenciaPareamento, contadorPareamento);
					}
				}
			}
			iteradorPareamento++;
		}
		// seta os resultados
		regiaoA.setPareamentoTamanho3Inferior(pareamentoTamanho3Inferior);
		regiaoA.setPareamentoTamanho4(pareamentoTamanho4);
		regiaoA.setPareamentoTamanho5(pareamentoTamanho5);
		regiaoA.setPareamentoTamanho6Superior(pareamentoTamanho6Superior);
	}


	private void encontrarVizinhosDoAminoacidoMaisFrequente(RegiaoBaixaSimilaridade regiaoA,
			RegiaoBaixaSimilaridade regiaoB){

		//conta os vizinhos que estão à direita e à esquerda do aminoácido mais frequente 
		int[] contadorVizinhoDireitaRegiaoA = new int[dados.getTiposAminoacidos().length];
		int[] contadorVizinhoEsquerdaRegiaoA = new int[dados.getTiposAminoacidos().length];
		int[] contadorVizinhoDireitaRegiaoB = new int[dados.getTiposAminoacidos().length];
		int[] contadorVizinhoEsquerdaRegiaoB = new int[dados.getTiposAminoacidos().length];

		//auxiliares da tarefa acima
		char caractere;
		char direita, dir;
		char esquerda, esq;

		Arrays.fill(contadorVizinhoDireitaRegiaoA, 0);
		Arrays.fill(contadorVizinhoEsquerdaRegiaoA, 0);

		for (int j = 0; j < regiaoA.getRegiao().length(); j++){
			caractere = regiaoA.getRegiao().charAt(j);


			if (caractere == dados.getTiposAminoacidos()
					[regiaoA.getIndiceMaiorFrequenciaNaRegiao()]){
				//System.out.println("carctere dentro : " + caractere + "");
				if (j != 0){
					if (j < regiaoA.getRegiao().length() - 1){
						direita = regiaoA.getRegiao().charAt(j + 1);

						for (int k = 0; k < dados.getTiposAminoacidos().length; k++){
							dir = dados.getTiposAminoacidos()[k];

							if (dir == direita){
								contadorVizinhoDireitaRegiaoA[k]++;
								break;
							}
						}
					}

					esquerda = regiaoA.getRegiao().charAt(j - 1);
					for (int k = 0; k < dados.getTiposAminoacidos().length; k++){
						esq = dados.getTiposAminoacidos()[k];

						if (esq == esquerda){
							contadorVizinhoEsquerdaRegiaoA[k]++;
							break;
						}
					}
				} else {
					direita = regiaoA.getRegiao().charAt(j + 1);

					for (int k = 0; k < dados.getTiposAminoacidos().length; k++){
						dir = dados.getTiposAminoacidos()[k];

						if (dir == direita){
							contadorVizinhoDireitaRegiaoA[k]++;
							break;
						}
					}
				}
			}
		}

		regiaoA.setContadorVizinhosADireitaAminoacidoDeMaiorFrequencia(
				contadorVizinhoDireitaRegiaoA);
		regiaoA.setContadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia(
				contadorVizinhoEsquerdaRegiaoA);


		Arrays.fill(contadorVizinhoDireitaRegiaoB, 0);
		Arrays.fill(contadorVizinhoEsquerdaRegiaoB, 0);					

		for (int j = 0; j < regiaoB.getRegiao().length(); j++){
			caractere = regiaoB.getRegiao().charAt(j);

			if (caractere == dados.getTiposAminoacidos()
					[regiaoB.getIndiceMaiorFrequenciaNaRegiao()]){
				if (j != 0){// pega o da dir e da esq
					if (j < regiaoB.getRegiao().length() - 1){
						direita = regiaoB.getRegiao().charAt(j + 1);
						for (int k = 0; k < dados.getTiposAminoacidos().length; k++){
							dir = dados.getTiposAminoacidos()[k];

							if (dir == direita){
								contadorVizinhoDireitaRegiaoB[k]++;
								break;
							}
						}
					}

					esquerda = regiaoB.getRegiao().charAt(j - 1);

					for (int k = 0; k < dados.getTiposAminoacidos().length; k++){
						esq = dados.getTiposAminoacidos()[k];

						if (esq == esquerda){
							contadorVizinhoEsquerdaRegiaoB[k]++;
							break;
						}
					}
				} else {
					direita = regiaoB.getRegiao().charAt(j + 1);

					for (int k = 0; k < dados.getTiposAminoacidos().length; k++){
						dir = dados.getTiposAminoacidos()[k];

						if (dir == direita){
							contadorVizinhoDireitaRegiaoB[k]++;
							break;
						}
					}
				}
			}

		}

		regiaoB.setContadorVizinhosADireitaAminoacidoDeMaiorFrequencia(
				contadorVizinhoDireitaRegiaoB);
		regiaoB.setContadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia(
				contadorVizinhoEsquerdaRegiaoB);


	}

	private void encontrarAminoacidoMaisFrequente(RegiaoBaixaSimilaridade regiaoA, 
			RegiaoBaixaSimilaridade regiaoB){
		// guarda as regiões de baixa similaridade de cada um dos alinhamentos por vez
		String regiaoBaixaSimilaridadeA;
		String regiaoBaixaSimilaridadeB;

		//conta o número de aminoácidos de cada tipo em cada uma das seq alinhadas
		int[] contadorAminoacidosRegiaoA;
		int[] contadorAminoacidosRegiaoB;

		// sabe-se o número de vezes que um aminoacido aparece
		int numeroDeSecoesSequenciaA;
		int numeroDeSecoesSequenciaB;

		int indiceArrays;

		// guarda o índice do aminoácido que mais aparece na região
		int indiceMaiorNumeroDeAminoacidosRegiaoA;
		int indiceMaiorNumeroDeAminoacidosRegiaoB;

		// guarda a quantidade de vezes que aminoácido MAIS frequente aparece
		int maiorNumeroDeAminoacidosRegiaoA;
		int maiorNumeroDeAminoacidosRegiaoB;

		regiaoBaixaSimilaridadeA = regiaoA.getRegiao();
		regiaoBaixaSimilaridadeB = regiaoB.getRegiao();

		// instancia os contadores de aminoácidos e vizinhos
		contadorAminoacidosRegiaoA = new int[dados.getTiposAminoacidos().length];
		contadorAminoacidosRegiaoB = new int[dados.getTiposAminoacidos().length];

		// põe o valor zero para todos os índices de cada um dos arrays e indiceArrays 
		// com zero tb 
		Arrays.fill(contadorAminoacidosRegiaoA, 0);
		Arrays.fill(contadorAminoacidosRegiaoB, 0);

		// dando valores às variáveis
		indiceArrays = 0;

		indiceMaiorNumeroDeAminoacidosRegiaoA = -1;
		indiceMaiorNumeroDeAminoacidosRegiaoB = -1;

		maiorNumeroDeAminoacidosRegiaoA = -1;
		maiorNumeroDeAminoacidosRegiaoB = -1;

		// compara cada uma das regiões com os tipos de aminoácidos
		for (char c : dados.getTiposAminoacidos()){
			numeroDeSecoesSequenciaA = regiaoBaixaSimilaridadeA.split(c + "").length;
			numeroDeSecoesSequenciaB = regiaoBaixaSimilaridadeB.split(c + "").length;

			// adiciona o número de resíduos de aminoacidos na regiãoA
			if (regiaoBaixaSimilaridadeA.charAt(regiaoBaixaSimilaridadeA.length() - 1) == c){
				contadorAminoacidosRegiaoA[indiceArrays] = numeroDeSecoesSequenciaA;
			} else{
				contadorAminoacidosRegiaoA[indiceArrays] = numeroDeSecoesSequenciaA - 1;
			}

			// adiciona o número de resíduos de aminoacidos na regiãoB
			if (regiaoBaixaSimilaridadeB.charAt(regiaoBaixaSimilaridadeB.length() - 1) == c){
				contadorAminoacidosRegiaoB[indiceArrays] = numeroDeSecoesSequenciaB;
			} else{
				contadorAminoacidosRegiaoB[indiceArrays] = numeroDeSecoesSequenciaB - 1;
			}

			// verifica se o novo aminoácido testado possui maior valor que 
			// o outro que tem o maior número
			if (contadorAminoacidosRegiaoA[indiceArrays] > maiorNumeroDeAminoacidosRegiaoA){
				maiorNumeroDeAminoacidosRegiaoA = contadorAminoacidosRegiaoA[indiceArrays];
				indiceMaiorNumeroDeAminoacidosRegiaoA = indiceArrays;
			}

			// idem acima, porém para a região B
			if (contadorAminoacidosRegiaoB[indiceArrays] > maiorNumeroDeAminoacidosRegiaoB){
				maiorNumeroDeAminoacidosRegiaoB = contadorAminoacidosRegiaoB[indiceArrays];
				indiceMaiorNumeroDeAminoacidosRegiaoB = indiceArrays;
			}

			indiceArrays++;
		}

		// adiciona o número de aminoácidos de cada tipo presente na região
		regiaoA.setContadorAminoacidosRegiao(contadorAminoacidosRegiaoA);
		regiaoB.setContadorAminoacidosRegiao(contadorAminoacidosRegiaoB);

		// adiciona o índice do aminoácido que mais aparece para facilitar a busca
		regiaoA.setIndiceMaiorFrequenciaNaRegiao(indiceMaiorNumeroDeAminoacidosRegiaoA);
		regiaoB.setIndiceMaiorFrequenciaNaRegiao(indiceMaiorNumeroDeAminoacidosRegiaoB);
	}


	private void encontrarRegioesBaixaSimilaridade(AlinhamentoAminoacido alinhamentoArquivo){

		// guarda as seq alinhadas
		String sequenciaAlinhadaA = alinhamentoArquivo.getSequenciaAAlinhada();
		String sequenciaAlinhadaB = alinhamentoArquivo.getSequenciaBAlinhada();

		List<RegiaoBaixaSimilaridade> regioesBaixaSimilaridadeA = new ArrayList<>();
		List<RegiaoBaixaSimilaridade> regioesBaixaSimilaridadeB = new ArrayList<>();

		String regiaoBaixaSimilaridadeA = "";
		String regiaoBaixaSimilaridadeB = "";



		//contador que indica se já pode ser considerada uma região de baixa similaridade
		int eRegiaoBaixaSimilaridade = 0;


		// NÃO NECESSÁRIO SE ESTIVEREM REALMENTE ALINHADAS
		// USANDO O ALINHAMENTO
		//apenas para descobrir qual tem o menor tamanho
		int tamanhoSeqA;
		int tamanhoSeqB;
		int menorTamanho;

		tamanhoSeqA = sequenciaAlinhadaA.length();
		tamanhoSeqB = sequenciaAlinhadaB.length();

		if (tamanhoSeqA < tamanhoSeqB) menorTamanho = tamanhoSeqA; else menorTamanho = tamanhoSeqB;

		for (int i = 0; i < menorTamanho; i++){
			if (sequenciaAlinhadaA.charAt(i) != sequenciaAlinhadaB.charAt(i)){
				eRegiaoBaixaSimilaridade++; // se é dif quer dizer que começa a se
				// formar uma região de baixa similaridade
				regiaoBaixaSimilaridadeA = regiaoBaixaSimilaridadeA + sequenciaAlinhadaA.charAt(i);
				regiaoBaixaSimilaridadeB = regiaoBaixaSimilaridadeB + sequenciaAlinhadaB.charAt(i);

			} else {
				// ver se tem tamanho mínimo para ser uma região de baixa similaridade
				if (eRegiaoBaixaSimilaridade >= dados.getTamanhoMinimoRegiaoBaixaSimilaridade()){
					// adiciona as regiões à lista de regiões de baixa similaridade
					regioesBaixaSimilaridadeA.add(new RegiaoBaixaSimilaridade(
							regiaoBaixaSimilaridadeA));
					regioesBaixaSimilaridadeB.add(new RegiaoBaixaSimilaridade(
							regiaoBaixaSimilaridadeB));
				}

				// seta para zero, quer dizer que acabou essa  região
				eRegiaoBaixaSimilaridade = 0; 

				// seta para null as regiões das seqs visto que se procurará uma outra
				regiaoBaixaSimilaridadeA = "";
				regiaoBaixaSimilaridadeB = "";
			}
		}
		alinhamentoArquivo.setRegioesBaixaSimilaridadeA(regioesBaixaSimilaridadeA);
		alinhamentoArquivo.setRegioesBaixaSimilaridadeB(regioesBaixaSimilaridadeB);
	}

	public void analisar(){
		RegiaoBaixaSimilaridade regiaoA;
		RegiaoBaixaSimilaridade regiaoB;	
		for (String cor : ManipularArquivos.arquivosAmino){
			for (AlinhamentoAminoacido alinhamentoArquivo : dados.getAlinhamentosAminoacidos().get(cor)){

				encontrarRegioesBaixaSimilaridade(alinhamentoArquivo);

				for (int i = 0; i < alinhamentoArquivo.getRegioesBaixaSimilaridadeA().size(); i++){
					regiaoA = alinhamentoArquivo.getRegioesBaixaSimilaridadeA().get(i);
					regiaoB = alinhamentoArquivo.getRegioesBaixaSimilaridadeB().get(i);

					encontrarAminoacidoMaisFrequente(regiaoA, regiaoB);
					encontrarVizinhosDoAminoacidoMaisFrequente(regiaoA, regiaoB);
					encontrarPareamentoEntreRegioes(regiaoA, regiaoB);
				}
			}
		}
	}
}
