package mindgamefc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientDefensor {
    static char  faixaAnterior;
    public static void main(String[] args) {

        Mensagens.apresentacao();
        try {
            
            //region variaveis
            Socket client = new Socket("", 1234);
            Scanner inServer = new Scanner(client.getInputStream());
            Scanner inUser = new Scanner(System.in);
            PrintWriter outServer = new PrintWriter(client.getOutputStream(),true);
            String jogada =  "";
            String jogadaAtacante = "";
            faixaAnterior = ' ';
            int numJogadas = inServer.nextInt();
            int resultado = 0;
            //endregion
            
            while (resultado == 0 && numJogadas != 0) {
                jogada = Mensagens.exibirJogada(inUser, numJogadas, "DEFENSOR");
                if(validarJogadaDefensor(jogada)){
                    System.out.println("\n####################################");
                    System.out.println("JOGADA VALIDA, AGUARDANDO RESPOSTA...");
                    numJogadas -= 1;
                    outServer.println(jogada);
                    resultado = Integer.parseInt(inServer.next());
                    jogadaAtacante = inServer.next();
                    divulgarResultado(resultado);
                    System.out.println("JOGADA DO ATACANTE: "+ jogadaAtacante);
                    System.out.println("####################################\n");
                }
            }
            fecharConexoes(client,outServer, inServer );
            System.out.println("FIM DE JOGO");
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    private static boolean validarJogadaDefensor(String jogada) {
        int posi = Integer.parseInt(Character.toString(jogada.charAt(1)));
        char faixa = jogada.charAt(0);
        
        switch (faixa){
            case 'A':
                if(posi > 3 || posi < 0){
                    System.out.println("\n\nCASA inválida");
                    return false;
                }else{
                    faixaAnterior = 'A';
                    return true;
                }

            case 'B':
               if(posi > 2 || posi < 0){
                    System.out.println("\n\nCASA inválida");
                    return false;
                }else{
                   faixaAnterior = 'B';
                    return true;
                }

            case 'C':
                if(posi > 3 || posi < 0){
                    System.out.println("\n\nCASA inválida");
                    return false;
                }else{
                    faixaAnterior = 'C';
                    return true;
                }

            case 'D':
                if(posi > 2 || posi < 0){
                    System.out.println("\n\nCASA inválida");
                    return false;
                }else{
                    return true;
                }

            default:
                System.out.println("\n\nJOGADA inválida");
                return false;
        }
    }
    
    public static void fecharConexoes(Socket clientSocket, PrintWriter outToServer, Scanner inFromServer) throws IOException {
        clientSocket.close();
        outToServer.close();
        inFromServer.close();
    }
    
    private static void divulgarResultado(int resultado) {
        switch (resultado) {
            case -1:
                System.out.println("\nBOA ROUBADA DE BOLA!");
                break;
            case 0:
                System.out.println("\nO time atacante segue com a bola...");
                break; 
            case 1:
                System.out.println("\n\nGOOOOOOL DO TIME ADVERSÁRIO....\n");
                break;
            default:
                break;
        }
    }
}

