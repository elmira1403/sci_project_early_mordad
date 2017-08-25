package ir.elmirayafteh.spinalcordinjury.sci;


public class FitnessCalculator {

    private String result;
    private String hw_result;
    private String bmi_result;
    private double bmi_number;
    private int my_age_i;
    private double ideal;

    private int[] age_up_list = {24, 34, 44, 54, 65, 100};
    private int[] age_down_list = {19, 25, 35, 45, 55, 65};

    private int[] bmi_up_list = {24, 25, 26, 27, 28, 29};
    private int[] bmi_down_list = {19, 20, 21, 22, 23, 24};


    public String FitnessCalc(int height, int weight, int age, String gender) {

        bmi_result = BMI(height, weight, age);

        if (gender.equals("مرد")) {
            ideal = height - 102;
            if (age > 59) {
                ideal = ideal * 1.1;
            }
            if (height > 174) {
                ideal = ideal * 1.1;
            } else if (height < 166) {
                ideal = ideal * 0.9;
            }

        } else {
            ideal = 45 + (height - 150) * 0.9;
            if (age > 59) {
                ideal = ideal * 1.1;
            }
            if (height > 164) {
                ideal = ideal * 1.1;
            } else if (height > 156) {
                ideal = ideal * 0.9;
            }

        }

        if (weight > ideal - 3 && weight < ideal + 4) {
            hw_result = "معمولی";
        } else if (weight > ideal + 3) {
            hw_result = "چاق";
        } else {
            hw_result = "لاغر اندام";
        }

        if (hw_result.equals(bmi_result)) {
            result = hw_result;
        } else {
            if (bmi_result.equals("معمولی") || hw_result.equals("معمولی")) {
                result = "معمولی";
            } else {
                result = bmi_result;
            }
        }

        return result;
    }

    private String BMI(int height, int weight, int age) {

        bmi_number = weight*10000 / Math.pow(height, 2);

        for (int i = 0; i < 6; i++) {
            if (age < age_up_list[i] + 1 && age > age_down_list[i] - 1) {
                my_age_i = i;
            }
        }

        if (bmi_number < bmi_up_list[my_age_i] + 1 && bmi_number > bmi_down_list[my_age_i] - 1) {
            return "معمولی";
        } else if (bmi_number > bmi_up_list[my_age_i]) {
            return "چاق";
        } else {
            return "لاغر اندام";
        }
    }
}
