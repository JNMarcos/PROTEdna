/**
 * 
 */
package console;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author JN
 *
 */
public class LerArquivos {

	public static final String[] arquivos = {"Amarelo.txt", "Azul.txt", "Verde.txt", "Rosa.txt",
			"Lil�s.txt", "Marrom.txt", "Vermelho.txt"};

	private static Scanner scannerSequencia = null;

	public static HashMap<String, List<String>> lerDados(){
		HashMap<String, List<String>> sequenciasDNA = new HashMap<String, List<String>>();
		List<String> sequenciasArquivo = new ArrayList<String>();
		String  sequencia;
		
		
		for (String arquivo: arquivos){
			
			//tenta abrir o arquivo passado para pegar os dados
			try {
				scannerSequencia = new Scanner(new FileReader(arquivo))
						.useDelimiter("\\n");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			//enquanto tem fasta
			while (scannerSequencia.hasNext()){
				sequencia = scannerSequencia.next();
				sequencia = sequencia.toUpperCase(); // p�e todas as letras para mai�scula
				sequencia = sequencia.trim();
				//diz o �ndice que se deve come�ar pegar
				sequencia = sequencia.substring(1); //retira o s�mbolo '>' da sequ�ncia
				sequenciasArquivo.add(sequencia);			
			}
			
			sequenciasDNA.put(arquivo, sequenciasArquivo);	
			
			sequenciasArquivo.clear();
			//fecha o esc�ner
			scannerSequencia.close();
		}
		
		return sequenciasDNA;
	}
}
