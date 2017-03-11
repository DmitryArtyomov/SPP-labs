package com.bsuir.petition.bean;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "petitions", schema = "petition_spring")
public class Petition extends BaseTable{

    @Column(name = "name", length = 128)
    @ColumnDefault("\'No name\'")
    private String name;

    @Column(name = "description", columnDefinition="VARCHAR(1024)")
    @ColumnDefault("\'No description\'")
    private String description;

    @Column(name = "number_necessary_votes", nullable = false)
    private int numberNecessaryVotes;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "statusId")
    @ColumnDefault("0")
    private int statusId;

    @OneToMany(mappedBy = "petition", fetch = FetchType.LAZY)
    private Set<Vote> voteSet = new HashSet<Vote>();

    @OneToMany(mappedBy = "petition", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<Comment>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdUser;

    @ManyToMany(mappedBy = "petitions", fetch = FetchType.LAZY)
    private Set<Category> categories = new HashSet<Category>();

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public Set<Vote> getVoteSet() {
        return voteSet;
    }

    public void setVoteSet(Set<Vote> voteSet) {
        this.voteSet = voteSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberNecessaryVotes() {
        return numberNecessaryVotes;
    }

    public void setNumberNecessaryVotes(int numberNecessaryVotes) {
        this.numberNecessaryVotes = numberNecessaryVotes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}