package cn.alberto.fileupload.service;

import cn.alberto.fileupload.dao.PicDao;
import cn.alberto.fileupload.model.PicInfo;
import cn.alberto.fileupload.utils.FileUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AlbertoWang
 * @email AlbertoWang@FoxMail.com
 * @date 2021/5/31 00:23
 * @description 上传文件实现
 **/


@Service
public class UploadService {
    @Value("${url-cfg.pic-url}")
    private String basePicUrl;
    @Value("${url-cfg.video-url}")
    private String baseVideoUrl;

    @Resource
    private PicDao picDao;

    // 上传文件
    public boolean uploadMultipartFile(MultipartFile multipartFile, String storePath, String fileName) throws IOException {
        if (multipartFile.isEmpty()) {
            return false;
        }
        byte[] fileBytes = multipartFile.getBytes();
        FileUtils.uploadFile(fileBytes, storePath, fileName);
        return true;
    }

    // 根据视频生成缩略图
    public boolean genVideoPic(String videoPath, String videoPicPath) throws FrameGrabber.Exception {
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(videoPath);
        ff.start();
        int ffLength = ff.getLengthInFrames();
        Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabImage();
            //截取第6帧
            if ((i > 5) && (f.image != null)) {
                doExecuteFrame(f, videoPicPath);
                break;
            }
            i++;
        }
        ff.stop();
        return true;
    }

    private void doExecuteFrame(Frame f, String targetFilePath) {
        String suffix = "png";
        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(targetFilePath);
        try {
            ImageIO.write(bi, suffix, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 全部图片
    public List<PicInfo> getAllPics(String picDir) {
        List<PicInfo> ret = new ArrayList<>();
        List<String> picNames = picDao.getAllPicName();
        for (String picName : picNames) {
            PicInfo picInfo = new PicInfo();
            picInfo.setPic_name(picName);
            picInfo.setPicUrls(new HashMap<>());
            List<Map<String, Object>> picCommits = picDao.getCommitByPicName(picName);
            for (Map picCommit : picCommits) {
                String picUrl = (String) picCommit.get("pic_url");
                String picKey = (String) picCommit.get("pic_path");
                picKey = picKey.replaceFirst(picDir, "");
                picInfo.getPicUrls().put(picKey, picUrl);
            }
            ret.add(picInfo);
        }
        return ret;
    }

    // 全部视频缩略图
    public HashMap<String, String> getAllVideoPics(String videoDir) {
        File dir = new File(videoDir);
        String[] pics = dir.list();
        HashMap<String, String> ret = new HashMap<>();
        for (String s : pics) {
            if (s.contains(".png"))
                ret.put(s, baseVideoUrl + s);
        }
        return ret;
    }

    // 单张图片
    public String getPicByName(String picName, String picsDir) {
        if (FileUtils.inDir(picName, picsDir)) {
            return basePicUrl + picName;
        }
        return "";
    }

    // 单个视频
    public String getVideoByName(String videoName, String videoDir) {
        videoName = videoName.substring(0, videoName.lastIndexOf("."));
        if (FileUtils.inDir(videoName, videoDir)) {
            return baseVideoUrl + videoName;
        }
        return "";
    }

    // 删除图片
    public boolean deletePic(String picName, String picsDir) {
        if (!FileUtils.inDir(picName, picsDir))
            return false;
        return FileUtils.deleteFile(picsDir + picName);
    }

    // 删除视频
    public boolean deleteVideo(String videoName, String videoDir) {
        if (!FileUtils.inDir(videoName, videoDir))
            return false;
        return FileUtils.deleteFile(videoDir + videoName) && FileUtils.deleteFile(videoDir + videoName.substring(0, videoName.lastIndexOf(".")));
    }
}
