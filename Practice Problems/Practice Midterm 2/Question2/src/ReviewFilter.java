import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2.2

public class ReviewFilter {

    private Map<String, List<AmazonReview>> userMap;
    private Map<String, Map<Integer, List<AmazonReview>>> productStarMap;

    // 2.5
    public ReviewFilter(String filepath) {
        userMap = new HashMap<>();
        productStarMap = new HashMap<>();

        loadMaps(loadReviews(filepath));
    }

    // 2.3
    private List<AmazonReview> loadReviews(String filepath) {

        List<AmazonReview> reviews = new ArrayList<>();

        // open the buffered reader
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(new File(filepath)));
        } catch(Exception e) {
            System.out.println(e);
            return reviews;
        }

        try {

            // read the lines of the file
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(";");

                // add the review to the review list (note how we parse the stars from a string to an int)
                reviews.add(new AmazonReview(tokens[0], tokens[1], Integer.parseInt(tokens[2]), tokens[3]));
            }

        } catch(Exception e) {
            System.out.println(e);
        } finally {

            // close the buffered reader
            try { br.close(); } catch(Exception e) { System.out.println(e); }
        }

        return reviews;
    }

    // 2.4
    private void loadMaps(List<AmazonReview> reviews) {

        for(AmazonReview review : reviews) {

            String user = review.getUsername();
            String product = review.getProduct();
            int stars = review.getStars();

            // add to user map
            if(!userMap.containsKey(user)) {
                userMap.put(user, new ArrayList<>());
            }

            userMap.get(user).add(review);

            // add to product stars map
            if(!productStarMap.containsKey(product)) {
                productStarMap.put(product, new HashMap<>());
            }

            if(!productStarMap.get(product).containsKey(stars)) {
                productStarMap.get(product).put(stars, new ArrayList<>());
            }

            productStarMap.get(product).get(stars).add(review);
        }
    }

    // 2.6
    public void getUserReviews(String user) throws Exception {
        // throw exception if user doesn't have any reviews
        if(!userMap.containsKey(user)) {
            throw new Exception("No reviews for user " + user + "!");
        }

        for(AmazonReview review : userMap.get(user)) {
            System.out.println(review);
        }
    }

    public void getProductStarReviews(String product, int stars) throws Exception {
        // throw exception if product doesn't have any reviews
        if(!productStarMap.containsKey(product)) {
            throw new Exception("No reviews for product " + product + "!");
        }

        // throw exception if that product has no reviews for given stars
        if(!productStarMap.get(product).containsKey(stars)) {
            throw new Exception("No " + stars + " star reviews for product " + product + "!");
        }

        for(AmazonReview review : productStarMap.get(product).get(stars)) {
            System.out.println(review);
        }
    }


    // testing
    public static void main(String[] args) {
        ReviewFilter rf = new ReviewFilter("Files/test.txt");

        System.out.println("Querying for echen24");

        try {
            rf.getUserReviews("echen24");
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("Querying for 5 star reviews for moon pillow");

        try {
            rf.getProductStarReviews("Moon Pillow", 5);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("Querying for 3 star reviews for moon pillow");

        try {
            rf.getProductStarReviews("Moon Pillow", 3);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
