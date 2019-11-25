import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class C_receiver extends Thread {

    private C_buffer buffer; //storage for connections
    private int port; //port that receiver is using

    private ServerSocket s_socket; //socket to receive data from
    private Socket socketFromNode; //socket that the node is using

    private C_Connection_r connect; //connection


    public C_receiver(C_buffer b, int p) {
        buffer = b;
        port = p;
    }


    public void run() {

        // >>> create the socket the server will listen to
        try {
            s_socket = new ServerSocket(port);//listen for a connection
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("SERVER LISTENING...");

        while (true) {
            try {
                // >>> get a new connection
                socketFromNode = s_socket.accept(); //accept a connection
                System.out.println("C:receiver    Coordinator has received a request ...");
                connect = new C_Connection_r(socketFromNode, buffer); //connection actions being carried out
                new Thread(connect).start(); //start thread

                // >>> create a separate thread to service the request, a C_Connection_r thread.
            } catch (IOException e) {
                System.out.println("Exception when creating a connection " + e);
            }

        }
    }//end run

}
	

