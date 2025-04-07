package anu.g35.sharebooks.data.model;

/**
 * The daily statistics data structure
 *
 * @author u7706346 Anbo Wu
 * @since 2024-05-06
 */
public class DailyStat {

   // Date | Registered Users | Active Users | Books Borrowed | Books Shared
    private String date;
    private int registeredUsers;
    private int activeUsers;
    private int booksBorrowed;
    private int booksShared;

    public DailyStat() {
    }
    public DailyStat(String date, int registeredUsers, int activeUsers, int booksBorrowed, int booksShared) {
        this.date = date;
        this.registeredUsers = registeredUsers;
        this.activeUsers = activeUsers;
        this.booksBorrowed = booksBorrowed;
        this.booksShared = booksShared;
    }

    /**
     * Copy a DailyStat object
     * @param from DailyStat object
     */
    public DailyStat(DailyStat from) {
        this.date = from.date;
        this.registeredUsers = from.registeredUsers;
        this.activeUsers = from.activeUsers;
        this.booksBorrowed = from.booksBorrowed;
        this.booksShared = from.booksShared;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(int registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public void setBooksBorrowed(int booksBorrowed) {
        this.booksBorrowed = booksBorrowed;
    }

    public int getBooksShared() {
        return booksShared;
    }

    public void setBooksShared(int booksShared) {
        this.booksShared = booksShared;
    }

    @Override
    public String toString() {
        return "DailyStat{" +
                "date='" + date + '\'' +
                ", registeredUsers=" + registeredUsers +
                ", activeUsers=" + activeUsers +
                ", booksBorrowed=" + booksBorrowed +
                ", booksShared=" + booksShared +
                '}';
    }
}
