import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Creates a story from a pre-written text and a list of adjectives.
 * The pre-written text must contain one or more markers in the form of ADJEKTIV to be replaced with random adjectives.
 * @author Tone Berg
 * @version 2015-10-21
 */
public class StoryCreator
{
    private InputReader reader;
    private OutputWriter writer;
    private Random random;

    private ArrayList<String> story;
    private ArrayList<String> adjectives;

    private final String ADJECTIVE = "ADJEKTIV";
    private final String DICTIONARY_MARKER = "adj";
    private final String ERROR = "Beklager. Det var ikke tilstrekkelig med adjektiver for å lage historien. Vennligst lag en fyldigere adjektivliste og prøv igjen!";

    /**
     * Constructor for objects of class StoryCreator
     */
    public StoryCreator()
    {
        reader = new InputReader();
        writer = new OutputWriter();
        random = new Random();
    }

    /**
     * Creates a story from a story file and an adjective file.
     * @param   storyFilename the name of the file containing the story
     * @param   adjectivesFilename the name of the file containing the adjectives
     * @param   outputFilename the name of the file to write the finished story to
     */
    public void createAdjectiveStory(String storyFilename, String adjectivesFilename, String outputFilename)
    {
        story = reader.getWordsInFile(storyFilename);
        adjectives = reader.getWordsInFile(adjectivesFilename);

        buildStory(story, adjectives, outputFilename);
    }

    /**
     * Creates a story from a story file and the user-input adjectives.
     * @param   storyFilename the name of the file containing the story
     * @param   outputFilename the name of the file to write the finished story to
     */
    public void createAdjectiveStory(String storyFilename, String outputFilename)
    {
        story = reader.getWordsInFile(storyFilename);
        adjectives = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        boolean finished = false;
        int neededCount = countAdjectives(story);

        System.out.println("La oss lage en adjektivhistorie!");
        System.out.println("Skriv inn så mange adjektiver du vil, ett eller flere per linje.");
        System.out.println("Merk at vi trenger minst " + neededCount + " adjektiver til denne historien!");
        System.out.println("Avslutt med 'exit'!");

        while (!finished) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if(input.contains("exit")) {
                finished = true;
            }
            else {
                if ( input.trim().contains(" ") ) {
                    String[] inputSplit = input.split("\\s");
                    for ( int i = 0 ; i < inputSplit.length ; i++ ) {
                        adjectives.add(inputSplit[i]);
                    }
                } else {
                    adjectives.add(input);
                }
                int neededLeft = neededCount - adjectives.size();
                if ( neededLeft < 1 ) {
                    System.out.println("Vi har nok adjektiver, men du kan fortsatt skrive inn flere hvis du vil.");
                    System.out.println("Avslutt med 'exit'!");
                } else {
                    System.out.println("Vi trenger " + neededLeft + " adjektiver til!");
                }
            }
        }

        if ( !validateAdjectives(countAdjectives(story), adjectives.size()) ) {
            System.out.println("Vi har ikke nok adjektiver til å bygge denne historien. Prøv gjerne igjen!");
        }

        buildStory(story, adjectives, outputFilename);
    }

    /**
     * Creates a story from a story file and a dictionary file.
     * @param   storyFilename the name of the file containing the story
     * @param   dictionaryFilename the name of the file containing the dictionary
     * @param   outputFilename the name of the file to write the finished story to
     */
    public void createAdjectiveStoryFromDictionary(String storyFilename, String dictionaryFilename, String outputFilename)
    {
        story = reader.getWordsInFile(storyFilename);
        ArrayList<String> dictionary = reader.getWordsInFile(dictionaryFilename);

        adjectives = new ArrayList<>();

        for ( int i = 1 ; i < dictionary.size() ; i = i + 2 ) {
            if ( dictionary.get(i).equals(DICTIONARY_MARKER) ) {
                adjectives.add(dictionary.get(i-1));
            }
        }

        buildStory(story, adjectives, outputFilename);
    }

    /**
     * Builds a story from a list of words and a list of adjectives.
     * @param   story the list of words making up the story
     * @param   adjectives the list of adjectives
     * @param   outputFilename the name of the file to write the finished story to
     */
    private void buildStory(ArrayList<String> story, ArrayList<String> adjectives, String outputFilename) 
    {
        lowerCase(adjectives);
        
        if ( validateAdjectives(countAdjectives(story), adjectives.size()) ) {

            for (int i = 0 ; i < story.size() ; i++) {
                if ( story.get(i).contains(ADJECTIVE) ) {
                    int randomAdjectiveIndex = random.nextInt(adjectives.size());
                    String randomAdjective = adjectives.get(randomAdjectiveIndex);
                    story.set(i, story.get(i).replace(ADJECTIVE,randomAdjective));
                    adjectives.remove(randomAdjectiveIndex);
                }
            }
            correctCase(story);
        } else {
            story.clear();
            story.add(ERROR);
        }

        writer.write(story, outputFilename);
    }
    
    /**
     * Converts all elements in the list to lowercase.
     * @param   adjectives the list containing the elements
     */
    private ArrayList<String> lowerCase(ArrayList<String> words) {
        for (int i = 0 ; i < words.size() ; i++) {
            words.set(i, words.get(i).toLowerCase());
        }
        return words;
    }

    /**
     * Corrects the case in the already built story.
     * @param   story the story
     * @return  the story with appropriately capitalized words
     */
    private ArrayList<String> correctCase(ArrayList<String> story)
    {
        // First word should always be capitalized
        boolean capitalizeNext = true;
        for (int i = 0 ; i < story.size() ; i++) {
            if ( capitalizeNext ) {
                story.set(i, WordUtils.capitalize(story.get(i)));
                capitalizeNext = false;
            }
            
            if ( story.get(i).trim().matches("[^0-9]+[\\.!\\?]$") ) {
                capitalizeNext = true;
            }
        }

        return story;
    }

    /**
     * Count the adjectives needed to build a story.
     * @param   story the list of words making up the story
     */
    private int countAdjectives(ArrayList<String> story) {
        int adjectivesNeeded = 0;
        for (String word : story) {
            if ( word.contains(ADJECTIVE) ) {
                adjectivesNeeded = adjectivesNeeded + 1;
            }
        }
        return adjectivesNeeded;
    }

    /**
     * Check if the adjective list contains enough adjectives to complete the story.
     * @param   adjectivesNeeded the number of adjectives needed
     * @param   adjectiveCount the number of adjectives in the list
     * @return  false if there is not enough adjectives in the list, true if there is
     */
    private boolean validateAdjectives(int adjectivesNeeded, int adjectiveCount)
    {
        if ( adjectivesNeeded <= adjectiveCount ) {
            return true;
        }
        return false;
    }

}
