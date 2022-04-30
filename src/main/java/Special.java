public class Special {

    private String name;
    private String type;
    private int minDmg;
    private int maxDmg;
    private int debuff;
    private int buff;
    private int cost;

    public Special(String name, int minDmg, int maxDmg, String type, int cost) {
        this.name = name;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.debuff = 0;
        this.type = "hurt";
        this.cost = cost;
        this.buff = 0;
    }
    public Special(String name, int debuff, int buff, int cost) {
        this.name = name;
        this.minDmg = 0;
        this.maxDmg = 0;
        this.debuff = debuff;
        this.buff = buff;
        this.setBuff(buff);
        this.cost = cost;
    }
    public int getMinDmg() {
        return this.minDmg;
    }
    public int getMaxDmg() {
        return this.maxDmg;
    }
    public int getBuff() {return this.buff;}
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
    public void setBuff(int buff) {
        if (buff > 0) {
            this.type = "buff";
        }
        else {
            this.type = "debuff";
        }
    }
}
