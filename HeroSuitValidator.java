package MVC;

public class HeroSuitValidator {
    //Model
    public static boolean isValidSuit(HeroSuit suit) {
        if (suit == null) return false;

        switch (suit.getType()) {
            case "ชุดทรงพลัง":
                return isValidPowerSuit(suit.getDurability());
            case "ชุดลอบเร้น":
                return isValidStealthSuit(suit.getDurability());
            case "ชุดปกปิดตัวตน":
                return isValidDisguiseSuit(suit.getDurability());
            default:
                return false;
        }
    }

    private static boolean isValidPowerSuit(int durability) {
        return durability >= 70;
    }

    private static boolean isValidStealthSuit(int durability) {
        return durability >= 50;
    }

    private static boolean isValidDisguiseSuit(int durability) {
        return !(durability % 10 == 3 || durability % 10 == 7);
    }
}
