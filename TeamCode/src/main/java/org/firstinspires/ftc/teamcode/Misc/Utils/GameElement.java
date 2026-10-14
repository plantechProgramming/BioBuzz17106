package org.firstinspires.ftc.teamcode.Misc.Utils;

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
}
