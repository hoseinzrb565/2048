import java.util.Random;
import java.util.Scanner;

//2048 program
public class Game {

	// number of rows and columns of the array containing the numbers
	private static final int DIMENSIONS = 4;
	// number of rows and columns of the whole table(lines and numbers)
	private static final int TABLE_ROWS = 9;
	private static final int TABLE_columnS = 29;
	// number of digits of the maximum number possible to achieve in the game
	// the number is 131072
	private static int MAX_DIGIT = 6;
	// two dimensional array containing the numbers
	private static int[][] numbers = new int[DIMENSIONS][DIMENSIONS];
	// random number generator
	private final static Random randomNumber = new Random();
	// getting a character input
	private static Scanner input = new Scanner(System.in);

	// start the program
	public static void main(String[] args) {
		playGame();
	}

	// playing the game
	public static void playGame() {
		// generate a 2 or a 4 and put it in a random square
		randomSquare();
		randomSquare();
		// print the initial table
		printTable();
		// play game
		do {
			
			// get character input and check if the move is possible
			char ch;
			do {
				ch = input.next().charAt(0);
				// check if the character is valid and the desired move is possible
				if (ch == 'w' && checkUp())
					break;

				else if (ch == 's' && checkDown())
					break;

				else if (ch == 'd' && checkRight())
					break;

				else if (ch == 'a' && checkLeft())
					break;

			} while (true);

			//print new lines
			for(int newLine = 0; newLine < 20; newLine++)
				System.out.printf("%n");
			// perform the turn
			move(ch);
			randomSquare();
			// print new table
			printTable();
		} while (!checkOver());

		System.out.printf("%n%nGame Over!");
		return;
	}

	// generate a 2 or a 4 and put it in a random square
	public static void randomSquare() {
		// the square's indices
		int row;
		int column;
		// generate random empty square
		do {
			row = randomNumber.nextInt(DIMENSIONS);
			column = randomNumber.nextInt(DIMENSIONS);
		} while (numbers[row][column] != 0);

		// generate a 2 or a 4
		int num = randomNumber.nextInt(10);
		if (num == 0)
			num = 4;
		else
			num = 2;
		// put the 2 or the 4 in the random square
		numbers[row][column] = num;
	}

	// perform the move
	public static void move(char character) {
		if (character == 'w')
			moveUp();
		else if (character == 's')
			moveDown();
		else if (character == 'a')
			moveLeft();
		else if (character == 'd')
			moveRight();
	}

	// move the tiles up
	public static void moveUp() {

		// possibly merge the appropriate tiles
		for (int column = 0; column < DIMENSIONS; column++) {
			for (int pass1 = 0; pass1 < DIMENSIONS - 1; pass1++) {
				int first = numbers[pass1][column];
				if (first == 0)
					continue;
				else {
					for (int pass2 = pass1 + 1; pass2 < DIMENSIONS; pass2++) {
						int second = numbers[pass2][column];
						if (second == 0)
							continue;
						else if (first != second)
							break;
						else if (first == second) {
							// merge
							numbers[pass1][column] += second;
							numbers[pass2][column] = 0;
							break;
						}
					}
				}
			}
		}

		// push the tiles upwards
		for (int column = 0; column < DIMENSIONS; column++) {
			for (int pass1 = 0; pass1 < DIMENSIONS - 1; pass1++) {
				for (int pass2 = pass1 + 1; pass2 < DIMENSIONS; pass2++) {
					int first = numbers[pass1][column];
					int second = numbers[pass2][column];
					if (first == 0 && second != 0) {
						// swap
						numbers[pass1][column] = second;
						numbers[pass2][column] = first;
					}
				}
			}
		}
	}

	// move the tiles down
	public static void moveDown() {
		// possibly merge the appropriate tiles
		for (int column = 0; column < DIMENSIONS; column++) {
			for (int pass1 = DIMENSIONS - 1; pass1 > 0; pass1--) {
				int first = numbers[pass1][column];
				if (first == 0)
					continue;
				else {
					for (int pass2 = pass1 - 1; pass2 >= 0; pass2--) {
						int second = numbers[pass2][column];
						if (second == 0)
							continue;
						else if (first != second)
							break;
						else if (first == second) {
							// merge
							numbers[pass1][column] += second;
							numbers[pass2][column] = 0;
							break;
						}
					}
				}
			}
		}

		// push the tiles downwards
		for (int column = 0; column < DIMENSIONS; column++) {
			for (int pass1 = DIMENSIONS - 1; pass1 > 0; pass1--) {
				for (int pass2 = pass1 - 1; pass2 >= 0; pass2--) {
					int first = numbers[pass1][column];
					int second = numbers[pass2][column];
					if (first == 0 && second != 0) {
						// swap
						numbers[pass1][column] = second;
						numbers[pass2][column] = first;
					}
				}
			}
		}
	}

