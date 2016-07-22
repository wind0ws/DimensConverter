package com.threshold.dimens;

import java.io.File;
import java.io.FileFilter;

/**
 * ClassName:DimensionCalcManager 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2015年9月21日 下午4:39:48 
 * @author   黄守江
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class DimensionCalcManager {

	/**
	 * executeBatchTask: 批量执行Dimension文件转换，
	 * 只转换dp，不转换sp、px
	 * 
	 *
	 * @author 黄守江
	 * @param standardDimensionFilePath 1136x640的标准dimension.xml文件路径
	 * @param destinationResFolderPath 含有许多values的 res文件夹路径。
	 * @since JDK 1.6
	 */
	public static void executeBatchTask(String standardDimensionFilePath, String destinationResFolderPath){
		File file=new File(destinationResFolderPath);
//		File file=new File("d:\\res\\");
		FileFilter filter= pathname -> {
            if (pathname.getName().contains("values")) {
                return true;
            }
            System.out.println("Attention：res文件夹下含有非values开头的文件夹："+pathname);
            return false;
        };
		File[] listFiles = file.listFiles(filter);
		for (File file2 : listFiles) {
			String folderName=file2.getName();
			System.out.println("Folder Name= "+folderName+"      Path= "+file2.getAbsolutePath());
		    String[] split = folderName.split("-");
		    String dpiString=split[1];
		    String[] resolutions = split[2].split("x");
		    int height=Integer.valueOf(resolutions[0]);
		    int width=Integer.valueOf(resolutions[1]);
		    System.out.println("height= "+height+"      width= "+width);
		    new ChangeDimensionTask(standardDimensionFilePath, file2.getAbsolutePath(), height, width, dpiString).execute();
		}
		System.out.println("\n\n\n ============================All Done================================");
		System.out.println("All Done,Calculate Dimension Complete!!!");
	}
}

