import com.fasterxml.jackson.databind.ObjectMapper;
import wanted.WantedPerson;
import wanted.WantedPersonGeneral;
import wanted.WantedPersonManager;
import wanted.WantedPersonWithPhoto;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import oracle.OraProcedureExecutor;

//Класс для запуска программы
public class TestDataGovJsons3 {

    final static String JSON_FILES_PATH = "C:\\Work\\Postman\\Requests with download files\\";

    final static String FULL_LIST_JSON_FILE_NAME = "mvswantedperson_1.json";

    final static List<String> WANTEDPERSONS_JSON_FILES_NAMES = Arrays.asList(
        "mvswantedperson_photo_220.json",
        "mvswantedperson_photo_297.json",
        "mvswantedperson_photo_298.json",
        "mvswantedperson_photo_299.json",
        "mvswantedperson_photo_303.json",
        "mvswantedperson_photo_304.json",
        "mvswantedperson_photo_306.json",
        "mvswantedperson_photo_309.json",
        "mvswantedperson_photo_310.json",
        "mvswantedperson_photo_311.json",
        "mvswantedperson_photo_363.json",
        "mvswantedperson_photo_374.json",
        "mvswantedperson_photo_407.json",
        "mvswantedperson_photo_429.json",
        "mvswantedperson_photo_451.json",
        "mvswantedperson_photo_555.json"
    );

