package com.sdms.sdms.Library;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "library")
@NoArgsConstructor
@AllArgsConstructor
public class Books {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    @Setter
    @Getter
    Long id;
    @Setter
    @Getter
    String name;
    @Setter
    @Getter
    String author;
    @Setter
    @Getter
    String publisher;
    @Column(name = "start_date")
    @Setter
    @Getter
    LocalDate startDate;
    @Transient
    private Integer period;

    public Books(String name, String author, String publisher, LocalDate startDate) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.startDate = startDate;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public void setPublisher(String publisher) {
//        this.publisher = publisher;
//    }
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public String  getAuthor() {
//        return this.author;
//    }
//
//    public String getPublisher() {
//        return this.publisher;
//    }
//
//    public LocalDate getStartDate() {
//        return this.startDate;
//    }
//
    public Integer getStartPeriod() {
        var periodStructured = Period.between(this.startDate, LocalDate.now());
        return periodStructured.getDays() + periodStructured.getMonths() * 30 + periodStructured.getYears() * 365;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return Objects.equals(id, books.id) && Objects.equals(name, books.name) && Objects.equals(author, books.author) && Objects.equals(publisher, books.publisher) && Objects.equals(startDate, books.startDate) && Objects.equals(period, books.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, publisher, startDate, period);
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", startDate=" + startDate +
                ", period=" + period +
                '}';
    }
}
