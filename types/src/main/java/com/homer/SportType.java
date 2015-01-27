package com.homer;

/**
 * Created by arigolub on 1/26/15.
 */
public class SportType {

    public static final SportType MLB       = new SportType("MLB");
    public static final SportType FANTASY   = new SportType("FANTASY");

    private String name;

    public String getName() {
        return name;
    }

    private SportType(String name) {
        this.name = name;
    }

    public static SportType getSportType(String type) {
        SportType sportType = null;
        switch(type) {
            case "MLB":
                sportType = MLB;
                break;
            case "FANTASY":
                sportType = FANTASY;
                break;
            default:
                break;
        }
        return sportType;
    }

    @Override
    public String toString() {
        return "SportType{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SportType sportType = (SportType) o;

        if (!name.equals(sportType.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
