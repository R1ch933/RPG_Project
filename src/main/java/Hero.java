/**
 * This subclass aggregates both Entity and the Special class
 */
public class Hero extends Entity{
    public Hero(){}
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
     */
    @Override
    public void addSpecial(String name, int min, int max, String type, int cost) {
        this.getSpecialMoves().add(new Special(name, min, max,type, cost));
    }


    /**
     * This is adding a special object as well, but this is particularly for buffs and debuffs and is a counterpart to addSpecial
     * @param name
     * @param db
     * @param b
     * @param c
     */
    public void addBuffer(String name, int db, int b, int c) {
        this.getSpecialMoves().add(new Special(name, db,b, c));

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
