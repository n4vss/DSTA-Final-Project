package WordGuessingGame;

import java.util.*;

public class WordBank {
    // HashMap for word categories
    private final Map<String, List<String>> wordMap = new HashMap<>();
    // HashMap for word hints
    private final Map<String, List<String>> hintMap = new HashMap<>();
    private final Random random = new Random();

    public WordBank() {
        initializeWords();
        initializeHints();
    }

    private void initializeWords() {
        // ArrayList for easy words
        wordMap.put("easy", new ArrayList<>(Arrays.asList(
            "apple", "dog", "fish", "book", "tree", 
            "moon", "car", "cat", "milk", "shoe",
            "bird", "rain", "snow", "star", "hand"
        )));

        // ArrayList for medium words
        wordMap.put("medium", new ArrayList<>(Arrays.asList(
            "banana", "wizard", "planet", "guitar", "rocket",
            "garden", "window", "pirate", "castle", "bridge",
            "dolphin", "forest", "puzzle", "sunset", "island"
        )));

        // ArrayList for hard words
        wordMap.put("hard", new ArrayList<>(Arrays.asList(
            "algorithm", "encyclopedia", "microscope", "hemoglobin", "parliament",
            "philosophy", "revolutionary", "neuroscience", "constitution", "hypothesis",
            "photosynthesis", "astrophysics", "cryptography", "metamorphosis", "extraterrestrial"
        )));
    }

    private void initializeHints() {
        // ArrayList for hints per word
        addHint("apple", "A common fruit that grows on trees");
        addHint("apple", "Comes in red, green, and yellow varieties");
        addHint("dog", "Man's best friend");
        addHint("dog", "Known for loyalty and barking");
        addHint("fish", "Lives in water and has gills");
        addHint("banana", "Yellow tropical fruit");
        addHint("banana", "Monkeys love this fruit");
        addHint("wizard", "Magical practitioner");
        addHint("wizard", "Often wears a robe and hat");
        addHint("algorithm", "Step-by-step procedure for calculations");
        addHint("algorithm", "Essential in computer programming");
    }

    private void addHint(String word, String hint) {
        hintMap.computeIfAbsent(word, k -> new ArrayList<>()).add(hint);
    }

    public String getRandomWord(String difficulty) {
        List<String> words = wordMap.getOrDefault(difficulty.toLowerCase(), wordMap.get("easy"));
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getHints(String word) {
        return hintMap.getOrDefault(word, Arrays.asList("No hints available for this word."));
    }
}