package com.example.filework;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
//    private String SDpath;//SD卡目录
//    private String AppPath;//app存储目录
//    private static FileUtil fileUtil;
//    private  FileUtil(){
//        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//        {SDpath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
//            File file=createSDDir(SDpath,"VB");
//            AppPath=file.getAbsolutePath()+"/";
//        }
//    }
//    public static FileUtil getInstance()
//    {
//        if(fileUtil==null)
//        {
//            synchronized (FileUtil.class)
//            {
//                if(fileUtil==null)
//                {
//                    fileUtil=new FileUtil();
//                }
//            }
//        }
//        return fileUtil;
//    }
//    public File createSDDir(String path,String dirName)
//    {
//        File dir=new File(path+dirName);
//        if(dir.exists()&&dir.isDirectory())
//        {
//            return dir;
//        }
//        dir.mkdir();
//        Log.d("目录","创建成功");
//        return dir;
//    }
//    public File createSDFile(String path,String filename)
//    {
//        File file=new File(path+filename);
//        if(file.exists()&&file.isFile())
//        {
//            return file;
//        }
//        try{
//            file.createNewFile();
//            Log.d("文件","创建成功");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
//    public void writeToSD(String path, String filename, InputStream inputStream) throws IOException {
//        OutputStream outputStream=null;
//        try{
//            File dir =createSDDir(AppPath,path);
//            File file=createSDFile(dir.getAbsolutePath()+"/",filename);
//            outputStream=new FileOutputStream(file);
//            int length;
//            byte[] buffer =new byte[2*1024];
//            while((length=inputStream.read(buffer))!=-1)
//            {
//                outputStream.write(buffer,0,length);
//            }
//            outputStream.flush();
//            Log.d("写入","成功");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Log.d("写入","失败");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(outputStream!=null)
//            {
//                outputStream.close();
//            }
//        }
//    }
//    public String  getPathinSD(String filename)
//    {
//        File file=new File(AppPath+filename);
//        if(file.exists())
//        {
//            return file.getAbsolutePath();
//        }
//        return "";
//    }
//    private void deletefile(File file)
//    {
//        if(file.exists())
//        {
//            if(file.isFile())
//            {
//                file.delete();
//            }
//            else if(file.isDirectory())
//            {
//                File[] files=file.listFiles();
//                for(int i=0;i<files.length;i++)
//                {
//                    deletefile(files[i]);
//                }
//                file.delete();
//            }
//            Log.d("删除","成功");
//        }
//    }
private String SDPATH;
    private String PATH;
    public String getSDPATH()
    {
        return SDPATH;
    }
    public String getPATH(){return PATH;}
    public FileUtil()
    {
        SDPATH= Environment.getExternalStorageDirectory()+"/";//获取当前外部储存设备的根目录
        creadSDDir("voice");
    }
    public File creadSDfile(String filename)throws IOException{//在SD卡上创建文件
        System.out.println("文件路径："+PATH+filename);
        File file=new File(PATH+filename);
        file.createNewFile();
        System.out.println("文件创建成功："+PATH+filename);
        return file;
    }
    public File creadSDDir(String dirName){//在SD卡上创建目录
        File dir=new File(SDPATH+dirName);
        PATH=SDPATH+dirName+"/";
        System.out.println("挡墙路径:"+PATH);
        dir.mkdir();
        System.out.println("目录创建成功："+PATH);
        return  dir;
    }
    public boolean isFileExist(String fileName)//判断文件是否存在
    {
        File file=new File(SDPATH+fileName);
        return  file.exists();
    }
    public File writeInputStream2SD(String path, String fileName, InputStream inputStream)
    {
        File file=null;
        OutputStream outputStream=null;
        try{
            creadSDDir(path);//创建一个目录
            file =creadSDDir(path+fileName);//创建一个文件
            outputStream=new FileOutputStream(file);//为此文件创建一个输出流来写入数据
            byte buffer[]=new byte[1024*4];// 创建一个4字节缓存
            while((inputStream.read(buffer)!=-1))
            {
                outputStream.write(buffer);//先从输入流里读取4k字节数据到buffer里，在从buffer写到文件里
            }
            outputStream.flush();//操作完成清空输出流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  file;
    }
}
