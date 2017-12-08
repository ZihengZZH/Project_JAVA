package cse105;


import java.io.IOException;
import static cse105.DictionaryFile.listOfWords;


/**
 *
 * @author ziheng.zhang14
 */
public class DictionaryMenu {

    
    //Run the menu
    public static void menuRun() throws IOException {
        System.out.println("Welcome to the Dictionary!" );
        System.out.println("----------------------------------");
        System.out.println("In this dictionary, you can\n" 
        + "0.Exit the program.\n"
        + "1.Display all words.\n"
        + "2.Display the words starting with particular letter.\n"
        + "3.Display a word and meaning.\n"
        + "4.Find a meaning according to your input.\n"
        + "5.Add a word.\n"
        + "6.Remove a word.\n"
        + "7.Save all words.\n"
        + "----------------------------------");
        boolean exit = false;
        
        
        //Keep processing menu util exit.
        while(!exit){
            System.out.print("Please input your choice: ");
            String choice = DataInput.inputString();
            switch (choice){
                case "0":
                    exit = true;
                    System.out.println("The program exits.");
                    break;
                case "1":
                    DisplayAllWords();
                    break;
                case "2":
                    DisplayParticularWords();
                    break;
                case "3":
                    DisplayWordWithMeaning();
                    break;
                case "4":
                    FindMeaning();
                    break;
                case "5":
                    AddWord();
                    break;
                case "6":
                    RemoveWord();
                    break;
                case "7":
                    SaveAllWords();
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }


    //Display all the words and meaning stored in txt file
    public static void DisplayAllWords() {
        System.out.println("All words and meannings: ");
        DictionaryFile.PrintingFile();
        System.out.println("----------------------------------");
    }

    //Display the word and meaing with same starting character
    public static void DisplayParticularWords() throws IOException {
        System.out.println("Please enter the letter to start with: ");
        String startingword = DataInput.inputString();
        DictionaryFile.SearchingCharacter(startingword);
        System.out.println("----------------------------------");
    }
    
    //Display the word and meaning that user input
    public static void DisplayWordWithMeaning() throws IOException{
        System.out.println("Enter the word: ");
        String words = DataInput.inputString();
        DictionaryFile.SearchingFile(words);
        System.out.println("----------------------------------");
    }
    
    //Find the meaning according to the word user input
    public static void FindMeaning() throws IOException{
        System.out.println("Enter the word you want to search: ");
        String finding = DataInput.inputString();
        DictionaryFile.SearchingFile(finding);
        System.out.println("----------------------------------");
    }
    
    //Enter the word and meaning to the list
    public static void AddWord(){
        System.out.print("Please enter the word you want to add:");
        String Addword = DataInput.inputString();
        System.out.print("Please enter the meaning: ");
        String Addmeaning = DataInput.inputString();
        DictionaryFile add = new DictionaryFile(Addword, Addmeaning);
        listOfWords.add(add);
        System.out.println("The word " + Addword + " has been added."); 
        System.out.println("----------------------------------");
    }
    
    //Remove some word and meaning from the list
    public static void RemoveWord() throws IOException{
        System.out.print("Please enter the word to remove: ");
        String removeword = DataInput.inputString();
        DictionaryFile.removeWordWithName(removeword);
        System.out.println("----------------------------------");
    }
        
    //Save all the words in the Buffer to txt file
    public static void SaveAllWords(){
        DictionaryFile.WritingFile();
    }

}