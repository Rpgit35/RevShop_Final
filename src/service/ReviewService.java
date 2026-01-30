package service;

import java.util.List;
import dao.ReviewDAO;
import model.Review;

public class ReviewService {

    private ReviewDAO dao = new ReviewDAO();

    // ✅ MUST RETURN BOOLEAN
    public boolean addReview(int productId, String email, int rating, String comment) {
        Review r = new Review();
        r.setProductId(productId);
        r.setBuyerEmail(email);
        r.setRating(rating);
        r.setComment(comment);

        return dao.addReview(r);
    }

    public List<Review> getReviewsByProduct(int productId) {
        return dao.getReviewsByProduct(productId);
    }
}
