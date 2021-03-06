package com.yurets_y.sarafan.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Data
@ToString(of = {"id", "text"})
@EqualsAndHashCode(of = {"id"})
// Решение цикличесских ссылок, если Message сылается на Comment, а Comment обратно на message. и без конца.
@JsonIdentityInfo(
        generator=ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullMessage.class)
    private LocalDateTime creationTime;

    @JsonView(Views.FullMessage.class)
    private String link;

    @JsonView(Views.FullMessage.class)
    private String linkTitle;

    @JsonView(Views.FullMessage.class)
    private String linkDescription;

    @JsonView(Views.FullMessage.class)
    private String linkCover;

    @ManyToOne
    @JoinColumn(name="author_id")
    @JsonView(Views.FullMessage.class)
    private User author;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonView(Views.FullMessage.class)
//    @JsonManagedReference - альтернативная запись для решения циклических ссылок
    private List<Comment> comments = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkDescription() {
        return linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription;
    }

    public String getLinkCover() {
        return linkCover;
    }

    public void setLinkCover(String linkCover) {
        this.linkCover = linkCover;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
