package MVC;

public class HeroSuit {
    private String id;
    private String type;
    private int durability;

    public HeroSuit(String id, String type, int durability) {
        this.id = id;
        this.type = type;
        this.durability = durability;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getDurability() {
        return durability;
    }

    public void repair() {
        this.durability = Math.min(this.durability + 25, 100);
    }

    public boolean isValid() {
        return HeroSuitValidator.isValidSuit(this);
    }
}
