package vn.edu.hcmuaf.fit.webbanquanao.user.auth.model;

import java.time.LocalDateTime;

public class TokenForgotPassword {
    private int id, userId;
    private boolean isUsed;
    private String token;
    private LocalDateTime expiredAt;

    public TokenForgotPassword() {};

    public TokenForgotPassword(int id, boolean isUsed, int userId, String token, LocalDateTime expiredAt) {
        this.id = id;
        this.isUsed = isUsed;
        this.userId = userId;
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public TokenForgotPassword( int userId, String token, LocalDateTime expiredAt) {
        this.userId = userId;
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public boolean isIsUsed() {
        return isUsed;
    }
    public void setIsUser(boolean isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "TokenForgotPassword{" +
                "id=" + id +
                ", userId=" + userId +
                ", isUsed=" + isUsed +
                ", token='" + token + '\'' +
                ", expiredAt=" + expiredAt +
                '}';
    }


}
