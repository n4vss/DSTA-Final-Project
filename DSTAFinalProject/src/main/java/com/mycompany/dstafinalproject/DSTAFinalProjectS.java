package com.mycompany.dstafinalprojectS;

import java.util.*;

public class DSTAFinalProjectS {

    private static Scanner scanner = new Scanner(System.in);
    private static Random rand = new Random();

    private static int enemiesDefeated = 0;
    private static boolean gameRunning = true;

    private static Stack<Integer> lastHP = new Stack<>();
    private static Stack<Integer> invulnerableTurns = new Stack<>();
    private static Queue<String> effectQueue = new LinkedList<>();
    private static Map<String, Integer> enemyStats = new HashMap<>();
    private static final List<String> botNames = Collections.unmodifiableList(Arrays.asList(
            "Star Platinum", "The World", "Crazy Diamond", "Killer Queen", "King Crimson",
            "Gold Experience", "Silver Chariot", "Hierophant Green", "Magician's Red",
            "White Album", "Sticky Fingers", "Stone Free", "Made in Heaven", "Weather Report",
            "C-Moon", "Soft & Wet", "Echoes", "Aerosmith", "Death 13", "Bad Company",
            "Black Sabbath", "Man in the Mirror", "Purple Haze", "Moody Blues", "Stray Cat",
            "Sugar Mountain", "Tusk", "Ball Breaker", "Cream", "Justice", "Strength",
            "Lover", "The Fool", "Tower of Gray", "Hermit Purple", "Baby Face", "White Snake",
            "Jail House Lock", "Civil War", "Bohemian Rhapsody", "Scary Monsters", "D4C",
            "Oh! Lonesome Me", "Highway Star", "Metallica", "Green Day", "Rolling Stones",
            "Nut King Call", "Paper Moon King", "Wonder of U"
    ));

    public static void main(String[] args) {
        // Create a mutable copy of botNames
        List<String> remainingEnemies = new ArrayList<>(botNames);

        for (String name : botNames) {
            enemyStats.put(name, 500); // Base HP for all enemies
        }

        while (gameRunning) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    startNewGame(remainingEnemies);
                    break;
                case 2:
                    displayInstructions();
                    break;
                case 3:
                    System.out.println("Exiting game...");
                    gameRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("========= GAME MENU =========");
        System.out.println("1. Start New Game");
        System.out.println("2. Instructions");
        System.out.println("3. Exit");
        System.out.println("=============================");
        System.out.print("Enter your choice: ");
    }

    private static void displayInstructions() {
        System.out.println("\n========= HOW TO PLAY =========");
        System.out.println("- You will encounter enemies one by one");
        System.out.println("- Each turn you can choose to:");
        System.out.println("  > Attack: Deal basic damage and build Jingu Mastery");
        System.out.println("  > Parry: Block 50% damage and reflect some back");
        System.out.println("  > Stasis: Stop time for 3 turns (Costs 50 MP)");
        System.out.println("  > Skip: End your turn without acting");
        System.out.println("- At 4 Jingu stacks, you get bonus damage and healing");
        System.out.println("- Enemies become invulnerable at low HP for 5 turns");
        System.out.println("- Defeat all enemies to win!\n");
    }

    private static void startNewGame(List<String> remainingEnemies) {
        enemiesDefeated = 0;
        // Reset the list of remaining enemies
        remainingEnemies.clear();
        remainingEnemies.addAll(botNames);

        while (!remainingEnemies.isEmpty() && gameRunning) {
            encounter(remainingEnemies);

            if (remainingEnemies.isEmpty()) {
                System.out.println("=================================");
                System.out.println(" CONGRATULATIONS! YOU DEFEATED ALL ENEMIES!");
                System.out.println(" Total enemies defeated: " + enemiesDefeated);
                System.out.println("=================================");
                gameRunning = false; // Add this line to exit the game after winning
                return;
            }

            System.out.println("Continue to next battle? (yes/no)");
            String continueChoice = scanner.nextLine();
            if (!continueChoice.equalsIgnoreCase("yes")) {
                gameRunning = false;
            }
        }
    }

