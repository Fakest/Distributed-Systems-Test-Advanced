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

        System.out.println("Save priority " + r[0]);
        adds(r[0]);
        System.out.println("Save node name " + r[1]);
        adds(r[1]);
        System.out.println("Save port " + r[2]);
        adds(r[2]);
    }

    public void show() {

        for (int i = 0; i < data.size(); i++)
            System.out.print(" " + data.get(i) + " ");
        System.out.println(" ");
    }


    public synchronized void adds(Object o) {
        data.add(o);
    }

    public Object get(int i){
        return data.get(i);
    }

    synchronized public Object get() {

        Object o = null;

        if (data.size() > 0) {
            o = data.get(0);
            data.remove(0);
        }
        return o;
    }


}
	
	
