// Name: Ziheng ZHANG;  ID: 201220030;  CS-ID: x6zz
// Version 4.0 (Final version)

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

// Copyist class which extends Thread
class Copyist extends Thread {
    // six attributes belonging to each copyist
    private final Pencil pencil; // the pencil next to him/her
    private final Eidograph eidograph; // the eidograph next to him/her
    private final int id; // his/her ID
    private final int ponderFactor; // puase factor (default = 1)
    private final Random rand = new Random(); // random time for his copying and checking
    private int drawing; // number of his copys
    
    // constructor
    public Copyist(Pencil pencil, Eidograph eidograph, int id, int ponderFactor) {
        this.pencil = pencil;
        this.eidograph = eidograph;
        this.id = id;
        this.ponderFactor = ponderFactor;
        this.drawing = 1; // default begin with 1
    }
    
    // pause function
    private void pause() throws InterruptedException {
        if (ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }
    
    // run function to control the whole process of one copyist (thread)
    public void run() {
        try {
            while ((!Thread.interrupted()) && (drawing < 6)) {
                System.out.println(this + " is waiting.");
                pause();
                System.out.println(this + " is using pencil.");
                pencil.take();
                System.out.println(this + " is using eidograph.");
                eidograph.take();
                copying();
                pencil.drop();
                System.out.println(this + " has finished with pencil.");
                eidograph.drop();
                System.out.println(this + " has finished with eidograph.");
                checking();
                drawing++;
            } // while thread available and his/her copys < 6
            System.out.println(this + " has finished work and gone to pub.");
        } catch (InterruptedException e) {
            System.out.println(this + " exiting via interrupt");
        } // Otherwise exit with interrupt
    }
    
    // copying function with specified random time range
    // ODD copyist 30 <= Copying <= 80
    // EVEN copyist 30 <= Copying <= 60
    public void copying() {
        System.out.println(this + " is making drawing.");
        try {
            if ((this.id & 2) == 0) {
                sleep(rand.nextInt(60 - 30));
            } else {
                sleep(rand.nextInt(80 - 30));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // checking function with specified random time range
    // ODD copyist 40 <= Checking <= 60
    // EVEN copyist 40 <= Checking <= 100
    public void checking() {
        System.out.println(this + " has finished copy " + this.drawing);
        System.out.println(this + " is checking copy " + this.drawing);
        try {
            if ((this.id & 2) == 0) {
                sleep(rand.nextInt(100 - 40));
            } else {
                sleep(rand.nextInt(60 - 40));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Override toString
    public String toString() {
        return "Copyist " + id;
    }
}

// Pencil class and its synchronization
class Pencil {
    private boolean takenPencil = false; // indicating pencil state (taken or not)
    public synchronized void take() throws InterruptedException {
        while (takenPencil) {
            wait(); // taking some time to get access
        }
        takenPencil = true; // pencil has been taken
    }

    public synchronized void drop() {
        takenPencil = false; // pencil has been dropped
        notifyAll(); // notify all other threads
    }
}

// Eidograph class and its synchronization
class Eidograph {
    private boolean takenEido = false; // indicating eidograph state (taken or not)
    public synchronized void take() throws InterruptedException {
        while (takenEido) {
            wait(); // taking some time to get access
        }
        takenEido = true; // Eidograph has been taken
    }

    public synchronized void drop() {
        takenEido = false; // eidograph has been dropped
        notifyAll(); // notify all other threads
    }
}

public class Engineers {

    public static void main(String[] args) throws Exception {
        // Main program
        int ponder = 1; // pause factor (defaul0t = 1)
        int size = 8; // number of copyists
        ExecutorService exec = Executors.newCachedThreadPool(); // Create a Thread Pool
        Pencil[] pencil = new Pencil[size / 2]; // Create indicated number of pencil objects
        Eidograph[] eidograph = new Eidograph[size / 2]; // Create indicated number of eidograph objects
        for (int i = 0; i < 4; i++) {
            pencil[i] = new Pencil();
            eidograph[i] = new Eidograph();
        }
        // Create indicated number of copyist under construction
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                exec.execute(new Copyist(pencil[(i - 1) / 2], eidograph[i / 2], i, ponder));
                // the pencil & eidograph number has some relationship with copyist number
            } else {
                exec.execute(new Copyist(pencil[3], eidograph[i / 2], i, ponder));
                // but no.0 copyist has no.3 pencil and no.1 eidograph
            }
        }
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow(); // shut down thread pool
    }
}
