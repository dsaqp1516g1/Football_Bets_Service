package edu.upc.eetac.dsa.football.entity;

/**
 * Created by toni on 28/04/16.
 */
public class FootballError {
    private int status;
    private String reason;

    public FootballError(){}

    public FootballError(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
