package wanted;

import com.fasterxml.jackson.annotation.JsonProperty;

//POJO-класс для отображения информации о всех разыскиваемых лицах с идентификатором записи PHOTOID из таблицы с фото
public class WantedPersonGeneral {

    //private int id;
    @JsonProperty("ID")
    private String id;

    @JsonProperty("OVD")
    private String ovd;
    @JsonProperty("CATEGORY")
    private String category;
    @JsonProperty("FIRST_NAME_U")
    private String first_name_u;
    @JsonProperty("LAST_NAME_U")
    private String last_name_u;
    @JsonProperty("MIDDLE_NAME_U")
    private String middle_name_u;
    @JsonProperty("FIRST_NAME_R")
    private String first_name_r;
    @JsonProperty("LAST_NAME_R")
    private String last_name_r;
    @JsonProperty("MIDDLE_NAME_R")
    private String middle_name_r;
    @JsonProperty("FIRST_NAME_E")
    private String first_name_e;
    @JsonProperty("LAST_NAME_E")
    private String last_name_e;
    @JsonProperty("MIDDLE_NAME_E")
    private String middle_name_e;

    //private LocalDateTime birth_date;
    @JsonProperty("BIRTH_DATE")
    private String birth_date;

    @JsonProperty("SEX")
    private String sex;
    //private LocalDateTime lost_date;
    @JsonProperty("LOST_DATE")
    private String lost_date;

    @JsonProperty("LOST_PLACE")
    private String lost_place;
    @JsonProperty("ARTICLE_CRIM")
    private String article_crim;
    @JsonProperty("RESTRAINT")
    private String restraint;
    @JsonProperty("CONTACT")
    private String contact;
    @JsonProperty("PHOTOID")
    private String photoid;

    public String getId() {
        return id;
    }

    public String getOvd() {
        return ovd;
    }

    public String getCategory() {
        return category;
    }

    public String getFirst_name_u() {
        return first_name_u;
    }

    public String getLast_name_u() {
        return last_name_u;
    }

    public String getMiddle_name_u() {
        return middle_name_u;
    }

    public String getFirst_name_r() {
        return first_name_r;
    }

    public String getLast_name_r() {
        return last_name_r;
    }

    public String getMiddle_name_r() {
        return middle_name_r;
    }

    public String getFirst_name_e() {
        return first_name_e;
    }

    public String getLast_name_e() {
        return last_name_e;
    }

    public String getMiddle_name_e() {
        return middle_name_e;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getSex() {
        return sex;
    }

    public String getLost_date() {
        return lost_date;
    }

    public String getLost_place() {
        return lost_place;
    }

    public String getArticle_crim() {
        return article_crim;
    }

    public String getRestraint() {
        return restraint;
    }

    public String getContact() {
        return contact;
    }

    public String getPhotoid() {
        return photoid;
    }

    @Override
    public String toString() {
        return "wanted.WantedPerson{" +
                "id='" + id + '\'' +
                ", first_name_u='" + first_name_u + '\'' +
                ", last_name_u='" + last_name_u + '\'' +
                ", middle_name_u='" + middle_name_u + '\'' +
                ", photoid='" + photoid + '\'' +
                '}';
    }
}
