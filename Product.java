public class Product {

    private int price;
    private String name;
    private float score;
    private int scoredNum;

    public Product(String name, int price, float score, int scoredNum) {
        this.name = name;
        this.price = price;
        this.score = score;
        this.scoredNum = scoredNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getScoredNum(){
        return scoredNum;
    }

    public void setScoredNum(int scoredNum) {
        this.scoredNum = scoredNum;
    }
}
