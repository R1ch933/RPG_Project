import java.util.ArrayList;

public class Hero extends Entity{

    private ArrayList<Special> specialMoves = new ArrayList<Special>();

    public Hero(String name, int hp, int mp, int att, int def, int spd, int spatt, int spdef) {
        this.setMaxHp(hp);
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

    public void addSpecial(String name, int min, int max, int db, String type, int cost) {
        this.specialMoves.add(new Special(name, min, max, db, type, cost));
    }

    public ArrayList<Special> getSpecialMoves() {
        return this.specialMoves;
    }

    public Special getSpecial(String name) {
        for (Special e: this.getSpecialMoves()) {
          if (e.getName() == name)  {
              return e;
          }
        }
        return null;
    }

}
