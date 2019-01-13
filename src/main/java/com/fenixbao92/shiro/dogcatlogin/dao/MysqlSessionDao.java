package com.fenixbao92.shiro.dogcatlogin.dao;

import com.fenixbao92.shiro.dogcatlogin.mapper.SessionMapper;
import com.fenixbao92.shiro.dogcatlogin.utils.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Create by Fenix_Bao on 2019/1/6.
 */
public class MysqlSessionDao extends AbstractSessionDAO {

    @Resource
    private SessionMapper sessionMapper;

    @Override
    protected Serializable doCreate(Session session) {
        //生成session的id
        Serializable sessionId = generateSessionId(session);
        //给Session设定id
        assignSessionId(session, sessionId);

        //插入session 到数据库
        this.sessionMapper.insert(session.getId().toString(), SerializableUtils.serializ(session));

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {

        //获取session的字符串
        com.fenixbao92.shiro.dogcatlogin.domain.Session dbSession = this.sessionMapper.load(sessionId.toString());
        if(dbSession == null) {
            return null;
        }

        //加载session数据
        String sessionStr = dbSession.getSession();
        return SerializableUtils.deserializ(sessionStr);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        //当是ValidatingSession 无效的情况下，直接退出
        if(session instanceof ValidatingSession &&
                !((ValidatingSession)session).isValid() ) {
            return ;
        }

        //检索到用户名
        String username = String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));

        //序列化Session
        this.sessionMapper.update(session.getId().toString(), SerializableUtils.serializ(session),username);
    }

    @Override
    public void delete(Session session) {
        //删除session
        this.sessionMapper.delete(session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }

    public List<Session> loadByUserName(String username) {
        //获取session的字符串
        List<com.fenixbao92.shiro.dogcatlogin.domain.Session> dbSessions = this.sessionMapper.loadByUserName(username);

        //判断是否存在用户的情况
        if(dbSessions == null || dbSessions.size() == 0) {
            return null;
        }

        List<Session> result = new ArrayList<Session>();
        for(com.fenixbao92.shiro.dogcatlogin.domain.Session session:dbSessions) {
            //加载session数据
            String sessionStr = session.getSession();

            //将Session的数据串，转化为对象
            result.add(SerializableUtils.deserializ(sessionStr));
        }

        return result;
    }

}
