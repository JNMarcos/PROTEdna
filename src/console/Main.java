/**
 * 
 */
package console;

import java.util.HashMap;
import java.util.List;

import desenvolvimento.AnalisarDNA;
import desenvolvimento.SequenciaDNA;

/**
 * @author JN
 *
 */
public class Main {

	public static void main(String[] args){
		
		HashMap<String, List<SequenciaDNA>> sequenciasDNA = ManipularArquivos.lerDados();
		
		Dados dados = new Dados(sequenciasDNA);
		
		//desmarcando, prova que as sequências estão realmente agrupadas por arquivos
		/*for (String cor : LerArquivos.arquivos){
			for (int i = 0; i < dados.getSequenciasDNA().get(cor).size(); i++){
				System.out.println( cor + "   " + dados.getSequenciasDNA().get(cor).get(i).getSequencia());
			}
		}*/
		AnalisarDNA analiseDNA = new AnalisarDNA(dados);
		
		analiseDNA.analisar();
		/*
		//passar analiseDNA para o blastx
		*/
		
		//algoritmo para contar
		/*
		System.out.println(7984984);
		String a = "KSJDAKDJKDASADJDAIIKJKKJJKATATA";
		int c = a.split("A").length;
		if (a.charAt(a.length() - 1) == 'A'){
			System.out.println(c);
		} else{
		System.out.println(c - 1);
		}
		*/
	}
}
