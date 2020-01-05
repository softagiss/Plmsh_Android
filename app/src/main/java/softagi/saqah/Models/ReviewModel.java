package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewModel implements Serializable
{
    @SerializedName("product_id")
    private int productId;
    @SerializedName("review")
    private String review;
    @SerializedName("reviewer")
    private String reviewer;
    @SerializedName("reviewer_email")
    private String reviewerEmail;
    @SerializedName("rating")
    private int rating;

    public ReviewModel() {
    }

    public ReviewModel(int productId, String review, String reviewer, String reviewerEmail, int rating) {
        this.productId = productId;
        this.review = review;
        this.reviewer = reviewer;
        this.reviewerEmail = reviewerEmail;
        this.rating = rating;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
