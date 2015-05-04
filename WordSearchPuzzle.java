
import java.util.*;
import java.io.*;

public class WordSearchPuzzle 
{
	private char[][] puzzle;
	private List<String> puzzleWords;
	private int height;
	private int width;
	private final double scalingFactor = 2.25;
	private List<String> wordsAndTheirLocations;	//used in the showWordSearchPuzzle method
	
	//Constructor method - gets words from a list provided by the user
	public WordSearchPuzzle(List<String> userSpecifiedWords)
	{
		if (userSpecifiedWords.isEmpty())	//check if the list the user provided is empty
		{
			System.out.println("\nThe list you provided is empty.");
		}
		else
		{
			puzzleWords = new ArrayList<String>(userSpecifiedWords.size());	 //create the puzzleWords list
			String pattern = "[a-zA-Z]+";	
			for (String s : userSpecifiedWords)
			{
				if (s.matches(pattern) && s.length() > 1)		//use the pattern to check the word. If it contains alphabetic characters only and it is not a 1-letter word, add it, else ignore it.
				{
					puzzleWords.add(s.toUpperCase());						
				}
			}	
			if (puzzleWords.isEmpty())	
			{
				System.out.println("\nAll the words in the list you provided were invalid \n(ie. each word contained at least 1 non-alphabetic character.");
			}
			else
			{
				//At this stage we have at least 1 valid word in the list provided by the user so go ahead and create the puzzle
				setGridDimensions(findLongestWordInList());		//Find the length of the longest word in the list and pass this information to the setGridDimensions method.
				puzzle = new char[height][width];				//create a 2d array with the required dimensions (calculated by the setGridDimensions method)
				fillGridWithHyphens();							//fill the whole grid with hyphens
				generateWordSearchPuzzle();						//place all the words and fill the remaining spaces with randomly generated letters.
			}
		}
	}
	
	//Constructor method - gets words from a file provided by the user
	public WordSearchPuzzle(String wordFile, int wordCount, int shortest, int longest)
	{
		if (wordCount == 0)		//the user must choose to place at least 1 word
		{
			System.out.println("\nwordCount value cannot be zero, it must be a value greater than or equal to one.");
		}
		else if (shortest < 2)	
		{
			System.out.println("\nOne letter words are not allowed in the puzzle. The 'shortest' value specified must be greater than 1.");
		}
		else if (shortest > longest)
		{
			System.out.println("\nThe 'shortest' length specified must not be greater than the 'longest' length specified.");
		}
		else
		{
			puzzleWords = new ArrayList<String>(wordCount);		//create the puzzleWords list	
			List<String> words = new ArrayList<String>();		//create a new list which will be used to store all the words of the appropriate length from the user's file	
			if (readWordsFromFile(wordFile,words,shortest,longest))	//the return value of the readWordsFromFile method called here will be zero if there are no lines in the file
			{
				System.out.println("\nThe file you provided is empty.");
			}			
			else if (words.size() < wordCount)					
			{
				System.out.println("\nThere were not enough words of the specified length (containing only alphabetic characters) in the file you provided.");
			}
			else
			{
				fillPuzzleWordsList(wordCount, words);			//call the method that will randomly select the required number of words from the file and put them in the puzzleWords list
				setGridDimensions(findLongestWordInList());		//Find the length of the longest word in the list and pass this information to the setGridDimensions method.
				puzzle = new char[height][width];				//create a 2d array with the required dimensions (calculated by the setGridDimensions method)
				fillGridWithHyphens();							//fill the whole grid with hyphens
				generateWordSearchPuzzle();						//place all the words and fill the remaining spaces with randomly generated letters.
			}
		}
	}
	
	// Method to find the longest word in the puzzleWords list
	private int findLongestWordInList()
	{
		int longestWordLength = puzzleWords.get(0).length();	//Make the assumption that the first word is the longest
		for (String s : puzzleWords)
		{
			if (s.length() > longestWordLength)					//then check all the words and find the longest
			{
				longestWordLength = s.length();
			}
		}
		return longestWordLength;								
	}
	
