package escalonador;

import java.io.IOException;

public class Main {

  
    public static void main(String[] args) throws IOException {
          

                Gerenciador escalonador = new Gerenciador();

                escalonador.readFile();
                
                System.out.println(escalonador);

            }
        
       }
