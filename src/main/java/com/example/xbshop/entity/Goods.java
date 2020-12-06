package com.example.xbshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Format;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liao
 * @date 2020/12/6 13:34
 * @Description
 */
@Entity
@Table(name = "goods")
public class Goods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String pic;

    private String content;

    private Long browseCount;

    private Double price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date publishDate;

    private String publishRealName;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Long browseCount) {
        this.browseCount = browseCount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPublishRealName() {
        return publishRealName;
    }

    public void setPublishRealName(String publishRealName) {
        this.publishRealName = publishRealName;
    }

}
