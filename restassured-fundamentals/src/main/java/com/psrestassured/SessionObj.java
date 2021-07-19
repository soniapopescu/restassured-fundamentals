package com.psrestassured;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionObj {

    private int session_id;
    private String session_name;
    private String session_description;
    private int session_length;
    private List<SpeakerObj> speakers;

    public int getSession_id() {
        return session_id;
    }

    public String getSession_name() {
        return session_name;
    }

    public String getSession_description() {
        return session_description;
    }

    public int getSession_length() {
        return session_length;
    }

    public List<SpeakerObj> getSpeakers() {
        return speakers;
    }

    @Override
    public String toString() {
        return "SessionObj{" +
                "session_id=" + session_id +
                ", session_name='" + session_name + '\'' +
                ", session_description='" + session_description + '\'' +
                ", session_length=" + session_length +
                ", speakers=" + speakers +
                '}';
    }
}
