/**
 * 
 */
package desenvolvimento;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author JN
 *
 */
public class Alinhar {
	private AlinhamentoAminoacido alinhamento;
	private int[][] matriz;
	private Hashtable<String, Integer> sistemaPontuacao;
	private ArrayList<Character> vetorCaminho;
	private int posX;
	private int posY;
	
	public Alinhar(AlinhamentoAminoacido alinhamento) {
		this.setAlinhamento(alinhamento);
		this.setMatriz(new int[alinhamento.getSequenciaA().length() + 1][alinhamento.getSequenciaB().length() + 1]);
		this.setSistemaPontuacao(new Hashtable<>());
		alinhamento.setVetorAlinhamento("");
	}

	public AlinhamentoAminoacido getAlinhamento() {
		return alinhamento;
	}

	public void setAlinhamento(AlinhamentoAminoacido alinhamento) {
		this.alinhamento = alinhamento;
	}

	public int[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(int[][] matriz) {
		this.matriz = matriz;
	}

	public Hashtable<String, Integer> getSistemaPontuacao() {
		return sistemaPontuacao;
	}

	public void setSistemaPontuacao(Hashtable<String, Integer> sistemaPontuacao) {
		this.sistemaPontuacao = sistemaPontuacao;
		sistemaPontuacao.put("gap", -2);
		sistemaPontuacao.put("match", 2);
		sistemaPontuacao.put("mismatch", -1);
	}

	public ArrayList<Character> getVetorCaminho() {
		return vetorCaminho;
	}

	public void setVetorCaminho(ArrayList<Character> vetorCaminho) {
		this.vetorCaminho = vetorCaminho;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void inicializarArrayList(){
		int linha = alinhamento.getSequenciaA().length() + 1;
		int coluna = alinhamento.getSequenciaB().length() + 1;
		this.vetorCaminho = new ArrayList<Character>(linha*coluna);
		for (int k = 0; k < linha; k++){
			for (int l = 0; l < coluna; l++){
				vetorCaminho.add('T');// letra qualquer
			}
		}
	}
	public void inicializarMatriz() {
		matriz[0][0] = 0;
		for (int i = 1; i < alinhamento.getSequenciaA().length() + 1; i++){
			matriz[i][0] = i * this.getSistemaPontuacao().get("gap");
			//tira uma posição pois no ArraYlist começa a se contar por zero
			// não como pelo cálculo
			getVetorCaminho().set(calcularPosicaoMatriz(i, 0) - 1, 'V');
		}

		for (int j = 1; j < alinhamento.getSequenciaB().length() + 1; j++){
			matriz[0][j] = j * this.getSistemaPontuacao().get("gap");
			//tira uma posição pois no ArraYlist começa a se contar por zero
			// não como pelo cálculo
			getVetorCaminho().set(calcularPosicaoMatriz(0, j) - 1, 'H');
		}
	}

	public int calcularMaximo(int horizontal, int vertical, int diagonal,  
			int posicaoArrayList) {
		int maximo;
		//precedência D>H>V
		//o igual garante a precedência requerida no projeto
		if ((horizontal >= vertical) && (horizontal > diagonal)){
			maximo = horizontal;
			//adiciona de onde veio o máximo
			getVetorCaminho().set(posicaoArrayList,'H');
		} else if (vertical > diagonal){
			maximo = vertical;
			getVetorCaminho().set(posicaoArrayList,'V');
		} else{
			maximo = diagonal;
			getVetorCaminho().set(posicaoArrayList,'D');
		}
		return maximo;
	}

	public void preencherMatriz(){
		//valores do cálculo para cada célula
		int horizontal;
		int vertical;
		int diagonal;
		int maximo;

		//como a matriz já foi inicializada, podemos começar da lin = 1 col = 1
		for (int i = 1; i <= alinhamento.getSequenciaA().length(); i++){
			for (int j = 1; j <= alinhamento.getSequenciaB().length(); j++){
				//para cada uma das células da matriz
				//chamamos os métodos de calcularHorizontal, calcularVertical
				//e calcularDiagonal passando a coordenada da célula atual 
				//como parâmetro
				horizontal = calcularHorizontal(i, j);
				vertical = calcularVertical(i, j);
				diagonal = calcularDiagonal(i, j);

				//verifica qual dos valores é o maior
				maximo = calcularMaximo(horizontal, vertical, diagonal,
						calcularPosicaoMatriz(i, j) - 1);

				//atribui o maior valor (máximo) a posição atual da matriz
				this.matriz[i][j] = maximo;				
			}
		}
	}

	public int calcularHorizontal(int linha, int coluna){
		int horizontal;
		horizontal = matriz[linha][coluna-1] + this.getSistemaPontuacao().get("gap");
		return horizontal;
	}

	public int calcularVertical(int linha, int coluna){
		int vertical;
		vertical = matriz[linha-1][coluna] + this.getSistemaPontuacao().get("gap");
		return vertical;
	}

	public int calcularDiagonal(int linha, int coluna){
		int diagonal;
		//se o símbolo na coluna x na seq A for igual ao símbolo na linha y na seq B
		//há um MATCH, senão é um mismatch ("letras diferentes")
		// se remove um da linha e coluna, porque as sequencias são salvas sem o '-'
		if (this.alinhamento.getSequenciaA().charAt(linha-1) == this.alinhamento.getSequenciaB().charAt(coluna-1)){
			//o cálculo pela vertical
			//o valor da célula de cima + o valor do match/mismatch
			diagonal = matriz[linha-1][coluna-1] + getSistemaPontuacao().get("match");
		} else { //são letras diferentes
			diagonal = matriz[linha-1][coluna-1] + getSistemaPontuacao().get("mismatch");
		}
		return diagonal;
	}
	
	//calcula a posição da matriz, o número da célula, primeira, segunda...
		public int calcularPosicaoMatriz(int i, int j){
			return (i+1)*(j+1) + (i)*(this.alinhamento.getSequenciaB().length() + 1 - (j+1));
		}
		
	public void construirCaminho() {
		//indica a direção que deve seguir para encontrar o melhor alinhamento
		char direcao;
		// dá a última posição da matriz
		int posMatriz;
		setPosX(this.alinhamento.getSequenciaA().length()); //obtém a última linha da matriz
		setPosY(this.alinhamento.getSequenciaB().length()); //obtém a última coluna da matriz

		//faça enquanto não chega na posição (0,0) da matriz
		while (getPosX() != 0 || getPosY() != 0){
			posMatriz = calcularPosicaoMatriz(getPosX(), getPosY());
			//subtrai um, pois o array começa por zero
			direcao = getVetorCaminho().get(posMatriz - 1);
			construirAlinhamento(direcao);
		}
	}
	
	public void construirAlinhamento(char direcao){
		//caracteres que se adicionarão a seqAAlinhamento e seqBAlinhamento
		char caractereSeqA = ' ';
		char caractereSeqB = ' ';
		alinhamento.setVetorAlinhamento(alinhamento.getVetorAlinhamento() + " " + direcao);
		if (direcao == 'D'){//é para prosseguir o alinhamento indo para a diagonal
			caractereSeqA = this.alinhamento.getSequenciaA().charAt(this.getPosX() - 1);
			caractereSeqB = this.alinhamento.getSequenciaB().charAt(this.getPosY() - 1);
			this.setPosX(this.getPosX() - 1);//sobe uma linha
			this.setPosY(this.getPosY() - 1); //volta uma coluna
		} else if (direcao == 'H'){ // é para prosseguir o alinh. pela horizontal
			caractereSeqA = '-';
			caractereSeqB = alinhamento.getSequenciaB().charAt(this.getPosY() - 1);
			this.setPosY(this.getPosY() - 1);//sobe uma linha
		} else if (direcao == 'V'){
			caractereSeqA = alinhamento.getSequenciaA().charAt(this.getPosX() - 1);
			caractereSeqB = '-';
			this.setPosX(this.getPosX() - 1); //volta uma coluna
		}

		//faz para todos, construi a string
		this.alinhamento.setSequenciaAAlinhada(construirString(this.alinhamento.getSequenciaAAlinhada(), caractereSeqA));
		this.alinhamento.setSequenciaBAlinhada(construirString(this.alinhamento.getSequenciaBAlinhada(), caractereSeqB));
	}

	public String construirString(String sequencia, char adicionar){
		return adicionar + sequencia;
	}
	
	public String toString() {
		return "Alinhamento Global";
	}
}
