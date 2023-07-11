// 2.1

public class AmazonReview {

    private String username;
    private String product;
    private int stars;
    private String comments;

    public AmazonReview(String username, String product, int stars, String comments) {
        this.username = username;
        this.product = product;
        this.stars = stars;
        this.comments = comments;
    }

    public String getUsername() {
        return this.username;
    }

    public String getProduct() {
        return this.product;
    }

    public int getStars() {
        return this.stars;
    }

    @Override
    public String toString() {
        return (username + " - Product '" + product + "' - " + stars + " stars: '" + comments + "'");
    }


}
