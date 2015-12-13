import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class StoryCreatorTest.
 *
 * @author  Tone Berg
 * @version 2015.12.13
 */
public class StoryCreatorTest
{
    /**
     * Default constructor for test class StoryCreatorTest
     */
    public StoryCreatorTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        StoryCreator storycreator = new StoryCreator();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void createAdjectiveStory()
    {
        storycreator.createAdjectiveStory("adjektivhistorie.txt", "adjektiv.txt", "adjektivhistorie-ferdig.txt");
        storycreator.createAdjectiveStory("aftenpostenhistorie.txt", "adjektiv.txt", "aftenpostenhistorie-ferdig.txt");
        storycreator.createAdjectiveStory("ringvollhistorie.txt", "adjektiv.txt", "ringvollhistorie-ferdig.txt");
    }

    @Test
    public void createAdjectiveStoryFromDictionary()
    {
        storycreator.createAdjectiveStoryFromDictionary("adjektivhistorie.txt", "NSF-ordlisten.txt", "adjektivhistorie-ferdig.txt");
        storycreator.createAdjectiveStoryFromDictionary("aftenpostenhistorie.txt", "NSF-ordlisten.txt", "aftenpostenhistorie-ferdig.txt");
        storycreator.createAdjectiveStoryFromDictionary("ringvollhistorie.txt", "NSF-ordlisten.txt", "ringvollhistorie-ferdig.txt");
    }
}


