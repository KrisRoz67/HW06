import java.util.List;

public enum Gender {

    MALE(List.of(1, 3, 5), "male"),
    FEMALE(List.of(2, 4, 6), "female");

    public final List<Integer> numeric;
    public final String gender;

    Gender(List<Integer> numeric, String gender) {
        this.numeric = numeric;
        this.gender = gender;
    }

    public static Gender getGender(int genderNumber) {
        if (MALE.numeric.contains(genderNumber)) {
            return MALE;
        } else if (FEMALE.numeric.contains(genderNumber)) {
            return FEMALE;
        } else {
            throw new RuntimeException("No such option");
        }
    }
    public static boolean isGenderNumberCorrect(int genderNumber) {
        return  (MALE.numeric.contains(genderNumber)|| FEMALE.numeric.contains(genderNumber));
    }
}