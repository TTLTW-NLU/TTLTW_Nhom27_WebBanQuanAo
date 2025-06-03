package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model;

public class CommentProduct {
    private int id;
    private String content;
    private String authorAvatar;
    private String authorName;
    private int rating;

    public CommentProduct() {
    }

    public CommentProduct(int id, String content, String authorAvatar, String authorName, int rating) {
        this.id = id;
        this.content = content;
        this.authorAvatar = authorAvatar;
        this.authorName = authorName;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", authorName='" + authorName + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
