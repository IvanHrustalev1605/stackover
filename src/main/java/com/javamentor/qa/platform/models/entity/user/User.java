package com.javamentor.qa.platform.models.entity.user;

import com.javamentor.qa.platform.models.entity.registration.VerificationToken;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_entity")
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "User_seq")
    private Long id;

    @Column
    @NonNull
    private String email;

    @Column
    @NonNull
    private String password;

    @Column
    private String fullName;

    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDateTime;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column
    private String city;

    @Column(name = "link_site")
    private String linkSite;

    @Column(name = "link_github")
    private String linkGitHub;

    @Column(name = "link_vk")
    private String linkVk;

    @Column
    private String about;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "last_redaction_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime lastUpdateDateTime;

    @Column
    private String nickname;
    @Column(name = "enabled")
    private boolean enabled;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "role_id", nullable = false)
    @NonNull
    private Role role;
//    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "id", referencedColumnName = "verify_id")
//    private VerificationToken verificationToken;
    public User(Object o, String s, String encode, String alexDuncan, LocalDateTime now, boolean b, boolean b1, String nyc, Object o1, Object o2, Object o3, String some, Object o4, Object o5, Object o6, Role admin) {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(fullName, user.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, fullName);
    }
}
