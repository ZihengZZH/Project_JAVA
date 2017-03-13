// Name: Ziheng ZHANG;  ID: 201220030

package Engineers;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Class of Copyist that extends Thread and controls the multi-programming
class Copyist extends Thread{
    int id;
    static int currentid = 0;
    private Drawing draw;
    private int pauseFactor;
    private Random rand = new Random(200); // Generating a random below 200?
    
    public Copyist(Drawing draw, int pauseFactor){
        super();
        id = currentid;
        currentid ++;
        this.draw = draw;
        this.pauseFactor = pauseFactor;
    }
    
    public void run(){
        while(true){
            waiting(); 
            draw.takeDrawing();
            drawing(); 
            draw.putDrawing();
        }
    }
    
    public void waiting(){
        System.out.println("Copyist " + id + " is waiting.");
        try{
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void drawing(){
        System.out.println("Copyist " + id + " is making drawing.");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void pending(){
        
    }
    
    public void quiting(){
        
    }
}

class Drawing{
    public boolean[] used = {false, false, false, false, false, false, false, false,};
    
    public synchronized void takeDrawing(){
        Copyist c = (Copyist) Thread.currentThread();
        int id = c.id;
        while(used[id] || used[(id+1) % 8]){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        used[id] = true;
        used[(id+1) % 8] = true;
    }
    
    public synchronized void putDrawing(){
        Copyist c = (Copyist) Thread.currentThread();
        int id = c.id;
        System.out.println("Copyist " + id + " has finished drawing.");
        used[id] = false;
        used[(id+1) % 8] = false;
        notifyAll();
    }
}

class Pencil{
    public boolean[] vacantP = {true, true, true, true};
    
    public synchronized void takePencil(){
        Copyist c = (Copyist) Thread.currentThread();
        int id = c.id;
        
    }
    
    public synchronized void putPencil(){
        
    }
}

class Eidograph{
    public boolean[] vacantE = {true, true, true, true};
    
    public synchronized void takeEidograph(){
        
    }
    
    public synchronized void putEidograph(){
        
    }
}

public class Engineers {
    
    public static void main(String[] args) {
        Drawing draw = new Drawing();
        int pauseFactor = 0;
        for (int i = 0; i < 8; i++){
            new Copyist(draw, pauseFactor).start();
        }
    } 
}