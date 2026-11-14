package pkg;

public class DayBirth {
    private int year;
    private int month;
    private Integer day;
    private String gender;
    private int numOfBirths;
    
    public DayBirth(int year, int month, Integer day, String gender, int numOfBirths) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.gender = gender;
        this.numOfBirths = numOfBirths;
    }

    public int getNumBirths() {
        return numOfBirths;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    public String getGender() {
        return gender;
    }
}