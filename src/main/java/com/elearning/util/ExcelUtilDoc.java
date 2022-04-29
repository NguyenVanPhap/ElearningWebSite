package com.elearning.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.elearning.entities.ReadingQuestion;



@Component
public class ExcelUtilDoc {
	
	public List<ReadingQuestion> getListQuestionFromFileExcel(InputStream excelFile) {

		try {
			List<ReadingQuestion> listQuestion = new ArrayList<>();
			Workbook workbook = new XSSFWorkbook(excelFile);

			Sheet datatypeSheet = workbook.getSheetAt(0);

			DataFormatter fmt = new DataFormatter();

			Iterator<Row> iterator = datatypeSheet.iterator();
			Row firstRow = iterator.next();
			Cell firstCell = firstRow.getCell(0);
			System.out.println(firstCell.getStringCellValue());


			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				ReadingQuestion Question = new ReadingQuestion();
				Question.setNumber(fmt.formatCellValue(currentRow.getCell(0)));
				Question.setQuestion((fmt.formatCellValue(currentRow.getCell(1))));
				Question.setAnswer_1(fmt.formatCellValue(currentRow.getCell(2)));
				Question.setAnswer_2(fmt.formatCellValue(currentRow.getCell(3)));
				Question.setAnswer_3(fmt.formatCellValue(currentRow.getCell(4)));
				Question.setAnswer_4(fmt.formatCellValue(currentRow.getCell(5)));
				Question.setCorrect_answer(fmt.formatCellValue(currentRow.getCell(6)));
				Question.setAnsExplain(fmt.formatCellValue(currentRow.getCell(7)));
				Question.setParagraph(fmt.formatCellValue(currentRow.getCell(8)));

				System.out.println(Question.toString());
				listQuestion.add(Question);
			}
			workbook.close();
			return listQuestion;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
