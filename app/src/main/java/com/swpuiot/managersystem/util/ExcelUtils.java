package com.swpuiot.managersystem.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.swpuiot.managersystem.entity.Attendance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


/**
 * Created by DELL on 2018/5/10.
 */
public class ExcelUtils {
    public static WritableFont arial14font = null;

    public static WritableCellFormat arial14format = null;
    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;
    public static WritableFont arial12font = null;
    public static WritableCellFormat arial12format = null;

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";

    public static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14,
                    WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 10,
                    WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(jxl.format.Colour.LIGHT_BLUE);
            arial12font = new WritableFont(WritableFont.ARIAL, 12);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
        } catch (WriteException e) {

            e.printStackTrace();
        }
    }

    public static void initExcel(String fileName, int i) {
        format();
        WritableWorkbook workbook = null;
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsoluteFile(), "test");
            File file = new File(dir.getAbsoluteFile(), fileName);
            workbook = Workbook.createWorkbook(file);
            for (int j = 1; j < i+1; j++) {
                WritableSheet sheet = workbook.createSheet("第" + j + "次签到", j);
            }
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(Attendance[] objList,
                                               String fileName, Context c,int i) {
        if (objList != null && objList.length > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsoluteFile(), "test");
                File file = new File(dir.getAbsoluteFile(), fileName);
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(file);
                Workbook workbook = Workbook.getWorkbook(in);

                writebook = Workbook.createWorkbook(file,
                        workbook);
                WritableSheet sheet = writebook.getSheet(i);
                sheet.addCell(new Label(0,0,"签到时间"));
                sheet.addCell(new Label(1,0,"学生学号"));
                sheet.addCell(new Label(2,0,"签到状态"));
                for (int j = 1;j<objList.length+1;j++){
                    sheet.addCell(new Label(0,j,new SimpleDateFormat("yyyy年MM月dd日").format(new Date(objList[j-1].getId().getDate())).toString() ));
                    sheet.addCell(new Label(1,j,objList[j-1].getId().getId()+""));
                    sheet.addCell(new Label(2,j,objList[j-1].getAttend()));
                }
//                sheet.addCell(new Label(0,0,objList[0].getId().getId()+""));
//                for (int j = 0; j < objList.length; j++) {
//                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
//                    for (int i = 0; i < list.size(); i++) {
//                        sheet.addCell(new Label(i, j + 1, list.get(i)));
//                    }
//                }
                writebook.write();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

//    public static  List<BillObject> read2DB(File f, Context con) {
//        ArrayList<BillObject> billList = new ArrayList<BillObject>();
//        try {
//            Workbook course = null;
//            course = Workbook.getWorkbook(f);
//            Sheet sheet = course.getSheet(0);
//
//            Cell cell = null;
//            for (int i = 1; i < sheet.getRows(); i++) {
//                BillObject tc = new BillObject();
//                cell = sheet.getCell(1, i);
//                tc.setFood(cell.getContents());
//                cell = sheet.getCell(2, i);
//                tc.setClothes(cell.getContents());
//                cell = sheet.getCell(3, i);
//                tc.setHouse(cell.getContents());
//                cell = sheet.getCell(4, i);
//                tc.setVehicle(cell.getContents());
//                Log.d("gaolei", "Row"+i+"---------"+tc.getFood() + tc.getClothes()
//                        + tc.getHouse() + tc.getVehicle());
//                billList.add(tc);
//
//            }
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return billList;
//    }

    public static Object getValueByRef(Class cls, String fieldName) {
        Object value = null;
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName
                .substring(0, 1).toUpperCase());
        String getMethodName = "get" + fieldName;
        try {
            Method method = cls.getMethod(getMethodName);
            value = method.invoke(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
