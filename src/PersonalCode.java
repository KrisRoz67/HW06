public class PersonalCode {
    int genderNumber;
    int year;
    int month;
    int day;
    int orderNumber;
    int uniqueNumber;


    public PersonalCode(int genderNumber, int year, int month, int day, int orderNumber, int uniqueNumber) {
        this.genderNumber = genderNumber;
        this.year = year;
        this.month = month;
        this.day = day;
        this.orderNumber = orderNumber;
        this.uniqueNumber = uniqueNumber;

    }

    public static PersonalCode getInformationFromIdCode(String idCode) {
        int genderNumber = Integer.parseInt(idCode.substring(0, 1));
        int year = Integer.parseInt(idCode.substring(1, 3));
        int month = Integer.parseInt(idCode.substring(3, 5));
        int day = Integer.parseInt(idCode.substring(5, 7));
        int orderNumber = Integer.parseInt(idCode.substring(7, 8));
        int uniqueNumber = Integer.parseInt(idCode.substring(8, 11));
        return new PersonalCode(genderNumber, year, month, day, orderNumber, uniqueNumber);
    }
}

