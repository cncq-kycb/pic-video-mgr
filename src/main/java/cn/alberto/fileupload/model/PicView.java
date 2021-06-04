package cn.alberto.fileupload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlbertoWang
 * @email AlbertoWang@FoxMail.com
 * @date 2021/6/2 21:00
 * @description 图片提交记录vo
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PicView {
    private int picId;
    private String picName;
    private String picPath;
    private String picUrl;
}
