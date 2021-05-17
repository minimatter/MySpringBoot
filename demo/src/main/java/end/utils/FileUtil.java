package end.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 文件工具类
 *
 */
public class FileUtil {
    /**
     * 单文件上传
     * @param file
     *          需要上传的文件
     * @param folderPath
     *          存在的服务器目标文件夹
     * @return
     *          上传文件名，是存放在服务器当中的已经被混淆过后的文件名
     */
    public static String uploadFileSingle(MultipartFile file,String folderPath){


        //新上传文件名
        String newFile="";
        try {

            File filePath=new File(folderPath);
            if(!filePath.exists()){
                filePath.mkdirs();
            }

            //获取文件后缀名
            String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            //UUID下的新文件名
            newFile=UUID.randomUUID() + suffixName;

            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(folderPath+newFile)));
            stream.write(bytes);
            stream.close();



        } catch (Exception e) {
            e.printStackTrace();
        }

        return newFile;
    }


}
