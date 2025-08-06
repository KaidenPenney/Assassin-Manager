/**
 JUnit Testing was used for this to test my code
 */


import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;


public class AssassinManagerTest {


   private List<String> names = new ArrayList<String>();
   
   @Before
   public void setUp() {
      names.add("Tom");
      names.add("Jerry");
      names.add("Butch");
   }
   
   @Test
   public void testConstructor() {
      names.add("Spike");     // names->[Tom,Jerry,Butch,Spike]
      AssassinManager test = new AssassinManager(names);
      //Tests to make sure that all proper names are added to the killring at the beginning of the game
      assertTrue(test.killRingContains("Tom"));
      assertTrue(test.killRingContains("Jerry"));
      assertTrue(test.killRingContains("Spike"));
      assertTrue(test.killRingContains("Butch"));
      assertFalse(test.killRingContains("Tim"));
      assertFalse(test.killRingContains("Nibbles"));
   }
   
   @Test
   public void testKill() {
      AssassinManager test = new AssassinManager(names);
      test.kill("Butch");
      assertTrue(test.graveyardContains("Butch")); //Test to check if recently killed player is in graveyard
      assertTrue(test.killRingContains("Jerry")); //Test to make sure player is not wrongly killed
   }
   
   @Test
   public void testWinner() {
      AssassinManager test = new AssassinManager(names);
      test.kill("Butch");
      assertEquals(null, test.winner()); //Test to check if null is returned when the game is not over yet
      test.kill("Jerry");
      assertEquals("Tom", test.winner()); //Test to check that winner is last standing player (Tom)
   }      
   
   @Test
   public void testGameOver() {
      AssassinManager test = new AssassinManager(names);
      assertFalse(test.gameOver()); //Test to make sure that game is not over when it started
      test.kill("Butch");
      assertFalse(test.gameOver()); //Test to make sure game is not over if more than one player is still alive
      test.kill("Jerry");
      assertTrue(test.gameOver()); //Test to make sure game is over when only one player left
   }
   
   @Test
   public void graveyardContains() {
      AssassinManager test = new AssassinManager(names);
      test.kill("Jerry");
      assertTrue(test.graveyardContains("Jerry")); //Test to check if killed player is in graveyard
      assertFalse(test.graveyardContains("Tom")); //Test to make sure no alive player is in graveyard
      assertFalse(test.killRingContains("Jery")); //Test to make sure that mispelled or not existing players are not in graveyard
   }
   
   @Test
   public void killRingContains() {
      AssassinManager test = new AssassinManager(names);
      test.kill("Jerry");
      assertFalse(test.killRingContains("Jerry")); //Test to make sure killed player is not in killring
      assertTrue(test.killRingContains("Tom")); //Test to make sure that players alive still remain in killring
      assertFalse(test.killRingContains("Tim")); //Test to make sure that mispelled or not existing players are not in killring
   }
   @Test
   public void testPrintKillRing() {
      AssassinManager test = new AssassinManager(names);
      test.printKillRing();
      test.kill("Tom");
      test.printKillRing();
   }
   
   @Test
   public void testPrintGraveyard() {
      AssassinManager test = new AssassinManager(names);
      test.kill("Tom");//should print jerry being killed before Tom when testing as alphabetical order
      test.kill("Jerry");
      test.printGraveyard();
      
   }
   //used help of chatgpt for these two methods
   @Test(expected = IllegalArgumentException.class)
   public void testKillThrowsIllegalArgumentException() {
      AssassinManager test = new AssassinManager(names);
      test.kill("NonExistentName"); // Trying to kill someone not in the kill ring
   }

   @Test(expected = IllegalStateException.class)
   public void testKillThrowsIllegalStateException() {
       AssassinManager test = new AssassinManager(names);
       test.kill("Jerry");
       test.kill("Butch"); // After this only Tom is left (winner)
       test.kill("Tom");   // Attempting to kill when the game is already over
   }
}
