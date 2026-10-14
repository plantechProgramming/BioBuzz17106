package org.firstinspires.ftc.teamcode.misc.utils;

public enum GameElement {
    POLLEN(Color.YELLOW_POLLEN, 2.8),
    BLUE_NECTAR(Color.BLUE_NECTAR, 3.6),
    RED_NECTAR(Color.RED_NECTAR, GameElement.BLUE_NECTAR.getRadiusInches());

    private final Color color;
    private final double radiusInches;
    GameElement(Color c, double radiusInches){
        color = c;
        this.radiusInches = radiusInches;
    }
    public Color getColor() {
        return color;
    }

    public double getRadiusInches() {
        return radiusInches;
    }

    public static GameElement fromString(String name){ //TODO: change the names to the detection names
        switch (name){
            case "Red nectar":
                return GameElement.RED_NECTAR;
            case "Blue nectar":
                return GameElement.BLUE_NECTAR;
            case  "Pollen":
                return GameElement.POLLEN;
            default:
                throw new IllegalArgumentException("No game element found with such name");
        }
    }
}
