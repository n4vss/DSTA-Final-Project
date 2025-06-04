package WordGuessingGame;

import java.util.*;

public class GameLogic {
    // Game state variables
    private String wordToGuess;
    private char[] progress;
    private int attemptsLeft;
    private final int maxAttempts = 6;
    private final WordBank wordBank = new WordBank();
    private final Scanner scanner = new Scanner(System.in);
    private boolean gameActive = true;

    // Data Structure 1: HashSet for tracking guessed letters
    private final Set<Character> guessedLetters = new HashSet<>();
    
    // Data Structure 2: Stack for undo functionality
    private final Stack<Character> guessHistory = new Stack<>();
    
    // Data Structure 3: Queue for managing hints
    private final Queue<String> hintQueue = new LinkedList<>();
    
    // Data Structure 4: HashMap for tracking letter positions
    private final Map<Character, List<Integer>> letterPositions = new HashMap<>();
    
    // Data Structure 5: ArrayList for tracking game history
    private final List<String> gameHistory = new ArrayList<>();

    public void startGameLoop() {
        while (gameActive) {
            showMainMenu();
            chooseDifficulty();
            initializeGame();
            playGame();
            askToPlayAgain();
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Word Guessing Game ===");
        System.out.println("1. Start New Game");
        System.out.println("2. View Previous Games");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                break;
            case "2":
                displayGameHistory();
                break;
            case "3":
                gameActive = false;
                System.out.println("Thanks for playing!");
                System.exit(0);
            default:
                System.out.println("Starting new game...");
        }
    }

    private void chooseDifficulty() {
        System.out.println("\n=== Difficulty Levels ===");
        System.out.println("1. Easy (Simple words)");
        System.out.println("2. Medium (Common words)");
        System.out.println("3. Hard (Complex words)");
        System.out.print("Choose difficulty (1-3): ");
        
        String choice = scanner.nextLine().trim();
        String difficulty = switch (choice) {
            case "1" -> "easy";
            case "2" -> "medium";
            case "3" -> "hard";
            default -> {
                System.out.println("Invalid choice. Defaulting to Medium.");
                yield "medium";
            }
        };
        
        wordToGuess = wordBank.getRandomWord(difficulty);
        hintQueue.addAll(wordBank.getHints(wordToGuess));
        mapLetterPositions();
    }

    private void mapLetterPositions() {
        for (int i = 0; i < wordToGuess.length(); i++) {
            char c = wordToGuess.charAt(i);
            letterPositions.computeIfAbsent(c, k -> new ArrayList<>()).add(i);
        }
    }

    private void initializeGame() {
        progress = new char[wordToGuess.length()];
        Arrays.fill(progress, '_');
        guessedLetters.clear();
        guessHistory.clear();
        attemptsLeft = maxAttempts;
        gameHistory.add("New game started with word: " + wordToGuess);
    }

    private void playGame() {
        System.out.println("\nGame started! The word has " + wordToGuess.length() + " letters.");
        
        while (!isGameOver()) {
            displayGameState();
            processUserInput();
        }
        
        displayResult();
        recordGameResult();
    }

    private void displayGameState() {
        System.out.println("\nWord: " + String.valueOf(progress));
        System.out.println("Guessed letters: " + String.join(", ", getSortedGuesses()));
        System.out.println("Attempts left: " + attemptsLeft);
        System.out.println("Hints remaining: " + hintQueue.size());
        System.out.println("Commands: hint, undo, restart, quit");
    }

    private List<String> getSortedGuesses() {
        return guessedLetters.stream()
                           .sorted()
                           .map(String::valueOf)
                           .toList();
    }

    private void processUserInput() {
        System.out.print("\nEnter a letter or command: ");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.isEmpty()) return;

        switch (input) {
            case "hint" -> showHint();
            case "undo" -> undoGuess();
            case "restart" -> { 
                System.out.println("Restarting game...");
                initializeGame();
            }
            case "quit" -> {
                gameActive = false;
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
            default -> {
                if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                    makeGuess(input.charAt(0));
                } else {
                    System.out.println("Invalid input. Please enter a single letter or valid command.");
                }
            }
        }
    }

    private void makeGuess(char guess) {
        if (guessedLetters.contains(guess)) {
            System.out.println("You already guessed '" + guess + "'");
            return;
        }

        guessedLetters.add(guess);
        guessHistory.push(guess);
        gameHistory.add("Guessed: " + guess);

        if (letterPositions.containsKey(guess)) {
            updateProgress(guess);
            System.out.println("Correct guess!");
        } else {
            attemptsLeft--;
            System.out.println("Wrong guess! Attempts left: " + attemptsLeft);
        }
    }

    private void updateProgress(char guess) {
        for (int pos : letterPositions.get(guess)) {
            progress[pos] = guess;
        }
    }

    private void showHint() {
        if (!hintQueue.isEmpty()) {
            System.out.println("Hint: " + hintQueue.poll());
            gameHistory.add("Used a hint");
        } else {
            System.out.println("No more hints available.");
        }
    }

    private void undoGuess() {
        if (guessHistory.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        char lastGuess = guessHistory.pop();
        guessedLetters.remove(lastGuess);
        gameHistory.add("Undid guess: " + lastGuess);
        
        if (letterPositions.containsKey(lastGuess)) {
            // Rebuild progress without the undone correct guess
            Arrays.fill(progress, '_');
            for (char c : guessedLetters) {
                if (letterPositions.containsKey(c)) {
                    updateProgress(c);
                }
            }
        } else {
            attemptsLeft++; // Only increment if it was a wrong guess
        }
        
        System.out.println("Undid guess: " + lastGuess);
    }

    private boolean isGameOver() {
        return attemptsLeft <= 0 || String.valueOf(progress).equals(wordToGuess);
    }

    private void displayResult() {
        if (String.valueOf(progress).equals(wordToGuess)) {
            System.out.println("\nCongratulations! You guessed the word: " + wordToGuess);
            System.out.println("You had " + attemptsLeft + " attempts remaining.");
        } else {
            System.out.println("\nGame over! The word was: " + wordToGuess);
        }
    }

    private void recordGameResult() {
        String result = "Game result: " + 
            (String.valueOf(progress).equals(wordToGuess) ? "Won" : "Lost") + 
            " with word: " + wordToGuess;
        gameHistory.add(result);
    }

    private void askToPlayAgain() {
        System.out.print("\nWould you like to play again? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        
        if (!response.startsWith("y")) {
            gameActive = false;
            System.out.println("Thanks for playing!");
        }
    }

    private void displayGameHistory() {
        System.out.println("\n=== Game History ===");
        if (gameHistory.isEmpty()) {
            System.out.println("No previous games recorded.");
        } else {
            gameHistory.forEach(System.out::println);
        }
        System.out.println();
    }
}