package com.xinran.qxviewslib.db.dao;

public class DuplicateTeamException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicateTeamException(String message) {
        super(message);
    }
}