	// move the tiles to the left
	public static void moveLeft() {
		// possibly merge the appropriate tiles
		for (int row = 0; row < DIMENSIONS; row++) {
			for (int pass1 = 0; pass1 < DIMENSIONS - 1; pass1++) {
				int first = numbers[row][pass1];
				if (first == 0)
					continue;
				for (int pass2 = pass1 + 1; pass2 < DIMENSIONS; pass2++) {
					int second = numbers[row][pass2];
					if (second == 0)
						continue;
					else if (first != second)
						break;
					else if (first == second) {
						// merge
						numbers[row][pass1] += second;
						numbers[row][pass2] = 0;
						break;
					}
				}
			}
		}

		// push the tiles to the left

		for (int row = 0; row < DIMENSIONS; row++) {
			for (int pass1 = 0; pass1 < DIMENSIONS - 1; pass1++) {
				for (int pass2 = pass1 + 1; pass2 < DIMENSIONS; pass2++) {
					int first = numbers[row][pass1];
					int second = numbers[row][pass2];
					if (first == 0 && second != 0) {
						// swap
						numbers[row][pass1] = second;
						numbers[row][pass2] = first;
					}
				}
			}
		}
	}

	// move the tiles to the right
	public static void moveRight() {
		// possibly merge the appropriate tiles
		for (int row = 0; row < DIMENSIONS; row++) {
			for (int pass1 = DIMENSIONS - 1; pass1 > 0; pass1--) {
				int first = numbers[row][pass1];
				if (first == 0)
					continue;
				for (int pass2 = pass1 - 1; pass2 >= 0; pass2--) {
					int second = numbers[row][pass2];
					if (second == 0)
						continue;
					else if (first != second)
						break;
					else if (first == second) {
						// merge
						numbers[row][pass1] += second;
						numbers[row][pass2] = 0;
						break;
					}
				}
			}
		}

		// push the tiles to the right
		for (int row = 0; row < DIMENSIONS; row++) {
			for (int pass1 = DIMENSIONS - 1; pass1 > 0; pass1--) {
				for (int pass2 = pass1 - 1; pass2 >= 0; pass2--) {
					int first = numbers[row][pass1];
					int second = numbers[row][pass2];
					if (first == 0 && second != 0) {
						// swap
						numbers[row][pass1] = second;
						numbers[row][pass2] = first;
					}
				}
			}
		}
	}

	// checks if any move is possible
	public static boolean checkOver() {
		/* check for empty squares */
		for (int row = 0; row < DIMENSIONS; row++) {
			for (int column = 0; column < DIMENSIONS; column++) {
				if (numbers[row][column] == 0)
					return false;
			}
		}

		/* check for identical adjacent squares */
		// check horizontally
		for (int row = 0; row < DIMENSIONS; row++) {
			for (int column = 0; column < DIMENSIONS - 1; column++) {
				int first = numbers[row][column];
				int second = numbers[row][column + 1];
				if (first == second)
					return false;
			}
		}

		// check vertically
		for (int column = 0; column < DIMENSIONS; column++) {
			for (int row = 0; row < DIMENSIONS - 1; row++) {
				int first = numbers[row][column];
				int second = numbers[row + 1][column];
				if (first == second)
					return false;
			}
		}

		return true;
	}

	// check if a move to the left is possible
	public static boolean checkLeft() {
		for (int row = 0; row < DIMENSIONS; row++) {
			// check if row is empty
			boolean empty = true;
			for (int column = 0; column < DIMENSIONS; column++) {
				if (numbers[row][column] != 0)
					empty = false;
			}
			// move on to the next row
			if (empty == true)
				continue;
			// row is not empty
			else {
				for (int pass1 = 0; pass1 < DIMENSIONS - 1; pass1++) {
					int first = numbers[row][pass1];
					// element on the first pass is empty
					if (first == 0) {
						// search for a full element on the right of the first element
						for (int pass2 = pass1 + 1; pass2 < DIMENSIONS; pass2++) {
							int second = numbers[row][pass2];
							// there exists a full element on the right side of the first element
							if (second != 0)
								return true;
						}
					}
					// element on the first pass is full
					else {
						// search for an element on the right side of the first element
						// which has the same value as the first element
						for (int pass2 = pass1 + 1; pass2 < DIMENSIONS; pass2++) {
							int second = numbers[row][pass2];
							// there exists an element on the right side of the first element
							// which has the same value as the first element
							if(first != second)
								break;
							else
								return true;
						}
					}
				}
			}
		}
		return false;
	}

