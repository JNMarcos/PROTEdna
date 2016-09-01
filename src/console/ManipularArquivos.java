/**
 * 
 */
package console;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import desenvolvimento.AlinhamentoAminoacido;
import desenvolvimento.AnalisarDNA;
import desenvolvimento.Dados;
import desenvolvimento.SequenciaDNA;

/**
 * @author JN
 *
 */
public class ManipularArquivos {

	public static final String[] arquivosDNA = {"Amarelo.txt", "Azul.txt", "Verde.txt", "Rosa.txt",
			"Lil�s.txt", "Marrom.txt", "Vermelho.txt"};

	public static final String[] arquivosAmino = {"AmareloAmino.txt", "AzulAmino.txt","Lil�sAmino.txt",
	"MarromAmino.txt"};

	public static final String[] arquivosSaida = {"ResultadoGCs.txt", "ResultadoAlinhamento.txt"};

	private static Scanner scannerSequencia = null;

	public static Hashtable<String, List<SequenciaDNA>> lerDadosDNA(){
		Hashtable<String, List<SequenciaDNA>> sequenciasDNA = new Hashtable<String, List<SequenciaDNA>>();
		List<SequenciaDNA> sequenciasArquivo;
		String  sequencia;
		String descricao;


		for (String arquivo: arquivosDNA){

			//tenta abrir o arquivo passado para pegar os dados
			try {
				scannerSequencia = new Scanner(new FileReader(arquivo))
						.useDelimiter("\\n");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			//cria um ArrayList para adicionar cada uma das sequ�ncias de um arquivo 
			sequenciasArquivo = new ArrayList<SequenciaDNA>();

			//enquanto tem sequencias no formato FASTA
			while (scannerSequencia.hasNext()){
				//pega a descri��o
				descricao = scannerSequencia.next();
				descricao = descricao.substring(1); //retira o s�mbolo '>' da sequ�ncia
				descricao = descricao.trim();

				//pega a sequ�ncia
				sequencia = scannerSequencia.next();
				sequencia = sequencia.toUpperCase(); // p�e todas as letras para mai�scula
				sequencia = sequencia.trim();

				//passa pela linha em branco
				scannerSequencia.next();

				sequenciasArquivo.add(new SequenciaDNA(sequencia, descricao));
			}

			sequenciasDNA.put(arquivo, sequenciasArquivo);

			//fecha o esc�ner
			scannerSequencia.close();
		}

		return sequenciasDNA;
	}

	public static void lerDadosAmino(Dados dados){
		String sequencia;
		List<String> sequenciasAminoacidosArquivo;
		List<String> sequenciasAminoAux;

		List<AlinhamentoAminoacido> alinhamentosArquivo;

		int indice;

		for(String arquivo : arquivosAmino){
			sequenciasAminoacidosArquivo = new ArrayList<String>();
			sequenciasAminoAux = new ArrayList<>();
			alinhamentosArquivo = new ArrayList<AlinhamentoAminoacido>();

			//tenta abrir o arquivo passado para pegar os dados
			try {
				scannerSequencia = new Scanner(new FileReader(arquivo))
						.useDelimiter("\\n");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			//enquanto tem sequencias
			while (scannerSequencia.hasNext()){
				//pega a descri��o
				sequencia = scannerSequencia.next();
				sequencia = sequencia.trim();
				sequencia = sequencia.toUpperCase();
				sequenciasAminoacidosArquivo.add(sequencia);
				sequenciasAminoAux.add(sequencia);
			}

			// cria-se os alinhamentos poss�veis
			for (String sequenciaAArquivo : sequenciasAminoacidosArquivo){
				// remove a sequ�ncia da vez da lista de sequ�ncias
				sequenciasAminoAux.remove(sequenciaAArquivo);

				// isso � para n�o criar Alinhamentos com a mesma seq. 
				// ex.: ALBCVGBBST com ALBCVGBBST
				for (String sequenciaBArquivo : sequenciasAminoAux){
					alinhamentosArquivo.add(new AlinhamentoAminoacido(sequenciaAArquivo,
							sequenciaBArquivo));
				}
			}

			//adiciona a lista de alinhamentos referente ao arquivo
			dados.getAlinhamentosAminoacidos().put(arquivo, alinhamentosArquivo);

			scannerSequencia.close();
		}
	}

	public static void gravarArquivoGCs (Dados dados){
		File arquivo;
		FileWriter fw;
		BufferedWriter gravador;
		
		// guarda o percentual de se encontrar GC naquela posi��o naquela seq
		double percentual;
		
		// guarda a soma da quantidade de vezes que aparece GC na sequencia
		int soma;
		
		DecimalFormat df = new DecimalFormat("##.##");
		try{ 
			arquivo = new File(arquivosSaida[0]);
			fw = new FileWriter(arquivo);
			gravador = new BufferedWriter(fw);
			gravador.write("QUANTIDADE DE GCs NAS SEQU�NCIAS DE DNA");
			gravador.newLine();
			gravador.newLine();
			for (String cor : ManipularArquivos.arquivosDNA){
				gravador.write(cor);
				gravador.newLine();
				gravador.newLine();
				for (int i = 0; i < dados.getSequenciasDNA().get(cor).size(); i++){
					gravador.write(dados.getSequenciasDNA().get(cor).get(i).getDescricao());
					gravador.newLine();
					gravador.write(dados.getSequenciasDNA().get(cor).get(i).getSequencia());
					gravador.newLine();
					gravador.newLine();
					soma = 0;
					for (int j = 0; j < AnalisarDNA.TAMANHO_CODON; j++){
						soma = soma + dados.getSequenciasDNA().get(cor).get(i).getQuantidadeGC()[j];
					}
					
					gravador.write("N�mero de GCs em cada uma das posi��es dos c�dons");
					gravador.newLine();
					for (int j = 0; j < AnalisarDNA.TAMANHO_CODON; j++){
						percentual = dados.getSequenciasDNA().get(cor).get(i).getQuantidadeGC()[j]/
								(double) (dados.getSequenciasDNA().get(cor).get(i).getnBasesEmCadaPosicaoDeTamanhoCodon()[j]) * 100;
						gravador.write((j+1) + "� posi��o: ");
						gravador.write(dados.getSequenciasDNA().get(cor).get(i).getQuantidadeGC()[j]
								+ "      " +  df.format(percentual) + "% de encontrar G ou C nessa posi��o apenas" + " e " + df.format(dados.getSequenciasDNA().get(cor).get(i).getQuantidadeGC()[j]/(double) soma * 100)
								+ "% de encontrar G ou C nessa posi��o na sequ�ncia.");
						gravador.newLine();
					}
					gravador.newLine();
					gravador.newLine();
				}
			}
			gravador.newLine();
			gravador.flush();  
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public static void gravarArquivoAlinhamento (Dados dados){
		File arquivo;
		FileWriter fw;
		BufferedWriter gravador;

		try{
			arquivo = new File(arquivosSaida[1]);
			fw = new FileWriter(arquivo);
			gravador = new BufferedWriter(fw);
			gravador.write("ALINHAMENTO DAS SEQU�NCIAS DE AMINO�CIDOS");
			gravador.newLine();
			gravador.newLine();
			gravador.write("LEGENDA");
			gravador.newLine();
			gravador.write("D: diagonal, H: horizontal e V: vertical.");
			gravador.newLine();
			gravador.write("Obs.: a ordem de leitura do alinhamento est� do fim da matriz (�ltima linha e �ltima coluna) at� acima.");
			gravador.newLine();
			gravador.newLine();
			for (String cor : ManipularArquivos.arquivosAmino){
				gravador.write(cor);
				gravador.newLine();
				gravador.newLine();
				for (int i = 0; i < dados.getAlinhamentosAminoacidos().get(cor).size(); i++){
					gravador.write("Sequ�ncia A: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getSequenciaA());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Sequ�ncia B: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getSequenciaB());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Sequ�ncia A alinhada: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getSequenciaAAlinhada());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Sequ�ncia B alinhada: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getSequenciaBAlinhada());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Melhor alinhamento global: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getVetorAlinhamento());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Encontradas " + dados.getAlinhamentosAminoacidos().get(cor).size() + " regi�es de baixa similaridade.");
					gravador.newLine();
					gravador.newLine();
					for (int j = 0; j < dados.getAlinhamentosAminoacidos().get(cor).get(i).
							getRegioesBaixaSimilaridadeA().size(); j++){
						gravador.write((j+1) + "� regi�o de baixa similaridade na sequ�ncia A: ");
						gravador.write(dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeA().get(j).getRegiao());
						gravador.newLine();
						gravador.write((j+1) + "� regi�o de baixa similaridade na sequ�ncia B: ");
						gravador.write(dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeB().get(j).getRegiao());
						gravador.newLine();
						
						gravador.write("Tamanho da regi�o: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeA().get(j).getRegiao().length());
						gravador.newLine();
						gravador.newLine();

						gravador.write("Quantidade de cada tipo de amino�cido em cada regi�o");
						gravador.newLine();
						for (int k = 0; k < dados.getTiposAminoacidos().length; k++){
							gravador.write(dados.getTiposAminoacidos()[k] + "  para a regi�o da sequ�ncia A: " + dados.getAlinhamentosAminoacidos().get(cor).
									get(i).getRegioesBaixaSimilaridadeA().get(j).getContadorAminoacidosRegiao()[k]);
							gravador.newLine();
							gravador.write(dados.getTiposAminoacidos()[k] + "  para a regi�o da sequ�ncia B: " + dados.getAlinhamentosAminoacidos().get(cor).
									get(i).getRegioesBaixaSimilaridadeB().get(j).getContadorAminoacidosRegiao()[k]);
							gravador.newLine();
							gravador.newLine();
						}
						gravador.write("Amino�cido mais frequente da regi�o na sequ�ncia A � " + 
								dados.getTiposAminoacidos()[dados.getAlinhamentosAminoacidos().get(cor).
								                            get(i).getRegioesBaixaSimilaridadeA().get(j).getIndiceMaiorFrequenciaNaRegiao()]
								                            		+ ", aparecendo " + dados.getAlinhamentosAminoacidos().get(cor).
								                            		get(i).getRegioesBaixaSimilaridadeA().get(j).
								                            		getContadorAminoacidosRegiao()[
								                            		                               dados.getAlinhamentosAminoacidos().get(cor).get(i).
								                            		                               getRegioesBaixaSimilaridadeA().get(j).getIndiceMaiorFrequenciaNaRegiao()] + " vezes.\n");
						gravador.newLine();
						gravador.write("Amino�cido mais frequente da regi�o na sequ�ncia B � " +
								dados.getTiposAminoacidos()[dados.getAlinhamentosAminoacidos().get(cor).
								                            get(i).getRegioesBaixaSimilaridadeB().get(j).
								                            getIndiceMaiorFrequenciaNaRegiao()] +
								", aparecendo " + dados.getAlinhamentosAminoacidos().get(cor).
								get(i).getRegioesBaixaSimilaridadeB().get(j).getContadorAminoacidosRegiao()[
								                                                                            dados.getAlinhamentosAminoacidos().get(cor).get(i).getRegioesBaixaSimilaridadeB().get(j).
								                                                                            getIndiceMaiorFrequenciaNaRegiao()] + " vezes.\n");
						gravador.newLine();
						gravador.newLine();
						gravador.write("Amino�cido vizinho de " + 
								dados.getTiposAminoacidos()[dados.getAlinhamentosAminoacidos().get(cor).get(i).
								                            getRegioesBaixaSimilaridadeA().get(j).
								                            getIndiceMaiorFrequenciaNaRegiao()] + " mais frequente na regi�o na sequ�ncia A:");
						gravador.newLine();
						gravador.write("Lado direito: " + dados.getTiposAminoacidos()[maior(dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeA().get(j).getContadorVizinhosADireitaAminoacidoDeMaiorFrequencia())]
										+ "       Lado esquerdo: " + dados.getTiposAminoacidos()[maior(dados.getAlinhamentosAminoacidos().get(cor).get(i).
												getRegioesBaixaSimilaridadeA().get(j).getContadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia())]);
						gravador.newLine();
						gravador.newLine();
						gravador.write("Amino�cido vizinho de " + 
								dados.getTiposAminoacidos()[dados.getAlinhamentosAminoacidos().get(cor).get(i).
								                            getRegioesBaixaSimilaridadeB().get(j).
								                            getIndiceMaiorFrequenciaNaRegiao()] + " mais frequente na regi�o na sequ�ncia B:");
						gravador.newLine();
						gravador.write("Lado direito: " + dados.getTiposAminoacidos()[maior(dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeB().get(j).getContadorVizinhosADireitaAminoacidoDeMaiorFrequencia())]
										+ "       Lado esquerdo: " + dados.getTiposAminoacidos()[maior(dados.getAlinhamentosAminoacidos().get(cor).get(i).
												getRegioesBaixaSimilaridadeB().get(j).getContadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia())]);
						gravador.newLine();
						gravador.newLine();

						gravador.write("An�lise de pareamento entre as regi�es da sequ�ncia A e da sequ�ncia B");
						gravador.newLine();
						gravador.newLine();
						String aux;
						int iteradorPareamento = dados.getTamanhoMinimoPareamento();
						while (iteradorPareamento <= dados.getTamanhoMaximoPareamento()){
							if (iteradorPareamento < dados.getAlinhamentosAminoacidos().get(cor).get(i).
									getRegioesBaixaSimilaridadeA().get(j).getRegiao().length()){
								gravador.write("Tamanho " + iteradorPareamento + ": ");
								gravador.newLine();
								for (int k = 0; k < dados.getAlinhamentosAminoacidos().get(cor).get(i).
										getRegioesBaixaSimilaridadeA().get(j).getRegiao().length()
										- (iteradorPareamento - 1); k++ ){
									aux = dados.getAlinhamentosAminoacidos().get(cor).get(i).getRegioesBaixaSimilaridadeA().get(j).getRegiao()
											.substring(k, k + iteradorPareamento);

									if (iteradorPareamento <= 3 && dados.getAlinhamentosAminoacidos().get(cor).get(i).
											getRegioesBaixaSimilaridadeA().get(j).getPareamentoTamanho3Inferior() != null){
										gravador.write(aux + ":   " + dados.getAlinhamentosAminoacidos().get(cor).get(i).
												getRegioesBaixaSimilaridadeA().get(j).getPareamentoTamanho3Inferior().get(aux) + "  ");
									} else if (iteradorPareamento == 4 && dados.getAlinhamentosAminoacidos().get(cor).get(i).
											getRegioesBaixaSimilaridadeA().get(j).getPareamentoTamanho4() != null){
										gravador.write(aux + ":   " + dados.getAlinhamentosAminoacidos().get(cor).get(i).
												getRegioesBaixaSimilaridadeA().get(j).getPareamentoTamanho4().get(aux) + "  ");
									} else if (iteradorPareamento == 5 && dados.getAlinhamentosAminoacidos().get(cor).get(i).
											getRegioesBaixaSimilaridadeA().get(j).getPareamentoTamanho5() != null) {
										gravador.write(aux + ":   " + dados.getAlinhamentosAminoacidos().get(cor).get(i).
												getRegioesBaixaSimilaridadeA().get(j).getPareamentoTamanho5().get(aux) + "  ");
									} else if (iteradorPareamento >= 6 && dados.getAlinhamentosAminoacidos().get(cor).get(i).
											getRegioesBaixaSimilaridadeA().get(j).getPareamentoTamanho6Superior() != null) {
										gravador.write(aux + ":   " + dados.getAlinhamentosAminoacidos().get(cor).get(i).
												getRegioesBaixaSimilaridadeA().get(j).getPareamentoTamanho6Superior().get(aux) + "  ");
									}
								}
								gravador.newLine();
								gravador.newLine();
							}
							iteradorPareamento++;
						}
					}
				}
				gravador.flush();
			}
			//gravador.flush();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static int maior(int[] a){
		int max = a[0];
		int indice = 0;
		for(int i = 1; i < a.length; i++){
			if(a[i] > max){
				max = a[i];
				indice = i;
			}
		}

		return indice;
	}
}