	//This method picks a random word from the 'words' list and if it is not already in the puzzleWords list, adds it to the puzzleWords list 
	private void fillPuzzleWordsList(int wordCount, List<String> words)
	{
		while (puzzleWords.size() < wordCount)		//keep adding words until we reach the required amount specified by the user
		{
			int randomNum = (int)(Math.random() * words.size());
			String wordFromList = words.get(randomNum);
			if(!puzzleWords.contains(wordFromList))		
			{
				puzzleWords.add(wordFromList);
			}	
		}
	}
	
	//This method reads every line from the file and fills the 'words' list with any valid words. The method returns a boolean which indicates whether the file is empty or not
	private boolean readWordsFromFile(String wordFile, List<String> words, int shortest, int longest)
	{
		boolean fileIsEmpty = true;		//assume the file is empty
		try 
		{
			FileReader aFileReader = new FileReader(wordFile);
			BufferedReader aBufferReader = new BufferedReader(aFileReader);
			String lineFromFile = aBufferReader.readLine();
			while(lineFromFile != null)		
			{
				String pattern = "[a-zA-Z]+";	//use this pattern to check that the word contains alphabetic characters only
				if (lineFromFile.length() >= shortest && lineFromFile.length() <= longest && lineFromFile.matches(pattern))
				{
					words.add(lineFromFile.toUpperCase());	//if the word in the file is the required length and matches our pattern, add it to the words list
				}
				fileIsEmpty = false;	//a line has been read so the file is not empty								
				lineFromFile = aBufferReader.readLine();	//read the next line
			}
			aBufferReader.close();
			aFileReader.close();
		}
		catch(IOException x)	
		{
			System.out.println("\nInvalid filename.\n\n");
			System.exit(0);
		}
		return fileIsEmpty;
	}
	
	//This method returns the (sorted) list of words used in the puzzle
	public List<String> getWordSearchList()
	{
		Collections.sort(puzzleWords);
		return puzzleWords;
	}
	
 	//This method returns the generated grid as a 2d array of characters
	public char[][] getPuzzleAsGrid()
	{
		return puzzle;
	}
	
	//This method returns the generated grid as a string
	public String getPuzzleAsString()
	{
		String gridAsString = "";
		for(int i = 0; i < height; i++)		
		{
			for(int j = 0; j < width; j++)
			{
				gridAsString += puzzle[i][j]+ " ";	
			}
			gridAsString += "\n";
		}
		return gridAsString;
	}
	
	//This method displays the grid and the list of words used to create it
	public void showWordSearchPuzzle(boolean hide)
	{
		System.out.println("\nThe puzzle with unused positions filled with random character...\n");
		System.out.println(getPuzzleAsString());
		if (hide)
		{
			System.out.println("\nThe puzzle words in alphabetical order...\n");
			for(String s : getWordSearchList())
			{
				System.out.println(s);
			}
			System.out.println();
		}
		else
		{
			System.out.println("The puzzle words in alphabetical order showing their [row][col] positions and directions...\n");
			Collections.sort(wordsAndTheirLocations);		//use the list we filled in the generateWordSearchPuzzle method
			for (String s : wordsAndTheirLocations)
			{
				System.out.println(s);
			}
			System.out.println();
		}
	}
	
