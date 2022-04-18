public class Enemy extends Entity{

    public Enemy(String name, int hp, int mp, int att, int def, int spd, int spatt, int spdef) {
        this.setName(name);
        this.setHp(hp);
        this.setMp(mp);
        this.setAtt(att);
        this.setDef(def);
        this.setSpd(spd);
        this.setSpAtt(spatt);
        this.setSpDef(spdef);
        this.setLife(true);
    }
}
