package com.optisystems.interview.test.model;


import com.optisystems.interview.test.model.base.AbstractBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Locale;
import java.util.Objects;

/**
 * Таблица с пользователями.
 *
 * @author bigblackbug
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Entity
//`user` is a predefined postgres keyword
@Table(
    name = "`user`",
    indexes = {
        @Index(columnList = "id", name = "user_id_idx")
    }
)
@Audited
@NoArgsConstructor
@Check(constraints = "hiring_date < firing_date")
public class User extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Логин, который использует юзер для авторизации.
     */
    @Basic(optional = false)
    @Column(name = "login", unique = true)
    private String login;

    /**
     * Хэш пароля для авторизации.
     */
    @Basic(optional = false)
    @Column(name = "hash")
    private String hash;

    /**
     * Дата найма.
     */
    @Basic(optional = false)
    @Column(name = "hiring_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
        parameters = {@org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
            @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")})
    private DateTime hiringDate;

    /**
     * Дата увольнения.
     */
    @Basic(optional = false)
    @Column(name = "firing_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
        parameters = {@org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
            @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")})
    private DateTime firingDate;

    /**
     * Кол-во попыток пользователя авторизоваться
     */
    @Column(name = "attempts", nullable = false, columnDefinition = "INT NOT NULL DEFAULT 0")
    @NotAudited
    private int attempts = 0;

    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    private Role role;

    /**
     * Считается ли пользователь заблокированным при попытке авторизации.
     * <br>
     * true - пользователь заблокирован
     * <br>
     * false - пользователь незаблокирован
     */
    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    /**
     * Считается ли пользователь сотрудником
     * true - все операторы или операторы которые стали менеджером
     * false - все остальные пользователи (менеджеры, коннектор, админ)
     */
    @Column(name = "is_employee", nullable = false)
    private boolean isEmployee = false;

    /**
     * Локализация пользователя.
     */
    @Column(name = "locale", nullable = false, columnDefinition = "VARCHAR NOT NULL DEFAULT 'ru'")
    private Locale locale;

    /**
     * Недопустимость переработок (true - если недопустимы, носит информативный характер).
     */
    @Column(name = "overwork", nullable = false, columnDefinition = "BOOLEAN default false")
    private boolean overWork = false;

    /**
     * Комментарий-причина, по которой передоработки не допускаются.
     */
    @Column(name = "overwork_comment")
    private String overWorkComment;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), login);
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", hash='" + hash + '\'' +
            ", role=" + (role == null ? null : role.getName()) +
            '}';
    }
}
