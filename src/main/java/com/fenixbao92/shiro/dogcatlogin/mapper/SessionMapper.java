package com.fenixbao92.shiro.dogcatlogin.mapper;

import com.fenixbao92.shiro.dogcatlogin.domain.Session;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create by Fenix_Bao on 2019/1/6.
 */
@Mapper
public interface SessionMapper {

    int insert(@Param("id") String id, @Param("session") String session);

    int delete(String sessionid);

    int update(@Param("id") String id, @Param("session") String session, @Param("username") String username);

    Session load(String sessionid);

    List<Session> loadByUserName(@Param("username") String username);
}
