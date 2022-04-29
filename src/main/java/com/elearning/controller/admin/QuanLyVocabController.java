package com.elearning.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elearning.entities.Vocabulary;
import com.elearning.entities.VocabularyContent;
import com.elearning.service.VocabularyService;
import com.elearning.service.VocabularyDetailService;

@Controller
@RequestMapping("/admin")
public class QuanLyVocabController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    VocabularyService vocabularyService;
    @Autowired
    VocabularyDetailService vocabularydetailService;

    @RequestMapping("/vocab/saveVocab")
    public String uploadingPost(
            @RequestParam("file_imageQuestion") MultipartFile[] file_imageQuestions
            , @RequestParam("file_listen") MultipartFile[] file_listens
            , @RequestParam("file_Excel") MultipartFile file_excel
            , @RequestParam("file_imageVocab") MultipartFile file_imageVocab
            , @ModelAttribute("vocabulary") Vocabulary vocabulary, BindingResult result
            , RedirectAttributes redirectAttrs
    ) throws IOException {


        // save file to folder local

        String rootDirectory = request.getSession().getServletContext().getRealPath("/");

        Path pathExcel = Paths.get(rootDirectory + "/static/file/excel/vocab" + file_excel.getOriginalFilename() + ".xlsx");
        file_excel.transferTo(new File(pathExcel.toString()));

        Path path_file_imageVocab = Paths.get(rootDirectory + "/static/file/images/vocab/" + file_imageVocab.getOriginalFilename());
        file_imageVocab.transferTo(new File(path_file_imageVocab.toString()));


        for (MultipartFile file_imageQuestion : file_imageQuestions) {
            Path pathImage = Paths.get(rootDirectory + "/static/file/images/vocab/" + file_imageQuestion.getOriginalFilename());
            file_imageQuestion.transferTo(new File(pathImage.toString()));
        }

        for (MultipartFile file_listen : file_listens) {

            Path pathAudio = Paths.get(rootDirectory + "/static/file/audio/vocab/" + file_listen.getOriginalFilename());
            file_listen.transferTo(new File(pathAudio.toString()));

        }

        //save to db vocabulary
        vocabulary.setImage(file_imageVocab.getOriginalFilename());
        vocabularyService.save(vocabulary);

        // save content from file excel to noi_dung_bai_tu_vung


        try {

            List<VocabularyContent> vocabularycontentList = new ArrayList<VocabularyContent>();
            XSSFWorkbook workbook = new XSSFWorkbook(file_excel.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
                VocabularyContent vocabularycontetn = new VocabularyContent();

                XSSFRow row = worksheet.getRow(i);

                vocabularycontetn.setNumber((int) row.getCell(0).getNumericCellValue());
                vocabularycontetn.setContent(row.getCell(1).getStringCellValue());
                vocabularycontetn.setTranscribe(row.getCell(2).getStringCellValue());
                vocabularycontetn.setImage(row.getCell(3).getStringCellValue());
                vocabularycontetn.setAudiomp3(row.getCell(4).getStringCellValue());
                vocabularycontetn.setMeaning(row.getCell(5).getStringCellValue());
                vocabularycontetn.setSentence(row.getCell(6).getStringCellValue());
                vocabularycontetn.setVocabulary(vocabulary);

                vocabularycontentList.add(vocabularycontetn);
                vocabularydetailService.save(vocabularycontetn);
            }


        } catch (Exception e) {
            System.out.println("Error: " + e);
            String error = "Có lỗi xảy ra, update or add again, id =" + vocabulary.getVocabularyId();
            redirectAttrs.addFlashAttribute("error", error);
            redirectAttrs.addFlashAttribute("errorInfo", e);

            return "redirect:/admin/vocab";
        }
        {

        }

        return "redirect:/admin/vocab";
//		 return "admin/quanLyVocab";
    }


//	 @RequestMapping(value="/editVocab/{id}")    
//	    public String edit(@PathVariable int id, Model model){    
//	        
//	        return "empeditform";    
//	    }    

    @RequestMapping(value = "/deleteVocab/{id}")
    public String edit(@PathVariable Integer id, Model model) {


        vocabularyService.delete(id);
        return "redirect:/admin/vocab";
//	        return "admin/quanLyVocab";    
    }


}