    public static void main(String[] args) {

        // JSON string to Java object
            /*String jsonInString = "{\"ID\":\"5119623\",\"OVD\":\"КИЇВСЬКИЙ ВІДДІЛ ПОЛІЦІЇ В МІСТІ ОДЕСІ ГУНП В ОДЕСЬКІЙ ОБЛАСТІ\",\"CATEGORY\":\"ОСОБА, ЯКА ПЕРЕХОВУЄТЬСЯ ВІД ОРГАНІВ ДОСУДОВОГО РОЗСЛІДУВАННЯ\",\"FIRST_NAME_U\":\"ШЕВЧУК\",\"LAST_NAME_U\":\"ОЛЕГ\",\"MIDDLE_NAME_U\":\"ВІТАЛІЙОВИЧ\",\"FIRST_NAME_R\":\"ШЕВЧУК\",\"LAST_NAME_R\":\"ОЛЕГ\",\"MIDDLE_NAME_R\":\"ВИТАЛЬЕВИЧ\",\"FIRST_NAME_E\":\"SHEVCHUK\",\"LAST_NAME_E\":\"OLEH\",\"MIDDLE_NAME_E\":\"VITALIIOVYCH\",\"BIRTH_DATE\":\"1953-10-16T00:00:00\",\"SEX\":\"ЧОЛОВІЧА\",\"LOST_DATE\":\"1994-09-03T00:00:00\",\"LOST_PLACE\":\"ОДЕСЬКА, ОДЕСА\",\"ARTICLE_CRIM\":\"СТ.121 Ч.2\",\"RESTRAINT\":\"УХВАЛА СУДУ ПРО ДОЗВІЛ НА ЗАТРИМАННЯ З МЕТОЮ ПРИВОДУ, 22.04.2021\",\"CONTACT\":\"048-7668998\",\"PHOTOID\":\"303016611\"}";
            wanted.WantedPerson wantedPerson2 = mapper.readValue(jsonInString, wanted.WantedPerson.class);

            // compact print
            System.out.println(wantedPerson2);

            // pretty print
            String prettyPerson2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wantedPerson2);
            System.out.println(prettyPerson2);*/

        String absolutePath = "C:\\Work\\Postman\\Requests with download files\\";

        ObjectMapper mapper = new ObjectMapper();

        /////////
        List<WantedPersonGeneral> wantedPersonsGeneralList = null;
        Set<String> categories = new HashSet<>();

        try {

            // JSON file to Java object
            //wanted.WantedPerson wantedPerson1 = mapper.readValue(new File("C:\\Work\\Postman\\Requests with download files\\mvswantedperson_1.json"), wanted.WantedPerson.class);

            //составляем общий список разыскиваемых лиц без фотографий, но с ключами на них в другую таблицу
            wantedPersonsGeneralList = Arrays.asList(mapper.readValue(new File(JSON_FILES_PATH + FULL_LIST_JSON_FILE_NAME), WantedPersonGeneral[].class));

            for (WantedPersonGeneral person : wantedPersonsGeneralList) {
                System.out.println(person);
                categories.add(person.getCategory());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //набор доступных категорий разыскиваемых лиц выводим в файл на диске
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("C:\\Work\\Temp\\categories.txt"));
            for (String category : categories) {
                bw.write(category.concat("\n"));
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /////////
        List<WantedPerson> wantedPersonsList = null;

        //int index = 0, index2 = 0;
        for (String jsonFileName : WANTEDPERSONS_JSON_FILES_NAMES) {
            //index++;
            //if (index < 16) continue;
            try {
                wantedPersonsList = Arrays.asList(mapper.readValue(new File(JSON_FILES_PATH + jsonFileName), WantedPerson[].class));

                //index2 = 0;
                for (WantedPerson person : wantedPersonsList) {
                    //if (index2 > 9)
                    //    break;
                    //wanted.WantedPerson person = wantedPersonsList.get(0);

                    String base64PictStr = person.getPhoto1base64encode();

                    //создаём объект с полем, содержащим фото, и выгружаем это фото на диск
                    WantedPersonWithPhoto wantedPersonWithPhoto = new WantedPersonWithPhoto(person.getId(), base64PictStr, absolutePath);
                    BufferedImage image = wantedPersonWithPhoto.getPhoto();
                    String photoFileFullPath = wantedPersonWithPhoto.getPhotoFileFullPath();

                    System.out.println("Путь к файлу на сервере: " + photoFileFullPath);
                    //index2++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //// тестирование на получение файлов картинок по идентификатору лица и пути к файлам

        BufferedImage returnedImage = null;
        WantedPersonGeneral personGeneral = wantedPersonsGeneralList.get(0);
        WantedPersonManager manager = new WantedPersonManager(personGeneral);

        String firstPersonId = personGeneral.getId();
        returnedImage = manager.getWantedPersonPhoto(firstPersonId, absolutePath);

        int listSize = wantedPersonsGeneralList.size();

        if (listSize > 2) {
            personGeneral = wantedPersonsGeneralList.get(listSize / 2);
            String inMiddlePersonId = personGeneral.getId();
            returnedImage = manager.getWantedPersonPhoto(inMiddlePersonId, absolutePath);
        }

        if (listSize >= 2) {
            personGeneral = wantedPersonsGeneralList.get(listSize - 1);
            String lastPersonId = personGeneral.getId();
            returnedImage = manager.getWantedPersonPhoto(lastPersonId, absolutePath);
        }

        File outputFile = null;
        byte[] imageBytes = null;
        //поиск по фамилии
        imageBytes = WantedPersonManager.getWantedPersonPhotoByFirstName("ДЕОПИК", 0, wantedPersonsGeneralList, absolutePath);
        //outputFile = new File(absolutePath + "first ДЕОПИК.png");
        if (imageBytes != null) {
            try {
                //ImageIO.write(returnedImage, "png", outputFile);
                Files.write(Paths.get(absolutePath + "first ДЕОПИК.png"), imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //поиск по имени
        //returnedImage = wanted.WantedPersonManager.getWantedPersonPhotoByLastName("НАТАЛЬЯ", 1, wantedPersonsGeneralList, absolutePath);
        imageBytes = WantedPersonManager.getWantedPersonPhotoByLastName("НАТАЛЬЯ", 1, wantedPersonsGeneralList, absolutePath);
        //outputFile = new File(absolutePath + "first НАТАЛЬЯ.png");
        if (imageBytes != null) {
            try {
                //ImageIO.write(returnedImage, "png", outputFile);
                Files.write(Paths.get(absolutePath + "first НАТАЛЬЯ.png"), imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //поиск по отчеству
        //returnedImage = wanted.WantedPersonManager.getWantedPersonPhotoByMiddleName("ANDRIIOVYCH", 2, wantedPersonsGeneralList, absolutePath);
        imageBytes = WantedPersonManager.getWantedPersonPhotoByMiddleName("ANDRIIOVYCH", 2, wantedPersonsGeneralList, absolutePath);
        //outputFile = new File(absolutePath + "first ANDRIIOVYCH.png");
        if (imageBytes != null) {
            try {
                //ImageIO.write(returnedImage, "png", outputFile);
                Files.write(Paths.get(absolutePath + "first ANDRIIOVYCH.png"), imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ///// begin 2021-06-08
        WantedPersonGeneral personGeneral2 = manager.getWantedPersonGeneral();

        String dbServer = "", login = "", pwrd = "";
        OraProcedureExecutor rp = new OraProcedureExecutor();
        rp.OraProcedureExecuter1(dbServer, login, pwrd, personGeneral2);
        ///// end 2021-06-08
    }

}
