/**
 * This class surrounds the stats around magic/moves that players can use
 */
public class Special {

    private String name;
    private String type;
    private int minDmg;
    private int maxDmg;
    private int debuff;
    private int buff;
    private int cost;

    /**
     * This constructor are for heal and hurting types. debuffs and buffs are automatically set to 0
     * @param name
     * @param minDmg
     * @param maxDmg
     * @param type
     * @param cost
     */
    public Special(String name, int minDmg, int maxDmg, String type, int cost) {
        this.name = name;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.debuff = 0;
        this.type = type;
        this.cost = cost;
        this.buff = 0;
    }

    /**
     * This is a constructor for buffs and debuffs. Damages are automatically set to 0
     * @param name
     * @param debuff
     * @param buff
     * @param cost
     */
    public Special(String name, int debuff, int buff, int cost) {
        this.name = name;
        this.minDmg = 0;
        this.maxDmg = 0;
        this.debuff = debuff;
        this.buff = buff;
        this.setBuff(buff);
        this.cost = cost;
    }

    /**
     * getters for Special object attributes
     * @return
     */
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

    /**
     * This method sets a Special object automatically to a buff or debuff type which is called in only the debuff/buff constructor
     * @param buff
     */
    public void setBuff(int buff) {
        if (buff > 0) {
            this.type = "buff";
        }
        else {
            this.type = "debuff";
        }
    }
}
