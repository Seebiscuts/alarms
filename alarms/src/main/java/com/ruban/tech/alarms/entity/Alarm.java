package com.ruban.tech.alarms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Locale;


@Entity
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private Long upvotes;

    public Date getCreated() {
        return new Date(System.currentTimeMillis());
    }

    public Date getUpdated() {
        return new Date(System.currentTimeMillis());
    }

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    public Long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Long upvotes) {
        this.upvotes = upvotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text.toUpperCase(Locale.US);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", upvotes=" + upvotes +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
