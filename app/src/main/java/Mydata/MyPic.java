package Mydata;

import android.media.Image;

public class MyPic
{

    private String name;
    private String key;
    private String owner;
    private String Image;


    public MyPic() {
    }

    public String getName() {
        return name;
    }



    public String getkey() {
        return key;
    }

    public String getOwner() {
        return owner;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getKey() {
        return key;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

