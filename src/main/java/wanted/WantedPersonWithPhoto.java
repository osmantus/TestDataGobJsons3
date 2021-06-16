package wanted;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

//Класс для хранения информации фотографии разыскиваемого лица
public class WantedPersonWithPhoto {
    private final int FILE_RANDOM_NAME_LEN = 16;
    private final String IMAGE_FILE_EXTENSION = ".png";
    private String id;
    private byte[] photoBase64Buf;
    private BufferedImage photo;
    private String photoFileName;
    private String photoFileFullPath;

    public WantedPersonWithPhoto(String id, String photoBase64Str, String absolutePath) {
        this.id = id;
        photoBase64Buf = Base64.getDecoder().decode(photoBase64Str);

        /*RandomString randomStringGen = new RandomString(FILE_RANDOM_NAME_LEN);
        this.photoFileName = randomStringGen.nextString() + IMAGE_FILE_EXTENSION;*/

        this.photoFileName = id + IMAGE_FILE_EXTENSION; //файл называем по строке в идентификаторе человека
        this.photoFileFullPath = absolutePath.concat(this.photoFileName);

        this.photo = null;
        try {
            Files.write(Paths.get(this.photoFileFullPath), photoBase64Buf);

            try {
                this.photo = ImageIO.read(Paths.get(this.photoFileFullPath).toAbsolutePath().toFile());

            } catch(Exception e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WantedPersonWithPhoto(String id, BufferedImage photo) {
        this.id = id;
        this.photo = photo;

        /*RandomString randomStringGen = new RandomString(FILE_RANDOM_NAME_LEN);
        this.photoFileName = randomStringGen.nextString() + IMAGE_FILE_EXTENSION;*/

        this.photoFileName = id + IMAGE_FILE_EXTENSION; //файл называем по строке в идентификаторе человека
    }

    public String getId() {
        return id;
    }

    public BufferedImage getPhoto() {
        return photo;
    }

    public byte[] getPhotoBase64Buf() {
        return photoBase64Buf;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public String getPhotoFileFullPath() {
        return photoFileFullPath;
    }
}
