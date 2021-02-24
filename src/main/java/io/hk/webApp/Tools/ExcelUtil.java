package io.hk.webApp.Tools;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import io.framecore.Tool.PropertiesHelp;
import io.hk.webApp.vo.UserDTO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {

    private static final String PATH = "path";

    /**
     * 创建Excel
     *
     * @param list 数据
     */
    public static String createExcel(List<UserDTO> list) {
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("用户数据表");
        // 添加表头行
        HSSFRow hssfRow = sheet.createRow(0);
        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 添加表头内容
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("用户账号");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("认证状态");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("类型");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(3);
        headCell.setCellValue("方案数量");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(4);
        headCell.setCellValue("创建日期");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(5);
        headCell.setCellValue("最后登录");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(6);
        headCell.setCellValue("启用状态");
        headCell.setCellStyle(cellStyle);

        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
            hssfRow = sheet.createRow((int) i + 1);
            UserDTO userDTO = list.get(i);

            // 创建单元格，并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(userDTO.getPhone());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(userDTO.getAuth());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(2);
            cell.setCellValue(userDTO.getType());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(3);
            cell.setCellValue(userDTO.getSchemeCount());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(4);
            cell.setCellValue(userDTO.getCreateTime());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(5);
            cell.setCellValue(userDTO.getLastLoginTime());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(6);
            cell.setCellValue(userDTO.getStatus());
            cell.setCellStyle(cellStyle);
        }
        String name = IdUtil.simpleUUID() + ".xls";
        // 保存Excel文件
        try {
            OutputStream outputStream = new FileOutputStream(PropertiesHelp.getApplicationConf(PATH) + name);
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

//    /**
//     * 读取Excel
//     *
//     * @return 数据集合
//     */
//    private static List<Student> readExcel() {
//        List<Student> list = new ArrayList<Student>();
//        HSSFWorkbook workbook = null;
//
//        try {
//            // 读取Excel文件
//            InputStream inputStream = new FileInputStream("D:/students.xls");
//            workbook = new HSSFWorkbook(inputStream);
//            inputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 循环工作表
//        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
//            HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
//            if (hssfSheet == null) {
//                continue;
//            }
//            // 循环行
//            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//                if (hssfRow == null) {
//                    continue;
//                }
//
//                // 将单元格中的内容存入集合
//                Student student = new Student();
//
//                HSSFCell cell = hssfRow.getCell(0);
//                if (cell == null) {
//                    continue;
//                }
//                student.setName(cell.getStringCellValue());
//
//                cell = hssfRow.getCell(1);
//                if (cell == null) {
//                    continue;
//                }
//                student.setAge((int) cell.getNumericCellValue());
//
//                cell = hssfRow.getCell(2);
//                if (cell == null) {
//                    continue;
//                }
//                student.setGrade(cell.getStringCellValue());
//
//                list.add(student);
//            }
//        }
//        return list;
//    }
}
