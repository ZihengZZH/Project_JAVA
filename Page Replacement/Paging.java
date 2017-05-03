package paging;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Name: Ziheng ZHANG;
// ID: 201220030;

class Page {
    private int current_frame; // The frame occupied by this page
    private int loaded_at; // The Clocktime this page was loaded into RAM (i)
    private int last_read; // The Clocktime this page was last accessed (i*)

    // Initiates all fields to -1;
    public Page() {
        this.current_frame = -1;
        this.loaded_at = -1;
        this.last_read = -1;
    }

    public Page(int ClockTime, int cframe) {
        this.current_frame = cframe;
        this.LoadPage(cframe, ClockTime);
        this.Update(ClockTime);
    }

    // Return current_frame for this page
    private int GetFrame() {
        return this.current_frame;
    }

    // Assigns this page to frame frame_value
    // if frame_value <= 7 loaded _at = Clocktime
    // if frame_value > 7 loaded_at = -1;
    private void LoadPage(int frame_value, int ClockTime) {
        if (frame_value < 8) {
            this.loaded_at = ClockTime;
        }
        if (frame_value > 7) {
            this.loaded_at = -1;
        }
    }

    // Updates last_read to ClockTime for this page
    private void Update(int ClockTime) {
        this.last_read = ClockTime;
    }

    // returns the value of loaded_at for this page
    private int GetAge() {
        return this.loaded_at;
    }

    // returns the value of last_read for this page
    private int GetLastAccess() {
        return this.last_read;
    }

    // returns the frame number of the frame with the longest resident page
    public static int Find_Oldest(int[] Store, Page[] Table) {
        int longest = 0;
        int Oldest = 20000;
        int num;
        for (int i = 0; i < 8; i++) {
            num = Store[i];  // Assign the page of frame 0~7 
            if (Oldest > Table[num].GetAge()) {
                Oldest = Table[num].GetAge();
                longest = num;
            } 
        } // Find the one with the smallest loaded_at
        return Table[longest].GetFrame();
    }

    // returns the frame number of the frame with the least recently used page
    public static int Find_LRU(int[] Store, Page[] Table) {
        int least = 0;
        int leastRecent = Table[0].GetLastAccess();
        int num;
        for (int i = 0; i < 8; i++){
            num = Store[i]; // Assign the page of frame 0~7 
               if (leastRecent > Table[num].GetLastAccess())  {
                leastRecent = Table[num].GetLastAccess();
                least = num;
            }
        } // Find the one with the smallest last_read
        return Table[least].GetFrame();
    }
    // Store[k] = p means that "page number p is currently held in page frame k"
    // Table[p].GetFrame() should return the value k
}

public class Paging {

    public static void main(String[] args) {
        // Read the data from the file and output the policy according to first character
        int[] pageFile = new int[20001];
        File(pageFile); // Read file data into a array pageFile
        System.out.print("Page Replacement Method used: ");
        Page[] listOfPage = new Page[1024]; // listOfPage[page] = Page
        int[] listOfFrame = new int[1024]; // listOfFrame[frame] = page
        for (int i = 0; i < listOfFrame.length; i++){
            listOfFrame[i] = i;
        } // Initialize the array
        for (int i = 0; i < listOfPage.length; i++){
            listOfPage[i] = new Page(0,i);
        } // Initialize the array
        int Y = 0; //  Y: new frame
        int X = 0; // X: current frame
        int P; // P: current page
        int policy = pageFile[0]; // Choose the policy according to first number
        switch (policy) {
            case 0:
                System.out.print("OLDEST\n");
                break;
            case 1:
                System.out.print("LRU\n");
                break;
            case 2:
                System.out.print("RANDOM\n");
                break;
            default:
                break;
        } // Output the page replacement policy
        
        int pageFault = 0; // Initialize a parameter pageFault
        for (int i = 1; i < pageFile.length; i++) {
            int page = pageFile[i]; // Load the pageFile element into program
            P = page;
            // Find the frame of a specific page
            for (int j = 0; j < listOfFrame.length; j++) {
                if (listOfFrame[j] == P) {
                    Y = j;
                }
            } // Find the page's current frame
            Page currentP = new Page(i, Y);
            listOfPage[P] = currentP; // Store the Page object into array listOfPage
            if (Y > 7) {
                switch (policy) {
                    case 0:
                        X = Page.Find_Oldest(listOfFrame, listOfPage);
                        break;
                    case 1:
                        X = Page.Find_LRU(listOfFrame, listOfPage);
                        break;
                    case 2:
                        Random rand = new Random();
                        X = rand.nextInt(8);
                        break;
                    default:
                        break;
                } // Return the swopping frame accroding to the replacement policy
                // Swop the current page and the page in frame 0~7
                int xP = listOfFrame[X];
                Page exchangeP1 = new Page(i, X);
                Page exchangeP2 = new Page(i, Y);
                listOfPage[xP] = exchangeP2;
                listOfPage[P] = exchangeP1;
                listOfFrame[X] = P;
                listOfFrame[Y] = xP;
                pageFault++; // Once swopping, pageFault increments by 1
            }
            if (i % 100 == 0) {
                for (int k = 0; k < 8; k++) {
                    System.out.print(listOfFrame[k] + " | ");
                }
                System.out.print(i + " | " + pageFault + "\n");
                pageFault = 0;
            } // Output pages in frame 0~7 and Time and pageFault
            // The output format is instructed by the assignment
        }
        
    }

    public static void File(int[] pageTrace) {
        // read file content from file
        FileReader fr = null; // Initialize a FileReader object
        BufferedReader br = null; // Initialize a BufferReader object
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Which page replacement would you like to use?");
            System.out.println("1.Oledst\n2.LRU\n3.Raondom");
            int choice = in.nextInt(); // Accept user's input
            String fileName = null;
            switch (choice) {
                case 1:
                    fileName = "Page_Trace_Oldest.txt";
                    break;
                case 2:
                    fileName = "Page_Trace_LRU.txt";
                    break;
                case 3:
                    fileName = "Page_Trace_Random.txt";
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            } // Read different files according to user's choice
            // NOTICE: The data file should be at the same location as project

            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String textLine;
            int count = 0;
            while ((textLine = br.readLine()) != null) {
                String[] arr = textLine.split(" "); // Separate each line with space
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].length() > 0 && arr[i] != null) {
                        pageTrace[count] = Integer.parseInt(arr[i]);
                        count++;
                    } // Store the element into array pageTrace in the format of integer
                }
            }
            br.close();
            fr.close(); // Close fileReader and BufferReader
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file!");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } // Tolerate exception and feedback
    }
}
