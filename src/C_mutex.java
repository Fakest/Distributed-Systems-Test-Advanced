import java.net.ServerSocket;
import java.net.Socket;

public class C_mutex extends Thread {

    C_buffer buffer;
    Socket s;
    int port;

    // ip address and port number of the node requesting the token.
    // They will be fetched from the buffer    
    String n_host;
    int n_port;
    int n_priority;

    public C_mutex(C_buffer b, int p) {
        buffer = b;
        port = p;
    }

    public void go() {
        try {
            //  >>>  Listening from the server socket on port 7001
            // from where the TOKEN will be later on returned.
            // This place the server creation outside he while loop.
            ServerSocket ss_back = new ServerSocket(port);//listen for a connection on port

            while (true) {
                // >>> Print some info on the current buffer content for debuggin purposes.
                // >>> please look at the available methods in C_buffer
                System.out.println("C:mutex   Buffer size is " + buffer.size());

                // if the buffer is not empty
                if (buffer.size() != 0) {
                    // >>>   Getting the first (FIFO) node that is waiting for a TOKEN form the buffer
                    //       Type conversions may be needed.

                    int max = 0;
                    int maxIndex = 0;
                    for (int i = 0; i < buffer.size(); i +=3){
                        int currPriority = Integer.parseInt(buffer.get(i).toString());
                        if(max < currPriority){
                            max = currPriority;
                            maxIndex = i;
                        }
                    }

                    System.out.println("Removing...");
                    System.out.println(buffer.size());
                    System.out.println("priority pos " + maxIndex + " node pos " + (maxIndex+1) + " port pos " + (maxIndex+2));
                    n_priority = Integer.parseInt(buffer.get(maxIndex).toString());
                    n_host = buffer.get(maxIndex + 1).toString();
                    n_port = Integer.parseInt(buffer.get(maxIndex + 2).toString());

                    System.out.println("Node priority " + n_priority + " Node Host " + n_host + " Node Port " + n_port);

                    // >>>  **** Granting the token

                    try {
                        System.out.println("Send response to " + n_host + ":" + n_port);
                        s = new Socket(n_host, n_port);
                        s.close();

                    } catch (java.io.IOException e) {
                        System.out.println(e);
                        System.out.println("CRASH Mutex connecting to the node for granting the TOKEN");
                        e.printStackTrace();
                    }


                    //  >>>  **** Getting the token back
                    try {
                        s = ss_back.accept();
                        System.out.println("Token received from " + s);
                        // THIS IS BLOCKING !
                    } catch (java.io.IOException e) {
                        System.out.println(e);
                        System.out.println("CRASH Mutex waiting for the TOKEN back" + e);
                    }

                }// endif
            }// endwhile
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        go();
    }
}
