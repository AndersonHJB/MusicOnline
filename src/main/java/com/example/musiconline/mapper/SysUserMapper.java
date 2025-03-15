package com.example.musiconline.mapper;

import com.example.musiconline.config.mybatis.mapper.BaseMapperPlus;
import com.example.musiconline.domain.SysUser;
import com.example.musiconline.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapperPlus<SysUser, SysUserVo> {
}