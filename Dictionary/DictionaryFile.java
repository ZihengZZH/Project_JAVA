package cse105;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ziheng.zhang14
 */
public class DictionaryFile {

    public static ArrayList<DictionaryFile> listOfWords = new ArrayList<DictionaryFile>();

    private String word;
    private String meaning;

    //String the word and meaning
    public DictionaryFile(String word, String meaning) {
        super();
        this.word = word;
        this.meaning = meaning;
        listOfWords.add(this);
    }

    //String thr lineFromFile 
    public DictionaryFile(String lineFromFile) {
        super();
        String[] s = lineFromFile.split(", ");
        this.word = s[0];
        this.meaning = s[1];
        listOfWords.add(this);
    }

    //String getWord
    public String getWord() {
        return word;
    }

    //setWord to string
    public void setWord(String word) {
        this.word = word;
    }

    //String getMeaning
    public String getMeaning() {
        return meaning;
    }

    //setMeaning to string
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    //String flatten
    public String flatten() {
        return word + ", " + meaning;
    }

    //Set the toString()
    public String toString() {
        return "Word [word = " + word + ", meaning = " + meaning + "]";
    }

    //Read the txt file 
    public static boolean ReadingFile() {
        boolean success = false;
        File file = new File("DictionaryContents.txt");
        String line = "";
        ArrayList listOfWords = new ArrayList<DictionaryFile>();
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file));
            while ((line = fr.readLine()) != null) {
                System.out.println(line);
            }
            fr.close();
            success = true;
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return success;
    }

    //Searching the specific character in txt file
    public static void SearchingCharacter(String character) throws IOException {
        boolean Found = false;
        String fileName = "DictionaryContents.txt";
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String nextLine;
        int line = 1;
        while ((nextLine = br.readLine()) != null) {
            if (nextLine.startsWith(character)) {
                System.out.println(nextLine);
                Found = true;
            }
            line++;
        }
        if (!Found) {
            System.out.println("Not found in file.");
        }
    }

    //Searching the specific word in txt file
    public static void SearchingFile(String searching) throws IOException {
        boolean Found = false;
        String fileName = "DictionaryContents.txt";
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String nextLine;

        int line = 1;
        while ((nextLine = br.readLine()) != null) {
            if (nextLine.contains(searching)) {
                System.out.println(nextLine);
                Found = true;
            }
            line++;
        }
        if (!Found) {
            System.out.println("Not found in file.");
        }
    }

    //Print all the contents in txt file
    public static void PrintingFile() {
        System.out.println(DictionaryFile.ReadingFile());
    }

    //Write all the contents into txt file
    public static void WritingFile() {
        File file = new File("DictionaryContents.txt");
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(file, true));
            Iterator<DictionaryFile> itr;
            itr = listOfWords.iterator();
            DictionaryFile x;
            while (itr.hasNext()) {
                x = itr.next();
                fw.write(x.flatten());
                fw.newLine();
            }
            fw.flush();
            fw.close();
            System.out.println("Objects written to the file.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    //Remove the word and its meaning according to user input
    public static void removeWordWithName(String removeword) throws IOException {
        boolean Found = false;
        String fileName = "DictionaryContents.txt";
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        Iterator<DictionaryFile> itr;
        itr = listOfWords.iterator();
        String nextLine;
        DictionaryFile d;

        int line = 1;
        while ((nextLine = br.readLine()) != null) {
            if (nextLine.contains(removeword)) {
                d = new DictionaryFile(nextLine);
                listOfWords.remove(d);
                System.out.println("The word has been removed.");
                Found = true;
            }
            line++;
        }
        if (!Found) {
            System.out.println("Not found in file.");
        }

    }

}
