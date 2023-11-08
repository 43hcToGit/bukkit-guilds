package net.juxyc.guilds.utils.hexcolor;

public enum HexColor {
    DARK_ORANGE("#bf5e34"),

    HEADER_I("#1164d4"),
    HEADER_C("#246fd4"),
    HEADER_E("#377ad4"),
    HEADER_H("#4e87d4"),
    HEADER_A("#739ed9"),
    HEADER_R("#9ab5db"),
    HEADER_D("#bccce3"),
    HEADER_P("#cad3e0"),
    HEADER_L("#e8eaed"),


    MEDIUM_ORANGE("#e0632d"),
    LIGHT_ORANGE("#f26e35"),
    DARK_YELLOW("#bfb32c"),
    MEDIUM_YELLOW("#e3d432"),
    LIGHT_YELLOW("#f7e94d"),
    DARK_GREEN1("#68bf32"),
    MEDIUM_GREEN1("#6fd92e"),
    LIGHT_GREEN1("#68e81a"),
    DARK_GREEN2("#29b339"),
    MEDIUM_GREEN2("#28d13b"),
    LIGHT_GREEN2("#0ee626"),
    DARK_BLUE1("#20b39d"),
    MEDIUM_BLUE1("#1cc7ad"),
    LIGHT_BLUE1("#1ee8c9"),
    DARK_BLUE2("#1c3391"),
    MEDIUM_BLUE2("#1b3dc4"),
    LIGHT_BLUE2("#113ef0"),
    WHITE("#ffffff"),
    WHITE2("#e6e6e6"),
    WHITE3("#d4d4d4"),
    GRAY("#b5b5b5"),
    MEDIUM_GRAY("#9e9d9d"),
    DARK_GRAY("#757575"),
    LIGHT_BLACK("#3d3d3d"),
    MEDIUM_BLACK("#2e2e2e"),
    DARK_BLACK("#000000"),
    LIGHT_RED("#ff4a4a"),
    MEDIUM_RED("#ff2929"),
    FULL_RED("#ff1717"),
    DARK_RED("#c70000");

    private final String HEX;

    public String getHEX() {
        return this.HEX;
    }

    HexColor(String HEX) {
        this.HEX = HEX;
    }

    public String getAsHex() {
        return "§" + getHEX();
    }
}