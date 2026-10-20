package org.firstinspires.ftc.teamcode.misc.utils;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public enum GameElement {
    POLLEN(Color.YELLOW_POLLEN, DistanceUnit.INCH, 1.4),
    BLUE_NECTAR(Color.BLUE_NECTAR, POLLEN.getUnit(), 1.8),
    RED_NECTAR(Color.RED_NECTAR, POLLEN.getUnit(), GameElement.BLUE_NECTAR.getRadiusInches());

    private final Color color;
    private final double radiusInches;
    private final DistanceUnit unit;
    GameElement(Color c, DistanceUnit unit, double radiusInches){
        color = c;
        this.radiusInches = radiusInches;
        this.unit = unit;
    }
    public Color getColor() {
        return color;
    }

    public double getRadiusInches() {
        return radiusInches;
    }
    public DistanceUnit getUnit(){
        return unit;
    }

    public static GameElement fromString(String name){ //TODO: change the names to the detection names
        switch (name){
            case "RedNectar":
                return GameElement.RED_NECTAR;
            case "BlueNectar":
                return GameElement.BLUE_NECTAR;
            case  "Pollen":
                return GameElement.POLLEN;
            default:
                throw new IllegalArgumentException("No game element found with such name");
        }
    }
}
