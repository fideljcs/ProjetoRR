package escalonador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import fila.Fila;
import java.util.Scanner;
import lista.Lista;

public class Gerenciador {

    private Lista list = new Lista();

    // Metodo para realizar a leitura do arquivo em CSV
    public void readFile() throws IOException {

        Processo processo;
        String sLine, sName, sDuration, sStart;

        JFileChooser jf = new JFileChooser();

        // apresenta somente arquivos de texto
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv", "csv");
        jf.setFileFilter(filter);

        int returnVal = jf.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileName = jf.getSelectedFile();

         try {
          FileReader file = new FileReader(fileName);
          BufferedReader bufReader = new BufferedReader(file);
          sLine = bufReader.readLine();

          while (sLine != null) {

          // transforma a linha em um vetor separando as  informacoes por informacoes por ponto e virgula
                  
          String[] vsLine = sLine.split(";");

           sName = vsLine[0]; // index 1 = PID
           sDuration = vsLine[1]; // index 2 =  duracao do processo
           sStart = vsLine[2]; // index 3 =  momento de chegada do processo

           // se o vetor de linha tiver um tamanho maior que 3 significa que o processo possui operacoes de I/O
                    
        if (vsLine.length > 3) {

        String[] viInOut = vsLine[3].split(",");

        processo = new Processo(sName, Integer.parseInt(sDuration), Integer.parseInt(sStart),viInOut.length);
                              
            for (int i = 0; i < viInOut.length; i++) {
            processo.preencheIO(i, Integer.parseInt(viInOut[i]));
            }
                //insere processo na lista
                list.insere(processo);
                            
            } else {
            // caso o  processo nao tenha operacoes de I/O criamos um objeto com essa informacao nula
            processo = new Processo(sName, Integer.parseInt(sDuration), Integer.parseInt(sStart), 0);

            //inserir processo na lista
            list.insere(processo);
                }
            sLine = bufReader.readLine();
             }

            file.close();
          }
            catch (FileNotFoundException exc) {
                
            System.out.println("arquivo " + fileName + " nao encontrado ");
              }   

            //Obter valor do quantum
            System.out.println( "Digite o valor do Quantum: " ); 
            Scanner s = new Scanner(System.in);
            String str = s.nextLine(); 
                           
              try {
   
            int quantum = Integer.parseInt(str); 
            System.out.println( "valor do quantum é  " + quantum );    
            escalonar (quantum);
                          
            } catch (NumberFormatException e) {
            System.out.println("Numero com formato errado!");
            
        } finally {
  s.close(); //fechar o Scanner para gerenciar melhor a memória.
}}}
   