    public static void encounter(List<String> remainingEnemies) {
        if (remainingEnemies.isEmpty()) {
            return;
        }

        String enemyName = remainingEnemies.get(rand.nextInt(remainingEnemies.size()));
        String[] skills = {"Parry", "Stasis"};

        // Get enemy HP from stats map
        int botHP = enemyStats.getOrDefault(enemyName, 500);

        int lowHpCounter = 0;
        int playerHP = 100;
        int playerMP = 100;
        int maxMP = 100;
        int manaGain = 20;
        int playerDmg = 10;
        int botDmg = 5;
        int jinguStacks = 0;
        int jinguMaxStacks = 4;
        int jinguBonusDmg = 10;
        int jinguHeal = 15;
        int jinguActiveTurns = 0;
        int stasisCounter = 0;
        int stasisCooldown = 0;
        int damageReturned = botDmg / 2;
        int damageBlocked = botDmg / 2;

        System.out.println("\n==============================");
        System.out.println("You encountered " + enemyName + "!");
        System.out.println("==============================");

        while (playerHP > 0 && botHP > 0) {
            playerMP = Math.min(playerMP + manaGain, maxMP);

            // Process any queued effects
            if (!effectQueue.isEmpty()) {
                System.out.println("Effect: " + effectQueue.poll());
            }

            System.out.println("------------------------------");
            System.out.println("Player HP: " + playerHP);
            System.out.println("Player MP: " + playerMP);
            System.out.println(enemyName + " HP: " + botHP);
            System.out.println("Jingu Mastery Stacks: " + jinguStacks);
            if (stasisCooldown > 0) {
                System.out.println("Stasis cooldown: " + stasisCooldown + " turns");
            }
            System.out.println("------------------------------");
            System.out.println("What would you like to do?");
            System.out.println(">> 1. Attack");
            System.out.println(">> 2." + skills[0]);
            System.out.println(">> 3." + skills[1] + " (Costs 50 MP)");
            System.out.println(">> 4. Skip Turn");
            System.out.println(">> 5. Flee (Quit battle)");
            System.out.println("------------------------------");
            System.out.print("Enter your choice: ");

            int c = scanner.nextInt();

            switch (c) {
                case 1:
                    if (lowHpCounter > 0) {
                        System.out.println("The enemy is resilient! No damage taken.");
                        invulnerableTurns.push(botHP);
                        lowHpCounter--;
                    } else {
                        lastHP.push(botHP);
                        botHP -= playerDmg;

                        if (jinguActiveTurns == 0) {
                            jinguStacks++;
                        }

                        System.out.println("------------------------------");
                        System.out.println("You dealt " + playerDmg + " damage to the enemy.");

                        if (jinguStacks >= jinguMaxStacks) {
                            effectQueue.add("Jingu Mastery activated!");
                            jinguActiveTurns = 4;
                            jinguStacks = 0;
                        }

                        if (jinguActiveTurns > 0) {
                            botHP -= jinguBonusDmg;
                            playerHP += jinguHeal;
                            jinguActiveTurns--;
                        }
                    }
                    break;

                case 2:
                    System.out.println(">> 50% Damage Block.");
                    System.out.println(">> " + damageReturned + " damage will be reflected.");

                    botHP -= damageReturned;
                    playerHP -= damageBlocked;
                    break;

                case 3:
                    if (stasisCooldown == 0 && playerMP >= 50) {
                        stasisCounter = 3;
                        stasisCooldown = 10;
                        playerMP -= 50;
                        effectQueue.add("You stopped time!");
                        lastHP.push(botHP);
                    } else if (stasisCooldown > 0) {
                        System.out.println("Stasis is still on cooldown for " + stasisCooldown + " turns.");
                    } else {
                        System.out.println("Not enough MP to use Stasis.");
                    }
                    break;

                case 4:
                    System.out.println("You skipped your turn.");
                    break;

                case 5:
                    System.out.println("You fled from the battle!");
                    return;

                default:
                    System.out.println("Invalid command. Your turn is wasted!");
            }

            // Enemy turn if time isn't stopped
            if (stasisCounter > 0) {
                stasisCounter--;
                System.out.println("Time's stopped. " + stasisCounter + " turns remaining.");
            } else if (botHP > 0) {
                System.out.println(enemyName + " attacks and deals " + botDmg + " damage!");
                playerHP -= botDmg;
            }

            // Check battle conditions
            if (botHP <= 0) {
                System.out.println("------------------------------");
                System.out.println("You defeated " + enemyName + "!");
                enemiesDefeated++;
                remainingEnemies.remove(enemyName);
                enemyStats.remove(enemyName); // Remove from stats map
                break;
            }

            if (playerHP <= 0) {
                System.out.println("------------------------------");
                System.out.println("You were defeated by " + enemyName + "!");
                System.out.println("Enemies defeated: " + enemiesDefeated);
                gameRunning = false;
                break;
            }

            // Cooldown and status updates
            if (stasisCooldown > 0) {
                stasisCooldown--;
            }

            if (botHP <= 20 && lowHpCounter == 0) {
                lowHpCounter = 5;
                effectQueue.add(enemyName + " has entered an invulnerable state!");
            }

            if (lowHpCounter == 0 && !invulnerableTurns.isEmpty()) {
                botHP = invulnerableTurns.pop();
                effectQueue.add("Enemy's invulnerability has ended!");
            }

            if (lowHpCounter > 0) {
                lowHpCounter--;
                if (lowHpCounter == 0) {
                    effectQueue.add("Enemy's invulnerable passive has worn off");
                    botHP = 0;
                }
            }
        }
    }
}
