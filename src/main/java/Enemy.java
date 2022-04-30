/**
 * subclass represents Enemies
 */
public class Enemy extends Entity {

    private int exp;
    private int gold;

    /**
     * Very similar constructor to Entity aside from having gold and exp attributes and Enemies do not have MP
     *
     * @param name
     * @param hp
     * @param att
     * @param def
     * @param spd
     * @param spatt
     * @param spdef
     * @param exp
     * @param gold
     */
    public Enemy(String name, int hp, int att, int def, int spd, int spatt, int spdef, int exp, int gold) {
        this.setName(name);
        this.setHp(hp);
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
     * The addSpecial must be dynamically woven to feed into the Special constructor. However, Enemies will not have MP.
     * The override is within the same fashion however.
     * @param name
     * @param min
     * @param max
     * @param type
     * @param cost
     */

    @Override
    public void addSpecial(String name, int min, int max, String type, int cost) {
        this.getSpecialMoves().add(new Special(name, min, max,type, cost));
    }

    /**
     * getters for gold and exp
     *
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
     *
     * @param exp/gold
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setGold(int g) {
        this.gold = g;
    }

}
