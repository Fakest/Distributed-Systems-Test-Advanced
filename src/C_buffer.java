import java.util.Vector;

public class C_buffer {

    private Vector<Object> data;


    public C_buffer() {
        data = new Vector<Object>();
    }


    public int size() {

        return data.size();
    }


    public synchronized void saveRequest(String[] r) {
        System.out.println("Save node " + r[0] + r[1] + r[2]);
        adds(r[0]);//add priority to memory
        adds(r[1]);//add name to memory
        adds(r[2]);//add port to memory
    }

    public void show() {

        for (int i = 0; i < data.size(); i++)
            System.out.print(" " + data.get(i) + " ");
        System.out.println(" ");
    }


    public synchronized void adds(Object o) {
        data.add(o);
    }

    public synchronized Object get(int i) {
        return data.get(i);
    }

    synchronized public void remove(int i) {
        data.remove(i);//remove the priority
        data.remove(i);//remove name
        data.remove(i);//remove from the port
    }

    synchronized public Object get() {

        Object o = null;

        if (data.size() > 0) {
            o = data.get(0);//return the first object for display purposes
            data.remove(0);//removes the first object
        }
        return o;
    }


    public void age() {
        for(int i = 0; i < data.size();  i+=3){
            int newVal = Integer.parseInt(data.get(i).toString()) + 3;
            data.set(i, newVal);
            //adds one to the priority, solving the starvation problem
        }
    }
}

	
	
