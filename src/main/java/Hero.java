import java.util.ArrayList;

public class Hero extends Entity{

    private ArrayList<Special> specialMoves = new ArrayList<Special>();

    /**
     * Constructor for the player subclass that is very similar to entity
     * @param name
     * @param hp
     * @param mp
     * @param att
     * @param def
     * @param spd
     * @param spatt
     * @param spdef
     */
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

    /**
     * The addSpecial must be dynamically woven to feed into the Special constructor
     * @param name
     * @param min
     * @param max
     * @param type
     * @param cost
     * @override
     */
    public void addSpecial(String name, int min, int max, String type, int cost) {
        this.specialMoves.add(new Special(name, min, max,type, cost));
    }

    /**
     * This is adding a special object as well, but this is particularly for buffs and debuffs and is a counterpart to addSpecial
     * @param name
     * @param db
     * @param b
     * @param c
     */
    public void addBuffer(String name, int db, int b, int c) {
        this.specialMoves.add(new Special(name, db,b, c));

    }

    /**
     * gets the arraylist containing assortment of Special objects representing the player's moves
     * @return ArrayList
     */
    public ArrayList<Special> getSpecialMoves() {
        return this.specialMoves;
    }

    /**
     * returns a Special object class based on name attribute
     * @param name
     * @return Special
     */
    public Special getSpecial(String name) {
        for (Special e: this.getSpecialMoves()) {
          if (e.getName() == name)  {
              return e;
          }
        }
        return null;
    }

}
