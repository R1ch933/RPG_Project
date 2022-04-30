import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;

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
    private int spDmgNum;
    private ArrayList<String> specials = new ArrayList<String>();

    public Entity(){}

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
    public int getMaxHp() {return this.MaxHp;}

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
    public void takeSpdBuff(int buff) {
        this.setSpd(this.getSpd() + buff);
        this.setSpDmgNum(buff);
    }

    public void setSpDmgNum(int num) {
        this.spDmgNum = num;
    }

    public int getSpDmgNum() {
        return this.spDmgNum;
    }

    public boolean checkAlive() {
        if (this.getHp() == 0) {
            return false;
        }
        return true;
    }
}
