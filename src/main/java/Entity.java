import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;

/**
 * This class represents the universal things most enemies/players will center around
 */
public abstract class Entity {
    private Random randomNum = new Random();
    private String name;
    private boolean Life;
    private int MaxHp;
    private int Hp;
    private int Mp;
    private int Att;
    private int SpAtt;
    private int Def;
    private int SpDef;
    private int Spd;
    private int spDmgNum; //This is an attribute to spit out for text display on the UI e.g. "he took spDmgNum amount of dmg!"

    /**
     * base constructor for any subclasses
     */
    public Entity(){}

    /**
     * constructor with stats for most entities like enemies and players.
     * @param name
     * @param hp
     * @param mp
     * @param att
     * @param def
     * @param spd
     * @param spatt
     * @param spdef
     */
    public Entity(String name, int hp, int mp, int att, int def, int spd, int spatt, int spdef) {
        this.MaxHp = hp;
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

    /**
     * These are all the setters for all the private attributes
     */
    public void setName(String name) {this.name = name;}
    public void setHp(int hp) {this.Hp = hp;}
    public void setMp(int mp) {this.Mp = mp;}
    public void setAtt(int att) {this.Att = att;}
    public void setDef(int def) {this.Def = def;}
    public void setSpd(int spd) {this.Spd = spd;}
    public void setSpAtt(int spatt) {this.SpAtt = spatt;}
    public void setSpDef(int spdef) {this.SpDef = spdef;}
    public void setLife(boolean alive) {this.Life = alive;}
    public void setMaxHp(int max) {this.MaxHp = max;}

    /**
     * These are all the getters for all the private attributes.
     * @return class attributes
     */
    public String getName() {return this.name;}
    public boolean getLife() {return this.Life;}
    public int getHp() {return this.Hp;}
    public int getMp() {return this.Mp;}
    public int getAtt() {return this.Att;}
    public int getDef() {return this.Def;}
    public int getSpd() {return this.Spd;}
    public int getSpAtt() {return this.SpAtt;}
    public int getSpDef() {return this.SpDef;}
    public int getMaxHp() {return this.MaxHp;}
    /**
     * This is how damage is calculated on basic attacks. Dmg received will be affected by randomness and def stat
     * @param enemyAtt
     */
    public void takeDmg(int enemyAtt) {
        int chosenDmg;
        int minDmg = enemyAtt;
        int maxDmg = enemyAtt*2;

        ArrayList<Integer> dmgNums = new ArrayList<Integer>();
        for (int i = minDmg; i < maxDmg + 1; i++) {
            dmgNums.add(i);
        }
        chosenDmg = dmgNums.get(this.randomNum.nextInt(dmgNums.size()));

        chosenDmg -= this.getDef();
        if (chosenDmg < 0) {
            chosenDmg = 1;
        }
        this.setSpDmgNum(chosenDmg);
        this.setHp(this.getHp() - chosenDmg);
        if (this.getHp() < 0) {
            this.setHp(0);
        }
    }

    /**
     * Spells have min and max dmg built in. The calculations include randomization of the range of dmg and spdef stats
     * @param minDmg
     * @param maxDmg
     */
    public void takeSpDmg(int minDmg, int maxDmg) {
        int chosenNum;
        ArrayList<Integer> dmgNums = new ArrayList<Integer>();
        if (minDmg == 0 && maxDmg == 0) {
            chosenNum = 0;
        } else {
            for (int i = minDmg; i < maxDmg + 1; i++) {
                dmgNums.add(i);
            }
            chosenNum = dmgNums.get(this.randomNum.nextInt(dmgNums.size()));
        }
        if (chosenNum > 0) {
            chosenNum -= this.getSpDef();
            if (chosenNum < 0) {
                chosenNum = 1;
            }
        }
        this.setHp(this.getHp() - chosenNum);
        if (this.getHp() < 0) {
            this.setHp(0);
        }

        this.setSpDmgNum(chosenNum);
    }

    /**
     * Healing is unique enough to warrant a differentiated function. Most notably because negative numbers are a problem for runtime. Will heal
     * and increase HP stat, but not beyond MaxHp
     * @param minDmg
     * @param maxDmg
     */
    public void healSelf(int minDmg, int maxDmg) {


        int chosenHeal;
        ArrayList<Integer> dmgNums = new ArrayList<Integer>();
        for (int i = minDmg; i < maxDmg + 1; i++) {
            dmgNums.add(i);
        }
        chosenHeal = dmgNums.get(this.randomNum.nextInt(dmgNums.size()));
        this.setHp(this.getHp() + chosenHeal);
        if (this.getHp() > this.getMaxHp()) {
            this.setHp(this.getMaxHp());
        }
        this.setSpDmgNum(chosenHeal);
    }

    /**
     * debuffs decrease stats but not below zero.
     * @param debuff
     */
    public void takeDebuff(int debuff) {
        this.setSpDef(this.getSpDef() - debuff);
        if (this.getSpDef() < 0) {
            this.setSpDef(0);
        }
        this.setDef(this.getDef() - debuff);
        if (this.getDef() < 0) {
            this.setDef(0);
        }
        this.setSpDmgNum(debuff);
    }

    /**
     * In this particular case, this is a spdbuff that increases spd stat. Because spd stat affects which Entity takes action first,
     * it must have its own distinct function. Although there are no other buffs in this particular program.
     * @param buff
     */
    public void takeSpdBuff(int buff) {
        this.setSpd(this.getSpd() + buff);
        this.setSpDmgNum(buff);
    }

    /**
     * Sets SpDmgNum; this number is used for dialogue purposes
     * @param num
     */
    public void setSpDmgNum(int num) {
        this.spDmgNum = num;
    }

    /**
     * getter for SpDmgNum
     * @return int
     */
    public int getSpDmgNum() {
        return this.spDmgNum;
    }

    /**
     * Checks if entity is alive for gameover/win
     * @return boolean
     */
    public boolean checkAlive() {
        if (this.getHp() == 0) {
            return false;
        }
        return true;
    }
}
