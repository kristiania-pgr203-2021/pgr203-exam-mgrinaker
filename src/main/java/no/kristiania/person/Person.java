package no.kristiania.person;

public class Person { //aka User
    private long userId;
    private String firstName;
    private String lastName;
    private String mailAddress;
    private long professionId;
    private long workplaceId;

    public long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(long professionId) {
        this.professionId = professionId;
    }

    public long getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(long workplaceId) {
        this.workplaceId = workplaceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
