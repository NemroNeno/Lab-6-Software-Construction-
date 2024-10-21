/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */


package twitter; 
import java.time.Instant;
import java.util.*;
import static org.junit.Assert.*; 
import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
	@Test
    public void testEmptyTweetList() {
        List<Tweet> tweets = new ArrayList<>();
        Map<String, Set<String>> result = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue(result.isEmpty());
    }

    // Test Case 2: Ensures that when there are tweets with no mentions in the list, the function returns an empty graph
    @Test
    public void testTweetsWithNoMentions() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1,"user1", "Hello world!",Instant.now()),
                new Tweet(2,"user2", "Just having a good day.",Instant.now())
        );
        Map<String, Set<String>> result = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue(result.isEmpty() );
    }

    // Test Case 3: Checks that the function correctly identifies users who are mentioned in tweets
    @Test
   public void testSingleMention() {
        List<Tweet> tweets = Collections.singletonList(
                new Tweet(1,"user1", "Hello @user2",Instant.now())
        );
        Map<String, Set<String>> result = SocialNetwork.guessFollowsGraph(tweets);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("user1"));
        assertTrue(result.get("user1").contains("user2"));
    }

    // Test Case 4: Validates that the function associates multiple mentioned users with the authors of the tweets
    @Test
  public  void testMultipleMentions() {
        List<Tweet> tweets = Collections.singletonList(
                new Tweet(1,"user1", "Hello @user2 and @user3",Instant.now())
        );
        Map<String, Set<String>> result = SocialNetwork.guessFollowsGraph(tweets);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("user1"));
        assertTrue(result.get("user1").containsAll(Arrays.asList("user2", "user3")));
    }

    // Test Case 5: Verifies that the function handles multiple tweets by the same author
    @Test
    public void testMultipleTweetsBySameAuthor() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1,"user1", "Hello @user2",Instant.now()),
                new Tweet(2,"user1", "Hey @user3",Instant.now())
        );
        Map<String, Set<String>> result = SocialNetwork.guessFollowsGraph(tweets);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("user1"));
        assertTrue(result.get("user1").containsAll(Arrays.asList("user2", "user3")));
    }
    
    @Test
    public void testEmptyFollowsGraph() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> result =  SocialNetwork.influencers(followsGraph);
        assertTrue( result.isEmpty());
    }

    // Test Case 7: Verifies that when there's only one user with no followers, the function returns an empty list
    @Test
    public void testSingleUserNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>());
        List<String> result = SocialNetwork.influencers(followsGraph);
        assertTrue(result.isEmpty());
    }

    // Test Case 8: Ensures that when there's a single influencer, the function correctly identifies and returns them
    @Test
    public void testSingleInfluencer() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user2")));
        List<String> result = SocialNetwork.influencers(followsGraph);
        assertEquals(1, result.size());
        assertEquals("user2", result.get(0));
    }

    // Test Case 9: Checks that the function correctly identifies top influencers when there are multiple users
    @Test
    public void testMultipleInfluencers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user2", "user3")));
        followsGraph.put("user2", new HashSet<>(Arrays.asList("user3")));
        List<String> result = SocialNetwork.influencers(followsGraph);
        assertEquals(2, result.size());
        assertEquals("user3", result.get(0), "user3");
    }

    // Test Case 10: Validates that the function handles cases where multiple users have equal followers
    @Test
    public void testMultipleUsersWithEqualFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user2")));
        followsGraph.put("user3", new HashSet<>(Arrays.asList("user2")));
        List<String> result = SocialNetwork.influencers(followsGraph);
        assertEquals(1, result.size());
        assertEquals("user2", result.get(0), "user2");
    }
    
    

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
