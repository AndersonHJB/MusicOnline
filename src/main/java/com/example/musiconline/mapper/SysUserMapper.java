package com.example.musiconline.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.musiconline.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}