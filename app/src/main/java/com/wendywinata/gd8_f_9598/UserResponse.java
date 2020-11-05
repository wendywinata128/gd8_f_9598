package com.wendywinata.gd8_f_9598;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("data")
    @Expose
    private List<UserDAO> users = null;

    @SerializedName("message")
    @Expose
    private String message;

    public List<UserDAO> getUsers() {return users;}

    public String getMessage() {return message;}

    public void setUsers(List<UserDAO> users){
        this.users = users;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
