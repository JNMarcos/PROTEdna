/**
 * 
 */
package console;

import java.util.HashMap;
import java.util.List;

import desenvolvimento.AnalisarDNA;

/**
 * @author JN
 *
 */
public class Main {

	public static void main(String[] args){
		
		HashMap<String, List<String>> sequenciasDNA = LerArquivos.lerDados();
		
		Dados dados = new Dados(sequenciasDNA);
		
		AnalisarDNA analiseDNA = new AnalisarDNA(dados);
		analiseDNA.analisar();
		
		//passar analiseDNA para o blastx
	}
}
