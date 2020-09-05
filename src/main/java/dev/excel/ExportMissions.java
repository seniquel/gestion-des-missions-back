package dev.excel;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import dev.domain.Mission;

/**Export d'une liste de missions en fichier xsl
 * @author formation
 *
 */
public interface ExportMissions {

	private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
	
	public static void creerFichierExcel(List<Mission> list, int annee) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employees sheet");
 
        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);
 
        row = sheet.createRow(rownum);
 
        // Début
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Date de début");
        cell.setCellStyle(style);
        // Fin
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Date de fin");
        cell.setCellStyle(style);
        // Nature
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Nature");
        cell.setCellStyle(style);
        // Prime
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Prime");
        cell.setCellStyle(style);
        
     // Data
        for (Mission mission : list) {
            rownum++;
            row = sheet.createRow(rownum);
 
            // Début
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(mission.getDateDebut().toString());
            // Fin)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(mission.getDateFin().toString());
            // Nature
            cell = row.createCell(2, CellType.NUMERIC);
            cell.setCellValue(mission.getNature().getLibelle());
            // Prime)
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(mission.getPrime().toString()+"€");
        }
        
        FileOutputStream fop = null;
        File file;
        
        try {
        	file = new File("C:/Users/"+System.getProperty("user.name")+"/primes/"+LocalDate.now()+"-primes-"+annee+".xls");
            file.getParentFile().mkdirs();
     
            fop = new FileOutputStream(file);
            workbook.write(fop);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
	}

}
