// Name: Ziheng ZHANG;  ID: 201220030
// Version 2.0

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Copyist extends Thread {
    private Drawing left;
    private Drawing right;
    private int id;
    private int ponderFactor;
    private boolean drawEd;
    private Random rand = new Random(47);

    public Copyist(Drawing left, Drawing right, int id, int ponderFactor) {
        this.left = left;
        this.right = right;
        this.id = id;
        this.ponderFactor = ponderFactor;
        this.drawEd = false;
    }

    private void pause() throws InterruptedException {
        if (ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }

    public void run() {
        try {
            while ((!Thread.interrupted())&&(!drawEd)) {
                System.out.println(this + " is waiting.");
                pause();
                System.out.println(this + " is grabbing right.");
                right.take();
                System.out.println(this + " is grabbing left.");
                left.take();
                System.out.println(this + " is drawing.");
                pause();
                right.drop();
                left.drop();
                System.out.println(this + " has completed drawing.");
                drawEd = true;
            }
        } catch (InterruptedException e) {
            System.out.println(this + " exiting via interrupt");
        }
    }

    public String toString() {
        return "Copyist " + id;
    }
}

class Drawing {
    private boolean taken = false;

    public synchronized void take() throws InterruptedException {
        while (taken) {
            wait();
        }
        taken = true;
    }

    public synchronized void drop() {
        taken = false;
        notifyAll();
    }
}

public class Engineers {

    public static void main(String[] args) throws Exception {
        int ponder = 10;
        int size = 8;
        ExecutorService exec = Executors.newCachedThreadPool();
        Drawing[] sticks = new Drawing[size];
        for (int i = 0; i < size; i++) {
            sticks[i] = new Drawing();
        }
        for (int i = 0; i < size; i++) {
            if (i < (size - 1)) {
                exec.execute(new Copyist(sticks[i], sticks[i + 1], i, ponder));
            } else {
                exec.execute(new Copyist(sticks[0], sticks[i], i, ponder));
            }
        }
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
