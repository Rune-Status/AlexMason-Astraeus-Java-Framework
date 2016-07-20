package astraeus.game.model.entity.mob.player;

/**
 * Represents a {@link Player}s appearance.
 * 
 * @author Seven
 */
public class Appearance {

      public static final int HEAD = 0;

      public static final int JAW = 1;

      public static final int TORSO = 2;

      public static final int ARMS = 3;

      public static final int HANDS = 4;

      public static final int LEGS = 5;

      public static final int FEET = 6;

      public enum Gender {
            MALE(0),

            FEMALE(1);

            private int code;

            private Gender(final int id) {
                  this.code = id;
            }

            /**
             * @return the id
             */
            public int getCode() {
                  return code;
            }

      }

      private Gender gender;

      private int head;

      private int jaw;

      private int torso;

      private int arms;

      private int hands;

      private int legs;

      private int feet;

      private int hairColor;

      private int torsoColor;

      private int legsColor;

      private int feetColor;

      private int skinColor;

      public Appearance(final Gender gender, final int head, final int jaw, final int torso,
                  final int arms, final int hands, final int legs, final int feet, int hairColor,
                  int torsoColor, int legsColor, int feetColor, int skinColor) {
            this.gender = gender;
            this.head = head;
            this.jaw = jaw;
            this.torso = torso;
            this.arms = arms;
            this.hands = hands;
            this.legs = legs;
            this.feet = feet;
            this.hairColor = hairColor;
            this.torsoColor = torsoColor;
            this.legsColor = legsColor;
            this.feetColor = feetColor;
            this.skinColor = skinColor;
      }

      /**
       * @return the arms
       */
      public int getArms() {
            return arms;
      }

      public Appearance getDefaultAppearance() {
            return Player.DEFAULT_APPEARANCE;
      }

      /**
       * @return the feet
       */
      public int getFeet() {
            return feet;
      }

      /**
       * @return the gender
       */
      public Gender getGender() {
            return gender;
      }

      /**
       * @return the hands
       */
      public int getHands() {
            return hands;
      }

      /**
       * @return the head
       */
      public int getHead() {
            return head;
      }

      /**
       * @return the jaw
       */
      public int getJaw() {
            return jaw;
      }

      /**
       * @return the legs
       */
      public int getLegs() {
            return legs;
      }

      /**
       * @return the torso
       */
      public int getTorso() {
            return torso;
      }

      /**
       * @param arms the arms to set
       */
      public void setArms(final int arms) {
            this.arms = arms;
      }

      /**
       * @param feet the feet to set
       */
      public void setFeet(final int feet) {
            this.feet = feet;
      }

      /**
       * @param gender the gender to set
       */
      public void setGender(final Gender gender) {
            this.gender = gender;
      }

      /**
       * @param hands the hands to set
       */
      public void setHands(final int hands) {
            this.hands = hands;
      }

      /**
       * @param head the head to set
       */
      public void setHead(final int head) {
            this.head = head;
      }

      /**
       * @param jaw the jaw to set
       */
      public void setJaw(final int jaw) {
            this.jaw = jaw;
      }

      /**
       * @param legs the legs to set
       */
      public void setLegs(final int legs) {
            this.legs = legs;
      }

      /**
       * @param torso the torso to set
       */
      public void setTorso(final int torso) {
            this.torso = torso;
      }

      /**
       * @return the feetColor
       */
      public int getFeetColor() {
            return feetColor;
      }

      /**
       * @return the hairColor
       */
      public int getHairColor() {
            return hairColor;
      }

      /**
       * @return the legsColor
       */
      public int getLegsColor() {
            return legsColor;
      }

      /**
       * @return the skinColor
       */
      public int getSkinColor() {
            return skinColor;
      }

      /**
       * @return the torsoColor
       */
      public int getTorsoColor() {
            return torsoColor;
      }

      /**
       * @param feetColor the feetColor to set
       */
      public void setFeetColor(int feetColor) {
            this.feetColor = feetColor;
      }

      /**
       * @param hairColor the hairColor to set
       */
      public void setHairColor(int hairColor) {
            this.hairColor = hairColor;
      }

      /**
       * @param legsColor the legsColor to set
       */
      public void setLegsColor(int legsColor) {
            this.legsColor = legsColor;
      }

      /**
       * @param skinColor the skinColor to set
       */
      public void setSkinColor(int skinColor) {
            this.skinColor = skinColor;
      }

      /**
       * @param torsoColor the torsoColor to set
       */
      public void setTorsoColor(int torsoColor) {
            this.torsoColor = torsoColor;
      }

      @Override
      public String toString() {
            return "Gender: " + getGender().name() + " Head: " + getHead() + " Jaw: " + getJaw()
                        + " Torso: " + getTorso() + " Arms: " + getArms() + " Hands: " + getHands()
                        + " Legs: " + getLegs() + " feet: " + getFeet();
      }

}
