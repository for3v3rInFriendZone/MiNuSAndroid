package model;

/**
 * Created by Dejan on 6/7/2017.
 */

public class Budget {

    private Long id;
    private Long dateFrom;
    private Long dateTo;
    private double value;
    private User user;

    public Budget(){

    }

    public Budget(Long dateFrom, Long dateTo, double value, User user) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.value = value;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Long getDateTo() {
        return dateTo;
    }

    public void setDateTo(Long dateTo) {
        this.dateTo = dateTo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
