package com.elearning.api.admin;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elearning.entities.Vocabulary;
//import com.elearning.entities.BaiThiThu;
//import com.elearning.entities.CauHoiBaiThiThu;
import com.elearning.entities.VocabularyContent;
import com.elearning.service.VocabularyService;
import com.elearning.service.VocabularyDetailService;

@RestController
@RequestMapping("/api/admin/vocab")
public class VocabApi {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    VocabularyService vocabularyService;
    @Autowired
    VocabularyDetailService vocabularydetailService;

    @GetMapping("/loadVocab")
    public List<String> showAllVocab() {

        List<Vocabulary> list = vocabularyService.findAll();

        List<String> response = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            String json = "vocabularyid:" + list.get(i).getVocabularyId() + "," + "image:"
                    + list.get(i).getImage() + "," + "vocabularyname:" + list.get(i).getVocabularyName();

            response.add(json);
        }

        return response;

    }

    @RequestMapping(value = "/delete/{idBaiVocab}")
    public String deleteById(@PathVariable("idBaiVocab") int id) {
        vocabularyService.delete(id);
        return "success";
    }

    @PostMapping(value = "/save", consumes = "multipart/form-data")
    @ResponseBody
    public List<String> addBaiThiThu(@RequestParam("file_excel") MultipartFile file_excel,
                                     @RequestParam("file_image") MultipartFile file_image, @RequestParam("name") String name,
                                     @RequestParam("file_image_question") MultipartFile[] file_image_question,
                                     @RequestParam("file_listening") MultipartFile[] file_listening) {

        List<String> response = new ArrayList<String>();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");

        Vocabulary vocabulary = new Vocabulary();
        vocabularyService.save(vocabulary);

        // System.out.println("id="+baithithu.getBaithithuid());
        try {
            // save file upload to local folder
            Path pathExcel = Paths.get(rootDirectory + "/static/file/excel/" + "vocab."
                    + vocabulary.getVocabularyId() + "." + file_excel.getOriginalFilename());
            file_excel.transferTo(new File(pathExcel.toString()));

            Path pathImage = Paths.get(rootDirectory + "/static/file/images/vocab/" + ""
                    + vocabulary.getVocabularyId() + "." + file_image.getOriginalFilename());
            file_image.transferTo(new File(pathImage.toString()));

            for (MultipartFile single_image : file_image_question) {
                Path pathImageQuestion = Paths.get(rootDirectory + "/static/file/images/vocab/" + ""
                        + vocabulary.getVocabularyId() + "." + single_image.getOriginalFilename());
                single_image.transferTo(new File(pathImageQuestion.toString()));
            }

            for (MultipartFile single_listening : file_listening) {
                Path pathListening = Paths.get(rootDirectory + "/static/file/audio/vocab/" + ""
                        + vocabulary.getVocabularyId() + "." + single_listening.getOriginalFilename());
                single_listening.transferTo(new File(pathListening.toString()));
            }

            vocabulary.setVocabularyName(name);
            vocabulary.setImage(vocabulary.getVocabularyId() + "." + file_image.getOriginalFilename());
            vocabularyService.save(vocabulary);

            // save data from file excel

            VocabApi btt = new VocabApi();
            List<VocabularyContent> listCauHoiFull = btt.getListFromExcel(pathExcel.toString(), vocabulary);

            for (int i = 0; i < listCauHoiFull.size(); i++) {
                vocabularydetailService.save(listCauHoiFull.get(i));
            }

        } catch (Exception e) {
            response.add(e.toString());
            System.out.println("ErrorReadFileExcel:" + e);

        }

        return response;
    }

    // get info Vocab ->edit Vocab
    @RequestMapping(value = "/infoVocab/{idBaiVocab}")
    public String infoVocabById(@PathVariable("idBaiVocab") int id) {
        Vocabulary vocabulary = vocabularyService.getVocabulary(id).get(0);

        return vocabulary.getVocabularyName();
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public List<String> updateBaiVocab(@RequestParam("vocabId") int id, @RequestParam("name") String name,
                                       @RequestParam("file_image") MultipartFile file_image,
                                       @RequestParam("file_image_question") MultipartFile[] file_image_question,
                                       @RequestParam("file_listening") MultipartFile[] file_listening,
                                       @RequestParam("file_excel") MultipartFile file_excel) {

        List<String> response = new ArrayList<String>();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");

        Vocabulary vocabulary = vocabularyService.getVocabulary(id).get(0);

        // System.out.println("id="+baithithu.getBaithithuid());
        try {
            // save file upload to local folder
            Path pathExcel = Paths.get(rootDirectory + "/static/file/excel/vocab" + "vocab."
                    + vocabulary.getVocabularyId() + "." + file_excel.getOriginalFilename());
            file_excel.transferTo(new File(pathExcel.toString()));

            Path pathImage = Paths.get(rootDirectory + "/static/file/images/vocab/" + ""
                    + vocabulary.getVocabularyId() + "." + file_image.getOriginalFilename());
            file_image.transferTo(new File(pathImage.toString()));

            for (MultipartFile single_image : file_image_question) {
                Path pathImageQuestion = Paths.get(rootDirectory + "/static/file/images/vocab/" + ""
                        + vocabulary.getVocabularyId() + "." + single_image.getOriginalFilename());
                single_image.transferTo(new File(pathImageQuestion.toString()));
            }

            for (MultipartFile single_listening : file_listening) {
                Path pathListening = Paths.get(rootDirectory + "/static/file/audio/vocab/" + ""
                        + vocabulary.getVocabularyId() + "." + single_listening.getOriginalFilename());
                single_listening.transferTo(new File(pathListening.toString()));
            }

            vocabulary.setVocabularyName(name);
            vocabulary.setImage(vocabulary.getVocabularyId() + "." + file_image.getOriginalFilename());
            vocabularyService.save(vocabulary);

            // save data from file excel

            VocabApi btt = new VocabApi();
            List<VocabularyContent> listCauHoiFull = btt.getListFromExcel(pathExcel.toString(), vocabulary);

            for (int i = 0; i < listCauHoiFull.size(); i++) {
                vocabularydetailService.save(listCauHoiFull.get(i));
            }

        } catch (Exception e) {
            response.add(e.toString());
            System.out.println("errorUpdate:" + e);
        }

        return response;
    }

    public List<VocabularyContent> getListFromExcel(String path_file_excel, Vocabulary vocabulary) {
        List<VocabularyContent> list = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(path_file_excel));
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                VocabularyContent vocabularycontent = new VocabularyContent();

                XSSFRow row = worksheet.getRow(i);

                if (row.getCell(0) != null)
                    vocabularycontent.setNumber((int) row.getCell(0).getNumericCellValue());

                if (row.getCell(1) != null)
                    vocabularycontent.setContent(row.getCell(1).getStringCellValue());

                if (row.getCell(2) != null)
                    vocabularycontent.setTranscribe(row.getCell(2).getStringCellValue());

                if (row.getCell(3) != null)
                    vocabularycontent.setImage(
                    		vocabulary.getVocabularyId() + "." + row.getCell(3).getStringCellValue().toString());

                if (row.getCell(4) != null)
                	vocabularycontent
                            .setAudiomp3(vocabulary.getVocabularyId() + "." + row.getCell(4).getStringCellValue());

                if (row.getCell(5) != null)
                	vocabularycontent.setMeaning(row.getCell(5).getStringCellValue());

                if (row.getCell(6) != null)
                	vocabularycontent.setSentence(row.getCell(6).getStringCellValue());

                vocabularycontent.setVocabulary(vocabulary);

                list.add(vocabularycontent);
            }

        } catch (Exception e) {
            System.out.println("errorhere:" + e);
        }

        return list;

    }

}