//Cria a saida com o resultado da simulação de execução dos processos
    public void writeFile(String sLine) {

        System.out.println(sLine);
         
        }
    
    //metodo de escalonamento que recebe o valor do quantum
    public void escalonar(int quantum) {
        Processo processoEmExecucao = new Processo();

    int iTempoAtual = 0, iTempoEmExecucao = 0;
    double iTempoEmEspera = 0;
    boolean bEmExecucao = false;
    int iQtdProcessos = list.getSize(); //armazena a quantidade de processos da lista para poder calcular media de espera
    Fila queue = new Fila();

    // String que ira pegar as informações da execução para escrever na saida
    String sLine = "" + "\n Escalonador RR"  + "\n -------" + "\n começando " + "\n-------\n";
               

        sLine += "\nquantum: " + quantum + "ms"   + "\n";
      
        //lopping para executar o Tempo Atual até o tempo de duração de todos processos
        for (iTempoAtual = 0; iTempoAtual <= list.verificaDuracao(); iTempoAtual++) {

            sLine += "\n\n ------- Tempo " + iTempoAtual + " ------ ";

           //verifica o tempo de chegada do processo, se o retorno do metodo for diferente de null, significa que um processo chegou no tempo atual e ele é adicionado na fila 
           
            if (list.verificaInicio(list, iTempoAtual) != null) {
                queue.enqueue(list.verificaInicio(list, iTempoAtual));
                sLine += "\nChegada nova : " + "<" + list.verificaInicio(list, iTempoAtual).getsNome() + ">";
            }

            // primeira verificação, não existe processo sendo executado e ele será posto em execução para iniciar.
            // Trato de diminuir valor do atributo DuracaoAT para verificar o quanto falta para ser executado
            if (!bEmExecucao) {
                if (!queue.isEmpty()) {
                    processoEmExecucao = queue.next();
                    queue.dequeue();
                    processoEmExecucao.setEspera(iTempoAtual);
                    sLine += "\nFila : " + queue.display() + "\nCPU: " + processoEmExecucao.getsNome() + "(Tamanho: " + processoEmExecucao.getDuracaoAT() + ")";

                    processoEmExecucao.setDuracaoAT();
                    processoEmExecucao.setTempoExecutado();
                    bEmExecucao = true;
                    iTempoEmExecucao = 1;
                }

            } else {
                //se ja existir um processo sendo executado, é necessario seguir nas verificações para tratamento do escalonador
            
                //se o tempo restante para execução for igual a zero significa que o processo finalizou execução e encerra o programa. 
                //caso exista algum processo na fila colocamos em execução, se não, encerramos o escalonador.
                if (processoEmExecucao.getDuracaoAT() == 0) {
                    iTempoEmEspera += processoEmExecucao.getEspera(); //variavel que armazena o tempo de espera de todos processos para calcular media 
                    sLine += "\n Encerrando Processo <" + processoEmExecucao.getsNome() + ">" + " - Tempo de Espera: " + processoEmExecucao.getEspera() + "ms";

                    if (!queue.isEmpty()) {

                        processoEmExecucao = queue.next();
                        queue.dequeue();
                        processoEmExecucao.setEspera(iTempoAtual);
                        sLine += "\nFila: " + queue.display() + "\n CPU: " + processoEmExecucao.getsNome() + "(Tamanho: " + processoEmExecucao.getDuracaoAT() + ")";
                        processoEmExecucao.setDuracaoAT();
                        processoEmExecucao.setTempoExecutado();
                        bEmExecucao = true;
                        iTempoEmExecucao = 1;

                    } else {
                        sLine += "\nFila : " + queue.display();
                        sLine += "\nAcabaram os processos";
                    }

                    //verificar se o tempo atual de execução do processo atingiu o valor do quantum para fazer a troca de contexto.
                } else if (iTempoEmExecucao == quantum) {

                    sLine += "\nFim do Quantum do Processo:" + processoEmExecucao.getsNome();
                    // se ainda houver tempo para execucao, o processo é enviado novamente para a fila, salvando o tempo que entrou na fila para calculo de espera
                    if (processoEmExecucao.getDuracaoAT() > 0) {

                        processoEmExecucao.setTempoFila(iTempoAtual);
                        queue.enqueue(processoEmExecucao);

                        processoEmExecucao = queue.next();
                        queue.dequeue();
                        //quando um processo é removido da fila e entra em execucao calcula a diferença para saber o tempo de espera. 
                        processoEmExecucao.setEspera(iTempoAtual);

                        sLine += "\nFila: " + queue.display() + "\nCPU: " + processoEmExecucao.getsNome() + "(Tamanho: " + processoEmExecucao.getDuracaoAT() + ")";
                        processoEmExecucao.setDuracaoAT();
                        processoEmExecucao.setTempoExecutado();
                        bEmExecucao = true; // para não cair novamente no primeiro IF 
                        iTempoEmExecucao = 1;
                    }
                    //analisa se  existe operações de I/O no tempo atual, passando para o metodo iverificaIO o valor TempoExecutado que retorna o tempo
                    // de execuccao do processo atual 
                } else if (processoEmExecucao.iverificaIO(processoEmExecucao.getTempoExecutado()) > -1 && processoEmExecucao.getDuracaoAT() > 0) {
                    sLine += "\n  -------operação I/O < -------- " + processoEmExecucao.getsNome();
                    processoEmExecucao.setTempoFila(iTempoAtual);
                    queue.enqueue(processoEmExecucao);
                    processoEmExecucao = queue.next();
                    queue.dequeue();
                    processoEmExecucao.setEspera(iTempoAtual);
                    sLine += "\nFILA: " + queue.display() + "\nCPU: " + processoEmExecucao.getsNome() + "(Tamanho: " + processoEmExecucao.getDuracaoAT() + ")";
                    processoEmExecucao.setDuracaoAT();
                    processoEmExecucao.setTempoExecutado();
                    bEmExecucao = true;
                    iTempoEmExecucao = 1;

                } //caso nao entre em nenhuma condiçao acima, podemos incrementar o tempo em execucao e decrementar a duracao total
                else {
                    sLine += "\n fila: " + queue.display() + "\n CPU: " + processoEmExecucao.getsNome() + "(" + processoEmExecucao.getDuracaoAT() + ")";
                    iTempoEmExecucao++;
                    processoEmExecucao.setDuracaoAT();
                    processoEmExecucao.setTempoExecutado();

                }
            }
        }
          sLine += "\n\n"
                + "\n*Tempo medio de espera: " + tempoMedioEspera(iTempoEmEspera, iQtdProcessos) + " ms*"
                + "\n";
        sLine += "\n\n"
                + "\nEncerrar simulação "
                + "\n";

        writeFile(sLine);
        System.exit(0);
    }

    public double tempoMedioEspera(double iTempoEspera, int qtdProcessos) {
        return iTempoEspera / qtdProcessos;
    }

}
