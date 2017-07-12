package tk.mybatis.springboot.model;

/**
 * Created by wudongfeng on 17/7/11.
 */
public class Element {
    private String id;
    private String name;
    private int type;
    private String attachPath;
    private String voicePath;
    private Long courseId;
    private String CourseName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", attachPath='" + attachPath + '\'' +
                ", voicePath='" + voicePath + '\'' +
                ", courseId=" + courseId +
                ", CourseName='" + CourseName + '\'' +
                '}';
    }
}
