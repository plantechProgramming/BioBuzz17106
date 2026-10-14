package org.firstinspires.ftc.teamcode.misc.utils;

public enum Color { // in hex
    YELLOW_POLLEN("#ffff00"),
    BLUE_NECTAR("#0000FF"),
    RED_NECTAR("#FF0000"),
    BLACK("#000000");

    // yellow - in opencv hsv
    // opencv hsv != normal hsv, h:0-179, s:0-255,v:0-255

    private final String color;
    Color(String color){
        this.color = color;
    }
    public String getColorHex(){
        return color;
    }
}
