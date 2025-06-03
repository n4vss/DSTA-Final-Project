package com.mycompany.dstafinalproject;

import java.util.*;

public class DSTAFinalProject {
    private static Scanner scanner = new Scanner(System.in);
    private static Random rand = new Random();
    private static Stack<Integer> jinguMastery = new Stack<>();
    private static Stack<Integer> lastHP = new Stack<>();
    private static Stack<Integer> invulnerableTurns = new Stack<>();
    private static LinkedList<String> botNames = new LinkedList<>();
    private static boolean gameRunning = true;
    private static int enemiesDefeated = 0;

    public static void main(String[] args) {
        initializeBotNames();
        
        while (gameRunning) {
            displayMainMenu();
            String choice = scanner.nextLine();
            
            switch (choice.toLowerCase()) {
                case "1":
                    startNewGame();
                    break;
                case "2":
                    displayInstructions();
                    break;
                case "3":
                    System.out.println("Exiting game...");
                    gameRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void initializeBotNames() {
        botNames.add("Star Platinum");
        botNames.add("The World");
        botNames.add("Crazy Diamond");
        botNames.add("Killer Queen");
        botNames.add("King Crimson");
        botNames.add("Gold Experience");
        botNames.add("Silver Chariot");
        botNames.add("Hierophant Green");
        botNames.add("Magician's Red");
        botNames.add("White Album");
        botNames.add("Sticky Fingers");
        botNames.add("Stone Free");
        botNames.add("Made in Heaven");
        botNames.add("Weather Report");
        botNames.add("C-Moon");
        botNames.add("Soft & Wet");
        botNames.add("Echoes");
        botNames.add("Aerosmith");
        botNames.add("Death 13");
        botNames.add("Bad Company");
        botNames.add("Black Sabbath");
        botNames.add("Man in the Mirror");
        botNames.add("Purple Haze");
        botNames.add("Moody Blues");
        botNames.add("Stray Cat");
        botNames.add("Sugar Mountain");
        botNames.add("Tusk");
        botNames.add("Ball Breaker");
        botNames.add("Cream");
        botNames.add("Justice");
        botNames.add("Strength");
        botNames.add("Lover");
        botNames.add("The Fool");
        botNames.add("Tower of Gray");
        botNames.add("Hermit Purple");
        botNames.add("Baby Face");
        botNames.add("White Snake");
        botNames.add("Jail House Lock");
        botNames.add("Civil War");
        botNames.add("Bohemian Rhapsody");
        botNames.add("Scary Monsters");
        botNames.add("D4C");
        botNames.add("Oh! Lonesome Me");
        botNames.add("Highway Star");
        botNames.add("Metallica");
        botNames.add("Green Day");
        botNames.add("Rolling Stones");
        botNames.add("Nut King Call");
        botNames.add("Paper Moon King");
        botNames.add("Wonder of U");
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
        System.out.println("\n=== HOW TO PLAY ===");
        System.out.println("- You will encounter enemies one by one");
        System.out.println("- Each turn you can choose to:");
        System.out.println("  > Attack: Deal basic damage and build Jingu Mastery");
        System.out.println("  > Meditate: Block 50% damage and reflect some back");
        System.out.println("  > Za Warudo: Stop time for 3 turns (Costs 50 MP)");
        System.out.println("  > Skip: End your turn without acting");
        System.out.println("- At 4 Jingu stacks, you get bonus damage and healing");
        System.out.println("- Enemies become invulnerable at low HP for 5 turns");
        System.out.println("- Defeat all enemies to win!\n");
    }

    private static void startNewGame() {
        enemiesDefeated = 0;
        while (!botNames.isEmpty() && gameRunning) {
            encounter();
            
            if (botNames.isEmpty()) {
                System.out.println("=================================");
                System.out.println(" CONGRATULATIONS! YOU DEFEATED ALL ENEMIES!");
                System.out.println(" Total enemies defeated: " + enemiesDefeated);
                System.out.println("=================================");
                return;
            }
            
            System.out.println("Continue to next battle? (yes/no)");
            String continueChoice = scanner.nextLine();
            if (!continueChoice.equalsIgnoreCase("yes")) {
                gameRunning = false;
            }
        }
    }

    public static void encounter() {
        if (botNames.isEmpty()) return;
        
        String enemyName = botNames.get(rand.nextInt(botNames.size()));
        String[] skills = {"Meditate", "Za Warudo"};

        int gameTimer = 2;
        int lowHpCounter = 0;
        int playerHP = 100;
        int botHP = 500;
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
        int timeStopCounter = 0;
        int zaWarudoCooldown = 0;
        int damageReturned = botDmg / 2;
        int damageBlocked = botDmg / 2;

        System.out.println("\n==============================");
        System.out.println("You encountered " + enemyName + "!");
        System.out.println("==============================");

        while (playerHP > 0 && botHP > 0) {
            playerMP = Math.min(playerMP + manaGain, maxMP);
            
            System.out.println("------------------------------");
            System.out.println("Player HP: " + playerHP);
            System.out.println("Player MP: " + playerMP);
            System.out.println(enemyName + " HP: " + botHP);
            System.out.println("Jingu Mastery Stacks: " + jinguStacks);
            if (zaWarudoCooldown > 0) {
                System.out.println("Za Warudo cooldown: " + zaWarudoCooldown + " turns");
            }
            System.out.println("------------------------------");
            System.out.println("What would you like to do?");
            System.out.println(">> Attack");
            System.out.println(">> " + skills[0]);
            System.out.println(">> " + skills[1] + " (Costs 50 MP)");
            System.out.println(">> Skip Turn");
            System.out.println(">> Flee (Quit battle)");
            System.out.println("------------------------------");

            String in = scanner.nextLine();

            if (in.equalsIgnoreCase("attack")) {
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

                    System.out.println("You dealt " + playerDmg + " damage to the enemy.");

                    if (jinguStacks >= jinguMaxStacks) {
                        System.out.println("Jingu Mastery activated! Bonus effect will last for 4 turns!");
                        jinguActiveTurns = 4;
                        jinguStacks = 0;
                    }

                    if (jinguActiveTurns > 0) {
                        System.out.println("Jingu Mastery is active! You deal " + jinguBonusDmg + " bonus damage!");
                        botHP -= jinguBonusDmg;
                        System.out.println("You lifesteal " + jinguHeal + " HP.");
                        playerHP += jinguHeal;
                        jinguActiveTurns--;
                    }
                }
            } 
            else if (in.equalsIgnoreCase("parry")) {
                System.out.println(">> 50% Damage Block.");
                System.out.println(">> " + damageReturned + " damage will be reflected.");

                botHP -= damageReturned;
                playerHP -= damageBlocked;
            } 
            else if (in.equalsIgnoreCase("za warudo")) {
                if (zaWarudoCooldown == 0 && playerMP >= 50) {
                    timeStopCounter = 3;
                    zaWarudoCooldown = 10;
                    playerMP -= 50;
                    System.out.println("You stopped time.");
                    lastHP.push(botHP);
                } else if (zaWarudoCooldown > 0) {
                    System.out.println("Za Warudo is still on cooldown for " + zaWarudoCooldown + " turns.");
                } else {
                    System.out.println("Not enough MP to use Za warudo.");
                }
            } 
            else if (in.equalsIgnoreCase("skip")) {
                System.out.println("You skipped your turn.");
            } 
            else if (in.equalsIgnoreCase("flee")) {
                System.out.println("You fled from the battle!");
                return;
            }
            else {
                System.out.println("Invalid command. Your turn is wasted!");
            }

            // Enemy turn if time isn't stopped
            if (timeStopCounter > 0) {
                timeStopCounter--;
                System.out.println("Time's stopped. " + timeStopCounter + " turns remaining.");
            } else if (botHP > 0) {
                System.out.println(enemyName + " attacks and deals " + botDmg + " damage!");
                playerHP -= botDmg;
            }

            // Check battle conditions
            if (botHP <= 0) {
                System.out.println("------------------------------");
                System.out.println("You defeated " + enemyName + "!");
                enemiesDefeated++;
                botNames.remove(enemyName);
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
            if (zaWarudoCooldown > 0) {
                zaWarudoCooldown--;
            }
            
            if (botHP <= 20 && lowHpCounter == 0) {
                lowHpCounter = 5;
                System.out.println("The enemy has entered an invulnerable state for 5 turns!");
            }
            
            if (lowHpCounter == 0 && !invulnerableTurns.isEmpty()) {
                botHP = invulnerableTurns.pop();
                System.out.println("Enemy's invulnerability has ended!");
            }
            
            if (lowHpCounter > 0) {
                lowHpCounter--;
                if (lowHpCounter == 0) {
                    System.out.println("Enemy's invulnerable passive has worn off. The enemy has succumbed!");
                    botHP = 0;
                }
            }
        }
    }

    static boolean isOddorEven(int i) {
        return i % 2 == 0;
    }
}