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
			//tira uma posi��o pois no ArraYlist come�a a se contar por zero
			// n�o como pelo c�lculo
			getVetorCaminho().set(calcularPosicaoMatriz(i, 0) - 1, 'V');
		}

		for (int j = 1; j < alinhamento.getSequenciaB().length() + 1; j++){
			matriz[0][j] = j * this.getSistemaPontuacao().get("gap");
			//tira uma posi��o pois no ArraYlist come�a a se contar por zero
			// n�o como pelo c�lculo
			getVetorCaminho().set(calcularPosicaoMatriz(0, j) - 1, 'H');
		}
	}

	public int calcularMaximo(int horizontal, int vertical, int diagonal,  
			int posicaoArrayList) {
		int maximo;
		//preced�ncia D>H>V
		//o igual garante a preced�ncia requerida no projeto
		if ((horizontal >= vertical) && (horizontal > diagonal)){
			maximo = horizontal;
			//adiciona de onde veio o m�ximo
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
		//valores do c�lculo para cada c�lula
		int horizontal;
		int vertical;
		int diagonal;
		int maximo;

		//como a matriz j� foi inicializada, podemos come�ar da lin = 1 col = 1
		for (int i = 1; i <= alinhamento.getSequenciaA().length(); i++){
			for (int j = 1; j <= alinhamento.getSequenciaB().length(); j++){
				//para cada uma das c�lulas da matriz
				//chamamos os m�todos de calcularHorizontal, calcularVertical
				//e calcularDiagonal passando a coordenada da c�lula atual 
				//como par�metro
				horizontal = calcularHorizontal(i, j);
				vertical = calcularVertical(i, j);
				diagonal = calcularDiagonal(i, j);

				//verifica qual dos valores � o maior
				maximo = calcularMaximo(horizontal, vertical, diagonal,
						calcularPosicaoMatriz(i, j) - 1);

				//atribui o maior valor (m�ximo) a posi��o atual da matriz
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
		//se o s�mbolo na coluna x na seq A for igual ao s�mbolo na linha y na seq B
		//h� um MATCH, sen�o � um mismatch ("letras diferentes")
		// se remove um da linha e coluna, porque as sequencias s�o salvas sem o '-'
		if (this.alinhamento.getSequenciaA().charAt(linha-1) == this.alinhamento.getSequenciaB().charAt(coluna-1)){
			//o c�lculo pela vertical
			//o valor da c�lula de cima + o valor do match/mismatch
			diagonal = matriz[linha-1][coluna-1] + getSistemaPontuacao().get("match");
		} else { //s�o letras diferentes
			diagonal = matriz[linha-1][coluna-1] + getSistemaPontuacao().get("mismatch");
		}
		return diagonal;
	}
	
	//calcula a posi��o da matriz, o n�mero da c�lula, primeira, segunda...
		public int calcularPosicaoMatriz(int i, int j){
			return (i+1)*(j+1) + (i)*(this.alinhamento.getSequenciaB().length() + 1 - (j+1));
		}
		
	public void construirCaminho() {
		//indica a dire��o que deve seguir para encontrar o melhor alinhamento
		char direcao;
		// d� a �ltima posi��o da matriz
		int posMatriz;
		setPosX(this.alinhamento.getSequenciaA().length()); //obt�m a �ltima linha da matriz
		setPosY(this.alinhamento.getSequenciaB().length()); //obt�m a �ltima coluna da matriz

		//fa�a enquanto n�o chega na posi��o (0,0) da matriz
		while (getPosX() != 0 || getPosY() != 0){
			posMatriz = calcularPosicaoMatriz(getPosX(), getPosY());
			//subtrai um, pois o array come�a por zero
			direcao = getVetorCaminho().get(posMatriz - 1);
			construirAlinhamento(direcao);
		}
	}
	
	public void construirAlinhamento(char direcao){
		//caracteres que se adicionar�o a seqAAlinhamento e seqBAlinhamento
		char caractereSeqA = ' ';
		char caractereSeqB = ' ';
		alinhamento.setVetorAlinhamento(alinhamento.getVetorAlinhamento() + " " + direcao);
		if (direcao == 'D'){//� para prosseguir o alinhamento indo para a diagonal
			caractereSeqA = this.alinhamento.getSequenciaA().charAt(this.getPosX() - 1);
			caractereSeqB = this.alinhamento.getSequenciaB().charAt(this.getPosY() - 1);
			this.setPosX(this.getPosX() - 1);//sobe uma linha
			this.setPosY(this.getPosY() - 1); //volta uma coluna
		} else if (direcao == 'H'){ // � para prosseguir o alinh. pela horizontal
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
