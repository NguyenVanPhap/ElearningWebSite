
package com.elearning.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.elearning.entities.ListeningQuestion;

@Component
public class ExcelUtil {

	public List<ListeningQuestion> getListCauHoiFromFileExcel(InputStream excelFile) {

		try {
			List<ListeningQuestion> listCauHoi = new ArrayList<>();
			Workbook workbook = new XSSFWorkbook(excelFile);

			Sheet datatypeSheet = workbook.getSheetAt(0);

			DataFormatter fmt = new DataFormatter();

			Iterator<Row> iterator = datatypeSheet.iterator();
			Row firstRow = iterator.next();
			Cell firstCell = firstRow.getCell(0);
			System.out.println(firstCell.getStringCellValue());
			XSSFDrawing dp = (XSSFDrawing) datatypeSheet.createDrawingPatriarch();
			List<XSSFShape> pics = dp.getShapes();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				ListeningQuestion cauHoi = new ListeningQuestion();
				cauHoi.setNumber(fmt.formatCellValue(currentRow.getCell(0)));
				cauHoi.setQuestion((fmt.formatCellValue(currentRow.getCell(1))));
				cauHoi.setAnswer_1(fmt.formatCellValue(currentRow.getCell(2)));
				cauHoi.setAnswer_2(fmt.formatCellValue(currentRow.getCell(3)));
				cauHoi.setAnswer_3(fmt.formatCellValue(currentRow.getCell(4)));
				cauHoi.setAnswer_4(fmt.formatCellValue(currentRow.getCell(5)));
				cauHoi.setCorrect_answer(fmt.formatCellValue(currentRow.getCell(6)));
				cauHoi.setAnsExplain(fmt.formatCellValue(currentRow.getCell(7)));

				for (Iterator<? extends XSSFShape> it = pics.iterator(); it.hasNext();) {
					XSSFPicture inpPic = (XSSFPicture) it.next();

					XSSFClientAnchor clientAnchor = inpPic.getClientAnchor();
					PictureData pict = inpPic.getPictureData();
					byte[] data = pict.getData();
					if (clientAnchor.getCol1() == 8 && clientAnchor.getRow1() == currentRow.getRowNum()) {
						byte[] pictureData = data;
						cauHoi.setPhotoData(pictureData);
					}
				}
				System.out.println(cauHoi.toString());
				cauHoi.setScript(fmt.formatCellValue(currentRow.getCell(9)));
				listCauHoi.add(cauHoi);
			}
			workbook.close();
			return listCauHoi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
