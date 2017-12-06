package com.taotao.manage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.vo.PicUploadResult;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@RequestMapping("/pic")
@Controller
public class PicUploadController {

    private static final String[] IMAGE_TYPES = {".jpg",".png",".bmp",".jpeg",".gif"};

    @Value("${TAOTAO_IMAGE_PATH}")
    private String TAOTAO_IMAGE_PATH;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    //produces 表示响应的文本类型
    @RequestMapping(value = "/upload", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile")MultipartFile multipartFile) throws JsonProcessingException {

        //返回结果
        PicUploadResult picUploadResult = new PicUploadResult();
        picUploadResult.setError(1);//非0表示失败

        //判断文件是否合法
        boolean isLegal = false;

        //校验图片类型
        for (String type : IMAGE_TYPES) {
            if(multipartFile.getOriginalFilename().lastIndexOf(type) > 0){
                isLegal = true;
                break;
            }
        }

        if(isLegal){
            //校验图片的内容是否图片
            try {
                //读取图片流；如果是图片则不会出现异常
                BufferedImage image = ImageIO.read(multipartFile.getInputStream());

                //上传图片
                // 获取tracker server的地址的配置文件路径
                String trackerConfig = this.getClass().getClassLoader().getResource("tracker.conf").toString();
                if(trackerConfig.split(":").length > 2){
                    //windows系统路径；如：file:/D:/itcast/../tracker.conf
                    trackerConfig = trackerConfig.replaceAll("file:/", "");
                } else {
                    //linux系统路径；如：file:/usr/local/../tracker.conf
                    trackerConfig = trackerConfig.replaceAll("file:", "");
                }

                // 设置Tracker server的地址
                ClientGlobal.init(trackerConfig);

                // 创建trackerClient
                TrackerClient trackerClient = new TrackerClient();

                // 创建trackerServer
                TrackerServer trackerServer = trackerClient.getConnection();

                // 创建storageServer
                StorageServer storageServer = null;

                // 创建 StorageClient
                StorageClient storageClient = new StorageClient(trackerServer, storageServer);

                // 上传文件
                //上传文件的后缀名称
                String file_ext_name = StringUtils.substringAfter(multipartFile.getOriginalFilename(), ".");
                // 第一个参数为本地的图片路径，第二个参数为图片的后缀，第三个为文件的信息可以设置为null
                String[] fileInfos = storageClient.upload_file(multipartFile.getBytes(), file_ext_name, null);
                // 第一个值是组名，第二个是相对路径；两个字符串拼接后是完整图片路径
                String url = TAOTAO_IMAGE_PATH;
                for (String info : fileInfos) {
                    url += "/" + info;
                }

                //设置返回结果
                picUploadResult.setError(0);
                picUploadResult.setUrl(url);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return MAPPER.writeValueAsString(picUploadResult);
    }

}

