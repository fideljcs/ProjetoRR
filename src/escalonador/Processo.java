package escalonador;

import java.util.Arrays;

public class Processo {

    private String sNome; // PID
    private int Comeco; // momento em que chega
    private int Duracao;  // Duracao do process
    private int DuracaoAT; // duracao atual
    private int TempoExecutado; //verificao o quanto ja foi executado para operacoes de I/O
    private int[] IOoper; // operacoes de I/O 

 
    private double Espera; // Sempre incrementa para verificamos o valor atual de espera (recebe dados do TempoFila para fazer a verificaçao)
  
    private double TempoFila; //recebe o valor do tempo do processo quando esta na fila

    public Processo(String sNome, int Duracao, int Comeco, int IOoper) {
        this.sNome = sNome;
        this.Duracao = Duracao;
        this.Comeco = Comeco;
        this.IOoper = new int[IOoper];
        this.DuracaoAT = Duracao;
        this.TempoExecutado = 0;
        this.TempoFila = Comeco;
    }

    public Processo() {

    }

    public String getsNome() {
        return sNome;
    }

    public int getDuracao() {
        return Duracao;
    }

    public int getComeco() {
        return Comeco;
    }

    public int getDuracaoAT() {
        return DuracaoAT;
    }

    //sempre que chamar DuracaoAT, será decrementado 1 do valor 
    public void setDuracaoAT() {
        this.DuracaoAT--;
    }

    public int  setComeco() {
        return Comeco;
    }
    
    public int getTempoExecutado () {
    
    return TempoExecutado;
}

    //sempre que chamar TempoExecutado, será incrementado 1 do valor 
    public void setTempoExecutado() {
        this.TempoExecutado++;
    }

    public int[] getIOoper() {
        return IOoper;
    }

    
    /**
     * Preencher vetor de  I/O com informações do arquivo
     *  
     */
    public void preencheIO(int indice, int value) {
        IOoper [indice] = value;
    }

    public int iverificaIO(int iTempoEmExecucao) {

        for (int i = 0; i < this.IOoper.length; i++) {
            if (IOoper[i] == iTempoEmExecucao) {
                return IOoper[i];
            }
        }
        return -1;
    }

    public double getEspera() {
        return Espera;
    }

    // calcula de tempo de espera
    public void setEspera (int iTempoAtual) {
        this.Espera = (iTempoAtual - TempoFila) + this.Espera;
    }

    public double getTempoFila() {
        return TempoFila;
    }

    // Instante que entrou na fila
    public void setTempoFila(int iTempoAtual) {
        this.TempoFila = iTempoAtual;
    }

    @Override
    public String toString() {
        return "Processo [sNome: " + sNome + ", Duracao: " + Duracao + ", Comeco: " + Comeco + ", DuracaoAT: "
                + DuracaoAT + ", IOoper: " + Arrays.toString(IOoper) + "]";
    }

}
