import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для проверки эстонских личных кодов (ID code).
 */
public class IDCodeValidator {
    /**
     * Проверяет, является ли данный ID code допустимым.
     * Использует все остальные методы для проверки
     *
     * @param idCode строка, представляющая ID code
     * @return {@code true}, если ID code допустим, иначе {@code false}
     */
    public static boolean isCorrect(String idCode) {
        System.out.println(idCode);
        if (idCode.length() == 11) {
            System.out.println("The length of your ID code is right");
            PersonalCode personalCode = PersonalCode.getInformationFromIdCode(idCode);
            if (isGenderNumberCorrect(personalCode.genderNumber)
                    && isDayNumberCorrect(personalCode.day)
                    && isMonthNumberCorrect(personalCode.month)
                    && isBirthDateCorrect(personalCode.year, personalCode.month, personalCode.day)
                    && checkControlNumber(idCode))
                return true;
        } else {
            System.out.println("The length doesn't match the rules");
            return false;
        }
        System.out.println("The Id code doesn't match the rules");
        return false;

    }

    /**
     * Проверяет корректность кода пола в ID code.
     *
     * @param genderNumber целое число, представляющее код пола
     * @return {@code true}, если код пола корректен, иначе {@code false}
     */
    public static boolean isGenderNumberCorrect(int genderNumber) {
        if (Gender.isGenderNumberCorrect(genderNumber)) {
            System.out.println("Gender number is correct");
            return true;
        }
        System.out.println("Gender number is not correct");
        return false;
    }


    /**
     * Проверяет корректность номера месяца рождения в ID code.
     *
     * @param dayNumber целое число, представляющее номер месяца
     * @return {@code true}, если номер дня корректен, иначе {@code false}
     */
    public static boolean isDayNumberCorrect(int dayNumber) {
        if (dayNumber > 0 && dayNumber < 32) {
            System.out.println("Day number is correct");
            return true;
        }
        System.out.println("Day number is not correct");
        return false;
    }

    /**
     * Проверяет корректность номера месяца рождения в ID code.
     *
     * @param monthNumber целое число, представляющее номер месяца
     * @return {@code true}, если номер месяца корректен, иначе {@code false}
     */
    public static boolean isMonthNumberCorrect(int monthNumber) {

        if (monthNumber > 0 && monthNumber < 13) {
            System.out.println("Month number is correct");
            return true;
        }
        System.out.println("Month number is not correct");
        return false;
    }


    /**
     * Проверяет корректность даты рождения в ID code.
     *
     * @param yearNumber  целое число, представляющее год рождения (1955, 1999, 2013)
     * @param monthNumber целое число, представляющее номер месяца (1, 8)
     * @param dayNumber   целое число, представляющее номер дня (15, 31)
     * @return {@code true}, если номер дня корректен, иначе {@code false}
     */
    public static boolean isBirthDateCorrect(int yearNumber, int monthNumber, int dayNumber) {
        boolean result;
        Map<Integer, Integer> monthToDays = new HashMap<>();
        monthToDays.put(1, 31);
        monthToDays.put(2, 28);
        monthToDays.put(3, 31);
        monthToDays.put(4, 30);
        monthToDays.put(5, 31);
        monthToDays.put(6, 30);
        monthToDays.put(7, 31);
        monthToDays.put(8, 31);
        monthToDays.put(9, 30);
        monthToDays.put(10, 31);
        monthToDays.put(11, 30);
        monthToDays.put(12, 31);
        if (monthNumber == 2 && isLeapYear(yearNumber)) {
            result = (dayNumber <= monthToDays.get(monthNumber) + 1);
        } else {
            result = (dayNumber <= monthToDays.get(monthNumber));
        }
        if (result) {
            System.out.println("Your birth date is correct");
        } else {
            System.out.println("Your birth date is not correct");
        }
        return result;
    }

