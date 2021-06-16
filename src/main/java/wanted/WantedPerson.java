package wanted;

import com.fasterxml.jackson.annotation.JsonProperty;

//Базовый POJO-класс для отображения идентификатора записи лица и его фотографии
public class WantedPerson {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("VD")
    private String vd;
    @JsonProperty("OPPHOTO1")
    private String opphoto1;
    @JsonProperty("PHOTO1BASE64ENCODE")
    private String photo1base64encode;

    public String getId() {
        return id;
    }

    public String getVd() {
        return vd;
    }

    public String getOpphoto1() {
        return opphoto1;
    }

    public String getPhoto1base64encode() {
        return photo1base64encode;
    }
}
