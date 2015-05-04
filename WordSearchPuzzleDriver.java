
import java.util.*;

public class WordSearchPuzzleDriver
{
	public static void main(String [] args)
	{
		//-------------------------------------------------------------------------------------------------------
		// In the following section I set up 4 different ArrayLists. These will be used as the userSpecifiedWords
		//-------------------------------------------------------------------------------------------------------
		
		//First arraylist provided by the user
		String s = "soccer rugby football swimming surfing fishing tennis golf bowls running handball hockey"; 
		List<String> wordList1 = new ArrayList<String>(s.split(" ").length);
		for(String x : s.split(" "))
		{
			wordList1.add(x);
		}
		
		//Second arraylist provided by the user
		String t = "after climbing a great hill one only finds that there are many more hills to climb nelson mandela";
		List<String> wordList2 = new ArrayList<String>(t.split(" ").length);
		for(String x : t.split(" "))
		{
			wordList2.add(x);
		}
		
		//Third arraylist provided by the user - it's empty
		List<String> wordList3 = new ArrayList<String>();
		
		//Fourth arraylist provided by the user - it has words with non-alphabetic characters
		String u = "word1! word2% word3 word4 word5& w0rd";
		List<String> wordList4 = new ArrayList<String>(u.split(" ").length);
		for(String x : u.split(" "))
		{
			wordList4.add(x);
		}

		//-----------------------------------------------------------------------------------------------------
		// In the following section I create 4 puzzles, 2 from ArrayLists and 2 from files provided by the user
		//-----------------------------------------------------------------------------------------------------
		
		//Create puzzle with the first arraylist provided by the user
		System.out.println();
		System.out.println("PUZZLE 1\n--------");
		System.out.println();
		WordSearchPuzzle puzzle1 = new WordSearchPuzzle(wordList1);
		System.out.println(puzzle1.getWordSearchList());
		System.out.println();		
		System.out.println(puzzle1.getPuzzleAsString());	//display the puzzle using the getPuzzleAsString method
		System.out.println();
		System.out.println("The same puzzle as above, shown here with the showWordSearchPuzzle method: \n");
		puzzle1.showWordSearchPuzzle(false);
		System.out.println();
		System.out.println();
		
		//Create puzzle with the second arraylist provided by the user
		System.out.println("PUZZLE 2\n--------");
		System.out.println();
		WordSearchPuzzle puzzle2 = new WordSearchPuzzle(wordList2);
		System.out.println(puzzle2.getWordSearchList());
		System.out.println();
		char[][] c = puzzle2.getPuzzleAsGrid();		//display the puzzle using the getPuzzleAsGrid method
		for(int i = 0; i < c.length; i++)
		{
			for(int j = 0; j < c[0].length; j++)
			{
				System.out.print(c[i][j] + " ");
			}
			System.out.println();
		} 
		System.out.println();
		System.out.println();
		System.out.println("The same puzzle as above, shown here with the showWordSearchPuzzle method: \n");
		puzzle2.showWordSearchPuzzle(true);
		System.out.println();
		System.out.println();
		
		//Create puzzle with a user specified file (animals.txt)
		System.out.println("PUZZLE 3\n--------");
		WordSearchPuzzle puzzle3 = new WordSearchPuzzle("animals.txt", 12, 2, 7);	
		puzzle3.showWordSearchPuzzle(true);
		System.out.println();
		System.out.println();
		
		//Create puzzle with another user specified file (dictionary.txt)
		System.out.println("PUZZLE 4\n--------");
		WordSearchPuzzle puzzle4 = new WordSearchPuzzle("dictionary.txt", 15, 2, 6);	
		puzzle4.showWordSearchPuzzle(false);
		System.out.println();
		System.out.println();

		//--------------------------------------------------------------------
		//The following is intended to show the program's validation in action
		//--------------------------------------------------------------------
		
		//Attempt to create a puzzle with a file that is empty
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle5 = new WordSearchPuzzle("emptyfile.txt", 12, 2, 7);
		System.out.println("\n\n");
		
		//Attempt to create a puzzle with a file and specifying a wordCount of zero
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle6 = new WordSearchPuzzle("animals.txt", 0, 2, 7);
		System.out.println("\n");
		
		//Attempt to create a puzzle with a file and specifying a 'shortest' value of 1
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle7 = new WordSearchPuzzle("animals.txt", 12, 1, 7);
		System.out.println("\n\n");
		
		//Attempt to create a puzzle with a file and specifying a 'shortest' value that is greater than the 'longest' value
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle8 = new WordSearchPuzzle("animals.txt", 12, 7, 2);
		System.out.println("\n\n");
		
		//Attempt to create a puzzle with a file, specifying valid values but there are not enough words of this type in the file (in this case there are no 13-letter words in the animals.txt file)
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle9 = new WordSearchPuzzle("animals.txt", 1, 13, 13);
		System.out.println("\n\n");
		
		//Attempt to create a puzzle with a file that contains words with non-alphabetic characters
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle10 = new WordSearchPuzzle("nonAlphabetical.txt", 12, 2, 7);
		System.out.println("\n\n");
		
		//Attempt to create a puzzle with the 3rd (empty) arraylist provided by the user
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle11 = new WordSearchPuzzle(wordList3);
		System.out.println();
		System.out.println();
		
		//Attempt to create a puzzle with the 4th arraylist provided by the user (this list contains words with non-alphabetic characters)
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle12 = new WordSearchPuzzle(wordList4);
		System.out.println();
		System.out.println();
		
		//Attempt to create a puzzle with a file that does not exist
		System.out.println("PUZZLE\n------");
		WordSearchPuzzle puzzle13 = new WordSearchPuzzle("blahblah.txt", 12, 2, 7);
		System.out.println("\n\n");	
	}
}




