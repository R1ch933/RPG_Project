import java.util.ArrayList;

public abstract class Entity {
    private String name;
    private boolean Life;
    private int Hp;
    private int Mp;
    private int Att;
    private int SpAtt;
    private int Def;
    private int SpDef;
    private int Spd;
    private ArrayList<String> specials = new ArrayList<String>();

    public Entity(){}

    public Entity(String name, int hp, int mp, int att, int def, int spd, int spatt, int spdef) {
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

    public void setName(String name) {this.name = name;}
    public void setHp(int hp) {this.Hp = hp;}
    public void setMp(int mp) {this.Mp = mp;}
    public void setAtt(int att) {this.Att = att;}
    public void setDef(int def) {this.Def = def;}
    public void setSpd(int spd) {this.Spd = spd;}
    public void setSpAtt(int spatt) {this.SpAtt = spatt;}
    public void setSpDef(int spdef) {this.SpDef = spdef;}
    public void setLife(boolean alive) {this.Life = alive;}
    public void addSpecials(String specialAttack) {this.specials.add(specialAttack);}

    public String getName() {return this.name;}
    public boolean getLife() {return this.Life;}
    public int getHp() {return this.Hp;}
    public int getMp() {return this.Mp;}
    public int getAtt() {return this.Att;}
    public int getDef() {return this.Def;}
    public int getSpd() {return this.Spd;}
    public int getSpAtt() {return this.SpAtt;}
    public int getSpDef() {return this.SpDef;}

    public void takeDmg(int enemyAtt, String type) {
        if (type == "normal") {
            enemyAtt -= this.getDef();
            this.setHp(this.getHp() - enemyAtt);

        } else if (type == "special") {
            enemyAtt -= this.getSpDef();
            this.setHp(this.getHp() - enemyAtt);

        }
        else {
            throw new RuntimeException("Expected 'normal' or 'special' in attack type. Recieved " + type + " instead.");
        }
    }

    public boolean checkAlive(int hp) {
        if (hp < 0) {
            return false;
        }
        return true;
    }
}
