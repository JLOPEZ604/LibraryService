/**
 * This class generates a Member object
 * A Member object holds a UUID (held as a string),
 * a first and last name, Address, and phone number
 * */
public class Member {
    private String uuid;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String phoneNumber;


    public Member(String id, String firstName, String lastName, String address, String number) {
        this.uuid = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = address;
        this.phoneNumber = number;
    }

    public void setUuid(String id) {
        this.uuid = id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getFirstName() {
        return  this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Member{" +
                "UUID='" + uuid + '\'' +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Street Address='" + streetAddress + '\'' +
                ", Phone Number='" + phoneNumber + '\'' +
                "}\n";
    }
}