	// check if a move to the right is possible
	public static boolean checkRight() {
		for (int row = 0; row < DIMENSIONS; row++) {
			// check if row is empty
			boolean empty = true;
			for (int column = 0; column < DIMENSIONS; column++) {
				if (numbers[row][column] != 0)
					empty = false;
			}
			// move on to the next row
			if (empty == true)
				continue;
			// row is not empty
			else {
				for (int pass1 = DIMENSIONS - 1; pass1 > 0; pass1--) {
					int first = numbers[row][pass1];
					// element on the first pass is empty
					if (first == 0) {
						// search for a full element on the left of the first element
						for (int pass2 = pass1 - 1; pass2 >= 0; pass2--) {
							int second = numbers[row][pass2];
							// there exists a full element on the left side of the first element
							if (second != 0)
								return true;
						}
					}
					// element on the first pass is full
					else {
						// search for an element on the left side of the first element
						// which has the same value as the first element
						for (int pass2 = pass1 - 1; pass2 >= 0; pass2--) {
							int second = numbers[row][pass2];
							// there exists an element on the right side of the first element
							// which has the same value as the first element
							if(first != second)
								break;
							else
								return true;
						}
					}
				}
			}
		}
		return false;
	}

	// check if an upwards move is possible
	public static boolean checkUp() {
		for (int column = 0; column < DIMENSIONS; column++) {
			// check if column is empty
			boolean empty = true;
			for (int row = 0; row < DIMENSIONS; row++) {
				if (numbers[row][column] != 0)
					empty = false;
			}
			// move on to the next column
			if (empty == true)
				continue;
			// column is not empty
			else {
				for (int pass1 = 0; pass1 < DIMENSIONS - 1; pass1++) {
					int first = numbers[pass1][column];
					// element on the first pass is empty
					if (first == 0) {
						// search for a full element below the first element
						for (int pass2 = pass1 + 1; pass2 < DIMENSIONS; pass2++) {
							int second = numbers[pass2][column];
							// there exists a full element on the right side of the first element
							if (second != 0)
								return true;
						}
					}
					// element on the first pass is full
					else {
						// search for an element below the first element
						// which has the same value as the first element
						for (int pass2 = pass1 + 1; pass2 < DIMENSIONS; pass2++) {
							int second = numbers[pass2][column];
							// there exists an element on the right side of the first element
							// which has the same value as the first element
							if(first != second)
								break;
							else
								return true;
						}
					}
				}
			}
		}
		return false;
	}

	// check if a downwards move is possible
	public static boolean checkDown() {
		for (int column = 0; column < DIMENSIONS; column++) {
			// check if column is empty
			boolean empty = true;
			for (int row = 0; row < DIMENSIONS; row++) {
				if (numbers[row][column] != 0)
					empty = false;
			}
			// move on to the next column
			if (empty == true)
				continue;
			// column is not empty
			else {
				for (int pass1 = DIMENSIONS - 1; pass1 > 0; pass1--) {
					int first = numbers[pass1][column];
					// element on the first pass is empty
					if (first == 0) {
						// search for a full element above of the first element
						for (int pass2 = pass1 - 1; pass2 >= 0; pass2--) {
							int second = numbers[pass2][column];
							// there exists a full element above the first element
							if (second != 0)
								return true;
						}
					}
					
					// element on the first pass is full
					else {
						// search for an element above the first element
						// which has the same value as the first element
						for (int pass2 = pass1 - 1; pass2 >= 0; pass2--) {
							int second = numbers[pass2][column];
							// there exists an element on the right side of the first element
							// which has the same value as the first element
							if(first != second)
								break;
							else
								return true;
							
						}
					}
				}
			}
		}
		return false;
	}

	// prints the table
	public static void printTable() {
		//instructions
		System.out.printf("move with 'w', 's', 'd' and 'a'%n%n");
		//printing the table
		for (int row = 0; row < TABLE_ROWS; row++) {
			if (row % 2 == 0)// rows only containing lines
			{
				for (int column = 0; column < TABLE_columnS; column++) {
					System.out.print("-");
				}
			}

			else// rows containing lines and numbers
			{
				for (int column = 0; column < TABLE_columnS; column++) {
					if (column % (MAX_DIGIT + 1) == 0)// "|" shaped line
					{
						System.out.print("|");
					}

					else// number
					{
						// convert table indices to number array indices
						int numberArrayRow = ((row + 1) / 2) - 1;
						int numberArraycolumn = (column - 1) / (MAX_DIGIT + 1);
						// print the number at the specified indices
						int element = numbers[numberArrayRow][numberArraycolumn];
						if (element == 0) {
							for(int space = 0; space < MAX_DIGIT; space++)
								System.out.print(" ");
						} else {
							System.out.printf("%-6d", element);
						}

						// shift the column to the index of the next non-number character
						column += MAX_DIGIT - 1;
					}
				}
			}
			//next row
			System.out.println();
		}
	}
}
