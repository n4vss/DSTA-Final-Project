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
    // Easy words
    addHint("apple", "A common fruit that grows on trees");
    addHint("apple", "Comes in red, green, and yellow varieties");
    addHint("dog", "Man's best friend");
    addHint("dog", "Known for loyalty and barking");
    addHint("fish", "Lives in water and has gills");
    addHint("fish", "Has scales and swims");
    addHint("book", "You read this for knowledge or stories");
    addHint("book", "Often found in a library");
    addHint("tree", "Tall plant with leaves and branches");
    addHint("tree", "Produces oxygen and provides shade");
    addHint("moon", "Orbits the Earth");
    addHint("moon", "Visible at night and has phases");
    addHint("car", "Used for transportation on roads");
    addHint("car", "Has four wheels and an engine");
    addHint("cat", "Independent and curious animal");
    addHint("cat", "Famous for chasing mice");
    addHint("milk", "White liquid from cows");
    addHint("milk", "Commonly used in cereals");
    addHint("shoe", "Worn on the feet");
    addHint("shoe", "Comes in pairs and many styles");
    addHint("bird", "Can fly and has feathers");
    addHint("bird", "Builds nests and lays eggs");
    addHint("rain", "Water droplets falling from clouds");
    addHint("rain", "Often comes with thunderstorms");
    addHint("snow", "Frozen water that falls in winter");
    addHint("snow", "Forms snowflakes and covers ground");
    addHint("star", "Shines in the night sky");
    addHint("star", "Our sun is one of these");
    addHint("hand", "Used to hold or grab things");
    addHint("hand", "Has five fingers");

    // Medium words
    addHint("banana", "Yellow tropical fruit");
    addHint("banana", "Monkeys love this fruit");
    addHint("wizard", "Magical practitioner");
    addHint("wizard", "Often wears a robe and hat");
    addHint("planet", "Orbits a star");
    addHint("planet", "Earth is one");
    addHint("guitar", "A string instrument");
    addHint("guitar", "Played with fingers or a pick");
    addHint("rocket", "Flies into space");
    addHint("rocket", "Powered by fuel and engines");
    addHint("garden", "Place to grow plants or flowers");
    addHint("garden", "May contain vegetables or herbs");
    addHint("window", "Transparent opening in a wall");
    addHint("window", "Lets light and air into a room");
    addHint("pirate", "Sails the seas looking for treasure");
    addHint("pirate", "Wears an eye patch or has a parrot");
    addHint("castle", "Large building often from medieval times");
    addHint("castle", "Usually has towers and thick walls");
    addHint("bridge", "Structure for crossing over water");
    addHint("bridge", "Connects two pieces of land");
    addHint("dolphin", "Smart marine mammal");
    addHint("dolphin", "Often seen jumping in the ocean");
    addHint("forest", "Large area covered with trees");
    addHint("forest", "Home to many wild animals");
    addHint("puzzle", "Game that challenges your mind");
    addHint("puzzle", "Often has many pieces to fit together");
    addHint("sunset", "Occurs when the sun goes down");
    addHint("sunset", "Sky turns orange or pink");
    addHint("island", "Land surrounded by water");
    addHint("island", "Can be tropical or remote");

    // Hard words
    addHint("algorithm", "Step-by-step procedure for calculations");
    addHint("algorithm", "Essential in computer programming");
    addHint("encyclopedia", "Book or digital source of knowledge");
    addHint("encyclopedia", "Organized information on many topics");
    addHint("microscope", "Used to see tiny objects");
    addHint("microscope", "Common in science labs");
    addHint("hemoglobin", "Protein in red blood cells");
    addHint("hemoglobin", "Carries oxygen in the blood");
    addHint("parliament", "Group that makes laws");
    addHint("parliament", "Common in many democracies");
    addHint("philosophy", "Study of fundamental questions");
    addHint("philosophy", "Includes ethics and logic");
    addHint("revolutionary", "Involving or causing great change");
    addHint("revolutionary", "Often linked to political uprisings");
    addHint("neuroscience", "Study of the brain and nervous system");
    addHint("neuroscience", "Includes fields like psychology and biology");
    addHint("constitution", "Fundamental set of laws or principles");
    addHint("constitution", "Defines how a country is governed");
    addHint("hypothesis", "Educated guess in science");
    addHint("hypothesis", "Needs to be tested in an experiment");
    addHint("photosynthesis", "How plants make their own food");
    addHint("photosynthesis", "Uses sunlight, water, and carbon dioxide");
    addHint("astrophysics", "Study of space and celestial bodies");
    addHint("astrophysics", "Combines astronomy and physics");
    addHint("cryptography", "Science of encoding messages");
    addHint("cryptography", "Used in cybersecurity");
    addHint("metamorphosis", "Transformation process in animals");
    addHint("metamorphosis", "Caterpillar to butterfly is one example");
    addHint("extraterrestrial", "Originating outside of Earth");
    addHint("extraterrestrial", "Often associated with aliens");
}


    private void addHint(String word, String hint) {
        hintMap.computeIfAbsent(word, k -> new ArrayList<>()).add(hint);
    }

    public String getRandomWord(String difficulty) {
        List<String> words = wordMap.getOrDefault(difficulty.toLowerCase(), wordMap.get("medium"));
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getHints(String word) {
        return hintMap.getOrDefault(word, Arrays.asList("No hints available for this word."));
    }
}