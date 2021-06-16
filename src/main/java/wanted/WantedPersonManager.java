package wanted;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class WantedPersonManager {

    public static final String IMAGE_FILE_EXTENSION = ".png";
    public WantedPersonGeneral wantedPersonGeneral;

    public WantedPersonManager(WantedPersonGeneral wantedPersonGeneral) {
        this.wantedPersonGeneral = wantedPersonGeneral;
    }

    public WantedPersonGeneral getWantedPersonGeneral() {
        return wantedPersonGeneral;
    }

    public BufferedImage getWantedPersonPhoto(String personId, String absoluteFilePath) {
        BufferedImage returnedImage = null;
        if (wantedPersonGeneral != null) {
            String photoId = wantedPersonGeneral.getPhotoid();
            String fileName = personId.concat(IMAGE_FILE_EXTENSION);
            String filePath = absoluteFilePath.concat(fileName);

            try {
                returnedImage = ImageIO.read(Paths.get(filePath).toAbsolutePath().toFile());

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return returnedImage;
    }

    private static BufferedImage getPhotoById(String photoId, String absoluteFilePath) {
        BufferedImage photo = null;
        String fileName = photoId.concat(IMAGE_FILE_EXTENSION);
        String filePath = absoluteFilePath.concat(fileName);

        try {
            photo = ImageIO.read(Paths.get(filePath).toAbsolutePath().toFile());

        } catch(Exception e) {
            e.printStackTrace();
        }
        return photo;
    }

    private static byte[] getOrigPhotoBufferById(String photoId, String absoluteFilePath) {
        byte[] photo = null;
        String fileName = photoId.concat(IMAGE_FILE_EXTENSION);
        String filePath = absoluteFilePath.concat(fileName);

        try {
            photo = Files.readAllBytes(Paths.get(filePath));

        } catch(Exception e) {
            e.printStackTrace();
        }
        return photo;
    }

    public static byte[] getWantedPersonPhotoByFirstName(String firstName, int language, List<WantedPersonGeneral> personsList, String absoluteFilePath) {
        //поиск фото по фамилии
        //int language - (0 - украинский, 1 - русский, 2 - английский варианты имён)
        //BufferedImage photo = null;
        byte[] photo = null;

        Optional<WantedPersonGeneral> matchingObject = null;
        if (language == 0) {
            matchingObject = personsList.stream().filter(p -> p.getFirst_name_u().equals(firstName)).findFirst();
        } else if (language == 1) {
            matchingObject = personsList.stream().filter(p -> p.getFirst_name_r().equals(firstName)).findFirst();
        } else if (language == 2) {
            matchingObject = personsList.stream().filter(p -> p.getFirst_name_e().equals(firstName)).findFirst();
        }

        if (matchingObject != null) {
            WantedPersonGeneral personGeneral = matchingObject.get();

            if (personGeneral != null) {
                String photoId = personGeneral.getPhotoid();
                //photo = getPhotoById(photoId, absoluteFilePath);
                photo = getOrigPhotoBufferById(photoId, absoluteFilePath);
            }
        }

        return photo;
    }
    public static byte[] getWantedPersonPhotoByLastName(String lastName, int language, List<WantedPersonGeneral> personsList, String absoluteFilePath) {
        //поиск фото по имени
        //int language - (0 - украинский, 1 - русский, 2 - английский варианты имён)
        //BufferedImage photo = null;
        byte[] photo = null;

        Optional<WantedPersonGeneral> matchingObject = null;
        if (language == 0) {
            matchingObject = personsList.stream().filter(p -> p.getLast_name_u().equals(lastName)).findFirst();
        } else if (language == 1) {
            matchingObject = personsList.stream().filter(p -> p.getLast_name_r().equals(lastName)).findFirst();
        } else if (language == 2) {
            matchingObject = personsList.stream().filter(p -> p.getLast_name_e().equals(lastName)).findFirst();
        }

        if (matchingObject != null) {
            WantedPersonGeneral personGeneral = matchingObject.get();

            if (personGeneral != null) {
                String photoId = personGeneral.getPhotoid();
                //photo = getPhotoById(photoId, absoluteFilePath);
                photo = getOrigPhotoBufferById(photoId, absoluteFilePath);
            }
        }

        return photo;
    }
    public static byte[] getWantedPersonPhotoByMiddleName(String middleName, int language, List<WantedPersonGeneral> personsList, String absoluteFilePath) {
        //поиск фото по отчеству
        //int language - (0 - украинский, 1 - русский, 2 - английский варианты имён)
        //BufferedImage photo = null;
        byte[] photo = null;

        Optional<WantedPersonGeneral> matchingObject = null;
        if (language == 0) {
            matchingObject = personsList.stream().filter(p -> p.getMiddle_name_u().equals(middleName)).findFirst();
        } else if (language == 1) {
            matchingObject = personsList.stream().filter(p -> p.getMiddle_name_r().equals(middleName)).findFirst();
        } else if (language == 2) {
            matchingObject = personsList.stream().filter(p -> p.getMiddle_name_e().equals(middleName)).findFirst();
        }

        if (matchingObject != null) {
            WantedPersonGeneral personGeneral = matchingObject.get();

            if (personGeneral != null) {
                String photoId = personGeneral.getPhotoid();
                //photo = getPhotoById(photoId, absoluteFilePath);
                photo = getOrigPhotoBufferById(photoId, absoluteFilePath);
            }
        }

        return photo;
    }
}
