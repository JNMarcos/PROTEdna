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

	// endere�o para traduzir as sequencias de dna para aminoacidos

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
		// usada para armazenar uma seq de amino�cido
		String sequenciaAminoacido;

		//cont�m uma lista das sequ�ncias de amino�cidos, � provis�ria, s�o strings
		List<String> sequenciasAminoacidosArquivo;

		//cont�m alinhamentos entre sequ�ncias de um arquivo, um objeto Alinham...
		List<AlinhamentoAminoacido> alinhamentosArquivo;

		/*
		HttpsURLConnection urlConnection;
		URL urlAminoacido = null;
		Scanner escaner;

		// tenta criar uma instancia da URL
		try {
			urlAminoacido = new URL(enderecoAminoacido);
		}catch (java.net.MalformedURLException e){
			System.out.println("Erro ao criar URL. Formato inv�lido.");
		}		

		for (String cor : ManipularArquivos.arquivos){

			// cria-se uma lista vazia para armazenar as sequencias e alinhamentos entre sequ�ncias de um
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
				// passa a sequ�ncia para o blastx
				// tem de fazer uma conex�o, ou tem de pegar por um arquivo a ser criado
				// obt�m-se as respostas, ap�s isso

				// sequenciaAminoacido recebe aqui a sequ�ncia ap�s o BLASTX e remove todos
				// os espa�os em branco finais e iniciais e deixa tudo em mai�sucla
				sequenciaAminoacido = null; // trocar
				sequenciaAminoacido = sequenciaAminoacido.trim();
				sequenciaAminoacido = sequenciaAminoacido.toUpperCase();

				// a sequ�ncia obtida � adicionada na lista de seq de amino�cidos do arquivo
				sequenciasAminoacidosArquivo.add(sequenciaAminoacido);				
			}

			// cria-se os alinhamentos poss�veis
			for (String sequenciaAArquivo : sequenciasAminoacidosArquivo){
				// remove a sequ�ncia da vez da lista de sequ�ncias
				sequenciasAminoacidosArquivo.remove(sequenciaAArquivo);

				// isso � para n�o criar Alinhamentos com a mesma seq. 
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
				// passa as duas sequ�ncias dentro de alinhamento e
				// alinha no blast

				// obt�m as duas sequ�ncias alinhadas e coloca no pr�prio objeto alinhamento
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

		// mant�m as sequ�ncias de baixa similaridade
		String regiaoBaixaSimilaridadeA = regiaoA.getRegiao();
		String regiaoBaixaSimilaridadeB = regiaoB.getRegiao();

		// a sequ�ncia a ser usada
		String sequenciaPareamento = "";

		int iteradorPareamento = dados.getTamanhoMinimoPareamento();

		//indica o n� de vezes que houve um match para um segmento da regi�o a na
		// regi�o b
		int contadorPareamento = 0;

		// usada para procurar regi�es de semelhan�a entre as regi�es
		Hashtable<String, Integer> pareamentoTamanho3Inferior = new Hashtable<>();
		Hashtable<String, Integer> pareamentoTamanho4 = new Hashtable<>();
		Hashtable<String, Integer> pareamentoTamanho5 = new Hashtable<>();
		Hashtable<String, Integer> pareamentoTamanho6Superior = new Hashtable<>();


		while(iteradorPareamento <= dados.getTamanhoMaximoPareamento()){
			if (iteradorPareamento < regiaoBaixaSimilaridadeA.length() + 1){
				for (int j = 0; j < regiaoBaixaSimilaridadeA.length() - 
						(iteradorPareamento - 1); j++){

					//armazena a sequ�ncia de pareamento de tamanho  iteradorPareamento
					sequenciaPareamento = regiaoBaixaSimilaridadeA.substring(j, 
							j + iteradorPareamento);

					contadorPareamento = 0;

					//compara com a regi�o B
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

		//conta os vizinhos que est�o � direita e � esquerda do amino�cido mais frequente 
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
		// guarda as regi�es de baixa similaridade de cada um dos alinhamentos por vez
		String regiaoBaixaSimilaridadeA;
		String regiaoBaixaSimilaridadeB;

		//conta o n�mero de amino�cidos de cada tipo em cada uma das seq alinhadas
		int[] contadorAminoacidosRegiaoA;
		int[] contadorAminoacidosRegiaoB;

		// sabe-se o n�mero de vezes que um aminoacido aparece
		int numeroDeSecoesSequenciaA;
		int numeroDeSecoesSequenciaB;

		int indiceArrays;

		// guarda o �ndice do amino�cido que mais aparece na regi�o
		int indiceMaiorNumeroDeAminoacidosRegiaoA;
		int indiceMaiorNumeroDeAminoacidosRegiaoB;

		// guarda a quantidade de vezes que amino�cido MAIS frequente aparece
		int maiorNumeroDeAminoacidosRegiaoA;
		int maiorNumeroDeAminoacidosRegiaoB;

		regiaoBaixaSimilaridadeA = regiaoA.getRegiao();
		regiaoBaixaSimilaridadeB = regiaoB.getRegiao();

		// instancia os contadores de amino�cidos e vizinhos
		contadorAminoacidosRegiaoA = new int[dados.getTiposAminoacidos().length];
		contadorAminoacidosRegiaoB = new int[dados.getTiposAminoacidos().length];

		// p�e o valor zero para todos os �ndices de cada um dos arrays e indiceArrays 
		// com zero tb 
		Arrays.fill(contadorAminoacidosRegiaoA, 0);
		Arrays.fill(contadorAminoacidosRegiaoB, 0);

		// dando valores �s vari�veis
		indiceArrays = 0;

		indiceMaiorNumeroDeAminoacidosRegiaoA = -1;
		indiceMaiorNumeroDeAminoacidosRegiaoB = -1;

		maiorNumeroDeAminoacidosRegiaoA = -1;
		maiorNumeroDeAminoacidosRegiaoB = -1;

		// compara cada uma das regi�es com os tipos de amino�cidos
		for (char c : dados.getTiposAminoacidos()){
			numeroDeSecoesSequenciaA = regiaoBaixaSimilaridadeA.split(c + "").length;
			numeroDeSecoesSequenciaB = regiaoBaixaSimilaridadeB.split(c + "").length;

			// adiciona o n�mero de res�duos de aminoacidos na regi�oA
			if (regiaoBaixaSimilaridadeA.charAt(regiaoBaixaSimilaridadeA.length() - 1) == c){
				contadorAminoacidosRegiaoA[indiceArrays] = numeroDeSecoesSequenciaA;
			} else{
				contadorAminoacidosRegiaoA[indiceArrays] = numeroDeSecoesSequenciaA - 1;
			}

			// adiciona o n�mero de res�duos de aminoacidos na regi�oB
			if (regiaoBaixaSimilaridadeB.charAt(regiaoBaixaSimilaridadeB.length() - 1) == c){
				contadorAminoacidosRegiaoB[indiceArrays] = numeroDeSecoesSequenciaB;
			} else{
				contadorAminoacidosRegiaoB[indiceArrays] = numeroDeSecoesSequenciaB - 1;
			}

			// verifica se o novo amino�cido testado possui maior valor que 
			// o outro que tem o maior n�mero
			if (contadorAminoacidosRegiaoA[indiceArrays] > maiorNumeroDeAminoacidosRegiaoA){
				maiorNumeroDeAminoacidosRegiaoA = contadorAminoacidosRegiaoA[indiceArrays];
				indiceMaiorNumeroDeAminoacidosRegiaoA = indiceArrays;
			}

			// idem acima, por�m para a regi�o B
			if (contadorAminoacidosRegiaoB[indiceArrays] > maiorNumeroDeAminoacidosRegiaoB){
				maiorNumeroDeAminoacidosRegiaoB = contadorAminoacidosRegiaoB[indiceArrays];
				indiceMaiorNumeroDeAminoacidosRegiaoB = indiceArrays;
			}

			indiceArrays++;
		}

		// adiciona o n�mero de amino�cidos de cada tipo presente na regi�o
		regiaoA.setContadorAminoacidosRegiao(contadorAminoacidosRegiaoA);
		regiaoB.setContadorAminoacidosRegiao(contadorAminoacidosRegiaoB);

		// adiciona o �ndice do amino�cido que mais aparece para facilitar a busca
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



		//contador que indica se j� pode ser considerada uma regi�o de baixa similaridade
		int eRegiaoBaixaSimilaridade = 0;


		// N�O NECESS�RIO SE ESTIVEREM REALMENTE ALINHADAS
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
				eRegiaoBaixaSimilaridade++; // se � dif quer dizer que come�a a se
				// formar uma regi�o de baixa similaridade
				regiaoBaixaSimilaridadeA = regiaoBaixaSimilaridadeA + sequenciaAlinhadaA.charAt(i);
				regiaoBaixaSimilaridadeB = regiaoBaixaSimilaridadeB + sequenciaAlinhadaB.charAt(i);

			} else {
				// ver se tem tamanho m�nimo para ser uma regi�o de baixa similaridade
				if (eRegiaoBaixaSimilaridade >= dados.getTamanhoMinimoRegiaoBaixaSimilaridade()){
					// adiciona as regi�es � lista de regi�es de baixa similaridade
					regioesBaixaSimilaridadeA.add(new RegiaoBaixaSimilaridade(
							regiaoBaixaSimilaridadeA));
					regioesBaixaSimilaridadeB.add(new RegiaoBaixaSimilaridade(
							regiaoBaixaSimilaridadeB));
				}

				// seta para zero, quer dizer que acabou essa  regi�o
				eRegiaoBaixaSimilaridade = 0; 

				// seta para null as regi�es das seqs visto que se procurar� uma outra
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
