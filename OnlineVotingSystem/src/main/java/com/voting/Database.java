 package com.voting;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Candidate> candidates = new HashMap<>();
    private Map<Integer, Vote> votes = new HashMap<>();
    private int userIdCounter = 1;
    private int candidateIdCounter = 1;
    private int voteIdCounter = 1;

    public void addUser(User user) {
        user = new User(userIdCounter++, user.getUsername(), user.getPassword());
        users.put(user.getId(), user);
    }

    public User getUserByUsername(String username) {
        return users.values().stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void addCandidate(Candidate candidate) {
        candidate = new Candidate(candidateIdCounter++, candidate.getName());
        candidates.put(candidate.getId(), candidate);
    }

    public void addVote(Vote vote) {
        vote = new Vote(voteIdCounter++, vote.getUserId(), vote.getCandidateId());
        votes.put(vote.getId(), vote);
    }

    public Map<Integer, Candidate> getCandidates() {
        return candidates;
    }

    public Map<Integer, Vote> getVotes() {
        return votes;
    }
}
