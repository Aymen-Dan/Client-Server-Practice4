package storage;

import lombok.Builder;

@Builder
public class Group {

    private String gName;
    private String description;

    public Group(final String gName, final String description){
        this.gName = gName;
        this.description = description;
    }


    @Override
    public String toString() {
        return "<-- Group name: " + gName +
                "; Group description: " + description + "-->\n";
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
