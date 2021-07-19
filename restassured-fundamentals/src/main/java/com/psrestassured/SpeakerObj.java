package com.psrestassured;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeakerObj {

    private int speaker_id;
    private String first_name;
    private String last_name;
    private String title;
    private String company;
    private String speaker_bio;
    private boolean speaker_photo;

    public int getSpeaker_id() {
        return speaker_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getSpeaker_bio() {
        return speaker_bio;
    }

    public boolean isSpeaker_photo() {
        return speaker_photo;
    }

    @Override
    public String toString() {
        return "SpeakerObj{" +
                "speaker_id=" + speaker_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", speaker_bio='" + speaker_bio + '\'' +
                ", speaker_photo=" + speaker_photo +
                '}';
    }
}
