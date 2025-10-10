package ait.shop.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "confirm_code")
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "expired")
    private LocalDateTime expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ConfirmationCode() {
    }

    public ConfirmationCode(String value, LocalDateTime expired, User user) {
        this.value = value;
        this.expired = expired;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationCode that = (ConfirmationCode) o;
        return Objects.equals(id, that.id) && Objects.equals(value, that.value) && Objects.equals(expired, that.expired) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, expired, user);
    }

    @Override
    public String toString() {
        return "ConfirmationCode{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", expired=" + expired +
                ", user=" + user +
                '}';
    }
}
