package com.threshold.dimens;

import java.io.*;

/**
 * ClassName:ChangeDimension
 * Date:     2015年9月21日 下午2:12:17
 */
public class ChangeDimensionTask {

    private StringBuilder stringBuilder;
    private String sourceFileName;
    private String destFileFolder;
    private String targetDpiString;
    private double ratio;
    private String firstLineStatement = "";

    /**
     *
     * @param sourceFileName dimens.xml文件路径
     * @param resFolder res文件夹路径
     * @param standardDpiString 比如mdpi、hdpi、xhdpi，640dpi等
     * @param targetDpiString 同上
     */
    public ChangeDimensionTask(String sourceFileName, String standardDpiString,
                               String resFolder, String targetDpiString) {
        this.sourceFileName = sourceFileName;
        this.destFileFolder = resFolder;
        this.targetDpiString = targetDpiString;
        stringBuilder = new StringBuilder();
        calculatorRatio(getStandardRatio(standardDpiString));
    }

    private double getStandardRatio(String standardDpiString){
        double dpiRatio ;
        switch (standardDpiString) {
            case DpiName.LDPI:
                dpiRatio = DpiName.LDPI_RATIO;
                break;
            case DpiName.MDPI:
                dpiRatio = DpiName.MDPI_RATIO;
                break;
            case DpiName.HDPI:
                dpiRatio = DpiName.HDPI_RATIO;
                break;
            case DpiName.XHDPI:
                dpiRatio = DpiName.XHDPI_RATIO;
                break;
            case DpiName.XXHDPI:
                dpiRatio = DpiName.XXHDPI_RATIO;
                break;
            case DpiName.XXXHDPI:
                dpiRatio = DpiName.XXXHDPI_RATIO;
                break;
            default:
                int dpiNumber = Integer.parseInt( targetDpiString.substring(0, targetDpiString.indexOf("dpi")));
                dpiRatio = (double)dpiNumber/(double) DpiName.MDPI_DPI;
                break;
        }
        return dpiRatio;
    }


    private void calculatorRatio(double standardRatio) {
//        float coefficient;
        switch (targetDpiString) {
            case DpiName.LDPI:
                ratio = DpiName.LDPI_RATIO / standardRatio ;
//                coefficient = 0.75f;
                firstLineStatement = "ldpi 120dpi";
                break;
            case DpiName.MDPI:
                ratio = DpiName.MDPI_RATIO /standardRatio;
//                coefficient = 1.0f;
                firstLineStatement = "mdpi 160dpi";
                break;
            case DpiName.HDPI:
                ratio = DpiName.HDPI_RATIO / standardRatio;
//                coefficient = 1.5f;
                firstLineStatement = "hdpi 240dpi";
                break;
            case DpiName.XHDPI:
                ratio = DpiName.XHDPI_RATIO / standardRatio;
//                coefficient = 2.0f;
                firstLineStatement = "xhdpi 320dpi";
                break;
            case DpiName.XXHDPI:
                ratio = DpiName.XXHDPI_RATIO / standardRatio;
//                coefficient = 3.0f;
                firstLineStatement = "xxhdpi 480dpi";
                break;
            case DpiName.XXXHDPI:
                ratio = DpiName.XXXHDPI_RATIO / standardRatio;
//                coefficient = 4.0f;
                firstLineStatement = "xxxhdpi 640dpi";
                break;
            default:
                int dpiNumber = Integer.parseInt( targetDpiString.substring(0, targetDpiString.indexOf("dpi")));
                ratio = (double)dpiNumber/(double) DpiName.MDPI_DPI;
//                coefficient = Integer.valueOf(dpiNumber) / 160;
                firstLineStatement = targetDpiString;
                break;
        }
        System.out.println("Ratio：" + ratio);
    }


    public void execute() {
        readFileByLines();
//		System.out.println("____________________________________________");
//		System.out.println(stringBuilder.toString());
        saveFile();
    }

