// Name: Ziheng ZHANG;  ID: 201220030
// Version 2.0
package Engineers;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

class Copyist extends Thread {
    private Pencil pencil;
    private Eidograph eidograph;
    private int id;
    private int ponderFactor;
    private int drawing;
    private Random rand = new Random(100);

    public Copyist(Pencil pencil, Eidograph eidograph, int id, int ponderFactor) {
        this.pencil = pencil;
        this.eidograph = eidograph;
        this.id = id;
        this.ponderFactor = ponderFactor;
        this.drawing = 1;
    }

    private void pause() throws InterruptedException {
        if (ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }

    public void run() {
        try {
            while ((!Thread.interrupted())&&(drawing < 6)) {
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
            }
            System.out.println(this + " has finished work and gone to pub.");
        } catch (InterruptedException e) {
            System.out.println(this + " exiting via interrupt");
        }
    }
    
    // ODD copyist 30 <= Copying <= 80
    // EVEN copyist 30 <= Copying <= 60
    public void copying(){
        System.out.println(this + " is making drawing.");
        try{
            if((this.id & 2) == 0){
                sleep(rand.nextInt(60-30));
            }else{
                sleep(rand.nextInt(80-30));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // ODD copyist 40 <= Checking <= 60
    // EVEN copyist 40 <= Checking <= 100
    public void checking(){
        System.out.println(this + " has finished copy " + this.drawing);
        System.out.println(this + " is checking copy " + this.drawing);
        try{
            if((this.id & 2) == 0){
                sleep(rand.nextInt(100-40));
            }else{
                sleep(rand.nextInt(60-40));
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

class Pencil{
    private boolean takenPencil = false;
    public synchronized void take() throws InterruptedException {
        while (takenPencil) {
            wait();
        }
        takenPencil = true;
    }
    public synchronized void drop() {
        takenPencil = false;
        notifyAll();
    }
}

class Eidograph{
    private boolean takenEido = false;
    public synchronized void take() throws InterruptedException {
        while (takenEido) {
            wait();
        }
        takenEido = true;
    }
    public synchronized void drop() {
        takenEido = false;
        notifyAll();
    }
}

public class Engineers {

    public static void main(String[] args) throws Exception {
        int ponder = 1;
        int size = 8;
        ExecutorService exec = Executors.newCachedThreadPool();
        Pencil[] pencil = new Pencil[size/2];
        Eidograph[] eidograph = new Eidograph[size/2];
        for (int i = 0; i < 4; i++) {
            pencil[i] = new Pencil();
            eidograph[i] = new Eidograph();
        }
        
//        for (int i = 0; i < size; i++){
//            if ((i % 2) == 0) {
//                if (i < (size - 1)){
//                    exec.execute(new Copyist(pencil[i], eidograph[i + 1], i, ponder));
//                }else{
//                    exec.execute(new Copyist(pencil[i], eidograph[i + 1], i, ponder));
//                }
//            } else {
//                exec.execute(new Copyist(pencil[0], eidograph[i], i, ponder));
//            }
//        }
        
        exec.execute(new Copyist(pencil[3], eidograph[0], 0, ponder));
        exec.execute(new Copyist(pencil[0], eidograph[0], 1, ponder));
        exec.execute(new Copyist(pencil[0], eidograph[1], 2, ponder));
        exec.execute(new Copyist(pencil[1], eidograph[1], 3, ponder));
        exec.execute(new Copyist(pencil[1], eidograph[2], 4, ponder));
        exec.execute(new Copyist(pencil[2], eidograph[2], 5, ponder));
        exec.execute(new Copyist(pencil[2], eidograph[3], 6, ponder));
        exec.execute(new Copyist(pencil[3], eidograph[3], 7, ponder));
        
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}