package com.philip.lab.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.philip.lab.pos.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT u.*, s.id as s_id, s.name as s_name, s.store_type as s_store_type, s.slug as s_slug " +
            "FROM users_user u " +
            "LEFT JOIN store_store s ON u.store_id = s.id " +
            "WHERE u.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "storeId", column = "store_id"),
            @Result(property = "store.id", column = "s_id"),
            @Result(property = "store.name", column = "s_name"),
            @Result(property = "store.storeType", column = "s_store_type"),
            @Result(property = "store.slug", column = "s_slug")
    })
    User selectUserWithStore(Long id);
}