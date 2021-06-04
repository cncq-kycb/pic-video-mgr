package cn.alberto.fileupload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author AlbertoWang
 * @email AlbertoWang@FoxMail.com
 * @date 2021/6/4 22:50
 * @description TODO
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PicInfo {
    private String pic_name;
    private HashMap<String, String> picUrls;
}
