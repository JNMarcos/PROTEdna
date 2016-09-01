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
		// usado para setar informa��es quanto ao alinhamento
		int tamanhoMinimoPareamento = 1;
		int tamanhoMaximoPareamento = 2;
		int tamanhoMinimoRegiaoBaixaSimilaridade;

		boolean estaTudoOK = false;
		Scanner entrada = new Scanner(System.in);
		
		/*
		 * NUNCA ponha o TAMANHO MINIMO como zero nem muito menos negativo
		 * N�o faz sentido
		 * E DAR ERRO
		 * N�o ponha tb igual a UM
		 * pois se � uma regi�o de baixa similaridade
		 * ent�o ao menos um � diferente
		 * e se s� tem, ent�o n�o h� como fazer o pareamento
		 */
		final int TAMANHO_MINIMO_REGIAO_BAIXA_SIMILARIDADE = 3;
		
		
		do{
			System.out.print("____________________PROTEdna_____________________\n");
			System.out.print("\n\nInsira o tamanho m�nimo de uma regi�o de baixa similaridade: ");
			tamanhoMinimoRegiaoBaixaSimilaridade = entrada.nextInt();

			if (tamanhoMinimoRegiaoBaixaSimilaridade >= TAMANHO_MINIMO_REGIAO_BAIXA_SIMILARIDADE){
				do{
					System.out.println("\n\nInsira o tamanho m�nimo do pareamento entre regi�es correspondentes: ");
					tamanhoMinimoPareamento = entrada.nextInt();

					if (tamanhoMinimoPareamento < tamanhoMinimoRegiaoBaixaSimilaridade && 
							tamanhoMinimoPareamento > 0){
						do {
							System.out.println("\n\nInsira o tamanho m�ximo do pareamento entre regi�es correspondentes: ");
							tamanhoMaximoPareamento = entrada.nextInt();

							if (tamanhoMaximoPareamento >= tamanhoMinimoPareamento){
								estaTudoOK = true;
							} else {
								System.out.println("\nO tamanho m�ximo de um pareamento entre regi�es deve ser maior ou igual ao tamanho m�nimo de um pareamento entre regi�es. \nInsira um valor que siga a instru��o acima.");
							}
						} while(!estaTudoOK);
					} else {
						System.out.println("\nO tamanho m�nimo de um pareamento entre regi�es deve ser menor que o tamanho m�nimo de uma regi�o de baixa similaridade.\nInsira um valor que siga a instru��o acima.");
					}

				} while (!estaTudoOK);
			} else {
				System.out.println("\nO tamanho m�nimo de uma regi�o de baixa similaridade deve ser " + TAMANHO_MINIMO_REGIAO_BAIXA_SIMILARIDADE +  ".\nInsira um valor que siga a instru��o acima.\n\n");
			}
		} while (!estaTudoOK);
		
		final String enderecoAminoacido = 
				"http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastx&BLAST_PROGRAMS=blastx&PAGE_TYPE=BlastSearch&SHOW_DEFAULTS=on&LINK_LOC=blasthome";

		final String enderecoProteina =
				"http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastp&PAGE_TYPE=BlastSearch&BLAST_SPEC=&LINK_LOC=blasttab";

		System.out.println("\n\n\n\nLendo dados das sequ�ncias de DNA.");
		Hashtable<String, List<SequenciaDNA>> sequenciasDNA = ManipularArquivos.lerDadosDNA();
		System.out.println("Encerrada a leitura das sequ�ncias de DNA.");

		Dados dados = new Dados(sequenciasDNA);
		dados.setTamanhoMinimoRegiaoBaixaSimilaridade(tamanhoMinimoRegiaoBaixaSimilaridade);
		dados.setTamanhoMinimoPareamento(tamanhoMinimoPareamento);
		dados.setTamanhoMaximoPareamento(tamanhoMaximoPareamento);

		AnalisarDNA analiseDNA = new AnalisarDNA(dados);

		System.out.println("An�lise das sequ�ncias de DNA iniciada.");
		analiseDNA.analisar();
		System.out.println("An�lise das sequ�ncias de DNA terminada com sucesso.");
		System.out.println("Grava��o de resultados em arquivo referente � an�lise de DNAs iniciada.");
		ManipularArquivos.gravarArquivoGCs(dados);
		System.out.println("Grava��o de resultados em arquivo referente � an�lise de DNAs encerrada.");
		System.out.println("Iniciando leitura das sequ�ncias de amino�cidos e alinhamento.");
		ManipularArquivos.lerDadosAmino(dados);
		System.out.println("Encerrada a leitura das sequ�ncias de amino�cidos e alinhamento.");

		//algoritmo para contar

		AnalisarAminoacidos analisarAmino = new AnalisarAminoacidos(dados, enderecoAminoacido, enderecoProteina);
		System.out.println("Iniciando o alinhamento de sequ�ncias.");
		analisarAmino.obterAlinhamento();
		System.out.println("Encerrado o alinhamento de sequ�ncias.");
		System.out.println("Iniciando a an�lise das sequ�ncias alinhadas de amino�cidos.");
		analisarAmino.analisar();
		System.out.println("An�lise das sequ�ncias alinhadas de amino�cidos terminada com sucesso.");
		System.out.println("Grava��o de resultados em arquivo referente � an�lise de alinhamento iniciada.");
		ManipularArquivos.gravarArquivoAlinhamento(dados);
		System.out.println("Grava��o de resultados em arquivo referente � an�lise de alinhamento encerrada.");
		System.out.println("----------------------------------ENCERRADO------------------------------------");
	}
}
