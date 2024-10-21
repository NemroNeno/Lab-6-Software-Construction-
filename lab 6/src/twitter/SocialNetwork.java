/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;




/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
	
	
	public static Map<String, Set<String>> constructFollowsGraph(List<Tweet> tweets) {
	    // Map to store the follows graph
	    Map<String, Set<String>> followsGraph = new HashMap<>();

	    // Loop through each tweet in the list
	    for (Tweet tweet : tweets) {
	        String author = tweet.getAuthor();
	        String content = tweet.getText();

	        // Set to store the users mentioned in the tweet
	        Set<String> mentionedUsers = new HashSet<>();

	        // Split the tweet into words to identify mentions
	        String[] wordsInTweet = content.split("\\s+");
	        for (String word : wordsInTweet) {
	            if (word.startsWith("@") && word.length() > 1) {
	                mentionedUsers.add(word.substring(1));  // Add mentioned user without "@" symbol
	            }
	        }

	        // Update the follows graph for the author of the tweet
	        for (String mentionedUser : mentionedUsers) {
	            if (!mentionedUser.equalsIgnoreCase(author)) {  // Avoid users following themselves
	                followsGraph.putIfAbsent(author, new HashSet<>());
	                followsGraph.get(author).add(mentionedUser);
	            }
	        }
	    }

	    return followsGraph;
	}

	public static List<String> getInfluencers(Map<String, Set<String>> followsGraph) {
	    // Map to store the follower counts for each user
	    Map<String, Integer> followerCount = new HashMap<>();

	    // Count the followers for each user
	    for (String user : followsGraph.keySet()) {
	        for (String followedUser : followsGraph.get(user)) {
	            followerCount.put(followedUser, followerCount.getOrDefault(followedUser, 0) + 1);
	        }
	    }

	    // Create a list of users to sort by follower count
	    List<String> influencerList = new ArrayList<>(followerCount.keySet());

	    // Sort users by their follower count in descending order
	    influencerList.sort((u1, u2) -> Integer.compare(followerCount.get(u2), followerCount.get(u1)));

	    return influencerList;
	}

	
}




	

	
	


