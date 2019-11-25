import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;


// Reacts to a node request.
// Receives and records the node request in the buffer.
//
public class C_Connection_r extends Thread {

    // class variables
    C_buffer buffer;

    Socket s;
    InputStream in;
    BufferedReader bin;
    Random r = new Random();

    public C_Connection_r(Socket s, C_buffer b) {
        this.s = s;
        this.buffer = b;

    }

    public void run() {

        final int PRIORITY = 0;
        final int NODE = 1;
        final int PORT = 2;

        String[] request = new String[3];

        System.out.println("C:connection IN    dealing with request from socket " + s);
        try {

            // >>> read the request, i.e. node ip and port from the socket s
            // >>> save it in a request object and save the object in the buffer (see C_buffer's methods).

            in = s.getInputStream();//gets the input stream from the node
            bin = new BufferedReader(new InputStreamReader(in)); //reads the input stream

            //read request
            request[PRIORITY] = String.valueOf(r.nextInt(10));
            request[NODE] = bin.readLine();
            request[PORT] = String.valueOf(bin.readLine());


            //save the request
            buffer.saveRequest(request);
            s.close(); //close the socket connection
            System.out.println("C:connection OUT    received and recorded request from " + request[NODE] + ":" + request[PORT] + "  (socket closed)");

        } catch (IOException e) {

            System.out.println(e);
            System.exit(1);

        }
        buffer.show();
    } // end of run() method
} // end of class
