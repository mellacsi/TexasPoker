package ch.supsi.texas.cards;

public class Card implements Comparable<Card>{
    private Seed seed;
    private Integer value;

    public static final int HIGHVALUE = 13;

    public enum Seed {
        PIKE, CLOVER, TILE, HEART
    }

    private Card(Seed seed, Integer value){
        this.seed = seed;
        this.value = value;
    }

    public static Card create(Seed seed, Integer value) {
        if((seed != null) && (value >= 0) && (value <= HIGHVALUE))
            return new Card(seed, value);

        return null;
    }

    public Seed getSeed() {
        return seed;
    }

    public void setSeed(Seed seed) {
        this.seed = seed;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;
        if (value != card.value) return false;

        return seed == card.seed;
    }

    @Override
    public int hashCode() {
        int result = seed != null ? seed.hashCode() : 0;
        result = 31 * result + value;
        return result;
    }

    public String toString(){
        return this.value + " " + this.seed;
    }

    public int compareTo(Card other) {
        if(!this.value.equals(other.value))
            return this.value.compareTo(other.value);

        return this.seed.compareTo(other.seed);
    }
}
