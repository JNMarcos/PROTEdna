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

import desenvolvimento.SequenciaDNA;

/**
 * @author JN
 *
 */
public class ManipularArquivos {

	public static final String[] arquivos = {"Amarelo.txt", "Azul.txt", "Verde.txt", "Rosa.txt",
			"Lil�s.txt", "Marrom.txt", "Vermelho.txt"};

	private static Scanner scannerSequencia = null;

	public static HashMap<String, List<SequenciaDNA>> lerDados(){
		HashMap<String, List<SequenciaDNA>> sequenciasDNA = new HashMap<String, List<SequenciaDNA>>();
		List<SequenciaDNA> sequenciasArquivo;
		String  sequencia;
		String descricao;
		
		
		for (String arquivo: arquivos){
			
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
}
