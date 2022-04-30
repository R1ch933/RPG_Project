public class Enemy extends Entity{
    void addSpecials(){}
    private int exp;
    private int gold;

    /**
     * Very similar constructor to Entity aside from having gold and exp attributes
     * @param name
     * @param hp
     * @param mp
     * @param att
     * @param def
     * @param spd
     * @param spatt
     * @param spdef
     * @param exp
     * @param gold
     */
    public Enemy(String name, int hp, int mp, int att, int def, int spd, int spatt, int spdef, int exp, int gold) {
        this.setName(name);
        this.setHp(hp);
        this.setMp(mp);
        this.setAtt(att);
        this.setDef(def);
        this.setSpd(spd);
        this.setSpAtt(spatt);
        this.setSpDef(spdef);
        this.setLife(true);
        this.setExp(exp);
        this.setGold(gold);

    }

    /**
     * getters for gold and exp
     * @return class attributes
     */
    public int getExp() {
        return this.exp;
    }
    public int getGold() {
        return this.gold;
    }

    /**
     * Setters for gold and exp
     * @param exp/gold
     */
    public void setExp(int exp) {
        this.exp = exp;
    }
    public void setGold(int g) {
        this.gold = g;
    }
}
