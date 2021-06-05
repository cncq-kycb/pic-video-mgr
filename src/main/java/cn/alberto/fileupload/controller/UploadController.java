package cn.alberto.fileupload.controller;

import cn.alberto.fileupload.dao.PicDao;
import cn.alberto.fileupload.model.PicCommit;
import cn.alberto.fileupload.model.PicNameCommit;
import cn.alberto.fileupload.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author AlbertoWang
 * @email AlbertoWang@FoxMail.com
 * @date 2021/5/31 00:08
 * @description 文件上传controller
 **/

@RestController
@CrossOrigin
public class UploadController {
    @Resource
    private PicDao picDao;
    // 图片上传路径
    @Value("${upload-cfg.pic-path}")
    private String picsDir;
    // 视频上传路径
    @Value("${upload-cfg.video-path}")
    private String videoDir;
    @Value("${url-cfg.pic-url}")
    private String basePicUrl;

    @Resource
    private UploadService uploadService;

    /*
    一次性上传多个图片
     */
    @PostMapping(value = "/upload-pics", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HashMap<String, Object> uploadPics(@RequestPart(value = "pics", required = false) MultipartFile[] pics, @RequestParam("picName") String picName) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        if (pics == null || pics.length == 0) {
            ret.put("msg", "未选择或上传任何文件");
            return ret;
        }
        PicNameCommit picNameCommit = new PicNameCommit();
        try {
            picNameCommit.setPicName(picName);
            picDao.addPicName(picNameCommit);
        } catch (Exception e) {
            ret.put("msg", "失败，图片名称可能重复");
            return ret;
        }
        for (MultipartFile f : pics) {
            String fileName = UUID.randomUUID() + "&" + f.getOriginalFilename();
            if (!uploadService.uploadMultipartFile(f, picsDir, fileName)) {
                ret.put("msg", "上传文件" + f.getOriginalFilename() + "失败");
                continue;
            }
            PicCommit picCommit = new PicCommit();
            picCommit.setPicNameId(picNameCommit.getPicNameId());
            picCommit.setPicPath(picsDir + fileName);
            picCommit.setPicUrl(basePicUrl + fileName);
            picDao.addPic(picCommit);
        }
        ret.put("msg", "上传成功");
        return ret;
    }

    /*
    根据图片名称查找图片
     */
    @GetMapping("/get-pics-by-name")
    public HashMap<String, Object> getPicsByName(@RequestParam("picName") String picName) {
        HashMap<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> pics = picDao.getCommitByPicName(picName);
        ret.put("msg", "成功");
        ret.put("picInfo", pics);
        return ret;
    }

    /*
    查找图片
     */
    @GetMapping("/get-pics")
    public HashMap<String, Object> getPics() {
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("picInfo", uploadService.getAllPics(picsDir));
        ret.put("msg", "成功");
        return ret;
    }

    /*
    删除图片
     */
    @PostMapping("/delete-pic")
    public HashMap<String, Object> deletePic(@RequestParam(value = "picName") String picName) {
        HashMap<String, Object> ret = new HashMap<>();
        if (picName == null || picName.equals("")) {
            ret.put("msg", "未给出删除图片的id");
            return ret;
        }
        if (uploadService.deletePic(picName, picsDir)) {
            picDao.deleteCommit(picsDir + picName);
            ret.put("msg", "成功");
        } else {
            ret.put("msg", "失败，考虑图片id不存在");
        }
        return ret;
    }

    /*
    删除视频
     */
    @PostMapping("/delete-video")
    public HashMap<String, Object> deleteVideo(@RequestParam(value = "videoName") String videoName) {
        HashMap<String, Object> ret = new HashMap<>();
        if (videoName == null || videoName.equals("")) {
            ret.put("msg", "未给出删除视频的id");
            return ret;
        }
        if (uploadService.deleteVideo(videoName, videoDir)) {
            ret.put("msg", "成功");
        } else {
            ret.put("msg", "失败，考虑视频id不存在");
        }
        return ret;
    }

    /*
    查找视频缩略图
     */
    @GetMapping("/get-video-pics")
    public HashMap<String, Object> getVideoPics() {
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("picUrls", uploadService.getAllVideoPics(videoDir));
        ret.put("msg", "成功");
        return ret;
    }

    /*
    查找视频
     */
    @GetMapping("/get-video")
    public HashMap<String, Object> getVideo(@RequestParam("videoName") String videoName) {
        HashMap<String, Object> ret = new HashMap<>();
        if (videoName == null || videoName.equals("")) {
            ret.put("msg", "未上传视频id");
        } else {
            ret.put("videoUrl", uploadService.getVideoByName(videoName, videoDir));
            ret.put("msg", "成功");
        }
        return ret;
    }

    /*
    上传视频
     */
    @PostMapping(value = "/upload-video", consumes = "multipart/form-data")
    public HashMap<String, Object> uploadVideo(@RequestParam("video") MultipartFile video) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        if (video == null) {
            ret.put("msg", "未选择/上传任何文件");
            return ret;
        }
        UUID uuid = UUID.randomUUID();
        String videoName = video.getOriginalFilename();
        String fileName = uuid + "&" + videoName;
        String videoPath = videoDir + fileName;
        String videoPicPath = videoPath + ".png";
        if (!(uploadService.uploadMultipartFile(video, videoDir, fileName) && uploadService.genVideoPic(videoPath, videoPicPath))) {
            ret.put("msg", "上传文件" + video.getOriginalFilename() + "失败");
        }
        ret.put("msg", "上传成功");
        return ret;
    }
}
