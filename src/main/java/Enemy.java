public class Enemy extends Entity{

    private int exp;
    private int gold;

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

    public int getExp() {
        return this.exp;
    }
    public int getGold() {
        return this.gold;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    public void setGold(int g) {
        this.gold = g;
    }
}
