 package com.voting;

public class Vote {
    private int id;
    private int userId;
    private int candidateId;

    public Vote(int id, int userId, int candidateId) {
        this.id = id;
        this.userId = userId;
        this.candidateId = candidateId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getCandidateId() {
        return candidateId;
    }
}
