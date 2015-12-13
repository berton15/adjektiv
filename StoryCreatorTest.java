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
        StoryCreator storyCre2 = new StoryCreator();
        storyCre2.createAdjectiveStory("adjektivhistorie.txt", "adjektiv.txt", "ferdig.txt");
    }
}

