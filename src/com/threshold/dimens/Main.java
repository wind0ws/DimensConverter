
package com.threshold.dimens;

import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * ClassName:Main
 * Date:     2015年9月21日 下午4:56:36
 *
 * @author 黄守江
 * @see
 * @since JDK 1.6
 */
public class Main {

    public static void main(String[] args) {
//        String currentPath = System.getProperty("user.dir");
//        System.out.println("Current folder path ："+currentPath);

        String standardDimensPath = null,resFolderPath = null;
        String standardDimensDpi = null;
        String[] dpiNames = new String[]{"ldpi", "mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi"};
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("-i")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("please input standard dimens.xml file path（eg: d:/dimens.xml ）");
                while (!isFileExists(standardDimensPath = scanner.next())) {
                    System.out.println("Error，dimens.xml not found，try again");
                }
                System.out.println("please input standard dimens.xml file dpi（eg: xhdpi ）");
                while (!(standardDimensDpi = scanner.next()).contains("dpi")) {
                    System.out.println("Error，dimens.xml not found，try again");
                }
                System.out.println("please input res folder path（eg: d:/res ）");
                while (!isFileExists(resFolderPath = scanner.next())) {
                    System.out.println("Error，res folder not found，try again");
                }
                scanner.close();
            } else if (args[0].equalsIgnoreCase("-h")) {
                System.out.println("Help： \n" +
                        "-i input dimens.xml and res folder location\n" +
                        "-h show this help\n" +
                        "-dimens [dimens.xml file path] -dimensDpi [dimens.xml file dpi number] -res [res folder path] -dpiNames [mdpi,hdpi,xhdpi] ('[',']' is not included!)");
            } else {
                System.out.println("unknown command：" + args[0]);
            }
        } else if (args.length >= 6) {
            for (int i = 0; i < args.length; i+=2) {
                String command = args[i];
                String commandArg = args[i + 1];
                if (command.equalsIgnoreCase("-dimens")) {
                    if (isFileExists(commandArg)) {
                        standardDimensPath = commandArg;
                    }
                } else if (command.equalsIgnoreCase("-dimensDpi")) {
                    standardDimensDpi = commandArg;
                } else if (command.equalsIgnoreCase("-res")) {
                    resFolderPath = commandArg;
                } else if (command.equalsIgnoreCase("-dpiNames")) {
                    dpiNames = commandArg.split(",");
                } else {
                    System.out.println("unknown command: " + command + " .using \"-h\"for help");
                }
            }
        }
        if (standardDimensPath==null|| standardDimensPath.isEmpty() ||resFolderPath==null|| resFolderPath.isEmpty()) {
            System.out.println("No enough information to process converter! Exit");
            return;
        }
        System.out.println("dimens.xml  : " + standardDimensPath);
        System.out.println("standard Dpi: " + standardDimensDpi);
        System.out.println("res folder  : " + resFolderPath);
        System.out.println("===============DpiNames===============");
        Stream.of(dpiNames)
                .forEach(System.out::println);
        System.out.println("=================End==================");
        DimensionCalcManager.executeBatchTask(standardDimensPath,standardDimensDpi,resFolderPath,dpiNames);
    }

    private static boolean isFileExists(String path) {
        if (!isStringEmpty(path)) {
            File file = new File(path);
            return file.exists();
        }
        return false;
    }

    private static boolean isStringEmpty(String str) {
        if (str != null && str.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        return str == null || str.length() == 0;
    }

}

