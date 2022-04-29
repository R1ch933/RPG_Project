public class Special {

    private String name;
    private String type;
    private int minDmg;
    private int maxDmg;
    private int debuff;
    private int cost;

    public Special(String name, int minDmg, int maxDmg, int debuff, String type, int cost) {
        this.name = name;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.debuff = debuff;
        this.type = type;
        this.cost = cost;
    }
    public int getMinDmg() {
        return this.minDmg;
    }
    public int getMaxDmg() {
        return this.maxDmg;
    }
    public int getDebuff() {
        return this.debuff;
    }
    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
    }
    public int getCost() {
        return this.cost;
    }
}
