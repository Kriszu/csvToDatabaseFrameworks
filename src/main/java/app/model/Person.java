package app.model;

import app.validator.InputUserValidator;

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

    @Column(name = "age", nullable = false)
    private int age;

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

    public Person(String firstName, String surname, String age, String... phone) throws ParseException {
        InputUserValidator validator = new InputUserValidator();
        this.firstName = validator.nameValidator(firstName);
        this.surname = validator.nameValidator(surname);
        this.age = validator.countAge(age);
        if (phone.length == 1 && validator.numberValidator(phone[0])) this.phone = phone[0];
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", age='" + age + '\'' +
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
