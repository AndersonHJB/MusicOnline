package com.example.musiconline.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.musiconline.config.mybatis.mapper.BaseMapperPlus;
import com.example.musiconline.domain.Vinyl;
import com.example.musiconline.domain.vo.VinylVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author lds
 * @date 2025/3/15
 */
@Mapper
public interface VinylMapper extends BaseMapperPlus<Vinyl, VinylVo> {

    Page<VinylVo> selectVinyPage(Page<Object> build,@Param("ew") LambdaQueryWrapper<Vinyl> queryWrapper);
}