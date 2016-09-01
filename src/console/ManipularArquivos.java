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
			"Lilás.txt", "Marrom.txt", "Vermelho.txt"};

	public static final String[] arquivosAmino = {"AmareloAmino.txt", "AzulAmino.txt","LilásAmino.txt",
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

			//cria um ArrayList para adicionar cada uma das sequências de um arquivo 
			sequenciasArquivo = new ArrayList<SequenciaDNA>();

			//enquanto tem sequencias no formato FASTA
			while (scannerSequencia.hasNext()){
				//pega a descrição
				descricao = scannerSequencia.next();
				descricao = descricao.substring(1); //retira o símbolo '>' da sequência
				descricao = descricao.trim();

				//pega a sequência
				sequencia = scannerSequencia.next();
				sequencia = sequencia.toUpperCase(); // põe todas as letras para maiúscula
				sequencia = sequencia.trim();

				//passa pela linha em branco
				scannerSequencia.next();

				sequenciasArquivo.add(new SequenciaDNA(sequencia, descricao));
			}

			sequenciasDNA.put(arquivo, sequenciasArquivo);

			//fecha o escâner
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
				//pega a descrição
				sequencia = scannerSequencia.next();
				sequencia = sequencia.trim();
				sequencia = sequencia.toUpperCase();
				sequenciasAminoacidosArquivo.add(sequencia);
				sequenciasAminoAux.add(sequencia);
			}

			// cria-se os alinhamentos possíveis
			for (String sequenciaAArquivo : sequenciasAminoacidosArquivo){
				// remove a sequência da vez da lista de sequências
				sequenciasAminoAux.remove(sequenciaAArquivo);

				// isso é para não criar Alinhamentos com a mesma seq. 
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
		
		// guarda o percentual de se encontrar GC naquela posição naquela seq
		double percentual;
		
		// guarda a soma da quantidade de vezes que aparece GC na sequencia
		int soma;
		
		DecimalFormat df = new DecimalFormat("##.##");
		try{ 
			arquivo = new File(arquivosSaida[0]);
			fw = new FileWriter(arquivo);
			gravador = new BufferedWriter(fw);
			gravador.write("QUANTIDADE DE GCs NAS SEQUÊNCIAS DE DNA");
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
					
					gravador.write("Número de GCs em cada uma das posições dos códons");
					gravador.newLine();
					for (int j = 0; j < AnalisarDNA.TAMANHO_CODON; j++){
						percentual = dados.getSequenciasDNA().get(cor).get(i).getQuantidadeGC()[j]/
								(double) (dados.getSequenciasDNA().get(cor).get(i).getnBasesEmCadaPosicaoDeTamanhoCodon()[j]) * 100;
						gravador.write((j+1) + "ª posição: ");
						gravador.write(dados.getSequenciasDNA().get(cor).get(i).getQuantidadeGC()[j]
								+ "      " +  df.format(percentual) + "% de encontrar G ou C nessa posição apenas" + " e " + df.format(dados.getSequenciasDNA().get(cor).get(i).getQuantidadeGC()[j]/(double) soma * 100)
								+ "% de encontrar G ou C nessa posição na sequência.");
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
			gravador.write("ALINHAMENTO DAS SEQUÊNCIAS DE AMINOÁCIDOS");
			gravador.newLine();
			gravador.newLine();
			gravador.write("LEGENDA");
			gravador.newLine();
			gravador.write("D: diagonal, H: horizontal e V: vertical.");
			gravador.newLine();
			gravador.write("Obs.: a ordem de leitura do alinhamento está do fim da matriz (última linha e última coluna) até acima.");
			gravador.newLine();
			gravador.newLine();
			for (String cor : ManipularArquivos.arquivosAmino){
				gravador.write(cor);
				gravador.newLine();
				gravador.newLine();
				for (int i = 0; i < dados.getAlinhamentosAminoacidos().get(cor).size(); i++){
					gravador.write("Sequência A: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getSequenciaA());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Sequência B: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getSequenciaB());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Sequência A alinhada: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getSequenciaAAlinhada());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Sequência B alinhada: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getSequenciaBAlinhada());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Melhor alinhamento global: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).getVetorAlinhamento());
					gravador.newLine();
					gravador.newLine();
					gravador.write("Encontradas " + dados.getAlinhamentosAminoacidos().get(cor).size() + " regiões de baixa similaridade.");
					gravador.newLine();
					gravador.newLine();
					for (int j = 0; j < dados.getAlinhamentosAminoacidos().get(cor).get(i).
							getRegioesBaixaSimilaridadeA().size(); j++){
						gravador.write((j+1) + "ª região de baixa similaridade na sequência A: ");
						gravador.write(dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeA().get(j).getRegiao());
						gravador.newLine();
						gravador.write((j+1) + "ª região de baixa similaridade na sequência B: ");
						gravador.write(dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeB().get(j).getRegiao());
						gravador.newLine();
						
						gravador.write("Tamanho da região: " + dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeA().get(j).getRegiao().length());
						gravador.newLine();
						gravador.newLine();

						gravador.write("Quantidade de cada tipo de aminoácido em cada região");
						gravador.newLine();
						for (int k = 0; k < dados.getTiposAminoacidos().length; k++){
							gravador.write(dados.getTiposAminoacidos()[k] + "  para a região da sequência A: " + dados.getAlinhamentosAminoacidos().get(cor).
									get(i).getRegioesBaixaSimilaridadeA().get(j).getContadorAminoacidosRegiao()[k]);
							gravador.newLine();
							gravador.write(dados.getTiposAminoacidos()[k] + "  para a região da sequência B: " + dados.getAlinhamentosAminoacidos().get(cor).
									get(i).getRegioesBaixaSimilaridadeB().get(j).getContadorAminoacidosRegiao()[k]);
							gravador.newLine();
							gravador.newLine();
						}
						gravador.write("Aminoácido mais frequente da região na sequência A é " + 
								dados.getTiposAminoacidos()[dados.getAlinhamentosAminoacidos().get(cor).
								                            get(i).getRegioesBaixaSimilaridadeA().get(j).getIndiceMaiorFrequenciaNaRegiao()]
								                            		+ ", aparecendo " + dados.getAlinhamentosAminoacidos().get(cor).
								                            		get(i).getRegioesBaixaSimilaridadeA().get(j).
								                            		getContadorAminoacidosRegiao()[
								                            		                               dados.getAlinhamentosAminoacidos().get(cor).get(i).
								                            		                               getRegioesBaixaSimilaridadeA().get(j).getIndiceMaiorFrequenciaNaRegiao()] + " vezes.\n");
						gravador.newLine();
						gravador.write("Aminoácido mais frequente da região na sequência B é " +
								dados.getTiposAminoacidos()[dados.getAlinhamentosAminoacidos().get(cor).
								                            get(i).getRegioesBaixaSimilaridadeB().get(j).
								                            getIndiceMaiorFrequenciaNaRegiao()] +
								", aparecendo " + dados.getAlinhamentosAminoacidos().get(cor).
								get(i).getRegioesBaixaSimilaridadeB().get(j).getContadorAminoacidosRegiao()[
								                                                                            dados.getAlinhamentosAminoacidos().get(cor).get(i).getRegioesBaixaSimilaridadeB().get(j).
								                                                                            getIndiceMaiorFrequenciaNaRegiao()] + " vezes.\n");
						gravador.newLine();
						gravador.newLine();
						gravador.write("Aminoácido vizinho de " + 
								dados.getTiposAminoacidos()[dados.getAlinhamentosAminoacidos().get(cor).get(i).
								                            getRegioesBaixaSimilaridadeA().get(j).
								                            getIndiceMaiorFrequenciaNaRegiao()] + " mais frequente na região na sequência A:");
						gravador.newLine();
						gravador.write("Lado direito: " + dados.getTiposAminoacidos()[maior(dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeA().get(j).getContadorVizinhosADireitaAminoacidoDeMaiorFrequencia())]
										+ "       Lado esquerdo: " + dados.getTiposAminoacidos()[maior(dados.getAlinhamentosAminoacidos().get(cor).get(i).
												getRegioesBaixaSimilaridadeA().get(j).getContadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia())]);
						gravador.newLine();
						gravador.newLine();
						gravador.write("Aminoácido vizinho de " + 
								dados.getTiposAminoacidos()[dados.getAlinhamentosAminoacidos().get(cor).get(i).
								                            getRegioesBaixaSimilaridadeB().get(j).
								                            getIndiceMaiorFrequenciaNaRegiao()] + " mais frequente na região na sequência B:");
						gravador.newLine();
						gravador.write("Lado direito: " + dados.getTiposAminoacidos()[maior(dados.getAlinhamentosAminoacidos().get(cor).get(i).
								getRegioesBaixaSimilaridadeB().get(j).getContadorVizinhosADireitaAminoacidoDeMaiorFrequencia())]
										+ "       Lado esquerdo: " + dados.getTiposAminoacidos()[maior(dados.getAlinhamentosAminoacidos().get(cor).get(i).
												getRegioesBaixaSimilaridadeB().get(j).getContadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia())]);
						gravador.newLine();
						gravador.newLine();

						gravador.write("Análise de pareamento entre as regiões da sequência A e da sequência B");
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