    /**
     * Проверяет, является ли данный год високосным.
     *
     * @param yearNumber целое число, представляющее год
     * @return {@code true}, если год високосный, иначе {@code false}
     */
    public static boolean isLeapYear(int yearNumber) {
        if (yearNumber % 4 == 0 && yearNumber % 100 != 0) {
            return true;
        } else return yearNumber % 400 == 0;
    }

    /**
     * Проверяет корректность контрольной суммы в ID code.
     *
     * @param idCode строка, представляющая ID code
     * @return {@code true}, если контрольная сумма корректна, иначе {@code false}
     */
    public static boolean checkControlNumber(String idCode) {
        int result;
        List<Integer> listOfIdCodeNumbers = new ArrayList<>();
        for (int i = 0; i < idCode.length(); i++) {
            String n = String.valueOf(idCode.charAt(i));
            listOfIdCodeNumbers.add(Integer.parseInt(n));
        }
        List<Integer> x1 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1);
        result = calculateSum(listOfIdCodeNumbers, x1);
        if (result%11 == 10) {
            List<Integer> x2 = List.of(3, 4, 5, 6, 7, 8, 9, 1, 2, 3);
            result = calculateSum(listOfIdCodeNumbers, x2);
            System.out.println("Your reminder is 10 , so we use another calculation");
        }
   //     int reminder = Math.abs((result / 11) * 11 - result); ----- dlja sebja
        int reminder = result%11;
        if (reminder == listOfIdCodeNumbers.get(listOfIdCodeNumbers.size() - 1)) {
            System.out.printf("Control number is correct. The reminder is %s.That equals to your control number %s %n",
                    reminder,
                    listOfIdCodeNumbers.get(listOfIdCodeNumbers.size() - 1));
            return true;
        } else {
            System.out.println("Control number is not correct");
            System.out.printf("Reminder %s  doesn't equal to control number %s%n",
                    reminder,
                    listOfIdCodeNumbers.get(listOfIdCodeNumbers.size() - 1));
            return false;
        }
    }

    /**
     * Метод, для получения информаций из ИД кода
     * Используйте данный формат - This is a (gender) born on (DD.MM.YYYY).
     * Gender в качестве ENUM
     *
     * @param idCode строка, представляющая ID code
     * @return Строка, в которой написан пол и дата рождения человека
     */
    public static String getInformation(String idCode) {
        if (isCorrect(idCode)) {
            PersonalCode personalCode = PersonalCode.getInformationFromIdCode(idCode);
            int year = personalCode.year;
            int genderNum = personalCode.genderNumber;
            int month = personalCode.month;
            int day = personalCode.day;
            int fullYear = getFullYear(genderNum, year);
            String gender = getGender(genderNum).gender;
            return String.format("This is a (%s) born on (%s.%s.%s)",
                    gender,
                    convertIntToString(day),
                    convertIntToString(month),
                    fullYear);
        } else {
            return "Idcode is not correct";
        }
    }

    /**
     * Верните пол человека
     *
     * @param genderNumber The gender number from the ID code.
     * @return The gender as a string ("male" or "female").
     */
    public static Gender getGender(int genderNumber) {
        return Gender.getGender(genderNumber);
    }

    /**
     * Верните год, когда человек родился (1955, 2014)
     *
     * @param genderNumber The gender number from the ID code.
     * @param year         The two last digits of the birth year from the ID code.
     * @return The full 4-digit birth year.
     */
    public static int getFullYear(int genderNumber, int year) {
        return switch (genderNumber) {
            case 1, 2 -> 1800 + year;
            case 3, 4 -> 1900 + year;
            case 5, 6 -> 2000 + year;
            default -> throw new RuntimeException("Unknown gender");
        };

    }

    public static String convertIntToString(int num) {
        String n = String.valueOf(num);
        if (n.length() == 1) {
            n = "0" + n;
        }
        return n;
    }

    public static int calculateSum(List<Integer> listOfIdCodeNumbers, List<Integer> x) {
        int result = 0;
        for (int i = 0; i < listOfIdCodeNumbers.size() - 1; i++) {
            result = result + (listOfIdCodeNumbers.get(i) * x.get(i));
        }
        return result;
    }
}