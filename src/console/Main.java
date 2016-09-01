/**
 * 
 */
package console;

import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import desenvolvimento.AnalisarAminoacidos;
import desenvolvimento.AnalisarDNA;
import desenvolvimento.Dados;
import desenvolvimento.SequenciaDNA;

/**
 * @author JN
 *
 */
public class Main {

	public static void main(String[] args){
		// usado para setar informações quanto ao alinhamento
		int tamanhoMinimoPareamento = 1;
		int tamanhoMaximoPareamento = 2;
		int tamanhoMinimoRegiaoBaixaSimilaridade;

		boolean estaTudoOK = false;
		Scanner entrada = new Scanner(System.in);
		
		/*
		 * NUNCA ponha o TAMANHO MINIMO como zero nem muito menos negativo
		 * Não faz sentido
		 * E DAR ERRO
		 * Não ponha tb igual a UM
		 * pois se é uma região de baixa similaridade
		 * então ao menos um é diferente
		 * e se só tem, então não há como fazer o pareamento
		 */
		final int TAMANHO_MINIMO_REGIAO_BAIXA_SIMILARIDADE = 3;
		
		
		do{
			System.out.print("____________________PROTEdna_____________________\n");
			System.out.print("\n\nInsira o tamanho mínimo de uma região de baixa similaridade: ");
			tamanhoMinimoRegiaoBaixaSimilaridade = entrada.nextInt();

			if (tamanhoMinimoRegiaoBaixaSimilaridade >= TAMANHO_MINIMO_REGIAO_BAIXA_SIMILARIDADE){
				do{
					System.out.println("\n\nInsira o tamanho mínimo do pareamento entre regiões correspondentes: ");
					tamanhoMinimoPareamento = entrada.nextInt();

					if (tamanhoMinimoPareamento < tamanhoMinimoRegiaoBaixaSimilaridade && 
							tamanhoMinimoPareamento > 0){
						do {
							System.out.println("\n\nInsira o tamanho máximo do pareamento entre regiões correspondentes: ");
							tamanhoMaximoPareamento = entrada.nextInt();

							if (tamanhoMaximoPareamento >= tamanhoMinimoPareamento){
								estaTudoOK = true;
							} else {
								System.out.println("\nO tamanho máximo de um pareamento entre regiões deve ser maior ou igual ao tamanho mínimo de um pareamento entre regiões. \nInsira um valor que siga a instrução acima.");
							}
						} while(!estaTudoOK);
					} else {
						System.out.println("\nO tamanho mínimo de um pareamento entre regiões deve ser menor que o tamanho mínimo de uma região de baixa similaridade.\nInsira um valor que siga a instrução acima.");
					}

				} while (!estaTudoOK);
			} else {
				System.out.println("\nO tamanho mínimo de uma região de baixa similaridade deve ser " + TAMANHO_MINIMO_REGIAO_BAIXA_SIMILARIDADE +  ".\nInsira um valor que siga a instrução acima.\n\n");
			}
		} while (!estaTudoOK);
		
		final String enderecoAminoacido = 
				"http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastx&BLAST_PROGRAMS=blastx&PAGE_TYPE=BlastSearch&SHOW_DEFAULTS=on&LINK_LOC=blasthome";

		final String enderecoProteina =
				"http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastp&PAGE_TYPE=BlastSearch&BLAST_SPEC=&LINK_LOC=blasttab";

		System.out.println("\n\n\n\nLendo dados das sequências de DNA.");
		Hashtable<String, List<SequenciaDNA>> sequenciasDNA = ManipularArquivos.lerDadosDNA();
		System.out.println("Encerrada a leitura das sequências de DNA.");

		Dados dados = new Dados(sequenciasDNA);
		dados.setTamanhoMinimoRegiaoBaixaSimilaridade(tamanhoMinimoRegiaoBaixaSimilaridade);
		dados.setTamanhoMinimoPareamento(tamanhoMinimoPareamento);
		dados.setTamanhoMaximoPareamento(tamanhoMaximoPareamento);

		AnalisarDNA analiseDNA = new AnalisarDNA(dados);

		System.out.println("Análise das sequências de DNA iniciada.");
		analiseDNA.analisar();
		System.out.println("Análise das sequências de DNA terminada com sucesso.");
		System.out.println("Gravação de resultados em arquivo referente à análise de DNAs iniciada.");
		ManipularArquivos.gravarArquivoGCs(dados);
		System.out.println("Gravação de resultados em arquivo referente à análise de DNAs encerrada.");
		System.out.println("Iniciando leitura das sequências de aminoácidos e alinhamento.");
		ManipularArquivos.lerDadosAmino(dados);
		System.out.println("Encerrada a leitura das sequências de aminoácidos e alinhamento.");

		//algoritmo para contar

		AnalisarAminoacidos analisarAmino = new AnalisarAminoacidos(dados, enderecoAminoacido, enderecoProteina);
		System.out.println("Iniciando o alinhamento de sequências.");
		analisarAmino.obterAlinhamento();
		System.out.println("Encerrado o alinhamento de sequências.");
		System.out.println("Iniciando a análise das sequências alinhadas de aminoácidos.");
		analisarAmino.analisar();
		System.out.println("Análise das sequências alinhadas de aminoácidos terminada com sucesso.");
		System.out.println("Gravação de resultados em arquivo referente à análise de alinhamento iniciada.");
		ManipularArquivos.gravarArquivoAlinhamento(dados);
		System.out.println("Gravação de resultados em arquivo referente à análise de alinhamento encerrada.");
		System.out.println("----------------------------------ENCERRADO------------------------------------");
	}
}
