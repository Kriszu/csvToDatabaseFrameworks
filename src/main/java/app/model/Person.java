package app.model;

import app.validator.InputUserValidator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy.mm.dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "phone")
    private String phone;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person(String firstName, String surname, String date, String... phone) throws ParseException {
        InputUserValidator validator = new InputUserValidator();
        this.firstName = validator.nameValidator(firstName);
        this.surname = validator.nameValidator(surname);
        this.date = validator.dateValidator(date);
        if (phone.length == 1 && validator.numberValidator(phone[0])) this.phone = phone[0];
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", date='" + date + '\'' +
                ", phoneNumber='" + phone + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
