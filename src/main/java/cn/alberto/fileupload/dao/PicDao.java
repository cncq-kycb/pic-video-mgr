package cn.alberto.fileupload.dao;

import cn.alberto.fileupload.model.PicCommit;
import cn.alberto.fileupload.model.PicNameCommit;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author AlbertoWang
 * @email AlbertoWang@FoxMail.com
 * @date 2021/6/2 21:05
 * @description 图片提交记录
 **/

@Mapper
public interface PicDao {
    // 全部图片上传记录
    @Select("select * from pic_view")
    List<Map<String, Object>> getAllCommits();

    // 根据图片路径查找上传记录
    @Select("select * from pic_view where pic_path = #{picPath}")
    Map<String, Object> getOneCommit(String picPath);

    // 根据名称查找上传记录
    @Select("select * from pic_view where pic_name = #{picName}")
    List<Map<String, Object>> getCommitByPicName(String picName);

    // 插入图片名称
    @Options(useGeneratedKeys = true, keyProperty = "picNameId", keyColumn = "pic_name_id")
    @Insert("insert into pic_name(pic_name) values (#{picName})")
    void addPicName(PicNameCommit picName);

    // 插入图片记录
    @Insert("insert into pic(pic_name_id, pic_path, pic_url) values (#{picNameId}, #{picPath}, #{picUrl})")
    void addPic(PicCommit pic);

    // 删除图片记录
    @Delete("delete from pic where pic_path = #{picPath}")
    void deleteCommit(String picPath);

    // 获取图片列表全部名称
    @Select("select pic_name from pic_name")
    List<String> getAllPicName();
}
