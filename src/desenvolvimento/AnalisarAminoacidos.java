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
		// usada para armazenar uma seq de amino�cido
		String sequenciaAminoacido;
		
		//cont�m uma lista das sequ�ncias de amino�cidos, � provis�ria, s�o strings
		List<String> sequenciasAminoacidosArquivo;
				
		//cont�m alinhamentos entre sequ�ncias de um arquivo, um objeto Alinham...
		List<AlinhamentoAminoacido> alinhamentosArquivo;
		
		for (String cor : ManipularArquivos.arquivos){
			
			// cria-se uma lista vazia para armazenar as sequencias e alinhamentos entre sequ�ncias de um
			// arquivo
			sequenciasAminoacidosArquivo = new ArrayList<String>();
			alinhamentosArquivo = new ArrayList<AlinhamentoAminoacido>();
			
			// for para 'transformar' cada seq DNA em seq aminoacido
			for (SequenciaDNA sequencia : dados.getSequenciasDNA().get(cor)){
				// passa a sequ�ncia para o blastx
				// tem de fazer uma conex�o, ou tem de pegar por um arquivo a ser criado
				// obt�m-se as respostas, ap�s isso
				
				// sequenciaAminoacido recebe aqui a sequ�ncia ap�s o BLASTX
				
				// a sequ�ncia obtida � adicionada na lista de seq de amino�cidos do arquivo
				sequenciasAminoacidosArquivo.add(sequenciaAminoacido);				
			}
			
			// cria-se os alinhamentos poss�veis
			for (String sequenciaArquivoA : sequenciasAminoacidosArquivo){
				// remove a sequ�ncia da vez da lista de sequ�ncias
				sequenciasAminoacidosArquivo.remove(sequenciaArquivoA);
				
				// isso � para n�o criar Alinhamentos com a mesma seq. 
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
				// passa as duas sequ�ncias dentro de alinhamento e
				// alinha no blast
				
				// obt�m as duas sequ�ncias alinhadas e coloca no pr�prio objeto alinhamento
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