    private void saveFile() {
        String targetFolder = destFileFolder + File.separator + "values-" + targetDpiString;
        File targetFolderFile = new File(targetFolder);
        if (!targetFolderFile.exists()) {
            if (!targetFolderFile.mkdirs()) {
                System.out.println("Create "+targetFolder+" folder FAILED !!!");
            }
        }
        File file = new File(targetFolder + File.separator + "dimens.xml");
        try (FileOutputStream fop = new FileOutputStream(file)) {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println(file.getAbsolutePath()+"   created successfully.");
                } else {
                    System.out.println("*** "+file.getAbsolutePath()+"  created FAILED !!!!");
                }
            } else {
                System.out.println(file.getAbsolutePath()+" already has a dimens.xml file.I will rewrite it.");
//                if (file.delete() && file.createNewFile()) {
//                    System.out.println(file.getAbsolutePath()+" already has a dimens.xml file, I delete it and recreate a new file successfully.");
//                } else {
//                    System.out.println("*** "+file.getAbsolutePath()+" already has a dimens.xml file,but FAILED on delete it or create new file !!!! ***");
//                }
            }
            byte[] contentInBytes = stringBuilder.toString().getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("Done:      " + targetFolder + File.separator + "dimens.xml");
            System.out.println("----------------------------------------------------------------" + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileByLines() {
        if (!sourceFileName.contains("dimens.xml")) { //this means sourceFileName is a folder
            sourceFileName = sourceFileName + File.separator + "dimens.xml";
        }
        File file = new File(sourceFileName);
        BufferedReader reader = null;
        try {
//	            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String oneLine;
//            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((oneLine = reader.readLine()) != null) {
                // 显示行号
//	                System.out.println("line " + line + ": " + oneLine);
                stringBuilder.append(updateDimension(oneLine));
                stringBuilder.append("\n");
//                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    System.out.println("error on read: "+e1);
                }
            }
        }
    }

    private String updateDimension(String oneLine) {
        if (oneLine.contains("Default screen margins")) {//说明是默认Values文件夹第一行备注
            return "    <!-- " + firstLineStatement +" -->";
        }
        int dpDimension = oneLine.indexOf("dp</dimen>");
        if (dpDimension > -1) {
            int begin = oneLine.indexOf("\">");
            int end = oneLine.indexOf("</");
            if (begin > 0 && end > 0 && end > begin) {
                String dimensionString = oneLine.substring(begin + 2, end);
                double dp = Double.valueOf(dimensionString.substring(0, dimensionString.indexOf("dp")));
                double newDimen = dp * ratio;
//				String newDimension=newDimen+"dp";
//				oneLine.replaceAll(dimensionString, newDimension);
                String newTempString = oneLine.replace(dimensionString, String.format("%.2f", newDimen) + "dp");
//				System.out.println("new String="+newTempString);
                return newTempString;
            }
        }
        return oneLine;
    }


    public static final class DpiName {
        static final String LDPI = "ldpi"; //0  ~ 120 dpi
        static final double LDPI_RATIO = 0.75;
        static final int LDPI_DPI = 120;

        static final String MDPI = "mdpi"; //120  ~ 160 dpi
        static final double MDPI_RATIO = 1;
        static final int MDPI_DPI = 160;

        static final String HDPI = "hdpi"; //160  ~ 240 dpi
        static final double HDPI_RATIO = 1.5;
        static final int HDPI_DPI = 240;

        static final String XHDPI = "xhdpi"; //240  ~ 320 dpi
        static final double XHDPI_RATIO = 2;
        static final int XHDPI_DPI = 320;

        static final String XXHDPI = "xxhdpi"; //320  ~ 480 dpi
        static final double XXHDPI_RATIO = 3;
        static final int XXHDPI_DPI = 480;

        static final String XXXHDPI = "xxxhdpi";//480  ~ 640 dpi
        static final double XXXHDPI_RATIO = 4;
        static final int XXXHDPI_DPI = 640;

    }


}

