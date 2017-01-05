package com.threshold.dimens;

import java.io.File;
import java.util.stream.Stream;

/**
 * ClassName:DimensionCalcManager
 * Date:     2015年9月21日 下午4:39:48 
 * @author   Threshold
 */
public class DimensionCalcManager {


	public static void executeBatchTask(String standardDimensionFilePath,String standardDimensDpi,
										String targetResFolderPath,String... dpiNames){
//		File file=new File(destinationResFolderPath);
//		FileFilter filter= pathname -> {
//            if (pathname.getName().startsWith("values-")) {
//                return true;
//            }
//            System.out.println("Attention：res文件夹下含有非values开头的文件夹："+pathname);
//            return false;
//        };
//		File[] listFiles = file.listFiles(filter);
//		for (File file2 : listFiles) {
//			String folderName=file2.getName();
//			System.out.println("Folder Name= "+folderName+"      Path= "+file2.getAbsolutePath());
//		    String[] split = folderName.split("-");
//		    String dpiString=split[1];
//		    String[] resolutions = split[2].split("x");
//		    int height=Integer.valueOf(resolutions[0]);
//		    int width=Integer.valueOf(resolutions[1]);
//		    System.out.println("height= "+height+"      width= "+width);
//		    new ChangeDimensionTask(standardDimensionFilePath, file2.getAbsolutePath(), height, width, dpiString).execute();
//		}
        if (!new File(standardDimensionFilePath).exists()) {
            System.out.println(standardDimensionFilePath+", file not found!");
            return;
        }
        if (!new File(targetResFolderPath).exists()) {
            System.out.println(targetResFolderPath+" ,res folder not found!");
            return;
        }
        Stream.of(dpiNames)
                .parallel()
                .filter(dpiName -> dpiName.contains("dpi"))
                .forEach(dpiName -> {
                    System.out.println("Now calculate："+dpiName);
                    new ChangeDimensionTask(standardDimensionFilePath,standardDimensDpi
                            ,targetResFolderPath,dpiName).execute();
                });
        System.out.println("\n\n\n ============================All Done================================");
		System.out.println("All Done,Calculate Dimension Complete!!! See result in res folder.");
	}
}

