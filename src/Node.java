import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Node {

    private Random ra;
    private Socket s;

    private PrintWriter pout = null;

    private ServerSocket n_ss;
    private Socket n_token;

    //C STANDS FOR COORDINATOR NOT CURRENT
    String c_host = "127.0.0.1";
    int c_request_port = 7000;
    int return_port = 7001;

    //N STANDS FOR NODE NOT NEW
    String n_host = "127.0.0.1";
    String n_host_name;
    int n_port;


    public Node(String nam, int por, int sec) {

        ra = new Random();
        n_host_name = nam;
        n_port = por;

        System.out.println("Node " + n_host_name + ":" + n_port + " of DME is active ....");


        // NODE sends n_host and n_port  through a socket s to the coordinator
        // c_host:c_req_port
        // and immediately opens a server socket through which will receive
        // a TOKEN (actually just a synchronization).


        while (true) {

            // >>>  sleep a random number of seconds linked to the initialisation sec value

            try {
                // >>>
                // **** Send to the coordinator a token request.
                // send your ip address and port number
                n_token = new Socket(c_host, c_request_port); //send a connection to coordinator
                OutputStream outputStream = n_token.getOutputStream(); //get the output stream from the token
                pout = new PrintWriter(outputStream, true); //get ready to write to the output with autoflush enabled
                pout.println(n_host_name);
                pout.println(n_port);
                System.out.println("Writing " + n_host_name + ":" + n_port);
                n_token.close(); //closes the connection
                // >>>
                // **** Wait for the token
                // this is just a synchronization
                // Print suitable messages

                n_ss = new ServerSocket(n_port);//listen for token
                s = n_ss.accept(); //accept token
                System.out.println("Received response");
                //close connections
                s.close();
                n_ss.close();
                //>>>
                // Sleep half a second, say
                // This is the critical session

                Thread.sleep(ra.nextInt(500));
                // >>>
                // **** Return the token
                // this is just establishing a synch connection to the coordinator's ip and return port.
                // Print suitable messages - also considering communication failures
                n_token = new Socket(c_host, return_port); //send connection to coordinator to return the token
                n_token.close();//close connection
            } catch (IOException e) {
                System.out.println(e);
                System.exit(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String args[]) {

        String n_host_name = "";
        int n_port;

        // port and millisec (average waiting time) are specific of a node
        if ((args.length < 1) || (args.length > 2)) {
            System.out.print("Usage: Node [port number] [millisecs]");
            System.exit(1);
        }

        // get the IP address and the port number of the node
        try {
            InetAddress n_inet_address = InetAddress.getLocalHost();
            n_host_name = n_inet_address.getHostName();
            System.out.println("node hostname is " + n_host_name + ":" + n_inet_address);
        } catch (UnknownHostException e) {
            System.out.println(e);
            System.exit(1);
        }

        n_port = Integer.parseInt(args[0]); //get port from args
        System.out.println("node port is " + n_port);

        Node n = new Node(n_host_name, n_port, Integer.parseInt(args[1]));
    }


}

