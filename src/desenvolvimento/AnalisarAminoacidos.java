/**
 * 
 */
package desenvolvimento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import console.Dados;
import console.ManipularArquivos;

/**
 * @author JN
 *
 */
public class AnalisarAminoacidos {
	private Dados dados;
	
	
	public AnalisarAminoacidos(Dados dados){
		this.dados = dados;
		this.dados.setAlinhamentosAminoacidos(new HashMap<String, List<AlinhamentoAminoacido>>());
	}
	
	public void tranformarDNAemAminoacidos(){
		// usada para armazenar uma seq de aminoácido
		String sequenciaAminoacido;
		
		//contém uma lista das sequências de aminoácidos, é provisória, são strings
		List<String> sequenciasAminoacidosArquivo;
				
		//contém alinhamentos entre sequências de um arquivo, um objeto Alinham...
		List<AlinhamentoAminoacido> alinhamentosArquivo;
		
		for (String cor : ManipularArquivos.arquivos){
			
			// cria-se uma lista vazia para armazenar as sequencias e alinhamentos entre sequências de um
			// arquivo
			sequenciasAminoacidosArquivo = new ArrayList<String>();
			alinhamentosArquivo = new ArrayList<AlinhamentoAminoacido>();
			
			// for para 'transformar' cada seq DNA em seq aminoacido
			for (SequenciaDNA sequencia : dados.getSequenciasDNA().get(cor)){
				// passa a sequência para o blastx
				// tem de fazer uma conexão, ou tem de pegar por um arquivo a ser criado
				// obtém-se as respostas, após isso
				
				// sequenciaAminoacido recebe aqui a sequência após o BLASTX
				
				// a sequência obtida é adicionada na lista de seq de aminoácidos do arquivo
				sequenciasAminoacidosArquivo.add(sequenciaAminoacido);				
			}
			
			// cria-se os alinhamentos possíveis
			for (String sequenciaArquivoA : sequenciasAminoacidosArquivo){
				// remove a sequência da vez da lista de sequências
				sequenciasAminoacidosArquivo.remove(sequenciaArquivoA);
				
				// isso é para não criar Alinhamentos com a mesma seq. 
				// ex.: ALBCVGBBST com ALBCVGBBST
				for (String sequenciaArquivoB : sequenciasAminoacidosArquivo){
					alinhamentosArquivo.add(new AlinhamentoAminoacido(sequenciaArquivoA,
							sequenciaArquivoB));
				}
			}
			
			//adiciona a lista de alinhamentos referente ao arquivo
			dados.getAlinhamentosAminoacidos().put(cor, alinhamentosArquivo);
		}
	}
	
	public void obterAlinhamento(){
		for (String cor : ManipularArquivos.arquivos){
			for (AlinhamentoAminoacido alinhamento : dados.getAlinhamentosAminoacidos().get(cor)){
				// passa as duas sequências dentro de alinhamento e
				// alinha no blast
				
				// obtém as duas sequências alinhadas e coloca no próprio objeto alinhamento
			}
		}
	}
	
	public void analisarAlinhamentos(){
		for (String cor : ManipularArquivos.arquivos){
			for (AlinhamentoAminoacido alinhamento : dados.getAlinhamentosAminoacidos().get(cor)){
				
			}
		}
	}
}
