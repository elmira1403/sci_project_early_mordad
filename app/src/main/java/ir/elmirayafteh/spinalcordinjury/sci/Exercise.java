package ir.elmirayafteh.spinalcordinjury.sci;

public class Exercise {

    private String goal;
    private String image_file;
    private String desc;

    public String getGoal() {
        return goal;
    }

    public String getImage_file() {
        return image_file;
    }

    public String getDesc() {
        return desc;
    }

    public Exercise(String goal, String image, String description) {

        this.goal = goal;
        this.image_file = image;
        this.desc = description;
    }
}
