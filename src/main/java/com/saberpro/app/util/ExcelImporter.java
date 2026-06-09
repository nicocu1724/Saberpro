package com.saberpro.app.util;

import com.saberpro.app.model.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelImporter {

    public static List<Usuario> importarEstudiantes(MultipartFile file) throws Exception {
        List<Usuario> lista = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getCell(1) == null) continue;

                Usuario est = new Usuario();
                est.setDocumento(getStringCellValue(row.getCell(1)));
                est.setNombreCompleto(getStringCellValue(row.getCell(2)) + " " + getStringCellValue(row.getCell(4)));
                est.setEmail(getStringCellValue(row.getCell(6)));
                est.setPrograma("Ingeniería de Sistemas");
                est.setSemestre("10");
                est.setRol("ESTUDIANTE");
                est.setPuntajeGlobal(getNumericCellValue(row.getCell(9)));

                if (!est.getEmail().isEmpty()) {
                    lista.add(est);
                }
            }
        }
        return lista;
    }

    private static String getStringCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((long) cell.getNumericCellValue());
        }
        return "";
    }

    private static Double getNumericCellValue(Cell cell) {
        if (cell == null) return 0.0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        return 0.0;
    }
}