	//This method is responsible for implementing the creation of the puzzle
	private void generateWordSearchPuzzle()
	{
		//Randomly place each word on the grid
		int row, col, i = 0;
		String[] directions = {"N", "S", "E", "W", "NE", "NW", "SW", "SE"};
		String word, direction;
		wordsAndTheirLocations = new ArrayList<String>(puzzleWords.size());	
		
		while (i < puzzleWords.size())	//keep placing words until we reach the end of the puzzleWords list
		{
			row = (int)(Math.random() * height);	//generate a random position on the grid and a random direction
			col = (int)(Math.random() * width);									
			direction = directions[(int)(Math.random() * directions.length)];	
			word = puzzleWords.get(i);		//get a word from the puzzleWords list
			boolean roomForWord = true;
			
			if ((puzzle[row][col] == '-') || (puzzle[row][col] == word.charAt(0)))	//if the random cell chosen is occupied by a hyphen or the contents of the cell is equal to the first letter of the word to be placed (this second part is to enable interlocking words).
			{	//This: '|| (puzzle[row][col] == word.charAt(0))' allows it to interlock words
				if (direction.equals("N"))	//if direction is Up
				{
					if (word.length()-1 <= row)			//if the word can fit in the grid by starting at the current row,col position and moving upwards
					{
						for (int j = 1; j < word.length() && roomForWord; j++) 		//check all the required cells in the up direction and see if they are occupied
						{
							if ((puzzle[row - j][col] != '-') && (puzzle[row - j][col] != (word.charAt(j))))	//if the cell does not contain a hyphen and the letter in the cell is not equal to the corresponding letter in the word we are trying to place
							{	//This: '&& (puzzle[row - j][col] != (word.charAt(j)))' allows it to interlock words
								roomForWord = false;	//the word cannot be placed
							}
						}
						if (roomForWord) 	//if all is ok then we can place the word 
						{						
							for (int j = 0; j < word.length() && roomForWord; j++) 
							{
								puzzle[row - j][col] = word.charAt(j);		//place each letter of the word 
							}
							wordsAndTheirLocations.add(word + " [" + row + "][" + col + "]" + direction);	//Store the word that was placed and it's location in a list which can be used by the 'showWordSearchPuzzle' method later.
							i++;	//ie move onto the next word in the list)	
						}
					}
				} 
				 
				else if (direction.equals("S"))		//if direction is Down
				{
					if ((word.length()-1 + row) < height)
					{
						for (int j = 1; j < word.length() && roomForWord; j++)
						{
							if ((puzzle[row + j][col] != '-') && (puzzle[row + j][col] != (word.charAt(j))))
							{
								roomForWord = false;
							}
						}
						if (roomForWord)
						{ 
							for (int j = 0; j < word.length(); j++)
							{
								puzzle[row + j][col] = word.charAt(j);
							}
							wordsAndTheirLocations.add(word + " [" + row + "][" + col + "]" + direction);
							i++;
						}
					}
				}
				
				else if (direction.equals("W"))		//if direction is Left
				{
					if ((col - (word.length()-1)) >= 0)
					{	
						for (int j = 1; j < word.length() && roomForWord; j++)
						{
							if ((puzzle[row][col - j] != '-') && (puzzle[row][col - j] != (word.charAt(j))))
							{
								roomForWord = false;
							}
						}
						if (roomForWord)
						{
							for (int j = 0; j < word.length(); j++)
							{
								puzzle[row][col - j] = word.charAt(j);
							}
							wordsAndTheirLocations.add(word + " [" + row + "][" + col + "]" + direction);
							i++;
						}
					}
				}
				
				else if (direction.equals("E"))  //if direction is Right
				{
					if ((col + (word.length()-1)) < width)
					{
						for (int j = 1; j < word.length() && roomForWord; j++)
						{
							if ((puzzle[row][col + j] != '-') && (puzzle[row][col + j] != (word.charAt(j))))
							{
								roomForWord = false;
							}
						}
						if (roomForWord)
						{
							for (int j = 0; j < word.length(); j++)
							{
								puzzle[row][col + j] = word.charAt(j);
							}
							wordsAndTheirLocations.add(word + " [" + row + "][" + col + "]" + direction);
							i++;
						}
					}
				}  
				
				else if (direction == "SE")		//diagonal placement
				{
					if ((row + (word.length()-1)) < height && (col + (word.length()-1)) < width)
					{
						for (int j = 1; j < word.length(); j++)
						{
							if ((puzzle[row+j][col+j] != '-')  && (puzzle[row+j][col+j] != (word.charAt(j))))
							{
								roomForWord = false;
							}
						}
						if (roomForWord)
						{
							for (int j = 0; j < word.length(); j++)
							{
								puzzle[row+j][col+j] = word.charAt(j);
							}
							wordsAndTheirLocations.add(word + " [" + row + "][" + col + "]" + direction);
							i++;
						}
					}
				}
				
				else if (direction == "NE")		//diagonal placement
				{
					if (row - (word.length() - 1) >= 0 && col + (word.length() - 1) < width)
					{
						for (int j = 1; j < word.length(); j++)
						{
							if ((puzzle[row-j][col+j] != '-') && (puzzle[row-j][col+j] != (word.charAt(j))))
							{
								roomForWord = false;
							}
						}
						if (roomForWord)
						{
							for (int j = 0; j < word.length(); j++)
							{
								puzzle[row-j][col+j] = word.charAt(j);								
							}
							wordsAndTheirLocations.add(word + " [" + row + "][" + col + "]" + direction);
							i++;
						}
					}
				}
				
				else if (direction == "NW")		//diagonal placement
				{
					if (row - (word.length() - 1) >= 0 && col - (word.length() - 1) >= 0)
					{
						for (int j = 1; j < word.length(); j++)
						{
							if ((puzzle[row-j][col-j] != '-') && (puzzle[row-j][col-j] != (word.charAt(j))))
							{
								roomForWord = false;
							}
						}
						if (roomForWord)
						{
							for (int j = 0; j < word.length(); j++)
							{
								puzzle[row-j][col-j] = word.charAt(j);								
							}
							wordsAndTheirLocations.add(word + " [" + row + "][" + col + "]" + direction);
							i++;
						}
					}
				}
						//diagonal placement
				else 	//direction is SW		
				{
					if (row + (word.length() - 1) < height && col - (word.length() - 1) >= 0)
					{
						for (int j = 1; j < word.length(); j++)
						{
							if ((puzzle[row+j][col-j] != '-') && (puzzle[row+j][col-j] != (word.charAt(j))))
							{
								roomForWord = false;
							}
						}
						if (roomForWord)
						{
							for (int j = 0; j < word.length(); j++)
							{
								puzzle[row+j][col-j] = word.charAt(j);								
							}
							wordsAndTheirLocations.add(word + " [" + row + "][" + col + "]" + direction);
							i++;
						}
					}
				}   
			}
		}
		
		//After all the words have been placed, fill the remaining grid positions with random letters
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int j = 0; j < height; j++)	
		{
			for(int k = 0; k < width; k++)
			{
				if(puzzle[j][k] == '-')
				{
					puzzle[j][k] = alphabet.charAt((int)(Math.random() * alphabet.length()));
				}
			}
		}
	}
	
	// Set the grid size based on the sum of the lengths of the words in the puzzleWords list.
	private void setGridDimensions(int lengthOfLongestWord)	
	{
		int sumOfCharsInWords = 0;
		for(String s : puzzleWords)
		{
			sumOfCharsInWords += s.length();
		}
		double numOfCharsInGrid = sumOfCharsInWords * scalingFactor;
		height = (int)(Math.ceil(Math.sqrt(numOfCharsInGrid)));
		width = (int)(Math.ceil(Math.sqrt(numOfCharsInGrid)));
		if (lengthOfLongestWord >= height)		//if the height (and width) value calculated is smaller than or equal to the length of the longest word from the puzzleWords list
		{
			height = lengthOfLongestWord + 1;	//increase the height and width so that every word from the list will fit.
			width = lengthOfLongestWord + 1;
		}
	}
	
	// Fill the initial grid with hyphens
	private void fillGridWithHyphens()
	{
		for(int i = 0; i < height; i++) 
		{
			for(int j = 0; j < width; j++) 
			{
				puzzle[i][j] = '-';
			}
		}
	}
}







