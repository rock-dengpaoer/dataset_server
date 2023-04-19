package com.xdt.dataset_server.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.*;


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
        Thumbnails.of(inputStream)
                .size(500, 500)
                .outputFormat("jpg")
                .watermark(Positions.CENTER, ImageIO.read(new File("watermark.png")), 0.5f)
                .toFile(oldName + "_new.jpg");
        return oldName + "_new.jpg";
    }


}
