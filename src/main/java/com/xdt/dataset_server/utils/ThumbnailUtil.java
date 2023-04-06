package com.xdt.dataset_server.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author XDT
 * @ClassName ThumbnailUtil
 * @Description: 图像压缩工具
 * @Date 2023/4/5 12:37
 **/
@Component
public class ThumbnailUtil {


    public String getThumbnail(InputStream inputStream, String imgName) throws IOException {
        String[] split = imgName.split("\\.");
        String oldName = split[0];
        Thumbnails.of(inputStream).size(500, 500).outputFormat("jpg").toFile(oldName + "_new.jpg");
        return oldName + "_new.jpg";
    }
}
