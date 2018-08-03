package sample.modelLibrary.views;

import javafx.beans.property.SimpleStringProperty;

public class BorrowedBooks {

    private SimpleStringProperty bookTitle;
    private SimpleStringProperty borrowerName;
    private SimpleStringProperty borrowerLastName;
    private SimpleStringProperty orderDate;

    public BorrowedBooks() {
        this.bookTitle = new SimpleStringProperty();
        this.borrowerName = new SimpleStringProperty();
        this.borrowerLastName = new SimpleStringProperty();
        this.orderDate = new SimpleStringProperty();
    }

    public String getBookTitle() {
        return bookTitle.get();
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
    }

    public String getBorrowerName() {
        return borrowerName.get();
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName.set(borrowerName);
    }

    public String getBorrowerLastName() {
        return borrowerLastName.get();
    }

    public void setBorrowerLastName(String borrowerLastName) {
        this.borrowerLastName.set(borrowerLastName);
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }
}
