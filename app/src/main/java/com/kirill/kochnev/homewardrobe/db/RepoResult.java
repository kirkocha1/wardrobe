package com.kirill.kochnev.homewardrobe.db;

/**
 * Created by kirill on 18.06.17.
 */

public class RepoResult {

    private final Long id;
    private final boolean wasUpdated;
    private final boolean wasInserted;

    public RepoResult(Long id, boolean wasInserted) {
        this.id = id;
        this.wasUpdated = !wasInserted;
        this.wasInserted = wasInserted;
    }

    public long getId() {
        return id;
    }

    public boolean isWasUpdated() {
        return wasUpdated;
    }

    public boolean isWasInserted() {
        return wasInserted;
    }
}
