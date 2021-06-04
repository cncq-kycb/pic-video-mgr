package cn.alberto.fileupload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlbertoWang
 * @email AlbertoWang@FoxMail.com
 * @date 2021/6/2 21:34
 * @description 图片名映射
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PicNameCommit {
    private int picNameId;
    private String picName;
}
