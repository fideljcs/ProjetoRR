package fila;
import escalonador.Processo;

public class Fila {


    private Node first, last;
    private int iSize;

    public Fila() {
        first = null;      //Fila vazia
        last = null;
        iSize = 0;
    }

    public boolean isEmpty() {
        // verdadeiro se a fila estiver vazia
        return (first == null);
    }

    public void enqueue(Processo p) {
        Node newNode = new Node(p);
        if (first == null) {
            first = newNode;
            last = first;
        } else {
            last.next = newNode;
            last = newNode;
        }
        iSize++;

    }

    public void dequeue() {// retira do inicio da fila                         
        if (!isEmpty()) { //caso esteja vazia retorna -1

            Node temp = first;
            // desloca o topo para o prox no
            first = first.next;
            if (first == null) {
                last = null;
            }
            iSize--;
        }

    }

    public String display() {
        if (isEmpty()) {
            return "Não há processos na fila";
        } else {
            Node atual = first; // do comeco
            while (atual != null) {//olha ate o final
                // mostra a informacao do no
               return atual.displayNo();
                // desloca para o proximo no                  
            }         
        }
         return "Não há processos na fila";
    }

    //retorna o primeiro da fila
    public Processo next() {
        Node temp = first;
        if (first == null) {
            last = null;
        }
        return temp.processo;
    }

    //retorna o tamanho da fila total
    public int getSize() {
        return iSize;
    }

}
