package cn.alberto.fileupload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlbertoWang
 * @email AlbertoWang@FoxMail.com
 * @date 2021/6/2 21:33
 * @description 图片提交dto
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PicCommit {
    private int picId;
    private int picNameId;
    private String picPath;
    private String picUrl;
}
