package com.example.jj_club.activities.myclub;

public class Club {
    private String clubName;
    private String clubDescription;

    public Club() {
        // 기본 생성자 필요 (Firebase에서 객체를 역직렬화할 때 사용됨)
    }

    public Club(String clubName, String clubDescription) {
        this.clubName = clubName;
        this.clubDescription = clubDescription;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }
}
