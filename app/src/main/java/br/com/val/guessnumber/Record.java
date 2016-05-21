package br.com.val.guessnumber;

import java.util.Date;

/**
 * Created by Valmir on 21/05/2016.
 */
public class Record implements Comparable<Record>{
    private String nameUser;
    private int number;
    private int attempts;
    private Date date;


    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Record another) {
        if (this.attempts < another.attempts) {
            return -1;
        }
        if (this.attempts > another.attempts) {
            return 1;
        }
        return 0;
    }
}
