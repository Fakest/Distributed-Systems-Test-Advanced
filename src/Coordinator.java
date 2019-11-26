import java.net.InetAddress;

public class Coordinator {

    private static C_buffer buffer = new C_buffer();
    public static void main(String args[]) {

        int port = 7000; //reciever port number
        int m_port = 7001; //mutex port number

        //initialise the coordinator values
        try {
            InetAddress c_addr = InetAddress.getLocalHost();
            String c_name = c_addr.getHostName();
            System.out.println("Coordinator address is " + c_addr);
            System.out.println("Coordinator host name is " + c_name + "\n\n");
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Error in corrdinator");
        }

        // allows defining port at launch time
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }


        // Create and run a C_receiver and a C_mutex object sharing a C_buffer object

        C_receiver receiver = new C_receiver(buffer, port); //reciever on port 7000
        C_mutex mutex = new C_mutex(buffer, m_port); //mutex on port 7001

        //start threads

        new Thread(receiver).start();
        new Thread(mutex).start();
    }

}